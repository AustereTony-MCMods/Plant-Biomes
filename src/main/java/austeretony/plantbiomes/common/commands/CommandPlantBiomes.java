package austeretony.plantbiomes.common.commands;

import austeretony.plantbiomes.common.main.EnumPBChatMessages;
import austeretony.plantbiomes.common.main.MetaPlant;
import austeretony.plantbiomes.common.main.PBDataLoader;
import austeretony.plantbiomes.common.main.PlantData;
import austeretony.plantbiomes.common.origin.CommonReference;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

public class CommandPlantBiomes extends CommandBase {

    public static final String 
    NAME = "pb",
    USAGE = "/pb <list, enable, plant, deny, allow, clear-latest, clear-all, save, disable>";

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
        if (args.length != 1 || !(
                args[0].equals("list") || 
                args[0].equals("enable") || 
                args[0].equals("plant") || 
                args[0].equals("deny") || 
                args[0].equals("allow") || 
                args[0].equals("clear-latest") || 
                args[0].equals("clear-all") || 
                args[0].equals("save") ||
                args[0].equals("disable")))             
            throw new WrongUsageException(this.getUsage(sender));   
        EntityPlayer player = getCommandSenderAsPlayer(sender);  
        switch(args[0]) {
        case "enable":
            PBDataLoader.setConfigModeEnabled(true);
            EnumPBChatMessages.showMessage(player, EnumPBChatMessages.CONFIGURATION_ENABLED);
            break;
        case "disable":
            PBDataLoader.setConfigModeEnabled(false);
            PBDataLoader.lpRegistryName = null;
            EnumPBChatMessages.showMessage(player, EnumPBChatMessages.CONFIGURATION_DISABLED);
            break;  
        case "list":
            EnumPBChatMessages.showMessage(player, EnumPBChatMessages.CONTAINERS_LIST);
            if (PBDataLoader.map().isEmpty())
                EnumPBChatMessages.showMessage(player, EnumPBChatMessages.EMPTY);
            for (PlantData plantData : PBDataLoader.map().values()) {
                for (MetaPlant metaPlant : plantData.getData().values()) {
                    EnumPBChatMessages.showMessage(player, EnumPBChatMessages.PLANT, PBDataLoader.createDisplayKey(plantData.registryName, metaPlant.meta), metaPlant.unlocalizedName);
                    for (ResourceLocation l : metaPlant.getDeniedBiomes())
                        EnumPBChatMessages.showMessage(player, EnumPBChatMessages.DENIED_BIOME, l.toString());
                }
            }
            break;
        case "plant":
            if (!PBDataLoader.isConfigModeEnabled()) {
                EnumPBChatMessages.showMessage(player, EnumPBChatMessages.NEED_ENABLE_CONFIGURATION);   
                return;
            } else if (PBDataLoader.lpRegistryName == null) {                  
                EnumPBChatMessages.showMessage(player, EnumPBChatMessages.NO_LATEST_PLANT);                 
                return;
            }
            EnumPBChatMessages.showMessage(player, EnumPBChatMessages.LATEST_PLANT);
            break;
        case "deny":
            if (!PBDataLoader.isConfigModeEnabled()) {
                EnumPBChatMessages.showMessage(player, EnumPBChatMessages.NEED_ENABLE_CONFIGURATION);                   
                return;
            } else if (PBDataLoader.lpRegistryName == null) {                  
                EnumPBChatMessages.showMessage(player, EnumPBChatMessages.NO_LATEST_PLANT);                                 
                return;
            }
            if (!PBDataLoader.exist(PBDataLoader.lpRegistryName, PBDataLoader.lpMeta)) {
                PBDataLoader.create(PBDataLoader.lpRegistryName, PBDataLoader.lpMeta, PBDataLoader.lpUnlocalizedName);
            }
            PBDataLoader.get(PBDataLoader.lpRegistryName, PBDataLoader.lpMeta).denyBiome(PBDataLoader.biomeRegistryName);
            EnumPBChatMessages.showMessage(player, EnumPBChatMessages.DENIED);                                    
            break;
        case "allow":
            if (!PBDataLoader.isConfigModeEnabled()) {
                EnumPBChatMessages.showMessage(player, EnumPBChatMessages.NEED_ENABLE_CONFIGURATION);                                   
                return;
            } else if (PBDataLoader.lpRegistryName == null) {                  
                EnumPBChatMessages.showMessage(player, EnumPBChatMessages.NO_LATEST_PLANT);                                                 
                return;
            } else if (!PBDataLoader.exist(PBDataLoader.lpRegistryName, PBDataLoader.lpMeta)) {
                EnumPBChatMessages.showMessage(player, EnumPBChatMessages.NO_DATA_FOR_PLANT);    
                return;
            }
            PBDataLoader.get(PBDataLoader.lpRegistryName, PBDataLoader.lpMeta).allowBiome(PBDataLoader.biomeRegistryName);
            EnumPBChatMessages.showMessage(player, EnumPBChatMessages.ALLOWED);                                
            break;
        case "save":
            if (!PBDataLoader.isConfigModeEnabled()) {
                EnumPBChatMessages.showMessage(player, EnumPBChatMessages.NEED_ENABLE_CONFIGURATION);                                                   
                return;
            }
            PBDataLoader.saveData();
            EnumPBChatMessages.showMessage(player, EnumPBChatMessages.CHANGES_SAVED);                                                       
            break;
        case "clear-latest":
            if (!PBDataLoader.isConfigModeEnabled()) {
                EnumPBChatMessages.showMessage(player, EnumPBChatMessages.NEED_ENABLE_CONFIGURATION);                                                   
                return;
            } 
            if (!PBDataLoader.exist(PBDataLoader.lpRegistryName, PBDataLoader.lpMeta)) {
                EnumPBChatMessages.showMessage(player, EnumPBChatMessages.NO_DATA_FOR_PLANT);  
                return;
            }
            PBDataLoader.remove(PBDataLoader.lpRegistryName, PBDataLoader.lpMeta);
            PBDataLoader.saveData();
            EnumPBChatMessages.showMessage(player, EnumPBChatMessages.PLANT_BIOMES_CLEARED);                                                     
            break;
        case "clear-all":
            if (!PBDataLoader.isConfigModeEnabled()) {
                EnumPBChatMessages.showMessage(player, EnumPBChatMessages.NEED_ENABLE_CONFIGURATION);                                                   
                return;
            } else if (PBDataLoader.map().isEmpty()) {
                EnumPBChatMessages.showMessage(player, EnumPBChatMessages.NO_DATA);                                                   
                return;
            }
            PBDataLoader.map().clear();
            PBDataLoader.saveData();
            EnumPBChatMessages.showMessage(player, EnumPBChatMessages.PLANTS_LIST_CLEARED);                                                     
            break;
        }
    }
}
