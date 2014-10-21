package com.thefjong.factorialautomation.tileentities.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
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
public class TilePump extends TileBase implements IFluidHandler {

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
    
    /** Fluid tank stuff */
    public class FluidPump extends FluidTank{

        public FluidPump() {
            super(null, 8000);
            
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
    
    FluidPump tank = new FluidPump();
    
    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
       
       return resource == null? 0 : tank.fill(resource, doFill); 
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        
        return tank.getFluid() == null? null : tank.drain(tank.getFluidAmount(), doDrain); 
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
       
        return tank.getFluid() == null? null : tank.drain(maxDrain, doDrain); 
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        
        return fluid == FluidRegistry.WATER? true : false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
       
        return new FluidTankInfo[]{ tank.getInfo() };
    }
    
    @Override
    public void writeToNBT(NBTTagCompound tCompound) {
        tCompound.setInteger("ticker", ticker);
        tank.writeToNBT(tCompound);
        super.writeToNBT(tCompound);
    }
    
    @Override
    public void readFromNBT(NBTTagCompound tCompound) {
        ticker = tCompound.getInteger("ticker");
        tank.readFromNBT(tCompound);     
        super.readFromNBT(tCompound);
        markForRenderUpdate();
    }
    
    public FluidPump getTankManager(){
        
        return tank;
    }
    
    int ticker;
    int waterTicker;
    @Override
    public void updateEntity() {
        ticker++;
        waterTicker++;
        /** Reloads after a minute **/
        
        if(ticker >= 1200){
            reloadConnectionCache();
            ticker = 0;
        }
            
        for(int i = 0; i < visualConnectionCache.length; i++){
            if(visualConnectionCache[i] && tank.getFluid() != null){
                ForgeDirection dir = ForgeDirection.getOrientation(i);
                TileEntity tileEntity = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
                
                if(!(tileEntity instanceof IFluidHandler) || tileEntity == null) return;
                
                    IFluidHandler tile = (IFluidHandler) tileEntity;
                if(tile.canFill(dir, tank.getFluid().getFluid())){
                    
                    int amount = tile.fill(dir, tank.getFluid(), true);
                    tank.drain(amount, true);
                }
            }
        }
        if(waterTicker >= 10){
            
            if(worldObj.getBlock(xCoord, yCoord-1, zCoord) == Blocks.water){
                if(worldObj.getBlockMetadata(xCoord, yCoord, zCoord) == 0){
                    if(!(tank.getFluidAmount() >= 8000)){
                        
                        tank.fill(new FluidStack(FluidRegistry.WATER, 1000), true);
                        worldObj.setBlockToAir(xCoord, yCoord - 1, zCoord);
                        waterTicker = 0;    
                    }
                }
            }
        }
        super.updateEntity();
    }
    
    public void sendMessage(EntityPlayer player){
        if(tank.getFluid() != null && !worldObj.isRemote)
            ChatMessageUtil.sendChatMessageToPlayer(player, "amount: " + tank.getFluid().amount);
    }

}
