package austeretony.plantbiomes.common.core;

import java.util.Random;

import austeretony.plantbiomes.common.main.EnumPBPlantType;
import austeretony.plantbiomes.common.main.EnumSpecialPlants;
import austeretony.plantbiomes.common.main.PBManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome.FlowerEntry;

public class PlantBiomesHooks {

    //TODO Get rid of repeating and similar methods.

    public static boolean isGrowthAllowedTick(World world, BlockPos pos, Block block, IBlockState blockState) {
        if (PBManager.exist(block)) {
            if (PBManager.get(block).isPermittedBiome(block, blockState, world, pos))
                return true;
            return false;           
        }
        return true;
    }

    public static boolean isGrowthAllowedBonemeal(World world, BlockPos pos, boolean flag) {
        IBlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        if (PBManager.exist(block)) {
            if (PBManager.get(block).isPermittedBiome(block, blockState, world, pos))
                return true;
            if (block instanceof BlockGrass) return true;//Allow bonemeal usage on grass block if grass spreading disabled in this biome.
            if (flag)
                world.playEvent(900, pos, 0);
            return false;
        }
        return true;
    }

    public static boolean isGrowthAllowedTallgrass(World world, BlockPos pos, IBlockState blockState) {
        Block block = blockState.getBlock();
        if (PBManager.exist(block)) {
            if (PBManager.get(block).isPermittedBiome(block, blockState, world, pos))
                return true;
            return false;
        }
        return true;
    }

    public static boolean isGrowthAllowedFlower(World world, BlockPos pos, FlowerEntry flowerEntry) {
        Block block = flowerEntry.state.getBlock();
        if (PBManager.exist(block)) {
            if (PBManager.get(block).isPermittedBiome(block, flowerEntry.state, world, pos))
                return true;
            return false;
        }
        return true;
    }

    public static int isGrowthAllowedAgriCraftCrop(World world, BlockPos pos, String cropId) {
        if (PBManager.existSpecial(cropId, EnumPBPlantType.AGRICRAFT_CROP)) {
            if (PBManager.getSpecial(cropId, EnumPBPlantType.AGRICRAFT_CROP).isPermittedBiome(EnumSpecialPlants.SPECIALS_META, PBManager.getBiomeRegistryName(world, pos)))
                return 1;
            return 0;           
        }
        return 1;
    }

    public static void showAgriCraftCropDeniedBiome(World world, BlockPos pos, String cropId, EntityPlayer player) {
        if (player.getHeldItemMainhand().getItem() == Items.DYE && EnumDyeColor.byDyeDamage(player.getHeldItemMainhand().getMetadata()) == EnumDyeColor.WHITE)
            if (isGrowthAllowedAgriCraftCrop(world, pos, cropId) == 0)
                world.playEvent(900, pos, 0);
    }

    public static boolean isGrowthAllowedTickDynamicTree(World world, BlockPos pos, ResourceLocation registryName) {
        if (PBManager.existServer(registryName)) {
            if (PBManager.getServer(registryName).isPermittedBiome(EnumSpecialPlants.SPECIALS_META, PBManager.getBiomeRegistryName(world, pos)))
                return true;
            return false;           
        }
        return true;
    }

    public static boolean isGrowthAllowedIC2Crop(World world, BlockPos pos, String cropId) {
        if (PBManager.existSpecial(cropId, EnumPBPlantType.IC2_CROP)) {
            if (PBManager.getSpecial(cropId, EnumPBPlantType.IC2_CROP).isPermittedBiome(EnumSpecialPlants.SPECIALS_META, PBManager.getBiomeRegistryName(world, pos)))
                return true;
            return false;           
        }
        return true;
    }

    public static void showIC2CropDeniedBiome(World world, BlockPos pos, String cropId, EntityPlayer player) {
        if (player.getHeldItemMainhand().getItem() == Items.DYE && EnumDyeColor.byDyeDamage(player.getHeldItemMainhand().getMetadata()) == EnumDyeColor.WHITE)
            if (!isGrowthAllowedIC2Crop(world, pos, cropId))
                world.playEvent(900, pos, 0);
    }

    public static boolean isGrowthAllowedForestryFruitPod(World world, BlockPos pos, String uid) {
        if (PBManager.existSpecial(uid, EnumPBPlantType.FORESTRY_FRUIT)) {
            if (PBManager.getSpecial(uid, EnumPBPlantType.FORESTRY_FRUIT).isPermittedBiome(EnumSpecialPlants.SPECIALS_META, PBManager.getBiomeRegistryName(world, pos)))
                return true;
            return false;           
        }
        return true;
    }

    public static float isGrowthAllowedBonemealForestryFruitPod(World world, BlockPos pos, String uid) {
        if (PBManager.existSpecial(uid, EnumPBPlantType.FORESTRY_FRUIT)) {
            if (PBManager.getSpecial(uid, EnumPBPlantType.FORESTRY_FRUIT).isPermittedBiome(EnumSpecialPlants.SPECIALS_META, PBManager.getBiomeRegistryName(world, pos)))
                return 0.5F;
            world.playEvent(900, pos, 0);
            return 0.0F;           
        }
        return 0.5F;
    }

    public static boolean isGrowthAllowedForestryLeaves(World world, BlockPos pos, String ident) {
        if (PBManager.existSpecial(ident, EnumPBPlantType.FORESTRY_LEAVES)) {
            if (PBManager.getSpecial(ident, EnumPBPlantType.FORESTRY_LEAVES).isPermittedBiome(EnumSpecialPlants.SPECIALS_META, PBManager.getBiomeRegistryName(world, pos)))
                return true;
            return false;           
        }
        return true;
    }

    public static float isGrowthAllowedBonemealForestryLeaves(World world, BlockPos pos, String ident) {
        if (PBManager.existSpecial(ident, EnumPBPlantType.FORESTRY_LEAVES)) {
            if (PBManager.getSpecial(ident, EnumPBPlantType.FORESTRY_LEAVES).isPermittedBiome(EnumSpecialPlants.SPECIALS_META, PBManager.getBiomeRegistryName(world, pos)))
                return 0.5F;
            world.playEvent(900, pos, 0);
            return 0.0F;           
        }
        return 0.5F;
    }

    public static boolean isGrowthAllowedForestrySapling(World world, BlockPos pos, String ident, boolean bonemealUsed) {
        if (PBManager.existSpecial(ident, EnumPBPlantType.FORESTRY_SAPLING)) {
            if (PBManager.getSpecial(ident, EnumPBPlantType.FORESTRY_SAPLING).isPermittedBiome(EnumSpecialPlants.SPECIALS_META, PBManager.getBiomeRegistryName(world, pos)))
                return true;
            if (bonemealUsed)
                world.playEvent(900, pos, 0);
            return false;           
        }
        return true;
    }

    public static void spawnParticles(World world, BlockPos pos, int type, Random random) {
        if (type == 900) {
            IBlockState blockState = world.getBlockState(pos);
            double d0, d1, d2;
            if (blockState.getMaterial() != Material.AIR) {
                for (int i = 0; i < 15; ++i) {
                    d0 = random.nextGaussian() * 0.02D;
                    d1 = random.nextGaussian() * 0.02D;
                    d2 = random.nextGaussian() * 0.02D;
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (double) ((float) pos.getX() + random.nextFloat()), (double) pos.getY() + (double) random.nextFloat() * blockState.getBoundingBox(world, pos).maxY, (double) ((float) pos.getZ() + random.nextFloat()), d0, d1, d2);
                }
            } else {
                for (int i = 0; i < 15; ++i) {
                    d0 = random.nextGaussian() * 0.02D;
                    d1 = random.nextGaussian() * 0.02D;
                    d2 = random.nextGaussian() * 0.02D;
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (double) ((float) pos.getX() + random.nextFloat()), (double) pos.getY() + (double) random.nextFloat() * 1.0f, (double) ((float) pos.getZ() + random.nextFloat()), d0, d1, d2, new int[0]);
                }
            }
        }
    }
}
