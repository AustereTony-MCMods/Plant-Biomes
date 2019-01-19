package austeretony.plantbiomes.common.core;

import java.util.Random;

import austeretony.plantbiomes.common.main.PBDataLoader;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PlantBiomesHooks {

    public static boolean isGrowthAllowedTick(World world, BlockPos pos, Block block, IBlockState blockState) {
        String key = PBDataLoader.createPlantKey(block, blockState);
        if (PBDataLoader.map().containsKey(key)) {
            if (PBDataLoader.get(key).isValidBiome(PBDataLoader.getBiomeRegistryName(world, pos))) {
                return true;
            } else {
                return false;
            }             
        }
        return true;
    }

    public static boolean isGrowthAllowedBonemeal(World world, BlockPos pos) {
        String key = PBDataLoader.createPlantKey(world.getBlockState(pos).getBlock(), world.getBlockState(pos));
        if (PBDataLoader.map().containsKey(key)) {
            if (PBDataLoader.get(key).isValidBiome(PBDataLoader.getBiomeRegistryName(world, pos))) {
                return true;
            } else {
                world.playEvent(900, pos, 0);
                return false;
            }
        }
        return true;
    }

    public static void spawnParticles(World world, BlockPos pos, int amount, int type, Random random) {
        if (type == 900) {
            if (amount == 0) {
                amount = 15;
            }
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
