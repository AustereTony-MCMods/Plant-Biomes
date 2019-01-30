package austeretony.plantbiomes.common.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonSyntaxException;

import austeretony.plantbiomes.common.commands.CommandPB;
import austeretony.plantbiomes.common.reference.CommonReference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = PlantBiomesMain.MODID, name = PlantBiomesMain.NAME, version = PlantBiomesMain.VERSION)
public class PlantBiomesMain {

    public static final String 
    MODID = "plantbiomes",
    NAME = "Restrictions: Plant Biomes",
    VERSION = "1.3.0",
    GAME_VERSION = "1.12.2",
    VERSIONS_URL = "https://raw.githubusercontent.com/AustereTony-MCMods/Plant-Biomes/info/versions.json",
    PROJECT_LOCATION = "minecraft.curseforge.com",
    PROJECT_URL = "https://minecraft.curseforge.com/projects/plant-biomes";

    public static final Logger LOGGER = LogManager.getLogger("Plant Biomes");

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {  
        try {                   
            DataLoader.load();
        } catch (JsonSyntaxException exception) {                       
            LOGGER.error("Config parsing failure! Fix syntax errors!");                     
            exception.printStackTrace();
        }
        CommonReference.registerCommand(event, new CommandPB());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {        
        CommonReference.registerEvent(new PlantBiomesEvents());
        UpdateChecker updateChecker = new UpdateChecker();              
        CommonReference.registerEvent(new UpdateChecker());
        new Thread(updateChecker, "Plant Biomes Update Check").start();                 
        LOGGER.info("Update check started...");
    }
}
