package austeretony.plantbiomes.client.render;

import java.util.HashMap;
import java.util.Map;

import austeretony.plantbiomes.common.main.DataManager;
import austeretony.plantbiomes.common.main.EnumSpecialPlants;
import austeretony.plantbiomes.common.main.EnumStandardPlants;
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

    private AxisAlignedBB blockAABB;

    private Tessellator tesselator;

    private BufferBuilder buffBuilder;

    public static final Map<WorldPosition, ResourceLocation> TILES = new HashMap<WorldPosition, ResourceLocation>();

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (DataManager.shouldRenderOverlayClient()) {
            this.world = Minecraft.getMinecraft().world;
            this.player = Minecraft.getMinecraft().player;
            for (this.x = this.player.posX - RANGE; this.x < this.player.posX + RANGE; this.x++) {
                for (this.y = this.player.posY - RANGE; this.y < this.player.posY + RANGE; this.y++) {
                    for (this.z = this.player.posZ - RANGE; this.z < this.player.posZ + RANGE; this.z++) {
                        this.blockPos.setPos(this.x, this.y, this.z);
                        this.renderOverlay(event.getPartialTicks());
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
            this.meta = this.block.getMetaFromState(this.state);
            if (DataManager.isTilesOverlayAllowedClient() && DataManager.shouldCheckSpecialPlantsClient()) {  
                if (this.block.hasTileEntity(this.state)) {
                    this.tile = this.world.getTileEntity(this.blockPos);
                    EnumSpecialPlants special = EnumSpecialPlants.identify(this.tile);
                    if (special != null) {
                        this.tilePos.setPosition(this.blockPos);
                        this.meta = EnumSpecialPlants.SPECIALS_META;
                        if (!this.TILES.containsKey(this.tilePos)) {
                            this.blockName = special.createRegistryName(this.tile.serializeNBT());
                            this.TILES.put(new WorldPosition(this.tilePos), this.blockName);
                        } else {
                            this.blockName = this.TILES.get(this.tilePos);
                        }
                    }
                }
            } 
            if (this.blockName == null) return;
            if ((DataManager.existClient(this.blockName) && DataManager.getClient(this.blockName).hasMetaData(this.meta)) 
                    || (DataManager.existClient(this.blockName) && DataManager.getClient(this.blockName).hasMainMeta())) {
                this.blockClassName = this.block.getClass().getName();
                this.biomeName = DataManager.getBiomeRegistryName(this.world, this.blockPos);
                this.offsetX = this.player.prevPosX + (this.player.posX - this.player.prevPosX) * (double) partialTicks;
                this.offsetY = this.player.prevPosY + (this.player.posY - this.player.prevPosY) * (double) partialTicks;
                this.offsetZ = this.player.prevPosZ + (this.player.posZ - this.player.prevPosZ) * (double) partialTicks;
                GlStateManager.pushMatrix();
                GlStateManager.translate(this.blockPos.getX() - this.offsetX, this.blockPos.getY() - this.offsetY + 0.03125F, this.blockPos.getZ() - this.offsetZ);
                GlStateManager.disableTexture2D();
                GlStateManager.color(0.0F, 0.9F, 0.0F);
                if (DataManager.getClient(this.blockName).isValidBiomesExist(this.meta)) {
                    if (DataManager.getClient(this.blockName).isValidBiome(this.meta, this.biomeName)) {
                        GlStateManager.color(0.0F, 0.5F, 0.05F);
                        GlStateManager.translate(0.0F, 0.005F, 0.0F);
                    } else {
                        GlStateManager.color(0.5F, 0.0F, 0.0F);
                    }
                } else if (DataManager.getClient(this.blockName).isDeniedBiome(this.meta, this.biomeName) || DataManager.getClient(this.blockName).isDeniedGlobal(this.meta)) {
                    GlStateManager.color(0.9F, 0.0F, 0.0F);
                    GlStateManager.translate(0.0F, - 0.005F, 0.0F);
                }
                this.blockAABB = this.block.getBoundingBox(this.state, this.world, this.blockPos);
                this.tesselator = Tessellator.getInstance();
                this.buffBuilder = this.tesselator.getBuffer();
                this.buffBuilder.begin(3, DefaultVertexFormats.POSITION);
                this.buffBuilder.pos(this.blockAABB.minX, this.blockAABB.maxY, this.blockAABB.minZ).endVertex();
                this.buffBuilder.pos(this.blockAABB.maxX, this.blockAABB.maxY, this.blockAABB.minZ).endVertex();
                this.buffBuilder.pos(this.blockAABB.maxX, this.blockAABB.maxY, this.blockAABB.maxZ).endVertex();
                this.buffBuilder.pos(this.blockAABB.minX, this.blockAABB.maxY, this.blockAABB.maxZ).endVertex();
                this.buffBuilder.pos(this.blockAABB.minX, this.blockAABB.maxY, this.blockAABB.minZ).endVertex();
                this.tesselator.draw();
                if (!(this.blockClassName.equals(EnumStandardPlants.MC_GRASS_BLOCK.className) 
                        || this.blockClassName.equals(EnumStandardPlants.MC_MYCELIUM_BLOCK.className)
                        || this.blockClassName.equals(EnumStandardPlants.BOP_BLOCK_GRASS.className))) {
                    this.buffBuilder.begin(3, DefaultVertexFormats.POSITION);
                    this.buffBuilder.pos(this.blockAABB.minX, this.blockAABB.minY, this.blockAABB.minZ).endVertex();
                    this.buffBuilder.pos(this.blockAABB.maxX, this.blockAABB.minY, this.blockAABB.minZ).endVertex();
                    this.buffBuilder.pos(this.blockAABB.maxX, this.blockAABB.minY, this.blockAABB.maxZ).endVertex();
                    this.buffBuilder.pos(this.blockAABB.minX, this.blockAABB.minY, this.blockAABB.maxZ).endVertex();
                    this.buffBuilder.pos(this.blockAABB.minX, this.blockAABB.minY, this.blockAABB.minZ).endVertex();
                    this.tesselator.draw();
                    this.buffBuilder.begin(1, DefaultVertexFormats.POSITION);
                    this.buffBuilder.pos(this.blockAABB.minX, this.blockAABB.minY, this.blockAABB.minZ).endVertex();
                    this.buffBuilder.pos(this.blockAABB.minX, this.blockAABB.maxY, this.blockAABB.minZ).endVertex();
                    this.buffBuilder.pos(this.blockAABB.maxX, this.blockAABB.minY, this.blockAABB.minZ).endVertex();
                    this.buffBuilder.pos(this.blockAABB.maxX, this.blockAABB.maxY, this.blockAABB.minZ).endVertex();
                    this.buffBuilder.pos(this.blockAABB.maxX, this.blockAABB.minY, this.blockAABB.maxZ).endVertex();
                    this.buffBuilder.pos(this.blockAABB.maxX, this.blockAABB.maxY, this.blockAABB.maxZ).endVertex();
                    this.buffBuilder.pos(this.blockAABB.minX, this.blockAABB.minY, this.blockAABB.maxZ).endVertex();
                    this.buffBuilder.pos(this.blockAABB.minX, this.blockAABB.maxY, this.blockAABB.maxZ).endVertex();
                    this.tesselator.draw();
                }
                GlStateManager.enableTexture2D();
                GlStateManager.popMatrix();
            }
        }
    }
}
