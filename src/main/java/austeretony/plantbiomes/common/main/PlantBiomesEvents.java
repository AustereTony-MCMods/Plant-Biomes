package austeretony.plantbiomes.common.main;

import austeretony.plantbiomes.common.reference.CommonReference;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlantBiomesEvents {

    @SubscribeEvent
    public void onBlockActivated(PlayerInteractEvent event) {
        if (!event.getWorld().isRemote && event.getHand() == EnumHand.MAIN_HAND) {
            if (DataLoader.isConfigModeEnabled() && CommonReference.isOpped(event.getEntityPlayer()) && CommonReference.isMainHandEmpty(event.getEntityPlayer())) {
                IBlockState blockState = event.getWorld().getBlockState(event.getPos());       
                if (blockState != null)
                    getPlantData(event.getWorld(), event.getPos(), blockState.getBlock(), blockState, event.getEntityPlayer());      
            }   
        }
    }

    private void getPlantData(World world, BlockPos pos, Block block, IBlockState blockState, EntityPlayer player) {
        if (this.tryGetSpecialPlantData(world, pos, player)) return;
        PlantBiomesMain.LOGGER.info("Block class name: " + block.getClass().getName());//TODO For debug
        EnumStandardPlants standard = EnumStandardPlants.identify(block);
        if (standard != null) {
            int meta = block.getMetaFromState(blockState);
            String unlocalizedName = new ItemStack(Item.getItemFromBlock(block), 1, meta).getUnlocalizedName() + ".name";
            DataLoader.latestPlant = new LatestPlant(block.getRegistryName(), meta, DataLoader.getBiomeRegistryName(world, pos), pos, unlocalizedName);
            EnumChatMessages.LATEST_PLANT.sendMessage(player);
        } else {
            EnumChatMessages.UNSUPPORTED_PLANT.sendMessage(player);
        }
    }

    private boolean tryGetSpecialPlantData(World world, BlockPos pos, EntityPlayer player) {
        boolean acquired = false;
        if (DataLoader.isIC2Loaded() 
                || DataLoader.forestryLoaded()) {   
            TileEntity tile = world.getTileEntity(pos);
            if (tile != null) {
                EnumSpecialPlants special = EnumSpecialPlants.identify(tile);
                if (special != null) {
                    NBTTagCompound tagCompound = tile.serializeNBT();
                    special.collectData(tagCompound, world, pos);
                    EnumChatMessages.LATEST_PLANT.sendMessage(player);
                    acquired = true;
                }
            }
        } 
        return acquired;
    }
}
