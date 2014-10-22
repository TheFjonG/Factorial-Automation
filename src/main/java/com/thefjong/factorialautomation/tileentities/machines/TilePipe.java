    package com.thefjong.factorialautomation.tileentities.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import com.qmunity.lib.tileentity.TileBase;
import com.thefjong.factorialautomation.utils.ChatMessageUtil;
/**
 *  
 * @author TheFjong
 * 
 */
public class TilePipe extends TileBase implements IFluidHandler {

    public boolean[] visualConnectionCache = new boolean[6];
    
    private void reloadConnectionCache() {
        visualConnectionCache = new boolean[6];
        for (ForgeDirection forgeDirection : ForgeDirection.VALID_DIRECTIONS) {
            if (worldObj.getTileEntity(xCoord + forgeDirection.offsetX, yCoord + forgeDirection.offsetY, zCoord + forgeDirection.offsetZ) instanceof IFluidHandler) {
                visualConnectionCache[forgeDirection.ordinal()] = true;
            }
        }
    }

    @Override
    public void onBlockNeighbourChanged() {

        reloadConnectionCache();
        sendUpdatePacket();
        super.onBlockNeighbourChanged();
    }

    @Override
    protected void writeToPacketNBT(NBTTagCompound tCompound) {
        super.writeToPacketNBT(tCompound);
        for (int i=0; i<visualConnectionCache.length; i++) {
            tCompound.setBoolean("connectionCache_" + i, visualConnectionCache[i]);
        }
    }

    @Override
    protected void readFromPacketNBT(NBTTagCompound tCompound) {
        super.readFromPacketNBT(tCompound);

        visualConnectionCache = new boolean[6];
        for (int i=0; i<visualConnectionCache.length; i++) {
            visualConnectionCache[i] = tCompound.getBoolean("connectionCache_" + i);
        }

        markForRenderUpdate();
    }
    
    /** Fluid pipe stuff */
    public class FluidPipe extends FluidTank{

        public FluidPipe() {
            super(null, 1000);
            
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
    
    FluidPipe pipe = new FluidPipe();
    
    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
       
       return resource == null? 0 : pipe.fill(resource, doFill); 
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        
        return pipe.getFluid() == null? null : pipe.drain(pipe.getFluidAmount(), doDrain); 
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
       
        return pipe.getFluid() == null? null : pipe.drain(maxDrain, doDrain); 
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        
        return worldObj.getTileEntity(xCoord + from.offsetX, yCoord + from.offsetY, zCoord + from.offsetZ) instanceof TilePipe? true : false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        
        return worldObj.getTileEntity(xCoord + from.offsetX, yCoord + from.offsetY, zCoord + from.offsetZ) instanceof TilePipe? true : false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
       
        return new FluidTankInfo[]{ pipe.getInfo() };
    }
    
    @Override
    public void writeToNBT(NBTTagCompound tCompound) {
        tCompound.setInteger("ticker", ticker);
        pipe.writeToNBT(tCompound);
        super.writeToNBT(tCompound);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound tCompound) {
        ticker = tCompound.getInteger("ticker");
        pipe.readFromNBT(tCompound);     
        super.readFromNBT(tCompound);
        markForRenderUpdate();
    }
    
    public FluidPipe getPipeManager(){
        
        return pipe;
    }
    
    int ticker;
    
    @Override
    public void updateEntity() {
        ticker++;
        /** Reloads after a minute **/
        
        if(ticker >= 1200){
            reloadConnectionCache();
            ticker = 0;
        }
            
        for(int i = 0; i < visualConnectionCache.length; i++){
            if(visualConnectionCache[i] && pipe.getFluid() != null){
                ForgeDirection dir = ForgeDirection.getOrientation(i);
                TileEntity tileEntity = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
                
                if(!(tileEntity instanceof IFluidHandler) || tileEntity == null) return;
                
                    IFluidHandler tile = (IFluidHandler) tileEntity;
                if(!(tile instanceof TilePipe) && tile.canFill(dir, pipe.getFluid().getFluid())){
                    if(!worldObj.isRemote){
                        
                        int amount = tile.fill(dir, pipe.getFluid(), true);
                        pipe.drain(amount, true);
                    }
                    
                }else if(tile instanceof TilePipe){
                    
                    TilePipe nextPipe = (TilePipe) tile;
                    if(nextPipe.getPipeManager().getFluid() == null){
                        
                        FluidStack newStack = pipe.getFluid().copy();
                        newStack.amount /= 2;
                        int amount = tile.fill(dir, newStack, true);
                        pipe.drain(amount, true);
                        
                    }else if(pipe.getFluidAmount() > nextPipe.getPipeManager().getFluidAmount()){
                        int amount = (pipe.getFluidAmount() - nextPipe.getPipeManager().getFluidAmount()) / 2;
                        FluidStack newStack = pipe.getFluid().copy();
                        newStack.amount = amount;
                        amount = tile.fill(dir, newStack, true);
                        pipe.drain(amount, true);
                    }
                }
            }
        }
        super.updateEntity();
    }
    
    public void sendMessage(EntityPlayer player){
        if(pipe.getFluid() != null && !worldObj.isRemote)
            ChatMessageUtil.sendChatMessageToPlayer(player, "amount: " + pipe.getFluid().amount);
    }
}
