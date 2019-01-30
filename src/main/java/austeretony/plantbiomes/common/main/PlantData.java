package austeretony.plantbiomes.common.main;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PlantData {

    public final ResourceLocation registryName;

    private final Map<Integer, MetaPlant> data = new HashMap<Integer, MetaPlant>();

    private int mainMeta = -1;

    private boolean hasMainMeta;

    public PlantData(ResourceLocation registryName) {
        this.registryName = registryName;
    }

    public boolean hasData() {
        return !this.data.isEmpty();
    }

    public Map<Integer, MetaPlant> getData() {
        return this.data;
    }

    public boolean hasMetaData(int meta) {
        return this.data.containsKey(meta);
    }

    public MetaPlant getMeta(int meta) {
        return this.data.get(meta);
    }

    public void addMeta(int meta, MetaPlant metaPlant) {
        this.data.put(meta, metaPlant);
    }

    public void createMeta(int meta, String unlocalizedName) {
        this.data.put(meta, new MetaPlant(meta, unlocalizedName));
    }

    public void removeMeta(int meta) {
        this.data.remove(meta);
    }

    public boolean hasMainMeta() {
        return this.hasMainMeta;
    }

    public int getMainMeta() {
        return this.mainMeta;
    }
    
    public MetaPlant getMainMetaPlant() {
        return this.data.get(this.mainMeta);
    }

    public void setMainMeta(int customizedMeta) {
        this.mainMeta = customizedMeta;
        this.hasMainMeta = customizedMeta != -1;
    }

    public void resetMainMeta() {
        this.mainMeta = -1;
        this.hasMainMeta = false;
    }

    private boolean verifyMeta(int meta, ResourceLocation biomeRegistryName) {
        return !this.data.containsKey(meta) || (this.data.containsKey(meta) && this.data.get(meta).isPermittedBiome(biomeRegistryName));
    }

    public boolean isPermittedBiome(int meta, ResourceLocation biomeRegistryName) {
        return this.hasMainMeta ? verifyMeta(this.mainMeta, biomeRegistryName) : verifyMeta(meta, biomeRegistryName);
    }

    public boolean isPermittedBiome(Block block, IBlockState blockState, ResourceLocation biomeRegistryName) {
        return isPermittedBiome(block.getMetaFromState(blockState), biomeRegistryName);
    }

    public boolean isPermittedBiome(Block block, IBlockState blockState, World world, BlockPos pos) {
        return isPermittedBiome(block, blockState, DataLoader.getBiomeRegistryName(world, pos));
    }
}
