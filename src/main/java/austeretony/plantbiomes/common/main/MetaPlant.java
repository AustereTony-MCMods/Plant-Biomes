package austeretony.plantbiomes.common.main;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.util.ResourceLocation;

public class MetaPlant {

    public final int meta;

    public final String unlocalizedName;

    private final Set<ResourceLocation> 
    denied = new HashSet<ResourceLocation>(),
    valid = new HashSet<ResourceLocation>();

    private boolean deniedGlobal,
    deniedEmpty = true,
    validEmpty = true;

    public MetaPlant(int meta, String unlocalizedName) {
        this.meta = meta;
        this.unlocalizedName = unlocalizedName;
    }

    public Set<ResourceLocation> getDeniedBiomes() {
        return this.denied;
    }

    public boolean isDeniedBiomesExist() {
        return !this.deniedEmpty;
    }

    public boolean isDeniedBiome(ResourceLocation biomeRegistryName) {
        return this.denied.contains(biomeRegistryName);
    }

    public void denyBiome(ResourceLocation biomeRegistryName) {
        this.denied.add(biomeRegistryName);
        this.deniedEmpty = false;
    }

    public void allowBiome(ResourceLocation biomeRegistryName) {
        this.denied.remove(biomeRegistryName);
        this.deniedEmpty = this.denied.isEmpty();
    }

    public void clearDeniedBiomes() {
        this.denied.clear();
        this.deniedEmpty = true;
    }

    public boolean isDeniedGlobal() {
        return this.deniedGlobal;
    }

    public void denyGlobal() {
        this.deniedGlobal = true;
    }

    public void allowGlobal() {
        this.deniedGlobal = false;
    }

    public Set<ResourceLocation> getValidBiomes() {
        return this.valid;
    }

    public boolean isValidBiome(ResourceLocation biomeRegistryName) {
        return this.valid.contains(biomeRegistryName);
    }

    public boolean isValidBiomesExist() {
        return !this.validEmpty;
    }

    public void addValidBiome(ResourceLocation biomeRegistryName) {
        this.valid.add(biomeRegistryName);
        this.validEmpty = false;
    }

    public void removeValidBiome(ResourceLocation biomeRegistryName) {
        this.valid.remove(biomeRegistryName);
        this.validEmpty = this.valid.isEmpty();
    }

    public void clearValidBiomes() {
        this.valid.clear();
        this.validEmpty = true;
    }

    public boolean isPermittedBiome(ResourceLocation biomeRegistryName) {
        return this.validEmpty ? !this.deniedGlobal && (this.deniedEmpty || !this.denied.contains(biomeRegistryName)) : this.valid.contains(biomeRegistryName);
    }
}
