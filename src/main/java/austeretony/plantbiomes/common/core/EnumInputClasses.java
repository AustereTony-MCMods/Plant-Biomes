package austeretony.plantbiomes.common.core;

import java.util.Iterator;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public enum EnumInputClasses {

    MC_BLOCK_SAPLING("Minecraft", "BlockSapling", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    MC_BLOCK_CROPS("Minecraft", "BlockCrops", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    MC_BLOCK_MELON("Minecraft", "BlockStem", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    MC_BLOCK_GRASS("Minecraft", "BlockGrass", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    MC_BLOCK_MYCELIUM("Minecraft", "BlockMycelium", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    MC_BLOCK_COCOA("Minecraft", "BlockCocoa", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    MC_BLOCK_NETHER_WART("Minecraft", "BlockNetherWart", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    MC_BLOCK_VINE("Minecraft", "BlockVine", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    MC_BLOCK_MUSHROOM("Minecraft", "BlockMushroom", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    MC_BLOCK_REED("Minecraft", "BlockReed", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    MC_BLOCK_CACTUS("Minecraft", "BlockCactus", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),

    MC_BIOME("Minecraft", "Biome", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    MC_ITEM_DYE("Minecraft", "ItemDye", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    MC_RENDER_GLOBAL("Minecraft", "RenderGlobal", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),

    AC_TILE_ENTITY_CROP("AgriCraft", "TileEntityCrop", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),    
    AC_BLOCK_CROP("AgriCraft", "BlockCrop", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),

    BOP_BLOCK_SAPLING("Biomes O' Plenty", "BlockBOPSapling", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    BOP_BLOCK_GRASS("Biomes O' Plenty", "BlockBOPGrass", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    BOP_BLOCK_BAMBOO("Biomes O' Plenty", "BlockBOPBamboo", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),

    DT_SPECIES("DynamicTrees", "Species", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    DT_SPECIES_CACTUS("DynamicTrees", "SpeciesCactus", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    DT_BLOCK_FRUIT("DynamicTrees", "BlockFruit", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),

    FORESTRY_BLOCK_SAPLING("Forestry", "BlockSapling", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    FORESTRY_TILE_ENTITY_SAPLING("Forestry", "TileSapling", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),

    MA_BLOCK_MYSTICAL_CROP("Mystical Agriculture", "BlockMysticalCrop", 0, 0),

    HC_BLOCK_SAPLING("Pam's HarvestCraft", "BlockPamSapling", 0, 0),
    HC_BLOCK_CROP("Pam's HarvestCraft", "BlockPamCrop", 0, 0),
    HC_BLOCK_FRUIT("Pam's HarvestCraft", "BlockPamFruit", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),

    HO_BONEMEAL_MODULE("Hunger Overhaul", "BonemealModule", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),

    IE_BLOCK_CROP("ImmersiveEngineering", "BlockIECrop", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),

    IC2_BLOCK_SAPLING("IndustrialCraft 2", "Ic2Sapling", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    IC2_CROP_VANILLA("IndustrialCraft 2", "CropVanilla", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    IC2_CROP_CARD("IndustrialCraft 2", "CropCard", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    IC2_CROP_POTATO("IndustrialCraft 2", "CropPotato", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    IC2_CROP_STICKREED("IndustrialCraft 2", "CropStickreed", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    IC2_CROP_HOPS("IndustrialCraft 2", "CropHops", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    IC2_CROP_RED_WHEAT("IndustrialCraft 2", "CropRedWheat", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    IC2_CROP_BASE_MUSHROOM("IndustrialCraft 2", "CropBaseMushroom", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    IC2_CROP_VENOMILLA("IndustrialCraft 2", "CropVenomilla", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    IC2_CROP_COLOR_FLOWER("IndustrialCraft 2", "CropColorFlower", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    IC2_CROP_BASE_METAL_COMMON("IndustrialCraft 2", "CropBaseMetalCommon", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    IC2_CROP_BASE_METAL_UNCOMMON("IndustrialCraft 2", "CropBaseMetalUncommon", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),
    IC2_CROP_EATING("IndustrialCraft 2", "CropEating", 0, ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES),

    TC_BLOCK_SAPLING("Thaumcraft", "BlockSaplingTC", 0, 0),

    TF_BLOCK_SAPLING("Twilight Forest", "BlockTFSapling", 0, 0);

    private static final String HOOKS_CLASS = "austeretony/plantbiomes/common/core/PlantBiomesHooks";

    public final String domain, clazz;

    public final int readerFlags, writerFlags;

    EnumInputClasses(String domain, String clazz, int readerFlags, int writerFlags) {
        this.domain = domain;
        this.clazz = clazz;
        this.readerFlags = readerFlags;
        this.writerFlags = writerFlags;
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
        case MC_RENDER_GLOBAL:
            return patchMCRenderGlobal(classNode); 

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

        case FORESTRY_BLOCK_SAPLING:
            return patchForestryBlockSapling(classNode); 
        case FORESTRY_TILE_ENTITY_SAPLING:
            return patchForestryTileSapling(classNode); 

        case MA_BLOCK_MYSTICAL_CROP:
            return patchMABlockMysticalCrop(classNode); 

        case HC_BLOCK_SAPLING:
            return patchHCBlockPamSapling(classNode); 
        case HC_BLOCK_CROP:
            return patchHCBlockPamCrop(classNode); 
        case HC_BLOCK_FRUIT:
            return patchHCBlockPamFruit(classNode); 

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
                            nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedTallgrass", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + iBlockStateClassName + ";)Z", false));
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
        plantFlowerMethodName = "plantFlower",
        worldClassName = PlantBiomesCorePlugin.isObfuscated() ? "amu" : "net/minecraft/world/World",
                blockPosClassName = PlantBiomesCorePlugin.isObfuscated() ? "et" : "net/minecraft/util/math/BlockPos",
                        flowerEntryClassName = PlantBiomesCorePlugin.isObfuscated() ? "anh$FlowerEntry" : "net/minecraft/world/biome/Biome$FlowerEntry",
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
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedFlower", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + flowerEntryClassName + ";)Z", false));
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
                            nodesList.add(new InsnNode(Opcodes.ICONST_1));
                            nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedBonemeal", "(L" + worldClassName + ";L" + blockPosClassName + ";Z)Z", false));
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

    private boolean patchMCRenderGlobal(ClassNode classNode) {
        String
        worldFieldName = PlantBiomesCorePlugin.isObfuscated() ? "k" : "world",
                playEventMethodName = PlantBiomesCorePlugin.isObfuscated() ? "a" : "playEvent",
                        worldClassName = PlantBiomesCorePlugin.isObfuscated() ? "amu" : "net/minecraft/world/World",
                                blockPosClassName = PlantBiomesCorePlugin.isObfuscated() ? "et" : "net/minecraft/util/math/BlockPos",
                                        entityPlayerClassName = PlantBiomesCorePlugin.isObfuscated() ? "aed" : "net/minecraft/entity/player/EntityPlayer",
                                                renderGlobalClassName = PlantBiomesCorePlugin.isObfuscated() ? "buy" : "net/minecraft/client/renderer/RenderGlobal",
                                                        worldClientClassName = PlantBiomesCorePlugin.isObfuscated() ? "bsb" : "net/minecraft/client/multiplayer/WorldClient",
                                                                randomClassName = "java/util/Random";
        boolean isSuccessful = false;        
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {               
            if (methodNode.name.equals(playEventMethodName) && methodNode.desc.equals("(L" + entityPlayerClassName + ";IL" + blockPosClassName + ";I)V")) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.ASTORE) {                             
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                        nodesList.add(new FieldInsnNode(Opcodes.GETFIELD, renderGlobalClassName, worldFieldName, "L" + worldClientClassName + ";"));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 3));
                        nodesList.add(new VarInsnNode(Opcodes.ILOAD, 2));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 5));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "spawnParticles", "(L" + worldClassName + ";L" + blockPosClassName + ";IL" + randomClassName + ";)V", false));
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
        stringClassName = "java/lang/String";
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
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedAgriCraftCrop", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";)I", false));
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
        stringClassName = "java/lang/String";
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
                            nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "showAgriCraftCropDeniedBiome", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";L" + entityPlayerClassName + ";)V", false));
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

    private boolean patchForestryBlockSapling(ClassNode classNode) {
        String
        canUseBonemealMethodName = "func_180670_a",
        getTreeMethodName = "getTree",
        getIdentMethodName = "getIdent",
        tileSaplingClassName = "forestry/arboriculture/tiles/TileSapling",
        iTreeClassName = "forestry/api/arboriculture/ITree",
        worldClassName = "net/minecraft/world/World",
        blockPosClassName = "net/minecraft/util/math/BlockPos",
        blockClassName = "net/minecraft/block/Block",
        stringClassName = "java/lang/String";
        boolean isSuccessful = false;        
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.name.equals(canUseBonemealMethodName)) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();  
                JumpInsnNode ifnullInsnNode = null      ;
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();  
                    if (currentInsn.getOpcode() == Opcodes.ASTORE) {         
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 3));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 5));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL, tileSaplingClassName, getTreeMethodName, "()L" + iTreeClassName + ";", false));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iTreeClassName, getIdentMethodName, "()L" + stringClassName + ";", true));
                        nodesList.add(new InsnNode(Opcodes.ICONST_1));     
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedForestrySapling", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";Z)Z", false));
                        nodesList.add(new InsnNode(Opcodes.IRETURN));
                        methodNode.instructions.insert(currentInsn, nodesList); 
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
        blockClassName = "net/minecraft/block/Block",
        stringClassName = "java/lang/String";
        boolean isSuccessful = false;        
        AbstractInsnNode currentInsn;

        for (MethodNode methodNode : classNode.methods) {
            if (methodNode.name.equals(canAcceptBoneMealMethodName) || methodNode.name.equals(tryGrowMethodName)) {                         
                Iterator<AbstractInsnNode> insnIterator = methodNode.instructions.iterator();              
                while (insnIterator.hasNext()) {                        
                    currentInsn = insnIterator.next();                  
                    if (currentInsn.getOpcode() == Opcodes.IFNONNULL) {     
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                        nodesList.add(new FieldInsnNode(Opcodes.GETFIELD, tileSaplingClassName, worldFieldName, "L" + worldClassName + ";"));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                        nodesList.add(new FieldInsnNode(Opcodes.GETFIELD, tileSaplingClassName, posFieldName, "L" + blockPosClassName + ";"));
                        if (methodNode.name.equals(canAcceptBoneMealMethodName))
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 2));
                        else
                            nodesList.add(new VarInsnNode(Opcodes.ALOAD, 3));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKEINTERFACE, iTreeClassName, getIdentMethodName, "()L" + stringClassName + ";", true));
                        if (methodNode.name.equals(canAcceptBoneMealMethodName))
                            nodesList.add(new InsnNode(Opcodes.ICONST_1));     
                        else
                            nodesList.add(new InsnNode(Opcodes.ICONST_0));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedForestrySapling", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";Z)Z", false));
                        nodesList.add(new JumpInsnNode(Opcodes.IFNE, ((JumpInsnNode) currentInsn).label));
                        methodNode.instructions.insertBefore(currentInsn.getPrevious(), nodesList); 
                        if (methodNode.name.equals(tryGrowMethodName))
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

    private boolean patchMABlockMysticalCrop(ClassNode classNode) {
        return patchBOPBlockSapling(classNode);
    }

    private boolean patchHCBlockPamSapling(ClassNode classNode) {
        return patchBOPBlockSapling(classNode);
    }

    private boolean patchHCBlockPamCrop(ClassNode classNode) {
        return patchBOPBlockSapling(classNode);
    }

    private boolean patchHCBlockPamFruit(ClassNode classNode) {
        return patchBOPBlockBamboo(classNode);
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
                            nodesList.add(new InsnNode(Opcodes.ICONST_0));
                            nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedBonemeal", "(L" + worldClassName + ";L" + blockPosClassName + ";Z)Z", false));
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
        stringClassName = "java/lang/String";
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
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedIC2Crop", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";)Z", false));
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
        stringClassName = "java/lang/String";
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
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedIC2Crop", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";)Z", false));
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
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "showIC2CropDeniedBiome", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";L" + entityPlayerClassName + ";)V", false));
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
        stringClassName = "java/lang/String";
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
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedIC2Crop", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";)Z", false));
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
        stringClassName = "java/lang/String";
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
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedIC2Crop", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";)Z", false));
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
        stringClassName = "java/lang/String";
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
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedIC2Crop", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";)Z", false));
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
        stringClassName = "java/lang/String";
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
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedIC2Crop", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";)Z", false));
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
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedIC2Crop", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";)Z", false));
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
        stringClassName = "java/lang/String";
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
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedIC2Crop", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";)Z", false));
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
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedIC2Crop", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + stringClassName + ";)Z", false));
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

    private boolean patchTCBlockSapling(ClassNode classNode) {
        return patchBOPBlockSapling(classNode);
    }

    private boolean patchTFBlockSapling(ClassNode classNode) {
        return patchBOPBlockSapling(classNode);
    }
}
