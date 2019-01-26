package austeretony.plantbiomes.common.core;

import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraft.launchwrapper.IClassTransformer;

public class PlantBiomesClassTransformer implements IClassTransformer {

    public static final Logger LOGGER = LogManager.getLogger("Plant Biomes Core");

    private static final String HOOKS_CLASS = "austeretony/plantbiomes/common/core/PlantBiomesHooks";

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {   
        switch (transformedName) {   	
        case "net.minecraft.block.BlockSapling":   			
            return patchBlockSapling(basicClass, "BlockSapling");   			 			
        case "net.minecraft.block.BlockCrops":							
            return patchBlockCrops(basicClass, "BlockCrops");	
        case "net.minecraft.block.BlockStem":                                                      
            return patchBlockStem(basicClass, "BlockStem"); 
        case "net.minecraft.block.BlockGrass":                                                      
            return patchBlockGrass(basicClass, "BlockGrass"); 
        case "net.minecraft.world.biome.Biome":                                                      
            return patchBiome(basicClass); 
        case "net.minecraft.item.ItemDye":
            return patchItemDye(basicClass);
        case "net.minecraft.client.renderer.RenderGlobal":
            return patchRenderGlobal(basicClass);
            //***non-vanilla plants support***
        case "twilightforest.block.BlockTFSapling"://twilight forest saplings
            return patchBlockTFSapling(basicClass, "BlockTFSapling");
        case "biomesoplenty.common.block.BlockBOPSapling"://biomes o plenty saplings
            return patchBlockBOPSapling(basicClass, "BlockBOPSapling");
        case "ic2.core.block.Ic2Sapling"://ic2 sapling
            return patchIC2Sapling(basicClass, "Ic2Sapling");
        //case "forestry.arboriculture.blocks.BlockTreeContainer"://forestry saplings
            //return patchBlockTreeContainer(basicClass);
        case "thaumcraft.common.blocks.world.plants.BlockSaplingTC"://thaumcraft saplings
            return patchBlockSaplingTC(basicClass, "BlockSaplingTC");
        }   	
        return basicClass;
    }

    private byte[] patchBlockSapling(byte[] basicClass, String clazz) {              
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(basicClass);
        classReader.accept(classNode, 0);

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
                if (!clazz.equals("BlockGrass"))
                    break;
            }
            if (clazz.equals("BlockGrass"))
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

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);        
        classNode.accept(writer);

        if (isSuccessful)
            LOGGER.info("<" + clazz + ".class> patched!");   

        return writer.toByteArray();    
    }

    private byte[] patchBlockCrops(byte[] basicClass, String clazz) {              
        return patchBlockSapling(basicClass, clazz);
    }

    private byte[] patchBlockStem(byte[] basicClass, String clazz) {              
        return patchBlockSapling(basicClass, clazz);
    }

    private byte[] patchBlockGrass(byte[] basicClass, String clazz) {              
        return patchBlockSapling(basicClass, clazz);
    }

    private byte[] patchBiome(byte[] basicClass) {              
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(basicClass);
        classReader.accept(classNode, 0);

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

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);        
        classNode.accept(writer);

        if (isSuccessful)
            LOGGER.info("<Biome.class> patched!");   

        return writer.toByteArray();    
    }

    private byte[] patchItemDye(byte[] basicClass) {              
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(basicClass);
        classReader.accept(classNode, 0);

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

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);        
        classNode.accept(writer);

        if (isSuccessful)
            LOGGER.info("<ItemDye.class> patched!");   

        return writer.toByteArray();    
    }

    private byte[] patchRenderGlobal(byte[] basicClass) {              
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(basicClass);
        classReader.accept(classNode, 0);

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
                        nodesList.add(new VarInsnNode(Opcodes.ILOAD, 4));
                        nodesList.add(new VarInsnNode(Opcodes.ILOAD, 2));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 5));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "spawnParticles", "(L" + worldClassName + ";L" + blockPosClassName + ";IIL" + randomClassName + ";)V", false));
                        methodNode.instructions.insert(currentInsn, nodesList); 
                        isSuccessful = true;                        
                        break;
                    }
                }                                           
                break;
            }
        }

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);        
        classNode.accept(writer);

        if (isSuccessful)
            LOGGER.info("<RenderGlobal.class> patched!");   

        return writer.toByteArray();    
    }

    private byte[] patchBlockTFSapling(byte[] basicClass, String clazz) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(basicClass);
        classReader.accept(classNode, 0);

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

        ClassWriter writer = new ClassWriter(/*ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES*/0);        
        classNode.accept(writer);

        if (isSuccessful)
            LOGGER.info("<" + clazz + ".class> patched!");   

        return writer.toByteArray();    
    }

    private byte[] patchBlockBOPSapling(byte[] basicClass, String clazz) {
        return patchBlockTFSapling(basicClass, clazz);
    }

    private byte[] patchIC2Sapling(byte[] basicClass, String clazz) {
        return patchBlockTFSapling(basicClass, clazz);
    }

    //TODO Fix Forestry BlockTreeContainer.class hook
    /*private byte[] patchBlockTreeContainer(byte[] basicClass) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(basicClass);
        classReader.accept(classNode, 0);

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
                    if (currentInsn.getOpcode() == Opcodes.IFLE) {         
                        methodNode.instructions.remove(currentInsn.getPrevious().getPrevious().getPrevious().getPrevious().getPrevious());
                        methodNode.instructions.remove(currentInsn.getPrevious().getPrevious().getPrevious().getPrevious());
                        InsnList nodesList = new InsnList();   
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 1));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 2));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 0));
                        nodesList.add(new VarInsnNode(Opcodes.ALOAD, 3));
                        nodesList.add(new MethodInsnNode(Opcodes.INVOKESTATIC, HOOKS_CLASS, "isGrowthAllowedForestrySapling", "(L" + worldClassName + ";L" + blockPosClassName + ";L" + blockClassName + ";L" + iBlockStateClassName + ";)F", false));
                        methodNode.instructions.insertBefore(currentInsn, nodesList); 
                        isSuccessful = true;                        
                        break;
                    }
                }                                           
                break;
            }
        }

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);        
        classNode.accept(writer);

        if (isSuccessful)
            LOGGER.info("<BlockTreeContainer.class> patched!");   

        return writer.toByteArray();   
    }*/

    private byte[] patchBlockSaplingTC(byte[] basicClass, String clazz) {
        return patchBlockTFSapling(basicClass, clazz);
    }
}

