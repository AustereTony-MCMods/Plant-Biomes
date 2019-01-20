package austeretony.plantbiomes.common.main;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;

public enum EnumPBChatMessages {

    UPDATE_MESSAGE_HEAD,
    UPDATE_MESSAGE_LINK,
    LATEST_PLANT,
    CONFIGURATION_ENABLED,
    CONFIGURATION_DISABLED,
    CONTAINERS_LIST,
    EMPTY,
    PLANT,
    DENIED_BIOME,
    NEED_ENABLE_CONFIGURATION,
    NO_LATEST_PLANT,
    DENIED,
    ALLOWED,
    PLANT_ABSENT,
    PLANT_BIOMES_CLEARED,
    NO_DATA_FOR_PLANT,
    NO_DATA,
    PLANTS_LIST_CLEARED,
    CHANGES_SAVED;

    private static int index;

    private final int id;

    EnumPBChatMessages() {
        this.id = createId();
    }

    private int createId() {
        return index++;
    }

    public int getId() {
        return this.id;
    }

    public ITextComponent getLocalizedMessage(String ... args) {
        ITextComponent 
        message = null,
        modPrefix = new TextComponentString("[Plant Biomes] "),
        plantKey, plantName, biomeName, msg1, msg2, msg3, msg4;
        modPrefix.getStyle().setColor(TextFormatting.AQUA);
        switch (this) {
        case UPDATE_MESSAGE_HEAD:
            msg1 = new TextComponentTranslation("pb.update.newVersion");
            msg2 = new TextComponentString(" [" + PlantBiomesMain.VERSION + "/" + args[0] + "]");        
            message = modPrefix.appendSibling(msg1).appendSibling(msg2);
            break;
        case UPDATE_MESSAGE_LINK:
            msg1 = new TextComponentTranslation("pb.update.projectPage");
            msg2 = new TextComponentString(": ");
            msg3 = new TextComponentString(PlantBiomesMain.PROJECT_LOCATION);	
            msg1.getStyle().setColor(TextFormatting.AQUA);      
            msg3.getStyle().setColor(TextFormatting.WHITE);		        	
            msg3.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, PlantBiomesMain.PROJECT_URL));		
            message = msg1.appendSibling(msg2).appendSibling(msg3);	
            break;
        case LATEST_PLANT:
            msg1 = new TextComponentTranslation("pb.command.plant");
            msg2 = new TextComponentString(": ");
            msg3 = new TextComponentString(" / ");
            msg4 = new TextComponentString(", ");
            msg4.getStyle().setColor(TextFormatting.AQUA);
            plantKey = new TextComponentString(PBDataLoader.createDisplayKey(PBDataLoader.lpRegistryName, PBDataLoader.lpMeta));				
            plantKey.getStyle().setColor(TextFormatting.WHITE);  
            plantName = new TextComponentTranslation((PBDataLoader.lpUnlocalizedName + ".name").trim());                           
            plantName.getStyle().setColor(TextFormatting.WHITE);  
            biomeName = new TextComponentString(PBDataLoader.biomeRegistryName.toString()); 
            boolean deniedBiome = PBDataLoader.exist(PBDataLoader.lpRegistryName, PBDataLoader.lpMeta) && 
                    !PBDataLoader.get(PBDataLoader.lpRegistryName, PBDataLoader.lpMeta).isValidBiome(PBDataLoader.biomeRegistryName);
            biomeName.getStyle().setColor(deniedBiome ? TextFormatting.RED : TextFormatting.GREEN);  
            message = modPrefix.appendSibling(msg1).appendSibling(msg2).appendSibling(plantKey).appendSibling(msg3).appendSibling(plantName).appendSibling(msg4).appendSibling(biomeName);
            break;
        case CONFIGURATION_ENABLED:
            msg1 = new TextComponentTranslation("pb.command.enable");
            message = modPrefix.appendSibling(msg1);
            break;		
        case CONFIGURATION_DISABLED:
            msg1 = new TextComponentTranslation("pb.command.disable");
            message = modPrefix.appendSibling(msg1);
            break;		
        case CONTAINERS_LIST:
            msg1 = new TextComponentTranslation("pb.command.list");
            message = modPrefix.appendSibling(msg1);
            break;	
        case EMPTY:
            message = new TextComponentTranslation("pb.command.list.empty");
            break;	
        case PLANT:
            msg1 = new TextComponentString(args[0]);
            msg2 = new TextComponentString(" / ");
            msg3 = new TextComponentTranslation((args[1] + ".name").trim());
            message = msg1.appendSibling(msg2).appendSibling(msg3);
            break;	
        case DENIED_BIOME:                 
            message = new TextComponentString(" - " + args[0]);
            message.getStyle().setColor(TextFormatting.RED);                   
            break;      
        case NEED_ENABLE_CONFIGURATION:
            msg1 = new TextComponentTranslation("pb.command.err.debugMode");			
            msg1.getStyle().setColor(TextFormatting.RED);			
            message = modPrefix.appendSibling(msg1);	
            break;
        case NO_LATEST_PLANT:
            msg1 = new TextComponentTranslation("pb.command.plant.notExist");			
            msg1.getStyle().setColor(TextFormatting.RED);			
            message = modPrefix.appendSibling(msg1);	
            break;
        case DENIED:
            msg1 = new TextComponentTranslation("pb.command.deny");
            msg2 = new TextComponentString(": ");
            msg3 = new TextComponentString(" -> "); 
            msg4 = new TextComponentString(" / ");
            msg3.getStyle().setColor(TextFormatting.AQUA);                   
            plantKey = new TextComponentString(PBDataLoader.createDisplayKey(PBDataLoader.lpRegistryName, PBDataLoader.lpMeta));
            plantKey.getStyle().setColor(TextFormatting.WHITE); 
            plantName = new TextComponentTranslation((PBDataLoader.lpUnlocalizedName + ".name").trim());                           
            plantName.getStyle().setColor(TextFormatting.WHITE);  
            biomeName = new TextComponentString(PBDataLoader.biomeRegistryName.toString());
            biomeName.getStyle().setColor(TextFormatting.WHITE);  
            message = modPrefix.appendSibling(msg1).appendSibling(msg2).appendSibling(biomeName).appendSibling(msg3).appendSibling(plantKey).appendSibling(msg4).appendSibling(plantName);
            break;
        case ALLOWED:
            msg1 = new TextComponentTranslation("pb.command.allow");
            msg2 = new TextComponentString(": ");
            msg3 = new TextComponentString(" -> "); 
            msg3.getStyle().setColor(TextFormatting.AQUA);
            plantKey = new TextComponentString(PBDataLoader.createDisplayKey(PBDataLoader.lpRegistryName, PBDataLoader.lpMeta));
            plantKey.getStyle().setColor(TextFormatting.WHITE);  
            biomeName = new TextComponentString(PBDataLoader.biomeRegistryName.toString());
            biomeName.getStyle().setColor(TextFormatting.WHITE);  
            message = modPrefix.appendSibling(msg1).appendSibling(msg2).appendSibling(biomeName).appendSibling(msg3).appendSibling(plantKey);
            break;
        case PLANT_ABSENT:
            msg1 = new TextComponentTranslation("pb.command.allow.absent");			
            msg1.getStyle().setColor(TextFormatting.RED);			
            message = modPrefix.appendSibling(msg1);
            break;
        case NO_DATA_FOR_PLANT:
            msg1 = new TextComponentTranslation("pb.command.nodataplant");                    
            msg1.getStyle().setColor(TextFormatting.RED);                       
            message = modPrefix.appendSibling(msg1);
            break;
        case NO_DATA:
            msg1 = new TextComponentTranslation("pb.command.noData");                    
            msg1.getStyle().setColor(TextFormatting.RED);                       
            message = modPrefix.appendSibling(msg1);
            break;
        case PLANT_BIOMES_CLEARED:
            msg1 = new TextComponentTranslation("pb.command.clear.latest");
            message = modPrefix.appendSibling(msg1);
            break;
        case PLANTS_LIST_CLEARED:
            msg1 = new TextComponentTranslation("pb.command.clear.all");
            message = modPrefix.appendSibling(msg1);
            break;
        case CHANGES_SAVED:
            msg1 = new TextComponentTranslation("pb.command.save");
            message = modPrefix.appendSibling(msg1);
            break;
        default:
            break;
        }
        return message;
    }

    public static void showMessage(EntityPlayer player, EnumPBChatMessages msg, String ... args) {
        player.sendMessage(msg.getLocalizedMessage(args));
    }      
}
