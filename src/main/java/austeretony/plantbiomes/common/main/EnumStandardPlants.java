package austeretony.plantbiomes.common.main;

import net.minecraft.block.Block;

public enum EnumStandardPlants {

    //Vanilla
    MC_SAPLING("net.minecraft.block.BlockSapling"),
    MC_CROP("net.minecraft.block.BlockCrops"),
    MC_CARROT("net.minecraft.block.BlockCarrot"),
    MC_POTATO("net.minecraft.block.BlockPotato"),
    MC_BEETROOT("net.minecraft.block.BlockBeetroot"),
    MC_MELON("net.minecraft.block.BlockStem"),
    MC_GRASS_BLOCK("net.minecraft.block.BlockGrass"),
    MC_MYCELIUM_BLOCK("net.minecraft.block.BlockMycelium"),
    MC_RED_FLOWER("net.minecraft.block.BlockRedFlower"),
    MC_YELLOW_FLOWER("net.minecraft.block.BlockYellowFlower"),
    MC_TALL_GRASS("net.minecraft.block.BlockTallGrass"),
    //TODO Add support for double plants (need hook to BlockTallGrass#grow(World, Random, BlockPos, IBlockState))
    //MC_DOUBLE_PLANT("net.minecraft.block.BlockDoublePlant"),
    MC_VINE("net.minecraft.block.BlockVine"),
    MC_MUSHROOM("net.minecraft.block.BlockMushroom"),
    MC_NETHER_WART("net.minecraft.block.BlockNetherWart"),
    MC_COCOA("net.minecraft.block.BlockCocoa"),
    MC_REED("net.minecraft.block.BlockReed"),
    MC_CACTUS("net.minecraft.block.BlockCactus"),
    //Biomes O' Plenty
    BOP_SAPLING("biomesoplenty.common.block.BlockBOPSapling"),
    BOP_BLOCK_GRASS("biomesoplenty.common.block.BlockBOPGrass"),
    BOP_VINE("biomesoplenty.common.block.BlockBOPVine"),
    BOP_BAMBOO("biomesoplenty.common.block.BlockBOPBamboo"),
    //DynamicTrees
    DT_FRUIT("com.ferreusveritas.dynamictrees.blocks.BlockFruit"),
    DT_COCOA("com.ferreusveritas.dynamictrees.blocks.BlockFruitCocoa"),
    //ImmersiveEngineering
    IE_HEMP("blusunrize.immersiveengineering.common.blocks.plant.BlockIECrop"),
    //IndustrialCraft 2
    IC2_SAPLING("ic2.core.block.Ic2Sapling"),
    //Mystical Agriculture
    MA_CROP("com.blakebr0.mysticalagriculture.blocks.crop.BlockMysticalCrop"),
    MA_INFERIUM_CROP("com.blakebr0.mysticalagriculture.blocks.crop.BlockInferiumCrop"),
    //Pam's HarvestCraft
    HC_SAPLING("com.pam.harvestcraft.blocks.growables.BlockPamSapling"),
    HC_CROP("com.pam.harvestcraft.blocks.growables.BlockPamCrop"),
    HC_FRUIT("com.pam.harvestcraft.blocks.growables.BlockPamFruit"),
    //Rustic
    RUSTIC_SAPLING("rustic.common.blocks.BlockSaplingRustic"),
    RUSTIC_APPLE_SAPLING("rustic.common.blocks.crops.BlockSaplingApple"),
    RUSTIC_CROP("rustic.common.blocks.crops.BlockStakeCrop"),
    RUSTIC_CROP_1("rustic.common.blocks.ModBlocks$1"),//tomato
    RUSTIC_CROP_2("rustic.common.blocks.ModBlocks$2"),//chili
    RUSTIC_APPLE_SEEDS("rustic.common.blocks.crops.BlockAppleSeeds"),
    RUSTIC_BERRY_BUSH("rustic.common.blocks.crops.BlockBerryBush"),
    RUSTIC_BERRY_BUSH_1("rustic.common.blocks.ModBlocks$3"),//wildberry
    RUSTIC_GRAPE_STEM("rustic.common.blocks.crops.BlockGrapeStem"),
    RUSTIC_HERB("rustic.common.blocks.crops.BlockHerbBase"),
    RUSTIC_HERB_1("rustic.common.blocks.crops.Herbs$1"),//aloe and other alchemy herbs
    RUSTIC_HERB_2("rustic.common.blocks.crops.Herbs$2"),
    RUSTIC_HERB_3("rustic.common.blocks.crops.Herbs$3"),
    RUSTIC_HERB_4("rustic.common.blocks.crops.Herbs$4"),
    RUSTIC_HERB_5("rustic.common.blocks.crops.Herbs$5"),
    RUSTIC_HERB_6("rustic.common.blocks.crops.Herbs$6"),
    RUSTIC_HERB_7("rustic.common.blocks.crops.Herbs$7"),
    RUSTIC_HERB_8("rustic.common.blocks.crops.Herbs$8"),
    RUSTIC_HERB_9("rustic.common.blocks.crops.Herbs$9"),
    RUSTIC_HERB_10("rustic.common.blocks.crops.Herbs$10"),
    RUSTIC_HERB_11("rustic.common.blocks.crops.Herbs$11"),
    RUSTIC_HERB_12("rustic.common.blocks.crops.Herbs$12"),
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
