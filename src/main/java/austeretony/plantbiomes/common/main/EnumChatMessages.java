package austeretony.plantbiomes.common.main;

import austeretony.plantbiomes.common.commands.EnumCommandPBArgs;
import austeretony.plantbiomes.common.reference.CommonReference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;

public enum EnumChatMessages {

    UPDATE_MESSAGE,
    COMMAND_HELP,
    SETTINGS_ENABLED,
    SETTINGS_DISABLED,
    STATUS,
    EXTERNAL_CONFIG_DISABLED,
    CONFIGURATION_ENABLED,
    CONFIGURATION_DISABLED,
    OVERLAY_DENIED,
    OVERLAY_ENABLED,
    OVERLAY_DISABLED,
    CURRENT_BIOME,
    SETTINGS_LIST,
    UNSUPPORTED_PLANT,
    LATEST_PLANT,
    NEED_ENABLE_CONFIGURATION,
    NO_LATEST_PLANT,
    DENIED,
    ALLOWED,
    DENIED_GLOBAL,
    ALLOWED_GLOBAL,
    VALID_BIOME_ADDED,
    VALID_BIOME_REMOVED,
    MAIN_META_SET,
    MAIN_META_RESET,
    PLANT_ABSENT,
    LATEST_PLANT_CLEARED,    
    DENIED_BIOMES_CLEARED,
    VALID_BIOMES_CLEARED,
    NO_DATA_FOR_PLANT,
    NO_DATA,
    PLANTS_LIST_CLEARED,
    SETTINGS_SAVED,
    SETTINGS_RELOADED,
    BACKUP_CREATED;

    public static final ITextComponent PREFIX;

    static {

        PREFIX = new TextComponentString("[Plant Biomes] ");
        PREFIX.getStyle().setColor(TextFormatting.AQUA);                   
    }

    private static ITextComponent prefix() {
        return PREFIX.createCopy();
    }

    public void sendMessage(EntityPlayer player, String... args) {
        ITextComponent msg1, msg2, msg3, msg4, plantKey, plantName, biomeName;
        switch (this) {
        case UPDATE_MESSAGE:
            msg1 = new TextComponentTranslation("pb.update.newVersion");
            msg2 = new TextComponentString(" [" + PlantBiomesMain.VERSION + "/" + args[0] + "]");        
            CommonReference.sendMessage(player, prefix().appendSibling(msg1).appendSibling(msg2));
            msg1 = new TextComponentTranslation("pb.update.projectPage");
            msg2 = new TextComponentString(": ");
            msg3 = new TextComponentString(PlantBiomesMain.PROJECT_LOCATION);   
            msg1.getStyle().setColor(TextFormatting.AQUA);      
            msg3.getStyle().setColor(TextFormatting.WHITE);                             
            msg3.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, PlantBiomesMain.PROJECT_URL));             
            CommonReference.sendMessage(player, msg1.appendSibling(msg2).appendSibling(msg3));    
            break;
        case COMMAND_HELP:
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.help.title")));
            for (EnumCommandPBArgs arg : EnumCommandPBArgs.values()) {
                if (arg != EnumCommandPBArgs.HELP) {
                    msg1 = new TextComponentString("/pb " + arg);
                    msg2 = new TextComponentString(" - ");
                    msg1.getStyle().setColor(TextFormatting.GREEN);  
                    msg2.getStyle().setColor(TextFormatting.WHITE); 
                    CommonReference.sendMessage(player, msg1.appendSibling(msg2.appendSibling(new TextComponentTranslation("pb.command.help." + arg))));
                }
            }
            break;
        case SETTINGS_ENABLED:
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.enable")));
            break;
        case SETTINGS_DISABLED:
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.disable")));
            break;
        case STATUS:
            msg1 = PBManager.isSettingsEnabled() ? new TextComponentTranslation("pb.status.enabled") : new TextComponentTranslation("pb.status.disabled");
            msg1.getStyle().setColor(PBManager.isSettingsEnabled() ? TextFormatting.GREEN : TextFormatting.RED);        
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.status").appendSibling(new TextComponentString(": ")).appendSibling(msg1)));
            break;
        case EXTERNAL_CONFIG_DISABLED:
            msg1 = new TextComponentTranslation("pb.config.invalidState");
            msg1.getStyle().setColor(TextFormatting.RED);        
            CommonReference.sendMessage(player, prefix().appendSibling(msg1)); 
            break;
        case CONFIGURATION_ENABLED:
            if (PBManager.isAutosaveEnabled())
                CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.enable.conf")).appendSibling(new TextComponentString(" ")).appendSibling(new TextComponentTranslation("pb.autosave.enabled")));
            else
                CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.enable.conf")).appendSibling(new TextComponentString(" ")).appendSibling(new TextComponentTranslation("pb.autosave.disabled")));  
            break;
        case CONFIGURATION_DISABLED:
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.disable.conf")));
            break;
        case OVERLAY_DENIED:
            msg1 = new TextComponentTranslation("pb.command.err.overlay");                        
            msg1.getStyle().setColor(TextFormatting.RED);                       
            CommonReference.sendMessage(player, prefix().appendSibling(msg1));             
            break;
        case OVERLAY_ENABLED:
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.enable.overlay")));
            break;
        case OVERLAY_DISABLED:
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.disable.overlay")));
            break;
        case CURRENT_BIOME:
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.biome", args[0])));
            break;
        case SETTINGS_LIST:
            STATUS.sendMessage(player, args);
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.settings")));
            if (PBManager.getDataServer().isEmpty())
                CommonReference.sendMessage(player, new TextComponentTranslation("pb.command.settings.empty"));
            for (PlantData plantData : PBManager.getDataServer().values()) {
                if (plantData.hasMainMeta()) {
                    msg1 = new TextComponentTranslation("pb.mainMetaSet", plantData.getMainMeta());
                    msg1.getStyle().setColor(TextFormatting.YELLOW); 
                    CommonReference.sendMessage(player, msg1);
                }
                for (MetaPlant metaPlant : plantData.getData().values()) {      
                    if (plantData.hasMainMeta() && plantData.getMainMeta() != metaPlant.meta) continue;
                    msg1 = new TextComponentString(PBManager.createDisplayKey(plantData.registryName, metaPlant.meta));
                    msg2 = new TextComponentString(" / ");
                    msg3 = new TextComponentTranslation((metaPlant.unlocalizedName).trim());
                    CommonReference.sendMessage(player, msg1.appendSibling(msg2).appendSibling(msg3));
                    if (metaPlant.isValidBiomesExist()) {
                        for (ResourceLocation l : metaPlant.getValidBiomes()) {
                            msg1 = new TextComponentString(" - " + l.toString());
                            msg1.getStyle().setColor(TextFormatting.DARK_GREEN); 
                            CommonReference.sendMessage(player, msg1);
                        }
                    } else {
                        if (!metaPlant.isDeniedGlobal()) {
                            for (ResourceLocation l : metaPlant.getDeniedBiomes()) {
                                msg1 = new TextComponentString(" - " + l.toString());
                                msg1.getStyle().setColor(TextFormatting.RED); 
                                CommonReference.sendMessage(player, msg1);
                            }
                        } else {
                            msg1 = new TextComponentTranslation("pb.status.deniedGlobal");
                            msg1.getStyle().setColor(TextFormatting.RED); 
                            CommonReference.sendMessage(player, msg1);
                        }
                    }
                }
                CommonReference.sendMessage(player, new TextComponentString(""));
            }
            break;
        case UNSUPPORTED_PLANT:
            msg1 = new TextComponentTranslation("pb.plantUnsupported");
            msg1.getStyle().setColor(TextFormatting.RED);        
            CommonReference.sendMessage(player, prefix().appendSibling(msg1));
            break;
        case LATEST_PLANT:
            msg1 = new TextComponentTranslation("pb.command.latest");
            msg2 = new TextComponentString(": ");
            msg3 = new TextComponentString(" / ");
            msg4 = new TextComponentString(", ");
            msg4.getStyle().setColor(TextFormatting.AQUA);
            plantKey = new TextComponentString(PBManager.createDisplayKey(PBManager.latestPlant.registryName, PBManager.latestPlant.meta));                                
            plantKey.getStyle().setColor(TextFormatting.WHITE);  
            plantName = new TextComponentTranslation((PBManager.latestPlant.unlocalizedName).trim());                           
            plantName.getStyle().setColor(TextFormatting.WHITE);  
            biomeName = new TextComponentString(PBManager.latestPlant.biomeRegistryName.toString());
            TextFormatting biomeColor = TextFormatting.GREEN;
            if (PBManager.existLatest()) {
                if (PBManager.getLatest().hasMainMeta()) {
                    if (PBManager.getLatest().getMainMetaPlant().isValidBiomesExist())
                        biomeColor = PBManager.getLatest().getMainMetaPlant().isValidBiome(PBManager.latestPlant.biomeRegistryName) ? TextFormatting.DARK_GREEN : TextFormatting.DARK_RED;
                    else if (PBManager.getLatest().getMainMetaPlant().isDeniedBiome(PBManager.latestPlant.biomeRegistryName) || PBManager.getLatest().getMainMetaPlant().isDeniedGlobal())
                        biomeColor = TextFormatting.RED;
                } else {
                    if (PBManager.existMetaLatest()) {
                        if (PBManager.getMetaLatest().isValidBiomesExist())
                            biomeColor = PBManager.getMetaLatest().isValidBiome(PBManager.latestPlant.biomeRegistryName) ? TextFormatting.DARK_GREEN : TextFormatting.DARK_RED;
                        else if (PBManager.getMetaLatest().isDeniedBiome(PBManager.latestPlant.biomeRegistryName) || PBManager.getMetaLatest().isDeniedGlobal())
                            biomeColor = TextFormatting.RED;
                    }
                }
            }      
            biomeName.getStyle().setColor(biomeColor);  
            CommonReference.sendMessage(player, prefix().appendSibling(msg1).appendSibling(msg2).appendSibling(plantKey).appendSibling(msg3).appendSibling(plantName).appendSibling(msg4).appendSibling(biomeName));
            break;
        case NEED_ENABLE_CONFIGURATION:
            msg1 = new TextComponentTranslation("pb.command.err.debugMode");                        
            msg1.getStyle().setColor(TextFormatting.RED);                       
            CommonReference.sendMessage(player, prefix().appendSibling(msg1));            
            break;
        case NO_LATEST_PLANT:
            msg1 = new TextComponentTranslation("pb.command.latest.notExist");                   
            msg1.getStyle().setColor(TextFormatting.RED);                       
            CommonReference.sendMessage(player, prefix().appendSibling(msg1)); 
            break;
        case DENIED:
            msg1 = new TextComponentTranslation("pb.command.deny");
            msg2 = new TextComponentString(": ");
            msg3 = new TextComponentString(" -> "); 
            msg4 = new TextComponentString(" / ");
            msg3.getStyle().setColor(TextFormatting.AQUA);                   
            plantKey = new TextComponentString(PBManager.createDisplayKey(PBManager.latestPlant.registryName, PBManager.latestPlant.meta));
            plantKey.getStyle().setColor(TextFormatting.WHITE); 
            plantName = new TextComponentTranslation((PBManager.latestPlant.unlocalizedName).trim());                           
            plantName.getStyle().setColor(TextFormatting.WHITE);  
            biomeName = new TextComponentString(PBManager.latestPlant.biomeRegistryName.toString());
            biomeName.getStyle().setColor(TextFormatting.WHITE);  
            CommonReference.sendMessage(player, prefix().appendSibling(msg1).appendSibling(msg2).appendSibling(biomeName).appendSibling(msg3).appendSibling(plantKey).appendSibling(msg4).appendSibling(plantName));
            break;
        case ALLOWED:
            msg1 = new TextComponentTranslation("pb.command.allow");
            msg2 = new TextComponentString(": ");
            msg3 = new TextComponentString(" -> "); 
            msg4 = new TextComponentString(" / ");
            msg3.getStyle().setColor(TextFormatting.AQUA);                   
            plantKey = new TextComponentString(PBManager.createDisplayKey(PBManager.latestPlant.registryName, PBManager.latestPlant.meta));
            plantKey.getStyle().setColor(TextFormatting.WHITE); 
            plantName = new TextComponentTranslation((PBManager.latestPlant.unlocalizedName).trim());                           
            plantName.getStyle().setColor(TextFormatting.WHITE);  
            biomeName = new TextComponentString(PBManager.latestPlant.biomeRegistryName.toString());
            biomeName.getStyle().setColor(TextFormatting.WHITE);  
            CommonReference.sendMessage(player, prefix().appendSibling(msg1).appendSibling(msg2).appendSibling(biomeName).appendSibling(msg3).appendSibling(plantKey).appendSibling(msg4).appendSibling(plantName));            
            break;   
        case DENIED_GLOBAL:
            msg1 = new TextComponentTranslation("pb.command.deny.global");
            msg2 = new TextComponentString(": ");
            msg3 = new TextComponentString(" / ");
            plantKey = new TextComponentString(PBManager.createDisplayKey(PBManager.latestPlant.registryName, PBManager.latestPlant.meta));
            plantKey.getStyle().setColor(TextFormatting.WHITE); 
            plantName = new TextComponentTranslation((PBManager.latestPlant.unlocalizedName).trim());                           
            plantName.getStyle().setColor(TextFormatting.WHITE);   
            CommonReference.sendMessage(player, prefix().appendSibling(msg1).appendSibling(msg2).appendSibling(plantKey).appendSibling(msg3).appendSibling(plantName));
            break;
        case ALLOWED_GLOBAL:
            msg1 = new TextComponentTranslation("pb.command.allow.global");
            msg2 = new TextComponentString(": ");
            msg3 = new TextComponentString(" / ");
            plantKey = new TextComponentString(PBManager.createDisplayKey(PBManager.latestPlant.registryName, PBManager.latestPlant.meta));
            plantKey.getStyle().setColor(TextFormatting.WHITE); 
            plantName = new TextComponentTranslation((PBManager.latestPlant.unlocalizedName).trim());                           
            plantName.getStyle().setColor(TextFormatting.WHITE);   
            CommonReference.sendMessage(player, prefix().appendSibling(msg1).appendSibling(msg2).appendSibling(plantKey).appendSibling(msg3).appendSibling(plantName));
            break;
        case VALID_BIOME_ADDED:
            msg1 = new TextComponentTranslation("pb.command.addValid");
            msg2 = new TextComponentString(": ");
            msg3 = new TextComponentString(" -> "); 
            msg4 = new TextComponentString(" / ");
            msg3.getStyle().setColor(TextFormatting.AQUA);                   
            plantKey = new TextComponentString(PBManager.createDisplayKey(PBManager.latestPlant.registryName, PBManager.latestPlant.meta));
            plantKey.getStyle().setColor(TextFormatting.WHITE); 
            plantName = new TextComponentTranslation((PBManager.latestPlant.unlocalizedName).trim());                           
            plantName.getStyle().setColor(TextFormatting.WHITE);  
            biomeName = new TextComponentString(PBManager.latestPlant.biomeRegistryName.toString());
            biomeName.getStyle().setColor(TextFormatting.WHITE);  
            CommonReference.sendMessage(player, prefix().appendSibling(msg1).appendSibling(msg2).appendSibling(biomeName).appendSibling(msg3).appendSibling(plantKey).appendSibling(msg4).appendSibling(plantName));
            break;
        case VALID_BIOME_REMOVED:
            msg1 = new TextComponentTranslation("pb.command.removeValid");
            msg2 = new TextComponentString(": ");
            msg3 = new TextComponentString(" -> "); 
            msg4 = new TextComponentString(" / ");
            msg3.getStyle().setColor(TextFormatting.AQUA);                   
            plantKey = new TextComponentString(PBManager.createDisplayKey(PBManager.latestPlant.registryName, PBManager.latestPlant.meta));
            plantKey.getStyle().setColor(TextFormatting.WHITE); 
            plantName = new TextComponentTranslation((PBManager.latestPlant.unlocalizedName).trim());                           
            plantName.getStyle().setColor(TextFormatting.WHITE);  
            biomeName = new TextComponentString(PBManager.latestPlant.biomeRegistryName.toString());
            biomeName.getStyle().setColor(TextFormatting.WHITE);  
            CommonReference.sendMessage(player, prefix().appendSibling(msg1).appendSibling(msg2).appendSibling(biomeName).appendSibling(msg3).appendSibling(plantKey).appendSibling(msg4).appendSibling(plantName));           
            break;
        case MAIN_META_SET:
            msg1 = new TextComponentTranslation("pb.command.set-main");
            msg2 = new TextComponentString(": ");
            msg3 = new TextComponentString(" / ");
            msg4 = new TextComponentString(" ");
            msg4.getStyle().setColor(TextFormatting.AQUA);
            plantKey = new TextComponentString(PBManager.createDisplayKey(PBManager.latestPlant.registryName, PBManager.latestPlant.meta));                                
            plantKey.getStyle().setColor(TextFormatting.WHITE);  
            plantName = new TextComponentTranslation((PBManager.latestPlant.unlocalizedName).trim());                           
            plantName.getStyle().setColor(TextFormatting.WHITE);  
            CommonReference.sendMessage(player, prefix().appendSibling(msg1).appendSibling(msg2).appendSibling(plantKey).appendSibling(msg3).appendSibling(plantName).appendSibling(msg4).appendSibling(new TextComponentTranslation("pb.command.set-main.end")));            
            break;
        case MAIN_META_RESET:
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.reset-main")));
            break;
        case PLANT_ABSENT:
            msg1 = new TextComponentTranslation("pb.command.allow.absent");                     
            msg1.getStyle().setColor(TextFormatting.RED);                       
            CommonReference.sendMessage(player, prefix().appendSibling(msg1));
            break;
        case LATEST_PLANT_CLEARED:
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.clear.latest")));    
            break;
        case DENIED_BIOMES_CLEARED:
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.clear.denied")));    
            break;
        case VALID_BIOMES_CLEARED:
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.clear.valid")));    
            break;
        case NO_DATA_FOR_PLANT:
            msg1 = new TextComponentTranslation("pb.command.nodataplant");                    
            msg1.getStyle().setColor(TextFormatting.RED);       
            CommonReference.sendMessage(player, prefix().appendSibling(msg1)); 
            break;
        case NO_DATA:
            msg1 = new TextComponentTranslation("pb.command.noData");                    
            msg1.getStyle().setColor(TextFormatting.RED);    
            CommonReference.sendMessage(player, prefix().appendSibling(msg1));  
            break;
        case PLANTS_LIST_CLEARED:
            CommonReference.sendMessage(player, prefix().createCopy().appendSibling(new TextComponentTranslation("pb.command.clear.all")));    
            break;
        case SETTINGS_SAVED:
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.save")));    
            break;
        case SETTINGS_RELOADED:
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.reload")));    
            break;
        case BACKUP_CREATED:
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.backup")));    
            break;
        }
    }
}
