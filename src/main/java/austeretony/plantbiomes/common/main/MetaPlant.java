package austeretony.plantbiomes.common.main;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MetaPlant {

    public final int meta;

    public final String unlocalizedName;

    private final Set<ResourceLocation> denied = new HashSet<ResourceLocation>();

    private boolean deniedGlobal;

    public MetaPlant(int meta, String unlocalizedName) {
        this.meta = meta;
        this.unlocalizedName = unlocalizedName;
    }

    public boolean hasDeniedBiomes() {
        return !this.denied.isEmpty();
    }

    public Set<ResourceLocation> getDeniedBiomes() {
        return this.denied;
    }

    public void denyBiome(ResourceLocation biomeRegistryName) {
        this.denied.add(biomeRegistryName);
    }

    public void denyGlobal() {
        this.deniedGlobal = true;
    }

    public void allowBiome(ResourceLocation biomeRegistryName) {
        this.denied.remove(biomeRegistryName);
    }

    public void allowGlobal() {
        this.deniedGlobal = false;
    }

    public boolean isDeniedGlobal() {
        return this.deniedGlobal;
    }

    public boolean isValidBiome(ResourceLocation biomeRegistryName) {
        return DataLoader.isSettingsDisabled() ? true : !this.deniedGlobal && !this.denied.contains(biomeRegistryName);
    }

    public boolean isValidBiome(World world, BlockPos pos) {
        return DataLoader.isSettingsDisabled() ? true : !this.deniedGlobal && !this.denied.contains(DataLoader.getBiomeRegistryName(world, pos));
    }
}
