package austeretony.plantbiomes.common.main;

import austeretony.plantbiomes.common.network.NetworkHandler;
import austeretony.plantbiomes.common.network.client.CPInitData;
import austeretony.plantbiomes.common.network.client.CPSyncPlantsData;
import austeretony.plantbiomes.common.reference.CommonReference;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class PlantBiomesEvents {

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
        if (CommonReference.isOpped(event.player) && PBManager.isOverlayEnabled()) {
            NetworkHandler.sendTo(new CPInitData(), (EntityPlayerMP) event.player);
            NetworkHandler.sendTo(new CPSyncPlantsData(CPSyncPlantsData.EnumAction.SYNC_ALL), (EntityPlayerMP) event.player);
        }
    }

    @SubscribeEvent
    public void onBlockActivated(PlayerInteractEvent event) {
        if (!event.getWorld().isRemote && event.getHand() == EnumHand.MAIN_HAND) {
            if (PBManager.isConfigModeEnabled() && CommonReference.isOpped(event.getEntityPlayer()) && CommonReference.isMainHandEmpty(event.getEntityPlayer())) {
                IBlockState blockState = event.getWorld().getBlockState(event.getPos());       
                if (blockState != null)
                    getPlantData(event.getWorld(), event.getPos(), blockState.getBlock(), blockState, event.getEntityPlayer());      
            }   
        }
    }

    private void getPlantData(World world, BlockPos pos, Block block, IBlockState blockState, EntityPlayer player) {
        PlantBiomesMain.LOGGER.info("block class name: " + block.getClass().getName());//TODO For debug.
        if (this.tryGetSpecialPlantData(world, pos, player)) return;
        EnumStandardPlants standard = EnumStandardPlants.identify(block);
        if (standard != null) {
            int meta = block.getMetaFromState(blockState);
            String unlocalizedName = new ItemStack(Item.getItemFromBlock(block), 1, meta).getUnlocalizedName() + ".name";
            PBManager.latestPlant = new LatestPlant(
                    EnumPBPlantType.STANDARD,
                    block.getRegistryName(), 
                    meta, 
                    "",
                    unlocalizedName,
                    PBManager.getBiomeRegistryName(world, pos), 
                    pos);
            EnumChatMessages.LATEST_PLANT.sendMessage(player);
        } else {
            EnumChatMessages.UNSUPPORTED_PLANT.sendMessage(player);
        }
    }

    private boolean tryGetSpecialPlantData(World world, BlockPos pos, EntityPlayer player) {
        boolean acquired = false;
        if (PBManager.shouldCheckSpecialPlantsServer()) {   
            TileEntity tile = world.getTileEntity(pos);
            if (tile != null) {
                EnumSpecialPlants special = EnumSpecialPlants.identify(tile);
                if (special != null) {
                    if (acquired = special.collectData(tile.serializeNBT(), world, pos))
                        EnumChatMessages.LATEST_PLANT.sendMessage(player);
                }
            }
        } 
        return acquired;
    }
}
