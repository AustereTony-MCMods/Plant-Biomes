package austeretony.plantbiomes.common.core;

import java.util.Random;

import austeretony.plantbiomes.common.main.DataLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome.FlowerEntry;

public class PlantBiomesHooks {

    public static boolean isGrowthAllowedTick(World world, BlockPos pos, Block block, IBlockState blockState) {
        if (DataLoader.exist(block)) {
            if (DataLoader.get(block).isPermittedBiome(block, blockState, world, pos))
                return true;
            return false;           
        }
        return true;
    }

    public static boolean isGrowthAllowedBonemeal(World world, BlockPos pos) {
        IBlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        if (DataLoader.exist(block)) {
            if (DataLoader.get(block).isPermittedBiome(block, blockState, world, pos))
                return true;
            if (block instanceof BlockGrass) return true;
            world.playEvent(900, pos, 0);
            return false;
        }
        return true;
    }

    public static boolean isGrowthAllowedTallgrass(World world, BlockPos pos, IBlockState blockState) {
        Block block = blockState.getBlock();
        if (DataLoader.exist(block)) {
            if (DataLoader.get(block).isPermittedBiome(block, blockState, world, pos))
                return true;
            return false;
        }
        return true;
    }

    public static boolean isGrowthAllowedFlower(World world, BlockPos pos, FlowerEntry flowerEntry) {
        Block block = flowerEntry.state.getBlock();
        if (DataLoader.exist(block)) {
            if (DataLoader.get(block).isPermittedBiome(block, flowerEntry.state, world, pos))
                return true;
            return false;
        }
        return true;
    }

    public static boolean isGrowthAllowedIC2Crop(World world, BlockPos pos, String cropId) {
        if (DataLoader.existIC2(cropId)) {
            if (DataLoader.getIC2(cropId).isPermittedBiome(0, DataLoader.getBiomeRegistryName(world, pos)))
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

    //TODO Update
    /*public static float isGrowthAllowedForestrySapling(World world, BlockPos pos, Block block, IBlockState blockState) {
        if (DataLoader.exist(block, blockState)) {
            if (DataLoader.get(block, blockState).isValidBiome(world, pos)) {
                return 0.0F;
            }
            return 1.0F;
        }
        return 1.0F;
    }*/

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
                for (int i1 = 0; i1 < 15; ++i1) {
                    d0 = random.nextGaussian() * 0.02D;
                    d1 = random.nextGaussian() * 0.02D;
                    d2 = random.nextGaussian() * 0.02D;
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (double) ((float) pos.getX() + random.nextFloat()), (double) pos.getY() + (double) random.nextFloat() * 1.0f, (double) ((float) pos.getZ() + random.nextFloat()), d0, d1, d2, new int[0]);
                }
            }
        }
    }
}
