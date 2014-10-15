package com.thefjong.factorialautomation.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.qmunity.lib.tileentity.TileBase;
import com.thefjong.factorialautomation.reference.ReferenceBlocks;
import com.thefjong.factorialautomation.tileentities.machines.TileConveyorBelt;
import com.thefjong.factorialautomation.utils.CustomIconFlipped;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockConveyorBelt extends BlockContainerBase{

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public BlockConveyorBelt(String name, Class TileEntity){
        super(Material.iron, name, TileEntity);
        setBlockBounds(0F, 0F, 0F, 1F, 0.0065F, 1F);

    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side){
        CustomIconFlipped icon = null;
        if(side == ForgeDirection.UP.ordinal()) {
            TileConveyorBelt belt = (TileConveyorBelt)world.getTileEntity(x, y, z);
            ForgeDirection facingDir = belt.getFacingDirection();

            icon = new CustomIconFlipped(blockIcon);

            switch(facingDir){
                case NORTH:
                    break;
                case SOUTH:
                    icon.setMinU(icon.getInterpolatedV(0));
                    icon.setMaxU(icon.getInterpolatedV(16));
                    icon.setMinV(icon.getInterpolatedU(16));
                    icon.setMaxV(icon.getInterpolatedU(0));
                    break;
                case EAST:

                case WEST:

            }
        }
        return icon;
    }

    @Override
    public void registerBlockIcons(IIconRegister register){

        blockIcon = register.registerIcon(getTextureName() + "_1");

    }

    @Override
    public boolean isOpaqueCube(){

        return false;
    }

    @Override
    public boolean renderAsNormalBlock(){

        return false;
    }

    @Override
    public boolean canBlockStay(World world, int x, int y, int z){
        return !world.isAirBlock(x, y - 1, z);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side){
        return side == ForgeDirection.UP.ordinal() ? true : false;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block){
        if(!canBlockStay(world, x, y, z)) {
            breakBlock(world, x, y, z, block, 0);
        } else {
            super.onNeighborBlockChange(world, x, y, z, block);
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity){
        TileBase tile = (TileBase)world.getTileEntity(x, y, z);
        TileConveyorBelt tileConveyor = (TileConveyorBelt)world.getTileEntity(x, y, z);

        int xx = x + tile.getFacingDirection().offsetX;
        int yy = y + tile.getFacingDirection().offsetY;
        int zz = z + tile.getFacingDirection().offsetZ;

        int Direction = tile.getFacingDirection().ordinal();
        double xMotion = 0;
        double yMotion = 0;
        double zMotion = 0;

        if(Direction == ForgeDirection.EAST.ordinal() || Direction == ReferenceBlocks.ConveyorBelt.UP_TO_RIGHT_METADATA_INTEGER || Direction == ReferenceBlocks.ConveyorBelt.DOWN_TO_RIGHT_METADATA_INTEGER) {
            if(entity.posZ < z + 0.22) {
                zMotion = 0.2D;
            } else if(entity.posZ > z + 0.78) {
                zMotion = -0.2D;
            } else

            xMotion = 0.2D;
        } else if(Direction == ForgeDirection.SOUTH.ordinal() || Direction == ReferenceBlocks.ConveyorBelt.RIGHT_TO_DOWN_METADATA_INTEGER || Direction == ReferenceBlocks.ConveyorBelt.LEFT_TO_DOWN_METADATA_INTEGER) {
            if(entity.posX > x + 0.78D) {
                xMotion = -0.2D;
            } else if(entity.posX < x + 0.22D) {
                xMotion = 0.2D;
            } else zMotion = 0.2D;
        } else if(Direction == ForgeDirection.WEST.ordinal() || Direction == ReferenceBlocks.ConveyorBelt.DOWN_TO_LEFT_METADATA_INTEGER || Direction == ReferenceBlocks.ConveyorBelt.UP_TO_LEFT_METADATA_INTEGER) {
            if(entity.posZ > z + 0.78D) {
                zMotion = -0.2D;
            } else if(entity.posZ < z + 0.22D) {
                zMotion = 0.2D;
            } else xMotion = -0.2D;
        } else if(Direction == ForgeDirection.NORTH.ordinal() || Direction == ReferenceBlocks.ConveyorBelt.RIGHT_TO_UP_METADATA_INTEGER || Direction == ReferenceBlocks.ConveyorBelt.LEFT_TO_UP_METADATA_INTEGER) {
            if(entity.posX < x + 0.22) {
                xMotion = 0.2D;
            }
            if(entity.posX > x + 0.78) {
                xMotion = -0.2D;
            } else zMotion = -0.2D;
        }

        if(entity instanceof EntityPlayer) {
            entity.motionX = xMotion;
            entity.motionZ = zMotion;
            ((EntityPlayer)entity).fallDistance = 0;

        }
    }

    @Override
    protected boolean canRotateVertical(){

        return false;
    }

    public boolean hasNext(World world, int x, int y, int z){

        TileBase tile = (TileBase)world.getTileEntity(x, y, z);
        if(world.getBlock(x + tile.getFacingDirection().offsetX, y + tile.getFacingDirection().offsetY, z + tile.getFacingDirection().offsetZ) == Blocks.conveyorBelt) {
            return true;
        } else return false;

    }

    public BlockConveyorBelt getNext(World world, int x, int y, int z){

        TileBase tile = (TileBase)world.getTileEntity(x, y, z);
        return (BlockConveyorBelt)world.getBlock(x + tile.getFacingDirection().offsetX, y + tile.getFacingDirection().offsetY, z + tile.getFacingDirection().offsetZ);
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z){
        return World.doesBlockHaveSolidTopSurface(world, x, y - 1, z);

    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
        if(player.isSneaking()) {
            return true;
        }
        TileConveyorBelt tile = (TileConveyorBelt)world.getTileEntity(x, y, z);

        if(player.inventory.getCurrentItem() != null) {
            if(player.inventory.getCurrentItem().getItem() == Items.stick) {
                rotateByWrench(world, x, y, z);
            }
        }
        return super.onBlockActivated(world, x, y, z, player, par6, par7, par8, par9);
    }

    public void rotateByWrench(World world, int x, int y, int z){
        int meta = world.getBlockMetadata(x, y, z);
        if(meta <= 5) {
            meta = 6;
        } else if(meta >= 13) {
            meta = 6;
        } else {
            meta++;
        }

        world.setBlockMetadataWithNotify(x, y, z, meta, 2);
        System.out.println(meta);
        world.markBlockForUpdate(x, y, z);
    }
}
