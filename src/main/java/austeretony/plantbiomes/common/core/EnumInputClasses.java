package austeretony.plantbiomes.common.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public enum EnumInputClasses {

    MC_BLOCK_SAPLING("mc_block_sapling", "Minecraft", "BlockSapling", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    MC_BLOCK_CROPS("mc_block_crops", "Minecraft", "BlockCrops", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    MC_BLOCK_MELON("mc_block_stem", "Minecraft", "BlockStem", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    MC_BLOCK_GRASS("mc_block_grass", "Minecraft", "BlockGrass", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    MC_BLOCK_MYCELIUM("mc_block_mycelium", "Minecraft", "BlockMycelium", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    MC_BLOCK_COCOA("mc_block_cocoa", "Minecraft", "BlockCocoa", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    MC_BLOCK_NETHER_WART("mc_block_nether_wart", "Minecraft", "BlockNetherWart", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    MC_BLOCK_VINE("mc_block_vine", "Minecraft", "BlockVine", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    MC_BLOCK_MUSHROOM("mc_block_mushroom", "Minecraft", "BlockMushroom", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    MC_BLOCK_REED("mc_block_reed", "Minecraft", "BlockReed", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    MC_BLOCK_CACTUS("mc_block_cactus", "Minecraft", "BlockCactus", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),

    MC_BIOME("mc_biome", "Minecraft", "Biome", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    MC_ITEM_DYE("mc_item_dye", "Minecraft", "ItemDye", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),

    AC_TILE_ENTITY_CROP("ac_tile_crop", "AgriCraft", "TileEntityCrop", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),    
    AC_BLOCK_CROP("ac_block_crop", "AgriCraft", "BlockCrop", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),

    BOP_BLOCK_SAPLING("bop_block_sapling", "Biomes O' Plenty", "BlockBOPSapling", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    BOP_BLOCK_GRASS("bop_block_grass", "Biomes O' Plenty", "BlockBOPGrass", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    BOP_BLOCK_BAMBOO("bop_block_bamboo", "Biomes O' Plenty", "BlockBOPBamboo", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),

    DT_SPECIES("dt_species", "DynamicTrees", "Species", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    DT_SPECIES_CACTUS("dt_species_cactus", "DynamicTrees", "SpeciesCactus", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    DT_BLOCK_FRUIT("dt_block_fruit", "DynamicTrees", "BlockFruit", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),

    FORESTRY_TILE_FRUIT_POD("f_tile_fruit", "Forestry", "TileFruitPod", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    FORESTRY_BLOCK_FRUIT_POD("f_block_fruit", "Forestry", "BlockFruitPod", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    FORESTRY_TILE_LEAVES("f_tile_leaves", "Forestry", "TileLeaves", 0, 0),
    FORESTRY_BLOCK_LEAVES("f_block_leaves", "Forestry", "BlockForestryLeaves", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    FORESTRY_TILE_SAPLING("f_tile_sapling", "Forestry", "TileSapling", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    FORESTRY_BLOCK_SAPLING("f_block_sapling", "Forestry", "BlockSapling", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),

    MA_BLOCK_MYSTICAL_CROP("ma_block_crop", "Mystical Agriculture", "BlockMysticalCrop", 0, 0),

    HO_BONEMEAL_MODULE("ho_bonemeal_module", "Hunger Overhaul", "BonemealModule", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),

    IE_BLOCK_CROP("ie_block_crop", "Immersive Engineering", "BlockIECrop", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),

    IC2_BLOCK_SAPLING("ic_block_sapling", "IndustrialCraft 2", "Ic2Sapling", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    IC2_CROP_VANILLA("ic_crop_vanilla", "IndustrialCraft 2", "CropVanilla", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    IC2_CROP_CARD("ic_crop_card", "IndustrialCraft 2", "CropCard", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    IC2_CROP_POTATO("ic_crop_potato", "IndustrialCraft 2", "CropPotato", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    IC2_CROP_STICKREED("ic_crop_stickreed", "IndustrialCraft 2", "CropStickreed", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    IC2_CROP_HOPS("ic_crop_hops", "IndustrialCraft 2", "CropHops", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    IC2_CROP_RED_WHEAT("ic_crop_red_wheat", "IndustrialCraft 2", "CropRedWheat", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    IC2_CROP_BASE_MUSHROOM("ic_crop_mushroom", "IndustrialCraft 2", "CropBaseMushroom", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    IC2_CROP_VENOMILLA("ic_crop_venomilla", "IndustrialCraft 2", "CropVenomilla", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    IC2_CROP_COLOR_FLOWER("ic_crop_flower", "IndustrialCraft 2", "CropColorFlower", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    IC2_CROP_BASE_METAL_COMMON("ic_crop_metal_common", "IndustrialCraft 2", "CropBaseMetalCommon", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    IC2_CROP_BASE_METAL_UNCOMMON("ic_crop_metal_uncommon", "IndustrialCraft 2", "CropBaseMetalUncommon", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    IC2_CROP_EATING("ic_crop_eating", "IndustrialCraft 2", "CropEating", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),

    PHC_BLOCK_SAPLING("phc_block_sapling", "Pam's HarvestCraft", "BlockPamSapling", 0, 0),
    PHC_BLOCK_CROP("phc_block_crop", "Pam's HarvestCraft", "BlockPamCrop", 0, 0),
    PHC_BLOCK_FRUIT("phc_block_fruit", "Pam's HarvestCraft", "BlockPamFruit", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),

    PLANTS_BLOCK_ENUM_SAPLING("p_block_enum_sapling", "Plants", "BlockEnumSapling", 0, 0),
    PLANTS_BLOCK_ENUM_HARVEST_BUSH("p_block_enum_harvest_bush", "Plants", "BlockEnumHarvestBush", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    PLANTS_BLOCK_BUSHLING("p_block_bushling", "Plants", "BlockBushling", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    PLANTS_BLOCK_ENUM_NETHER_HARVEST("p_block_enum_nether_harvest", "Plants", "BlockEnumNetherHarvest", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    PLANTS_BLOCK_ENUM_CROP("p_block_enum_crop", "Plants", "BlockEnumCrop", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    PLANTS_BLOCK_ENUM_DOUBLE_HARVEST_BUSH("p_block_enum_double_harvest_bush", "Plants", "BlockEnumDoubleHarvestBush", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),

    RUSTIC_BLOCK_SAPLING("r_block_sapling", "Rustic", "BlockSaplingRustic", 0, 0),
    RUSTIC_BLOCK_SAPLING_APPLE("r_block_sapling_apple", "Rustic", "BlockSaplingApple", 0, 0),
    RUSTIC_BLOCK_STAKE_CROP("r_block_crop", "Rustic", "BlockStakeCrop", 0, 0),
    RUSTIC_BLOCK_APPLE_SEEDS("r_block_apple_seeds", "Rustic", "BlockAppleSeeds", 0, 0),
    RUSTIC_BLOCK_BERRY_BUSH("r_block_berry_bush", "Rustic", "BlockBerryBush", 0, 0),
    RUSTIC_BLOCK_GRAPE_STEM("r_block_grape", "Rustic", "BlockGrapeStem", 0, 0),
    RUSTIC_BLOCK_HERB_BASE("r_block_herb", "Rustic", "BlockHerbBase", 0, 0),

    TC_BLOCK_SAPLING("tc_block_sapling", "Thaumcraft", "BlockSaplingTC", 0, 0),

    TF_BLOCK_SAPLING("tf_block_sapling", "Twilight Forest", "BlockTFSapling", 0, 0);

    public static Map<String, EnumInputClasses> classesById;

    private static final String HOOKS_CLASS = "austeretony/plantbiomes/common/core/PlantBiomesHooks";

    public final String transformedClassId, domain, clazz;

    public final int readerFlags, writerFlags;

    private boolean patch = true;

    EnumInputClasses(String id, String domain, String clazz, int readerFlags, int writerFlags) {
        this.transformedClassId = id;
        this.domain = domain;
        this.clazz = clazz;
        this.readerFlags = readerFlags;
        this.writerFlags = writerFlags;
    }

    public static void map() {
        classesById = new HashMap<String, EnumInputClasses>();
        for (EnumInputClasses enumClass : values()) 
            classesById.put(enumClass.transformedClassId, enumClass);
    }

    public boolean shouldPatch() {
        return this.patch;
    }

    public void setShouldPatch(boolean flag) {
        this.patch = flag;
    }

    public boolean patch(ClassNode classNode) {
        switch (this) {
        case MC_BLOCK_SAPLING:
            return patchMCBlockSapling(classNode);
        case MC_BLOCK_CROPS:
            return patchMCBlockCrops(classNode);
        case MC_BLOCK_MELON:
            return patchMCBlockStem(classNode); 
        case MC_BLOCK_GRASS:
            return patchMCBlockGrass(classNode);  
        case MC_BLOCK_MYCELIUM:
            return patchMCBlockMycelium(classNode); 
        case MC_BLOCK_COCOA:
            return patchMCBlockCocoa(classNode); 
        case MC_BLOCK_NETHER_WART:
            return patchMCBlockNetherWart(classNode); 
        case MC_BLOCK_VINE:
            return patchMCBlockVine(classNode); 
        case MC_BLOCK_MUSHROOM:
            return patchMCBlockMushroom(classNode); 
        case MC_BLOCK_REED:
            return patchMCBlockReed(classNode); 
        case MC_BLOCK_CACTUS:
            return patchMCBlockCactus(classNode); 

        case MC_BIOME:
            return patchMCBiome(classNode); 
        case MC_ITEM_DYE:
            return patchMCItemDye(classNode); 

        case AC_TILE_ENTITY_CROP:
            return patchACTileEntityCrop(classNode); 
        case AC_BLOCK_CROP:
            return patchACBlockCrop(classNode); 

        case BOP_BLOCK_SAPLING:
            return patchBOPBlockSapling(classNode); 
        case BOP_BLOCK_GRASS:
            return patchBOPBlockGrass(classNode); 
        case BOP_BLOCK_BAMBOO:
            return patchBOPBlockBamboo(classNode); 

        case DT_SPECIES:
            return patchDTSpecies(classNode);
        case DT_SPECIES_CACTUS:
            return patchDTSpeciesCactus(classNode); 
        case DT_BLOCK_FRUIT:
            return patchDTBlockFruit(classNode); 

        case FORESTRY_TILE_FRUIT_POD:
            return patchForestryTileFruitPod(classNode); 
        case FORESTRY_BLOCK_FRUIT_POD:
            return patchForestryBlockFruitPod(classNode);    
        case FORESTRY_TILE_LEAVES:
            return patchForestryTileLeaves(classNode); 
        case FORESTRY_BLOCK_LEAVES:
            return patchForestryBlockLeaves(classNode); 
        case FORESTRY_TILE_SAPLING:
            return patchForestryTileSapling(classNode); 
        case FORESTRY_BLOCK_SAPLING:
            return patchForestryBlockSapling(classNode); 

        case MA_BLOCK_MYSTICAL_CROP:
            return patchMABlockMysticalCrop(classNode); 

        case HO_BONEMEAL_MODULE:
            return patchHOBonemealModule(classNode); 

        case IE_BLOCK_CROP:
            return patchIEBlockCrop(classNode); 

        case IC2_BLOCK_SAPLING:
            return patchIC2Sapling(classNode);
        case IC2_CROP_VANILLA:
            return patchIC2CropVanilla(classNode); 
        case IC2_CROP_CARD:
            return patchIC2CropCard(classNode); 
        case IC2_CROP_POTATO:
            return patchIC2CropPotato(classNode); 
        case IC2_CROP_STICKREED:
            return patchIC2CropStickreed(classNode); 
        case IC2_CROP_HOPS:
            return patchIC2CropHops(classNode);
        case IC2_CROP_RED_WHEAT:
            return patchIC2CropRedWheat(classNode);
        case IC2_CROP_BASE_MUSHROOM:
            return patchIC2CropBaseMushroom(classNode);
        case IC2_CROP_VENOMILLA:
            return patchIC2CropVenomilla(classNode);
        case IC2_CROP_COLOR_FLOWER:
            return patchIC2CropColorFlower(classNode);
        case IC2_CROP_BASE_METAL_COMMON:
            return patchIC2CropBaseMetalCommon(classNode);
        case IC2_CROP_BASE_METAL_UNCOMMON:
            return patchIC2CropBaseMetalUncommon(classNode);
        case IC2_CROP_EATING:
            return patchIC2CropEating(classNode);

        case PHC_BLOCK_SAPLING:
            return patchPHCBlockPamSapling(classNode); 
        case PHC_BLOCK_CROP:
            return patchPHCBlockPamCrop(classNode); 
        case PHC_BLOCK_FRUIT:
            return patchPHCBlockPamFruit(classNode); 

        case PLANTS_BLOCK_ENUM_SAPLING:
            return patchPlantsBlockEnumSapling(classNode); 
        case PLANTS_BLOCK_ENUM_HARVEST_BUSH:
            return patchPlantsBlockEnumHarvestBush(classNode); 
        case PLANTS_BLOCK_BUSHLING:
            return patchPlantsBlockBushling(classNode); 
        case PLANTS_BLOCK_ENUM_NETHER_HARVEST:
            return patchPlantsBlockEnumNetherHarvest(classNode); 
        case PLANTS_BLOCK_ENUM_CROP:
            return patchPlantsBlockEnumCrop(classNode); 
        case PLANTS_BLOCK_ENUM_DOUBLE_HARVEST_BUSH:
            return patchPlantsBlockDoubleHarvetBush(classNode); 

        case RUSTIC_BLOCK_SAPLING:
            return patchRusticBlockSapling(classNode);
        case RUSTIC_BLOCK_SAPLING_APPLE:
            return patchRusticBlockSaplingApple(classNode);
        case RUSTIC_BLOCK_STAKE_CROP:
            return patchRusticBlockStakeCrop(classNode);
        case RUSTIC_BLOCK_APPLE_SEEDS:
            return patchRusticBlockAppleSeeds(classNode);
        case RUSTIC_BLOCK_BERRY_BUSH:
            return patchRusticBlockBerryBush(classNode);
        case RUSTIC_BLOCK_GRAPE_STEM:
            return patchRusticBlockGrapeStem(classNode);
        case RUSTIC_BLOCK_HERB_BASE:
            return patchRusticBlockHerbBase(classNode);

        case TC_BLOCK_SAPLING:
            return patchTCBlockSapling(classNode);

        case TF_BLOCK_SAPLING:
            return patchTFBlockSapling(classNode);
        }
        return false;
    }

    private boolean patchMCBlockSapling(ClassNode classNode) {
        String
        updateTickMethodName = PlantBiomesCorePlugin.isObfuscated() ? "b" : "updateTick",
                worldClassName = PlantBiomesCorePlugin.isObfuscated() ? "amu" : "net/minecraft/world/World",
                        blockPosClassName = PlantBiomesCorePlugin.isObfuscated() ? "et" : "net/minecraft/util/math/BlockPos",
                                iBlockStateClassName = PlantBiomesCorePlugin.isObfuscated() ? "awt" : "net/minecraft/block/state/IBlockState",
                                        blockClassName = PlantBiomesCorePlugin.isObfuscated() ? "aow" : "net/minecraft/block/Block",
                                                randomClassName = "java/util/Random";
        boolean isSuccessful = false;   
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {               
            if (methodNode.name.equals(updateTickMethodName) && methodNode.desc.equals("(L" + worldClassName + ";L" + blockPosClassName + ";L" + iBlockStateClassName + ";L" + randomClassName + ";)V")) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IF_ICMPLT) {                             
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 2));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 3));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedTick", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + blockClassName + ";L" + iBlockStateClassName + ";)Z", false));
                        nodesList.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) currentInsn).label));
                        methodNode.instructions.insertBefore(currentInsn.getPrevious().getPrevious().getPrevious().getPrevious().getPrevious(), nodesList); 
                        isSuccessful = true;                        
                        break;
                    }
                }    
                break;
            }
        }
        return isSuccessful;
    }

    private boolean patchMCBlockCrops(ClassNode classNode) {
        return patchMCBlockSapling(classNode);
    }

    private boolean patchMCBlockStem(ClassNode classNode) {
        return patchMCBlockSapling(classNode);
    }

    private boolean patchMCBlockGrass(ClassNode classNode) {
        String
        updateTickMethodName = PlantBiomesCorePlugin.isObfuscated() ? "b" : "updateTick",
                growMethodName = PlantBiomesCorePlugin.isObfuscated() ? "b" : "grow",
                        getBlockMethodName = PlantBiomesCorePlugin.isObfuscated() ? "u" : "getBlock",
                                worldClassName = PlantBiomesCorePlugin.isObfuscated() ? "amu" : "net/minecraft/world/World",
                                        blockPosClassName = PlantBiomesCorePlugin.isObfuscated() ? "et" : "net/minecraft/util/math/BlockPos",
                                                iBlockStateClassName = PlantBiomesCorePlugin.isObfuscated() ? "awt" : "net/minecraft/block/state/IBlockState",
                                                        blockClassName = PlantBiomesCorePlugin.isObfuscated() ? "aow" : "net/minecraft/block/Block",
                                                                randomClassName = "java/util/Random";
        boolean isSuccessful = false;   
        int ifeqCount = 0;
        AbstractInsnNode currentInsn;
        for (MethodNode methodNode : classNode.methods) {               
            if (methodNode.name.equals(updateTickMethodName) && methodNode.desc.equals("(L" + worldClassName + ";L" + blockPosClassName + ";L" + iBlockStateClassName + ";L" + randomClassName + ";)V")) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IF_ICMPLT) {                             
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 2));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 3));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedTick", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + blockClassName + ";L" + iBlockStateClassName + ";)Z", false));
                        nodesList.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) currentInsn).label));
                        methodNode.instructions.insertBefore(currentInsn.getPrevious().getPrevious().getPrevious().getPrevious().getPrevious(), nodesList);                     
                        isSuccessful = true;                        
                        break;
                    }
                }    
            }
            if (methodNode.name.equals(growMethodName) && methodNode.desc.equals("(L" + worldClassName + ";L" + randomClassName + ";L" + blockPosClassName + ";L" + iBlockStateClassName + ";)V")) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IFEQ) { 
                        ifeqCount++;
                        if (ifeqCount == 2) {
                            InsnList nodesList = new InsnList();   
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 3));
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 9));
                            nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iBlockStateClassName, getBlockMethodName, "()L" + blockClassName + ";", true));
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 9));
                            nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedTick", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + blockClassName + ";L" + iBlockStateClassName + ";)Z", false));
                            nodesList.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) currentInsn).label));
                            methodNode.instructions.insertBefore(currentInsn.getPrevious().getPrevious().getPrevious().getPrevious().getPrevious(), nodesList); 
                            isSuccessful = true;                        
                            break;
                        }
                    }
                }                                           
                break;
            }
        }
        return isSuccessful;
    }

    private boolean patchMCBlockMycelium(ClassNode classNode) {
        return patchMCBlockGrass(classNode);
    }

    private boolean patchMCBlockCocoa(ClassNode classNode) {
        String
        updateTickMethodName = PlantBiomesCorePlugin.isObfuscated() ? "b" : "updateTick",
                worldClassName = PlantBiomesCorePlugin.isObfuscated() ? "amu" : "net/minecraft/world/World",
                        blockPosClassName = PlantBiomesCorePlugin.isObfuscated() ? "et" : "net/minecraft/util/math/BlockPos",
                                iBlockStateClassName = PlantBiomesCorePlugin.isObfuscated() ? "awt" : "net/minecraft/block/state/IBlockState",
                                        blockClassName = PlantBiomesCorePlugin.isObfuscated() ? "aow" : "net/minecraft/block/Block",
                                                randomClassName = "java/util/Random";
        boolean isSuccessful = false;   
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {               
            if (methodNode.name.equals(updateTickMethodName) && methodNode.desc.equals("(L" + worldClassName + ";L" + blockPosClassName + ";L" + iBlockStateClassName + ";L" + randomClassName + ";)V")) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IF_ICMPGE) {                             
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 2));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 3));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedTick", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + blockClassName + ";L" + iBlockStateClassName + ";)Z", false));
                        nodesList.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) currentInsn).label));
                        methodNode.instructions.insertBefore(currentInsn.getPrevious().getPrevious(), nodesList); 
                        isSuccessful = true;                        
                        break;
                    }
                }    
                break;
            }
        }
        return isSuccessful;
    }

    private boolean patchMCBlockNetherWart(ClassNode classNode) {
        return patchMCBlockCocoa(classNode);
    }

    private boolean patchMCBlockVine(ClassNode classNode) {
        String
        updateTickMethodName = PlantBiomesCorePlugin.isObfuscated() ? "b" : "updateTick",
                worldClassName = PlantBiomesCorePlugin.isObfuscated() ? "amu" : "net/minecraft/world/World",
                        blockPosClassName = PlantBiomesCorePlugin.isObfuscated() ? "et" : "net/minecraft/util/math/BlockPos",
                                iBlockStateClassName = PlantBiomesCorePlugin.isObfuscated() ? "awt" : "net/minecraft/block/state/IBlockState",
                                        blockClassName = PlantBiomesCorePlugin.isObfuscated() ? "aow" : "net/minecraft/block/Block",
                                                randomClassName = "java/util/Random";
        boolean isSuccessful = false;   
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {               
            if (methodNode.name.equals(updateTickMethodName) && methodNode.desc.equals("(L" + worldClassName + ";L" + blockPosClassName + ";L" + iBlockStateClassName + ";L" + randomClassName + ";)V")) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IFNE) {                             
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 2));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 3));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedTick", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + blockClassName + ";L" + iBlockStateClassName + ";)Z", false));
                        nodesList.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) currentInsn).label));
                        methodNode.instructions.insertBefore(currentInsn.getPrevious().getPrevious().getPrevious().getPrevious(), nodesList);
                        isSuccessful = true;                        
                        break;
                    }
                }    
                break;
            }
        }        
        return isSuccessful;
    }

    private boolean patchMCBlockMushroom(ClassNode classNode) {
        String
        updateTickMethodName = PlantBiomesCorePlugin.isObfuscated() ? "b" : "updateTick",
                worldClassName = PlantBiomesCorePlugin.isObfuscated() ? "amu" : "net/minecraft/world/World",
                        blockPosClassName = PlantBiomesCorePlugin.isObfuscated() ? "et" : "net/minecraft/util/math/BlockPos",
                                iBlockStateClassName = PlantBiomesCorePlugin.isObfuscated() ? "awt" : "net/minecraft/block/state/IBlockState",
                                        blockClassName = PlantBiomesCorePlugin.isObfuscated() ? "aow" : "net/minecraft/block/Block",
                                                randomClassName = "java/util/Random";
        boolean isSuccessful = false;   
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {               
            if (methodNode.name.equals(updateTickMethodName) && methodNode.desc.equals("(L" + worldClassName + ";L" + blockPosClassName + ";L" + iBlockStateClassName + ";L" + randomClassName + ";)V")) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IFNE) {                             
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 2));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 3));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedTick", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + blockClassName + ";L" + iBlockStateClassName + ";)Z", false));
                        nodesList.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) currentInsn).label));
                        methodNode.instructions.insertBefore(currentInsn.getPrevious().getPrevious().getPrevious(), nodesList);
                        isSuccessful = true;                        
                        break;
                    }
                }    
                break;
            }
        }
        return isSuccessful;
    }

    private boolean patchMCBlockReed(ClassNode classNode) {
        String
        updateTickMethodName = PlantBiomesCorePlugin.isObfuscated() ? "b" : "updateTick",
                worldClassName = PlantBiomesCorePlugin.isObfuscated() ? "amu" : "net/minecraft/world/World",
                        blockPosClassName = PlantBiomesCorePlugin.isObfuscated() ? "et" : "net/minecraft/util/math/BlockPos",
                                iBlockStateClassName = PlantBiomesCorePlugin.isObfuscated() ? "awt" : "net/minecraft/block/state/IBlockState",
                                        blockClassName = PlantBiomesCorePlugin.isObfuscated() ? "aow" : "net/minecraft/block/Block",
                                                randomClassName = "java/util/Random";
        boolean isSuccessful = false;   
        int ifeqCount = 0;
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {               
            if (methodNode.name.equals(updateTickMethodName) && methodNode.desc.equals("(L" + worldClassName + ";L" + blockPosClassName + ";L" + iBlockStateClassName + ";L" + randomClassName + ";)V")) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IFEQ) {   
                        ifeqCount++;
                        if (ifeqCount == 2) {
                            InsnList nodesList = new InsnList();   
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 2));
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 3));
                            nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedTick", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + blockClassName + ";L" + iBlockStateClassName + ";)Z", false));
                            nodesList.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) currentInsn).label));
                            methodNode.instructions.insertBefore(currentInsn.getPrevious().getPrevious().getPrevious().getPrevious(), nodesList); 
                            isSuccessful = true;                        
                            break;
                        }
                    }
                }    
                break;
            }
        }      
        return isSuccessful;
    }

    private boolean patchMCBlockCactus(ClassNode classNode) {
        String
        updateTickMethodName = PlantBiomesCorePlugin.isObfuscated() ? "b" : "updateTick",
                worldClassName = PlantBiomesCorePlugin.isObfuscated() ? "amu" : "net/minecraft/world/World",
                        blockPosClassName = PlantBiomesCorePlugin.isObfuscated() ? "et" : "net/minecraft/util/math/BlockPos",
                                iBlockStateClassName = PlantBiomesCorePlugin.isObfuscated() ? "awt" : "net/minecraft/block/state/IBlockState",
                                        blockClassName = PlantBiomesCorePlugin.isObfuscated() ? "aow" : "net/minecraft/block/Block",
                                                randomClassName = "java/util/Random";
        boolean isSuccessful = false;   
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {               
            if (methodNode.name.equals(updateTickMethodName) && methodNode.desc.equals("(L" + worldClassName + ";L" + blockPosClassName + ";L" + iBlockStateClassName + ";L" + randomClassName + ";)V")) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IFEQ) {   
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 2));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 3));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedTick", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + blockClassName + ";L" + iBlockStateClassName + ";)Z", false));
                        nodesList.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) currentInsn).label));
                        methodNode.instructions.insertBefore(currentInsn.getPrevious().getPrevious().getPrevious(), nodesList); 
                        isSuccessful = true;                        
                        break;
                    }
                }    
                break;
            }
        }      
        return isSuccessful;
    }

    private boolean patchMCBiome(ClassNode classNode) {
        String
        stateFieldName = "state",
        plantFlowerMethodName = "plantFlower",
        getBlockMethodName = PlantBiomesCorePlugin.isObfuscated() ? "u" : "getBlock",
                worldClassName = PlantBiomesCorePlugin.isObfuscated() ? "amu" : "net/minecraft/world/World",
                        blockPosClassName = PlantBiomesCorePlugin.isObfuscated() ? "et" : "net/minecraft/util/math/BlockPos",
                                flowerEntryClassName = PlantBiomesCorePlugin.isObfuscated() ? "anh$FlowerEntry" : "net/minecraft/world/biome/Biome$FlowerEntry",
                                        iBlockStateClassName = PlantBiomesCorePlugin.isObfuscated() ? "awt" : "net/minecraft/block/state/IBlockState",
                                                blockClassName = PlantBiomesCorePlugin.isObfuscated() ? "aow" : "net/minecraft/block/Block",
                                                        randomClassName = "java/util/Random";
        boolean isSuccessful = false;        
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {               
            if (methodNode.name.equals(plantFlowerMethodName) && methodNode.desc.equals("(L" + worldClassName + ";L" + randomClassName + ";L" + blockPosClassName + ";)V")) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IFNULL) {                             
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 3));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 4));
                        nodesList.add(new FieldInsnNode(Opcodes.GETFIELD, flowerEntryClassName, stateFieldName, "L" + iBlockStateClassName + ";"));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iBlockStateClassName, getBlockMethodName, "()L" + blockClassName + ";", true));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 4));
                        nodesList.add(new FieldInsnNode(Opcodes.GETFIELD, flowerEntryClassName, stateFieldName, "L" + iBlockStateClassName + ";"));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedTick", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + blockClassName + ";L" + iBlockStateClassName + ";)Z", false));
                        nodesList.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) currentInsn).label));
                        methodNode.instructions.insertBefore(currentInsn.getPrevious(), nodesList); 
                        isSuccessful = true;                        
                        break;
                    }
                }                                           
                break;
            }
        }
        return isSuccessful;
    }

    private boolean patchMCItemDye(ClassNode classNode) {
        String
        onItemUseMethodName = "applyBonemeal",
        itemStackClassName = PlantBiomesCorePlugin.isObfuscated() ? "aip" : "net/minecraft/item/ItemStack",
                worldClassName = PlantBiomesCorePlugin.isObfuscated() ? "amu" : "net/minecraft/world/World",
                        blockPosClassName = PlantBiomesCorePlugin.isObfuscated() ? "et" : "net/minecraft/util/math/BlockPos",
                                entityPlayerClassName = PlantBiomesCorePlugin.isObfuscated() ? "aed" : "net/minecraft/entity/player/EntityPlayer",
                                        enumHandClassName = PlantBiomesCorePlugin.isObfuscated() ? "ub" : "net/minecraft/util/EnumHand";
        boolean isSuccessful = false;  
        int ifeqCount = 0;
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {               
            if (methodNode.name.equals(onItemUseMethodName) && methodNode.desc.equals("(L" + itemStackClassName + ";L" + worldClassName + ";L" + blockPosClassName + ";L" + entityPlayerClassName + ";L" + enumHandClassName + ";)Z")) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IFEQ) {  
                        ifeqCount++;
                        if (ifeqCount == 2) {
                            InsnList nodesList = new InsnList();   
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 2));
                            nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedBonemeal", "(L" + worldClassName + ";L" + blockPosClassName + ";)Z", false));
                            nodesList.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) currentInsn).label));
                            methodNode.instructions.insertBefore(currentInsn.getPrevious().getPrevious().getPrevious(), nodesList); 
                            isSuccessful = true;                        
                            break;
                        }
                    }
                }                                           
                break;
            }
        }
        return isSuccessful;
    }

    private boolean patchACTileEntityCrop(ClassNode classNode) {
        String
        worldFieldName = "field_145850_b",
        posFieldName = "field_174879_c",
        seedFieldName = "seed",
        applyGrowthTickMethodName = "applyGrowthTick",
        getPlantMethodName = "getPlant",
        getIdMethodName = "getId",
        tileCropClassName = "com/infinityraider/agricraft/tiles/TileEntityCrop",
        iAgriSeedClassName = "com/infinityraider/agricraft/api/v1/seed/AgriSeed",
        iAgriPlantClassName = "com/infinityraider/agricraft/api/v1/plant/IAgriPlant",
        worldClassName = "net/minecraft/world/World",
        blockPosClassName = "net/minecraft/util/math/BlockPos",
        stringClassName = "java/lang/String",
        enumPlantTypeClassName = "austeretony/plantbiomes/common/main/EnumPlantType";
        boolean isSuccessful = false;
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {               
            if (methodNode.name.equals(applyGrowthTickMethodName)) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.ICONST_1) {   
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                        nodesList.add(new FieldInsnNode(Opcodes.GETFIELD, tileCropClassName, worldFieldName, "L" + worldClassName + ";"));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                        nodesList.add(new FieldInsnNode(Opcodes.GETFIELD, tileCropClassName, posFieldName, "L" + blockPosClassName + ";"));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                        nodesList.add(new FieldInsnNode(Opcodes.GETFIELD, tileCropClassName, seedFieldName, "L" + iAgriSeedClassName + ";"));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, iAgriSeedClassName, getPlantMethodName, "()L" + iAgriPlantClassName + ";", false));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iAgriPlantClassName, getIdMethodName, "()L" + stringClassName + ";", true));
                        nodesList.add(new FieldInsnNode(Opcodes.GETSTATIC, enumPlantTypeClassName, "AGRICRAFT_CROP", "L" + enumPlantTypeClassName + ";"));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedTickSpecialInt", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";L" + enumPlantTypeClassName + ";)I", false));
                        methodNode.instructions.insertBefore(currentInsn, nodesList); 
                        methodNode.instructions.remove(currentInsn);
                        isSuccessful = true;                        
                        break;
                    }
                }                                           
                break;
            }
        }
        return isSuccessful;
    }

    //TODO Fix denied biome check to spawn smoke particles. Current transformation breaks interaction.
    private boolean patchACBlockCrop(ClassNode classNode) {
        String
        onBlockActivatedMethodName = "func_180639_a",
        getSeedMethodName = "getSeed",
        getPlantMethodName = "getPlant",
        getIdMethodName = "getId",
        tileCropClassName = "com/infinityraider/agricraft/tiles/TileEntityCrop",
        iAgriSeedClassName = "com/infinityraider/agricraft/api/v1/seed/AgriSeed",
        iAgriPlantClassName = "com/infinityraider/agricraft/api/v1/plant/IAgriPlant",
        worldClassName = "net/minecraft/world/World",
        blockPosClassName = "net/minecraft/util/math/BlockPos",
        entityPlayerClassName = "net/minecraft/entity/player/EntityPlayer",
        stringClassName = "java/lang/String",
        enumPlantTypeClassName = "austeretony/plantbiomes/common/main/EnumPlantType";
        boolean isSuccessful = false;  
        int ifeqCount = 0;
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {               
            if (methodNode.name.equals(onBlockActivatedMethodName)) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IFEQ) {    
                        ifeqCount++;
                        if (ifeqCount == 3) {
                            InsnList nodesList = new InsnList();   
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 2));
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 11));
                            nodesList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, tileCropClassName, getSeedMethodName, "()L" + iAgriSeedClassName + ";", false));
                            nodesList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, iAgriSeedClassName, getPlantMethodName, "()L" + iAgriPlantClassName + ";", false));
                            nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iAgriPlantClassName, getIdMethodName, "()L" + stringClassName + ";", true));
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 4));
                            nodesList.add(new FieldInsnNode(Opcodes.GETSTATIC, enumPlantTypeClassName, "AGRICRAFT_CROP", "L" + enumPlantTypeClassName + ";"));
                            nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "showDeniedBiomeOnBonemealUseSpecial", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";L" + entityPlayerClassName + ";L" + enumPlantTypeClassName + ";)V", false));
                            methodNode.instructions.insertBefore(currentInsn.getPrevious().getPrevious(), nodesList); 
                            isSuccessful = true;                        
                            break;
                        }
                    }
                }                                      
                break;
            }
        }

        return isSuccessful;    
    }

    private boolean patchBOPBlockSapling(ClassNode classNode) {
        String
        updateTickMethodName = "func_180650_b",
        worldClassName = "net/minecraft/world/World",
        blockPosClassName = "net/minecraft/util/math/BlockPos",
        iBlockStateClassName = "net/minecraft/block/state/IBlockState",
        blockClassName = "net/minecraft/block/Block",
        randomClassName = "java/util/Random";
        boolean isSuccessful = false;        
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {               
            if (methodNode.name.equals(updateTickMethodName) && methodNode.desc.equals("(L" + worldClassName + ";L" + blockPosClassName + ";L" + iBlockStateClassName + ";L" + randomClassName + ";)V")) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IF_ICMPLT) {                             
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 2));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 3));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedTick", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + blockClassName + ";L" + iBlockStateClassName + ";)Z", false));
                        nodesList.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) currentInsn).label));
                        methodNode.instructions.insertBefore(currentInsn.getPrevious().getPrevious().getPrevious().getPrevious().getPrevious(), nodesList); 
                        isSuccessful = true;                        
                        break;
                    }
                }                                           
                break;
            }
        }
        return isSuccessful;
    }

    private boolean patchBOPBlockGrass(ClassNode classNode) {
        String
        spreadGrassMethodName = "spreadGrass",
        worldClassName = "net/minecraft/world/World",
        blockPosClassName = "net/minecraft/util/math/BlockPos",
        iBlockStateClassName = "net/minecraft/block/state/IBlockState",
        blockClassName = "net/minecraft/block/Block",
        randomClassName = "java/util/Random";
        boolean isSuccessful = false;        
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {               
            if (methodNode.name.equals(spreadGrassMethodName)) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IF_ICMPLT) {                             
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 2));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 3));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedTick", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + blockClassName + ";L" + iBlockStateClassName + ";)Z", false));
                        nodesList.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) currentInsn).label));
                        methodNode.instructions.insertBefore(currentInsn.getPrevious().getPrevious().getPrevious().getPrevious().getPrevious(), nodesList); 
                        isSuccessful = true;                        
                        break;
                    }
                }                                           
                break;
            }
        }
        return isSuccessful;
    }

    private boolean patchBOPBlockBamboo(ClassNode classNode) {
        String
        updateTickMethodName = "func_180650_b",
        worldClassName = "net/minecraft/world/World",
        blockPosClassName = "net/minecraft/util/math/BlockPos",
        iBlockStateClassName = "net/minecraft/block/state/IBlockState",
        blockClassName = "net/minecraft/block/Block",
        randomClassName = "java/util/Random";
        boolean isSuccessful = false;        
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {               
            if (methodNode.name.equals(updateTickMethodName) && methodNode.desc.equals("(L" + worldClassName + ";L" + blockPosClassName + ";L" + iBlockStateClassName + ";L" + randomClassName + ";)V")) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IF_ICMPGE) {                             
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 2));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 3));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedTick", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + blockClassName + ";L" + iBlockStateClassName + ";)Z", false));
                        nodesList.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) currentInsn).label));
                        methodNode.instructions.insertBefore(currentInsn.getPrevious().getPrevious(), nodesList); 
                        isSuccessful = true;                        
                        break;
                    }
                }                                           
                break;
            }
        }
        return isSuccessful;
    }

    private boolean patchDTSpecies(ClassNode classNode) {
        String
        transitionToTreeMethodName = "transitionToTree",
        getNameMethodName = "getName",
        worldClassName = "net/minecraft/world/World",
        blockPosClassName = "net/minecraft/util/math/BlockPos",
        resourceLocationClassName = "net/minecraft/util/ResourceLocation",
        treeFamilyClassName = "com/ferreusveritas/dynamictrees/trees/TreeFamily";
        boolean isSuccessful = false;        
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {               
            if (methodNode.name.equals(transitionToTreeMethodName) && methodNode.desc.equals("(L" + worldClassName + ";L" + blockPosClassName + ";)Z")) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IFEQ) {                             
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 2));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 3));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, treeFamilyClassName, getNameMethodName, "()L" + resourceLocationClassName + ";", false));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedTickDynamicTree", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + resourceLocationClassName + ";)Z", false));
                        nodesList.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) currentInsn).label));
                        methodNode.instructions.insertBefore(currentInsn.getPrevious().getPrevious().getPrevious().getPrevious(), nodesList); 
                        isSuccessful = true;                        
                        break;
                    }
                }                                           
                break;
            }
        }
        return isSuccessful;
    }

    private boolean patchDTSpeciesCactus(ClassNode classNode) {
        return patchDTSpecies(classNode);
    }

    private boolean patchDTBlockFruit(ClassNode classNode) {
        return patchBOPBlockBamboo(classNode);
    }

    private boolean patchForestryTileFruitPod(ClassNode classNode) {
        String
        alleleFieldName = "allele",
        onBlockTickMethodName = "onBlockTick",
        getUIDMethodName = "getUID",
        tileFruitPodClassName = "forestry/arboriculture/tiles/TileFruitPod",
        iAlleleFruitClassName = "forestry/api/arboriculture/IAlleleFruit",
        worldClassName = "net/minecraft/world/World",
        blockPosClassName = "net/minecraft/util/math/BlockPos",
        stringClassName = "java/lang/String",
        enumPlantTypeClassName = "austeretony/plantbiomes/common/main/EnumPlantType";
        boolean isSuccessful = false;        
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.name.equals(onBlockTickMethodName)) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IFEQ) {     
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 2));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                        nodesList.add(new FieldInsnNode(Opcodes.GETFIELD, tileFruitPodClassName, alleleFieldName, "L" + iAlleleFruitClassName + ";"));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iAlleleFruitClassName, getUIDMethodName, "()L" + stringClassName + ";", true));
                        nodesList.add(new FieldInsnNode(Opcodes.GETSTATIC, enumPlantTypeClassName, "FORESTRY_FRUIT", "L" + enumPlantTypeClassName + ";"));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedTickSpecial", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";L" + enumPlantTypeClassName + ";)Z", false));
                        nodesList.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) currentInsn).label));
                        methodNode.instructions.insertBefore(currentInsn.getPrevious().getPrevious(), nodesList); 
                        isSuccessful = true;                        
                        break;
                    }
                } 
                break;
            }
        }
        return isSuccessful;
    }

    private boolean patchForestryBlockFruitPod(ClassNode classNode) {
        String
        growMethodName = "func_176474_b",
        serializeNBTMethodName = "serializeNBT",
        getStringMethodName = "func_74779_i",
        tileFruitPodClassName = "forestry/arboriculture/tiles/TileFruitPod",
        nbtTagCompoundClassName = "net/minecraft/nbt/NBTTagCompound",
        worldClassName = "net/minecraft/world/World",
        blockPosClassName = "net/minecraft/util/math/BlockPos",
        stringClassName = "java/lang/String",
        enumPlantTypeClassName = "austeretony/plantbiomes/common/main/EnumPlantType";
        boolean isSuccessful = false;        
        int ldcCount = 0;
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.name.equals(growMethodName)) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();  
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();  
                    if (currentInsn.getOpcode() == Opcodes.LDC) {       
                        ldcCount++;
                        if (ldcCount == 2) {
                            InsnList nodesList = new InsnList();   
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 3));
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 5));
                            nodesList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, tileFruitPodClassName, serializeNBTMethodName, "()L" + nbtTagCompoundClassName + ";", false));
                            nodesList.add(new LdcInsnNode("UID"));//fruid id key
                            nodesList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, nbtTagCompoundClassName, getStringMethodName, "(L" + stringClassName + ";)L" + stringClassName + ";", false));
                            nodesList.add(new FieldInsnNode(Opcodes.GETSTATIC, enumPlantTypeClassName, "FORESTRY_FRUIT", "L" + enumPlantTypeClassName + ";"));
                            nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedWithBonemealSpecialFloat", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";L" + enumPlantTypeClassName + ";)F", false));
                            methodNode.instructions.insertBefore(currentInsn, nodesList); 
                            methodNode.instructions.remove(currentInsn);
                            isSuccessful = true;                        
                            break;
                        }
                    }
                }                                           
                break;
            }
        }
        return isSuccessful;
    }

    private boolean patchForestryTileLeaves(ClassNode classNode) {
        String
        onBlockTickMethodName = "onBlockTick",
        getIdentMethodName = "getIdent",
        iTreeClassName = "forestry/api/arboriculture/ITree",
        worldClassName = "net/minecraft/world/World",
        blockPosClassName = "net/minecraft/util/math/BlockPos",
        stringClassName = "java/lang/String",
        enumPlantTypeClassName = "austeretony/plantbiomes/common/main/EnumPlantType";
        boolean isSuccessful = false;       
        int ifgeCount = 0;
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.name.equals(onBlockTickMethodName)) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.FCMPG) {    
                        ifgeCount++;
                        if (ifgeCount == 1) {
                            InsnList nodesList = new InsnList();   
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 2));
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 5));
                            nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iTreeClassName, getIdentMethodName, "()L" + stringClassName + ";", true));
                            nodesList.add(new FieldInsnNode(Opcodes.GETSTATIC, enumPlantTypeClassName, "FORESTRY_LEAVES", "L" + enumPlantTypeClassName + ";"));
                            nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedTickSpecial", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";L" + enumPlantTypeClassName + ";)Z", false));
                            nodesList.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) currentInsn.getNext()).label));
                            methodNode.instructions.insertBefore(currentInsn.getPrevious().getPrevious().getPrevious(), nodesList); 
                            isSuccessful = true;                        
                            break;
                        }
                    }
                } 
                break;
            }
        }
        return isSuccessful;
    }

    private boolean patchForestryBlockLeaves(ClassNode classNode) {
        String
        growMethodName = "func_176474_b",
        getTreeMethodName = "getTree",
        getIdentMethodName = "getIdent",
        tileFruitPodClassName = "forestry/arboriculture/tiles/TileFruitPod",
        iTreeClassName = "forestry/api/arboriculture/ITree",
        tileLeavesClassName = "forestry/arboriculture/tiles/TileLeaves",
        worldClassName = "net/minecraft/world/World",
        blockPosClassName = "net/minecraft/util/math/BlockPos",
        stringClassName = "java/lang/String",
        enumPlantTypeClassName = "austeretony/plantbiomes/common/main/EnumPlantType";
        boolean isSuccessful = false;      
        int ldcCount = 0;
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.name.equals(growMethodName)) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();  
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();  
                    if (currentInsn.getOpcode() == Opcodes.LDC) {
                        ldcCount++;
                        if (ldcCount == 2) {
                            InsnList nodesList = new InsnList();   
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 3));
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 5));
                            nodesList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, tileLeavesClassName, getTreeMethodName, "()L" + iTreeClassName + ";", false));
                            nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iTreeClassName, getIdentMethodName, "()L" + stringClassName + ";", true));
                            nodesList.add(new FieldInsnNode(Opcodes.GETSTATIC, enumPlantTypeClassName, "FORESTRY_LEAVES", "L" + enumPlantTypeClassName + ";"));
                            nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedWithBonemealSpecialFloat", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";L" + enumPlantTypeClassName + ";)F", false));
                            methodNode.instructions.insertBefore(currentInsn, nodesList); 
                            methodNode.instructions.remove(currentInsn);
                            isSuccessful = true;                        
                            break;
                        }
                    }
                }                                           
                break;
            }
        }
        return isSuccessful;
    }

    private boolean patchForestryTileSapling(ClassNode classNode) {
        String
        worldFieldName = "field_145850_b",
        posFieldName = "field_174879_c",
        canAcceptBoneMealMethodName = "canAcceptBoneMeal",
        tryGrowMethodName = "tryGrow",
        getIdentMethodName = "getIdent",
        tileSaplingClassName = "forestry/arboriculture/tiles/TileSapling",
        iTreeClassName = "forestry/api/arboriculture/ITree",
        worldClassName = "net/minecraft/world/World",
        blockPosClassName = "net/minecraft/util/math/BlockPos",
        stringClassName = "java/lang/String",
        enumPlantTypeClassName = "austeretony/plantbiomes/common/main/EnumPlantType";
        boolean isSuccessful = false;        
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.name.equals(canAcceptBoneMealMethodName)) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IFNONNULL) {     
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                        nodesList.add(new FieldInsnNode(Opcodes.GETFIELD, tileSaplingClassName, worldFieldName, "L" + worldClassName + ";"));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                        nodesList.add(new FieldInsnNode(Opcodes.GETFIELD, tileSaplingClassName, posFieldName, "L" + blockPosClassName + ";"));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 2));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iTreeClassName, getIdentMethodName, "()L" + stringClassName + ";", true));
                        nodesList.add(new FieldInsnNode(Opcodes.GETSTATIC, enumPlantTypeClassName, "FORESTRY_SAPLING", "L" + enumPlantTypeClassName + ";"));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedWithBonemealSpecial", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";L" + enumPlantTypeClassName + ";)Z", false));
                        nodesList.add(new JumpInsnNode(Opcodes.IFNE, ((JumpInsnNode) currentInsn).label));
                        methodNode.instructions.insertBefore(currentInsn.getPrevious(), nodesList);                   
                        break;
                    }
                } 
                if (methodNode.name.equals(tryGrowMethodName))
                    break;
            }
            if (methodNode.name.equals(tryGrowMethodName)) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IFNONNULL) {     
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                        nodesList.add(new FieldInsnNode(Opcodes.GETFIELD, tileSaplingClassName, worldFieldName, "L" + worldClassName + ";"));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                        nodesList.add(new FieldInsnNode(Opcodes.GETFIELD, tileSaplingClassName, posFieldName, "L" + blockPosClassName + ";"));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 3));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iTreeClassName, getIdentMethodName, "()L" + stringClassName + ";", true));
                        nodesList.add(new FieldInsnNode(Opcodes.GETSTATIC, enumPlantTypeClassName, "FORESTRY_SAPLING", "L" + enumPlantTypeClassName + ";"));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedTickSpecial", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";L" + enumPlantTypeClassName + ";)Z", false));
                        nodesList.add(new JumpInsnNode(Opcodes.IFNE, ((JumpInsnNode) currentInsn).label));
                        methodNode.instructions.insertBefore(currentInsn.getPrevious(), nodesList); 
                        isSuccessful = true;                        
                        break;
                    }
                } 
                if (methodNode.name.equals(tryGrowMethodName))
                    break;
            }
        }
        return isSuccessful;
    }

    private boolean patchForestryBlockSapling(ClassNode classNode) {
        String
        canUseBonemealMethodName = "func_180670_a",
        getTreeMethodName = "getTree",
        getIdentMethodName = "getIdent",
        tileSaplingClassName = "forestry/arboriculture/tiles/TileSapling",
        iTreeClassName = "forestry/api/arboriculture/ITree",
        worldClassName = "net/minecraft/world/World",
        blockPosClassName = "net/minecraft/util/math/BlockPos",
        stringClassName = "java/lang/String",
        enumPlantTypeClassName = "austeretony/plantbiomes/common/main/EnumPlantType";
        boolean isSuccessful = false;        
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.name.equals(canUseBonemealMethodName)) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();  
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();  
                    if (currentInsn.getOpcode() == Opcodes.ASTORE) {         
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 3));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 5));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, tileSaplingClassName, getTreeMethodName, "()L" + iTreeClassName + ";", false));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iTreeClassName, getIdentMethodName, "()L" + stringClassName + ";", true));
                        nodesList.add(new FieldInsnNode(Opcodes.GETSTATIC, enumPlantTypeClassName, "FORESTRY_SAPLING", "L" + enumPlantTypeClassName + ";"));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedWithBonemealSpecial", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";L" + enumPlantTypeClassName + ";)Z", false));
                        nodesList.add(new InsnNode(Opcodes.IRETURN));
                        methodNode.instructions.insert(currentInsn, nodesList); 
                        isSuccessful = true;                        
                        break;
                    }
                }                                           
                break;
            }
        }
        return isSuccessful;
    }

    private boolean patchMABlockMysticalCrop(ClassNode classNode) {
        return patchBOPBlockSapling(classNode);
    }

    private boolean patchHOBonemealModule(ClassNode classNode) {
        String
        onBonemealUsedMethodName = "onBonemealUsed",
        getWorldMethodName = "getWorld",
        getPosMethodName = "getPos",
        bonemealEventClassName = "net/minecraftforge/event/entity/player/BonemealEvent",
        worldClassName = "net/minecraft/world/World",
        blockPosClassName = "net/minecraft/util/math/BlockPos";
        boolean isSuccessful = false;  
        int ifeqCount = 0;
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {               
            if (methodNode.name.equals(onBonemealUsedMethodName)) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IFEQ) {   
                        ifeqCount++;
                        if (ifeqCount == 2) {
                            InsnList nodesList = new InsnList();   
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                            nodesList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, bonemealEventClassName, getWorldMethodName, "()L" + worldClassName + ";", false));
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                            nodesList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, bonemealEventClassName, getPosMethodName, "()L" + blockPosClassName + ";", false));
                            nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedBonemeal", "(L" + worldClassName + ";L" + blockPosClassName + ";)Z", false));
                            nodesList.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) currentInsn).label));
                            methodNode.instructions.insertBefore(currentInsn.getPrevious().getPrevious(), nodesList); 
                            isSuccessful = true;                        
                            break;
                        }
                    }
                }    
                break;
            }
        }
        return isSuccessful;
    }

    private boolean patchIEBlockCrop(ClassNode classNode) {
        String
        updateTickMethodName = "func_180650_b",
        worldClassName = "net/minecraft/world/World",
        blockPosClassName = "net/minecraft/util/math/BlockPos",
        iBlockStateClassName = "net/minecraft/block/state/IBlockState",
        blockClassName = "net/minecraft/block/Block",
        randomClassName = "java/util/Random";
        boolean isSuccessful = false;        
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {               
            if (methodNode.name.equals(updateTickMethodName) && methodNode.desc.equals("(L" + worldClassName + ";L" + blockPosClassName + ";L" + iBlockStateClassName + ";L" + randomClassName + ";)V")) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IF_ICMPLT) {                             
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 2));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 3));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedTick", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + blockClassName + ";L" + iBlockStateClassName + ";)Z", false));
                        nodesList.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) currentInsn).label));
                        methodNode.instructions.insertBefore(currentInsn.getPrevious().getPrevious(), nodesList); 
                        isSuccessful = true;                        
                        break;
                    }
                }    
                break;
            }
        }
        return isSuccessful;
    }

    private boolean patchIC2Sapling(ClassNode classNode) {
        return patchBOPBlockSapling(classNode);
    }

    private boolean patchIC2CropVanilla(ClassNode classNode) {
        String
        canGrowMethodName = "canGrow",
        getWorldObjMethodName = "getWorldObj",
        getPositionMethodName = "getPosition",
        getCropMethodName = "getCrop",
        getIdMethodName = "getId",
        worldClassName = "net/minecraft/world/World",
        blockPosClassName = "net/minecraft/util/math/BlockPos",
        entityPlayerClassName = "net/minecraft/entity/player/EntityPlayer",
        iCropTileClassName = "ic2/api/crops/ICropTile",
        iLocatableClassName = "ic2/api/info/ILocatable",
        cropCardClassName = "ic2/api/crops/CropCard",
        stringClassName = "java/lang/String",
        enumPlantTypeClassName = "austeretony/plantbiomes/common/main/EnumPlantType";
        boolean isSuccessful = false;   
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {               
            if (methodNode.name.equals(canGrowMethodName)) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IF_ICMPGE) {                             
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iCropTileClassName, getWorldObjMethodName, "()L" + worldClassName + ";", true));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iCropTileClassName, getPositionMethodName, "()L" + blockPosClassName + ";", true));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iCropTileClassName, getCropMethodName, "()L" + cropCardClassName + ";", true));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, cropCardClassName, getIdMethodName, "()L" + stringClassName + ";", false));
                        nodesList.add(new FieldInsnNode(Opcodes.GETSTATIC, enumPlantTypeClassName, "IC2_CROP", "L" + enumPlantTypeClassName + ";"));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedTickSpecial", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";L" + enumPlantTypeClassName + ";)Z", false));
                        nodesList.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) currentInsn).label));
                        methodNode.instructions.insertBefore(currentInsn.getPrevious().getPrevious().getPrevious().getPrevious(), nodesList);
                        isSuccessful = true;                        
                        break;
                    }
                }    
                break;
            }
        }
        return isSuccessful;
    }

    private boolean patchIC2CropCard(ClassNode classNode) {
        String
        canGrowMethodName = "canGrow",
        onRightClickMethodName = "onRightClick",
        getWorldObjMethodName = "getWorldObj",
        getPositionMethodName = "getPosition",
        getCropMethodName = "getCrop",
        getIdMethodName = "getId",
        worldClassName = "net/minecraft/world/World",
        blockPosClassName = "net/minecraft/util/math/BlockPos",
        entityPlayerClassName = "net/minecraft/entity/player/EntityPlayer",
        iCropTileClassName = "ic2/api/crops/ICropTile",
        iLocatableClassName = "ic2/api/info/ILocatable",
        cropCardClassName = "ic2/api/crops/CropCard",
        stringClassName = "java/lang/String",
        enumPlantTypeClassName = "austeretony/plantbiomes/common/main/EnumPlantType";
        boolean isSuccessful = false;   
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {               
            if (methodNode.name.equals(canGrowMethodName)) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IF_ICMPGE) {                             
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iCropTileClassName, getWorldObjMethodName, "()L" + worldClassName + ";", true));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iCropTileClassName, getPositionMethodName, "()L" + blockPosClassName + ";", true));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iCropTileClassName, getCropMethodName, "()L" + cropCardClassName + ";", true));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, cropCardClassName, getIdMethodName, "()L" + stringClassName + ";", false));
                        nodesList.add(new FieldInsnNode(Opcodes.GETSTATIC, enumPlantTypeClassName, "IC2_CROP", "L" + enumPlantTypeClassName + ";"));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedTickSpecial", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";L" + enumPlantTypeClassName + ";)Z", false));
                        nodesList.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) currentInsn).label));
                        methodNode.instructions.insertBefore(currentInsn.getPrevious().getPrevious().getPrevious().getPrevious(), nodesList);                   
                        break;
                    }
                }    
            }
            if (methodNode.name.equals(onRightClickMethodName)) {
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.ALOAD) {                             
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iCropTileClassName, getWorldObjMethodName, "()L" + worldClassName + ";", true));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iCropTileClassName, getPositionMethodName, "()L" + blockPosClassName + ";", true));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iCropTileClassName, getCropMethodName, "()L" + cropCardClassName + ";", true));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, cropCardClassName, getIdMethodName, "()L" + stringClassName + ";", false));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 2));
                        nodesList.add(new FieldInsnNode(Opcodes.GETSTATIC, enumPlantTypeClassName, "IC2_CROP", "L" + enumPlantTypeClassName + ";"));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "showDeniedBiomeOnBonemealUseSpecial", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";L" + entityPlayerClassName + ";L" + enumPlantTypeClassName + ";)V", false));
                        methodNode.instructions.insertBefore(currentInsn, nodesList);
                        isSuccessful = true;                        
                        break;
                    }
                }
            }
        }
        return isSuccessful;
    }

    private boolean patchIC2CropPotato(ClassNode classNode) {
        String
        canGrowMethodName = "canGrow",
        getWorldObjMethodName = "getWorldObj",
        getPositionMethodName = "getPosition",
        getCropMethodName = "getCrop",
        getIdMethodName = "getId",
        worldClassName = "net/minecraft/world/World",
        blockPosClassName = "net/minecraft/util/math/BlockPos",
        entityPlayerClassName = "net/minecraft/entity/player/EntityPlayer",
        iCropTileClassName = "ic2/api/crops/ICropTile",
        iLocatableClassName = "ic2/api/info/ILocatable",
        cropCardClassName = "ic2/api/crops/CropCard",
        stringClassName = "java/lang/String",
        enumPlantTypeClassName = "austeretony/plantbiomes/common/main/EnumPlantType";
        boolean isSuccessful = false;   
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {               
            if (methodNode.name.equals(canGrowMethodName)) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IF_ICMPGE) {                             
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iCropTileClassName, getWorldObjMethodName, "()L" + worldClassName + ";", true));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iCropTileClassName, getPositionMethodName, "()L" + blockPosClassName + ";", true));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iCropTileClassName, getCropMethodName, "()L" + cropCardClassName + ";", true));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, cropCardClassName, getIdMethodName, "()L" + stringClassName + ";", false));
                        nodesList.add(new FieldInsnNode(Opcodes.GETSTATIC, enumPlantTypeClassName, "IC2_CROP", "L" + enumPlantTypeClassName + ";"));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedTickSpecial", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";L" + enumPlantTypeClassName + ";)Z", false));
                        nodesList.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) currentInsn).label));
                        methodNode.instructions.insertBefore(currentInsn.getPrevious().getPrevious().getPrevious(), nodesList);
                        isSuccessful = true;                        
                        break;
                    }
                }    
                break;
            }
        }
        return isSuccessful;
    }

    private boolean patchIC2CropStickreed(ClassNode classNode) {
        return patchIC2CropPotato(classNode);
    }

    private boolean patchIC2CropHops(ClassNode classNode) {
        return patchIC2CropPotato(classNode);
    }

    private boolean patchIC2CropRedWheat(ClassNode classNode) {
        return patchIC2CropPotato(classNode);
    }

    private boolean patchIC2CropBaseMushroom(ClassNode classNode) {
        String
        canGrowMethodName = "canGrow",
        getWorldObjMethodName = "getWorldObj",
        getPositionMethodName = "getPosition",
        getCropMethodName = "getCrop",
        getIdMethodName = "getId",
        worldClassName = "net/minecraft/world/World",
        blockPosClassName = "net/minecraft/util/math/BlockPos",
        entityPlayerClassName = "net/minecraft/entity/player/EntityPlayer",
        iCropTileClassName = "ic2/api/crops/ICropTile",
        iLocatableClassName = "ic2/api/info/ILocatable",
        cropCardClassName = "ic2/api/crops/CropCard",
        stringClassName = "java/lang/String",
        enumPlantTypeClassName = "austeretony/plantbiomes/common/main/EnumPlantType";
        boolean isSuccessful = false;   
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {               
            if (methodNode.name.equals(canGrowMethodName)) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IF_ICMPGE) {                             
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iCropTileClassName, getWorldObjMethodName, "()L" + worldClassName + ";", true));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iCropTileClassName, getPositionMethodName, "()L" + blockPosClassName + ";", true));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iCropTileClassName, getCropMethodName, "()L" + cropCardClassName + ";", true));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, cropCardClassName, getIdMethodName, "()L" + stringClassName + ";", false));
                        nodesList.add(new FieldInsnNode(Opcodes.GETSTATIC, enumPlantTypeClassName, "IC2_CROP", "L" + enumPlantTypeClassName + ";"));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedTickSpecial", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";L" + enumPlantTypeClassName + ";)Z", false));
                        nodesList.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) currentInsn).label));
                        methodNode.instructions.insertBefore(currentInsn.getPrevious().getPrevious().getPrevious().getPrevious(), nodesList);
                        isSuccessful = true;                        
                        break;
                    }
                }    
                break;
            }
        }
        return isSuccessful;    
    }

    private boolean patchIC2CropVenomilla(ClassNode classNode) {
        String
        canGrowMethodName = "canGrow",
        getWorldObjMethodName = "getWorldObj",
        getPositionMethodName = "getPosition",
        getCropMethodName = "getCrop",
        getIdMethodName = "getId",
        worldClassName = "net/minecraft/world/World",
        blockPosClassName = "net/minecraft/util/math/BlockPos",
        entityPlayerClassName = "net/minecraft/entity/player/EntityPlayer",
        iCropTileClassName = "ic2/api/crops/ICropTile",
        iLocatableClassName = "ic2/api/info/ILocatable",
        cropCardClassName = "ic2/api/crops/CropCard",
        stringClassName = "java/lang/String",
        enumPlantTypeClassName = "austeretony/plantbiomes/common/main/EnumPlantType";
        boolean isSuccessful = false;   
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {               
            if (methodNode.name.equals(canGrowMethodName)) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IF_ICMPGT) {                             
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iCropTileClassName, getWorldObjMethodName, "()L" + worldClassName + ";", true));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iCropTileClassName, getPositionMethodName, "()L" + blockPosClassName + ";", true));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iCropTileClassName, getCropMethodName, "()L" + cropCardClassName + ";", true));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, cropCardClassName, getIdMethodName, "()L" + stringClassName + ";", false));
                        nodesList.add(new FieldInsnNode(Opcodes.GETSTATIC, enumPlantTypeClassName, "IC2_CROP", "L" + enumPlantTypeClassName + ";"));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedTickSpecial", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";L" + enumPlantTypeClassName + ";)Z", false));
                        nodesList.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) currentInsn).label));
                        methodNode.instructions.insertBefore(currentInsn.getPrevious().getPrevious().getPrevious(), nodesList);
                        isSuccessful = true;                        
                        break;
                    }
                }    
                break;
            }
        }
        return isSuccessful;
    }

    private boolean patchIC2CropColorFlower(ClassNode classNode) {
        return patchIC2CropVenomilla(classNode);
    }

    private boolean patchIC2CropBaseMetalCommon(ClassNode classNode) {
        String
        canGrowMethodName = "canGrow",
        getWorldObjMethodName = "getWorldObj",
        getPositionMethodName = "getPosition",
        getCropMethodName = "getCrop",
        getIdMethodName = "getId",
        worldClassName = "net/minecraft/world/World",
        blockPosClassName = "net/minecraft/util/math/BlockPos",
        iCropTileClassName = "ic2/api/crops/ICropTile",
        iLocatableClassName = "ic2/api/info/ILocatable",
        cropCardClassName = "ic2/api/crops/CropCard",
        stringClassName = "java/lang/String",
        enumPlantTypeClassName = "austeretony/plantbiomes/common/main/EnumPlantType";
        boolean isSuccessful = false;   
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {               
            if (methodNode.name.equals(canGrowMethodName)) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IF_ICMPGE) {                             
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iCropTileClassName, getWorldObjMethodName, "()L" + worldClassName + ";", true));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iCropTileClassName, getPositionMethodName, "()L" + blockPosClassName + ";", true));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iCropTileClassName, getCropMethodName, "()L" + cropCardClassName + ";", true));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, cropCardClassName, getIdMethodName, "()L" + stringClassName + ";", false));
                        nodesList.add(new FieldInsnNode(Opcodes.GETSTATIC, enumPlantTypeClassName, "IC2_CROP", "L" + enumPlantTypeClassName + ";"));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedTickSpecial", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";L" + enumPlantTypeClassName + ";)Z", false));
                        nodesList.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) currentInsn).label));
                        methodNode.instructions.insertBefore(currentInsn.getPrevious().getPrevious().getPrevious(), nodesList);
                    } else if (currentInsn.getOpcode() == Opcodes.IF_ICMPNE) {                             
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iCropTileClassName, getWorldObjMethodName, "()L" + worldClassName + ";", true));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iCropTileClassName, getPositionMethodName, "()L" + blockPosClassName + ";", true));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iCropTileClassName, getCropMethodName, "()L" + cropCardClassName + ";", true));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, cropCardClassName, getIdMethodName, "()L" + stringClassName + ";", false));
                        nodesList.add(new FieldInsnNode(Opcodes.GETSTATIC, enumPlantTypeClassName, "IC2_CROP", "L" + enumPlantTypeClassName + ";"));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedTickSpecial", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";L" + enumPlantTypeClassName + ";)Z", false));
                        nodesList.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) currentInsn).label));
                        methodNode.instructions.insertBefore(currentInsn.getPrevious().getPrevious().getPrevious(), nodesList);
                        isSuccessful = true;                        
                        break;
                    }
                }    
                break;
            }
        }    
        return isSuccessful;
    }

    private boolean patchIC2CropBaseMetalUncommon(ClassNode classNode) {
        return patchIC2CropBaseMetalCommon(classNode);
    }

    private boolean patchIC2CropEating(ClassNode classNode) {
        String
        canGrowMethodName = "canGrow",
        getWorldObjMethodName = "getWorldObj",
        getPositionMethodName = "getPosition",
        getCropMethodName = "getCrop",
        getIdMethodName = "getId",
        worldClassName = "net/minecraft/world/World",
        blockPosClassName = "net/minecraft/util/math/BlockPos",
        iCropTileClassName = "ic2/api/crops/ICropTile",
        iLocatableClassName = "ic2/api/info/ILocatable",
        cropCardClassName = "ic2/api/crops/CropCard",
        stringClassName = "java/lang/String",
        enumPlantTypeClassName = "austeretony/plantbiomes/common/main/EnumPlantType";
        boolean isSuccessful = false;   
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {               
            if (methodNode.name.equals(canGrowMethodName)) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IF_ICMPGE) {                             
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iCropTileClassName, getWorldObjMethodName, "()L" + worldClassName + ";", true));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iCropTileClassName, getPositionMethodName, "()L" + blockPosClassName + ";", true));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iCropTileClassName, getCropMethodName, "()L" + cropCardClassName + ";", true));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, cropCardClassName, getIdMethodName, "()L" + stringClassName + ";", false));
                        nodesList.add(new FieldInsnNode(Opcodes.GETSTATIC, enumPlantTypeClassName, "IC2_CROP", "L" + enumPlantTypeClassName + ";"));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedTickSpecial", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";L" + enumPlantTypeClassName + ";)Z", false));
                        nodesList.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) currentInsn).label));
                        methodNode.instructions.insertBefore(currentInsn.getPrevious().getPrevious().getPrevious(), nodesList);
                    } else if (currentInsn.getOpcode() == Opcodes.IF_ICMPLE) {                             
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iCropTileClassName, getWorldObjMethodName, "()L" + worldClassName + ";", true));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iCropTileClassName, getPositionMethodName, "()L" + blockPosClassName + ";", true));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iCropTileClassName, getCropMethodName, "()L" + cropCardClassName + ";", true));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, cropCardClassName, getIdMethodName, "()L" + stringClassName + ";", false));
                        nodesList.add(new FieldInsnNode(Opcodes.GETSTATIC, enumPlantTypeClassName, "IC2_CROP", "L" + enumPlantTypeClassName + ";"));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedTickSpecial", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";L" + enumPlantTypeClassName + ";)Z", false));
                        nodesList.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) currentInsn).label));
                        methodNode.instructions.insertBefore(currentInsn.getPrevious().getPrevious().getPrevious(), nodesList);
                        isSuccessful = true;                        
                        break;
                    }
                }    
                break;
            }
        }    
        return isSuccessful;
    }

    private boolean patchPHCBlockPamSapling(ClassNode classNode) {
        return patchBOPBlockSapling(classNode);
    }

    private boolean patchPHCBlockPamCrop(ClassNode classNode) {
        return patchBOPBlockSapling(classNode);
    }

    private boolean patchPHCBlockPamFruit(ClassNode classNode) {
        return patchBOPBlockBamboo(classNode);
    }

    private boolean patchPlantsBlockEnumSapling(ClassNode classNode) {
        return patchBOPBlockSapling(classNode);
    }

    private boolean patchPlantsBlockEnumHarvestBush(ClassNode classNode) {
        String
        updateTickMethodName = "func_180650_b",
        worldClassName = "net/minecraft/world/World",
        blockPosClassName = "net/minecraft/util/math/BlockPos",
        iBlockStateClassName = "net/minecraft/block/state/IBlockState",
        blockClassName = "net/minecraft/block/Block",
        randomClassName = "java/util/Random";
        boolean isSuccessful = false;  
        int ifeqCount = 0;
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {               
            if (methodNode.name.equals(updateTickMethodName) && methodNode.desc.equals("(L" + worldClassName + ";L" + blockPosClassName + ";L" + iBlockStateClassName + ";L" + randomClassName + ";)V")) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IFEQ) {  
                        ifeqCount++;
                        if (ifeqCount == 2) {
                            InsnList nodesList = new InsnList();   
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 2));
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 3));
                            nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedTick", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + blockClassName + ";L" + iBlockStateClassName + ";)Z", false));
                            nodesList.add(new JumpInsnNode(Opcodes.IFEQ, ((JumpInsnNode) currentInsn).label));
                            methodNode.instructions.insertBefore(currentInsn.getPrevious().getPrevious().getPrevious().getPrevious().getPrevious(), nodesList); 
                            isSuccessful = true;                        
                            break;
                        }
                    }
                }    
                break;
            }
        }
        return isSuccessful;
    }

    private boolean patchPlantsBlockBushling(ClassNode classNode) {
        return patchPlantsBlockEnumHarvestBush(classNode);
    }

    private boolean patchPlantsBlockEnumNetherHarvest(ClassNode classNode) {
        return patchPlantsBlockEnumHarvestBush(classNode);
    }

    private boolean patchPlantsBlockEnumCrop(ClassNode classNode) {
        return patchPlantsBlockEnumHarvestBush(classNode);
    }

    private boolean patchPlantsBlockDoubleHarvetBush(ClassNode classNode) {
        return patchPlantsBlockEnumHarvestBush(classNode);
    }

    private boolean patchRusticBlockSapling(ClassNode classNode) {
        return patchBOPBlockSapling(classNode);
    }

    private boolean patchRusticBlockSaplingApple(ClassNode classNode) {
        return patchBOPBlockSapling(classNode);
    }

    private boolean patchRusticBlockStakeCrop(ClassNode classNode) {
        return patchBOPBlockSapling(classNode);
    }

    private boolean patchRusticBlockAppleSeeds(ClassNode classNode) {
        return patchBOPBlockSapling(classNode);
    }

    private boolean patchRusticBlockBerryBush(ClassNode classNode) {
        return patchBOPBlockSapling(classNode);
    }

    private boolean patchRusticBlockGrapeStem(ClassNode classNode) {
        return patchBOPBlockSapling(classNode);
    }

    private boolean patchRusticBlockHerbBase(ClassNode classNode) {
        return patchBOPBlockSapling(classNode);
    }

    private boolean patchTCBlockSapling(ClassNode classNode) {
        return patchBOPBlockSapling(classNode);
    }

    private boolean patchTFBlockSapling(ClassNode classNode) {
        return patchBOPBlockSapling(classNode);
    }
}
