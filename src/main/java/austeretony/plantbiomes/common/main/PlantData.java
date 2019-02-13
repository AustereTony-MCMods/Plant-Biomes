package austeretony.plantbiomes.common.main;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;

public class PlantData {

    public final ResourceLocation registryName;

    public final EnumPlantType enumType;

    private final Map<Integer, MetaPlant> data = new HashMap<Integer, MetaPlant>();

    private int mainMeta = -1;

    private boolean hasMainMeta;

    public PlantData(EnumPlantType enumType, ResourceLocation registryName) {
        this.enumType = enumType;
        this.registryName = registryName;
    }

    public boolean hasData() {
        return !this.data.isEmpty();
    }

    public Map<Integer, MetaPlant> getData() {
        return this.data;
    }

    public boolean hasMetaData(int meta) {
        return this.hasData() && this.data.containsKey(meta);
    }

    public MetaPlant getMeta(int meta) {
        return this.data.get(meta);
    }

    public void addMeta(int meta, MetaPlant metaPlant) {
        this.data.put(meta, metaPlant);
    }

    public void createMeta(int meta, String specialName, String unlocalizedName) {
        this.data.put(meta, new MetaPlant(meta, specialName, unlocalizedName));
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

    public void setMainMeta(int mainMeta) {
        this.mainMeta = mainMeta;
        this.hasMainMeta = mainMeta != -1;
    }

    public void resetMainMeta() {
        this.mainMeta = -1;
        this.hasMainMeta = false;
    }

    public boolean isDeniedBiomesExist(int meta) {
        return this.hasMainMeta ? this.data.get(this.mainMeta).isDeniedBiomesExist() : this.data.get(meta).isDeniedBiomesExist();
    }

    public Set<ResourceLocation> getDeniedBiomes(int meta) {
        return this.hasMainMeta ? this.data.get(this.mainMeta).getDeniedBiomes() : this.data.get(meta).getDeniedBiomes();
    }

    public boolean isDeniedBiome(int meta, ResourceLocation biomeRegistryName) {
        return this.hasMainMeta ? this.data.get(this.mainMeta).isDeniedBiome(biomeRegistryName) : this.data.get(meta).isDeniedBiome(biomeRegistryName);
    }

    public void denyBiome(int meta, ResourceLocation biomeRegistryName) {
        if (this.hasMainMeta)
            this.data.get(this.mainMeta).denyBiome(biomeRegistryName);
        else
            this.data.get(meta).denyBiome(biomeRegistryName);
    }

    public void allowBiome(int meta, ResourceLocation biomeRegistryName) {
        if (this.hasMainMeta)
            this.data.get(this.mainMeta).allowBiome(biomeRegistryName);
        else
            this.data.get(meta).allowBiome(biomeRegistryName);
    }

    public void clearDeniedBiomes(int meta) {
        if (this.hasMainMeta)
            this.data.get(this.mainMeta).clearDeniedBiomes();
        else
            this.data.get(meta).clearDeniedBiomes();
    }

    public boolean isDeniedGlobal(int meta) {
        return this.hasMainMeta ? this.data.get(this.mainMeta).isDeniedGlobal() : this.data.get(meta).isDeniedGlobal();
    }

    public void setDeniedGlobal(int meta, boolean flag) {
        if (this.hasMainMeta)
            this.data.get(this.mainMeta).setDeniedGlobal(flag);
        else
            this.data.get(meta).setDeniedGlobal(flag);
    }

    public boolean canGrowOverTime(int meta) {
        return this.hasMainMeta ? this.data.get(this.mainMeta).canGrowOverTime() : this.data.get(meta).canGrowOverTime();
    }

    public void setCanGrowOverTime(int meta, boolean flag) {
        if (this.hasMainMeta)
            this.data.get(this.mainMeta).setCanGrowOverTime(flag);
        else
            this.data.get(meta).setCanGrowOverTime(flag);
    }

    public boolean canGrowWithBonemeal(int meta) {
        return this.hasMainMeta ? this.data.get(this.mainMeta).canGrowWithBonemeal() : this.data.get(meta).canGrowWithBonemeal();
    }

    public void setCanGrowWithBonemeal(int meta, boolean flag) {
        if (this.hasMainMeta)
            this.data.get(this.mainMeta).setCanGrowWithBonemeal(flag);
        else
            this.data.get(meta).setCanGrowWithBonemeal(flag);
    }

    public boolean isValidBiomesExist(int meta) {
        return this.hasMainMeta ? this.data.get(this.mainMeta).isValidBiomesExist() : this.data.get(meta).isValidBiomesExist();
    }

    public Set<ResourceLocation> getValidBiomes(int meta) {
        return this.hasMainMeta ? this.data.get(this.mainMeta).getValidBiomes() : this.data.get(meta).getValidBiomes();
    }

    public boolean isValidBiome(int meta, ResourceLocation biomeRegistryName) {
        return this.hasMainMeta ? this.data.get(this.mainMeta).isValidBiome(biomeRegistryName) : this.data.get(meta).isValidBiome(biomeRegistryName);
    }

    public void addValidBiome(int meta, ResourceLocation biomeRegistryName) {
        if (this.hasMainMeta)
            this.data.get(this.mainMeta).addValidBiome(biomeRegistryName);
        else
            this.data.get(meta).addValidBiome(biomeRegistryName);
    }

    public void removeValidBiome(int meta, ResourceLocation biomeRegistryName) {
        if (this.hasMainMeta)
            this.data.get(this.mainMeta).removeValidBiome(biomeRegistryName);
        else
            this.data.get(meta).removeValidBiome(biomeRegistryName);
    }

    public void clearValidBiomes(int meta) {
        if (this.hasMainMeta)
            this.data.get(this.mainMeta).clearValidBiomes();
        else
            this.data.get(meta).clearValidBiomes();
    }

    public boolean hasBoundItem(int meta) {
        return this.hasMainMeta ? this.data.get(this.mainMeta).hasBoundItem() : this.data.get(meta).hasBoundItem();
    }

    public ResourceLocation getBoundItemRegistryName(int meta) {
        return this.hasMainMeta ? this.data.get(this.mainMeta).getBoundItemRegistryName() : this.data.get(meta).getBoundItemRegistryName();
    }

    public int getBoundItemMeta(int meta) {
        return this.hasMainMeta ? this.data.get(this.mainMeta).getBoundItemMeta() : this.data.get(meta).getBoundItemMeta();
    }

    public void setBoundItem(int meta, ResourceLocation boundItemRegistryName, int itemMeta) {
        if (this.hasMainMeta)
            this.data.get(this.mainMeta).setBoundItem(boundItemRegistryName, itemMeta);
        else
            this.data.get(meta).setBoundItem(boundItemRegistryName, itemMeta);
    }

    public void resetBoundItem(int meta) {
        if (this.hasMainMeta)
            this.data.get(this.mainMeta).resetBoundItem();
        else
            this.data.get(meta).resetBoundItem();
    }

    private boolean verifyMetaGrowOverTime(int meta, ResourceLocation biomeRegistryName) {
        return !this.data.containsKey(meta) || this.data.get(meta).canGrowOverTime(biomeRegistryName);
    }

    public boolean canGrowOverTime(int meta, ResourceLocation biomeRegistryName) {
        return !DataManager.isSettingsEnabled() || (this.hasMainMeta ? this.verifyMetaGrowOverTime(this.mainMeta, biomeRegistryName) : this.verifyMetaGrowOverTime(meta, biomeRegistryName));
    }

    public boolean canGrowOverTime(Block block, IBlockState blockState, ResourceLocation biomeRegistryName) {
        return this.canGrowOverTime(block.getMetaFromState(blockState), biomeRegistryName);
    }

    private boolean verifyMetaGrowBonemeal(int meta, ResourceLocation biomeRegistryName) {
        return !this.data.containsKey(meta) || this.data.get(meta).canGrowWithBonemeal(biomeRegistryName);
    }

    public boolean canGrowWithBonemeal(int meta, ResourceLocation biomeRegistryName) {
        return !DataManager.isSettingsEnabled() || (this.hasMainMeta ? this.verifyMetaGrowBonemeal(this.mainMeta, biomeRegistryName) : this.verifyMetaGrowBonemeal(meta, biomeRegistryName));
    }

    public boolean canGrowWithBonemeal(Block block, IBlockState blockState, ResourceLocation biomeRegistryName) {
        return this.canGrowWithBonemeal(block.getMetaFromState(blockState), biomeRegistryName);
    }
}
