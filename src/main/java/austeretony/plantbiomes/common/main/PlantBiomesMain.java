package austeretony.plantbiomes.common.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonSyntaxException;

import austeretony.plantbiomes.common.commands.CommandPlantBiomes;
import austeretony.plantbiomes.common.origin.CommonReference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = PlantBiomesMain.MODID, name = PlantBiomesMain.NAME, version = PlantBiomesMain.VERSION)
public class PlantBiomesMain {

    public static final String 
    MODID = "plantbiomes",
    NAME = "Plant Biomes",
    VERSION = "1.0.0",
    GAME_VERSION = "1.12.2",
    VERSIONS_URL = "https://raw.githubusercontent.com/AustereTony-MCMods/NMQM/info/versions.json",//edit
    PROJECT_LOCATION = "minecraft.curseforge.com",
    PROJECT_URL = "https://minecraft.curseforge.com/projects/nmqm";//edit

    public static final Logger LOGGER = LogManager.getLogger("Plant Biomes");

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {  
        try {                   
            PBDataLoader.load();
        } catch (JsonSyntaxException exception) {                       
            LOGGER.error("Config parsing failure! Fix syntax errors!");                     
            exception.printStackTrace();
        }
        CommonReference.registerCommand(event, new CommandPlantBiomes());
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
