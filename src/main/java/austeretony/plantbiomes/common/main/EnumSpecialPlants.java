package austeretony.plantbiomes.common.main;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public enum EnumSpecialPlants {

    AGRICRAFT_CROP("ac_tile_crop", "com.infinityraider.agricraft.tiles.TileEntityCrop"),
    FORESTRY_FRUIT("f_tile_fruit", "forestry.arboriculture.tiles.TileFruitPod"),
    FORESTRY_LEAVES("f_tile_leaves", "forestry.arboriculture.tiles.TileLeaves"),
    FORESTRY_SAPLING("f_tile_sapling", "forestry.arboriculture.tiles.TileSapling"),
    DYNAMIC_TREES_SAPLING("dt_species", "com.ferreusveritas.dynamictrees.tileentity.TileEntitySpecies"),
    IC2_CROP("ic_crop_card", "ic2.core.crop.TileEntityCrop");

    public static final int SPECIALS_META = 16;

    public final String transformedClassId, tileClassName;

    private boolean supported = true;

    EnumSpecialPlants(String id, String tileClassName) {
        this.transformedClassId = id;
        this.tileClassName = tileClassName;
    }

    public boolean isSupported() {
        return this.supported;
    }

    public void setSupported(boolean flag) {
        this.supported = flag;
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
        DataManager.latestPlantServer = new LatestPlant(
                EnumPlantType.AGRICRAFT_CROP,
                new ResourceLocation("agricraft", id.replace(':', '.')), 
                SPECIALS_META, 
                id,
                id,
                DataManager.getBiomeRegistryName(world, pos), 
                pos);
        return true;
    }

    private boolean getForestryFruit(NBTTagCompound tagCompound, World world, BlockPos pos) {
        String uid = tagCompound.getString("UID");
        if (uid.isEmpty())
            return false;
        String[] uidSplitted = uid.split("[.]");
        DataManager.latestPlantServer = new LatestPlant(
                EnumPlantType.FORESTRY_FRUIT,
                new ResourceLocation(uidSplitted[0], uidSplitted[1]), 
                SPECIALS_META, 
                uid,
                uid,
                DataManager.getBiomeRegistryName(world, pos), 
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
            DataManager.latestPlantServer = new LatestPlant(
                    EnumPlantType.FORESTRY_LEAVES,
                    new ResourceLocation(uidSplitted[0], uidSplitted[1] + ".fruit"), 
                    SPECIALS_META, 
                    uid,
                    uid,
                    DataManager.getBiomeRegistryName(world, pos), 
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
        DataManager.latestPlantServer = new LatestPlant(
                EnumPlantType.FORESTRY_SAPLING,
                new ResourceLocation(identSplitted[0], identSplitted[1]), 
                SPECIALS_META, 
                ident,
                ident,
                DataManager.getBiomeRegistryName(world, pos), 
                pos);
        return true;
    }

    private boolean getDynamicTreesSapling(NBTTagCompound tagCompound, World world, BlockPos pos) {
        String speciesName = tagCompound.getString("species");
        if (speciesName.isEmpty())
            return false;
        DataManager.latestPlantServer = new LatestPlant(
                EnumPlantType.DYNAMIC_TREES_SAPLING,
                new ResourceLocation(speciesName), 
                SPECIALS_META, 
                speciesName,
                speciesName,
                DataManager.getBiomeRegistryName(world, pos), 
                pos);
        return true;
    }

    private boolean getIC2Crop(NBTTagCompound tagCompound, World world, BlockPos pos) {
        String 
        owner = tagCompound.getString("cropOwner"), 
        cropId = tagCompound.getString("cropId");
        if (cropId.isEmpty())
            return false;
        DataManager.latestPlantServer = new LatestPlant(
                EnumPlantType.IC2_CROP,
                new ResourceLocation(owner, cropId), 
                SPECIALS_META, 
                cropId,
                owner + ".crop." + cropId,
                DataManager.getBiomeRegistryName(world, pos), 
                pos);
        return true;
    }

    @SideOnly(Side.CLIENT)
    public ResourceLocation createRegistryName(NBTTagCompound tagCompound) {
        switch (this) {
        case AGRICRAFT_CROP:
            return !tagCompound.getString("agri_seed").isEmpty() ? new ResourceLocation("agricraft", tagCompound.getString("agri_seed").replace(':', '.')) : null;
        case FORESTRY_FRUIT:
            String uid = tagCompound.getString("UID");
            String[] uidSplitted = uid.split("[.]");
            return !uid.isEmpty() ? new ResourceLocation(uidSplitted[0], uidSplitted[1]) : null;
        case FORESTRY_LEAVES:
            NBTTagCompound 
            containedTreeCompound1 = tagCompound.getCompoundTag("ContainedTree"),
            genomeCompound1 = containedTreeCompound1.getCompoundTag("Genome"),
            alleleCompound1;
            NBTTagList chromosomesTagList1 = genomeCompound1.getTagList("Chromosomes", 10);
            alleleCompound1 = chromosomesTagList1.getCompoundTagAt(0);
            String uid1 = alleleCompound1.getString("UID1");
            String[] uidSplitted1 = uid1.split("[.]");
            return tagCompound.getBoolean("FL") ? (!uid1.isEmpty() ? new ResourceLocation(uidSplitted1[0], uidSplitted1[1] + ".fruit") : null) : null;
        case FORESTRY_SAPLING:
            NBTTagCompound 
            containedTreeCompound = tagCompound.getCompoundTag("ContainedTree"),
            genomeCompound = containedTreeCompound.getCompoundTag("Genome"),
            alleleCompound;
            NBTTagList chromosomesTagList = genomeCompound.getTagList("Chromosomes", 10);
            alleleCompound = chromosomesTagList.getCompoundTagAt(0);
            String ident = alleleCompound.getString("UID1");
            String[] identSplitted = ident.split("[.]");
            return !ident.isEmpty() ? new ResourceLocation(identSplitted[0], identSplitted[1]) : null;
        case DYNAMIC_TREES_SAPLING:
            return !tagCompound.getString("species").isEmpty() ? new ResourceLocation(tagCompound.getString("species")) : null;
        case IC2_CROP:
            return !tagCompound.getString("cropId").isEmpty() ? new ResourceLocation(tagCompound.getString("cropOwner"), tagCompound.getString("cropId")) : null;
        }
        return null;
    }

    public static EnumSpecialPlants identify(TileEntity tile) {
        String className = tile.getClass().getName();
        for (EnumSpecialPlants enumPlant : values())
            if (enumPlant.isSupported() && enumPlant.tileClassName.equals(className))
                return enumPlant;
        return null;
    }
}
