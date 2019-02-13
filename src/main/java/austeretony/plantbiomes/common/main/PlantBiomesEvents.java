package austeretony.plantbiomes.common.main;

import java.util.List;

import org.lwjgl.input.Keyboard;

import austeretony.plantbiomes.common.config.EnumConfigSettings;
import austeretony.plantbiomes.common.network.NetworkHandler;
import austeretony.plantbiomes.common.network.client.CPSyncPlantsData;
import austeretony.plantbiomes.common.network.client.CPSyncSettings;
import austeretony.plantbiomes.common.reference.CommonReference;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlantBiomesEvents {

    @SubscribeEvent
    public void onPlayerConnectsToServer(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        DataManager.initClientData();
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
        NetworkHandler.sendTo(new CPSyncSettings(), (EntityPlayerMP) event.player);
        if (EnumConfigSettings.SETTINGS_TOOLTIPS.isEnabled() 
                || (CommonReference.isOpped(event.player) && EnumConfigSettings.SETTINGS_OVERLAY.isEnabled()))
            NetworkHandler.sendTo(new CPSyncPlantsData(CPSyncPlantsData.EnumAction.SYNC_ALL), (EntityPlayerMP) event.player);
    }

    @SubscribeEvent
    public void onBlockActivated(PlayerInteractEvent event) {
        if (!event.getWorld().isRemote
                && DataManager.isConfigModeEnabled() 
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
            DataManager.latestPlantServer = new LatestPlant(
                    EnumPlantType.STANDARD,
                    block.getRegistryName(), 
                    meta, 
                    "",
                    new ItemStack(Item.getItemFromBlock(block), 1, meta).getUnlocalizedName() + ".name",
                    DataManager.getBiomeRegistryName(world, pos), 
                    pos);
            EnumChatMessages.COMMAND_PB_LATEST.sendMessage(player);
        } else {
            EnumChatMessages.COMMAND_PB_ERR_UNSUPPORTED_PLANT.sendMessage(player);
        }
    }

    private boolean tryGetSpecialPlantData(World world, BlockPos pos, Block block, IBlockState blockState, EntityPlayer player) {
        boolean acquired = false;
        if (DataManager.shouldCheckSpecialPlantsServer()) {   
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

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
        if (DataManager.isSettingsTooltipsAllowedClient()) {
            ResourceLocation itemRegistryName = event.getItemStack().getItem().getRegistryName();
            int itemMeta = event.getItemStack().getMetadata();
            if (DataManager.getBoundItemsMapClient().containsKey(itemRegistryName) && DataManager.getBoundItemsMapClient().get(itemRegistryName).hasMetaData(itemMeta))
                this.addBiomeSettingsTooltip(itemRegistryName, itemMeta, event.getToolTip());
        }
    }

    @SideOnly(Side.CLIENT)
    private void addBiomeSettingsTooltip(ResourceLocation itemRegistryName, int itemMeta, List<String> tooltip) {
        BoundMeta boundItem = DataManager.getBoundItemsMapClient().get(itemRegistryName).getMeta(itemMeta);
        PlantData plantData = DataManager.getClient(boundItem.plantRegistryName);
        if (plantData.getBoundItemRegistryName(boundItem.plantMeta).equals(itemRegistryName)) {
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                if (!DataManager.isSettingsEnabledClient())
                    tooltip.add(TextFormatting.AQUA.toString() + TextFormatting.ITALIC.toString() + I18n.format("pb.tooltip.settingsDisabled"));
                if (plantData.isValidBiomesExist(boundItem.plantMeta)) {
                    tooltip.add(I18n.format("pb.tooltip.validBiomes"));
                    for (ResourceLocation biomeRegName : plantData.getValidBiomes(boundItem.plantMeta))
                        tooltip.add(TextFormatting.DARK_GREEN + " - " + DataManager.getBiomeNamesMapClient().get(biomeRegName));
                } else {
                    if (plantData.isDeniedGlobal(boundItem.plantMeta)) {
                        tooltip.add(TextFormatting.RED + I18n.format("pb.tooltip.deniedGlobal"));
                    } else if (plantData.isDeniedBiomesExist(boundItem.plantMeta)) {
                        tooltip.add(I18n.format("pb.tooltip.deniedBiomes"));
                        for (ResourceLocation biomeRegName : plantData.getDeniedBiomes(boundItem.plantMeta))
                            tooltip.add(TextFormatting.RED + " - " + DataManager.getBiomeNamesMapClient().get(biomeRegName));   
                    }
                }
                if (plantData.canGrowOverTime(boundItem.plantMeta))
                    tooltip.add(TextFormatting.GREEN.toString() + TextFormatting.ITALIC.toString() + I18n.format("pb.tooltip.got"));
                if (plantData.canGrowWithBonemeal(boundItem.plantMeta))
                    tooltip.add(TextFormatting.GREEN.toString() + TextFormatting.ITALIC.toString() + I18n.format("pb.tooltip.gwb"));
            } else {
                tooltip.add(I18n.format("pb.tooltip.holdKey"));
            }
        }
    }
}
