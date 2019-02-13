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
        certificateFingerprint = "@FINGERPRINT@",
        updateJSON = PlantBiomesMain.VERSIONS_FORGE_URL)
public class PlantBiomesMain {

    public static final String 
    MODID = "plantbiomes",
    NAME = "Plant Biomes",
    VERSION = "1.5.0",
    VERSION_CUSTOM = VERSION + ":beta:0",
    GAME_VERSION = "1.12.2",
    VERSIONS_FORGE_URL = "https://raw.githubusercontent.com/AustereTony-MCMods/Plant-Biomes/info/mod_versions_forge.json",
    VERSIONS_CUSTOM_URL = "https://raw.githubusercontent.com/AustereTony-MCMods/Plant-Biomes/info/mod_versions_custom.json",
    PROJECT_LOCATION = "minecraft.curseforge.com",
    PROJECT_URL = "https://minecraft.curseforge.com/projects/plant-biomes";

    public static final Logger LOGGER = LogManager.getLogger(NAME);

    @EventHandler
    public void init(FMLInitializationEvent event) { 
        NetworkHandler.init();
        CommonReference.registerEvent(new PlantBiomesEvents());
        CommonReference.registerEvent(new UpdateChecker());
        CommonReference.registerEvent(new OverlayRenderer());
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {  
        DataManager.initServerData();
        CommonReference.registerCommand(event, new CommandPB());
        new Thread(new UpdateChecker(), "Plant Biomes Update Check").start();  
    }
}
