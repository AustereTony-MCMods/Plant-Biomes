package austeretony.plantbiomes.common.main;

import austeretony.plantbiomes.common.reference.CommonReference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlantBiomesEvents {

    @SubscribeEvent
    public void onBlockActivated(PlayerInteractEvent event) {
        if (!event.getWorld().isRemote && event.getHand() == EnumHand.MAIN_HAND) {
            if (DataLoader.isConfigModeEnabled() && CommonReference.isOpped(event.getEntityPlayer())) {
                IBlockState blockState = event.getWorld().getBlockState(event.getPos());       
                if (blockState != null) {
                    Block block = blockState.getBlock();
                    if (block instanceof IGrowable || block instanceof IPlantable || block instanceof BlockVine) {
                        DataLoader.lpRegistryName = block.getRegistryName();
                        DataLoader.lpMeta = block.getMetaFromState(blockState);
                        DataLoader.lpBiomeRegistryName = DataLoader.getBiomeRegistryName(event.getWorld(), event.getPos());
                        DataLoader.lpBlockPos = event.getPos();
                        DataLoader.lpUnlocalizedName = new ItemStack(Item.getItemFromBlock(block), 1, DataLoader.lpMeta).getUnlocalizedName();
                        if (DataLoader.lpUnlocalizedName.equals("tile.air"))
                            DataLoader.lpUnlocalizedName = "pb.undefined";
                        EnumChatMessages.LATEST_PLANT.sendMessage(event.getEntityPlayer());
                    }
                }
            }
        }
    }
}
