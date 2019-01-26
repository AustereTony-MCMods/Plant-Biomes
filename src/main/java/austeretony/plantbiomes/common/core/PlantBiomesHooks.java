package austeretony.plantbiomes.common.core;

import java.util.Random;

import austeretony.plantbiomes.common.main.DataLoader;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome.FlowerEntry;

public class PlantBiomesHooks {

    public static boolean isGrowthAllowedTick(World world, BlockPos pos, Block block, IBlockState blockState) {
        if (DataLoader.exist(block, blockState)) {
            if (DataLoader.get(block, blockState).isValidBiome(world, pos)) {
                return true;
            }
            return false;           
        }
        return true;
    }

    public static boolean isGrowthAllowedBonemeal(World world, BlockPos pos) {
        if (DataLoader.exist(world.getBlockState(pos).getBlock(), world.getBlockState(pos))) {
            if (DataLoader.get(world.getBlockState(pos).getBlock(), world.getBlockState(pos)).isValidBiome(world, pos)) {
                return true;
            }
            world.playEvent(900, pos, 15);
            return false;
        }
        return true;
    }

    public static boolean isGrowthAllowedTallgrass(World world, BlockPos pos, IBlockState blockState) {
        if (DataLoader.exist(blockState.getBlock(), blockState)) {
            if (DataLoader.get(blockState.getBlock(), blockState).isValidBiome(world, pos)) {
                return true;
            }
            return false;
        }
        return true;
    }

    public static boolean isGrowthAllowedFlower(World world, BlockPos pos, FlowerEntry flowerEntry) {
        if (DataLoader.exist(flowerEntry.state.getBlock(), flowerEntry.state)) {
            if (DataLoader.get(flowerEntry.state.getBlock(), flowerEntry.state).isValidBiome(world, pos)) {
                return true;
            }
            return false;
        }
        return true;
    }
    
    public static float isGrowthAllowedForestrySapling(World world, BlockPos pos, Block block, IBlockState blockState) {
        if (DataLoader.exist(block, blockState)) {
            if (DataLoader.get(block, blockState).isValidBiome(world, pos)) {
                return 0.0F;
            }
            return 1.0F;
        }
        return 1.0F;
    }

    public static void spawnParticles(World world, BlockPos pos, int amount, int type, Random random) {
        if (type == 900) {
            IBlockState iBlockState = world.getBlockState(pos);
            if (iBlockState.getMaterial() != Material.AIR) {
                double d0, d1, d2;
                for (int i = 0; i < amount; ++i) {
                    d0 = random.nextGaussian() * 0.02D;
                    d1 = random.nextGaussian() * 0.02D;
                    d2 = random.nextGaussian() * 0.02D;
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (double) ((float) pos.getX() + random.nextFloat()), (double) pos.getY() + (double) random.nextFloat() * iBlockState.getBoundingBox(world, pos).maxY, (double) ((float) pos.getZ() + random.nextFloat()), d0, d1, d2);
                }
            } else {
                double d0, d1, d2;
                for (int i1 = 0; i1 < amount; ++i1) {
                    d0 = random.nextGaussian() * 0.02D;
                    d1 = random.nextGaussian() * 0.02D;
                    d2 = random.nextGaussian() * 0.02D;
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (double) ((float) pos.getX() + random.nextFloat()), (double) pos.getY() + (double) random.nextFloat() * 1.0f, (double) ((float) pos.getZ() + random.nextFloat()), d0, d1, d2, new int[0]);
                }
            }
        }
    }
}
