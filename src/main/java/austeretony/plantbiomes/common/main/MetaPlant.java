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

    public void allowBiome(ResourceLocation biomeRegistryName) {
        this.denied.remove(biomeRegistryName);
    }

    public boolean isValidBiome(ResourceLocation biomeRegistryName) {
        return !this.denied.contains(biomeRegistryName);
    }

    public boolean isValidBiome(World world, BlockPos pos) {
        return !this.denied.contains(PBDataLoader.getBiomeRegistryName(world, pos));
    }
}
