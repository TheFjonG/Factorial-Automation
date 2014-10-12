package com.thefjong.factorialautomation.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.thefjong.factorialautomation.FactorialAutomation;
import com.thefjong.factorialautomation.reference.Reference;
import com.thefjong.factorialautomation.tileentities.TileBase;
import com.thefjong.factorialautomation.utils.GuiIDs;
/**
 * 
 * @author The Fjong
 *
 */
public class BlockContainerBase extends BlockBase implements ITileEntityProvider{
	private Class<? extends TileBase> tileEntityClass;
	 
	public BlockContainerBase(Material material, String name, Class TileEntity) {
		super(material, name);
		setTileEntityClass(TileEntity);
	}
	
	public GuiIDs getGuiID(){
		
		return GuiIDs.INVALID;
	}
	@Override
    public String getUnlocalizedName() {
    
        return String.format("tile.%s:%s", Reference.MODID, super.getUnlocalizedName());
    }

	
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {


        if (player.isSneaking()) {
            return false;
        }

        TileEntity entity = world.getTileEntity(x, y, z);
        if (entity == null || !(entity instanceof TileBase)) {
            return false;
        }

        if (getGuiID() != GuiIDs.INVALID) {
            if (!world.isRemote)
                player.openGui(FactorialAutomation.instance, getGuiID().ordinal(), world, x, y, z);
            return true;
        }
        return false;
    }
	
	public BlockContainerBase setTileEntityClass(Class<? extends TileBase> tileEntityClass) {

        this.tileEntityClass = tileEntityClass;
        return this;
    }
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {

		try {
			return getTileEntity().newInstance();
		} catch (Exception e) {
			return null;
		}
	}
	
	protected Class<? extends TileEntity> getTileEntity() {

        return tileEntityClass;
    }
	
	@Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack iStack) {
    
        int sideToPlace;
        if (canRotateVertical() && player.rotationPitch > 45) {
            sideToPlace = 4;
        } else if (canRotateVertical() && player.rotationPitch < -45) {
            sideToPlace = 5;
        } else {
            sideToPlace = MathHelper.floor_double(player.rotationYaw / 90F + 0.5D) & 3;
        }
        
        int metaDataToSet = 0;
        switch (sideToPlace) {
            case 0:
                metaDataToSet = ForgeDirection.SOUTH.ordinal();
                break;
            case 1:
                metaDataToSet = ForgeDirection.WEST.ordinal();
                break;
            case 2:
                metaDataToSet = ForgeDirection.NORTH.ordinal();
                break;
            case 3:
                metaDataToSet = ForgeDirection.EAST.ordinal();
                break;
            case 4:
                metaDataToSet = 1;
                break;
            case 5:
                metaDataToSet = 0;
                break;
        }
        
        world.setBlockMetadataWithNotify(x, y, z, metaDataToSet, 2);
        TileBase tile = (TileBase) world.getTileEntity(x, y, z);
        tile.setOrientation(ForgeDirection.getOrientation(metaDataToSet));
        
        //System.out.println(ForgeDirection.getOrientation(metaDataToSet).toString());
    }
    
    protected boolean canRotateVertical() {
    
        return true;
    }
}
