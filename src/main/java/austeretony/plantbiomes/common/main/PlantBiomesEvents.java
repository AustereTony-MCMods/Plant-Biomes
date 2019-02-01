package austeretony.plantbiomes.common.main;

import austeretony.plantbiomes.common.reference.CommonReference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
                    if (block instanceof IGrowable || block instanceof IPlantable || block instanceof BlockVine)
                        getPlantData(event.getWorld(), event.getPos(), block, blockState, event.getEntityPlayer());      
                }
            }   
        }
    }

    private void getPlantData(World world, BlockPos pos, Block block, IBlockState blockState, EntityPlayer player) {
        if (tryGetSpecialPlantData(world, pos, player)) return;
        int meta = block.getMetaFromState(blockState);
        String unlocalizedName = new ItemStack(Item.getItemFromBlock(block), 1, meta).getUnlocalizedName() + ".name";
        DataLoader.latestPlant = new LatestPlant(block.getRegistryName(), meta, DataLoader.getBiomeRegistryName(world, pos), pos, unlocalizedName);
        EnumChatMessages.LATEST_PLANT.sendMessage(player);
    }

    private boolean tryGetSpecialPlantData(World world, BlockPos pos, EntityPlayer player) {
        boolean acquired = false;
        if (DataLoader.isIC2Loaded() || DataLoader.forestryLoaded()) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile != null) {
                NBTTagCompound tagCompound;
                if (tile.getClass().getName().equals("ic2.core.crop.TileEntityCrop")) {
                    tagCompound = tile.serializeNBT();
                    String 
                    owner = tagCompound.getString("cropOwner"), 
                    cropId = tagCompound.getString("cropId");
                    DataLoader.latestPlant = new LatestPlant(new ResourceLocation(owner, cropId), 0, DataLoader.getBiomeRegistryName(world, pos), pos, owner + ".crop." + cropId);
                    DataLoader.latestPlant.setIC2CropId(cropId);
                    acquired = true;
                } else if (tile.getClass().getName().equals("forestry.arboriculture.tiles.TileSapling")) {
                    tagCompound = tile.serializeNBT();
                    NBTTagCompound 
                    containedTreeCompound = tagCompound.getCompoundTag("ContainedTree"),
                    genomeCompound = containedTreeCompound.getCompoundTag("Genome"),
                    alleleCompound;
                    NBTTagList chromosomesTagList = genomeCompound.getTagList("Chromosomes", 10);
                    alleleCompound = chromosomesTagList.getCompoundTagAt(0);
                    String ident = alleleCompound.getString("UID1");
                    String[] splitted = ident.split("[.]");
                    if (splitted.length > 1) {
                        DataLoader.latestPlant = new LatestPlant(new ResourceLocation(splitted[0], splitted[1]), 0, DataLoader.getBiomeRegistryName(world, pos), pos, ident + ".name");
                        DataLoader.latestPlant.setForestrySaplingIdent(ident);
                        acquired = true;
                    }
                }
            }
        } 
        if (acquired)
            EnumChatMessages.LATEST_PLANT.sendMessage(player);
        return acquired;
    }
}
