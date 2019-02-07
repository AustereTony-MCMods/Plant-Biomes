package austeretony.plantbiomes.common.network.client;

import austeretony.plantbiomes.common.main.PBManager;
import austeretony.plantbiomes.common.network.ProxyPacket;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;

public class CPSetOverlayStatus extends ProxyPacket {

    private byte overlayStatus;

    private boolean isTilesAllowed;

    public CPSetOverlayStatus() {}

    public CPSetOverlayStatus(EnumStatus enumStatus) {
        this.overlayStatus = (byte) enumStatus.ordinal();
        this.isTilesAllowed = PBManager.isTilesOverlayEnabled();
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeByte(this.overlayStatus);
        buffer.writeBoolean(this.isTilesAllowed);
    }

    @Override
    public void read(PacketBuffer buffer) {
        this.overlayStatus = buffer.readByte();
        this.isTilesAllowed = buffer.readBoolean();
    }

    @Override
    public void process(INetHandler netHandler) {
        PBManager.setOverlayStatusClient(EnumStatus.values()[this.overlayStatus]);
        PBManager.setTilesAllowedClient(this.isTilesAllowed);
    }

    public enum EnumStatus {

        ENABLED,
        DISABLED
    }
}
