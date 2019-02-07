package austeretony.plantbiomes.common.commands;

import austeretony.plantbiomes.common.main.EnumChatMessages;
import austeretony.plantbiomes.common.main.PBLoader;
import austeretony.plantbiomes.common.main.PBManager;
import austeretony.plantbiomes.common.network.NetworkHandler;
import austeretony.plantbiomes.common.network.client.CPSetOverlayStatus;
import austeretony.plantbiomes.common.network.client.CPSyncPlantsData;
import austeretony.plantbiomes.common.reference.CommonReference;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class CommandPB extends CommandBase {

    public static final String 
    NAME = "pb",
    USAGE = "/pb <arg>, type </pb help> for available arguments.";

    @Override
    public String getName() {               
        return NAME;
    }

    @Override
    public String getUsage(ICommandSender sender) {         
        return USAGE;
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {     
        return sender instanceof EntityPlayerMP && CommonReference.isOpped((EntityPlayerMP) sender);
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {  
        EnumCommandPBArgs arg;
        if (args.length != 1 || (arg = EnumCommandPBArgs.get(args[0])) == null)        
            throw new WrongUsageException(this.getUsage(sender));   
        EntityPlayer player = getCommandSenderAsPlayer(sender);  
        switch(arg) {
        case HELP:
            EnumChatMessages.COMMAND_HELP.sendMessage(player);
            break;
        case ENABLE:
            PBManager.setSettingsEnabled(true);
            if (PBManager.isAutosaveEnabled())
                PBLoader.saveSettings();
            EnumChatMessages.SETTINGS_ENABLED.sendMessage(player);
            break;
        case DISABLE:
            PBManager.setSettingsEnabled(false);
            if (PBManager.isAutosaveEnabled())
                PBLoader.saveSettings();
            EnumChatMessages.SETTINGS_DISABLED.sendMessage(player);
            break;  
        case STATUS:
            EnumChatMessages.STATUS.sendMessage(player);
            break;  
        case ENABLE_CONFIG_MODE:
            if (!this.validAction(player, false, false, false, false))
                return;
            PBManager.setConfigModeEnabled(true);
            EnumChatMessages.CONFIGURATION_ENABLED.sendMessage(player);
            break;
        case DISABLE_CONFIG_MODE:
            if (!this.validAction(player, false, false, false, false))
                return;
            PBManager.setConfigModeEnabled(false);
            PBManager.latestPlant = null;
            EnumChatMessages.CONFIGURATION_DISABLED.sendMessage(player);
            break;  
        case ENABLE_OVERLAY:
            if (!PBManager.isOverlayEnabled()) {
                EnumChatMessages.OVERLAY_DENIED.sendMessage(player);
                return;
            }
            NetworkHandler.sendTo(new CPSetOverlayStatus(CPSetOverlayStatus.EnumStatus.ENABLED), (EntityPlayerMP) player);
            EnumChatMessages.OVERLAY_ENABLED.sendMessage(player);
            break;
        case DISABLE_OVERLAY:
            if (!PBManager.isOverlayEnabled()) {
                EnumChatMessages.OVERLAY_DENIED.sendMessage(player);
                return;
            }
            NetworkHandler.sendTo(new CPSetOverlayStatus(CPSetOverlayStatus.EnumStatus.DISABLED), (EntityPlayerMP) player);
            EnumChatMessages.OVERLAY_DISABLED.sendMessage(player);
            break;  
        case SETTINGS:
            EnumChatMessages.SETTINGS_LIST.sendMessage(player);
            break;
        case BIOME:
            EnumChatMessages.CURRENT_BIOME.sendMessage(player, PBManager.getBiomeRegistryName(player.world, player.getPosition()).toString());
            break;
        case LATEST:
            if (!this.validAction(player, true, true, false, false))
                return;
            EnumChatMessages.LATEST_PLANT.sendMessage(player);
            break;
        case DENY:
            if (!this.validAction(player, true, true, false, false))
                return;
            if (!PBManager.existMetaLatest())
                PBManager.createMetaLatest();
            PBManager.getMetaLatest().denyBiome(PBManager.latestPlant.biomeRegistryName);
            if (PBManager.isAutosaveEnabled())
                PBLoader.saveSettings();
            if (PBManager.isOverlayEnabled())
                NetworkHandler.sendTo(new CPSyncPlantsData(CPSyncPlantsData.EnumAction.SYNC_LATEST), (EntityPlayerMP) player);
            EnumChatMessages.DENIED.sendMessage(player);
            break;
        case ALLOW:
            if (!this.validAction(player, true, true, true, false))
                return;
            PBManager.getMetaLatest().allowBiome(PBManager.latestPlant.biomeRegistryName);
            if (PBManager.isAutosaveEnabled())
                PBLoader.saveSettings();
            if (PBManager.isOverlayEnabled())
                NetworkHandler.sendTo(new CPSyncPlantsData(CPSyncPlantsData.EnumAction.SYNC_LATEST), (EntityPlayerMP) player);
            EnumChatMessages.ALLOWED.sendMessage(player);
            break;
        case DENY_GLOBAL:
            if (!this.validAction(player, true, true, false, false))
                return;
            if (!PBManager.existMetaLatest())
                PBManager.createMetaLatest();
            PBManager.getMetaLatest().denyGlobal();
            if (PBManager.isAutosaveEnabled())
                PBLoader.saveSettings();
            if (PBManager.isOverlayEnabled())
                NetworkHandler.sendTo(new CPSyncPlantsData(CPSyncPlantsData.EnumAction.SYNC_LATEST), (EntityPlayerMP) player);
            EnumChatMessages.DENIED_GLOBAL.sendMessage(player);
            break;
        case ALLOW_GLOBAL:
            if (!this.validAction(player, true, true, true, false))
                return;
            PBManager.getMetaLatest().allowGlobal();
            if (PBManager.isAutosaveEnabled())
                PBLoader.saveSettings();
            if (PBManager.isOverlayEnabled())
                NetworkHandler.sendTo(new CPSyncPlantsData(CPSyncPlantsData.EnumAction.SYNC_LATEST), (EntityPlayerMP) player);
            EnumChatMessages.ALLOWED_GLOBAL.sendMessage(player);
            break;
        case ADD_VALID:
            if (!this.validAction(player, true, true, false, false))
                return;
            if (!PBManager.existMetaLatest())
                PBManager.createMetaLatest();
            PBManager.getMetaLatest().addValidBiome(PBManager.latestPlant.biomeRegistryName);
            if (PBManager.isAutosaveEnabled())
                PBLoader.saveSettings();
            if (PBManager.isOverlayEnabled())
                NetworkHandler.sendTo(new CPSyncPlantsData(CPSyncPlantsData.EnumAction.SYNC_LATEST), (EntityPlayerMP) player);
            EnumChatMessages.VALID_BIOME_ADDED.sendMessage(player);
            break;
        case REMOVE_VALID:
            if (!this.validAction(player, true, true, true, false))
                return;
            PBManager.getMetaLatest().removeValidBiome(PBManager.latestPlant.biomeRegistryName);
            if (PBManager.isAutosaveEnabled())
                PBLoader.saveSettings();
            if (PBManager.isOverlayEnabled())
                NetworkHandler.sendTo(new CPSyncPlantsData(CPSyncPlantsData.EnumAction.SYNC_LATEST), (EntityPlayerMP) player);
            EnumChatMessages.VALID_BIOME_REMOVED.sendMessage(player);
            break;
        case SET_MAIN:
            if (!this.validAction(player, true, true, true, false))
                return;
            PBManager.getLatest().setMainMeta(PBManager.latestPlant.meta);
            if (PBManager.isAutosaveEnabled())
                PBLoader.saveSettings();
            if (PBManager.isOverlayEnabled())
                NetworkHandler.sendTo(new CPSyncPlantsData(CPSyncPlantsData.EnumAction.SYNC_LATEST), (EntityPlayerMP) player);
            EnumChatMessages.MAIN_META_SET.sendMessage(player);
            break;
        case RESET_MAIN:
            if (!this.validAction(player, true, true, false, false))
                return;
            PBManager.getLatest().resetMainMeta();
            if (PBManager.isAutosaveEnabled())
                PBLoader.saveSettings();
            if (PBManager.isOverlayEnabled())
                NetworkHandler.sendTo(new CPSyncPlantsData(CPSyncPlantsData.EnumAction.SYNC_LATEST), (EntityPlayerMP) player);
            EnumChatMessages.MAIN_META_RESET.sendMessage(player);
            break;
        case CLEAR_DENIED:
            if (!this.validAction(player, true, false, true, false))
                return;
            PBManager.getMetaLatest().clearDeniedBiomes();
            PBLoader.saveSettings();
            if (PBManager.isOverlayEnabled())
                NetworkHandler.sendTo(new CPSyncPlantsData(CPSyncPlantsData.EnumAction.SYNC_LATEST), (EntityPlayerMP) player);
            EnumChatMessages.DENIED_BIOMES_CLEARED.sendMessage(player);                                                   
            break;
        case CLEAR_VALID:
            if (!this.validAction(player, true, false, true, false))
                return;
            PBManager.getMetaLatest().clearValidBiomes();
            PBLoader.saveSettings();
            if (PBManager.isOverlayEnabled())
                NetworkHandler.sendTo(new CPSyncPlantsData(CPSyncPlantsData.EnumAction.SYNC_LATEST), (EntityPlayerMP) player);
            EnumChatMessages.VALID_BIOMES_CLEARED.sendMessage(player);                                                   
            break;
        case CLEAR_LATEST:
            if (!this.validAction(player, true, false, true, false))
                return;
            PBManager.removeMetaLatest();//TODO This cause settings mess up (map data disappear), need to investigate why.
            PBLoader.saveSettings();
            PBManager.initServerData();//Fast fix. 
            if (PBManager.isOverlayEnabled())
                NetworkHandler.sendTo(new CPSyncPlantsData(CPSyncPlantsData.EnumAction.SYNC_ALL), (EntityPlayerMP) player);//REMOVE_LATEST should be used instead, but it will break overlay render.
            EnumChatMessages.LATEST_PLANT_CLEARED.sendMessage(player);                                                   
            break;
        case CLEAR_ALL:
            if (!this.validAction(player, true, false, false, true))
                return; 
            PBManager.clearDataServer();
            PBLoader.saveSettings();
            if (PBManager.isOverlayEnabled())
                NetworkHandler.sendTo(new CPSyncPlantsData(CPSyncPlantsData.EnumAction.REMOVE_ALL), (EntityPlayerMP) player);
            EnumChatMessages.PLANTS_LIST_CLEARED.sendMessage(player);
            break;
        case SAVE:
            if (!this.validAction(player, true, false, false, false))
                return;
            PBLoader.saveSettings();
            EnumChatMessages.SETTINGS_SAVED.sendMessage(player);
            break;
        case RELOAD:
            if (!this.validAction(player, true, false, false, false))
                return;
            PBManager.initServerData();
            if (PBManager.isOverlayEnabled())
                NetworkHandler.sendTo(new CPSyncPlantsData(CPSyncPlantsData.EnumAction.SYNC_ALL), (EntityPlayerMP) player);
            EnumChatMessages.SETTINGS_RELOADED.sendMessage(player);
            break;
        case BACKUP:
            if (!this.validAction(player, true, false, false, false))
                return;
            PBLoader.backupSettings();
            EnumChatMessages.BACKUP_CREATED.sendMessage(player);
            break;
        }
    }

    private boolean validAction(EntityPlayer player, boolean checkMode, boolean checkLatest, boolean checkDataLatest, boolean checkDataAll) {
        if (!PBManager.isExternalConfigEnabled()) {
            EnumChatMessages.EXTERNAL_CONFIG_DISABLED.sendMessage(player);
            return false;
        } else if (checkMode && !PBManager.isConfigModeEnabled()) {
            EnumChatMessages.NEED_ENABLE_CONFIGURATION.sendMessage(player);
            return false;
        } else if (checkLatest && PBManager.latestPlant == null) {   
            EnumChatMessages.NO_LATEST_PLANT.sendMessage(player);
            return false;
        } else if (checkDataLatest && !PBManager.existMetaLatest()) {
            EnumChatMessages.NO_DATA_FOR_PLANT.sendMessage(player);
            return false;
        } else if (checkDataAll && PBManager.getDataServer().isEmpty()) {
            EnumChatMessages.NO_DATA.sendMessage(player);
            return false;
        }
        return true;
    }
}
