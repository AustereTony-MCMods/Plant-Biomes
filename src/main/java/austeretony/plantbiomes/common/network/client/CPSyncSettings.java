package austeretony.plantbiomes.common.network.client;

import austeretony.plantbiomes.common.config.EnumConfigSettings;
import austeretony.plantbiomes.common.main.DataManager;
import austeretony.plantbiomes.common.network.ProxyPacket;
import net.minecraft.network.INetHandler;
import net.minecraft.network.PacketBuffer;

public class CPSyncSettings extends ProxyPacket {

    private boolean isSettingsEnabled, allowSettingsTooltips, allowTilesOverlay;

    public CPSyncSettings() {
        this.isSettingsEnabled = DataManager.isSettingsEnabled();
        this.allowSettingsTooltips = EnumConfigSettings.SETTINGS_TOOLTIPS.isEnabled();
        this.allowTilesOverlay = EnumConfigSettings.TILES_OVERLAY.isEnabled();
    }

    @Override
    public void write(PacketBuffer buffer) {  
        buffer.writeBoolean(this.isSettingsEnabled);
        buffer.writeBoolean(this.allowSettingsTooltips);
        buffer.writeBoolean(this.allowTilesOverlay);
    }

    @Override
    public void read(PacketBuffer buffer) { 
        this.isSettingsEnabled = buffer.readBoolean();
        this.allowSettingsTooltips = buffer.readBoolean();
        this.allowTilesOverlay = buffer.readBoolean();
    }

    @Override
    public void process(INetHandler netHandler) {
        DataManager.setSettingsEnabledClient(this.isSettingsEnabled);
        DataManager.setSettingsTooltipsAllowedClient(this.allowSettingsTooltips);
        DataManager.setTilesOverlayAllowedClient(this.allowTilesOverlay);
    }
}
