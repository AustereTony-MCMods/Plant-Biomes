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
        if (args.length != 1 || !EnumCommandPBArgs.isValid(args[0]))        
            throw new WrongUsageException(this.getUsage(sender));   
        EnumCommandPBArgs arg = EnumCommandPBArgs.get(args[0]);
        EntityPlayer player = getCommandSenderAsPlayer(sender);  
        switch(arg) {
        case HELP:
            EnumChatMessages.COMMAND_HELP.sendMessage(player);
            break;
        case ENABLE:
            DataLoader.setSettingsDisabled(false);
            EnumChatMessages.SETTINGS_ENABLED.sendMessage(player);
            break;
        case DISABLE:
            DataLoader.setSettingsDisabled(true);
            EnumChatMessages.SETTINGS_DISABLED.sendMessage(player);
            break;  
        case STATUS:
            EnumChatMessages.STATUS.sendMessage(player);
            break;  
        case ENABLE_CONFIG:
            DataLoader.setConfigModeEnabled(true);
            EnumChatMessages.CONFIGURATION_ENABLED.sendMessage(player);
            break;
        case DISABLE_CONFIG:
            DataLoader.setConfigModeEnabled(false);
            DataLoader.lpRegistryName = null;
            EnumChatMessages.CONFIGURATION_DISABLED.sendMessage(player);
            break;  
        case SETTINGS:
            EnumChatMessages.SETTINGS_LIST.sendMessage(player);
            break;
        case BIOME:
            EnumChatMessages.CURRENT_BIOME.sendMessage(player, DataLoader.getBiomeRegistryName(player.world, player.getPosition()).toString());
            break;
        case LATEST:
            if (!DataLoader.isConfigModeEnabled()) {
                EnumChatMessages.NEED_ENABLE_CONFIGURATION.sendMessage(player);
                return;
            } else if (DataLoader.lpRegistryName == null) {   
                EnumChatMessages.NO_LATEST_PLANT.sendMessage(player);
                return;
            }
            EnumChatMessages.LATEST_PLANT.sendMessage(player);
            break;
        case DENY:
            if (!DataLoader.isConfigModeEnabled()) {
                EnumChatMessages.NEED_ENABLE_CONFIGURATION.sendMessage(player);
                return;
            } else if (DataLoader.lpRegistryName == null) {   
                EnumChatMessages.NO_LATEST_PLANT.sendMessage(player);
                return;
            }
            if (!DataLoader.exist(DataLoader.lpRegistryName, DataLoader.lpMeta)) {
                DataLoader.create(DataLoader.lpRegistryName, DataLoader.lpMeta, DataLoader.lpUnlocalizedName);
            }
            DataLoader.get(DataLoader.lpRegistryName, DataLoader.lpMeta).denyBiome(DataLoader.biomeRegistryName);
            EnumChatMessages.DENIED.sendMessage(player);
            break;
        case DENY_GLOBAL:
            if (!DataLoader.isConfigModeEnabled()) {
                EnumChatMessages.NEED_ENABLE_CONFIGURATION.sendMessage(player);
                return;
            } else if (DataLoader.lpRegistryName == null) {   
                EnumChatMessages.NO_LATEST_PLANT.sendMessage(player);
                return;
            }
            if (!DataLoader.exist(DataLoader.lpRegistryName, DataLoader.lpMeta)) {
                DataLoader.create(DataLoader.lpRegistryName, DataLoader.lpMeta, DataLoader.lpUnlocalizedName);
            }
            DataLoader.get(DataLoader.lpRegistryName, DataLoader.lpMeta).denyGlobal();
            EnumChatMessages.DENIED_GLOBAL.sendMessage(player);
            break;
        case ALLOW:
            if (!DataLoader.isConfigModeEnabled()) {
                EnumChatMessages.NEED_ENABLE_CONFIGURATION.sendMessage(player);
                return;
            } else if (DataLoader.lpRegistryName == null) {   
                EnumChatMessages.NO_LATEST_PLANT.sendMessage(player);
                return;
            } else if (!DataLoader.exist(DataLoader.lpRegistryName, DataLoader.lpMeta)) {
                EnumChatMessages.NO_DATA_FOR_PLANT.sendMessage(player);
                return;
            }
            DataLoader.get(DataLoader.lpRegistryName, DataLoader.lpMeta).allowBiome(DataLoader.biomeRegistryName);
            EnumChatMessages.ALLOWED.sendMessage(player);
            break;
        case ALLOW_GLOBAL:
            if (!DataLoader.isConfigModeEnabled()) {
                EnumChatMessages.NEED_ENABLE_CONFIGURATION.sendMessage(player);
                return;
            } else if (DataLoader.lpRegistryName == null) {   
                EnumChatMessages.NO_LATEST_PLANT.sendMessage(player);
                return;
            } else if (!DataLoader.exist(DataLoader.lpRegistryName, DataLoader.lpMeta)) {
                EnumChatMessages.NO_DATA_FOR_PLANT.sendMessage(player);
                return;
            }
            DataLoader.get(DataLoader.lpRegistryName, DataLoader.lpMeta).allowGlobal();
            EnumChatMessages.ALLOWED_GLOBAL.sendMessage(player);
            break;
        case CLEAR_LATEST:
            if (!DataLoader.isConfigModeEnabled()) {
                EnumChatMessages.NEED_ENABLE_CONFIGURATION.sendMessage(player);
                return;
            } else if (!DataLoader.exist(DataLoader.lpRegistryName, DataLoader.lpMeta)) {
                EnumChatMessages.NO_DATA_FOR_PLANT.sendMessage(player);
                return;
            }
            DataLoader.remove(DataLoader.lpRegistryName, DataLoader.lpMeta);
            DataLoader.saveSettings();
            EnumChatMessages.PLANT_BIOMES_CLEARED.sendMessage(player);                                                   
            break;
        case CLEAR_ALL:
            if (DataLoader.map().isEmpty()) {
                EnumChatMessages.NO_DATA.sendMessage(player);
                return;
            }
            DataLoader.map().clear();
            DataLoader.saveSettings();
            EnumChatMessages.PLANTS_LIST_CLEARED.sendMessage(player);
            break;
        case SAVE:
            if (!DataLoader.isConfigModeEnabled()) {
                EnumChatMessages.NEED_ENABLE_CONFIGURATION.sendMessage(player);
                return;
            }
            DataLoader.saveSettings();
            EnumChatMessages.SETTINGS_SAVED.sendMessage(player);
            break;
        case BACKUP:
            if (!DataLoader.isConfigModeEnabled()) {
                EnumChatMessages.NEED_ENABLE_CONFIGURATION.sendMessage(player);
                return;
            }
            DataLoader.backupSettings();
            EnumChatMessages.BACKUP_CREATED.sendMessage(player);
            break;
        }
    }
}
