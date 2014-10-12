package com.thefjong.factorialautomation.tileentities.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.thefjong.factorialautomation.handlers.KnowledgeHandler;
import com.thefjong.factorialautomation.items.ItemSciencePack;
import com.thefjong.factorialautomation.items.ItemUpgrades;
import com.thefjong.factorialautomation.reference.ReferenceBlocks;
import com.thefjong.factorialautomation.tileentities.TileBase;
/**
 * 
 * @author The Fjong
 *
 */
public class TileResearchLab extends TileBase implements IInventory{

	public KnowledgeHandler playerOwner = null;
	public String playerOwnerName;
	
	public void setPlayerOwner(KnowledgeHandler owner){
		
		playerOwnerName = owner.getPlayerProfile().playerName;
		playerOwner = owner;
	}
	
	public KnowledgeHandler getPlayerOwner(){
		
		return playerOwner;	
	}

	@Override
	public void readFromNBT(NBTTagCompound tCompound) {
		
		playerOwnerName = tCompound.getString("playerOwnerName");
		for(int i = 0; i < itemStacks.length; i++){
			itemStacks[i].readFromNBT(tCompound);
		}
		super.readFromNBT(tCompound);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tCompound) {
		
		tCompound.setString(playerOwnerName, "playerOwnerName");
		for(int i = 0; i < itemStacks.length; i++){
			itemStacks[i].writeToNBT(tCompound);
		}
		super.writeToNBT(tCompound);
	}
	
	@Override
	protected void onTileLoaded() {
		
		playerOwner = new KnowledgeHandler(playerOwnerName);
		super.onTileLoaded();
	}
	
	/** IInventory **/
	
	public ItemStack[] itemStacks = new ItemStack[6];
	
	@Override
	public int getSizeInventory() {
		
		return itemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		
		return itemStacks[slot];
	}
	
	/**
	 * Part copied from BluePower
	 * 
	 * @author MineMaarten
	 *
	 * Copied by TheFjong
	 */
	@Override
    public ItemStack decrStackSize(int slot, int amount) {
    
        ItemStack itemStack = getStackInSlot(slot);
        if (itemStack != null) {
            if (itemStack.stackSize <= amount) {
                setInventorySlotContents(slot, null);
            } else {
                itemStack = itemStack.splitStack(amount);
                if (itemStack.stackSize == 0) {
                    setInventorySlotContents(slot, null);
                }
            }
        }
        
        return itemStack;
    }

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		
		return itemStacks[slot];
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		itemStacks[slot] = stack;
		
	}

	@Override
	public String getInventoryName() {
		
		return ReferenceBlocks.RESEARCH_LAB_NAME;
	}

	@Override
	public boolean hasCustomInventoryName() {

		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {

		if(player.getDisplayName() == playerOwnerName) 
			return true;
		
		return false;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack ItemStack) {
		if(slot <= 3 && ItemStack.getItem() instanceof ItemSciencePack){
			return true;
		}
		if(slot >3 && ItemStack.getItem() instanceof ItemUpgrades){
			return true;
		}
		return false;
	}
	
	
}
