package austeretony.plantbiomes.common.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import net.minecraft.launchwrapper.IClassTransformer;

public class PlantBiomesClassTransformer implements IClassTransformer {

    public static final Logger LOGGER = LogManager.getLogger("Plant Biomes Core");

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {   
        switch (transformedName) {
        //***vanilla plants support***
        case "net.minecraft.block.BlockSapling"://saplings   			
            return patch(basicClass, EnumInputClasses.MC_BLOCK_SAPLING);   			 			
        case "net.minecraft.block.BlockCrops"://crops (wheat, potato, carrot, etc.)							
            return patch(basicClass, EnumInputClasses.MC_BLOCK_CROP);                                                 
        case "net.minecraft.block.BlockStem"://melon, pumpkin                                                      
            return patch(basicClass, EnumInputClasses.MC_BLOCK_MELON);                                                 
        case "net.minecraft.block.BlockGrass"://grass spreading                                                      
            return patch(basicClass, EnumInputClasses.MC_BLOCK_GRASS);  
        case "net.minecraft.block.BlockMycelium"://mycelium spreading                                                      
            return patch(basicClass, EnumInputClasses.MC_BLOCK_MYCELIUM);  
        case "net.minecraft.block.BlockCocoa"://cocoa beans                                                      
            return patch(basicClass, EnumInputClasses.MC_BLOCK_COCOA);   
        case "net.minecraft.block.BlockNetherWart"://nether wart                                                   
            return patch(basicClass, EnumInputClasses.MC_BLOCK_NETHER_WART);   
        case "net.minecraft.block.BlockVine"://vines spreading                                                      
            return patch(basicClass, EnumInputClasses.MC_BLOCK_VINE);   
        case "net.minecraft.block.BlockMushroom"://mushrooms spreading                                                    
            return patch(basicClass, EnumInputClasses.MC_BLOCK_MUSHROOM);   
        case "net.minecraft.block.BlockReed"://reed                                                      
            return patch(basicClass, EnumInputClasses.MC_BLOCK_REED);   
        case "net.minecraft.block.BlockCactus"://cactus                                                      
            return patch(basicClass, EnumInputClasses.MC_BLOCK_CACTUS);   
        case "net.minecraft.world.biome.Biome"://flowers and grass growth with bonemeal                                                      
            return patch(basicClass, EnumInputClasses.MC_BIOME);   
        case "net.minecraft.item.ItemDye"://bonemeal behavior 
            return patch(basicClass, EnumInputClasses.MC_ITEM_DYE);   
        case "net.minecraft.client.renderer.RenderGlobal"://gray particles for client
            return patch(basicClass, EnumInputClasses.MC_RENDER_GLOBAL);   
            //***non-vanilla plants support***
            //Biomes O' Plenty (tested for 7.0.1.2419)
        case "biomesoplenty.common.block.BlockBOPSapling"://saplings
            return patch(basicClass, EnumInputClasses.BOP_SAPLING);
        case "biomesoplenty.common.block.BlockBOPGrass"://grass spreading
            return patch(basicClass, EnumInputClasses.BOP_GRASS_BLOCK);
        case "biomesoplenty.common.block.BlockBOPBamboo"://bamboo
            return patch(basicClass, EnumInputClasses.BOP_BLOCK_BAMBOO);
            //Forestry (tested for 5.8.2.382)
        case "forestry.arboriculture.tiles.TileSapling"://saplings
            return patch(basicClass, EnumInputClasses.FORESTRY_TILE_SAPLING);
        case "forestry.arboriculture.blocks.BlockSapling"://saplings    
            return patch(basicClass, EnumInputClasses.FORESTRY_BLOCK_SAPLING);
            //IndustrialCraft 2 (tested for 2.8.108)
            //Why different classes for every crop?
        case "ic2.core.block.Ic2Sapling"://sapling
            return patch(basicClass, EnumInputClasses.IC2_BLOCK_SAPLING);
        case "ic2.core.crop.CropVanilla"://vanilla crops
            return patch(basicClass, EnumInputClasses.IC2_CROP_VANILLA);
        case "ic2.api.crops.CropCard"://crops
            return patch(basicClass, EnumInputClasses.IC2_CROP_CARD);
        case "ic2.core.crop.cropcard.CropPotato"://potato crop
            return patch(basicClass, EnumInputClasses.IC2_CROP_POTATO);
        case "ic2.core.crop.cropcard.CropStickreed"://stickreed crop
            return patch(basicClass, EnumInputClasses.IC2_CROP_STICKREED);
        case "ic2.core.crop.cropcard.CropVenomilia"://venomilla crop
            return patch(basicClass, EnumInputClasses.IC2_CROP_VENOMILLA);
        case "ic2.core.crop.cropcard.CropBaseMushroom"://mushroom crop
            return patch(basicClass, EnumInputClasses.IC2_CROP_BASE_MUSHROOM);
        case "ic2.core.crop.cropcard.CropBaseMetalCommon"://metal plant crop
            return patch(basicClass, EnumInputClasses.IC2_CROP_BASE_METAL_COMMON);
        case "ic2.core.crop.cropcard.CropBaseMetalUncommon"://metal plant crop
            return patch(basicClass, EnumInputClasses.IC2_CROP_BASE_METAL_UNCOMMON);
        case "ic2.core.crop.cropcard.CropEating"://eating crop
            return patch(basicClass, EnumInputClasses.IC2_CROP_EATING);
        case "ic2.core.crop.cropcard.CropHops"://hops crop
            return patch(basicClass, EnumInputClasses.IC2_CROP_HOPS);
        case "ic2.core.crop.cropcard.CropColorFlower"://flower crops
            return patch(basicClass, EnumInputClasses.IC2_CROP_COLOR_FLOWER);
        case "ic2.core.crop.cropcard.CropRedWheat"://red wheat crop
            return patch(basicClass, EnumInputClasses.IC2_CROP_RED_WHEAT);
            //Thaumcraft (tested for 6.1.BETA26)
        case "thaumcraft.common.blocks.world.plants.BlockSaplingTC"://thaumcraft saplings
            return patch(basicClass, EnumInputClasses.TC_BLOCK_SAPLING);
            //Twilight Forest (tested for 3.8.689)
        case "twilightforest.block.BlockTFSapling"://saplings
            return patch(basicClass, EnumInputClasses.TF_BLOCK_SAPLING);
        }   	
        return basicClass;
    }

    private byte[] patch(byte[] basicClass, EnumInputClasses enumInput) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(basicClass);
        classReader.accept(classNode, enumInput.readerFlags);
        if (enumInput.patch(classNode))
            LOGGER.info(enumInput.domain + " <" + enumInput.clazz + ".class> patched!");
        ClassWriter writer = new ClassWriter(enumInput.writerFlags);        
        classNode.accept(writer);
        return writer.toByteArray();    
    }
}

