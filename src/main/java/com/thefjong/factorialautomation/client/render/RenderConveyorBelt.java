package com.thefjong.factorialautomation.client.render;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import uk.co.qmunity.lib.tileentity.IRotatable;

import com.thefjong.factorialautomation.tileentities.machines.TileConveyorBelt;
import com.thefjong.factorialautomation.tileentities.machines.TileConveyorBelt.Section;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
/**
 * 
 * @author MineMaarten
 *
 */
public class RenderConveyorBelt extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler {

    public static final int RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
    public static final Block FAKE_RENDER_BLOCK = new Block(Material.rock) {

        @Override
        public IIcon getIcon(int meta, int side) {

            return currentBlockToRender.getIcon(meta, side);
        }
    };

    public static Block currentBlockToRender = Blocks.stone;

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

        currentBlockToRender = block;
        FAKE_RENDER_BLOCK.setBlockBounds(0F, 0F, 0F, 1F, 0.0065F, 1F);
        renderer.renderBlockAsItem(FAKE_RENDER_BLOCK, 1, 1.0F);
        FAKE_RENDER_BLOCK.setBlockBounds(0, 0, 0, 1, 1, 1);
    }

    /*
     * UV Deobfurscation derp: West = South East = North North = East South = West
     */
    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

        TileConveyorBelt te = (TileConveyorBelt) world.getTileEntity(x, y, z);
        ForgeDirection d = ((IRotatable) te).getFacingDirection().getOpposite();
        renderer.flipTexture = true;
        if (te.getSection() == Section.LEFT) {
            d = d.getRotation(ForgeDirection.UP);
        } else if (te.getSection() == Section.RIGHT) {
            d = d.getRotation(ForgeDirection.DOWN);
        }

        switch (d) {
        case DOWN:
            renderer.uvRotateSouth = 3;
            renderer.uvRotateNorth = 3;
            renderer.uvRotateEast = 3;
            renderer.uvRotateWest = 3;
            break;
        case NORTH:
            renderer.uvRotateSouth = 1;
            renderer.uvRotateNorth = 2;
            break;
        case SOUTH:
            renderer.uvRotateSouth = 2;
            renderer.uvRotateNorth = 1;
            renderer.uvRotateTop = 3;
            renderer.uvRotateBottom = 3;
            break;
        case WEST:
            renderer.uvRotateEast = 1;
            renderer.uvRotateWest = 2;
            renderer.uvRotateTop = 2;
            renderer.uvRotateBottom = 1;
            break;
        case EAST:
            renderer.uvRotateEast = 2;
            renderer.uvRotateWest = 1;
            renderer.uvRotateTop = 1;
            renderer.uvRotateBottom = 2;
            break;
        }
        boolean ret = renderer.renderStandardBlock(block, x, y, z);
        renderer.uvRotateSouth = 0;
        renderer.uvRotateEast = 0;
        renderer.uvRotateWest = 0;
        renderer.uvRotateNorth = 0;
        renderer.uvRotateTop = 0;
        renderer.uvRotateBottom = 0;

        return ret;
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
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTick) {

        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        for (TileConveyorBelt.BeltStack stack : ((TileConveyorBelt) te).beltStacks) {
            stack.render(partialTick);
        }
        GL11.glPopMatrix();
    }

}