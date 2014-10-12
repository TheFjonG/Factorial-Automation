package com.thefjong.factorialautomation.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.thefjong.factorialautomation.reference.ReferenceBlocks;
import com.thefjong.factorialautomation.tileentities.TileBase;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockConveyorBelt extends BlockContainerBase {
	IIcon[] blockIcons;
	IIcon[] rotatedBlockIcons;
	
	public BlockConveyorBelt(String name, Class TileEntity) {
		super(Material.iron, name, TileEntity);
		setBlockBounds(0F, 0F, 0F, 1F, 0.0065F, 1F);
		blockIcons = new IIcon[4];
		rotatedBlockIcons = new IIcon[8];
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		if(side == ForgeDirection.UP.ordinal()){		
			if(meta == ReferenceBlocks.ConveyorBelt.NORTH_METADATA_INTEGER){
				
				return blockIcons[1];
			}
			if(meta == ReferenceBlocks.ConveyorBelt.SOUTH_METADATA_INTEGER){
				return blockIcons[0];
			}
			if(meta == ReferenceBlocks.ConveyorBelt.WEST_METADATA_INTEGER){
				return blockIcons[3];
			}
			if(meta == ReferenceBlocks.ConveyorBelt.EAST_METADATA_INTEGER){
				return blockIcons[2];
			}
			if(meta == ReferenceBlocks.ConveyorBelt.DOWN_TO_LEFT_METADATA_INTEGER){
				return rotatedBlockIcons[0];
			}
			if(meta == ReferenceBlocks.ConveyorBelt.DOWN_TO_RIGHT_METADATA_INTEGER){
				return rotatedBlockIcons[1];
			}
			if(meta == ReferenceBlocks.ConveyorBelt.UP_TO_LEFT_METADATA_INTEGER){
				return rotatedBlockIcons[2];
			}
			if(meta == ReferenceBlocks.ConveyorBelt.UP_TO_RIGHT_METADATA_INTEGER){
				return rotatedBlockIcons[3];
			}
			if(meta == ReferenceBlocks.ConveyorBelt.RIGHT_TO_UP_METADATA_INTEGER){
				return rotatedBlockIcons[4];
			}
			if(meta == ReferenceBlocks.ConveyorBelt.RIGHT_TO_DOWN_METADATA_INTEGER){
				return rotatedBlockIcons[5];
			}
			if(meta == ReferenceBlocks.ConveyorBelt.LEFT_TO_UP_METADATA_INTEGER){
				return rotatedBlockIcons[6];
			}
			if(meta == ReferenceBlocks.ConveyorBelt.LEFT_TO_DOWN_METADATA_INTEGER){
				return rotatedBlockIcons[7];
			}
		}
		return blockIcon;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister register) {
		for(int i = 0; i < blockIcons.length; i++){
			blockIcons[i] = register.registerIcon(getTextureName()+"_"+i);
			
		}
		//DOWN
		rotatedBlockIcons[0] = register.registerIcon(getTextureName()+"_" + "13");
		rotatedBlockIcons[1] = register.registerIcon(getTextureName()+"_" + "12");
		//UP
		rotatedBlockIcons[2] = register.registerIcon(getTextureName()+"_" + "03");
		rotatedBlockIcons[3] = register.registerIcon(getTextureName()+"_" + "02");
		//RIGHT
		rotatedBlockIcons[4] = register.registerIcon(getTextureName()+"_" + "31");
		rotatedBlockIcons[5] = register.registerIcon(getTextureName()+"_" + "30");
		//LEFT
		rotatedBlockIcons[6] = register.registerIcon(getTextureName()+"_" + "21");
		rotatedBlockIcons[7] = register.registerIcon(getTextureName()+"_" + "20");
	}
	
	@Override
	public boolean isOpaqueCube() {
		
		return false;
	}
	@Override
	public boolean renderAsNormalBlock() {
		
		return false;
	}
	
	
	public boolean canBlockStay(World world, int x, int y, int z){
        return !world.isAirBlock(x, y - 1, z);
    }
	
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
    {
        return side == ForgeDirection.UP.ordinal() ? true : false;
    }
	
	@Override
	public void onNeighborBlockChange(World world, int x,int y, int z, Block block) {
		if(!canBlockStay(world, x, y, z)){
			this.breakBlock(world, x, y, z, block, 0);
		}else{
			super.onNeighborBlockChange(world, x, y, z, block);
		}
	}
	
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x,int y, int z, Entity entity) {
		
		TileBase tile = (TileBase) world.getTileEntity(x, y, z);
			int xx = x+tile.getOrientation().offsetX;
			int yy = y+tile.getOrientation().offsetY;
			int zz = z+tile.getOrientation().offsetZ;
			int slot = 1;
			if(entity instanceof EntityItem){	
				if(entity.posX > x+.5 && entity.posZ > z+.5){
					slot = 1;
				}else if(entity.posX < x+.5 && entity.posZ > z+.5){
					slot = 2;
				}else if(entity.posX > x+.5 && entity.posZ < z+.5){
					slot = 3;
				}else if(entity.posX < x+.5 && entity.posZ <z+.5){
					slot = 4;
				}else{
					return;
				}
			}
			
			int Direction = world.getBlockMetadata(x, y, z);
			double xMotion = 0;
			double yMotion = 0;
			double zMotion = 0;
			
			if(Direction == ForgeDirection.EAST.ordinal()|| Direction == ReferenceBlocks.ConveyorBelt.UP_TO_RIGHT_METADATA_INTEGER || Direction == ReferenceBlocks.ConveyorBelt.DOWN_TO_RIGHT_METADATA_INTEGER){
				if(entity.posZ < z + 0.22){			
					zMotion = 0.2D;
				}else
				if(entity.posZ > z + 0.78){			
					zMotion = -0.2D;
				}else
				
					xMotion = 0.2D;	
			}
			else if(Direction == ForgeDirection.SOUTH.ordinal()|| Direction == ReferenceBlocks.ConveyorBelt.RIGHT_TO_DOWN_METADATA_INTEGER || Direction == ReferenceBlocks.ConveyorBelt.LEFT_TO_DOWN_METADATA_INTEGER){
				if(entity.posX > x + 0.78D){
					xMotion = -0.2D;
				}else
				if(entity.posX < x + 0.22D){
					xMotion = 0.2D;
				}else
					zMotion = 0.2D;	
			}
			else if(Direction == ForgeDirection.WEST.ordinal()|| Direction == ReferenceBlocks.ConveyorBelt.DOWN_TO_LEFT_METADATA_INTEGER|| Direction == ReferenceBlocks.ConveyorBelt.UP_TO_LEFT_METADATA_INTEGER){
				if(entity.posZ > z + 0.78D){
					zMotion = - 0.2D;
				}else
				if(entity.posZ < z + 0.22D){
					zMotion = 0.2D;
				}else				
					xMotion = -0.2D;		
			}
			else if(Direction == ForgeDirection.NORTH.ordinal()|| Direction == ReferenceBlocks.ConveyorBelt.RIGHT_TO_UP_METADATA_INTEGER || Direction == ReferenceBlocks.ConveyorBelt.LEFT_TO_UP_METADATA_INTEGER){
				if(entity.posX < x + 0.22){
					xMotion = 0.2D;
				}
				if(entity.posX > x + 0.78){				
					xMotion = - 0.2D;
				}else				
					zMotion = -0.2D;		
			}
			
		
			
			
			if(entity instanceof EntityPlayer){			
				entity.motionX = xMotion;
				entity.motionZ = zMotion;				
				((EntityPlayer)entity).fallDistance = 0;
			}
	}
	
	@Override
	protected boolean canRotateVertical() {
		
		return false;
	}
	
	public int getMetaData(World world,int x, int y, int z){
		
		return world.getBlockMetadata(x, y, z);
	}
	public boolean hasNext(World world, int x, int y, int z){
		
		TileBase tile = (TileBase) world.getTileEntity(x, y, z);
		if(world.getBlock(x+tile.getOrientation().offsetX, y+tile.getOrientation().offsetY, z+tile.getOrientation().offsetZ) == Blocks.conveyorBelt){
			return true;
		}else
			return false;
		
	}
	public BlockConveyorBelt getNext(World world, int x, int y, int z){
		
		TileBase tile = (TileBase) world.getTileEntity(x, y, z);
		return (BlockConveyorBelt) world.getBlock(x+tile.getOrientation().offsetX, y+tile.getOrientation().offsetY, z+tile.getOrientation().offsetZ);
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, int x,int y, int z) {	
		return World.doesBlockHaveSolidTopSurface(world, x, y-1, z);
				
	}
	
	public AxisAlignedBB getAxisFromSlot(int slot, int x, int y, int z){
		if(slot == 1){
			
			return AxisAlignedBB.getBoundingBox(x + .5, y + 0, z + .5, x + 1, y + 1,z + 1);
		}else
		if(slot == 2){
			
			return AxisAlignedBB.getBoundingBox(x + 0, y + 0, z + .5, x + .5, y + 1,z + 1);
		}else
		if(slot == 3){
			
			return AxisAlignedBB.getBoundingBox(x + .5, y + 0, z + 0, x + 1, y + 1,z + .5);
		}else
		if(slot == 4){
			
			return AxisAlignedBB.getBoundingBox(x + 0, y + 0, z + 0, x + .5, y + 1,z + .5);
		}
			
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,EntityPlayer player, int par6, float par7, float par8, float par9) {
		if(player.isSneaking()){
			return true;
		}
		System.out.println(world.getBlockMetadata(x, y, z));
		if(player.inventory.getCurrentItem() != null){
			if(player.inventory.getCurrentItem().getItem() == Items.stick){
				rotateByWrench(world, x, y, z);
			}
		}
		return super.onBlockActivated(world, x, y, z, player, par6, par7, par8, par9);
	}
	
	public void rotateByWrench(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta <=5){
			meta = 6;
		}else
		if(meta >=13 ){
			meta = 6;
		}else{
			meta++;
		}
		
		world.setBlockMetadataWithNotify(x, y, z, meta, 2);
		System.out.println(meta);
		world.markBlockForUpdate(x, y, z);
	}
}
