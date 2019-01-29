package austeretony.plantbiomes.common.main;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import austeretony.plantbiomes.common.reference.CommonReference;
import austeretony.plantbiomes.util.PBUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DataLoader {

    private static final String 
    EXT_CONFIGURAION_FILE = CommonReference.getGameFolder() + "/config/plantbiomes/config.json",
    EXT_DATA_FILE = CommonReference.getGameFolder() + "/config/plantbiomes/plants_settings.json";

    private static final DateFormat BACKUP_DATE_FORMAT = new SimpleDateFormat("yy_MM_dd-HH-mm-ss");

    public static ResourceLocation lpRegistryName, lpBiomeRegistryName;

    public static String lpUnlocalizedName;

    public static int lpMeta;

    public static BlockPos lpBlockPos;

    private static final Map<ResourceLocation, PlantData> PLANTS_DATA = new HashMap<ResourceLocation, PlantData>();

    private static boolean 
    showUpdateMessages, 
    useExternalConfig, 
    autosave,
    isSettingsEnabled = true,
    isConfigModeEnabled;

    public static boolean exist(ResourceLocation registryName, int meta) {
        return PLANTS_DATA.containsKey(registryName) && get(registryName).hasData(meta);
    }

    public static boolean exist(Block block, IBlockState blockState) {
        return exist(block.getRegistryName(), block.getMetaFromState(blockState));
    }

    public static boolean existLatest() {
        return exist(lpRegistryName, lpMeta);
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

    public static void createLatest() {
        create(lpRegistryName, lpMeta, lpUnlocalizedName);
    }

    public static Map<ResourceLocation, PlantData> map() {
        return PLANTS_DATA;
    }

    private static PlantData get(ResourceLocation registryName) {
        return PLANTS_DATA.get(registryName);
    }

    public static MetaPlant get(ResourceLocation registryName, int meta) {
        return get(registryName).get(meta);
    }

    public static MetaPlant get(Block block, IBlockState blockState) {
        return get(block.getRegistryName(), block.getMetaFromState(blockState));
    }

    public static MetaPlant getLatest() {
        return get(lpRegistryName, lpMeta);
    }

    public static void remove(ResourceLocation registryKey, int meta) {
        get(registryKey).remove(meta);
        if (!get(registryKey).hasData())
            PLANTS_DATA.remove(registryKey);
    }

    public static void removeLatest() {
        remove(lpRegistryName, lpMeta);
    }

    public static ResourceLocation getBiomeRegistryName(World world, BlockPos pos) {
        return world.getBiome(pos).getRegistryName();
    }

    public static String createDisplayKey(ResourceLocation registryName, int meta) {
        return registryName.toString() + "-" + meta;
    }

    public static void load() {             
        JsonObject internalConfig, internalSettings;
        try {       
            internalConfig = (JsonObject) PBUtils.getInternalJsonData("assets/plantbiomes/config.json");  
            internalSettings = (JsonObject) PBUtils.getInternalJsonData("assets/plantbiomes/plants_settings.json");  
        } catch (IOException exception) {       
            PlantBiomesMain.LOGGER.error("Internal configuration files damaged!");
            exception.printStackTrace();
            return;
        }
        useExternalConfig = internalConfig.get("main").getAsJsonObject().get("external_config").getAsBoolean();          
        if (!useExternalConfig) {               
            loadConfigData(internalConfig, internalSettings);
        } else                  
            loadExternalConfig(internalConfig, internalSettings);
    }

    private static void loadExternalConfig(JsonObject internalConfig, JsonObject internalSettings) {
        String 
        gameFolder = CommonReference.getGameFolder(),
        configFolder = gameFolder + "/config/plantbiomes/config.json",
        settingsFolder = gameFolder + "/config/plantbiomes/plants_settings.json";
        Path configPath = Paths.get(configFolder);      
        if (Files.exists(configPath)) {
            JsonObject externalConfig, externalSettings;
            try {                   
                externalConfig = updateConfig(internalConfig);    
                externalSettings = (JsonObject) PBUtils.getExternalJsonData(settingsFolder);       
            } catch (IOException exception) {  
                PlantBiomesMain.LOGGER.error("External configuration file damaged!");
                exception.printStackTrace();
                return;
            }       
            loadConfigData(externalConfig, externalSettings);
        } else {                
            Path dataPath = Paths.get(settingsFolder);      
            try {               
                Files.createDirectories(configPath.getParent());                                
                Files.createDirectories(dataPath.getParent());                            
                createExternalCopyAndLoad(internalConfig, internalSettings);  
            } catch (IOException exception) {               
                exception.printStackTrace();
            }                       
        }
    }

    private static JsonObject updateConfig(JsonObject internalConfig) throws IOException {
        String 
        gameFolder = CommonReference.getGameFolder(),
        configFolder = gameFolder + "/config/plantbiomes/config.json";
        JsonObject externalConfigOld, externalConfigNew, externalGroupNew;
        try {                   
            externalConfigOld = (JsonObject) PBUtils.getExternalJsonData(configFolder);       
        } catch (IOException exception) {  
            PlantBiomesMain.LOGGER.error("External configuration file damaged!");
            exception.printStackTrace();
            return null;
        }
        JsonElement versionElement = externalConfigOld.get("ver");
        if (versionElement == null || EnumModVersions.isOutdatedConfig(versionElement.getAsString())) {
            Map<String, JsonElement> 
            internalData = new LinkedHashMap<String, JsonElement>(),
            externlDataOld = new HashMap<String, JsonElement>(),
            internalGroup, externlGroupOld;
            externalConfigNew = new JsonObject();
            for (Map.Entry<String, JsonElement> entry : internalConfig.entrySet())
                internalData.put(entry.getKey(), entry.getValue());
            for (Map.Entry<String, JsonElement> entry : externalConfigOld.entrySet())
                externlDataOld.put(entry.getKey(), entry.getValue());      
            for (String key : internalData.keySet()) {
                if (key.equals("ver")) {
                    externalConfigNew.add(key, internalData.get(key));
                    continue;
                }
                internalGroup = new LinkedHashMap<String, JsonElement>();
                externlGroupOld = new HashMap<String, JsonElement>();
                externalGroupNew = new JsonObject();
                for (Map.Entry<String, JsonElement> entry : internalData.get(key).getAsJsonObject().entrySet())
                    internalGroup.put(entry.getKey(), entry.getValue());
                if (externlDataOld.containsKey(key)) {                    
                    for (Map.Entry<String, JsonElement> entry : externlDataOld.get(key).getAsJsonObject().entrySet())
                        externlGroupOld.put(entry.getKey(), entry.getValue());   
                    for (String k : internalGroup.keySet()) {
                        if (externlGroupOld.containsKey(k))
                            externalGroupNew.add(k, externlGroupOld.get(k));
                        else 
                            externalGroupNew.add(k, internalGroup.get(k));
                    }
                } else {
                    for (String k : internalGroup.keySet())
                        externalGroupNew.add(k, internalGroup.get(k));
                }
                externalConfigNew.add(key, externalGroupNew);
                PBUtils.createExternalJsonFile(EXT_CONFIGURAION_FILE, externalConfigNew);
            }
            return externalConfigNew;
        }
        return externalConfigOld;
    }

    private static void createExternalCopyAndLoad(JsonObject internalConfig, JsonObject internalSettings) {       
        try {           
            PBUtils.createExternalJsonFile(EXT_CONFIGURAION_FILE, internalConfig); 
            PBUtils.createExternalJsonFile(EXT_DATA_FILE, internalSettings);                                                                                                    
        } catch (IOException exception) {               
            exception.printStackTrace();
        }
        loadConfigData(internalConfig, internalSettings);
    }

    private static void loadConfigData(JsonObject config, JsonObject data) {          
        JsonObject mainSettings = config.get("main").getAsJsonObject();
        autosave = mainSettings.get("autosave").getAsBoolean();     
        showUpdateMessages = mainSettings.get("update_checker").getAsBoolean();     
        JsonObject plantObject, metaObject;
        String plantRegistryName;
        String[] plantRegistryNameSplitted, biomeNameSplitted;
        ResourceLocation registryName;
        int meta;
        PlantData plantData;
        MetaPlant metaPlant;
        for (Map.Entry<String, JsonElement> plantEntry : data.entrySet()) {
            if (plantEntry.getKey().equals("enabled")) {
                isSettingsEnabled = data.get("enabled").getAsBoolean();
                continue;
            }
            plantRegistryName = plantEntry.getKey();
            plantObject = plantEntry.getValue().getAsJsonObject();
            plantRegistryNameSplitted = plantRegistryName.split("[:]");
            registryName = new ResourceLocation(plantRegistryNameSplitted[0], plantRegistryNameSplitted[1]);
            plantData = new PlantData(registryName);
            for (Map.Entry<String, JsonElement> metaEntry : plantObject.entrySet()) {  
                meta = Integer.parseInt(metaEntry.getKey());
                metaObject = metaEntry.getValue().getAsJsonObject();
                metaPlant = new MetaPlant(meta, metaObject.get("name").getAsString());
                if (metaObject.get("denied_global").getAsBoolean())
                    metaPlant.denyGlobal();
                for (JsonElement b : metaObject.get("deny").getAsJsonArray()) {
                    biomeNameSplitted = b.getAsString().split("[:]");
                    metaPlant.denyBiome(new ResourceLocation(biomeNameSplitted[0], biomeNameSplitted[1]));
                }
                if (metaObject.get("valid") != null)//For previous versions compatibility
                    for (JsonElement b : metaObject.get("valid").getAsJsonArray()) {
                        biomeNameSplitted = b.getAsString().split("[:]");
                        metaPlant.addValidBiome(new ResourceLocation(biomeNameSplitted[0], biomeNameSplitted[1]));
                    }
                plantData.add(meta, metaPlant);
            }
            PLANTS_DATA.put(registryName, plantData);
        }
    }

    public static void saveSettings() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();   
        JsonObject 
        dataObject = new JsonObject(),
        plantObject, metaObject;
        JsonArray deniedArray, vailidArray;   
        dataObject.add("enabled", new JsonPrimitive(isSettingsEnabled));
        for (PlantData plantData : PLANTS_DATA.values()) {
            plantObject = new JsonObject();
            for (MetaPlant metaPlant : plantData.getData().values()) {
                metaObject = new JsonObject();
                deniedArray = new JsonArray();
                vailidArray = new JsonArray();
                metaObject.add("name", new JsonPrimitive(metaPlant.unlocalizedName));
                metaObject.add("denied_global", new JsonPrimitive(metaPlant.isDeniedGlobal()));
                for (ResourceLocation b : metaPlant.getDeniedBiomes())
                    deniedArray.add(new JsonPrimitive(b.toString()));
                metaObject.add("deny", deniedArray);
                for (ResourceLocation b : metaPlant.getValidBiomes())
                    vailidArray.add(new JsonPrimitive(b.toString()));
                metaObject.add("valid", vailidArray);
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

    public static void backupSettings() {
        try {
            PBUtils.createExternalJsonFile(
                    CommonReference.getGameFolder() + "/config/plantbiomes/plants_settings_" + BACKUP_DATE_FORMAT.format(new Date()) + ".json", 
                    PBUtils.getExternalJsonData(EXT_DATA_FILE));         
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static boolean isSettingsEnabled() {
        return isSettingsEnabled;
    }

    public static void setSettingsEnabled(boolean isEnabled) {
        isSettingsEnabled = isEnabled;
    }

    public static boolean isUpdateMessagesEnabled() {               
        return showUpdateMessages;
    }

    public static boolean isExternalConfigEnabled() {               
        return useExternalConfig;
    }

    public static boolean isAutosaveEnabled() {               
        return autosave;
    }

    public static boolean isConfigModeEnabled() {           
        return isConfigModeEnabled;
    }

    public static void setConfigModeEnabled(boolean isEnabled) {            
        isConfigModeEnabled = isEnabled;
    }
}
