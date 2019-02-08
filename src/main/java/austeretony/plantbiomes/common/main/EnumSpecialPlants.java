package austeretony.plantbiomes.common.main;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public enum EnumSpecialPlants {

    AGRICRAFT_CROP("com.infinityraider.agricraft.tiles.TileEntityCrop"),
    FORESTRY_SAPLING("forestry.arboriculture.tiles.TileSapling"),
    DYNAMIC_TREES_SAPLING("com.ferreusveritas.dynamictrees.tileentity.TileEntitySpecies"),
    IC2_CROP("ic2.core.crop.TileEntityCrop");

    public static final int SPECIALS_META = 16;

    public final String tileClassName;

    EnumSpecialPlants(String tileClassName) {
        this.tileClassName = tileClassName;
    }

    public boolean collectData(NBTTagCompound tagCompound, World world, BlockPos pos) {
        switch (this) {
        case AGRICRAFT_CROP:
            return collectAgricraftCrop(tagCompound, world, pos);
        case FORESTRY_SAPLING:
            return collectForestrySapling(tagCompound, world, pos);
        case DYNAMIC_TREES_SAPLING:
            return collectDynamicTreesSapling(tagCompound, world, pos);
        case IC2_CROP:
            return collectIC2Crop(tagCompound, world, pos);
        }
        return false;
    }

    private boolean collectAgricraftCrop(NBTTagCompound tagCompound, World world, BlockPos pos) {
        String id = tagCompound.getString("agri_seed");
        if (id.isEmpty())
            return false;
        PBManager.latestPlant = new LatestPlant(
                EnumPBPlantType.AGRICRAFT_CROP,
                new ResourceLocation("agricraft", id.replace(':', '.')), 
                SPECIALS_META, 
                id,
                id,
                PBManager.getBiomeRegistryName(world, pos), 
                pos);
        return true;
    }

    private boolean collectForestrySapling(NBTTagCompound tagCompound, World world, BlockPos pos) {
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

    private boolean collectDynamicTreesSapling(NBTTagCompound tagCompound, World world, BlockPos pos) {
        String speciesName = tagCompound.getString("species");
        if (speciesName.isEmpty())
            return false;
        PBManager.latestPlant = new LatestPlant(
                EnumPBPlantType.STANDARD,
                new ResourceLocation(speciesName), 
                SPECIALS_META, 
                "",
                speciesName,
                PBManager.getBiomeRegistryName(world, pos), 
                pos);
        return true;
    }

    private boolean collectIC2Crop(NBTTagCompound tagCompound, World world, BlockPos pos) {
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

    public ResourceLocation createRegistryName(NBTTagCompound tagCompound) {
        switch (this) {
        case AGRICRAFT_CROP:
            return new ResourceLocation("agricraft", tagCompound.getString("agri_seed").replace(':', '.'));
        case FORESTRY_SAPLING:
            NBTTagCompound 
            containedTreeCompound = tagCompound.getCompoundTag("ContainedTree"),
            genomeCompound = containedTreeCompound.getCompoundTag("Genome"),
            alleleCompound;
            NBTTagList chromosomesTagList = genomeCompound.getTagList("Chromosomes", 10);
            alleleCompound = chromosomesTagList.getCompoundTagAt(0);
            String ident = alleleCompound.getString("UID1");
            String[] splitted = ident.split("[.]");
            return new ResourceLocation(splitted[0], splitted[1]);
        case DYNAMIC_TREES_SAPLING:
            return new ResourceLocation(tagCompound.getString("species"));
        case IC2_CROP:
            return new ResourceLocation(tagCompound.getString("cropOwner"), tagCompound.getString("cropId"));
        }
        return null;
    }

    public static EnumSpecialPlants identify(TileEntity tile) {
        String className = tile.getClass().getName();
        for (EnumSpecialPlants enumPlant : values())
            if (enumPlant.tileClassName.equals(className))
                return enumPlant;
        return null;
    }
}
