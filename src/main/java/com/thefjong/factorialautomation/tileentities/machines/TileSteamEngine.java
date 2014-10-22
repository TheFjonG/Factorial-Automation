package com.thefjong.factorialautomation.tileentities.machines;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import com.qmunity.lib.tileentity.TileBase;
import com.thefjong.factorialautomation.blocks.ModBlocks;
import com.thefjong.factorialautomation.powersystem.IPowerEmitter;
import com.thefjong.factorialautomation.utils.ChatMessageUtil;

/**
 * 
 * @author TheFjong
 * 
 */
public class TileSteamEngine extends TileBase implements IFluidHandler, IPowerEmitter {

    @Override
    public void readFromNBT(NBTTagCompound tCompound) {
        energyAmount = tCompound.getInteger("energyAmount");
        tank = (SteamFluidTank) tank.readFromNBT(tCompound);
        super.readFromNBT(tCompound);
    }

    @Override
    public void writeToNBT(NBTTagCompound tCompound) {
        tCompound.setInteger("energyAmount", energyAmount);
        tank.writeToNBT(tCompound);
        super.writeToNBT(tCompound);
        markDirty();
        markForRenderUpdate();
    }

    /** Fluid pipe stuff */
    public class SteamFluidTank extends FluidTank {

        public SteamFluidTank() {
            super(new FluidStack(ModBlocks.fluidSteam, 0), 40000);

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

    public SteamFluidTank tank = new SteamFluidTank();

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {

        return tank.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {

        return tank.drain(tank.getFluidAmount(), doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {

        return tank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {

        if (fluid == ModBlocks.fluidSteam)
            return true;
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {

        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {

        return new FluidTankInfo[] { tank.getInfo() };
    }

    /**
     * Energy
     */
    public int energyAmount;

    @Override
    public int getEnergyAmount() {

        if (worldObj.isRemote)
            return 0;
        return energyAmount;
    }

    @Override
    public int getCapacity() {

        if (worldObj.isRemote)
            return 0;
        return 1000;
    }

    @Override
    public boolean canEmitEnergy(ForgeDirection direction) {

        return true;
    }

    @Override
    public int drainEnergy(ForgeDirection from, int maxDrain, boolean doDrain) {
        int drained = maxDrain;

        if (energyAmount <= 0) {
            energyAmount = 0;
            drained = 0;
        }

        if (energyAmount > drained) {

            drained = maxDrain;
        } else {

            drained = energyAmount;
        }

        if (doDrain) {
            energyAmount -= drained;
        }
        return drained;
    }

    @Override
    public boolean automatedEmit(ForgeDirection from) {

        return true;
    }

    @Override
    public void updateEntity() {
        if (tank.getFluidAmount() > 10 && energyAmount < getCapacity()) {
            energyAmount++;
            tank.drain(10, true);
        }
        super.updateEntity();
    }

    public void sendMessageToPlayer(EntityPlayer player) {
        if (!worldObj.isRemote) {
            ChatMessageUtil.sendChatMessageToPlayer(player, "Steam amount: " + tank.getFluidAmount());
            ChatMessageUtil.sendChatMessageToPlayer(player, "Energy: " + energyAmount);
        }
    }
}
