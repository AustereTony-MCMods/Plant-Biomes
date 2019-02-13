package austeretony.plantbiomes.common.config;

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

import austeretony.plantbiomes.common.core.EnumInputClasses;
import austeretony.plantbiomes.common.core.PlantBiomesClassTransformer;
import austeretony.plantbiomes.common.main.DataManager;
import austeretony.plantbiomes.common.main.EnumPlantType;
import austeretony.plantbiomes.common.main.EnumSpecialPlants;
import austeretony.plantbiomes.common.main.EnumStandardPlants;
import austeretony.plantbiomes.common.main.MetaPlant;
import austeretony.plantbiomes.common.main.PlantBiomesMain;
import austeretony.plantbiomes.common.main.PlantData;
import austeretony.plantbiomes.common.main.UpdateChecker;
import austeretony.plantbiomes.common.reference.CommonReference;
import austeretony.plantbiomes.common.util.PBUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;

public class ConfigLoader {

    private static final String 
    EXT_TRANSFORMERS_FILE = CommonReference.getGameFolder() + "/config/plantbiomes/transformers.json",
    EXT_CONFIGURATION_FILE = CommonReference.getGameFolder() + "/config/plantbiomes/config.json",
    EXT_DATA_FILE = CommonReference.getGameFolder() + "/config/plantbiomes/plants_settings.json";

    private static final DateFormat BACKUP_DATE_FORMAT = new SimpleDateFormat("yy_MM_dd-HH-mm-ss");

    public static void loadTransformerSettings() {
        JsonObject internalConfig;
        try {       
            internalConfig = (JsonObject) PBUtils.getInternalJsonData("assets/plantbiomes/config.json");  
        } catch (IOException exception) {       
            PlantBiomesClassTransformer.CORE_LOGGER.error("Internal configuration files damaged!");
            exception.printStackTrace();
            return;
        }
        if (EnumConfigSettings.EXTERNAL_CONFIG.initBoolean(internalConfig)) {
            JsonObject externalConfig;
            try {       
                externalConfig = (JsonObject) PBUtils.getExternalJsonData(EXT_CONFIGURATION_FILE);  
            } catch (IOException exception) {       
                PlantBiomesClassTransformer.CORE_LOGGER.error("External configuration files damaged!");
                exception.printStackTrace();
                return;
            }
            if (EnumConfigSettings.TRANSFORMERS_FILE.initBoolean(externalConfig)) {
                PlantBiomesClassTransformer.CORE_LOGGER.error("Transformer settings enabled!");
                EnumInputClasses.map();          
                loadExternalTransformerSettings();
            } else {
                PlantBiomesClassTransformer.CORE_LOGGER.error("Transformer settings disabled!");
            }
        } else {
            if (EnumConfigSettings.TRANSFORMERS_FILE.initBoolean(internalConfig)) {
                PlantBiomesClassTransformer.CORE_LOGGER.error("Transformer settings enabled!");
                EnumInputClasses.map();             
                loadInternalTransformerSettings();
            } else {
                PlantBiomesClassTransformer.CORE_LOGGER.error("Transformer settings disabled!");
            }
        }
    }

    private static void loadInternalTransformerSettings() {
        JsonObject transformersConfig;
        try {       
            transformersConfig = (JsonObject) PBUtils.getInternalJsonData("assets/plantbiomes/transformers.json");  
        } catch (IOException exception) {       
            PlantBiomesClassTransformer.CORE_LOGGER.error("Internal transformers configuration file damaged!");
            exception.printStackTrace();
            return;
        }
        loadTransformerSettings(transformersConfig);
    }

    private static void loadExternalTransformerSettings() {
        Path configPath = Paths.get(EXT_TRANSFORMERS_FILE);      
        if (Files.exists(configPath)) {
            JsonObject transformersConfig;
            try {                   
                transformersConfig = (JsonObject) PBUtils.getExternalJsonData(EXT_TRANSFORMERS_FILE);       
            } catch (IOException exception) {  
                PlantBiomesClassTransformer.CORE_LOGGER.error("External transformers configuration file damaged!");
                exception.printStackTrace();
                return;
            }       
            loadTransformerSettings(transformersConfig);
        } else {                
            try {               
                Files.createDirectories(configPath.getParent());
                JsonObject transformersConfig = (JsonObject) PBUtils.getInternalJsonData("assets/plantbiomes/transformers.json");
                PBUtils.createExternalJsonFile(EXT_TRANSFORMERS_FILE, transformersConfig);    
                loadTransformerSettings(transformersConfig);
            } catch (IOException exception) {               
                exception.printStackTrace();
            }                       
        }
    }

    private static void loadTransformerSettings(JsonObject transformersConfig) { 
        JsonObject modObject, classObject;
        String mod, id;
        boolean transform;
        EnumInputClasses enumClass;
        PlantBiomesClassTransformer.CORE_LOGGER.info("Load transformers settings...");
        for (JsonElement modElement : transformersConfig.get("settings").getAsJsonArray()) {
            modObject = modElement.getAsJsonObject();
            mod = modObject.get("mod").getAsString();
            for (JsonElement classElement : modElement.getAsJsonObject().get("classes").getAsJsonArray()) {
                classObject = classElement.getAsJsonObject();
                id = classObject.get("id").getAsString();
                transform = classObject.get("transform").getAsBoolean();
                enumClass = EnumInputClasses.classesById.get(id);
                if (enumClass != null) {
                    enumClass.setShouldPatch(transform);
                    if (transform)
                        PlantBiomesClassTransformer.CORE_LOGGER.info(mod + " class <" + enumClass.clazz + "> will be patched!");
                    else
                        PlantBiomesClassTransformer.CORE_LOGGER.info(mod + " class <" + enumClass.clazz + "> WILL NOT be patched!");
                } else {
                    PlantBiomesClassTransformer.CORE_LOGGER.info("Can't find class transformer for id: " + id + "!");
                }
            }
        }
        //if some class transformer disabled, disable appropriate plant identification (plant will not be supported)
        for (EnumStandardPlants enumPlant : EnumStandardPlants.values())
            if (EnumInputClasses.classesById.containsKey(enumPlant.transformedBlockId)) 
                enumPlant.setEnabled(EnumInputClasses.classesById.get(enumPlant.transformedBlockId).shouldPatch());
        for (EnumSpecialPlants enumPlant : EnumSpecialPlants.values())
            if (EnumInputClasses.classesById.containsKey(enumPlant.transformedBlockId)) 
                enumPlant.setEnabled(EnumInputClasses.classesById.get(enumPlant.transformedBlockId).shouldPatch());
    }

    public static void loadOverallSettings() {
        JsonObject internalConfig, internalSettings;
        try {       
            internalConfig = (JsonObject) PBUtils.getInternalJsonData("assets/plantbiomes/config.json");  
            internalSettings = (JsonObject) PBUtils.getInternalJsonData("assets/plantbiomes/plants_settings.json");  
        } catch (IOException exception) {       
            PlantBiomesMain.LOGGER.error("Internal configuration files damaged!");
            exception.printStackTrace();
            return;
        }
        if (EnumConfigSettings.EXTERNAL_CONFIG.isEnabled()) {               
            loadExternalConfig(internalConfig, internalSettings);
        } else                  
            loadData(internalConfig, internalSettings);
    }

    private static void loadExternalConfig(JsonObject internalConfig, JsonObject internalSettings) {
        Path configPath = Paths.get(EXT_CONFIGURATION_FILE);      
        if (Files.exists(configPath)) {
            JsonObject externalConfig, externalSettings;
            try {                   
                externalConfig = updateConfig(internalConfig);    
                externalSettings = (JsonObject) PBUtils.getExternalJsonData(EXT_DATA_FILE);       
            } catch (IOException exception) {  
                PlantBiomesMain.LOGGER.error("External configuration file damaged!");
                exception.printStackTrace();
                return;
            }       
            loadData(externalConfig, externalSettings);
        } else {                
            Path dataPath = Paths.get(EXT_DATA_FILE);      
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
        JsonObject externalConfigOld, externalConfigNew, externalGroupNew;
        try {                   
            externalConfigOld = (JsonObject) PBUtils.getExternalJsonData(EXT_CONFIGURATION_FILE);       
        } catch (IOException exception) {  
            PlantBiomesMain.LOGGER.error("External configuration file damaged!");
            exception.printStackTrace();
            return null;
        }
        JsonElement versionElement = externalConfigOld.get("version");
        if (versionElement == null || UpdateChecker.isOutdated(versionElement.getAsString(), PlantBiomesMain.VERSION_CUSTOM)) {
            externalConfigNew = new JsonObject();
            externalConfigNew.add("version", new JsonPrimitive(PlantBiomesMain.VERSION_CUSTOM));
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
                PBUtils.createExternalJsonFile(EXT_CONFIGURATION_FILE, externalConfigNew);
            }
            return externalConfigNew;
        }
        return externalConfigOld;
    }

    private static void createExternalCopyAndLoad(JsonObject internalConfig, JsonObject internalSettings) {       
        try {           
            PBUtils.createExternalJsonFile(EXT_CONFIGURATION_FILE, internalConfig); 
            PBUtils.createExternalJsonFile(EXT_DATA_FILE, internalSettings);                                                                                                    
        } catch (IOException exception) {               
            exception.printStackTrace();
        }
        loadData(internalConfig, internalSettings);
    }

    private static void loadData(JsonObject configFile, JsonObject settingsFile) {          
        EnumConfigSettings.CHECK_UPDATES.initBoolean(configFile);     
        EnumConfigSettings.AUTOSAVE.initBoolean(configFile); 
        EnumConfigSettings.SETTINGS_TOOLTIPS.initBoolean(configFile); 
        EnumConfigSettings.SETTINGS_OVERLAY.initBoolean(configFile);     
        EnumConfigSettings.TILES_OVERLAY.initBoolean(configFile);   
        EnumConfigSettings.SMOKE_OVER_TIME.initBoolean(configFile);   
        EnumConfigSettings.SMOKE_ON_BONEMEAL.initBoolean(configFile);   
        JsonObject plantObject, metaObject, boundItemObject;
        String boundItemName;
        ResourceLocation registryName;
        EnumPlantType enumPlantType;
        int meta;
        MetaPlant metaPlant;
        for (Map.Entry<String, JsonElement> plantEntry : settingsFile.entrySet()) {
            if (plantEntry.getKey().equals("enabled")) {
                DataManager.setSettingsEnabled(settingsFile.get("enabled").getAsBoolean());
                continue;
            }
            plantObject = plantEntry.getValue().getAsJsonObject();
            enumPlantType = EnumPlantType.getOf(plantObject.get("type").getAsString());
            if (enumPlantType != EnumPlantType.STANDARD && !Loader.isModLoaded(enumPlantType.domain)) continue;
            registryName = new ResourceLocation(plantEntry.getKey());
            for (Map.Entry<String, JsonElement> metaEntry : plantObject.entrySet()) { 
                if (metaEntry.getKey().equals("type") || metaEntry.getKey().equals("main_meta")) continue;
                meta = Integer.parseInt(metaEntry.getKey());
                metaObject = metaEntry.getValue().getAsJsonObject();
                DataManager.createMetaServer(
                        enumPlantType, 
                        registryName,
                        Integer.parseInt(metaEntry.getKey()), 
                        metaObject.get("special").getAsString(), 
                        metaObject.get("unlocalized").getAsString());
                metaPlant = DataManager.getServer(registryName).getMeta(meta);
                metaPlant.setDeniedGlobal(metaObject.get("denied_global").getAsBoolean());
                metaPlant.setCanGrowOverTime(metaObject.get("grow_over_time").getAsBoolean());
                metaPlant.setCanGrowWithBonemeal(metaObject.get("grow_with_bonemeal").getAsBoolean());
                for (JsonElement biomeName : metaObject.get("deny").getAsJsonArray())
                    metaPlant.denyBiome(new ResourceLocation(biomeName.getAsString()));
                for (JsonElement biomeName : metaObject.get("valid").getAsJsonArray())
                    metaPlant.addValidBiome(new ResourceLocation(biomeName.getAsString()));
                boundItemObject = metaObject.get("bound_item").getAsJsonObject();
                boundItemName = boundItemObject.get("registry_name").getAsString();
                if (!boundItemName.isEmpty())
                    metaPlant.setBoundItem(new ResourceLocation(boundItemName), boundItemObject.get("meta").getAsInt());
            }
            DataManager.getServer(registryName).setMainMeta(plantObject.get("main_meta").getAsInt());
        }
    }

    public static void save() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();   
        JsonObject 
        dataObject = new JsonObject(),
        plantObject, metaObject, boundItemObject;
        JsonArray deniedArray, vailidArray;   
        dataObject.add("enabled", new JsonPrimitive(DataManager.isSettingsEnabled()));
        for (PlantData plantData : DataManager.getDataServer().values()) {
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
                metaObject.add("grow_over_time", new JsonPrimitive(metaPlant.canGrowOverTime()));
                metaObject.add("grow_with_bonemeal", new JsonPrimitive(metaPlant.canGrowWithBonemeal()));
                for (ResourceLocation b : metaPlant.getDeniedBiomes())
                    deniedArray.add(new JsonPrimitive(b.toString()));
                metaObject.add("deny", deniedArray);
                for (ResourceLocation b : metaPlant.getValidBiomes())
                    vailidArray.add(new JsonPrimitive(b.toString()));
                metaObject.add("valid", vailidArray);
                boundItemObject = new JsonObject();
                boundItemObject.add("registry_name", new JsonPrimitive(metaPlant.getBoundItemRegistryName() == null ? "" : metaPlant.getBoundItemRegistryName().toString()));
                boundItemObject.add("meta", new JsonPrimitive(metaPlant.getBoundItemMeta()));
                metaObject.add("bound_item", boundItemObject);
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

    public static void backup() {
        try {
            PBUtils.createExternalJsonFile(
                    CommonReference.getGameFolder() + "/config/plantbiomes/plants_settings_" + BACKUP_DATE_FORMAT.format(new Date()) + ".json", 
                    EnumConfigSettings.EXTERNAL_CONFIG.isEnabled() ? PBUtils.getExternalJsonData(EXT_DATA_FILE) : PBUtils.getInternalJsonData("assets/plantbiomes/plants_settings.json"));         
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
