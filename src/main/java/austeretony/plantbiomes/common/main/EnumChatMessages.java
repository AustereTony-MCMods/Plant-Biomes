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

    UPDATE_MESSAGE {

        @Override
        public void sendMessage(EntityPlayer player, String... args) {
            ITextComponent msg1, msg2, msg3;
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
        } 
    },
    COMMAND_HELP {

        @Override
        public void sendMessage(EntityPlayer player, String... args) {      
            ITextComponent msg1, msg2;
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
        }
    },
    SETTINGS_ENABLED {

        @Override
        public void sendMessage(EntityPlayer player, String... args) {
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.enable")));
        }    
    },
    SETTINGS_DISABLED {

        @Override
        public void sendMessage(EntityPlayer player, String... args) {
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.disable")));
        }   
    },
    STATUS {

        @Override
        public void sendMessage(EntityPlayer player, String... args) {
            ITextComponent msg1;
            msg1 = DataLoader.isSettingsEnabled() ? new TextComponentTranslation("pb.status.enabled") : new TextComponentTranslation("pb.status.disabled");
            msg1.getStyle().setColor(DataLoader.isSettingsEnabled() ? TextFormatting.GREEN : TextFormatting.RED);        
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.status").appendSibling(new TextComponentString(": ")).appendSibling(msg1)));
        }   
    },
    EXTERNAL_CONFIG_DISABLED {

        @Override
        public void sendMessage(EntityPlayer player, String... args) {
            ITextComponent msg1;
            msg1 = new TextComponentTranslation("pb.config.invalidState");
            msg1.getStyle().setColor(TextFormatting.RED);        
            CommonReference.sendMessage(player, prefix().appendSibling(msg1));
        }   
    },
    CONFIGURATION_ENABLED {

        @Override
        public void sendMessage(EntityPlayer player, String... args) {
            if (DataLoader.isAutosaveEnabled())
                CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.enable.conf")).appendSibling(new TextComponentString(" ")).appendSibling(new TextComponentTranslation("pb.autosave.enabled")));
            else
                CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.enable.conf")).appendSibling(new TextComponentString(" ")).appendSibling(new TextComponentTranslation("pb.autosave.disabled")));
        }    
    },
    CONFIGURATION_DISABLED {

        @Override
        public void sendMessage(EntityPlayer player, String... args) {
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.disable.conf")));
        }   
    },
    CURRENT_BIOME {

        @Override
        public void sendMessage(EntityPlayer player, String... args) {
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.biome", args[0])));
        }   
    },
    SETTINGS_LIST {

        @Override
        public void sendMessage(EntityPlayer player, String... args) {
            ITextComponent msg1, msg2, msg3;
            STATUS.sendMessage(player, args);
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.settings")));
            if (DataLoader.map().isEmpty())
                CommonReference.sendMessage(player, new TextComponentTranslation("pb.command.settings.empty"));
            for (PlantData plantData : DataLoader.map().values()) {
                if (plantData.hasMainMeta()) {
                    msg1 = new TextComponentTranslation("pb.mainMetaSet", plantData.getMainMeta());
                    msg1.getStyle().setColor(TextFormatting.YELLOW); 
                    CommonReference.sendMessage(player, msg1);
                }
                for (MetaPlant metaPlant : plantData.getData().values()) {      
                    if (plantData.hasMainMeta() && plantData.getMainMeta() != metaPlant.meta) continue;
                    msg1 = new TextComponentString(DataLoader.createDisplayKey(plantData.registryName, metaPlant.meta));
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
        }    
    },
    UNSUPPORTED_PLANT {
        
        @Override
        public void sendMessage(EntityPlayer player, String... args) {
            ITextComponent msg1;
            msg1 = new TextComponentTranslation("pb.plantUnsupported");
            msg1.getStyle().setColor(TextFormatting.RED);        
            CommonReference.sendMessage(player, prefix().appendSibling(msg1));
        }   
    },
    LATEST_PLANT {

        @Override
        public void sendMessage(EntityPlayer player, String... args) {
            ITextComponent plantKey, plantName, biomeName, msg1, msg2, msg3, msg4;
            msg1 = new TextComponentTranslation("pb.command.latest");
            msg2 = new TextComponentString(": ");
            msg3 = new TextComponentString(" / ");
            msg4 = new TextComponentString(", ");
            msg4.getStyle().setColor(TextFormatting.AQUA);
            plantKey = new TextComponentString(DataLoader.createDisplayKey(DataLoader.latestPlant.registryName, DataLoader.latestPlant.meta));                                
            plantKey.getStyle().setColor(TextFormatting.WHITE);  
            plantName = new TextComponentTranslation((DataLoader.latestPlant.unlocalizedName).trim());                           
            plantName.getStyle().setColor(TextFormatting.WHITE);  
            biomeName = new TextComponentString(DataLoader.latestPlant.biomeRegistryName.toString());
            TextFormatting biomeColor = TextFormatting.GREEN;
            if (DataLoader.existLatest()) {
                if (DataLoader.getLatest().hasMainMeta()) {
                    if (DataLoader.getLatest().getMainMetaPlant().isValidBiomesExist())
                        biomeColor = DataLoader.getLatest().getMainMetaPlant().isValidBiome(DataLoader.latestPlant.biomeRegistryName) ? TextFormatting.DARK_GREEN : TextFormatting.DARK_RED;
                    else if (DataLoader.getLatest().getMainMetaPlant().isDeniedBiome(DataLoader.latestPlant.biomeRegistryName) || DataLoader.getLatest().getMainMetaPlant().isDeniedGlobal())
                        biomeColor = TextFormatting.RED;
                } else {
                    if (DataLoader.existMetaLatest()) {
                        if (DataLoader.getMetaLatest().isValidBiomesExist())
                            biomeColor = DataLoader.getMetaLatest().isValidBiome(DataLoader.latestPlant.biomeRegistryName) ? TextFormatting.DARK_GREEN : TextFormatting.DARK_RED;
                        else if (DataLoader.getMetaLatest().isDeniedBiome(DataLoader.latestPlant.biomeRegistryName) || DataLoader.getMetaLatest().isDeniedGlobal())
                            biomeColor = TextFormatting.RED;
                    }
                }
            }      
            biomeName.getStyle().setColor(biomeColor);  
            CommonReference.sendMessage(player, prefix().appendSibling(msg1).appendSibling(msg2).appendSibling(plantKey).appendSibling(msg3).appendSibling(plantName).appendSibling(msg4).appendSibling(biomeName));
        }       
    },
    NEED_ENABLE_CONFIGURATION {

        @Override
        public void sendMessage(EntityPlayer player, String... args) {
            ITextComponent msg1;
            msg1 = new TextComponentTranslation("pb.command.err.debugMode");                        
            msg1.getStyle().setColor(TextFormatting.RED);                       
            CommonReference.sendMessage(player, prefix().appendSibling(msg1));    
        }
    },
    NO_LATEST_PLANT {

        @Override
        public void sendMessage(EntityPlayer player, String... args) {
            ITextComponent msg1;
            msg1 = new TextComponentTranslation("pb.command.latest.notExist");                   
            msg1.getStyle().setColor(TextFormatting.RED);                       
            CommonReference.sendMessage(player, prefix().appendSibling(msg1));    
        }
    },
    DENIED {

        @Override
        public void sendMessage(EntityPlayer player, String... args) {
            ITextComponent plantKey, plantName, biomeName, msg1, msg2, msg3, msg4;
            msg1 = new TextComponentTranslation("pb.command.deny");
            msg2 = new TextComponentString(": ");
            msg3 = new TextComponentString(" -> "); 
            msg4 = new TextComponentString(" / ");
            msg3.getStyle().setColor(TextFormatting.AQUA);                   
            plantKey = new TextComponentString(DataLoader.createDisplayKey(DataLoader.latestPlant.registryName, DataLoader.latestPlant.meta));
            plantKey.getStyle().setColor(TextFormatting.WHITE); 
            plantName = new TextComponentTranslation((DataLoader.latestPlant.unlocalizedName).trim());                           
            plantName.getStyle().setColor(TextFormatting.WHITE);  
            biomeName = new TextComponentString(DataLoader.latestPlant.biomeRegistryName.toString());
            biomeName.getStyle().setColor(TextFormatting.WHITE);  
            CommonReference.sendMessage(player, prefix().appendSibling(msg1).appendSibling(msg2).appendSibling(biomeName).appendSibling(msg3).appendSibling(plantKey).appendSibling(msg4).appendSibling(plantName));
        }
    },
    ALLOWED {

        @Override
        public void sendMessage(EntityPlayer player, String... args) {
            ITextComponent plantKey, plantName, biomeName, msg1, msg2, msg3, msg4;
            msg1 = new TextComponentTranslation("pb.command.allow");
            msg2 = new TextComponentString(": ");
            msg3 = new TextComponentString(" -> "); 
            msg4 = new TextComponentString(" / ");
            msg3.getStyle().setColor(TextFormatting.AQUA);                   
            plantKey = new TextComponentString(DataLoader.createDisplayKey(DataLoader.latestPlant.registryName, DataLoader.latestPlant.meta));
            plantKey.getStyle().setColor(TextFormatting.WHITE); 
            plantName = new TextComponentTranslation((DataLoader.latestPlant.unlocalizedName).trim());                           
            plantName.getStyle().setColor(TextFormatting.WHITE);  
            biomeName = new TextComponentString(DataLoader.latestPlant.biomeRegistryName.toString());
            biomeName.getStyle().setColor(TextFormatting.WHITE);  
            CommonReference.sendMessage(player, prefix().appendSibling(msg1).appendSibling(msg2).appendSibling(biomeName).appendSibling(msg3).appendSibling(plantKey).appendSibling(msg4).appendSibling(plantName));
        }
    },
    DENIED_GLOBAL {

        @Override
        public void sendMessage(EntityPlayer player, String... args) {
            ITextComponent plantKey, plantName, msg1, msg2, msg3;
            msg1 = new TextComponentTranslation("pb.command.deny.global");
            msg2 = new TextComponentString(": ");
            msg3 = new TextComponentString(" / ");
            plantKey = new TextComponentString(DataLoader.createDisplayKey(DataLoader.latestPlant.registryName, DataLoader.latestPlant.meta));
            plantKey.getStyle().setColor(TextFormatting.WHITE); 
            plantName = new TextComponentTranslation((DataLoader.latestPlant.unlocalizedName).trim());                           
            plantName.getStyle().setColor(TextFormatting.WHITE);   
            CommonReference.sendMessage(player, prefix().appendSibling(msg1).appendSibling(msg2).appendSibling(plantKey).appendSibling(msg3).appendSibling(plantName));
        }
    },
    ALLOWED_GLOBAL {

        @Override
        public void sendMessage(EntityPlayer player, String... args) {
            ITextComponent plantKey, plantName, msg1, msg2, msg3;
            msg1 = new TextComponentTranslation("pb.command.allow.global");
            msg2 = new TextComponentString(": ");
            msg3 = new TextComponentString(" / ");
            plantKey = new TextComponentString(DataLoader.createDisplayKey(DataLoader.latestPlant.registryName, DataLoader.latestPlant.meta));
            plantKey.getStyle().setColor(TextFormatting.WHITE); 
            plantName = new TextComponentTranslation((DataLoader.latestPlant.unlocalizedName).trim());                           
            plantName.getStyle().setColor(TextFormatting.WHITE);   
            CommonReference.sendMessage(player, prefix().appendSibling(msg1).appendSibling(msg2).appendSibling(plantKey).appendSibling(msg3).appendSibling(plantName));
        }
    },
    VALID_BIOME_ADDED {

        @Override
        public void sendMessage(EntityPlayer player, String... args) {
            ITextComponent plantKey, plantName, biomeName, msg1, msg2, msg3, msg4;
            msg1 = new TextComponentTranslation("pb.command.addValid");
            msg2 = new TextComponentString(": ");
            msg3 = new TextComponentString(" -> "); 
            msg4 = new TextComponentString(" / ");
            msg3.getStyle().setColor(TextFormatting.AQUA);                   
            plantKey = new TextComponentString(DataLoader.createDisplayKey(DataLoader.latestPlant.registryName, DataLoader.latestPlant.meta));
            plantKey.getStyle().setColor(TextFormatting.WHITE); 
            plantName = new TextComponentTranslation((DataLoader.latestPlant.unlocalizedName).trim());                           
            plantName.getStyle().setColor(TextFormatting.WHITE);  
            biomeName = new TextComponentString(DataLoader.latestPlant.biomeRegistryName.toString());
            biomeName.getStyle().setColor(TextFormatting.WHITE);  
            CommonReference.sendMessage(player, prefix().appendSibling(msg1).appendSibling(msg2).appendSibling(biomeName).appendSibling(msg3).appendSibling(plantKey).appendSibling(msg4).appendSibling(plantName));
        }
    },
    VALID_BIOME_REMOVED {

        @Override
        public void sendMessage(EntityPlayer player, String... args) {
            ITextComponent plantKey, plantName, biomeName, msg1, msg2, msg3, msg4;
            msg1 = new TextComponentTranslation("pb.command.removeValid");
            msg2 = new TextComponentString(": ");
            msg3 = new TextComponentString(" -> "); 
            msg4 = new TextComponentString(" / ");
            msg3.getStyle().setColor(TextFormatting.AQUA);                   
            plantKey = new TextComponentString(DataLoader.createDisplayKey(DataLoader.latestPlant.registryName, DataLoader.latestPlant.meta));
            plantKey.getStyle().setColor(TextFormatting.WHITE); 
            plantName = new TextComponentTranslation((DataLoader.latestPlant.unlocalizedName).trim());                           
            plantName.getStyle().setColor(TextFormatting.WHITE);  
            biomeName = new TextComponentString(DataLoader.latestPlant.biomeRegistryName.toString());
            biomeName.getStyle().setColor(TextFormatting.WHITE);  
            CommonReference.sendMessage(player, prefix().appendSibling(msg1).appendSibling(msg2).appendSibling(biomeName).appendSibling(msg3).appendSibling(plantKey).appendSibling(msg4).appendSibling(plantName));
        }
    },
    MAIN_META_SET {

        @Override
        public void sendMessage(EntityPlayer player, String... args) {
            ITextComponent plantKey, plantName, msg1, msg2, msg3, msg4;
            msg1 = new TextComponentTranslation("pb.command.set-main");
            msg2 = new TextComponentString(": ");
            msg3 = new TextComponentString(" / ");
            msg4 = new TextComponentString(" ");
            msg4.getStyle().setColor(TextFormatting.AQUA);
            plantKey = new TextComponentString(DataLoader.createDisplayKey(DataLoader.latestPlant.registryName, DataLoader.latestPlant.meta));                                
            plantKey.getStyle().setColor(TextFormatting.WHITE);  
            plantName = new TextComponentTranslation((DataLoader.latestPlant.unlocalizedName).trim());                           
            plantName.getStyle().setColor(TextFormatting.WHITE);  
            CommonReference.sendMessage(player, prefix().appendSibling(msg1).appendSibling(msg2).appendSibling(plantKey).appendSibling(msg3).appendSibling(plantName).appendSibling(msg4).appendSibling(new TextComponentTranslation("pb.command.set-main.end")));
        } 
    },
    MAIN_META_RESET {

        @Override
        public void sendMessage(EntityPlayer player, String... args) {
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.reset-main")));
        } 
    },
    PLANT_ABSENT {

        @Override
        public void sendMessage(EntityPlayer player, String... args) {
            ITextComponent msg1;
            msg1 = new TextComponentTranslation("pb.command.allow.absent");                     
            msg1.getStyle().setColor(TextFormatting.RED);                       
            CommonReference.sendMessage(player, prefix().appendSibling(msg1));    
        }
    },
    PLANT_BIOMES_CLEARED {

        @Override
        public void sendMessage(EntityPlayer player, String... args) {                    
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.clear.latest")));    
        }
    },    
    DENIED_BIOMES_CLEARED {

        @Override
        public void sendMessage(EntityPlayer player, String... args) {                    
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.clear.denied")));    
        }
    },
    VALID_BIOMES_CLEARED {

        @Override
        public void sendMessage(EntityPlayer player, String... args) {                    
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.clear.valid")));    
        }
    },
    NO_DATA_FOR_PLANT {

        @Override
        public void sendMessage(EntityPlayer player, String... args) { 
            ITextComponent msg1;
            msg1 = new TextComponentTranslation("pb.command.nodataplant");                    
            msg1.getStyle().setColor(TextFormatting.RED);       
            CommonReference.sendMessage(player, prefix().appendSibling(msg1));    
        }
    },
    NO_DATA {

        @Override
        public void sendMessage(EntityPlayer player, String... args) {   
            ITextComponent msg1;
            msg1 = new TextComponentTranslation("pb.command.noData");                    
            msg1.getStyle().setColor(TextFormatting.RED);    
            CommonReference.sendMessage(player, prefix().appendSibling(msg1));    
        }
    },
    PLANTS_LIST_CLEARED {

        @Override
        public void sendMessage(EntityPlayer player, String... args) {                    
            CommonReference.sendMessage(player, prefix().createCopy().appendSibling(new TextComponentTranslation("pb.command.clear.all")));    
        }
    },
    SETTINGS_SAVED {

        @Override
        public void sendMessage(EntityPlayer player, String... args) {                    
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.save")));    
        }
    },
    BACKUP_CREATED {

        @Override
        public void sendMessage(EntityPlayer player, String... args) {                    
            CommonReference.sendMessage(player, prefix().appendSibling(new TextComponentTranslation("pb.command.backup")));    
        }
    };

    public static final ITextComponent PREFIX;

    static {

        PREFIX = new TextComponentString("[Plant Biomes] ");
        PREFIX.getStyle().setColor(TextFormatting.AQUA);                   
    }

    private static ITextComponent prefix() {
        return PREFIX.createCopy();
    }

    public abstract void sendMessage(EntityPlayer player, String... args); 
}
