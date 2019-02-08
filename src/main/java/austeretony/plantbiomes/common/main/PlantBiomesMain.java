package austeretony.plantbiomes.common.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import austeretony.plantbiomes.client.render.OverlayRenderer;
import austeretony.plantbiomes.common.commands.CommandPB;
import austeretony.plantbiomes.common.network.NetworkHandler;
import austeretony.plantbiomes.common.reference.CommonReference;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(
        modid = PlantBiomesMain.MODID, 
        name = PlantBiomesMain.NAME, 
        version = PlantBiomesMain.VERSION,
        certificateFingerprint = "@FINGERPRINT@")
public class PlantBiomesMain {

    public static final String 
    MODID = "plantbiomes",
    NAME = "Plant Biomes",
    VERSION = "1.4.6",
    GAME_VERSION = "1.12.2",
    VERSIONS_URL = "https://raw.githubusercontent.com/AustereTony-MCMods/Plant-Biomes/info/versions.json",
    PROJECT_LOCATION = "minecraft.curseforge.com",
    PROJECT_URL = "https://minecraft.curseforge.com/projects/plant-biomes";

    public static final Logger LOGGER = LogManager.getLogger(NAME);

    @EventHandler
    public void init(FMLInitializationEvent event) { 
        NetworkHandler.init();
        CommonReference.registerEvent(new PlantBiomesEvents());
        UpdateChecker updateChecker = new UpdateChecker();              
        CommonReference.registerEvent(new UpdateChecker());
        new Thread(updateChecker, "Plant Biomes Update Check").start();  
        CommonReference.registerEvent(new OverlayRenderer());
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {                 
        PBManager.initServerData();
        CommonReference.registerCommand(event, new CommandPB());
    }
}
