package com.thefjong.factorialautomation.client.render;

import com.qmunity.lib.tileentity.TileBase;
import com.thefjong.factorialautomation.blocks.ModBlocks;
import com.thefjong.factorialautomation.reference.Reference;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.fluids.IFluidHandler;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author The Fjong
 *
 */
public class RenderPipe extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler {

    public static final IModelCustom PIPE_MODEL = AdvancedModelLoader.loadModel(new ResourceLocation(Reference.MODID + ":models/pipe.obj"));
    public static final IModelCustom PIPE_BEND_MODEL = AdvancedModelLoader.loadModel(new ResourceLocation(Reference.MODID + ":models/pipe_bend.obj"));
    public static final IModelCustom PIPE_JUNCTION_MODEL = AdvancedModelLoader.loadModel(new ResourceLocation(Reference.MODID + ":models/pipe_junction.obj"));

    public static final int RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
    public static Block currentBlockToRender = Blocks.stone;
    public static final Block FAKE_RENDER_BLOCK = new Block(Material.rock) {

        @Override
        public IIcon getIcon(int meta, int side) {

            return currentBlockToRender.getIcon(meta, side);
        }
    };

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        PIPE_MODEL.renderAll();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        return false;
    }

    public boolean canPipeConnectToTile(TileBase tile) {

        if (tile instanceof IFluidHandler)
            return true;

        return false;
    }

    public void setCoords(RenderBlocks renderer, double minX, double maxX, double minY, double maxY, double minZ, double maxZ) {

        renderer.renderMinX = minX;
        renderer.renderMaxX = maxX;
        renderer.renderMinY = minY;
        renderer.renderMaxY = maxY;
        renderer.renderMinZ = minZ;
        renderer.renderMaxZ = maxZ;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {

        return true;
    }

    @Override
    public int getRenderId() {

        return RENDER_ID;
    }

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTick) {
        GL11.glPushMatrix();
        GL11.glTranslated(x + .5, y + .5, z + .5);
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        Block above = tile.getWorldObj().getBlock(tile.xCoord, tile.yCoord + 1, tile.zCoord);

        if (above == ModBlocks.pipe) {
            GL11.glRotated(90, 1, 0, 0);
            PIPE_MODEL.renderAll();
        } else {
            Block next = tile.getWorldObj().getBlock(tile.xCoord, tile.yCoord, tile.zCoord - 1);
            if (next == ModBlocks.pipe) {
                PIPE_MODEL.renderAll();
            } else {
                PIPE_BEND_MODEL.renderAll();
            }
        }

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
    }

}
