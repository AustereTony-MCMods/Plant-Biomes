package austeretony.plantbiomes.common.main;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public enum EnumSpecialPlants {

    AGRICRAFT_CROP("com.infinityraider.agricraft.tiles.TileEntityCrop") {

        @Override
        public boolean collectData(NBTTagCompound tagCompound, World world, BlockPos pos) {
            String cropId = tagCompound.getString("agri_seed");
            if (cropId.isEmpty())
                return false;
            PBManager.latestPlant = new LatestPlant(
                    EnumPBPlantType.AGRICRAFT_CROP,
                    new ResourceLocation("agricraft", cropId.replace(':', '.')), 
                    SPECIALS_META, 
                    cropId,
                    cropId,
                    PBManager.getBiomeRegistryName(world, pos), 
                    pos);
            return true;
        }

        @Override
        public ResourceLocation createRegistryName(NBTTagCompound tagCompound) {
            return new ResourceLocation("agricraft", tagCompound.getString("agri_seed").replace(':', '.'));
        }
    },
    FORESTRY_SAPLING("forestry.arboriculture.tiles.TileSapling") {

        @Override
        public boolean collectData(NBTTagCompound tagCompound, World world, BlockPos pos) {
            NBTTagCompound 
            containedTreeCompound = tagCompound.getCompoundTag("ContainedTree"),
            genomeCompound = containedTreeCompound.getCompoundTag("Genome"),
            alleleCompound;
            NBTTagList chromosomesTagList = genomeCompound.getTagList("Chromosomes", 10);
            alleleCompound = chromosomesTagList.getCompoundTagAt(0);
            String ident = alleleCompound.getString("UID1");
            if (ident.isEmpty())
                return false;
            String[] splitted = ident.split("[.]");
            PBManager.latestPlant = new LatestPlant(
                    EnumPBPlantType.FORESTRY_SAPLING,
                    new ResourceLocation(splitted[0], splitted[1]), 
                    SPECIALS_META, 
                    ident,
                    ident,
                    PBManager.getBiomeRegistryName(world, pos), 
                    pos);
            return true;
        }

        @Override
        public ResourceLocation createRegistryName(NBTTagCompound tagCompound) {
            NBTTagCompound 
            containedTreeCompound = tagCompound.getCompoundTag("ContainedTree"),
            genomeCompound = containedTreeCompound.getCompoundTag("Genome"),
            alleleCompound;
            NBTTagList chromosomesTagList = genomeCompound.getTagList("Chromosomes", 10);
            alleleCompound = chromosomesTagList.getCompoundTagAt(0);
            String ident = alleleCompound.getString("UID1");
            String[] splitted = ident.split("[.]");
            return new ResourceLocation(splitted[0], splitted[1]);
        }
    },
    IC2_CROP("ic2.core.crop.TileEntityCrop") {

        @Override
        public boolean collectData(NBTTagCompound tagCompound, World world, BlockPos pos) {
            String 
            owner = tagCompound.getString("cropOwner"), 
            cropId = tagCompound.getString("cropId");
            if (cropId.isEmpty())
                return false;
            PBManager.latestPlant = new LatestPlant(
                    EnumPBPlantType.IC2_CROP,
                    new ResourceLocation(owner, cropId), 
                    SPECIALS_META, 
                    cropId,
                    owner + ".crop." + cropId,
                    PBManager.getBiomeRegistryName(world, pos), 
                    pos);
            return true;
        }

        @Override
        public ResourceLocation createRegistryName(NBTTagCompound tagCompound) {
            return new ResourceLocation(tagCompound.getString("cropOwner"), tagCompound.getString("cropId"));
        }
    };

    public static final int SPECIALS_META = 16;

    public final String tileClassName;

    EnumSpecialPlants(String tileClassName) {
        this.tileClassName = tileClassName;
    }

    public abstract boolean collectData(NBTTagCompound tagCompound, World world, BlockPos pos);

    public abstract ResourceLocation createRegistryName(NBTTagCompound tagCompound);

    public static EnumSpecialPlants identify(TileEntity tile) {
        String className = tile.getClass().getName();
        for (EnumSpecialPlants enumPlant : values())
            if (enumPlant.tileClassName.equals(className))
                return enumPlant;
        return null;
    }
}
