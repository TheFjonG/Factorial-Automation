package com.thefjong.factorialautomation.tileentities.machines;

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
import net.minecraftforge.fluids.IFluidTank;

import com.qmunity.lib.tileentity.TileBase;

public class TilePump extends TileBase implements IFluidTank, IFluidHandler {

    /** Do not use this!.. use tank.getFluid() instead! **/
    private FluidStack fluidStack;

    private int capacity = 8000;
    FluidTank tank = new FluidTank(fluidStack, capacity);

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

    int ticker;

    @Override
    public void updateEntity() {

        ticker++;
        if (ticker >= 10) {
            if (worldObj.getBlock(xCoord, yCoord - 1, zCoord) == Blocks.water && worldObj.getBlockMetadata(xCoord, yCoord - 1, zCoord) == 0) {
                if (tank.getFluidAmount() < tank.getCapacity()) {

                    fill(new FluidStack(FluidRegistry.WATER, 1000), true);
                    worldObj.setBlock(xCoord, yCoord - 1, zCoord, Blocks.air, 0, 7);

                }
            }
            ticker = 0;
        }
        forceOutput();
        super.updateEntity();
    }

    public void forceOutput() {

        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            if (worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ) instanceof IFluidHandler) {
                IFluidHandler tile = (IFluidHandler) worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord
                        + dir.offsetZ);
                if (tank.getFluid() != null && tank.getFluidAmount() > 0) {

                    FluidStack fluid = tank.getFluid().copy();
                    fluid.amount = tank.getFluidAmount();
                    int amount = tile.fill(dir, fluid, false);
                    if (amount > 0) {
                        tile.fill(dir, fluid, true);
                        drain(amount, true);
                    } else {
                        continue;
                    }
                }
            }
        }

    }

    public void sendMessageToPlayer(EntityPlayer player) {

        if (!worldObj.isRemote && tank.getFluid() != null) {
            player.addChatComponentMessage(new ChatComponentText("Fluid Amount: " + tank.getFluid().amount));
        }
    }

    public void updateBlock() {

        if (worldObj.isRemote)
            return;
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }
}
