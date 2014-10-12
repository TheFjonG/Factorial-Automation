package com.thefjong.factorialautomation.tileentities.machines;

import net.minecraft.item.ItemStack;

import com.thefjong.factorialautomation.tileentities.TileBase;

public class TileConveyorBelt extends TileBase{

	public ItemStack[] inventoryStacks = new ItemStack[8];

	
	public ItemStack getStackInSlot(int slot){
		
		return inventoryStacks[slot];
	}
	
	public void setInventorySlotContents(int slot, ItemStack itemStack) {
		inventoryStacks[slot] = itemStack;		
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
	}
	
	public void moveToNextSlot(int currentSlot){
	
	}
}
