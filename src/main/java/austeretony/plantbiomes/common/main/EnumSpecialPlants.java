package austeretony.plantbiomes.common.main;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public enum EnumSpecialPlants {

    AGRICRAFT_CROP("com.infinityraider.agricraft.tiles.TileEntityCrop"),
    FORESTRY_FRUIT("forestry.arboriculture.tiles.TileFruitPod"),
    FORESTRY_LEAVES("forestry.arboriculture.tiles.TileLeaves"),
    FORESTRY_SAPLING("forestry.arboriculture.tiles.TileSapling"),
    DYNAMIC_TREES_SAPLING("com.ferreusveritas.dynamictrees.tileentity.TileEntitySpecies"),
    IC2_CROP("ic2.core.crop.TileEntityCrop");

    public static final int SPECIALS_META = 16;

    public final String tileClassName;

    EnumSpecialPlants(String tileClassName) {
        this.tileClassName = tileClassName;
    }

    public boolean getData(NBTTagCompound tagCompound, World world, BlockPos pos) {
        switch (this) {
        case AGRICRAFT_CROP:
            return getAgricraftCrop(tagCompound, world, pos);
        case FORESTRY_FRUIT:
            return getForestryFruit(tagCompound, world, pos);
        case FORESTRY_LEAVES:
            return getForestryLeaves(tagCompound, world, pos);
        case FORESTRY_SAPLING:
            return getForestrySapling(tagCompound, world, pos);
        case DYNAMIC_TREES_SAPLING:
            return getDynamicTreesSapling(tagCompound, world, pos);
        case IC2_CROP:
            return getIC2Crop(tagCompound, world, pos);
        }
        return false;
    }

    private boolean getAgricraftCrop(NBTTagCompound tagCompound, World world, BlockPos pos) {
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

    private boolean getForestryFruit(NBTTagCompound tagCompound, World world, BlockPos pos) {
        String uid = tagCompound.getString("UID");
        if (uid.isEmpty())
            return false;
        String[] uidSplitted = uid.split("[.]");
        PBManager.latestPlant = new LatestPlant(
                EnumPBPlantType.FORESTRY_FRUIT,
                new ResourceLocation(uidSplitted[0], uidSplitted[1]), 
                SPECIALS_META, 
                uid,
                uid,
                PBManager.getBiomeRegistryName(world, pos), 
                pos);
        return true;
    }

    private boolean getForestryLeaves(NBTTagCompound tagCompound, World world, BlockPos pos) {
        if (tagCompound.getBoolean("FL")) {
            NBTTagCompound 
            containedTreeCompound = tagCompound.getCompoundTag("ContainedTree"),
            genomeCompound = containedTreeCompound.getCompoundTag("Genome"),
            alleleCompound;
            NBTTagList chromosomesTagList = genomeCompound.getTagList("Chromosomes", 10);
            alleleCompound = chromosomesTagList.getCompoundTagAt(0);
            String uid = alleleCompound.getString("UID1");
            if (uid.isEmpty())
                return false;
            String[] uidSplitted = uid.split("[.]");
            PBManager.latestPlant = new LatestPlant(
                    EnumPBPlantType.FORESTRY_LEAVES,
                    new ResourceLocation(uidSplitted[0], uidSplitted[1] + ".fruit"), 
                    SPECIALS_META, 
                    uid,
                    uid,
                    PBManager.getBiomeRegistryName(world, pos), 
                    pos);
            return true;
        }
        return false;
    }

    private boolean getForestrySapling(NBTTagCompound tagCompound, World world, BlockPos pos) {
        NBTTagCompound 
        containedTreeCompound = tagCompound.getCompoundTag("ContainedTree"),
        genomeCompound = containedTreeCompound.getCompoundTag("Genome"),
        alleleCompound;
        NBTTagList chromosomesTagList = genomeCompound.getTagList("Chromosomes", 10);
        alleleCompound = chromosomesTagList.getCompoundTagAt(0);
        String ident = alleleCompound.getString("UID1");
        if (ident.isEmpty())
            return false;
        String[] identSplitted = ident.split("[.]");
        PBManager.latestPlant = new LatestPlant(
                EnumPBPlantType.FORESTRY_SAPLING,
                new ResourceLocation(identSplitted[0], identSplitted[1]), 
                SPECIALS_META, 
                ident,
                ident,
                PBManager.getBiomeRegistryName(world, pos), 
                pos);
        return true;
    }

    private boolean getDynamicTreesSapling(NBTTagCompound tagCompound, World world, BlockPos pos) {
        String speciesName = tagCompound.getString("species");
        if (speciesName.isEmpty())
            return false;
        PBManager.latestPlant = new LatestPlant(
                EnumPBPlantType.DYNAMIC_TREES_SAPLING,
                new ResourceLocation(speciesName), 
                SPECIALS_META, 
                speciesName,
                speciesName,
                PBManager.getBiomeRegistryName(world, pos), 
                pos);
        return true;
    }

    private boolean getIC2Crop(NBTTagCompound tagCompound, World world, BlockPos pos) {
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
        case FORESTRY_FRUIT:
            String uid = tagCompound.getString("UID");
            String[] uidSplitted = uid.split("[.]");
            return new ResourceLocation(uidSplitted[0], uidSplitted[1]);
        case FORESTRY_LEAVES:
            NBTTagCompound 
            containedTreeCompound1 = tagCompound.getCompoundTag("ContainedTree"),
            genomeCompound1 = containedTreeCompound1.getCompoundTag("Genome"),
            alleleCompound1;
            NBTTagList chromosomesTagList1 = genomeCompound1.getTagList("Chromosomes", 10);
            alleleCompound1 = chromosomesTagList1.getCompoundTagAt(0);
            String uid1 = alleleCompound1.getString("UID1");
            String[] uidSplitted1 = uid1.split("[.]");
            return tagCompound.getBoolean("FL") ? new ResourceLocation(uidSplitted1[0], uidSplitted1[1] + ".fruit") : null;
        case FORESTRY_SAPLING:
            NBTTagCompound 
            containedTreeCompound = tagCompound.getCompoundTag("ContainedTree"),
            genomeCompound = containedTreeCompound.getCompoundTag("Genome"),
            alleleCompound;
            NBTTagList chromosomesTagList = genomeCompound.getTagList("Chromosomes", 10);
            alleleCompound = chromosomesTagList.getCompoundTagAt(0);
            String ident = alleleCompound.getString("UID1");
            String[] identSplitted = ident.split("[.]");
            return new ResourceLocation(identSplitted[0], identSplitted[1]);
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
