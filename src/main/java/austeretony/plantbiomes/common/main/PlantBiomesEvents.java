package austeretony.plantbiomes.common.main;

import austeretony.plantbiomes.common.reference.CommonReference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Loader;
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
                        //dummy ic2 crops support
                        if (Loader.instance().isModLoaded("ic2")) {
                            TileEntity tile = event.getWorld().getTileEntity(event.getPos());
                            if (tile != null) {
                                if (tile.getClass().getName().equals("ic2.core.crop.TileEntityCrop")) {
                                    NBTTagCompound tagCompound = tile.serializeNBT();
                                    DataLoader.latestPlant = new LatestPlant(
                                            new ResourceLocation(tagCompound.getString("cropOwner"), tagCompound.getString("cropId")),
                                            0,
                                            DataLoader.getBiomeRegistryName(event.getWorld(), event.getPos()),
                                            event.getPos(),
                                            tagCompound.getString("cropOwner") + ".crop." + tagCompound.getString("cropId"));
                                    DataLoader.latestPlant.setIC2CropId(tagCompound.getString("cropId"));
                                    EnumChatMessages.LATEST_PLANT.sendMessage(event.getEntityPlayer());
                                    return;
                                }
                            }
                        }
                        int meta = block.getMetaFromState(blockState);
                        DataLoader.latestPlant = new LatestPlant(
                                block.getRegistryName(),
                                meta,
                                DataLoader.getBiomeRegistryName(event.getWorld(), event.getPos()),
                                event.getPos(),
                                new ItemStack(Item.getItemFromBlock(block), 1, meta).getUnlocalizedName() + ".name");
                        EnumChatMessages.LATEST_PLANT.sendMessage(event.getEntityPlayer());
                    } 
                }
            }   
        }
    }
}
