package com.thefjong.factorialautomation.tileentities.machines;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
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

public class TilePump extends TileBase implements IFluidHandler{
    
    private FluidStack fluidStack = new FluidStack(FluidRegistry.WATER, 0);
    
    private FluidTank tank = new FluidTank(fluidStack, 8000);
    
    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if(resource.getFluid() == FluidRegistry.WATER){
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
    
    int ticker;
    
    @Override
    public void updateEntity() {
        ticker++;
        if(ticker >=40){
            
            Block block = worldObj.getBlock(xCoord, yCoord-1, zCoord);
            if(block == Blocks.water && fluidStack.amount <=7000){  
                if(FluidRegistry.lookupFluidForBlock(block) != null && worldObj.getBlockMetadata(xCoord, yCoord-1, zCoord) == 0){
                    
                    fill(ForgeDirection.UNKNOWN, new FluidStack(FluidRegistry.WATER, 1000), true);
                    markDirty();
                    worldObj.setBlockToAir(xCoord, yCoord-1, zCoord);
                    worldObj.markBlockForUpdate(xCoord, yCoord-1, zCoord);
                }
            }
                
            ticker = 0;
        }
        for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS){
            if(worldObj.getTileEntity(xCoord+dir.offsetX, yCoord+dir.offsetY, zCoord+dir.offsetZ) instanceof TilePipe){
                TilePipe pipe = (TilePipe) worldObj.getTileEntity(xCoord+dir.offsetX, yCoord+dir.offsetY, zCoord+dir.offsetZ); 
                if(fluidStack != null && pipe.fluidStack != null){
                    
                    if(fluidStack.amount > pipe.fluidStack.amount){
                        if(fluidStack.getFluid() == pipe.fluidStack.getFluid()){
                            
                            int amount = fluidStack.amount - pipe.fluidStack.amount / 2;
                            pipe.fill(dir, new FluidStack(fluidStack.getFluid(), amount), true);
                            drain(dir.getOpposite(), amount, true);
                        }
                    }
                }else{
                    pipe.fluidStack = new FluidStack(fluidStack.getFluid(), 0);
                    
                    if(fluidStack.amount > pipe.fluidStack.amount){
                        if(fluidStack.getFluid() == pipe.fluidStack.getFluid()){
                            
                            int amount = fluidStack.amount - pipe.fluidStack.amount / 2;
                            pipe.fill(dir, new FluidStack(fluidStack.getFluid(), amount), true);
                            drain(dir.getOpposite(), amount, true);
                        }
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
