package austeretony.plantbiomes.common.network.client;

import austeretony.plantbiomes.common.main.DataManager;
import austeretony.plantbiomes.common.network.ProxyPacket;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;

public class CPSetOverlayStatus extends ProxyPacket {

    private byte overlayStatus;

    public CPSetOverlayStatus() {}

    public CPSetOverlayStatus(EnumStatus enumStatus) {
        this.overlayStatus = (byte) enumStatus.ordinal();
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeByte(this.overlayStatus);
    }

    @Override
    public void read(PacketBuffer buffer) {
        this.overlayStatus = buffer.readByte();
    }

    @Override
    public void process(INetHandler netHandler) {
        DataManager.setOverlayStatusClient(EnumStatus.values()[this.overlayStatus]);
    }

    public enum EnumStatus {

        ENABLED,
        DISABLED
    }
}
