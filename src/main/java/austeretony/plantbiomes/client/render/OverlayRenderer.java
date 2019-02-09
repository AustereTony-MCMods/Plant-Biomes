package austeretony.plantbiomes.client.render;

import java.util.HashMap;
import java.util.Map;

import austeretony.plantbiomes.common.main.EnumSpecialPlants;
import austeretony.plantbiomes.common.main.EnumStandardPlants;
import austeretony.plantbiomes.common.main.PBManager;
import austeretony.plantbiomes.common.util.WorldPosition;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class OverlayRenderer {

    private World world;

    private EntityPlayer player;

    private static final double RANGE = 20.0D;//overlay range (40x40x40 cube now)

    private double x, y, z, offsetX, offsetY, offsetZ;

    private MutableBlockPos blockPos = new MutableBlockPos();

    private WorldPosition tilePos = new WorldPosition();

    private TileEntity tile;

    private IBlockState state;

    private int meta;

    private Block block;

    private ResourceLocation blockName, biomeName;

    private String blockClassName;

    public static final Map<WorldPosition, ResourceLocation> SPECIAL_NAMES = new HashMap<WorldPosition, ResourceLocation>();

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onWorldRender(RenderWorldLastEvent event) {
        if (PBManager.shouldRenderOverlayClient()) {
            this.world = Minecraft.getMinecraft().world;
            this.player = Minecraft.getMinecraft().player;
            for (this.x = this.player.posX - RANGE; this.x < this.player.posX + RANGE; this.x++) {
                for (this.y = this.player.posY - RANGE; this.y < this.player.posY + RANGE; this.y++) {
                    for (this.z = this.player.posZ - RANGE; this.z < this.player.posZ + RANGE; this.z++) {
                        this.blockPos.setPos(this.x, this.y, this.z);
                        renderOverlay(event.getPartialTicks());
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private void renderOverlay(float partialTicks) {
        this.state = this.world.getBlockState(this.blockPos);
        this.block = this.state.getBlock();
        if (this.block != Blocks.AIR) {
            this.blockName = this.block.getRegistryName();
            this.meta = this.block.getMetaFromState(state);
            if (PBManager.isTilesAllowedClient() && PBManager.shouldCheckSpecialPlantsClient()) {  
                this.tile = this.world.getTileEntity(this.blockPos);
                if (this.tile != null) {
                    EnumSpecialPlants special = EnumSpecialPlants.identify(this.tile);
                    if (special != null) {
                        this.tilePos.setPosition(this.blockPos);
                        this.meta = EnumSpecialPlants.SPECIALS_META;
                        if (!this.SPECIAL_NAMES.containsKey(this.tilePos)) {
                            this.blockName = special.createRegistryName(this.tile.serializeNBT());
                            this.SPECIAL_NAMES.put(new WorldPosition(this.tilePos), this.blockName);
                        } else {
                            this.blockName = this.SPECIAL_NAMES.get(this.tilePos);
                        }
                    }
                }
            } 
            if (PBManager.existClient(this.blockName)) {
                this.blockClassName = this.block.getClass().getName();
                this.biomeName = PBManager.getBiomeRegistryName(this.world, this.blockPos);
                this.offsetX = this.player.prevPosX + (this.player.posX - this.player.prevPosX) * (double) partialTicks;
                this.offsetY = this.player.prevPosY + (this.player.posY - this.player.prevPosY) * (double) partialTicks;
                this.offsetZ = this.player.prevPosZ + (this.player.posZ - this.player.prevPosZ) * (double) partialTicks;
                GlStateManager.pushMatrix();
                GlStateManager.translate(this.blockPos.getX() - this.offsetX, this.blockPos.getY() - this.offsetY + 0.03125F, this.blockPos.getZ() - this.offsetZ);
                GlStateManager.disableTexture2D();
                GlStateManager.color(0.0F, 0.9F, 0.0F);
                if (PBManager.getClient(this.blockName).hasMainMeta()) {
                    if (PBManager.getClient(this.blockName).getMainMetaPlant().isValidBiomesExist()) {
                        if (PBManager.getClient(this.blockName).getMainMetaPlant().isValidBiome(this.biomeName)) {
                            GlStateManager.color(0.0F, 0.5F, 0.05F);
                            GlStateManager.translate(0.0F, 0.005F, 0.0F);
                        } else {
                            GlStateManager.color(0.5F, 0.0F, 0.0F);
                        }
                    } else if (PBManager.getClient(this.blockName).getMainMetaPlant().isDeniedBiome(this.biomeName) || PBManager.getClient(this.blockName).getMainMetaPlant().isDeniedGlobal()) {
                        GlStateManager.color(0.9F, 0.0F, 0.0F);
                        GlStateManager.translate(0.0F, - 0.005F, 0.0F);
                    }
                } else {
                    if (PBManager.getClient(this.blockName).hasMetaData(this.meta)) {
                        if (PBManager.getClient(this.blockName).getMeta(this.meta).isValidBiomesExist()) {
                            if (PBManager.getClient(this.blockName).getMeta(this.meta).isValidBiome(this.biomeName)) {
                                GlStateManager.color(0.0F, 0.5F, 0.05F);
                                GlStateManager.translate(0.0F, 0.005F, 0.0F);
                            } else {
                                GlStateManager.color(0.5F, 0.0F, 0.0F);
                            }
                        } else if (PBManager.getClient(this.blockName).getMeta(this.meta).isDeniedBiome(this.biomeName) || PBManager.getClient(this.blockName).getMeta(meta).isDeniedGlobal()) {
                            GlStateManager.color(0.9F, 0.0F, 0.0F);
                            GlStateManager.translate(0.0F, - 0.005F, 0.0F);
                        }
                    }
                }
                AxisAlignedBB blockAABB = this.block.getBoundingBox(this.state, this.world, this.blockPos);
                Tessellator tesselator = Tessellator.getInstance();
                BufferBuilder buffBuilder = tesselator.getBuffer();
                buffBuilder.begin(3, DefaultVertexFormats.POSITION);
                buffBuilder.pos(blockAABB.minX, blockAABB.maxY, blockAABB.minZ).endVertex();
                buffBuilder.pos(blockAABB.maxX, blockAABB.maxY, blockAABB.minZ).endVertex();
                buffBuilder.pos(blockAABB.maxX, blockAABB.maxY, blockAABB.maxZ).endVertex();
                buffBuilder.pos(blockAABB.minX, blockAABB.maxY, blockAABB.maxZ).endVertex();
                buffBuilder.pos(blockAABB.minX, blockAABB.maxY, blockAABB.minZ).endVertex();
                tesselator.draw();
                if (!(this.blockClassName.equals(EnumStandardPlants.MC_GRASS_BLOCK.className) 
                        || this.blockClassName.equals(EnumStandardPlants.MC_MYCELIUM_BLOCK.className)
                        || this.blockClassName.equals(EnumStandardPlants.BOP_BLOCK_GRASS.className))) {
                    buffBuilder.begin(3, DefaultVertexFormats.POSITION);
                    buffBuilder.pos(blockAABB.minX, blockAABB.minY, blockAABB.minZ).endVertex();
                    buffBuilder.pos(blockAABB.maxX, blockAABB.minY, blockAABB.minZ).endVertex();
                    buffBuilder.pos(blockAABB.maxX, blockAABB.minY, blockAABB.maxZ).endVertex();
                    buffBuilder.pos(blockAABB.minX, blockAABB.minY, blockAABB.maxZ).endVertex();
                    buffBuilder.pos(blockAABB.minX, blockAABB.minY, blockAABB.minZ).endVertex();
                    tesselator.draw();
                    buffBuilder.begin(1, DefaultVertexFormats.POSITION);
                    buffBuilder.pos(blockAABB.minX, blockAABB.minY, blockAABB.minZ).endVertex();
                    buffBuilder.pos(blockAABB.minX, blockAABB.maxY, blockAABB.minZ).endVertex();
                    buffBuilder.pos(blockAABB.maxX, blockAABB.minY, blockAABB.minZ).endVertex();
                    buffBuilder.pos(blockAABB.maxX, blockAABB.maxY, blockAABB.minZ).endVertex();
                    buffBuilder.pos(blockAABB.maxX, blockAABB.minY, blockAABB.maxZ).endVertex();
                    buffBuilder.pos(blockAABB.maxX, blockAABB.maxY, blockAABB.maxZ).endVertex();
                    buffBuilder.pos(blockAABB.minX, blockAABB.minY, blockAABB.maxZ).endVertex();
                    buffBuilder.pos(blockAABB.minX, blockAABB.maxY, blockAABB.maxZ).endVertex();
                    tesselator.draw();
                }
                GlStateManager.enableTexture2D();
                GlStateManager.popMatrix();
            }
        }
    }
}
