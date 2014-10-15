package com.thefjong.factorialautomation.tileentities.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import com.qmunity.lib.tileentity.TileBase;
import com.thefjong.factorialautomation.blocks.Blocks;

public class TilePipe extends TileBase implements IFluidHandler{
    
    public FluidStack fluidStack;
    
    private FluidTank tank = new FluidTank(fluidStack, 8000);
    
    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if(resource.getFluid() == FluidRegistry.WATER || resource.getFluid() == Blocks.fluidSteam){
           markDirty();
           return tank.fill(resource, doFill);
        }
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        markDirty();
        return tank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
      
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        
       return fluidStack.amount >=0? true : false;
        
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        
        FluidTankInfo[] fluidTankInfo = {new FluidTankInfo(tank)};
        return fluidTankInfo;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound tCompound) {
        tank.readFromNBT(tCompound);
        super.readFromNBT(tCompound);
    }
    
    @Override
    public void writeToNBT(NBTTagCompound tCompound) {
        tank.writeToNBT(tCompound);
        super.writeToNBT(tCompound);
    }
    
    
    
    @Override
    public void updateEntity() {
        
        for(ForgeDirection dir: ForgeDirection.VALID_DIRECTIONS){
            if(worldObj.getTileEntity(xCoord+dir.offsetX, yCoord+dir.offsetY, zCoord+dir.offsetZ) instanceof TilePipe){
               
                TilePipe nextPipe = (TilePipe) worldObj.getTileEntity(xCoord+dir.offsetX, yCoord+dir.offsetY, zCoord+dir.offsetZ);
                if(fluidStack.amount > nextPipe.fluidStack.amount){
                    if(fluidStack.getFluid() == nextPipe.fluidStack.getFluid()){
                        
                        int amount = fluidStack.amount - nextPipe.fluidStack.amount / 2;
                        nextPipe.fill(dir, new FluidStack(fluidStack.getFluid(), amount), true);
                        drain(dir.getOpposite(), amount, true);
                    }
                }
            }
            
        }
        
        
        
        
        super.updateEntity();
    }
    
    
    public void sendMessageToPlayer(EntityPlayer player){
        
        if(!worldObj.isRemote)         
           player.addChatComponentMessage(new ChatComponentText("Fluid Amount: " + fluidStack.amount));
    }
}
