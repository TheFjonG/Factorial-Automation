package com.thefjong.factorialautomation.tileentities.machines;

import scala.xml.dtd.impl.WordBerrySethi;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import com.qmunity.lib.tileentity.TileBase;
import com.thefjong.factorialautomation.blocks.ModBlocks;
import com.thefjong.factorialautomation.reference.ReferenceBlocks;
import com.thefjong.factorialautomation.utils.ChatMessageUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 
 * @author TheFjong
 * 
 */
public class TileBoiler extends TileBase implements IFluidHandler, IInventory {
    
    public FluidBoiler waterTank = new FluidBoiler();
    public FluidBoiler steamTank = new FluidBoiler();
    private int heat = 0;
    public int boilerBurnTime;
    public int currentItemBurnTime;
    public int boilerHeatTime;
    public int maxBurnTime = 100;
    
    public class FluidBoiler extends FluidTank {

        public FluidBoiler() {
            super(null, 10000);

        }

        @Override
        public int fill(FluidStack resource, boolean doFill) {
            int fill = super.fill(resource, doFill);
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            return fill;
        }

        @Override
        public FluidStack drain(int maxDrain, boolean doDrain) {
            FluidStack stack = super.drain(maxDrain, doDrain);
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
            return stack;
        }

    }

    

    @Override
    public void readFromNBT(NBTTagCompound tCompound) {
        NBTTagCompound tag1 = (NBTTagCompound) tCompound.getTag("waterTank");
        NBTTagCompound tag2 = (NBTTagCompound) tCompound.getTag("steamTank");    
        waterTank = (FluidBoiler) waterTank.readFromNBT(tag1);
        steamTank = (FluidBoiler) steamTank.readFromNBT(tag2);
        heat = tCompound.getInteger("heat");
        boilerBurnTime = tCompound.getInteger("boilerBurnTime");
        boilerHeatTime = tCompound.getInteger("boilerHeatTime");
        currentItemBurnTime = TileEntityFurnace.getItemBurnTime(stack);
        stack = ItemStack.loadItemStackFromNBT(tCompound);
        super.readFromNBT(tCompound);
        markDirty();
    }

    @Override
    public void writeToNBT(NBTTagCompound tCompound) {
        NBTTagCompound tag1 = new NBTTagCompound();
        NBTTagCompound tag2 = new NBTTagCompound();
        waterTank.writeToNBT(tag1);
        steamTank.writeToNBT(tag2);
        tCompound.setTag("waterTank", tag1);
        tCompound.setTag("steamTank", tag2);
        tCompound.setInteger("heat", heat);
        tCompound.setInteger("boilerBurnTime", boilerBurnTime);
        tCompound.setInteger("boilerHeatTime", boilerHeatTime);
        if(stack != null)
            stack.writeToNBT(tCompound);
        super.writeToNBT(tCompound);
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (resource.getFluid() != FluidRegistry.WATER)
            return 0;
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

        return fluid == FluidRegistry.WATER ? true : false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {

        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        
        return new FluidTankInfo[] { waterTank.getInfo(), steamTank.getInfo() };
    }
    @SideOnly(Side.CLIENT)
    public int getWaterProgressScaled(int max){
        return this.waterTank.getFluidAmount() * max / waterTank.getCapacity();
    }
    @SideOnly(Side.CLIENT)
    public int getSteamProgressScaled(int max){
        return this.steamTank.getFluidAmount() * max / steamTank.getCapacity();
    }
    @Override
    public void updateEntity() {
        
        
            
            if (waterTank.getFluid() != null && steamTank.getFluidAmount() != steamTank.getCapacity() && heat >= 100) {

                int amount = steamTank.fill(new FluidStack(ModBlocks.fluidSteam, waterTank.getFluidAmount()), true);
                waterTank.drain(amount, true);
            }
            steamTank.setFluid(new FluidStack(ModBlocks.fluidSteam,10000));
        
        updateEntityBoiler();
        super.updateEntity();
    }

    public void sendMessage(EntityPlayer player) {
        if (!worldObj.isRemote) {
            ChatMessageUtil.sendChatMessageToPlayer(player, "");
            ChatMessageUtil.sendChatMessageToPlayer(player, "Heat Amount : " + heat);
            ChatMessageUtil.sendChatMessageToPlayer(player, "BurnTime : " + boilerBurnTime);
            ChatMessageUtil.sendChatMessageToPlayer(player, "Water Amount : " + waterTank.getFluidAmount());
            ChatMessageUtil.sendChatMessageToPlayer(player, "Steam Amount : " + steamTank.getFluidAmount());
            
        }
    }

    /** INVENTORY **/
    ItemStack stack;
    @Override
    public int getSizeInventory() {
        
        return 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
       
        return stack;
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        
        ItemStack itemStack = getStackInSlot(slot);
        if (itemStack != null) {
            if (itemStack.stackSize <= amount) {
                setInventorySlotContents(slot, null);
            } else {
                itemStack = itemStack.splitStack(amount);
                if (itemStack.stackSize == 0) {
                    setInventorySlotContents(slot, null);
                }
            }
        }
        
        return itemStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return getStackInSlot(slot);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        this.stack = stack;
    }

    @Override
    public String getInventoryName() {
       
        return "Inventory_" + ReferenceBlocks.BOILER_NAME;
    }

    @Override
    public boolean hasCustomInventoryName() {
       
        return true;
    }

    @Override
    public int getInventoryStackLimit() {
        
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        
        return true;
    }

    @Override
    public void openInventory() {
        
    }

    @Override
    public void closeInventory() {}
    
    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        
        return true;
    }
    
    int boilerHeatTicker;
    int boilerBurnTicker;
    int boilerCoolDownTicker;
    
    public void updateEntityBoiler(){

        if(stack != null){
            if(currentItemBurnTime > 0 && boilerBurnTime <= 0){
                decrStackSize(0, 1);
                boilerBurnTime += currentItemBurnTime;
            }
        }
        
        if(boilerBurnTime >0 && heat <=1000){
            
            boilerHeatTicker++;
            boilerBurnTicker++;
            
            if(boilerBurnTicker >=10){
                boilerBurnTime--;
                boilerBurnTicker = 0;
            }
            
            if(boilerHeatTicker >= 70){
                
                heat+=10;
                boilerHeatTicker = 0;
            }
        }
        if(stack == null && boilerBurnTime == 0 && heat >0){
            boilerCoolDownTicker++;
            if(boilerCoolDownTicker >=40){
                heat--;
                boilerCoolDownTicker = 0;
            }
        }
            
    }
    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt){
        readFromNBT(pkt.func_148857_g());
    }
}
