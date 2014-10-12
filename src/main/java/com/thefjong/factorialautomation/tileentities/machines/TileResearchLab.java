package com.thefjong.factorialautomation.tileentities.machines;

import net.minecraft.nbt.NBTTagCompound;

import com.thefjong.factorialautomation.handlers.KnowledgeHandler;
import com.thefjong.factorialautomation.tileentities.TileBase;
/**
 * 
 * @author The Fjong
 *
 */
public class TileResearchLab extends TileBase{

	public KnowledgeHandler PlayerOwner = null;
	public String PlayerOwnerName;
	
	public void setPlayerOwner(KnowledgeHandler owner){
		PlayerOwnerName = owner.getPlayerProfile().playerName;
		PlayerOwner = owner;
	}
	public KnowledgeHandler getPlayerOwner(){
		
		return PlayerOwner;
		
	}

	@Override
	public void readFromNBT(NBTTagCompound tCompound) {
		PlayerOwnerName = tCompound.getString("PlayerOwnerName");
		super.readFromNBT(tCompound);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tCompound) {
		tCompound.setString("PlayerOwnerName", PlayerOwnerName);
		super.writeToNBT(tCompound);
	}
	
	@Override
	protected void onTileLoaded() {
		PlayerOwner = new KnowledgeHandler(PlayerOwnerName);
		super.onTileLoaded();
	}
	
	
}
