package com.thefjong.factorialautomation.tileentities.machines;

import java.util.ArrayList;
import java.util.List;

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
import uk.co.qmunity.lib.tile.TileBase;
import uk.co.qmunity.lib.vec.Vec3i;

import com.thefjong.factorialautomation.utils.ChatMessageUtil;

/**
 * 
 * @author TheFjong
 * 
 */
public class TilePump extends TileBase implements IFluidHandler {

    private FluidTank               tank                = new FluidTank(8000);
    private List<IFluidHandler>     fluidTanks          = new ArrayList<IFluidHandler>();
    private List<Vec3i>              waterBlockCoords    = new ArrayList<Vec3i>();
    
    /**************************************************************TANK********************************************************/
    public void addWaterAmount(int waterAmount) {
        
        this.getTankManager().fill(new FluidStack(FluidRegistry.WATER, waterAmount), true);
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {

        return fluid == FluidRegistry.WATER;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {

        return false;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {

        return this.getTankManager().drain(tank.getFluidAmount(), doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {

        return this.getTankManager().drain(maxDrain, doDrain);
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {

        return canFill(from, resource == null ? null : resource.getFluid()) ? this.getTankManager().fill(resource, doFill) : 0;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {

        return new FluidTankInfo[] { this.getTankManager().getInfo() };
    }

    public FluidTank getTankManager() {

        return this.tank;
    }
    /******************************************************TANK***************************************************************************/
    @Override
    public void onBlockNeighbourChanged() {
        super.onBlockNeighbourChanged();

        refreshFluidTanks();
        sendUpdatePacket();
    }

    @Override
    protected void onTileLoaded() {
        super.onTileLoaded();
        for(ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS){
            if(direction != ForgeDirection.UP && direction != ForgeDirection.DOWN){
                
                this.waterBlockCoords.add(new Vec3i(this.getX() + direction.offsetX,this.getY() - 1,this.getZ() + direction.offsetZ));
            }
        }
    }
    @Override
    public void readFromNBT(NBTTagCompound tCompound) {
        super.readFromNBT(tCompound);

        this.getTankManager().readFromNBT(tCompound);
    }
    
    @Override
    public void writeToNBT(NBTTagCompound tCompound) {
        super.writeToNBT(tCompound);

        this.getTankManager().writeToNBT(tCompound);
    }
    
    protected void refreshFluidTanks() {

        this.fluidTanks.clear();
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
            if (direction != ForgeDirection.DOWN && !this.getWorld().isAirBlock(this.getX() + direction.offsetX, this.getY() + direction.offsetY, this.getZ() + direction.offsetZ)) {
                TileEntity tile = this.getWorld().getTileEntity(this.getX() + direction.offsetX, this.getY() + direction.offsetY, this.getZ() + direction.offsetZ);
                if (tile instanceof IFluidHandler) {

                    this.fluidTanks.add((IFluidHandler) tile);
                }
            }
        }
    }

    private boolean canGenerateWater(){
        if(this.getWorld().getBlock(this.getX(), this.getY() - 1, this.getZ()) == Blocks.water){
            
            if(this.getWorld().getBlock(this.waterBlockCoords.get(0).getX(), this.waterBlockCoords.get(0).getY(), this.waterBlockCoords.get(0).getZ()) == Blocks.water && this.getWorld().getBlock(this.waterBlockCoords.get(1).getX(), this.waterBlockCoords.get(1).getY(), this.waterBlockCoords.get(1).getZ()) == Blocks.water)
                return true;
            if(this.getWorld().getBlock(this.waterBlockCoords.get(2).getX(), this.waterBlockCoords.get(2).getY(), this.waterBlockCoords.get(2).getZ()) == Blocks.water && this.getWorld().getBlock(this.waterBlockCoords.get(3).getX(), this.waterBlockCoords.get(3).getY(), this.waterBlockCoords.get(3).getZ()) == Blocks.water)
                return true;
        }

        return false;
    }
    
    @Override
    public void updateEntity() {
        if (this.getTicker() > 0) {
            if(this.getTankManager().getFluidAmount() != this.getTankManager().getCapacity() && canGenerateWater()){
                this.addWaterAmount(1000);
            }
            if (this.getTankManager().getFluidAmount() > 0 && this.fluidTanks.size() > 0) {
                double waterToExtract = this.getTankManager().getFluidAmount() / this.fluidTanks.size();
                waterToExtract = waterToExtract % 1 == 0 ? (int) waterToExtract : (int) Math.round(waterToExtract);
                for (IFluidHandler tank : this.fluidTanks) {
                    if (tank != null) {
                        this.drain(ForgeDirection.UNKNOWN, tank.fill(ForgeDirection.UNKNOWN, new FluidStack(FluidRegistry.WATER, (int) waterToExtract), true), true);
                    }
                }
            }
        }
        super.updateEntity();
    }


    public void sendMessage(EntityPlayer player) {

        if (!this.getWorld().isRemote) {
            ChatMessageUtil.sendChatMessageToPlayer(player, "Water amount: " + this.getTankManager().getFluidAmount());
            ChatMessageUtil.sendChatMessageToPlayer(player, "Tanks located: " + this.fluidTanks.size());
        }
    }
}
