package austeretony.plantbiomes.common.network.client;

import austeretony.plantbiomes.common.main.PBManager;
import austeretony.plantbiomes.common.network.ProxyPacket;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;

public class CPInitData extends ProxyPacket {

    public CPInitData() {}

    @Override
    public void write(PacketBuffer buffer) {}

    @Override
    public void read(PacketBuffer buffer) {}

    @Override
    public void process(INetHandler netHandler) {
        PBManager.initClientData();
    }
}
