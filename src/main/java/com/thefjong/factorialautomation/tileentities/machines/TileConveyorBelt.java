package com.thefjong.factorialautomation.tileentities.machines;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;

import com.qmunity.lib.tileentity.TileBase;

public class TileConveyorBelt extends TileBase{

    private static final double MIN_ITEM_DISTANCE = 0.2;
    private final List<BeltStack> beltStacks = new ArrayList<BeltStack>();
    private ForgeDirection entryDirection = ForgeDirection.SOUTH;

    private void setEntryDirection(ForgeDirection dir){
        boolean isDifferent = dir != entryDirection;
        entryDirection = dir;
        if(isDifferent) {

        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag){
        super.writeToNBT(tag);
        NBTTagList tagList = new NBTTagList();
        for(BeltStack stack : beltStacks) {
            NBTTagCompound stackTag = new NBTTagCompound();
            stack.writeToNBT(stackTag);
            tagList.appendTag(stackTag);
        }
        tag.setTag("beltStacks", tagList);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag){
        super.readFromNBT(tag);

        NBTTagList tagList = tag.getTagList("beltStacks", 10);
        beltStacks.clear();
        for(int i = 0; i < tagList.tagCount(); i++) {
            beltStacks.add(loadBeltStackFromNBT(tagList.getCompoundTagAt(i)));
        }
    }

    private BeltStack loadBeltStackFromNBT(NBTTagCompound tag){
        BeltStack stack = new BeltStack(ItemStack.loadItemStackFromNBT(tag.getCompoundTag("stack")), tag.getBoolean("isOnLeftSide"));
        stack.isOnFirstPart = tag.getBoolean("isOnFirstPart");
        stack.progress = tag.getDouble("progress");
        return stack;
    }

    private class BeltStack{
        public ItemStack stack;
        public boolean isOnFirstPart;
        public boolean isOnLeftSide;
        public double progress;

        public BeltStack(ItemStack stack, boolean isOnLeftSide){
            this.stack = stack;
            this.isOnLeftSide = isOnLeftSide;
            isOnFirstPart = true;
        }

        public void writeToNBT(NBTTagCompound tag){
            NBTTagCompound stackTag = new NBTTagCompound();
            stack.writeToNBT(stackTag);
            tag.setTag("stack", stackTag);
            tag.setBoolean("isOnFirstPart", isOnFirstPart);
            tag.setBoolean("isOnLeftSide", isOnLeftSide);
            tag.setDouble("progress", progress);
        }

    }

}
