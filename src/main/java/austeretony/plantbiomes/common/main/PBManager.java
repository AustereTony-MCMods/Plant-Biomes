package austeretony.plantbiomes.common.main;

import java.util.HashMap;
import java.util.Map;

import austeretony.plantbiomes.client.render.OverlayRenderer;
import austeretony.plantbiomes.common.network.client.CPSetOverlayStatus;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PBManager {

    private static Map<ResourceLocation, PlantData> plantsDataClient, plantsDataServer;

    public static Map<String, SpecialPlantsContainer> specialPlantsClient, specialPlantsServer;

    public static LatestPlant latestPlant;

    private static boolean 
    isUpdateMessagesEnabled, 
    useExternalConfig, 
    isAutosaveEnabled,
    isOverlayEnabled,
    isTilesOverlayEnabled,
    isSettingsEnabled = true,
    isConfigModeEnabled,
    hasPlantsDataServer,
    hasPlantsDataClient,
    hasSpecialPlantsServer,
    hasSpecialPlantsClient;

    @SideOnly(Side.CLIENT)
    private static boolean 
    overlayStatus,
    isTilesAllowed;

    public static void initServerData() {
        plantsDataServer = new HashMap<ResourceLocation, PlantData>();
        checkForSpecialPlantsServer();
        PBLoader.load();
    }

    public static void checkForSpecialPlantsServer() {
        for (EnumPBPlantType enumPlant : EnumPBPlantType.values()) {
            if (enumPlant != EnumPBPlantType.STANDARD && Loader.isModLoaded(enumPlant.domain)) {
                if (specialPlantsServer == null)
                    specialPlantsServer = new HashMap<String, SpecialPlantsContainer>();
                specialPlantsServer.put(enumPlant.type, new SpecialPlantsContainer(enumPlant));
                hasSpecialPlantsServer = true;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static void initClientData() {
        plantsDataClient = new HashMap<ResourceLocation, PlantData>();
        checkForSpecialPlantsClient();
    }

    @SideOnly(Side.CLIENT)
    public static void checkForSpecialPlantsClient() {
        for (EnumPBPlantType enumPlant : EnumPBPlantType.values()) {
            if (enumPlant != EnumPBPlantType.STANDARD && Loader.isModLoaded(enumPlant.domain)) {
                if (specialPlantsClient == null)
                    specialPlantsClient = new HashMap<String, SpecialPlantsContainer>();
                specialPlantsClient.put(enumPlant.type, new SpecialPlantsContainer(enumPlant));
                hasSpecialPlantsClient = true;
            }
        }
    }

    public static boolean shouldCheckSpecialPlantsServer() {
        return hasSpecialPlantsServer;
    }

    @SideOnly(Side.CLIENT)
    public static boolean shouldCheckSpecialPlantsClient() {
        return hasSpecialPlantsClient;
    }

    public static Map<ResourceLocation, PlantData> getDataServer() {
        return plantsDataServer;
    }

    @SideOnly(Side.CLIENT)
    public static Map<ResourceLocation, PlantData> getDataClient() {
        return plantsDataClient;
    }

    public static boolean existServer(ResourceLocation registryName) {
        return hasPlantsDataServer && plantsDataServer.containsKey(registryName);
    }

    @SideOnly(Side.CLIENT)
    public static boolean existClient(ResourceLocation registryName) {
        return hasPlantsDataClient && plantsDataClient.containsKey(registryName);
    }

    public static boolean exist(Block block) {
        return existServer(block.getRegistryName());
    }

    public static boolean existLatest() {
        return existServer(latestPlant.registryName);
    }

    public static boolean existMetaLatest() {
        return existServer(latestPlant.registryName) && getServer(latestPlant.registryName).hasMetaData(latestPlant.meta);
    }

    public static boolean existSpecial(String specialName, EnumPBPlantType enumPlant) {
        return specialPlantsServer.containsKey(enumPlant.type) && specialPlantsServer.get(enumPlant.type).contains(specialName);
    }

    public static void createMetaServer(EnumPBPlantType enumType, ResourceLocation registryName, int meta, String specialName, String unlocalizedName) {   
        if (!plantsDataServer.containsKey(registryName)) {
            PlantData plantData = new PlantData(enumType, registryName);
            plantData.createMeta(meta, specialName, unlocalizedName);
            plantsDataServer.put(registryName, plantData);  
            if (enumType != EnumPBPlantType.STANDARD)
                specialPlantsServer.get(enumType.type).put(specialName, registryName);
        } else {
            getServer(registryName).createMeta(meta, specialName, unlocalizedName); 
        }
        hasPlantsDataServer = true;
    }

    public static void createMetaLatest() {
        createMetaServer(
                latestPlant.enumType,
                latestPlant.registryName,
                latestPlant.meta,
                latestPlant.specialName,
                latestPlant.unlocalizedName);
    }

    @SideOnly(Side.CLIENT)
    public static void createMetaClient(EnumPBPlantType enumType, ResourceLocation registryName, int meta, String specialName, String unlocalizedName) {   
        if (!plantsDataClient.containsKey(registryName)) {
            PlantData plantData = new PlantData(enumType, registryName);
            plantData.createMeta(meta, specialName, unlocalizedName);
            plantsDataClient.put(registryName, plantData);  
            if (enumType != EnumPBPlantType.STANDARD)
                specialPlantsClient.get(enumType.type).put(specialName, registryName);
        } else {
            getClient(registryName).createMeta(meta, specialName, unlocalizedName); 
        }
        hasPlantsDataClient = true;
    }

    public static void clearDataServer() {
        plantsDataServer.clear();
        hasPlantsDataServer = false;
        if (hasSpecialPlantsServer)
            for (SpecialPlantsContainer container : specialPlantsServer.values())
                container.clear();
    }

    @SideOnly(Side.CLIENT)
    public static void clearDataClient() {
        plantsDataClient.clear();
        hasPlantsDataClient = false;
        if (hasSpecialPlantsClient)
            for (SpecialPlantsContainer container : specialPlantsClient.values())
                container.clear();
    }

    public static PlantData getServer(ResourceLocation registryName) {
        return plantsDataServer.get(registryName);
    }

    @SideOnly(Side.CLIENT)
    public static PlantData getClient(ResourceLocation registryName) {
        return plantsDataClient.get(registryName);
    }

    public static PlantData get(Block block) {
        return getServer(block.getRegistryName());
    }

    public static PlantData getLatest() {
        return getServer(latestPlant.registryName);
    }

    public static PlantData getSpecial(String specialName, EnumPBPlantType enumPlant) {
        return getServer(specialPlantsServer.get(enumPlant.type).get(specialName));
    }

    public static MetaPlant getMetaLatest() {
        return getServer(latestPlant.registryName).getMeta(latestPlant.meta);
    }

    public static void removeServer(ResourceLocation registryName) {
        PlantData plantData = plantsDataServer.remove(registryName);
        hasPlantsDataServer = plantsDataServer.isEmpty();
    }

    public static void removeMetaLatest() {
        getServer(latestPlant.registryName).removeMeta(latestPlant.meta);
        if (latestPlant.enumType != EnumPBPlantType.STANDARD && hasSpecialPlantsServer)
            specialPlantsServer.get(latestPlant.enumType.type).remove(latestPlant.specialName);
        if (!getServer(latestPlant.registryName).hasData())
            removeServer(latestPlant.registryName);
    }

    @SideOnly(Side.CLIENT)
    public static void removeClient(ResourceLocation registryName) {
        PlantData plantData = plantsDataClient.remove(registryName);
        hasPlantsDataClient = plantsDataClient.isEmpty();
    }

    @SideOnly(Side.CLIENT)
    public static void removeMetaClient(ResourceLocation registryName, int meta) {
        PlantData plantData = getClient(registryName);
        if (plantData.enumType != EnumPBPlantType.STANDARD && hasSpecialPlantsClient)
            specialPlantsClient.get(plantData.enumType.type).remove(plantData.getMeta(meta).specialName);
        plantData.removeMeta(meta);
        if (!getClient(registryName).hasData())
            removeClient(registryName);
    }

    public static ResourceLocation getBiomeRegistryName(World world, BlockPos pos) {
        return world.getBiome(pos).getRegistryName();
    }

    public static String createDisplayKey(ResourceLocation registryName, int meta) {
        return registryName.toString() + "-" + meta;
    }

    @SideOnly(Side.CLIENT)
    public static boolean shouldRenderOverlayClient() {
        return overlayStatus;
    }

    @SideOnly(Side.CLIENT)
    public static void setOverlayStatusClient(CPSetOverlayStatus.EnumStatus enumStatus) {
        overlayStatus = enumStatus == CPSetOverlayStatus.EnumStatus.ENABLED;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isTilesAllowedClient() {
        return isTilesAllowed;
    }

    @SideOnly(Side.CLIENT)
    public static void setTilesAllowedClient(boolean flag) {
        isTilesAllowed = flag;
        if (isTilesAllowed)
            OverlayRenderer.SPECIAL_NAMES.clear();//TODO Need better way to clear cached data.
    }

    public static boolean isSettingsEnabled() {
        return isSettingsEnabled;
    }

    public static void setSettingsEnabled(boolean flag) {
        isSettingsEnabled = flag;
    }

    public static boolean isUpdateMessagesEnabled() {               
        return isUpdateMessagesEnabled;
    }

    public static void setUpdateMessagesEnabled(boolean flag) {
        isUpdateMessagesEnabled = flag;
    }

    public static boolean isExternalConfigEnabled() {               
        return useExternalConfig;
    }

    public static void setExternalConfigEnabled(boolean flag) {
        useExternalConfig = flag;
    }

    public static boolean isAutosaveEnabled() {               
        return isAutosaveEnabled;
    }

    public static void setAutosaveEnabled(boolean flag) {
        isAutosaveEnabled = flag;
    }

    public static boolean isOverlayEnabled() {               
        return isOverlayEnabled;
    }

    public static void setOverlayEnabled(boolean flag) {
        isOverlayEnabled = flag;
    }

    public static boolean isTilesOverlayEnabled() {               
        return isTilesOverlayEnabled;
    }

    public static void setTilesOverlayEnabled(boolean flag) {
        isTilesOverlayEnabled = flag;
    }

    public static boolean isConfigModeEnabled() {           
        return isConfigModeEnabled;
    }

    public static void setConfigModeEnabled(boolean isEnabled) {            
        isConfigModeEnabled = isEnabled;
    }
}
