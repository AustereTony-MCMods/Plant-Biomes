package austeretony.plantbiomes.common.main;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class LatestPlant {

    public final ResourceLocation registryName, biomeRegistryName;

    public final String unlocalizedName;

    public final int meta;

    public final BlockPos blockPos;

    private String 
    ic2CropId = "",
    forestrySaplingIdent = "";

    public LatestPlant(ResourceLocation registryName, int meta, ResourceLocation biomeRegistryName, BlockPos blockPos, String unlocalizedName) {
        this.registryName = registryName;
        this.meta = meta;
        this.biomeRegistryName = biomeRegistryName;
        this.blockPos = blockPos;
        this.unlocalizedName = unlocalizedName.equals("tile.air.name") ? "pb.undefined.name" : unlocalizedName;
    }

    public boolean isIC2Crop() {
        return !this.ic2CropId.isEmpty();
    }

    public String getIC2CropId() {
        return this.ic2CropId;
    }

    public void setIC2CropId(String cropId) {
        this.ic2CropId = cropId;
    }

    public boolean isForestrySapling() {
        return !this.forestrySaplingIdent.isEmpty();
    }

    public String getForestrySaplingIdent() {
        return this.forestrySaplingIdent;
    }

    public void setForestrySaplingIdent(String ident) {
        this.forestrySaplingIdent = ident;
    }
}
