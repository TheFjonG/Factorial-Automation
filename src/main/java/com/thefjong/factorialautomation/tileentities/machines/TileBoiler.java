package com.thefjong.factorialautomation.tileentities.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import com.qmunity.lib.tileentity.TileBase;
import com.thefjong.factorialautomation.blocks.ModBlocks;
import com.thefjong.factorialautomation.utils.ChatMessageUtil;
/**
 *  
 * @author TheFjong
 * 
 */
public class TileBoiler extends TileBase implements IFluidHandler{
    
    
    public class FluidBoiler extends FluidTank{

        public FluidBoiler() {
            super(null, 10000);
            
        }
        @Override
        public int fill(FluidStack resource, boolean doFill) {
            
            return super.fill(resource, doFill);
        }
        @Override
        public FluidStack drain(int maxDrain, boolean doDrain) {
           
            return super.drain(maxDrain, doDrain);
        }
 
    }
    FluidBoiler waterTank = new FluidBoiler();
    FluidBoiler steamTank = new FluidBoiler();
    private int heat = 0;
    private int burnTime = 0;
    
   
    @Override
    public void readFromNBT(NBTTagCompound tCompound) {
        
        waterTank = (FluidBoiler) waterTank.readFromNBT(tCompound);
        steamTank = (FluidBoiler) steamTank.readFromNBT(tCompound);
        heat = tCompound.getInteger("heat");
        heat = tCompound.getInteger("burnTime");
        super.readFromNBT(tCompound);
        markForRenderUpdate();
        markDirty();
    }
    
    @Override
    public void writeToNBT(NBTTagCompound tCompound) {
       
        waterTank.writeToNBT(tCompound);
        steamTank.writeToNBT(tCompound);
        tCompound.setInteger("heat", heat);
        tCompound.setInteger("burnTime", burnTime);
        super.writeToNBT(tCompound);
    }
    
    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if(resource.getFluid() != FluidRegistry.WATER) return 0;
        return waterTank.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
       
        return steamTank.drain(steamTank.getFluidAmount(), doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
       
        return steamTank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        
        return fluid == FluidRegistry.WATER? true : false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        
        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        
        return new FluidTankInfo[]{waterTank.getInfo(), steamTank.getInfo()};
    }
    
    @Override
    public void updateEntity() {
        heat = 100;
        if(waterTank.getFluid() != null && steamTank.getFluidAmount() != steamTank.getCapacity() && heat >= 100){
            if(!worldObj.isRemote){
                
                int amount = steamTank.fill(new FluidStack(ModBlocks.fluidSteam, waterTank.getFluidAmount()), true);
                waterTank.drain(amount, true);
            }
        }
        super.updateEntity();
    }
    
    public void sendMessage(EntityPlayer player){
        if(!worldObj.isRemote){
            ChatMessageUtil.sendChatMessageToPlayer(player, "Water Amount : " + waterTank.getFluidAmount());
            ChatMessageUtil.sendChatMessageToPlayer(player, "Steam Amount : " + steamTank.getFluidAmount());
        }
    }
}
