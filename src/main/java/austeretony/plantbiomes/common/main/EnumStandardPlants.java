package austeretony.plantbiomes.common.main;

import net.minecraft.block.Block;

public enum EnumStandardPlants {

    //Vanilla
    MC_SAPLING("mc_block_sapling", "net.minecraft.block.BlockSapling"),
    MC_CROP("mc_block_crops", "net.minecraft.block.BlockCrops"),
    MC_CARROT("mc_block_crops", "net.minecraft.block.BlockCarrot"),
    MC_POTATO("mc_block_crops", "net.minecraft.block.BlockPotato"),
    MC_BEETROOT("mc_block_crops", "net.minecraft.block.BlockBeetroot"),
    MC_MELON("mc_block_stem", "net.minecraft.block.BlockStem"),
    MC_GRASS_BLOCK("mc_block_grass", "net.minecraft.block.BlockGrass"),
    MC_MYCELIUM_BLOCK("mc_block_mycelium", "net.minecraft.block.BlockMycelium"),
    MC_RED_FLOWER("mc_biome", "net.minecraft.block.BlockRedFlower"),
    MC_YELLOW_FLOWER("mc_biome", "net.minecraft.block.BlockYellowFlower"),
    MC_TALL_GRASS("mc_block_grass", "net.minecraft.block.BlockTallGrass"),
    //TODO Add support for double plants (need hook to BlockTallGrass#grow(World, Random, BlockPos, IBlockState))
    //MC_DOUBLE_PLANT("net.minecraft.block.BlockDoublePlant"),
    MC_VINE("mc_block_vine", "net.minecraft.block.BlockVine"),
    MC_MUSHROOM("mc_block_mushroom", "net.minecraft.block.BlockMushroom"),
    MC_NETHER_WART("mc_block_nether_wart", "net.minecraft.block.BlockNetherWart"),
    MC_COCOA("mc_block_cocoa", "net.minecraft.block.BlockCocoa"),
    MC_REED("mc_block_reed", "net.minecraft.block.BlockReed"),
    MC_CACTUS("mc_block_cactus", "net.minecraft.block.BlockCactus"),
    //Biomes O' Plenty
    BOP_SAPLING("bop_block_sapling", "biomesoplenty.common.block.BlockBOPSapling"),
    BOP_BLOCK_GRASS("bop_block_grass", "biomesoplenty.common.block.BlockBOPGrass"),
    BOP_VINE("mc_block_vine", "biomesoplenty.common.block.BlockBOPVine"),
    BOP_BAMBOO("bop_block_bamboo", "biomesoplenty.common.block.BlockBOPBamboo"),
    //DynamicTrees
    DT_FRUIT("dt_block_fruit", "com.ferreusveritas.dynamictrees.blocks.BlockFruit"),
    DT_COCOA("mc_block_cocoa", "com.ferreusveritas.dynamictrees.blocks.BlockFruitCocoa"),
    //ImmersiveEngineering
    IE_HEMP("ie_block_crop", "blusunrize.immersiveengineering.common.blocks.plant.BlockIECrop"),
    //IndustrialCraft 2
    IC2_SAPLING("ic_block_sapling", "ic2.core.block.Ic2Sapling"),
    //Mystical Agriculture
    MA_CROP("ma_block_crop", "com.blakebr0.mysticalagriculture.blocks.crop.BlockMysticalCrop"),
    MA_INFERIUM_CROP("ma_block_crop", "com.blakebr0.mysticalagriculture.blocks.crop.BlockInferiumCrop"),    
    //Oreberries
    OREBERRIES_OREBERRY_BUSH("o_block_oreberry_bush", "josephcsible.oreberries.BlockOreberryBush"),
    //Pam's HarvestCraft
    PHC_SAPLING("phc_block_sapling", "com.pam.harvestcraft.blocks.growables.BlockPamSapling"),
    PHC_CROP("phc_block_crop", "com.pam.harvestcraft.blocks.growables.BlockPamCrop"),
    PHC_FRUIT("phc_block_fruit", "com.pam.harvestcraft.blocks.growables.BlockPamFruit"),
    //Plants
    PLANTS_SAPLING("p_block_enum_sapling", "shadows.plants2.block.BlockEnumSapling"),
    PLANTS_NETHER_SAPLING("p_block_enum_sapling", "shadows.plants2.block.forgotten.BlockNetherSapling"),
    PLANTS_HARVEST_BUSH("p_block_enum_harvest_bush", "shadows.plants2.block.BlockEnumHarvestBush"),
    PLANTS_BUSHLING("p_block_bushling", "shadows.plants2.block.forgotten.BlockBushling"),
    PLANTS_NETHER_HARVEST("p_block_enum_nether_harvest", "shadows.plants2.block.BlockEnumNetherHarvest"),
    PLANTS_CROP("p_block_enum_crop", "shadows.plants2.block.BlockEnumCrop"),
    PLANTS_DOUBLE_HARVEST_BUSH("p_block_enum_double_harvest_bush", "shadows.plants2.block.BlockEnumDoubleHarvestBush"),
    PLANTS_VINE("mc_block_vine", "shadows.plants2.block.BlockCustomVine"),
    PLANTS_CRYSTAL_SAPLING("p_block_enum_sapling", "shadows.plants2.block.forgotten.BlockCrystal$Sapling"),
    //Rustic
    RUSTIC_SAPLING("r_block_sapling", "rustic.common.blocks.BlockSaplingRustic"),
    RUSTIC_APPLE_SAPLING("r_block_sapling_apple", "rustic.common.blocks.crops.BlockSaplingApple"),
    RUSTIC_CROP("r_block_crop", "rustic.common.blocks.crops.BlockStakeCrop"),
    RUSTIC_CROP_1("r_block_crop", "rustic.common.blocks.ModBlocks$1"),//tomato
    RUSTIC_CROP_2("r_block_crop", "rustic.common.blocks.ModBlocks$2"),//chili
    RUSTIC_APPLE_SEEDS("r_block_apple_seeds", "rustic.common.blocks.crops.BlockAppleSeeds"),
    RUSTIC_BERRY_BUSH("r_block_berry_bush", "rustic.common.blocks.crops.BlockBerryBush"),
    RUSTIC_BERRY_BUSH_1("r_block_berry_bush", "rustic.common.blocks.ModBlocks$3"),//wildberry
    RUSTIC_GRAPE_STEM("r_block_grape", "rustic.common.blocks.crops.BlockGrapeStem"),
    RUSTIC_HERB("r_block_herb", "rustic.common.blocks.crops.BlockHerbBase"),
    RUSTIC_HERB_1("r_block_herb", "rustic.common.blocks.crops.Herbs$1"),//aloe and other alchemy herbs
    RUSTIC_HERB_2("r_block_herb", "rustic.common.blocks.crops.Herbs$2"),
    RUSTIC_HERB_3("r_block_herb", "rustic.common.blocks.crops.Herbs$3"),
    RUSTIC_HERB_4("r_block_herb", "rustic.common.blocks.crops.Herbs$4"),
    RUSTIC_HERB_5("r_block_herb", "rustic.common.blocks.crops.Herbs$5"),
    RUSTIC_HERB_6("r_block_herb", "rustic.common.blocks.crops.Herbs$6"),
    RUSTIC_HERB_7("r_block_herb", "rustic.common.blocks.crops.Herbs$7"),
    RUSTIC_HERB_8("r_block_herb", "rustic.common.blocks.crops.Herbs$8"),
    RUSTIC_HERB_9("r_block_herb", "rustic.common.blocks.crops.Herbs$9"),
    RUSTIC_HERB_10("r_block_herb", "rustic.common.blocks.crops.Herbs$10"),
    RUSTIC_HERB_11("r_block_herb", "rustic.common.blocks.crops.Herbs$11"),
    RUSTIC_HERB_12("r_block_herb", "rustic.common.blocks.crops.Herbs$12"),
    //Thaumcraft
    TC_SAPLING("tc_block_sapling", "thaumcraft.common.blocks.world.plants.BlockSaplingTC"),
    //Twilight Forest
    TF_SAPLING("tf_block_sapling", "twilightforest.block.BlockTFSapling");

    public final String transformedClassId, className;

    private boolean supported = true;

    EnumStandardPlants(String id, String className) {
        this.transformedClassId = id;
        this.className = className;
    }

    public boolean isSupported() {
        return this.supported;
    }

    public void setSupported(boolean flag) {
        this.supported = flag;
    }

    public static EnumStandardPlants identify(Block block) {
        for (EnumStandardPlants enumPlant : values())
            if (enumPlant.isSupported() && enumPlant.className.equals(block.getClass().getName()))
                return enumPlant;   
        return null;
    }
}
