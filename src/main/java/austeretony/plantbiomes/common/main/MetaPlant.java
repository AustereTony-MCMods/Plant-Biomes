package austeretony.plantbiomes.common.main;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MetaPlant {

    public final int meta;

    public final String unlocalizedName;

    private final Set<ResourceLocation> 
    denied = new HashSet<ResourceLocation>(),
    valid = new HashSet<ResourceLocation>();

    private boolean deniedGlobal, 
    validEmpty = true;

    public MetaPlant(int meta, String unlocalizedName) {
        this.meta = meta;
        this.unlocalizedName = unlocalizedName;
    }

    public Set<ResourceLocation> getDeniedBiomes() {
        return this.denied;
    }

    public boolean isDeniedBiome(ResourceLocation biomeRegistryName) {
        return this.denied.contains(biomeRegistryName);
    }

    public void denyBiome(ResourceLocation biomeRegistryName) {
        this.denied.add(biomeRegistryName);
    }

    public void allowBiome(ResourceLocation biomeRegistryName) {
        this.denied.remove(biomeRegistryName);
    }

    public void clearDeniedBiomes() {
        this.denied.clear();
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
    }

    public boolean isPermittedBiome(ResourceLocation biomeRegistryName) {
        return DataLoader.isSettingsEnabled() ? (this.validEmpty ? !this.deniedGlobal && !this.denied.contains(biomeRegistryName) : this.valid.contains(biomeRegistryName)) : true;
    }

    public boolean isPermittedBiome(World world, BlockPos pos) {
        return isPermittedBiome(DataLoader.getBiomeRegistryName(world, pos));
    }
}
