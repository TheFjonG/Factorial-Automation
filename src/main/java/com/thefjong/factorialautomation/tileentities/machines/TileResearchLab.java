package com.thefjong.factorialautomation.tileentities.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.thefjong.factorialautomation.items.Items;
import com.thefjong.factorialautomation.reference.ReferenceBlocks;
import com.thefjong.factorialautomation.utils.TileEntityOwnable;

/**
 *
 * @author The Fjong
 *
 */
public class TileResearchLab extends TileEntityOwnable implements IInventory {

    @Override
    public void writeToNBT(NBTTagCompound tCompound) {

        for (ItemStack itemStack : itemStacks) {
            itemStack.writeToNBT(tCompound);
        }
        super.writeToNBT(tCompound);
    }

    @Override
    protected void onTileLoaded() {

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
     *         Copied by TheFjong
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

        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {

        if (slot <= 3 && itemStack.getItem() == Items.sciencepack) {
            return true;
        }
        if (slot > 3
                && (itemStack.getItem() == Items.upgradeeffectivity || itemStack.getItem() == Items.upgradeproductivity || itemStack.getItem() == Items.upgradespeed)) {
            return true;
        }
        return false;
    }

}
