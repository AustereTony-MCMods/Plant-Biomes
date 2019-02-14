package austeretony.plantbiomes.common.main;

import java.util.HashMap;
import java.util.Map;

import austeretony.plantbiomes.client.render.OverlayRenderer;
import austeretony.plantbiomes.common.config.ConfigLoader;
import austeretony.plantbiomes.common.network.client.CPSetOverlayStatus;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DataManager {

    private static Map<ResourceLocation, PlantData> plantsDataClient, plantsDataServer;

    private static Map<String, SpecialPlantsContainer> specialPlantsClient, specialPlantsServer;

    public static LatestPlant latestPlantServer;

    private static boolean 
    isSettingsEnabled = true,
    isConfigModeEnabled,
    hasPlantsDataServer,
    hasPlantsDataClient,
    hasSpecialPlantsServer,
    hasSpecialPlantsClient;

    @SideOnly(Side.CLIENT)
    private static boolean 
    settingsEnabledClient,
    renderOverlayClient,
    settingsTooltipsClient,
    tilesOverlayClient;

    @SideOnly(Side.CLIENT)
    private static Map<ResourceLocation, BoundItemStack> boundItems;

    @SideOnly(Side.CLIENT)
    private static Map<ResourceLocation, String> biomeNames;

    public static void initServerData() {
        plantsDataServer = new HashMap<ResourceLocation, PlantData>();
        checkForSpecialPlantsServer();
        ConfigLoader.loadOverallSettings();
    }

    public static void checkForSpecialPlantsServer() {
        for (EnumPlantType enumPlant : EnumPlantType.values()) {
            if (enumPlant != EnumPlantType.STANDARD && Loader.isModLoaded(enumPlant.modId)) {
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
        for (EnumPlantType enumPlant : EnumPlantType.values()) {
            if (enumPlant != EnumPlantType.STANDARD && Loader.isModLoaded(enumPlant.modId)) {
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
        return existServer(latestPlantServer.registryName);
    }

    public static boolean existMetaLatest() {
        return existServer(latestPlantServer.registryName) && getServer(latestPlantServer.registryName).hasMetaData(latestPlantServer.meta);
    }

    public static boolean existSpecial(String specialName, EnumPlantType enumPlant) {
        return specialPlantsServer.containsKey(enumPlant.type) && specialPlantsServer.get(enumPlant.type).contains(specialName);
    }

    public static void createMetaServer(EnumPlantType enumType, ResourceLocation registryName, int meta, String specialName, String unlocalizedName) {   
        if (!plantsDataServer.containsKey(registryName)) {
            PlantData plantData = new PlantData(enumType, registryName);
            plantData.createMeta(meta, specialName, unlocalizedName);
            plantsDataServer.put(registryName, plantData);  
            if (enumType != EnumPlantType.STANDARD)
                specialPlantsServer.get(enumType.type).put(specialName, registryName);
        } else {
            getServer(registryName).createMeta(meta, specialName, unlocalizedName); 
        }
        hasPlantsDataServer = true;
    }

    public static void createMetaLatest() {
        createMetaServer(
                latestPlantServer.enumType,
                latestPlantServer.registryName,
                latestPlantServer.meta,
                latestPlantServer.specialName,
                latestPlantServer.unlocalizedName);
    }

    @SideOnly(Side.CLIENT)
    public static void createMetaClient(EnumPlantType enumType, ResourceLocation registryName, int meta, String specialName, String unlocalizedName) {   
        if (!plantsDataClient.containsKey(registryName)) {
            PlantData plantData = new PlantData(enumType, registryName);
            plantData.createMeta(meta, specialName, unlocalizedName);
            plantsDataClient.put(registryName, plantData);  
            if (enumType != EnumPlantType.STANDARD)
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
        return getServer(latestPlantServer.registryName);
    }

    public static PlantData getSpecial(String specialName, EnumPlantType enumPlant) {
        return getServer(specialPlantsServer.get(enumPlant.type).get(specialName));
    }

    public static MetaPlant getMetaLatest() {
        return getServer(latestPlantServer.registryName).getMeta(latestPlantServer.meta);
    }

    public static void removeServer(ResourceLocation registryName) {
        PlantData plantData = plantsDataServer.remove(registryName);
        hasPlantsDataServer = plantsDataServer.isEmpty();
    }

    public static void removeMetaLatest() {
        getServer(latestPlantServer.registryName).removeMeta(latestPlantServer.meta);
        if (latestPlantServer.enumType != EnumPlantType.STANDARD && hasSpecialPlantsServer)
            specialPlantsServer.get(latestPlantServer.enumType.type).remove(latestPlantServer.specialName);
        if (!getServer(latestPlantServer.registryName).hasData())
            removeServer(latestPlantServer.registryName);
    }

    @SideOnly(Side.CLIENT)
    public static void removeClient(ResourceLocation registryName) {
        PlantData plantData = plantsDataClient.remove(registryName);
        hasPlantsDataClient = plantsDataClient.isEmpty();
    }

    @SideOnly(Side.CLIENT)
    public static void removeMetaClient(ResourceLocation registryName, int meta) {
        PlantData plantData = getClient(registryName);
        if (plantData.enumType != EnumPlantType.STANDARD && hasSpecialPlantsClient)
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
    public static void initBoundItemsMapClient() {
        boundItems = new HashMap<ResourceLocation, BoundItemStack>();
        biomeNames = new HashMap<ResourceLocation, String>();
        Biome biome;
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            biome = Biome.getBiome(i);
            if (biome == null) break;
            biomeNames.put(biome.getRegistryName(), biome.getBiomeName());
        }
    }

    @SideOnly(Side.CLIENT)
    public static Map<ResourceLocation, BoundItemStack> getBoundItemsMapClient() {
        return boundItems;
    }

    @SideOnly(Side.CLIENT)
    public static Map<ResourceLocation, String> getBiomeNamesMapClient() {
        return biomeNames;
    }

    @SideOnly(Side.CLIENT)
    public static void createBoundItemClient(ResourceLocation plantRegistryName, int meta) {
        PlantData plantData = getClient(plantRegistryName);
        if (!boundItems.containsKey(plantData.getBoundItemRegistryName(meta))) {
            BoundItemStack boundStack = new BoundItemStack(plantData.getBoundItemRegistryName(meta));
            boundStack.createMeta(plantData.getBoundItemMeta(meta), plantRegistryName, meta);
            boundItems.put(plantData.getBoundItemRegistryName(meta), boundStack);
        } else {
            boundItems.get(plantData.getBoundItemRegistryName(meta)).createMeta(plantData.getBoundItemMeta(meta), plantRegistryName, meta);
        }
    }

    @SideOnly(Side.CLIENT)
    public static void clearBoundItemsClient() {
        boundItems.clear();
    }
    
    @SideOnly(Side.CLIENT)
    public static boolean isSettingsEnabledClient() {
        return settingsEnabledClient;
    }

    @SideOnly(Side.CLIENT)
    public static void setSettingsEnabledClient(boolean flag) {
        settingsEnabledClient = flag;
    }

    @SideOnly(Side.CLIENT)
    public static boolean shouldRenderOverlayClient() {
        return renderOverlayClient;
    }

    @SideOnly(Side.CLIENT)
    public static void setOverlayStatusClient(CPSetOverlayStatus.EnumStatus enumStatus) {
        renderOverlayClient = enumStatus == CPSetOverlayStatus.EnumStatus.ENABLED;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isTilesOverlayAllowedClient() {
        return tilesOverlayClient;
    }

    @SideOnly(Side.CLIENT)
    public static void setTilesOverlayAllowedClient(boolean flag) {
        tilesOverlayClient = flag;
        if (tilesOverlayClient)
            OverlayRenderer.TILES.clear();//TODO Need better way to clear cached data.
    }

    @SideOnly(Side.CLIENT)
    public static boolean isSettingsTooltipsAllowedClient() {
        return settingsTooltipsClient;
    }

    @SideOnly(Side.CLIENT)
    public static void setSettingsTooltipsAllowedClient(boolean flag) {
        settingsTooltipsClient = flag;
        if (flag)
            initBoundItemsMapClient();
    }

    public static boolean isSettingsEnabled() {
        return isSettingsEnabled;
    }

    public static void setSettingsEnabled(boolean flag) {
        isSettingsEnabled = flag;
    }

    public static boolean isConfigModeEnabled() {           
        return isConfigModeEnabled;
    }

    public static void setConfigModeEnabled(boolean isEnabled) {            
        isConfigModeEnabled = isEnabled;
    }
}
