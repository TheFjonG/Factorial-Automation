package com.thefjong.factorialautomation.tileentities;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
/**
 * 
 * @author The Fjong
 *
 */
public class TileBase extends TileEntity{
	
	public int ticker;
	@Override
    public void updateEntity() {

        if (ticker == 0) {
            onTileLoaded();
        }
        super.updateEntity();
        ticker++;
    }
	
	public void setOrientation(ForgeDirection orientation){
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, orientation.ordinal(), 1);
    }

    public void setOrientation(int orientation){
    	worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, orientation, 1);
    }
    
    public ForgeDirection getOrientation(){
        return ForgeDirection.getOrientation(worldObj.getBlockMetadata(xCoord, yCoord, zCoord));
    }
    
	protected void onTileLoaded() {}
}
