package austeretony.plantbiomes.common.main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PBDataLoader {

    private static final String 
    EXT_CONFIGURAION_FILE = CommonReference.getGameFolder() + "/config/plantbiomes/pb_config.json",
    EXT_DATA_FILE = CommonReference.getGameFolder() + "/config/plantbiomes/pb_plants.json";

    public static ResourceLocation lpRegistryName, biomeRegistryName;

    public static String lpUnlocalizedName;

    public static int lpMeta;

    public static BlockPos lpBlockPos;

    private static final Map<ResourceLocation, PlantData> PLANTS_DATA = new LinkedHashMap<ResourceLocation, PlantData>();

    private static boolean 
    showUpdateMessages, 
    useExternalConfig, 
    isConfigModeEnabled;

    public static boolean exist(ResourceLocation registryName, int meta) {
        return PLANTS_DATA.containsKey(registryName) && get(registryName).hasData(meta);
    }

    public static boolean exist(Block block, IBlockState blockState) {
        return PLANTS_DATA.containsKey(block.getRegistryName()) && get(block.getRegistryName()).hasData(block.getMetaFromState(blockState));
    }

    public static void create(ResourceLocation registryName, int meta, String unlocalizedName) {
        if (!PLANTS_DATA.containsKey(registryName)) {
            PlantData plantData = new PlantData(registryName);
            plantData.create(meta, unlocalizedName);
            PLANTS_DATA.put(registryName, plantData);    
        } else {
            get(registryName).create(meta, unlocalizedName); 
        }
    }

    public static Map<ResourceLocation, PlantData> map() {
        return PLANTS_DATA;
    }

    public static PlantData get(ResourceLocation registryName) {
        return PLANTS_DATA.get(registryName);
    }

    public static MetaPlant get(ResourceLocation registryName, int meta) {
        return get(registryName).get(meta);
    }

    public static MetaPlant get(Block block, IBlockState blockState) {
        return get(block.getRegistryName()).get(block.getMetaFromState(blockState));
    }

    public static void remove(ResourceLocation registryKey, int meta) {
        get(registryKey).remove(meta);
        if (!get(registryKey).get(meta).hasDeniedBiomes())
            get(registryKey).remove(meta);
        if (!get(registryKey).hasData())
            PLANTS_DATA.remove(registryKey);
    }

    public static ResourceLocation getBiomeRegistryName(World world, BlockPos pos) {
        return world.getBiome(pos).getRegistryName();
    }

    public static String createDisplayKey(ResourceLocation registryName, int meta) {
        return registryName.toString() + "-" + meta;
    }

    public static void load() {             
        JsonObject internalConfig, internalData;
        try {       
            internalConfig = (JsonObject) PlantBiomesUtil.getInternalJsonData("assets/plantbiomes/pb_config.json");  
            internalData = (JsonObject) PlantBiomesUtil.getInternalJsonData("assets/plantbiomes/pb_plants.json");  
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
        dataFolder = gameFolder + "/config/plantbiomes/pb_plants.json";
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
        JsonObject plantObject, metaObject;
        String plantRegistryName, metaUnlocalizedName;
        String[] plantRegistryNameSplitted, biomeNameSplitted;
        ResourceLocation registryName;
        int meta;
        PlantData plantData;
        MetaPlant metaPlant;
        for (Map.Entry<String, JsonElement> plantEntry : data.entrySet()) {  
            plantRegistryName = plantEntry.getKey();
            plantObject = plantEntry.getValue().getAsJsonObject();
            plantRegistryNameSplitted = plantRegistryName.split("[:]");
            registryName = new ResourceLocation(plantRegistryNameSplitted[0], plantRegistryNameSplitted[1]);
            plantData = new PlantData(registryName);
            for (Map.Entry<String, JsonElement> metaEntry : plantObject.entrySet()) {  
                meta = Integer.parseInt(metaEntry.getKey());
                metaObject = metaEntry.getValue().getAsJsonObject();
                metaUnlocalizedName = metaObject.get("name").getAsString();
                metaPlant = new MetaPlant(meta, metaUnlocalizedName);
                for (JsonElement biomeName : metaObject.get("deny").getAsJsonArray()) {
                    biomeNameSplitted = biomeName.getAsString().split("[:]");
                    metaPlant.denyBiome(new ResourceLocation(biomeNameSplitted[0], biomeNameSplitted[1]));
                }
                plantData.add(meta, metaPlant);
            }
            PLANTS_DATA.put(registryName, plantData);
        }
    }

    public static void saveData() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();   
        JsonObject 
        dataObject = new JsonObject(),
        plantObject, metaObject;
        JsonArray deniedArray;        
        for (PlantData plantData : PLANTS_DATA.values()) {
            plantObject = new JsonObject();
            for (MetaPlant metaPlant : plantData.getData().values()) {
                metaObject = new JsonObject();
                deniedArray = new JsonArray();
                metaObject.add("name", new JsonPrimitive(metaPlant.unlocalizedName));
                for (ResourceLocation biomeName : metaPlant.getDeniedBiomes())
                    deniedArray.add(new JsonPrimitive(biomeName.toString()));
                metaObject.add("deny", deniedArray);
                plantObject.add(String.valueOf(metaPlant.meta), metaObject);
            }
            dataObject.add(plantData.registryName.toString(), plantObject);
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
