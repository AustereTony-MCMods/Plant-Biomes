package austeretony.plantbiomes.common.main;

import austeretony.plantbiomes.common.origin.CommonReference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockStem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlantBiomesEvents {

    @SubscribeEvent
    public void onBlockActivated(PlayerInteractEvent event) {
        if (!event.getWorld().isRemote && event.getHand() == EnumHand.MAIN_HAND) {
            if (PBDataLoader.isConfigModeEnabled() && CommonReference.isOpped(event.getEntityPlayer())) {
                IBlockState blockState = event.getWorld().getBlockState(event.getPos());       
                if (blockState != null) {
                    Block block = blockState.getBlock();
                    if (block instanceof BlockSapling || block instanceof BlockCrops || block instanceof BlockStem) {
                        PBDataLoader.latestPlantKey = PBDataLoader.createPlantKey(block, blockState);
                        PBDataLoader.latestBlockPos = event.getPos();
                        PBDataLoader.latestPlantUnlocalizedName = new ItemStack(Item.getItemFromBlock(block), 1, block.getMetaFromState(blockState)).getUnlocalizedName();
                        EnumPBChatMessages.showMessage(event.getEntityPlayer(), EnumPBChatMessages.LATEST_PLANT, PBDataLoader.latestPlantKey, 
                                PBDataLoader.getBiomeRegistryName(event.getWorld(), event.getPos()), PBDataLoader.latestPlantUnlocalizedName);
                    }
                }
            }
        }
    }
}
