package austeretony.plantbiomes.common.main;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.util.ResourceLocation;

public class MetaPlant {

    public final int meta;

    public final String specialName, unlocalizedName;

    private final Set<ResourceLocation> 
    denied = new HashSet<ResourceLocation>(),
    valid = new HashSet<ResourceLocation>();

    private boolean 
    deniedGlobal,
    hasDenied,
    hasValid;

    public MetaPlant(int meta, String specialName, String unlocalizedName) {
        this.meta = meta;
        this.specialName = specialName;
        this.unlocalizedName = unlocalizedName;
    }

    public Set<ResourceLocation> getDeniedBiomes() {
        return this.denied;
    }

    public boolean isDeniedBiomesExist() {
        return this.hasDenied;
    }
    
    public boolean isDeniedBiome(ResourceLocation biomeRegistryName) {
        return this.denied.contains(biomeRegistryName);
    }

    public void denyBiome(ResourceLocation biomeRegistryName) {
        this.denied.add(biomeRegistryName);
        this.hasDenied = true;
    }

    public void allowBiome(ResourceLocation biomeRegistryName) {
        this.denied.remove(biomeRegistryName);
        this.hasDenied = !this.denied.isEmpty();
    }

    public void clearDeniedBiomes() {
        this.denied.clear();
        this.hasDenied = false;
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
        return this.hasValid;
    }

    public void addValidBiome(ResourceLocation biomeRegistryName) {
        this.valid.add(biomeRegistryName);
        this.hasValid = true;
    }

    public void removeValidBiome(ResourceLocation biomeRegistryName) {
        this.valid.remove(biomeRegistryName);
        this.hasValid = !this.valid.isEmpty();
    }

    public void clearValidBiomes() {
        this.valid.clear();
        this.hasValid = false;
    }

    public boolean isPermittedBiome(ResourceLocation biomeRegistryName) {
        return this.hasValid ? this.valid.contains(biomeRegistryName) : !this.deniedGlobal && (!this.hasDenied || !this.denied.contains(biomeRegistryName));
    }
}
