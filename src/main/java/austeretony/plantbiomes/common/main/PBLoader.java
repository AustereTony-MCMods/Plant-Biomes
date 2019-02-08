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
import austeretony.plantbiomes.common.util.PBUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;

public class PBLoader {

    private static final String 
    EXT_CONFIGURAION_FILE = CommonReference.getGameFolder() + "/config/plantbiomes/config.json",
    EXT_DATA_FILE = CommonReference.getGameFolder() + "/config/plantbiomes/plants_settings.json";

    private static final DateFormat BACKUP_DATE_FORMAT = new SimpleDateFormat("yy_MM_dd-HH-mm-ss");

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
        PBManager.setExternalConfigEnabled(internalConfig.get("main").getAsJsonObject().get("external_config").getAsBoolean());          
        if (!PBManager.isExternalConfigEnabled()) {               
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
        PBManager.setAutosaveEnabled(mainSettings.get("autosave").getAsBoolean());     
        PBManager.setOverlayEnabled(mainSettings.get("settings_overlay").getAsBoolean());    
        PBManager.setTilesOverlayEnabled(mainSettings.get("tiles_overlay").getAsBoolean());     
        PBManager.setUpdateMessagesEnabled(mainSettings.get("update_checker").getAsBoolean());     
        JsonObject plantObject, metaObject;
        String[] plantRegistryNameSplitted, biomeNameSplitted;
        ResourceLocation registryName;
        EnumPBPlantType enumType;
        int meta;
        for (Map.Entry<String, JsonElement> plantEntry : data.entrySet()) {
            if (plantEntry.getKey().equals("enabled")) {
                PBManager.setSettingsEnabled(data.get("enabled").getAsBoolean());
                continue;
            }
            plantObject = plantEntry.getValue().getAsJsonObject();
            enumType = EnumPBPlantType.getOf(plantObject.get("type").getAsString());
            if (enumType != EnumPBPlantType.STANDARD && !Loader.isModLoaded(enumType.domain)) continue;
            plantRegistryNameSplitted = plantEntry.getKey().split("[:]");
            registryName = new ResourceLocation(plantRegistryNameSplitted[0], plantRegistryNameSplitted[1]);
            for (Map.Entry<String, JsonElement> metaEntry : plantObject.entrySet()) { 
                if (metaEntry.getKey().equals("type") || metaEntry.getKey().equals("main_meta")) continue;
                meta = Integer.parseInt(metaEntry.getKey());
                metaObject = metaEntry.getValue().getAsJsonObject();
                PBManager.createMetaServer(
                        enumType, 
                        new ResourceLocation(plantRegistryNameSplitted[0], plantRegistryNameSplitted[1]), 
                        Integer.parseInt(metaEntry.getKey()), 
                        metaObject.get("special").getAsString(), 
                        metaObject.get("unlocalized").getAsString());
                PBManager.getServer(registryName).setMainMeta(plantObject.get("main_meta").getAsInt());
                if (metaObject.get("denied_global").getAsBoolean())
                    PBManager.getServer(registryName).getMeta(meta).denyGlobal();
                for (JsonElement biomeName : metaObject.get("deny").getAsJsonArray()) {
                    biomeNameSplitted = biomeName.getAsString().split("[:]");
                    PBManager.getServer(registryName).getMeta(meta).denyBiome(new ResourceLocation(biomeNameSplitted[0], biomeNameSplitted[1]));
                }
                for (JsonElement biomeName : metaObject.get("valid").getAsJsonArray()) {
                    biomeNameSplitted = biomeName.getAsString().split("[:]");
                    PBManager.getServer(registryName).getMeta(meta).addValidBiome(new ResourceLocation(biomeNameSplitted[0], biomeNameSplitted[1]));
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
        dataObject.add("enabled", new JsonPrimitive(PBManager.isSettingsEnabled()));
        for (PlantData plantData : PBManager.getDataServer().values()) {
            plantObject = new JsonObject();
            plantObject.add("type", new JsonPrimitive(plantData.enumType.toString()));
            plantObject.add("main_meta", new JsonPrimitive(plantData.getMainMeta()));
            for (MetaPlant metaPlant : plantData.getData().values()) {
                metaObject = new JsonObject();
                deniedArray = new JsonArray();
                vailidArray = new JsonArray();
                metaObject.add("special", new JsonPrimitive(metaPlant.specialName));
                metaObject.add("unlocalized", new JsonPrimitive(metaPlant.unlocalizedName));
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
                    PBManager.isExternalConfigEnabled() ? PBUtils.getExternalJsonData(EXT_DATA_FILE) : PBUtils.getInternalJsonData("assets/plantbiomes/plants_settings.json"));         
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
