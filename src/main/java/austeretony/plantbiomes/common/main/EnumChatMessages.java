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
    COMMAND_PB_HELP,
    COMMAND_PB_ENABLE,
    COMMAND_PB_DISABLE,
    COMMAND_PB_STATUS,
    COMMAND_PB_ERR_EXTERNAL_CONFIG_DISABLED,
    COMMAND_PB_ENABLE_CONFIG,
    COMMAND_PB_DISABLE_CONFIG,
    COMMAND_PB_ERR_OVERLAY_DENIED,
    COMMAND_PB_ENABLE_OVERLAY,
    COMMAND_PB_DISABLE_OVERLAY,
    COMMAND_PB_BIOME,
    COMMAND_PB_SETTINGS,
    COMMAND_PB_ERR_UNSUPPORTED_PLANT,
    COMMAND_PB_LATEST,
    COMMAND_PB_ERR_NEED_ENABLE_CONFIG,
    COMMAND_PB_ERR_NO_LATEST,
    COMMAND_PB_DENY,
    COMMAND_PB_ALLOW,
    COMMAND_PB_DENY_GLOBAL,
    COMMAND_PB_ALLOW_GLOBAL,
    COMMAND_PB_ADD_VALID,
    COMMAND_PB_REMOVE_VALID,
    COMMAND_PB_SET_MAIN,
    COMMAND_PB_RESET_MAIN,
    COMMAND_PB_CLEAR_LATEST,    
    COMMAND_PB_CLEAR_DENIED,
    COMMAND_PB_CLEAR_VALID,
    COMMAND_PB_ERR_NO_DATA_FOR_LATEST,
    COMMAND_PB_ERR_NO_DATA,
    COMMAND_PB_CLEAR_ALL,
    COMMAND_PB_SAVE,
    COMMAND_PB_RELOAD,
    COMMAND_PB_BACKUP;

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
        case COMMAND_PB_HELP:
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
        case COMMAND_PB_ENABLE:
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.enable")));
            break;
        case COMMAND_PB_DISABLE:
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.disable")));
            break;
        case COMMAND_PB_STATUS:
            msg1 = PBManager.isSettingsEnabled() ? new TextComponentTranslation("pb.status.enabled") : new TextComponentTranslation("pb.status.disabled");
            msg1.getStyle().setColor(PBManager.isSettingsEnabled() ? TextFormatting.GREEN : TextFormatting.RED);        
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.status").appendSibling(new TextComponentString(": ")).appendSibling(msg1)));
            break;
        case COMMAND_PB_ERR_EXTERNAL_CONFIG_DISABLED:
            msg1 = new TextComponentTranslation("pb.config.invalidState");
            msg1.getStyle().setColor(TextFormatting.RED);        
            CommonReference.sendMessage(player, prefix().appendSibling(msg1)); 
            break;
        case COMMAND_PB_ENABLE_CONFIG:
            if (PBManager.isAutosaveEnabled())
                CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.enable.conf")).appendSibling(new TextComponentString(" ")).appendSibling(new TextComponentTranslation("pb.autosave.enabled")));
            else
                CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.enable.conf")).appendSibling(new TextComponentString(" ")).appendSibling(new TextComponentTranslation("pb.autosave.disabled")));  
            break;
        case COMMAND_PB_DISABLE_CONFIG:
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.disable.conf")));
            break;
        case COMMAND_PB_ERR_OVERLAY_DENIED:
            msg1 = new TextComponentTranslation("pb.command.err.overlay");                        
            msg1.getStyle().setColor(TextFormatting.RED);                       
            CommonReference.sendMessage(player, prefix().appendSibling(msg1));             
            break;
        case COMMAND_PB_ENABLE_OVERLAY:
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.enable.overlay")));
            break;
        case COMMAND_PB_DISABLE_OVERLAY:
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.disable.overlay")));
            break;
        case COMMAND_PB_BIOME:
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.biome", args[0])));
            break;
        case COMMAND_PB_SETTINGS:
            COMMAND_PB_STATUS.sendMessage(player, args);
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
        case COMMAND_PB_ERR_UNSUPPORTED_PLANT:
            msg1 = new TextComponentTranslation("pb.plantUnsupported");
            msg1.getStyle().setColor(TextFormatting.RED);        
            CommonReference.sendMessage(player, prefix().appendSibling(msg1));
            break;
        case COMMAND_PB_LATEST:
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
        case COMMAND_PB_ERR_NEED_ENABLE_CONFIG:
            msg1 = new TextComponentTranslation("pb.command.err.debugMode");                        
            msg1.getStyle().setColor(TextFormatting.RED);                       
            CommonReference.sendMessage(player, prefix().appendSibling(msg1));            
            break;
        case COMMAND_PB_ERR_NO_LATEST:
            msg1 = new TextComponentTranslation("pb.command.latest.notExist");                   
            msg1.getStyle().setColor(TextFormatting.RED);                       
            CommonReference.sendMessage(player, prefix().appendSibling(msg1)); 
            break;
        case COMMAND_PB_DENY:
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
        case COMMAND_PB_ALLOW:
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
        case COMMAND_PB_DENY_GLOBAL:
            msg1 = new TextComponentTranslation("pb.command.deny.global");
            msg2 = new TextComponentString(": ");
            msg3 = new TextComponentString(" / ");
            plantKey = new TextComponentString(PBManager.createDisplayKey(PBManager.latestPlant.registryName, PBManager.latestPlant.meta));
            plantKey.getStyle().setColor(TextFormatting.WHITE); 
            plantName = new TextComponentTranslation((PBManager.latestPlant.unlocalizedName).trim());                           
            plantName.getStyle().setColor(TextFormatting.WHITE);   
            CommonReference.sendMessage(player, prefix().appendSibling(msg1).appendSibling(msg2).appendSibling(plantKey).appendSibling(msg3).appendSibling(plantName));
            break;
        case COMMAND_PB_ALLOW_GLOBAL:
            msg1 = new TextComponentTranslation("pb.command.allow.global");
            msg2 = new TextComponentString(": ");
            msg3 = new TextComponentString(" / ");
            plantKey = new TextComponentString(PBManager.createDisplayKey(PBManager.latestPlant.registryName, PBManager.latestPlant.meta));
            plantKey.getStyle().setColor(TextFormatting.WHITE); 
            plantName = new TextComponentTranslation((PBManager.latestPlant.unlocalizedName).trim());                           
            plantName.getStyle().setColor(TextFormatting.WHITE);   
            CommonReference.sendMessage(player, prefix().appendSibling(msg1).appendSibling(msg2).appendSibling(plantKey).appendSibling(msg3).appendSibling(plantName));
            break;
        case COMMAND_PB_ADD_VALID:
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
        case COMMAND_PB_REMOVE_VALID:
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
        case COMMAND_PB_SET_MAIN:
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
        case COMMAND_PB_RESET_MAIN:
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.reset-main")));
            break;
        case COMMAND_PB_CLEAR_LATEST:
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.clear.latest")));    
            break;
        case COMMAND_PB_CLEAR_DENIED:
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.clear.denied")));    
            break;
        case COMMAND_PB_CLEAR_VALID:
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.clear.valid")));    
            break;
        case COMMAND_PB_ERR_NO_DATA_FOR_LATEST:
            msg1 = new TextComponentTranslation("pb.command.nodataplant");                    
            msg1.getStyle().setColor(TextFormatting.RED);       
            CommonReference.sendMessage(player, prefix().appendSibling(msg1)); 
            break;
        case COMMAND_PB_ERR_NO_DATA:
            msg1 = new TextComponentTranslation("pb.command.noData");                    
            msg1.getStyle().setColor(TextFormatting.RED);    
            CommonReference.sendMessage(player, prefix().appendSibling(msg1));  
            break;
        case COMMAND_PB_CLEAR_ALL:
            CommonReference.sendMessage(player, prefix().createCopy().appendSibling(new TextComponentTranslation("pb.command.clear.all")));    
            break;
        case COMMAND_PB_SAVE:
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.save")));    
            break;
        case COMMAND_PB_RELOAD:
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.reload")));    
            break;
        case COMMAND_PB_BACKUP:
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.backup")));    
            break;
        }
    }
}
