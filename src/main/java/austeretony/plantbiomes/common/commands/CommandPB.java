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
            EnumChatMessages.COMMAND_PB_HELP.sendMessage(player);
            break;
        case ENABLE:
            PBManager.setSettingsEnabled(true);
            if (PBManager.isAutosaveEnabled())
                PBLoader.saveSettings();
            EnumChatMessages.COMMAND_PB_ENABLE.sendMessage(player);
            break;
        case DISABLE:
            PBManager.setSettingsEnabled(false);
            if (PBManager.isAutosaveEnabled())
                PBLoader.saveSettings();
            EnumChatMessages.COMMAND_PB_DISABLE.sendMessage(player);
            break;  
        case STATUS:
            EnumChatMessages.COMMAND_PB_STATUS.sendMessage(player);
            break;  
        case ENABLE_CONFIG_MODE:
            if (!this.validAction(player, false, false, false, false)) break;
            PBManager.setConfigModeEnabled(true);
            EnumChatMessages.COMMAND_PB_ENABLE_CONFIG.sendMessage(player);
            break;
        case DISABLE_CONFIG_MODE:
            if (!this.validAction(player, false, false, false, false)) break;
            PBManager.setConfigModeEnabled(false);
            PBManager.latestPlant = null;
            EnumChatMessages.COMMAND_PB_DISABLE_CONFIG.sendMessage(player);
            break;  
        case ENABLE_OVERLAY:
            if (!PBManager.isOverlayEnabled()) {
                EnumChatMessages.COMMAND_PB_ERR_OVERLAY_DENIED.sendMessage(player);
                break;
            }
            NetworkHandler.sendTo(new CPSetOverlayStatus(CPSetOverlayStatus.EnumStatus.ENABLED), (EntityPlayerMP) player);
            EnumChatMessages.COMMAND_PB_ENABLE_OVERLAY.sendMessage(player);
            break;
        case DISABLE_OVERLAY:
            if (!PBManager.isOverlayEnabled()) {
                EnumChatMessages.COMMAND_PB_ERR_OVERLAY_DENIED.sendMessage(player);
                break;
            }
            NetworkHandler.sendTo(new CPSetOverlayStatus(CPSetOverlayStatus.EnumStatus.DISABLED), (EntityPlayerMP) player);
            EnumChatMessages.COMMAND_PB_DISABLE_OVERLAY.sendMessage(player);
            break;  
        case SETTINGS:
            EnumChatMessages.COMMAND_PB_SETTINGS.sendMessage(player);
            break;
        case BIOME:
            EnumChatMessages.COMMAND_PB_BIOME.sendMessage(player, PBManager.getBiomeRegistryName(player.world, player.getPosition()).toString());
            break;
        case LATEST:
            if (!this.validAction(player, true, true, false, false)) break;
            EnumChatMessages.COMMAND_PB_LATEST.sendMessage(player);
            break;
        case DENY:
            if (!this.validAction(player, true, true, false, false))  break;
            if (!PBManager.existMetaLatest())
                PBManager.createMetaLatest();
            PBManager.getMetaLatest().denyBiome(PBManager.latestPlant.biomeRegistryName);
            if (PBManager.isAutosaveEnabled())
                PBLoader.saveSettings();
            if (PBManager.isOverlayEnabled())
                NetworkHandler.sendTo(new CPSyncPlantsData(CPSyncPlantsData.EnumAction.SYNC_LATEST), (EntityPlayerMP) player);
            EnumChatMessages.COMMAND_PB_DENY.sendMessage(player);
            break;
        case ALLOW:
            if (!this.validAction(player, true, true, true, false)) break;
            PBManager.getMetaLatest().allowBiome(PBManager.latestPlant.biomeRegistryName);
            if (PBManager.isAutosaveEnabled())
                PBLoader.saveSettings();
            if (PBManager.isOverlayEnabled())
                NetworkHandler.sendTo(new CPSyncPlantsData(CPSyncPlantsData.EnumAction.SYNC_LATEST), (EntityPlayerMP) player);
            EnumChatMessages.COMMAND_PB_ALLOW.sendMessage(player);
            break;
        case DENY_GLOBAL:
            if (!this.validAction(player, true, true, false, false)) break;
            if (!PBManager.existMetaLatest())
                PBManager.createMetaLatest();
            PBManager.getMetaLatest().denyGlobal();
            if (PBManager.isAutosaveEnabled())
                PBLoader.saveSettings();
            if (PBManager.isOverlayEnabled())
                NetworkHandler.sendTo(new CPSyncPlantsData(CPSyncPlantsData.EnumAction.SYNC_LATEST), (EntityPlayerMP) player);
            EnumChatMessages.COMMAND_PB_DENY_GLOBAL.sendMessage(player);
            break;
        case ALLOW_GLOBAL:
            if (!this.validAction(player, true, true, true, false)) break;
            PBManager.getMetaLatest().allowGlobal();
            if (PBManager.isAutosaveEnabled())
                PBLoader.saveSettings();
            if (PBManager.isOverlayEnabled())
                NetworkHandler.sendTo(new CPSyncPlantsData(CPSyncPlantsData.EnumAction.SYNC_LATEST), (EntityPlayerMP) player);
            EnumChatMessages.COMMAND_PB_ALLOW_GLOBAL.sendMessage(player);
            break;
        case ADD_VALID:
            if (!this.validAction(player, true, true, false, false)) break;
            if (!PBManager.existMetaLatest())
                PBManager.createMetaLatest();
            PBManager.getMetaLatest().addValidBiome(PBManager.latestPlant.biomeRegistryName);
            if (PBManager.isAutosaveEnabled())
                PBLoader.saveSettings();
            if (PBManager.isOverlayEnabled())
                NetworkHandler.sendTo(new CPSyncPlantsData(CPSyncPlantsData.EnumAction.SYNC_LATEST), (EntityPlayerMP) player);
            EnumChatMessages.COMMAND_PB_ADD_VALID.sendMessage(player);
            break;
        case REMOVE_VALID:
            if (!this.validAction(player, true, true, true, false)) break;
            PBManager.getMetaLatest().removeValidBiome(PBManager.latestPlant.biomeRegistryName);
            if (PBManager.isAutosaveEnabled())
                PBLoader.saveSettings();
            if (PBManager.isOverlayEnabled())
                NetworkHandler.sendTo(new CPSyncPlantsData(CPSyncPlantsData.EnumAction.SYNC_LATEST), (EntityPlayerMP) player);
            EnumChatMessages.COMMAND_PB_REMOVE_VALID.sendMessage(player);
            break;
        case SET_MAIN:
            if (!this.validAction(player, true, true, true, false)) break;
            PBManager.getLatest().setMainMeta(PBManager.latestPlant.meta);
            if (PBManager.isAutosaveEnabled())
                PBLoader.saveSettings();
            if (PBManager.isOverlayEnabled())
                NetworkHandler.sendTo(new CPSyncPlantsData(CPSyncPlantsData.EnumAction.SYNC_LATEST), (EntityPlayerMP) player);
            EnumChatMessages.COMMAND_PB_SET_MAIN.sendMessage(player);
            break;
        case RESET_MAIN:
            if (!this.validAction(player, true, true, false, false)) break;
            PBManager.getLatest().resetMainMeta();
            if (PBManager.isAutosaveEnabled())
                PBLoader.saveSettings();
            if (PBManager.isOverlayEnabled())
                NetworkHandler.sendTo(new CPSyncPlantsData(CPSyncPlantsData.EnumAction.SYNC_LATEST), (EntityPlayerMP) player);
            EnumChatMessages.COMMAND_PB_RESET_MAIN.sendMessage(player);
            break;
        case CLEAR_DENIED:
            if (!this.validAction(player, true, false, true, false)) break;
            PBManager.getMetaLatest().clearDeniedBiomes();
            PBLoader.saveSettings();
            if (PBManager.isOverlayEnabled())
                NetworkHandler.sendTo(new CPSyncPlantsData(CPSyncPlantsData.EnumAction.SYNC_LATEST), (EntityPlayerMP) player);
            EnumChatMessages.COMMAND_PB_CLEAR_DENIED.sendMessage(player);                                                   
            break;
        case CLEAR_VALID:
            if (!this.validAction(player, true, false, true, false)) break;
            PBManager.getMetaLatest().clearValidBiomes();
            PBLoader.saveSettings();
            if (PBManager.isOverlayEnabled())
                NetworkHandler.sendTo(new CPSyncPlantsData(CPSyncPlantsData.EnumAction.SYNC_LATEST), (EntityPlayerMP) player);
            EnumChatMessages.COMMAND_PB_CLEAR_VALID.sendMessage(player);                                                   
            break;
        case CLEAR_LATEST:
            if (!this.validAction(player, true, false, true, false)) break;
            PBManager.removeMetaLatest();//TODO This cause settings mess up (map data disappear), need to investigate why.
            PBLoader.saveSettings();
            PBManager.initServerData();//Fast fix. 
            if (PBManager.isOverlayEnabled())
                NetworkHandler.sendTo(new CPSyncPlantsData(CPSyncPlantsData.EnumAction.SYNC_ALL), (EntityPlayerMP) player);//REMOVE_LATEST should be used instead, but it will break overlay render.
            EnumChatMessages.COMMAND_PB_CLEAR_LATEST.sendMessage(player);                                                   
            break;
        case CLEAR_ALL:
            if (!this.validAction(player, true, false, false, true)) break; 
            PBManager.clearDataServer();
            PBLoader.saveSettings();
            if (PBManager.isOverlayEnabled())
                NetworkHandler.sendTo(new CPSyncPlantsData(CPSyncPlantsData.EnumAction.REMOVE_ALL), (EntityPlayerMP) player);
            EnumChatMessages.COMMAND_PB_CLEAR_ALL.sendMessage(player);
            break;
        case SAVE:
            if (!this.validAction(player, true, false, false, false)) break;
            PBLoader.saveSettings();
            EnumChatMessages.COMMAND_PB_SAVE.sendMessage(player);
            break;
        case RELOAD:
            if (!this.validAction(player, true, false, false, false)) break;
            PBManager.initServerData();
            if (PBManager.isOverlayEnabled())
                NetworkHandler.sendTo(new CPSyncPlantsData(CPSyncPlantsData.EnumAction.SYNC_ALL), (EntityPlayerMP) player);
            EnumChatMessages.COMMAND_PB_RELOAD.sendMessage(player);
            break;
        case BACKUP:
            if (!this.validAction(player, true, false, false, false)) break;
            PBLoader.backupSettings();
            EnumChatMessages.COMMAND_PB_BACKUP.sendMessage(player);
            break;
        }
    }

    private boolean validAction(EntityPlayer player, boolean checkMode, boolean checkLatest, boolean checkDataLatest, boolean checkDataAll) {
        if (!PBManager.isExternalConfigEnabled()) {
            EnumChatMessages.COMMAND_PB_ERR_EXTERNAL_CONFIG_DISABLED.sendMessage(player);
            return false;
        } else if (checkMode && !PBManager.isConfigModeEnabled()) {
            EnumChatMessages.COMMAND_PB_ERR_NEED_ENABLE_CONFIG.sendMessage(player);
            return false;
        } else if (checkLatest && PBManager.latestPlant == null) {   
            EnumChatMessages.COMMAND_PB_ERR_NO_LATEST.sendMessage(player);
            return false;
        } else if (checkDataLatest && !PBManager.existMetaLatest()) {
            EnumChatMessages.COMMAND_PB_ERR_NO_DATA_FOR_LATEST.sendMessage(player);
            return false;
        } else if (checkDataAll && PBManager.getDataServer().isEmpty()) {
            EnumChatMessages.COMMAND_PB_ERR_NO_DATA.sendMessage(player);
            return false;
        }
        return true;
    }
}
