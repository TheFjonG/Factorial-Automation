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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;

import com.qmunity.lib.tileentity.TileBase;
import com.thefjong.factorialautomation.tileentities.machines.TileBoiler;
import com.thefjong.factorialautomation.tileentities.machines.TilePipe;
import com.thefjong.factorialautomation.tileentities.machines.TilePump;
import com.thefjong.factorialautomation.tileentities.machines.TileSteamEngine;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
/**
 * 
 * @author The Fjong
 *
 */
public class RenderPipe extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler{
    
    
    
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
       
        currentBlockToRender = block;
        FAKE_RENDER_BLOCK.setBlockBounds(.2F, .2F, .2F, .8F, .8F, .8F);
        renderer.renderBlockAsItem(FAKE_RENDER_BLOCK, 1, 1.0F);
        FAKE_RENDER_BLOCK.setBlockBounds(0, 0, 0, 1, 1, 1);
    }
    
    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        IIcon icon = block.getIcon(0, 0);
        boolean renderPass = true;
        boolean renderBoxTop = true,renderBoxBottom = true,renderBoxNorth = true,renderBoxSouth = true,renderBoxWest = true,renderBoxEast = true;
        renderer.overrideBlockBounds(0.2, 0.2, 0.2, .8, .8, .8);
        
        for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
            TileEntity tile = world.getTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
            if(tile instanceof TileBase){ 
                if(canPipeConnectToTile((TileBase)tile)){
                    
                   
                    if(dir == ForgeDirection.NORTH){
                        renderBoxNorth = false;
                    }
                    if(dir == ForgeDirection.SOUTH){
                        renderBoxSouth = false;
                    }
                    if(dir == ForgeDirection.DOWN){
                        renderBoxBottom = false;
                    }
                    if(dir == ForgeDirection.UP){
                        renderBoxTop = false;
                    }
                    if(dir == ForgeDirection.EAST){
                        renderBoxEast = false;
                    }
                    if(dir == ForgeDirection.WEST){
                        renderBoxWest = false;
                    }
                }
            }
        }
        
        renderer.brightnessBottomLeft = 0;
        renderer.brightnessBottomRight = 0;
        renderer.brightnessTopLeft = 0;
        renderer.brightnessTopRight = 0;
        /** TOP **/
        if(renderBoxTop){
            
            renderer.renderFaceYPos(block, x, y, z, icon);
            if(renderPass)
                renderer.renderFaceYNeg(block, x, y+.6, z, icon);
            
        }else{
            renderer.overrideBlockBounds(0.2, 0.2, 0.2, .8, .45, .8);
            renderer.renderFaceXNeg(block, x, y+.55, z, icon);
            renderer.renderFaceXPos(block, x, y+.55, z, icon);
            renderer.renderFaceZNeg(block, x, y+.55, z, icon);
            renderer.renderFaceZPos(block, x, y+.55, z, icon);
            if(renderPass){
                renderer.renderFaceXNeg(block, x + .6, y +.55, z, icon);
                renderer.renderFaceXPos(block, x - .6, y +.55, z, icon);
                renderer.renderFaceZNeg(block, x,      y +.55, z + .6, icon);
                renderer.renderFaceZPos(block, x,      y +.55, z - .6, icon);
            }
            renderer.overrideBlockBounds(0.2, 0.2, 0.2, .8, .8, .8);
        }
        /**BOTTOM**/
        if(renderBoxBottom){ 
            renderer.renderFaceYNeg(block, x, y, z, icon);
            if(renderPass)
                renderer.renderFaceYPos(block, x, y-.6, z, icon);   
        }else{
            renderer.overrideBlockBounds(0.2, 0.55, 0.2, .8, .8, .8);
            renderer.renderFaceXNeg(block, x, y-.55, z, icon);
            renderer.renderFaceXPos(block, x, y-.55, z, icon);
            renderer.renderFaceZNeg(block, x, y-.55, z, icon);
            renderer.renderFaceZPos(block, x, y-.55, z, icon);
            if(renderPass){
                renderer.renderFaceXNeg(block, x + .6, y -.55, z, icon);
                renderer.renderFaceXPos(block, x - .6, y -.55, z, icon);
                renderer.renderFaceZNeg(block, x,      y -.55, z + .6, icon);
                renderer.renderFaceZPos(block, x,      y -.55, z - .6, icon);
            }
            renderer.overrideBlockBounds(0.2, 0.2, 0.2, .8, .8, .8);
        }
        /**NORTH**/
        if(renderBoxNorth){
            renderer.renderFaceZNeg(block, x, y, z, icon);
            if(renderPass)
                renderer.renderFaceZPos(block, x, y, z-.6, icon);  
        }else{
            renderer.overrideBlockBounds(0.2, 0.2, 0, .8, .8, .2);
            renderer.renderFaceXNeg(block, x, y, z, icon);
            renderer.renderFaceYPos(block, x, y, z, icon);
            renderer.renderFaceYNeg(block, x, y, z, icon);
            renderer.overrideBlockBounds(0.2, 0.2, .75, .8, .8, 1);
            renderer.renderFaceXPos(block, x, y, z-.75, icon);
            if(renderPass){
                renderer.overrideBlockBounds(0.2, 0.2, 0, .8, .8, .2);
                renderer.renderFaceXNeg(block, x + .6, y, z, icon);
                renderer.renderFaceYPos(block, x, y - .6, z, icon);
                renderer.renderFaceYNeg(block, x, y + .6, z, icon);
                renderer.overrideBlockBounds(0.2, 0.2, .75, .8, .8, 1);
                renderer.renderFaceXPos(block, x - .6, y, z-.75, icon);
            }
            renderer.overrideBlockBounds(0.2, 0.2, 0.2, .8, .8, .8);
        }
        if(renderBoxSouth){    
            renderer.renderFaceZPos(block, x, y, z, icon);
            if(renderPass)
                renderer.renderFaceZNeg(block, x, y, z+.6, icon);
            
        }else{
            renderer.overrideBlockBounds(0.2, 0.2, .8, .8, .8, 1);
            renderer.renderFaceXNeg(block, x, y, z, icon);
            renderer.renderFaceYPos(block, x, y, z, icon);
            renderer.renderFaceYNeg(block, x, y, z, icon);
            renderer.overrideBlockBounds(0.2, 0.2, 0, .8, .8, .25);
            renderer.renderFaceXPos(block, x, y, z + .75, icon);
            if(renderPass){
                renderer.overrideBlockBounds(0.2, 0.2, .8, .8, .8, 1);
                renderer.renderFaceXNeg(block, x + .6, y, z, icon);
                renderer.renderFaceYPos(block, x, y - .6, z, icon);
                renderer.renderFaceYNeg(block, x, y + .6, z, icon);
                renderer.overrideBlockBounds(0.2, 0.2, 0, .8, .8, .25);
                renderer.renderFaceXPos(block, x - .6, y, z + .75, icon);
            }
            renderer.overrideBlockBounds(0.2, 0.2, 0.2, .8, .8, .8);
        }
        if(renderBoxWest){   
            renderer.renderFaceXNeg(block, x, y, z, icon);
            if(renderPass)
                renderer.renderFaceXPos(block, x-.6, y, z, icon);
        }else{
            renderer.overrideBlockBounds(.75, .2, .2, 1, .8, .8);
            renderer.renderFaceZNeg(block, x-.75, y, z, icon);
            renderer.overrideBlockBounds(0, .2, .2, .25, .8, .8);
            renderer.renderFaceZPos(block, x, y, z, icon);
            renderer.renderFaceYPos(block, x, y, z, icon);
            renderer.renderFaceYNeg(block, x, y, z, icon);
            if(renderPass){
                renderer.overrideBlockBounds(.75, .2, .2, 1, .8, .8);
                renderer.renderFaceZNeg(block, x-.75, y, z + .6, icon);
                renderer.overrideBlockBounds(0, .2, .2, .25, .8, .8);
                renderer.renderFaceZPos(block, x, y, z - .6, icon);
                renderer.renderFaceYPos(block, x, y - .6, z, icon);
                renderer.renderFaceYNeg(block, x, y + .6, z, icon);
            }
            renderer.overrideBlockBounds(0.2, 0.2, 0.2, .8, .8, .8);
        }
        if(renderBoxEast){
            renderer.renderFaceXPos(block, x, y, z, icon);
            if(renderPass)
                renderer.renderFaceXNeg(block, x+.6, y, z, icon);
        }else{
            renderer.overrideBlockBounds(.75, .2, .2, 1, .8, .8);
            renderer.renderFaceYPos(block, x, y, z, icon);
            renderer.renderFaceYNeg(block, x, y, z, icon);
            renderer.renderFaceZPos(block, x, y, z, icon);
            renderer.overrideBlockBounds(0, .2, .2, .25, .8, .8);
            renderer.renderFaceZNeg(block, x+.75, y, z, icon);
            if(renderPass)
                renderer.overrideBlockBounds(.75, .2, .2, 1, .8, .8);
                renderer.renderFaceYPos(block, x, y - .6, z, icon);
                renderer.renderFaceYNeg(block, x, y + .6, z, icon);
                renderer.renderFaceZPos(block, x, y, z - .6, icon);
                renderer.overrideBlockBounds(0, .2, .2, .25, .8, .8);
                renderer.renderFaceZNeg(block, x+.75, y, z + .6, icon);
            renderer.overrideBlockBounds(0.2, 0.2, 0.2, .8, .8, .8);
        }
        
        
        
        
        
       
        
        renderer.unlockBlockBounds();
        renderer.clearOverrideBlockTexture();
        return true;
    }
    
    
    
    public boolean canPipeConnectToTile(TileBase tile){
        if(tile instanceof IFluidHandler)
            return true;
        
        return false;
    }
    
    public void setCoords(RenderBlocks renderer, double minX, double maxX, double minY, double maxY, double minZ, double maxZ){
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
        RenderBlocks renderer = RenderBlocks.getInstance();
        TilePipe pipe = (TilePipe)tile;
        
//        IFluidTank tank = pipe.getTank();
//        FluidStack stack = tank.getFluid();
//        if(stack != null){
//            int amount = pipe.getFluidAmount();
//            double height = amount / pipe.getCapacity() * .6;
//            renderer.overrideBlockBounds(.21, .21, .21, .8, height, .8);
//            renderer.renderStandardBlock(tile.getWorldObj().getBlock((int)x, (int)y, (int)z), (int)x, (int)y, (int)z);
//        }
        
    }

   

}
