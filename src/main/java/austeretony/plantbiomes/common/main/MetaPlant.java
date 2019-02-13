package austeretony.plantbiomes.common.main;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.util.ResourceLocation;

public class MetaPlant {

    public final int meta;

    public final String specialName, unlocalizedName;

    private ResourceLocation boundItemName;

    private int boundItemMeta;

    private final Set<ResourceLocation> 
    denied = new HashSet<ResourceLocation>(),
    valid = new HashSet<ResourceLocation>();

    private boolean 
    deniedGlobal,
    canGrowOverTime,
    canGrowWithBonemeal,
    hasDeniedBiomes,
    hasValidBiomes,
    hasBoundItem;

    public MetaPlant(int meta, String specialName, String unlocalizedName) {
        this.meta = meta;
        this.specialName = specialName;
        this.unlocalizedName = unlocalizedName;
    }

    public boolean isDeniedBiomesExist() {
        return this.hasDeniedBiomes;
    }
    
    public Set<ResourceLocation> getDeniedBiomes() {
        return this.denied;
    }

    public boolean isDeniedBiome(ResourceLocation biomeRegistryName) {
        return this.denied.contains(biomeRegistryName);
    }

    public void denyBiome(ResourceLocation biomeRegistryName) {
        this.denied.add(biomeRegistryName);
        this.hasDeniedBiomes = true;
    }

    public void allowBiome(ResourceLocation biomeRegistryName) {
        this.denied.remove(biomeRegistryName);
        this.hasDeniedBiomes = !this.denied.isEmpty();
    }

    public void clearDeniedBiomes() {
        this.denied.clear();
        this.hasDeniedBiomes = false;
    }

    public boolean isDeniedGlobal() {
        return this.deniedGlobal;
    }

    public void setDeniedGlobal(boolean flag) {
        this.deniedGlobal = flag;
    }

    public boolean canGrowOverTime() {
        return this.canGrowOverTime;
    }

    public void setCanGrowOverTime(boolean flag) {
        this.canGrowOverTime = flag;
    }

    public boolean canGrowWithBonemeal() {
        return this.canGrowWithBonemeal;
    }

    public void setCanGrowWithBonemeal(boolean flag) {
        this.canGrowWithBonemeal = flag;
    }

    public boolean isValidBiomesExist() {
        return this.hasValidBiomes;
    }

    public Set<ResourceLocation> getValidBiomes() {
        return this.valid;
    }

    public boolean isValidBiome(ResourceLocation biomeRegistryName) {
        return this.valid.contains(biomeRegistryName);
    }

    public void addValidBiome(ResourceLocation biomeRegistryName) {
        this.valid.add(biomeRegistryName);
        this.hasValidBiomes = true;
    }

    public void removeValidBiome(ResourceLocation biomeRegistryName) {
        this.valid.remove(biomeRegistryName);
        this.hasValidBiomes = !this.valid.isEmpty();
    }

    public void clearValidBiomes() {
        this.valid.clear();
        this.hasValidBiomes = false;
    }
    
    public boolean hasBoundItem() {
        return this.hasBoundItem;
    }

    public ResourceLocation getBoundItemRegistryName() {
        return this.boundItemName;
    }

    public int getBoundItemMeta() {
        return this.boundItemMeta;
    }

    public void setBoundItem(ResourceLocation boundItemRegistryName, int meta) {
        this.boundItemName = boundItemRegistryName;
        this.boundItemMeta = meta;
        this.hasBoundItem = true;
    }

    public void resetBoundItem() {
        this.boundItemName = null;
        this.boundItemMeta = 0;
        this.hasBoundItem = false;
    }

    public boolean canGrowOverTime(ResourceLocation biomeRegistryName) {
        return this.canGrowOverTime || (this.hasValidBiomes ? this.valid.contains(biomeRegistryName) : !this.deniedGlobal && (!this.hasDeniedBiomes || !this.denied.contains(biomeRegistryName)));
    }

    public boolean canGrowWithBonemeal(ResourceLocation biomeRegistryName) {
        return this.canGrowWithBonemeal || (this.hasValidBiomes ? this.valid.contains(biomeRegistryName) : !this.deniedGlobal && (!this.hasDeniedBiomes || !this.denied.contains(biomeRegistryName)));
    }
}
