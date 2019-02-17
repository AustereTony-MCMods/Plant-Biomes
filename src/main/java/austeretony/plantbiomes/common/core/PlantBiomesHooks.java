package austeretony.plantbiomes.common.core;

import austeretony.plantbiomes.common.config.EnumConfigSettings;
import austeretony.plantbiomes.common.main.DataManager;
import austeretony.plantbiomes.common.main.EnumPlantType;
import austeretony.plantbiomes.common.main.EnumSpecialPlants;
import austeretony.plantbiomes.common.main.EnumStandardPlants;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PlantBiomesHooks {

    public static boolean isGrowthAllowedTick(World world, BlockPos pos, Block block, IBlockState blockState) {
        if (DataManager.existServer(block)) {
            if (DataManager.getServer(block).canGrowOverTime(block, blockState, DataManager.getBiomeRegistryName(world, pos)))
                return true;
            if (EnumConfigSettings.SMOKE_OVER_TIME.isEnabled())
                world.playEvent(2000, pos, 4);  
            return false;           
        }
        return true;
    }

    public static boolean isGrowthAllowedBonemeal(World world, BlockPos pos) {
        IBlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        if (DataManager.existServer(block)) {
            if (DataManager.getServer(block).canGrowWithBonemeal(block, blockState, DataManager.getBiomeRegistryName(world, pos)))
                return true;
            if (block.getClass().getName().equals(EnumStandardPlants.MC_GRASS_BLOCK.className) 
                    || block.getClass().getName().equals(EnumStandardPlants.MC_MYCELIUM_BLOCK.className)
                    || block.getClass().getName().equals(EnumStandardPlants.BOP_BLOCK_GRASS.className)) 
                return true;//Allow bonemeal usage on these blocks if spreading disabled in this biome.
            if (EnumConfigSettings.SMOKE_ON_BONEMEAL.isEnabled())
                world.playEvent(2000, pos, 4);
            return false;
        }
        return true;
    }

    public static boolean isGrowthAllowedTickSpecial(World world, BlockPos pos, String id, EnumPlantType plantType) {
        if (DataManager.existSpecialServer(id, plantType)) {
            if (DataManager.getSpecialServer(id, plantType).canGrowOverTime(EnumSpecialPlants.SPECIALS_META, DataManager.getBiomeRegistryName(world, pos)))
                return true;
            if (EnumConfigSettings.SMOKE_OVER_TIME.isEnabled())
                world.playEvent(2000, pos, 4);
            return false;           
        }
        return true;
    }

    public static int isGrowthAllowedTickSpecialInt(World world, BlockPos pos, String id, EnumPlantType plantType) {
        int allowed = 0;
        switch (plantType) {
        case AGRICRAFT_CROP:
            allowed = 1;
            break;
        default:
            break;
        }
        if (DataManager.existSpecialServer(id, plantType)) {
            if (DataManager.getSpecialServer(id, plantType).canGrowOverTime(EnumSpecialPlants.SPECIALS_META, DataManager.getBiomeRegistryName(world, pos)))
                return allowed;
            if (EnumConfigSettings.SMOKE_OVER_TIME.isEnabled())
                world.playEvent(2000, pos, 4);
            return 0;           
        }
        return allowed;
    }

    public static boolean isGrowthAllowedWithBonemealSpecial(World world, BlockPos pos, String id, EnumPlantType plantType) {
        if (DataManager.existSpecialServer(id, plantType)) {
            if (DataManager.getSpecialServer(id, plantType).canGrowWithBonemeal(EnumSpecialPlants.SPECIALS_META, DataManager.getBiomeRegistryName(world, pos)))
                return true;
            if (EnumConfigSettings.SMOKE_ON_BONEMEAL.isEnabled())
                world.playEvent(2000, pos, 4);  
            return false;           
        }
        return true;
    }

    public static float isGrowthAllowedWithBonemealSpecialFloat(World world, BlockPos pos, String id, EnumPlantType plantType) {
        float allowed = 0.0F;
        switch (plantType) {
        case FORESTRY_FRUIT:
        case FORESTRY_LEAVES:
            allowed = 0.5F;
            break;
        default:
            break;
        }
        if (DataManager.existSpecialServer(id, plantType)) {
            if (DataManager.getSpecialServer(id, plantType).canGrowWithBonemeal(EnumSpecialPlants.SPECIALS_META, DataManager.getBiomeRegistryName(world, pos)))
                return allowed;
            if (EnumConfigSettings.SMOKE_ON_BONEMEAL.isEnabled())
                world.playEvent(2000, pos, 4);  
            return 0.0F;
        }
        return allowed;
    }

    public static void showDeniedBiomeOnBonemealUseSpecial(World world, BlockPos pos, String id, EntityPlayer player, EnumPlantType plantType) {
        if (player.getHeldItemMainhand().getItem() == Items.DYE && EnumDyeColor.byDyeDamage(player.getHeldItemMainhand().getMetadata()) == EnumDyeColor.WHITE)
            isGrowthAllowedWithBonemealSpecial(world, pos, id, plantType);
    }

    //Unique case
    public static boolean isGrowthAllowedTickDynamicTree(World world, BlockPos pos, ResourceLocation registryName) {
        if (DataManager.existServer(registryName)) {
            if (DataManager.getServer(registryName).canGrowOverTime(EnumSpecialPlants.SPECIALS_META, DataManager.getBiomeRegistryName(world, pos)))
                return true;
            if (EnumConfigSettings.SMOKE_OVER_TIME.isEnabled())
                world.playEvent(2000, pos, 4);
            return false;           
        }
        return true;
    }
}
