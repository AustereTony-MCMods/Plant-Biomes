package austeretony.plantbiomes.common.main;

import net.minecraft.block.Block;

public enum EnumStandardPlants {

    //Vanilla
    MC_SAPLING("net.minecraft.block.BlockSapling"),
    MC_CROP("net.minecraft.block.BlockCrops"),
    MC_BEETROOT("net.minecraft.block.BlockBeetroot"),
    MC_MELON("net.minecraft.block.BlockStem"),
    MC_GRASS_BLOCK("net.minecraft.block.BlockGrass"),
    MC_MYCELIUM_BLOCK("net.minecraft.block.BlockMycelium"),
    MC_RED_FLOWER("net.minecraft.block.BlockRedFlower"),
    MC_YELLOW_FLOWER("net.minecraft.block.BlockYellowFlower"),
    MC_TALL_GRASS("net.minecraft.block.BlockTallGrass"),
    //MC_DOUBLE_PLANT("net.minecraft.block.BlockDoublePlant"),//TODO Add support for double plants (need hook to BlockTallGrass#grow(World, Random, BlockPos, IBlockState))
    MC_VINE("net.minecraft.block.BlockVine"),
    MC_MUSHROOM("net.minecraft.block.BlockMushroom"),
    MC_NETHER_WART("net.minecraft.block.BlockNetherWart"),
    MC_COCOA("net.minecraft.block.BlockCocoa"),
    MC_REED("net.minecraft.block.BlockReed"),
    MC_CACTUS("net.minecraft.block.BlockCactus"),
    //Biomes O' Plenty
    BOP_SAPLING("biomesoplenty.common.block.BlockBOPSapling"),
    BOP_GRASS_BLOCK("biomesoplenty.common.block.BlockBOPGrass"),
    BOP_VINE("biomesoplenty.common.block.BlockBOPVine"),
    BOP_BAMBOO("biomesoplenty.common.block.BlockBOPBamboo"),
    //IndustrialCraft 2
    IC2_SAPLING("ic2.core.block.Ic2Sapling"),
    //Thaumcraft
    TC_SAPLING("thaumcraft.common.blocks.world.plants.BlockSaplingTC"),
    //Twilight Forest
    TF_SAPLING("twilightforest.block.BlockTFSapling");

    public final String className;

    EnumStandardPlants(String className) {
        this.className = className;
    }

    public static EnumStandardPlants identify(Block block) {
        for (EnumStandardPlants enumPlant : values())
            if (enumPlant.className.equals(block.getClass().getName()))
                return enumPlant;   
        return null;
    }
}
