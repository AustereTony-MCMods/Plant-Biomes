package austeretony.plantbiomes.common.main;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public enum EnumSpecialPlants {

    FORESTRY_SAPLING("forestry.arboriculture.tiles.TileSapling") {

        @Override
        public void collectData(NBTTagCompound tagCompound, World world, BlockPos pos) {
            NBTTagCompound 
            containedTreeCompound = tagCompound.getCompoundTag("ContainedTree"),
            genomeCompound = containedTreeCompound.getCompoundTag("Genome"),
            alleleCompound;
            NBTTagList chromosomesTagList = genomeCompound.getTagList("Chromosomes", 10);
            alleleCompound = chromosomesTagList.getCompoundTagAt(0);
            String ident = alleleCompound.getString("UID1");
            String[] splitted = ident.split("[.]");
            if (splitted.length > 1) {
                DataLoader.latestPlant = new LatestPlant(new ResourceLocation(splitted[0], splitted[1]), 16, DataLoader.getBiomeRegistryName(world, pos), pos, ident + ".name");
                DataLoader.latestPlant.setForestrySaplingIdent(ident);
            }
        }
    },
    IC2_CROP("ic2.core.crop.TileEntityCrop") {

        @Override
        public void collectData(NBTTagCompound tagCompound, World world, BlockPos pos) {
            String 
            owner = tagCompound.getString("cropOwner"), 
            cropId = tagCompound.getString("cropId");
            DataLoader.latestPlant = new LatestPlant(new ResourceLocation(owner, cropId), 16, DataLoader.getBiomeRegistryName(world, pos), pos, owner + ".crop." + cropId);
            DataLoader.latestPlant.setIC2CropId(cropId);
        }
    };

    public final String tileClassName;

    EnumSpecialPlants(String tileClassName) {
        this.tileClassName = tileClassName;
    }

    public abstract void collectData(NBTTagCompound tagCompound, World world, BlockPos pos);

    public static EnumSpecialPlants identify(TileEntity tile) {
        String className = tile.getClass().getName();
        for (EnumSpecialPlants enumPlant : values())
            if (enumPlant.tileClassName.equals(className))
                return enumPlant;
        return null;
    }
}
