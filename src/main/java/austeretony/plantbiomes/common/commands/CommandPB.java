package austeretony.plantbiomes.common.commands;

import austeretony.plantbiomes.common.main.DataLoader;
import austeretony.plantbiomes.common.main.EnumChatMessages;
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
            DataLoader.setSettingsEnabled(true);
            if (DataLoader.isAutosaveEnabled())
                DataLoader.saveSettings();
            EnumChatMessages.SETTINGS_ENABLED.sendMessage(player);
            break;
        case DISABLE:
            DataLoader.setSettingsEnabled(false);
            if (DataLoader.isAutosaveEnabled())
                DataLoader.saveSettings();
            EnumChatMessages.SETTINGS_DISABLED.sendMessage(player);
            break;  
        case STATUS:
            EnumChatMessages.STATUS.sendMessage(player);
            break;  
        case ENABLE_CONFIG_MODE:
            DataLoader.setConfigModeEnabled(true);
            EnumChatMessages.CONFIGURATION_ENABLED.sendMessage(player);
            break;
        case DISABLE_CONFIG_MODE:
            DataLoader.setConfigModeEnabled(false);
            DataLoader.latestPlant = null;
            EnumChatMessages.CONFIGURATION_DISABLED.sendMessage(player);
            break;  
        case SETTINGS:
            EnumChatMessages.SETTINGS_LIST.sendMessage(player);
            break;
        case BIOME:
            EnumChatMessages.CURRENT_BIOME.sendMessage(player, DataLoader.getBiomeRegistryName(player.world, player.getPosition()).toString());
            break;
        case LATEST:
            if (!this.validAction(player, true, true, false, false))
                return;
            EnumChatMessages.LATEST_PLANT.sendMessage(player);
            break;
        case DENY:
            if (!this.validAction(player, true, true, false, false))
                return;
            if (!DataLoader.existMetaLatest())
                DataLoader.createMetaLatest();
            DataLoader.getMetaLatest().denyBiome(DataLoader.latestPlant.biomeRegistryName);
            if (DataLoader.isAutosaveEnabled())
                DataLoader.saveSettings();
            EnumChatMessages.DENIED.sendMessage(player);
            break;
        case ALLOW:
            if (!this.validAction(player, true, true, true, false))
                return;
            DataLoader.getMetaLatest().allowBiome(DataLoader.latestPlant.biomeRegistryName);
            if (DataLoader.isAutosaveEnabled())
                DataLoader.saveSettings();
            EnumChatMessages.ALLOWED.sendMessage(player);
            break;
        case DENY_GLOBAL:
            if (!this.validAction(player, true, true, false, false))
                return;
            if (!DataLoader.existMetaLatest())
                DataLoader.createMetaLatest();
            DataLoader.getMetaLatest().denyGlobal();
            if (DataLoader.isAutosaveEnabled())
                DataLoader.saveSettings();
            EnumChatMessages.DENIED_GLOBAL.sendMessage(player);
            break;
        case ALLOW_GLOBAL:
            if (!this.validAction(player, true, true, true, false))
                return;
            DataLoader.getMetaLatest().allowGlobal();
            if (DataLoader.isAutosaveEnabled())
                DataLoader.saveSettings();
            EnumChatMessages.ALLOWED_GLOBAL.sendMessage(player);
            break;
        case ADD_VALID:
            if (!this.validAction(player, true, true, false, false))
                return;
            if (!DataLoader.existMetaLatest())
                DataLoader.createMetaLatest();
            DataLoader.getMetaLatest().addValidBiome(DataLoader.latestPlant.biomeRegistryName);
            if (DataLoader.isAutosaveEnabled())
                DataLoader.saveSettings();
            EnumChatMessages.VALID_BIOME_ADDED.sendMessage(player);
            break;
        case REMOVE_VALID:
            if (!this.validAction(player, true, true, true, false))
                return;
            DataLoader.getMetaLatest().removeValidBiome(DataLoader.latestPlant.biomeRegistryName);
            if (DataLoader.isAutosaveEnabled())
                DataLoader.saveSettings();
            EnumChatMessages.VALID_BIOME_REMOVED.sendMessage(player);
            break;
        case SET_MAIN:
            if (!this.validAction(player, true, true, true, false))
                return;
            DataLoader.getLatest().setMainMeta(DataLoader.latestPlant.meta);
            if (DataLoader.isAutosaveEnabled())
                DataLoader.saveSettings();
            EnumChatMessages.MAIN_META_SET.sendMessage(player);
            break;
        case RESET_MAIN:
            if (!this.validAction(player, true, true, false, false))
                return;
            DataLoader.getLatest().resetMainMeta();
            if (DataLoader.isAutosaveEnabled())
                DataLoader.saveSettings();
            EnumChatMessages.MAIN_META_RESET.sendMessage(player);
            break;
        case CLEAR_DENIED:
            if (!this.validAction(player, true, false, true, false))
                return;
            DataLoader.getMetaLatest().clearDeniedBiomes();
            DataLoader.saveSettings();
            EnumChatMessages.DENIED_BIOMES_CLEARED.sendMessage(player);                                                   
            break;
        case CLEAR_VALID:
            if (!this.validAction(player, true, false, true, false))
                return;
            DataLoader.getMetaLatest().clearValidBiomes();
            DataLoader.saveSettings();
            EnumChatMessages.VALID_BIOMES_CLEARED.sendMessage(player);                                                   
            break;
        case CLEAR_LATEST:
            if (!this.validAction(player, true, false, true, false))
                return;
            DataLoader.removeMetaLatest();
            DataLoader.saveSettings();
            EnumChatMessages.PLANT_BIOMES_CLEARED.sendMessage(player);                                                   
            break;
        case CLEAR_ALL:
            if (!this.validAction(player, true, false, false, true))
                return;
            DataLoader.clear();
            DataLoader.saveSettings();
            EnumChatMessages.PLANTS_LIST_CLEARED.sendMessage(player);
            break;
        case SAVE:
            if (!this.validAction(player, true, false, false, false))
                return;
            DataLoader.saveSettings();
            EnumChatMessages.SETTINGS_SAVED.sendMessage(player);
            break;
        case BACKUP:
            if (!this.validAction(player, true, false, false, false))
                return;
            DataLoader.backupSettings();
            EnumChatMessages.BACKUP_CREATED.sendMessage(player);
            break;
        }
    }

    private boolean validAction(EntityPlayer player, boolean checkMode, boolean checkLatest, boolean checkDataLatest, boolean checkDataAll) {
        if (checkMode && !DataLoader.isConfigModeEnabled()) {
            EnumChatMessages.NEED_ENABLE_CONFIGURATION.sendMessage(player);
            return false;
        } else if (checkLatest && DataLoader.latestPlant == null) {   
            EnumChatMessages.NO_LATEST_PLANT.sendMessage(player);
            return false;
        } else if (checkDataLatest && !DataLoader.existMetaLatest()) {
            EnumChatMessages.NO_DATA_FOR_PLANT.sendMessage(player);
            return false;
        } else if (checkDataAll && DataLoader.map().isEmpty()) {
            EnumChatMessages.NO_DATA.sendMessage(player);
            return false;
        }
        return true;
    }
}
