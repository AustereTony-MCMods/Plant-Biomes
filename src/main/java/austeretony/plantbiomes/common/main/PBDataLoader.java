package austeretony.plantbiomes.common.main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import austeretony.plantbiomes.common.origin.CommonReference;
import austeretony.plantbiomes.util.PlantBiomesUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PBDataLoader {

    private static final String 
    EXT_CONFIGURAION_FILE = CommonReference.getGameFolder() + "/config/plantbiomes/pb_config.json",
    EXT_DATA_FILE = CommonReference.getGameFolder() + "/config/plantbiomes/pb_data.json";

    public static String 
    latestPlantKey = "",
    latestPlantUnlocalizedName;
    
    public static BlockPos latestBlockPos;

    private static final Map<String, PlantData> DENIED_BIOMES = new HashMap<String, PlantData>();

    private static boolean 
    showUpdateMessages, 
    useExternalConfig, 
    isConfigModeEnabled;

    public static boolean exist(String key) {
        return DENIED_BIOMES.containsKey(key);
    }

    public static void create(String key) {
        String[] keySplitted = key.split("[-]");
        DENIED_BIOMES.put(key, new PlantData(keySplitted[0], Integer.parseInt(keySplitted[1])));
    }

    public static Map<String, PlantData> map() {
        return DENIED_BIOMES;
    }

    public static PlantData get(String key) {
        return DENIED_BIOMES.get(key);
    }

    public static PlantData remove(String key) {
        return DENIED_BIOMES.remove(key);
    }

    public static String createPlantKey(Block block, IBlockState blockState) {
        return block.getRegistryName().toString() + "-" + block.getMetaFromState(blockState);
    }

    public static String getBiomeRegistryName(World world, BlockPos pos) {
        return world.getBiome(pos).getRegistryName().toString();
    }

    public static void load() {             
        JsonObject internalConfig, internalData;
        try {       
            internalConfig = (JsonObject) PlantBiomesUtil.getInternalJsonData("assets/plantbiomes/pb_config.json");  
            internalData = (JsonObject) PlantBiomesUtil.getInternalJsonData("assets/plantbiomes/pb_data.json");  
        } catch (IOException exception) {       
            PlantBiomesMain.LOGGER.error("Internal configuration files damaged!");
            exception.printStackTrace();
            return;
        }
        useExternalConfig = internalConfig.get("main").getAsJsonObject().get("external_config").getAsBoolean();          
        if (!useExternalConfig) {               
            loadConfigData(internalConfig, internalData);
        } else                  
            loadExternalConfig(internalConfig, internalData);
    }

    private static void loadExternalConfig(JsonObject internalConfig, JsonObject internalData) {
        String 
        gameFolder = CommonReference.getGameFolder(),
        configFolder = gameFolder + "/config/plantbiomes/pb_config.json",
        dataFolder = gameFolder + "/config/plantbiomes/pb_data.json";
        Path configPath = Paths.get(configFolder);      
        if (Files.exists(configPath)) {
            JsonObject externalConfig, externalContainers;
            try {                   
                externalConfig = (JsonObject) PlantBiomesUtil.getExternalJsonData(configFolder);       
                externalContainers = (JsonObject) PlantBiomesUtil.getExternalJsonData(dataFolder);       
            } catch (IOException exception) {  
                PlantBiomesMain.LOGGER.error("External configuration file damaged!");
                exception.printStackTrace();
                return;
            }       
            loadConfigData(externalConfig, externalContainers);
        } else {                
            Path dataPath = Paths.get(dataFolder);      
            try {               
                Files.createDirectories(configPath.getParent());                                
                Files.createDirectories(dataPath.getParent());                            
                createExternalCopyAndLoad(internalConfig, internalData);  
            } catch (IOException exception) {               
                exception.printStackTrace();
            }                       
        }
    }

    private static void createExternalCopyAndLoad(JsonObject internalConfig, JsonObject internalData) {       
        try {           
            PlantBiomesUtil.createExternalJsonFile(EXT_CONFIGURAION_FILE, internalConfig); 
            PlantBiomesUtil.createExternalJsonFile(EXT_DATA_FILE, internalData);                                                                                                    
        } catch (IOException exception) {               
            exception.printStackTrace();
        }
        loadConfigData(internalConfig, internalData);
    }

    private static void loadConfigData(JsonObject config, JsonObject data) {          
        JsonObject mainSettings = config.get("main").getAsJsonObject();       
        showUpdateMessages = mainSettings.get("update_checker").getAsBoolean();     
        JsonObject plantObject;
        String plantKey, plantRegistryName;
        String[] plantKeySplitted;
        int meta;
        PlantData plantData;
        for (Map.Entry<String, JsonElement> entry : data.entrySet()) {  
            plantKey = entry.getKey();
            plantObject = entry.getValue().getAsJsonObject();
            plantKeySplitted = plantKey.split("[-]");
            plantData = new PlantData(plantKeySplitted[0], Integer.parseInt(plantKeySplitted[1]));
            plantData.setUnlocalizedName(plantObject.get("name").getAsString());
            for (JsonElement biomeName : plantObject.get("deny").getAsJsonArray())
                plantData.denyBiome(biomeName.getAsString());
            DENIED_BIOMES.put(plantKey, plantData);
        }
    }

    public static void saveData() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();   
        JsonObject 
        dataObject = new JsonObject(),
        plantObject;
        JsonArray deniedArray;        
        for (String plantKey : DENIED_BIOMES.keySet()) {
            deniedArray = new JsonArray();
            plantObject = new JsonObject();
            plantObject.add("name", new JsonPrimitive(DENIED_BIOMES.get(plantKey).getUnlocalizedName()));
            for (String biomeName : DENIED_BIOMES.get(plantKey).getDeniedBiomesSet())
                deniedArray.add(new JsonPrimitive(biomeName));
            plantObject.add("deny", deniedArray);
            dataObject.add(plantKey, plantObject);
        }       
        try (Writer writer = new FileWriter(EXT_DATA_FILE)) {             
            gson.toJson(dataObject, writer);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static boolean isUpdateMessagesEnabled() {               
        return showUpdateMessages;
    }

    public static boolean isExternalConfigEnabled() {               
        return useExternalConfig;
    }

    public static boolean isConfigModeEnabled() {           
        return isConfigModeEnabled;
    }

    public static void setConfigModeEnabled(boolean isEnabled) {            
        isConfigModeEnabled = isEnabled;
    }
}
