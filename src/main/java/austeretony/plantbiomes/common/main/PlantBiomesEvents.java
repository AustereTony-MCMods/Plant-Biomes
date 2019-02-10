package austeretony.plantbiomes.common.main;

import austeretony.plantbiomes.common.network.NetworkHandler;
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
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class PlantBiomesEvents {

    @SubscribeEvent
    public void onPlayerConnectsToServer(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        PBManager.initClientData();
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
        if (CommonReference.isOpped(event.player) && PBManager.isOverlayEnabled())
            NetworkHandler.sendTo(new CPSyncPlantsData(CPSyncPlantsData.EnumAction.SYNC_ALL), (EntityPlayerMP) event.player);
    }

    @SubscribeEvent
    public void onBlockActivated(PlayerInteractEvent event) {
        if (!event.getWorld().isRemote
                && PBManager.isConfigModeEnabled() 
                && CommonReference.isOpped(event.getEntityPlayer()) 
                && event.getHand() == EnumHand.MAIN_HAND 
                && CommonReference.isMainHandEmpty(event.getEntityPlayer())) {        
            IBlockState blockState = event.getWorld().getBlockState(event.getPos());       
            if (blockState != null)
                this.getPlantData(event.getWorld(), event.getPos(), blockState.getBlock(), blockState, event.getEntityPlayer());      
        }
    }

    private void getPlantData(World world, BlockPos pos, Block block, IBlockState blockState, EntityPlayer player) {
        PlantBiomesMain.LOGGER.info("Block class name: " + block.getClass().getName());//TODO For debug.
        if (this.tryGetSpecialPlantData(world, pos, block, blockState, player)) return;
        EnumStandardPlants standard = EnumStandardPlants.identify(block);
        if (standard != null) {
            int meta = block.getMetaFromState(blockState);
            PBManager.latestPlant = new LatestPlant(
                    EnumPBPlantType.STANDARD,
                    block.getRegistryName(), 
                    meta, 
                    "",
                    new ItemStack(Item.getItemFromBlock(block), 1, meta).getUnlocalizedName() + ".name",
                    PBManager.getBiomeRegistryName(world, pos), 
                    pos);
            EnumChatMessages.COMMAND_PB_LATEST.sendMessage(player);
        } else {
            EnumChatMessages.COMMAND_PB_ERR_UNSUPPORTED_PLANT.sendMessage(player);
        }
    }

    private boolean tryGetSpecialPlantData(World world, BlockPos pos, Block block, IBlockState blockState, EntityPlayer player) {
        boolean acquired = false;
        if (PBManager.shouldCheckSpecialPlantsServer()) {   
            if (block.hasTileEntity(blockState)) {
                TileEntity tile = world.getTileEntity(pos);
                PlantBiomesMain.LOGGER.info("Tile Entity class name: " + tile.getClass().getName());//TODO For debug.
                EnumSpecialPlants special = EnumSpecialPlants.identify(tile);
                if (special != null)
                    if (acquired = special.getData(tile.serializeNBT(), world, pos))
                        EnumChatMessages.COMMAND_PB_LATEST.sendMessage(player);
            }
        } 
        return acquired;
    }
}
