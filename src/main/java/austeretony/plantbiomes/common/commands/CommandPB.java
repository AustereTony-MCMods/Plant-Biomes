package austeretony.plantbiomes.common.commands;

import austeretony.plantbiomes.common.config.ConfigLoader;
import austeretony.plantbiomes.common.config.EnumConfigSettings;
import austeretony.plantbiomes.common.main.DataManager;
import austeretony.plantbiomes.common.main.EnumChatMessages;
import austeretony.plantbiomes.common.network.NetworkHandler;
import austeretony.plantbiomes.common.network.client.CPSetOverlayStatus;
import austeretony.plantbiomes.common.network.client.CPSyncPlantsData;
import austeretony.plantbiomes.common.network.client.CPSyncSettings;
import austeretony.plantbiomes.common.reference.CommonReference;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
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
            DataManager.setSettingsEnabled(true);
            this.save();
            NetworkHandler.sendTo(new CPSyncSettings(), (EntityPlayerMP) player);
            EnumChatMessages.COMMAND_PB_ENABLE.sendMessage(player);
            break;
        case DISABLE:
            DataManager.setSettingsEnabled(false);
            this.save();
            NetworkHandler.sendTo(new CPSyncSettings(), (EntityPlayerMP) player);
            EnumChatMessages.COMMAND_PB_DISABLE.sendMessage(player);
            break;  
        case STATUS:
            EnumChatMessages.COMMAND_PB_STATUS.sendMessage(player);
            break;  
        case ENABLE_CONFIG_MODE:
            if (!this.validAction(player, false, false, false, false)) break;
            DataManager.setConfigModeEnabled(true);
            EnumChatMessages.COMMAND_PB_ENABLE_CONFIG.sendMessage(player);
            break;
        case DISABLE_CONFIG_MODE:
            if (!this.validAction(player, false, false, false, false)) break;
            DataManager.setConfigModeEnabled(false);
            DataManager.latestPlantServer = null;
            EnumChatMessages.COMMAND_PB_DISABLE_CONFIG.sendMessage(player);
            break;  
        case ENABLE_OVERLAY:
            if (!EnumConfigSettings.SETTINGS_OVERLAY.isEnabled()) {
                EnumChatMessages.COMMAND_PB_ERR_OVERLAY_DENIED.sendMessage(player);
                break;
            }
            NetworkHandler.sendTo(new CPSetOverlayStatus(CPSetOverlayStatus.EnumStatus.ENABLED), (EntityPlayerMP) player);
            EnumChatMessages.COMMAND_PB_ENABLE_OVERLAY.sendMessage(player);
            break;
        case DISABLE_OVERLAY:
            if (!EnumConfigSettings.SETTINGS_OVERLAY.isEnabled()) {
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
            EnumChatMessages.COMMAND_PB_BIOME.sendMessage(player, DataManager.getBiomeRegistryName(player.world, player.getPosition()).toString());
            break;
        case LATEST:
            if (!this.validAction(player, true, true, false, false)) break;
            EnumChatMessages.COMMAND_PB_LATEST.sendMessage(player);
            break;
        case DENY:
            if (!this.validAction(player, true, true, false, false))  break;
            if (!DataManager.existMetaLatest())
                DataManager.createMetaLatest();
            DataManager.getLatest().denyBiome(DataManager.latestPlantServer.meta, DataManager.latestPlantServer.biomeRegistryName);
            this.save();
            this.sync(player, CPSyncPlantsData.EnumAction.SYNC_LATEST);
            EnumChatMessages.COMMAND_PB_DENY.sendMessage(player);
            break;
        case ALLOW:
            if (!this.validAction(player, true, true, true, false)) break;
            DataManager.getLatest().allowBiome(DataManager.latestPlantServer.meta, DataManager.latestPlantServer.biomeRegistryName);
            this.save();
            this.sync(player, CPSyncPlantsData.EnumAction.SYNC_LATEST);
            EnumChatMessages.COMMAND_PB_ALLOW.sendMessage(player);
            break;
        case DENY_GLOBAL:
            if (!this.validAction(player, true, true, false, false)) break;
            if (!DataManager.existMetaLatest())
                DataManager.createMetaLatest();
            DataManager.getLatest().setDeniedGlobal(DataManager.latestPlantServer.meta, true);
            this.save();
            this.sync(player, CPSyncPlantsData.EnumAction.SYNC_LATEST);
            EnumChatMessages.COMMAND_PB_DENY_GLOBAL.sendMessage(player);
            break;
        case ALLOW_GLOBAL:
            if (!this.validAction(player, true, true, true, false)) break;
            DataManager.getLatest().setDeniedGlobal(DataManager.latestPlantServer.meta, false);
            this.save();
            this.sync(player, CPSyncPlantsData.EnumAction.SYNC_LATEST);
            EnumChatMessages.COMMAND_PB_ALLOW_GLOBAL.sendMessage(player);
            break;
        case ADD_VALID:
            if (!this.validAction(player, true, true, false, false)) break;
            if (!DataManager.existMetaLatest())
                DataManager.createMetaLatest();
            DataManager.getLatest().addValidBiome(DataManager.latestPlantServer.meta, DataManager.latestPlantServer.biomeRegistryName);
            this.save();
            this.sync(player, CPSyncPlantsData.EnumAction.SYNC_LATEST);
            EnumChatMessages.COMMAND_PB_ADD_VALID.sendMessage(player);
            break;
        case REMOVE_VALID:
            if (!this.validAction(player, true, true, true, false)) break;
            DataManager.getLatest().removeValidBiome(DataManager.latestPlantServer.meta, DataManager.latestPlantServer.biomeRegistryName);
            this.save();
            this.sync(player, CPSyncPlantsData.EnumAction.SYNC_LATEST);
            EnumChatMessages.COMMAND_PB_REMOVE_VALID.sendMessage(player);
            break;
        case ALLOW_GROW_OVER_TIME:
            if (!this.validAction(player, true, true, true, false)) break;
            DataManager.getLatest().setCanGrowOverTime(DataManager.latestPlantServer.meta, true);
            this.save();
            this.sync(player, CPSyncPlantsData.EnumAction.SYNC_LATEST);
            EnumChatMessages.COMMAND_PB_ALLOW_GROW_OVER_TIME.sendMessage(player);
            break;   
        case DENY_GROW_OVER_TIME:
            if (!this.validAction(player, true, true, true, false)) break;
            DataManager.getLatest().setCanGrowOverTime(DataManager.latestPlantServer.meta, false);
            this.save();
            this.sync(player, CPSyncPlantsData.EnumAction.SYNC_LATEST);
            EnumChatMessages.COMMAND_PB_DENY_GROW_OVER_TIME.sendMessage(player);
            break;   
        case ALLOW_GROW_WITH_BONEMEAL:
            if (!this.validAction(player, true, true, true, false)) break;
            DataManager.getLatest().setCanGrowWithBonemeal(DataManager.latestPlantServer.meta, true);
            this.save();
            this.sync(player, CPSyncPlantsData.EnumAction.SYNC_LATEST);
            EnumChatMessages.COMMAND_PB_ALLOW_GROW_WITH_BONEMEAL.sendMessage(player);
            break;   
        case DENY_GROW_WITH_BONEMEAL:
            if (!this.validAction(player, true, true, true, false)) break;
            DataManager.getLatest().setCanGrowWithBonemeal(DataManager.latestPlantServer.meta, false);
            this.save();
            this.sync(player, CPSyncPlantsData.EnumAction.SYNC_LATEST);
            EnumChatMessages.COMMAND_PB_DENY_GROW_WITH_BONEMEAL.sendMessage(player);
            break;            
        case SET_MAIN:
            if (!this.validAction(player, true, true, true, false)) break;
            DataManager.getLatest().setMainMeta(DataManager.latestPlantServer.meta);
            this.save();
            this.sync(player, CPSyncPlantsData.EnumAction.SYNC_LATEST);
            EnumChatMessages.COMMAND_PB_SET_MAIN.sendMessage(player);
            break;
        case RESET_MAIN:
            if (!this.validAction(player, true, true, false, false)) break;
            DataManager.getLatest().resetMainMeta();
            this.save();
            this.sync(player, CPSyncPlantsData.EnumAction.SYNC_LATEST);
            EnumChatMessages.COMMAND_PB_RESET_MAIN.sendMessage(player);
            break;    
        case BIND_ITEM:
            if (!this.validAction(player, true, true, true, false)) break;
            if (CommonReference.isMainHandEmpty(player)) {
                EnumChatMessages.COMMAND_PB_ERR_NO_HELD_ITEM.sendMessage(player);
                break;
            }
            ItemStack itemStack = player.getHeldItemMainhand();
            DataManager.getLatest().setBoundItem(DataManager.latestPlantServer.meta, itemStack.getItem().getRegistryName(), itemStack.getMetadata());
            this.save();
            this.sync(player, CPSyncPlantsData.EnumAction.SYNC_LATEST);
            EnumChatMessages.COMMAND_PB_BIND_ITEM.sendMessage(player);
            break;
        case UNBIND_ITEM:
            if (!this.validAction(player, true, true, true, false)) break;
            DataManager.getLatest().resetBoundItem(DataManager.latestPlantServer.meta);
            this.save();
            this.sync(player, CPSyncPlantsData.EnumAction.SYNC_LATEST);
            EnumChatMessages.COMMAND_PB_UNBIND_ITEM.sendMessage(player);
            break;
        case CLEAR_DENIED:
            if (!this.validAction(player, true, false, true, false)) break;
            DataManager.getLatest().clearDeniedBiomes(DataManager.latestPlantServer.meta);
            ConfigLoader.save();
            this.sync(player, CPSyncPlantsData.EnumAction.SYNC_LATEST);
            EnumChatMessages.COMMAND_PB_CLEAR_DENIED.sendMessage(player);                                                   
            break;
        case CLEAR_VALID:
            if (!this.validAction(player, true, false, true, false)) break;
            DataManager.getLatest().clearValidBiomes(DataManager.latestPlantServer.meta);
            ConfigLoader.save();
            this.sync(player, CPSyncPlantsData.EnumAction.SYNC_LATEST);
            EnumChatMessages.COMMAND_PB_CLEAR_VALID.sendMessage(player);                                                   
            break;
        case CLEAR_LATEST:
            if (!this.validAction(player, true, false, true, false)) break;
            DataManager.removeMetaLatest();//TODO This cause settings mess up (map data disappear), need to investigate why.
            ConfigLoader.save();
            DataManager.initServerData();//Fast fix. 
            this.sync(player, CPSyncPlantsData.EnumAction.SYNC_ALL);//REMOVE_LATEST should be used instead, but it will break overlay render.
            EnumChatMessages.COMMAND_PB_CLEAR_LATEST.sendMessage(player);                                                   
            break;
        case CLEAR_ALL:
            if (!this.validAction(player, true, false, false, true)) break; 
            DataManager.clearDataServer();
            ConfigLoader.save();
            this.sync(player, CPSyncPlantsData.EnumAction.REMOVE_ALL);
            EnumChatMessages.COMMAND_PB_CLEAR_ALL.sendMessage(player);
            break;
        case SAVE:
            if (!this.validAction(player, true, false, false, false)) break;
            ConfigLoader.save();
            EnumChatMessages.COMMAND_PB_SAVE.sendMessage(player);
            break;
        case RELOAD:
            if (!this.validAction(player, true, false, false, false)) break;
            DataManager.initServerData();
            this.sync(player, CPSyncPlantsData.EnumAction.SYNC_ALL);
            EnumChatMessages.COMMAND_PB_RELOAD.sendMessage(player);
            break;
        case BACKUP:
            if (!this.validAction(player, true, false, false, false)) break;
            ConfigLoader.backup();
            EnumChatMessages.COMMAND_PB_BACKUP.sendMessage(player);
            break;
        }
    }

    private boolean validAction(EntityPlayer player, boolean checkMode, boolean checkLatest, boolean checkDataLatest, boolean checkDataAll) {
        if (!EnumConfigSettings.EXTERNAL_CONFIG.isEnabled()) {
            EnumChatMessages.COMMAND_PB_ERR_EXTERNAL_CONFIG_DISABLED.sendMessage(player);
            return false;
        } else if (checkMode && !DataManager.isConfigModeEnabled()) {
            EnumChatMessages.COMMAND_PB_ERR_NEED_ENABLE_CONFIG.sendMessage(player);
            return false;
        } else if (checkLatest && DataManager.latestPlantServer == null) {   
            EnumChatMessages.COMMAND_PB_ERR_NO_LATEST.sendMessage(player);
            return false;
        } else if (checkDataLatest && !DataManager.existMetaLatest()) {
            EnumChatMessages.COMMAND_PB_ERR_NO_DATA_FOR_LATEST.sendMessage(player);
            return false;
        } else if (checkDataAll && DataManager.getDataServer().isEmpty()) {
            EnumChatMessages.COMMAND_PB_ERR_NO_DATA.sendMessage(player);
            return false;
        }
        return true;
    }

    private void save() {
        if (EnumConfigSettings.AUTOSAVE.isEnabled())
            ConfigLoader.save();
    }

    private void sync(EntityPlayer player, CPSyncPlantsData.EnumAction enumAction) {
        if (EnumConfigSettings.SETTINGS_TOOLTIPS.isEnabled()) {
            for (EntityPlayerMP playerMP : CommonReference.getPlayersListServer())
                NetworkHandler.sendTo(new CPSyncPlantsData(enumAction), playerMP);
        } else if (EnumConfigSettings.SETTINGS_OVERLAY.isEnabled()) {
            NetworkHandler.sendTo(new CPSyncPlantsData(enumAction), (EntityPlayerMP) player);
        }
    }
}
