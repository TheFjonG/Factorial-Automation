package com.thefjong.factorialautomation.client.render;

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
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;

import org.lwjgl.opengl.GL11;

import uk.co.qmunity.lib.tile.TileBase;

import com.thefjong.factorialautomation.reference.Reference;
import com.thefjong.factorialautomation.tileentities.machines.TilePipe;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

/**
 *
 * @author Dmillerw
 *
 */
public class RenderPipe extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler {

    public static final IModelCustom PIPE_MODEL = AdvancedModelLoader.loadModel(new ResourceLocation(Reference.MODID + ":models/pipe.obj"));
    public static final IModelCustom PIPE_BEND_MODEL = AdvancedModelLoader.loadModel(new ResourceLocation(Reference.MODID + ":models/pipe_bend.obj"));
    public static final IModelCustom PIPE_JUNCTION_MODEL = AdvancedModelLoader.loadModel(new ResourceLocation(Reference.MODID
            + ":models/pipe_junction.obj"));

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
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        PIPE_MODEL.renderAll();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
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
        TilePipe tilePipe = (TilePipe) tile;

        GL11.glPushMatrix();
        GL11.glTranslated(x + .5, y + .5, z + .5);
        GL11.glDisable(GL11.GL_TEXTURE_2D);

        //TODO Preemptive TODO as this will DEFINITELY need to be cleaned up / rewritten

        int connectionCount = 0;
        for (boolean element : tilePipe.visualConnectionCache) {
            if (element)
                connectionCount++;
        }

        if (connectionCount == 0) {
            PIPE_MODEL.renderAll();
        } else if (connectionCount == 1) {
            for (int i = 0; i < tilePipe.visualConnectionCache.length; i++) {
                if (tilePipe.visualConnectionCache[i]) {
                    ForgeDirection forgeDirection = ForgeDirection.getOrientation(i);

                    if (forgeDirection == ForgeDirection.NORTH || forgeDirection == ForgeDirection.SOUTH) {
                        // NO ROTATION
                    } else if (forgeDirection == ForgeDirection.WEST || forgeDirection == ForgeDirection.EAST) {
                        GL11.glRotated(90, 0, 1, 0);
                    } else if (forgeDirection == ForgeDirection.UP || forgeDirection == ForgeDirection.DOWN) {
                        GL11.glRotated(90, 1, 0, 0);
                    }

                    PIPE_MODEL.renderAll();
                }
            }
        } else if (connectionCount == 2) {
            ForgeDirection connection1 = null;
            ForgeDirection connection2 = null;

            for (int i = 0; i < tilePipe.visualConnectionCache.length; i++) {
                if (tilePipe.visualConnectionCache[i]) {
                    if (connection1 == null)
                        connection1 = ForgeDirection.getOrientation(i);
                    else
                        connection2 = ForgeDirection.getOrientation(i);
                }
            }

            if (connection1 == ForgeDirection.SOUTH && connection2 == ForgeDirection.UP || connection2 == ForgeDirection.SOUTH
                    && connection1 == ForgeDirection.UP) {

                GL11.glRotated(180, 0, 1, 0);
            } else if (connection1 == ForgeDirection.NORTH && connection2 == ForgeDirection.UP || connection2 == ForgeDirection.NORTH
                    && connection1 == ForgeDirection.UP) {

                GL11.glRotated(180, 0, 1, 0);
            } else if (connection1 == ForgeDirection.SOUTH && connection2 == ForgeDirection.DOWN || connection2 == ForgeDirection.SOUTH
                    && connection1 == ForgeDirection.DOWN) {

                GL11.glRotated(180, 0, 1, 0);
            } else if (connection1 == ForgeDirection.NORTH && connection2 == ForgeDirection.DOWN || connection2 == ForgeDirection.NORTH
                    && connection1 == ForgeDirection.DOWN) {

                GL11.glRotated(180, 0, 1, 0);
            }

            if (connection1.getOpposite() == connection2 || connection2.getOpposite() == connection1) {
                if (connection1 == ForgeDirection.NORTH || connection1 == ForgeDirection.SOUTH) {
                    // NO ROTATION
                } else if (connection1 == ForgeDirection.WEST || connection1 == ForgeDirection.EAST) {
                    GL11.glRotated(90, 0, 1, 0);
                } else if (connection1 == ForgeDirection.UP || connection1 == ForgeDirection.DOWN) {
                    GL11.glRotated(90, 1, 0, 0);
                }

                PIPE_MODEL.renderAll();
            } else {
                boolean flip = false;
                ForgeDirection rotation = ForgeDirection.UNKNOWN;
                if (connection1 == ForgeDirection.UP || connection2 == ForgeDirection.UP) {
                    flip = true;
                    if (connection1 == ForgeDirection.UP) {
                        rotation = connection2;
                    } else {
                        rotation = connection1;
                    }
                } else if (connection1 == ForgeDirection.DOWN || connection2 == ForgeDirection.DOWN) {
                    flip = false;
                    if (connection1 == ForgeDirection.DOWN) {
                        rotation = connection2;
                    } else {
                        rotation = connection1;
                    }
                }

                if (rotation != ForgeDirection.UNKNOWN) {
                    switch (rotation) {
                    case SOUTH:
                        if (!flip)
                            GL11.glRotated(180, 0, 1, 0);
                        break;
                    case NORTH:
                        if (flip)
                            GL11.glRotated(180, 0, 1, 0);
                        break;
                    case EAST:
                        GL11.glRotated(flip ? -90 : 90, 0, 1, 0);
                        break;
                    case WEST:
                        GL11.glRotated(flip ? 90 : -90, 0, 1, 0);
                        break;
                    }
                    if (flip)
                        GL11.glRotated(180, 1, 0, 0);
                } else {
                    if (connection1 == ForgeDirection.NORTH && connection2 == ForgeDirection.WEST || connection1 == ForgeDirection.WEST
                            || connection2 == ForgeDirection.NORTH) {
                        GL11.glRotated(90, 0, 0, 1);
                        GL11.glRotated(180, 1, 0, 0);
                    } else if (connection1 == ForgeDirection.NORTH && connection2 == ForgeDirection.EAST || connection1 == ForgeDirection.EAST
                            || connection2 == ForgeDirection.NORTH) {
                        GL11.glRotated(90, 0, 0, 1);
                        GL11.glRotated(90, 1, 0, 0);
                    } else if (connection1 == ForgeDirection.SOUTH && connection2 == ForgeDirection.WEST || connection1 == ForgeDirection.WEST
                            || connection2 == ForgeDirection.SOUTH) {
                        GL11.glRotated(-90, 0, 0, 1);
                    } else if (connection1 == ForgeDirection.SOUTH && connection2 == ForgeDirection.EAST || connection1 == ForgeDirection.EAST
                            || connection2 == ForgeDirection.SOUTH) {
                        GL11.glRotated(-90, 0, 0, 1);
                        GL11.glRotated(-90, 1, 0, 0);
                    }
                }

                PIPE_BEND_MODEL.renderAll();
            }
        } else {
            renderJunction(tilePipe.visualConnectionCache);
        }

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
    }

    private void renderJunction(boolean[] connectionMap) {
        //TODO Make these names changes to the actual model
        final String[] newNames = new String[] { "down", "up", "west", "east", "north", "south" };
        String[] parts = new String[connectionMap.length + 1];
        for (int i = 0; i < connectionMap.length; i++) {
            if (connectionMap[i])
                parts[i] = "junction_" + newNames[i];
            else
                parts[i] = "junction_" + newNames[i] + "_cover";

        }
        parts[connectionMap.length] = "junction";
        PIPE_JUNCTION_MODEL.renderOnly(parts);
    }

}
