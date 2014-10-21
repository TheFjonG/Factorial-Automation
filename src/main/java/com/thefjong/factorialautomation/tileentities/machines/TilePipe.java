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
import net.minecraftforge.fluids.IFluidTank;

import com.qmunity.lib.tileentity.TileBase;
/**
 *  
 * @author TheFjong
 * 
 */
public class TilePipe extends TileBase implements IFluidTank, IFluidHandler {

    /** Do not use this!.. use tank.getFluid() instead! **/
    private FluidStack fluidStack;
    public int capacity = 1000;
    public FluidTank tank = new FluidTank(fluidStack, capacity);

    public boolean[] visualConnectionCache = new boolean[6];

    @Override
    public FluidStack getFluid() {

        return fluidStack;
    }

    @Override
    public int getFluidAmount() {

        return tank.getFluidAmount();
    }

    @Override
    public int getCapacity() {

        return 8000;
    }

    @Override
    public FluidTankInfo getInfo() {

        return tank.getInfo();
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {

        if (resource.getFluid() != FluidRegistry.WATER)
            return 0;
        return tank.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {

        return tank.drain(maxDrain, doDrain);
    }

    @Override
    public void writeToNBT(NBTTagCompound tCompound) {

        tank.writeToNBT(tCompound);
        super.writeToNBT(tCompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound tCompound) {

        tank = tank.readFromNBT(tCompound);
        super.readFromNBT(tCompound);
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {

        return fill(resource, doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {

        return drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {

        return drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {

        if (fluid == FluidRegistry.WATER)
            return true;
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {

        if (fluid == FluidRegistry.WATER && from != ForgeDirection.DOWN)
            return true;
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {

        return new FluidTankInfo[] { tank.getInfo() };
    }

    public void sendMessageToPlayer(EntityPlayer player) {

        if (!worldObj.isRemote && tank.getFluid() != null)
            player.addChatComponentMessage(new ChatComponentText("Fluid Amount: " + tank.getFluidAmount()));

    }

    int ticker;

    @Override
    public void updateEntity() {

        ticker++;
        if (ticker > 3) {

            forceMovement();
            ticker = 0;
        }
        super.updateEntity();
    }

    public void forceMovement() {

        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            if (worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ) instanceof IFluidHandler) {
                IFluidHandler tile = (IFluidHandler) worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord
                        + dir.offsetZ);
                if (tank.getFluid() != null && tank.getFluidAmount() > 0) {
                    markDirty();
                    FluidStack fluid = tank.getFluid().copy();
                    fluid.amount = tank.getFluidAmount();
                    int amount = tile.fill(ForgeDirection.UNKNOWN, fluid, true);
                    drain(amount, true);
                }
            }
        }
    }

    public IFluidTank getTank() {

        if (worldObj.isRemote)
            return null;
        return tank;
    }

    private void reloadConnectionCache() {
        visualConnectionCache = new boolean[6];
        for (ForgeDirection forgeDirection : ForgeDirection.VALID_DIRECTIONS) {
            if (worldObj.getTileEntity(xCoord + forgeDirection.offsetX, yCoord + forgeDirection.offsetY, zCoord + forgeDirection.offsetZ) instanceof IFluidHandler) {
                visualConnectionCache[forgeDirection.ordinal()] = true;
            }
        }
    }

    @Override
    protected void onTileLoaded() {

        markDirty();
        super.onTileLoaded();
    }

    @Override
    public void onBlockNeighbourChanged() {

        markDirty();
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
}
