package austeretony.plantbiomes.common.network;

import java.io.IOException;

import com.google.common.collect.HashBiMap;

import austeretony.plantbiomes.common.main.PlantBiomesMain;
import austeretony.plantbiomes.common.network.client.CPSetOverlayStatus;
import austeretony.plantbiomes.common.network.client.CPSyncPlantsData;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;

public class NetworkHandler {

    private static FMLEventChannel channel;

    public static HashBiMap<Integer, Class<? extends ProxyPacket>> packets = HashBiMap.<Integer, Class<? extends ProxyPacket>>create();

    private static int id;

    private NetworkHandler() {}

    public static void init() {
        channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(PlantBiomesMain.MODID);
        channel.register(new NetworkHandler());
        registerPackets();
    }

    private static void registerPackets() {
        register(CPSyncPlantsData.class);
        register(CPSetOverlayStatus.class);
    }

    private static void register(Class<? extends ProxyPacket> packet) {
        packets.put(id++, packet);
    }

    @SubscribeEvent
    public void onClientPacketRecieve(FMLNetworkEvent.ClientCustomPacketEvent event) throws IOException {
        process(event);
    }

    @SubscribeEvent
    public void onServerPacketRecieve(FMLNetworkEvent.ServerCustomPacketEvent event) throws IOException {
        process(event);
    }

    private static FMLProxyPacket pack(ProxyPacket modPacket) {
        PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
        packetBuffer.writeByte(packets.inverse().get(modPacket.getClass()));
        modPacket.write(packetBuffer);
        FMLProxyPacket packet = new FMLProxyPacket(packetBuffer, PlantBiomesMain.MODID);
        return packet;
    }

    private void process(FMLNetworkEvent.CustomPacketEvent event) throws IOException {
        FMLProxyPacket proxyPacket = event.getPacket();
        if (PlantBiomesMain.MODID.equals(proxyPacket.channel())) {
            ByteBuf byteBuf = proxyPacket.payload();
            int readableBytes = byteBuf.readableBytes();
            if (readableBytes != 0) {
                PacketBuffer packetBuffer = new PacketBuffer(byteBuf);
                int id = packetBuffer.readByte();
                ProxyPacket packet = ProxyPacket.create(packets, id);
                if (packet == null) {
                    PlantBiomesMain.LOGGER.error("Wrong packet id: " + id);
                } else {
                    packet.read(packetBuffer);
                    packet.process(event.getHandler());
                }
            }
        }
    }

    public static void sendTo(ProxyPacket packet, EntityPlayerMP player) {
        channel.sendTo(pack(packet), player);
    }
}
