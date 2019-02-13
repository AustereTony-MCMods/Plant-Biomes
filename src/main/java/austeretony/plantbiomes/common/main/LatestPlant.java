package austeretony.plantbiomes.common.main;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class LatestPlant {

    public final EnumPlantType enumType;

    public final ResourceLocation registryName, biomeRegistryName;

    public final String specialName, unlocalizedName;

    public final int meta;

    public final BlockPos blockPos;

    public LatestPlant(EnumPlantType enumType, ResourceLocation registryName, int meta, String specialName, String unlocalizedName, ResourceLocation biomeRegistryName, BlockPos blockPos) {
        this.enumType = enumType;
        this.registryName = registryName;
        this.meta = meta;
        this.specialName = specialName;
        this.unlocalizedName = unlocalizedName.equals("tile.air.name") ? "pb.undefined.name" : unlocalizedName;
        this.biomeRegistryName = biomeRegistryName;
        this.blockPos = blockPos;
    }
}
