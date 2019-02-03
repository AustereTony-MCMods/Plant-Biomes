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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

public class DataLoader {

    private static final String 
    EXT_CONFIGURAION_FILE = CommonReference.getGameFolder() + "/config/plantbiomes/config.json",
    EXT_DATA_FILE = CommonReference.getGameFolder() + "/config/plantbiomes/plants_settings.json";

    private static final DateFormat BACKUP_DATE_FORMAT = new SimpleDateFormat("yy_MM_dd-HH-mm-ss");

    public static LatestPlant latestPlant;

    private static final Map<ResourceLocation, PlantData> PLANTS_DATA = new HashMap<ResourceLocation, PlantData>();

    public static Map<String, ResourceLocation> ic2Crops, forestrySaplings;

    private static boolean 
    showUpdateMessages, 
    useExternalConfig, 
    autosave,
    isSettingsEnabled = true,
    isConfigModeEnabled,
    ic2Loaded,
    forestryLoaded,
    hasPlantsData,
    hasIC2Crops,
    hasForestrySaplings;

    public static boolean exist(ResourceLocation registryName) {
        return hasPlantsData && PLANTS_DATA.containsKey(registryName);
    }

    public static boolean exist(Block block) {
        return exist(block.getRegistryName());
    }

    public static boolean existLatest() {
        return exist(latestPlant.registryName);
    }

    public static boolean existIC2(String cropId) {
        return hasIC2Crops && ic2Crops.containsKey(cropId) && exist(ic2Crops.get(cropId));
    }

    public static boolean existForestry(String ident) {
        return hasForestrySaplings && forestrySaplings.containsKey(ident) && exist(forestrySaplings.get(ident));
    }

    public static boolean existMetaLatest() {
        return exist(latestPlant.registryName) && get(latestPlant.registryName).hasMetaData(latestPlant.meta);
    }

    public static void createMetaLatest() {
        if (!PLANTS_DATA.containsKey(latestPlant.registryName)) {
            PlantData plantData = new PlantData(latestPlant.registryName);
            plantData.createMeta(latestPlant.meta, latestPlant.unlocalizedName);
            PLANTS_DATA.put(latestPlant.registryName, plantData);  
            if (latestPlant.isIC2Crop()) {
                ic2Crops.put(latestPlant.getIC2CropId(), latestPlant.registryName);
                hasIC2Crops = true;
            } else if (latestPlant.isForestrySapling()) {
                forestrySaplings.put(latestPlant.getForestrySaplingIdent(), latestPlant.registryName);
                hasForestrySaplings = true;
            }
        } else {
            get(latestPlant.registryName).createMeta(latestPlant.meta, latestPlant.unlocalizedName); 
        }
        hasPlantsData = true;
    }

    public static Map<ResourceLocation, PlantData> map() {
        return PLANTS_DATA;
    }

    public static void clear() {
        PLANTS_DATA.clear();
        hasPlantsData = false;
        if (ic2Loaded) {
            ic2Crops.clear();
            hasIC2Crops = false;
        }
        if (forestryLoaded) {
            forestrySaplings.clear();
            hasForestrySaplings = false;
        }
    }

    private static PlantData get(ResourceLocation registryName) {
        return PLANTS_DATA.get(registryName);
    }

    public static PlantData get(Block block) {
        return get(block.getRegistryName());
    }

    public static PlantData getLatest() {
        return get(latestPlant.registryName);
    }

    public static PlantData getIC2(String cropId) {
        return get(ic2Crops.get(cropId));
    }

    public static PlantData getForestry(String ident) {
        return get(forestrySaplings.get(ident));
    }

    public static MetaPlant getMetaLatest() {
        return get(latestPlant.registryName).getMeta(latestPlant.meta);
    }

    public static void removeMetaLatest() {
        get(latestPlant.registryName).removeMeta(latestPlant.meta);
        if (latestPlant.isIC2Crop()) {
            ic2Crops.remove(latestPlant.getIC2CropId());
            hasIC2Crops = ic2Crops.isEmpty();
        } else if (latestPlant.isForestrySapling()) {
            forestrySaplings.remove(latestPlant.getForestrySaplingIdent());
            hasForestrySaplings = forestrySaplings.isEmpty();
        }
        if (!get(latestPlant.registryName).hasData())
            PLANTS_DATA.remove(latestPlant.registryName);
        hasPlantsData = PLANTS_DATA.isEmpty();
    }

    public static ResourceLocation getBiomeRegistryName(World world, BlockPos pos) {
        return world.getBiome(pos).getRegistryName();
    }

    public static String createDisplayKey(ResourceLocation registryName, int meta) {
        return registryName.toString() + "-" + meta;
    }

    public static void load() {
        ic2Loaded = Loader.isModLoaded("ic2");
        forestryLoaded = Loader.isModLoaded("forestry");
        if (ic2Loaded)
            ic2Crops = new HashMap<String, ResourceLocation>();
        if (forestryLoaded)
            forestrySaplings = new HashMap<String, ResourceLocation>();
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
        if (versionElement == null || isOutdatedConfig(versionElement.getAsString())) {
            externalConfigNew = new JsonObject();
            externalConfigNew.add("ver", new JsonPrimitive(PlantBiomesMain.VERSION));
            Map<String, JsonElement> 
            internalData = new LinkedHashMap<String, JsonElement>(),
            externlDataOld = new HashMap<String, JsonElement>(),
            internalGroup, externlGroupOld;
            for (Map.Entry<String, JsonElement> entry : internalConfig.entrySet())
                internalData.put(entry.getKey(), entry.getValue());
            for (Map.Entry<String, JsonElement> entry : externalConfigOld.entrySet())
                externlDataOld.put(entry.getKey(), entry.getValue());      
            for (String key : internalData.keySet()) {
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

    private static boolean isOutdatedConfig(String configVersion) {
        return PBUtils.isOutdated(configVersion, PlantBiomesMain.VERSION);
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
        String plantRegistryName, plantUnlocalizedName;
        String[] plantRegistryNameSplitted, biomeNameSplitted, unlocNameSplitted;
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
            if (plantRegistryNameSplitted.length != 2) continue;
            registryName = new ResourceLocation(plantRegistryNameSplitted[0], plantRegistryNameSplitted[1]);
            plantData = new PlantData(registryName);
            if (plantObject.get("main_meta") != null)//for compatibility (till 1.3.0) 
                plantData.setMainMeta(plantObject.get("main_meta").getAsInt());
            for (Map.Entry<String, JsonElement> metaEntry : plantObject.entrySet()) {  
                if (metaEntry.getKey().equals("main_meta")) continue;
                meta = Integer.parseInt(metaEntry.getKey());
                metaObject = metaEntry.getValue().getAsJsonObject();
                plantUnlocalizedName = metaObject.get("name").getAsString();
                unlocNameSplitted = plantUnlocalizedName.split("[.]");
                if (unlocNameSplitted.length > 1 && !unlocNameSplitted[1].equals("crop") && !unlocNameSplitted[unlocNameSplitted.length - 1].equals("name"))//for compatibility (till 1.3.0) 
                    plantUnlocalizedName = plantUnlocalizedName + ".name";
                metaPlant = new MetaPlant(meta, plantUnlocalizedName);
                if (metaObject.get("denied_global").getAsBoolean())
                    metaPlant.denyGlobal();
                for (JsonElement b : metaObject.get("deny").getAsJsonArray()) {
                    biomeNameSplitted = b.getAsString().split("[:]");
                    metaPlant.denyBiome(new ResourceLocation(biomeNameSplitted[0], biomeNameSplitted[1]));
                }
                if (metaObject.get("valid") != null)//for compatibility (till 1.2.0)
                    for (JsonElement b : metaObject.get("valid").getAsJsonArray()) {
                        biomeNameSplitted = b.getAsString().split("[:]");
                        metaPlant.addValidBiome(new ResourceLocation(biomeNameSplitted[0], biomeNameSplitted[1]));
                    }
                plantData.addMeta(meta, metaPlant);
            }
            PLANTS_DATA.put(registryName, plantData);
            hasPlantsData = true;
            if (plantData.hasMetaData(16)) {
                if (ic2Loaded || forestryLoaded) {
                    String[] splitted = plantData.getMeta(16).unlocalizedName.split("[.]");
                    if (splitted.length == 3) {
                        if (splitted[1].equals("crop")) {
                            ic2Crops.put(splitted[2], plantData.registryName);
                            hasIC2Crops = true;
                        } else if (splitted[0].equals("forestry")) {
                            forestrySaplings.put(splitted[0] + "." + splitted[1], plantData.registryName);
                            hasForestrySaplings = true;
                        }
                    }
                }
            }
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
            plantObject.add("main_meta", new JsonPrimitive(plantData.getMainMeta()));
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
                    isExternalConfigEnabled() ? PBUtils.getExternalJsonData(EXT_DATA_FILE) : PBUtils.getInternalJsonData("assets/plantbiomes/plants_settings.json"));         
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static boolean isIC2Loaded() {
        return ic2Loaded;
    }

    public static boolean forestryLoaded() {
        return forestryLoaded;
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
