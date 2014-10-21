package com.thefjong.factorialautomation.blocks.machines;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.qmunity.lib.tileentity.TileBase;
import com.thefjong.factorialautomation.blocks.BlockContainerBase;
import com.thefjong.factorialautomation.client.render.RenderPipe;
import com.thefjong.factorialautomation.tileentities.machines.TileBoiler;
import com.thefjong.factorialautomation.tileentities.machines.TilePipe;
import com.thefjong.factorialautomation.tileentities.machines.TilePump;
import com.thefjong.factorialautomation.tileentities.machines.TileSteamEngine;
/**
 * 
 * @author TheFjong
 *
 */
public class BlockPipe extends BlockContainerBase {

    public BlockPipe(String name, Class<? extends TileBase> tileClass) {

        super(Material.iron, name, tileClass);

    }
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
        ((TilePipe)world.getTileEntity(x, y, z)).sendMessage(player);
        return super.onBlockActivated(world, x, y, z, player, par6, par7, par8, par9);
    }
    @Override
    public int getRenderType() {

        return RenderPipe.RENDER_ID;
    }

    @Override
    public boolean isOpaqueCube() {

        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {

        return false;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {

        AxisAlignedBB boundingbox = AxisAlignedBB.getBoundingBox(0.2, 0.2, 0.2, .8, .8, .8);

        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            TileEntity tile = world.getTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
            if (tile instanceof TileBase) {
                if (canPipeConnectToTile((TileBase) tile)) {

                    if (tile.xCoord == x - 1) {
                        boundingbox.minX = 0;
                    }
                    if (tile.xCoord == x + 1) {
                        boundingbox.maxX = 1;
                    }
                    if (tile.yCoord == y - 1) {
                        boundingbox.minY = 0;
                    }
                    if (tile.yCoord == y + 1) {
                        boundingbox.maxY = 1;
                    }
                    if (tile.zCoord == z - 1) {
                        boundingbox.minZ = 0;
                    }
                    if (tile.zCoord == z + 1) {
                        boundingbox.maxZ = 1;
                    }
                }
            }
        }
        setBlockBounds((float) boundingbox.minX, (float) boundingbox.minY, (float) boundingbox.minZ, (float) boundingbox.maxX,
                (float) boundingbox.maxY, (float) boundingbox.maxZ);
    }

    public boolean canPipeConnectToTile(TileBase tile) {

        if (tile instanceof TilePipe || tile instanceof TilePump || tile instanceof TileBoiler || tile instanceof TileSteamEngine)
            return true;

        return false;
    }
}
