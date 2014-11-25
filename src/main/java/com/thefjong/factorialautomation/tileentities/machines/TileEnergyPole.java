package com.thefjong.factorialautomation.tileentities.machines;

import java.util.ArrayList;
import java.util.List;

import uk.co.qmunity.lib.tileentity.TileBase;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import com.thefjong.factorialautomation.powersystem.IPowerAcceptor;
import com.thefjong.factorialautomation.powersystem.IPowerEmitter;
import com.thefjong.factorialautomation.powersystem.IPowerHandler;
import com.thefjong.factorialautomation.powersystem.IPowerPole;
import com.thefjong.factorialautomation.utils.ChatMessageUtil;

/**
 * 
 * @author TheFjong
 * 
 */
public class TileEnergyPole extends TileBase implements IPowerPole {

    public int energyAmount;
    public List<IPowerHandler> tiles = new ArrayList<IPowerHandler>();

    @Override
    public boolean canEmitEnergy(ForgeDirection direction) {

        return true;
    }

    @Override
    public int drainEnergy(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (worldObj.isRemote)
            return 0;
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
    public int getEnergyAmount() {

        return energyAmount;
    }

    @Override
    public int getCapacity() {

        return 200;
    }

    @Override
    public boolean canAcceptEnergy(ForgeDirection direction) {

        return false;
    }

    @Override
    public int fillEnergy(ForgeDirection from, int maxFill, boolean doFill) {
        if (worldObj.isRemote)
            return 0;

        int filled = maxFill;
        if (energyAmount < getCapacity()) {

            if (energyAmount + filled < getCapacity()) {
                filled = maxFill;
            } else {
                int freeSpace = getCapacity() - energyAmount;
                if (freeSpace <= maxFill) {
                    filled = freeSpace;

                } else {
                    filled = 0;
                }
            }
        } else {
            filled = 0;
        }

        if (doFill)
            energyAmount += filled;
        return filled;
    }

    public void reloadTiles() {
        tiles.clear();
        int radius = 16;
        for (int posX = 0; posX < radius + 1; posX++) {
            for (int posY = 0; posY < radius + 5; posY++) {
                for (int posZ = 0; posZ < radius + 1; posZ++) {
                    if (worldObj.getTileEntity(xCoord + posX - (radius / 2), yCoord + posY - (radius / 2), zCoord + posZ - (radius / 2)) instanceof IPowerHandler) {
                        IPowerHandler tile = (IPowerHandler) worldObj.getTileEntity(xCoord + posX - (radius / 2), yCoord + posY - (radius / 2),
                                zCoord + posZ - (radius / 2));
                        if (tile.getTile() != this) {

                            tiles.add(tile);
                            if (tile instanceof TileEnergyPole)
                                if (!((TileEnergyPole) tile).tiles.contains(this))
                                    ((TileEnergyPole) tile).onBlockNeighbourChanged();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tCompound) {
        energyAmount = tCompound.getInteger("energyAmount");

        for (int i = 0; i < tCompound.getInteger("tilesAmount"); i++) {
            NBTTagCompound tag = tCompound.getCompoundTag("tile" + i);
            TileEntity tile = new TileEntity();
            tile.readFromNBT(tag);
            if (tile != null && tile instanceof IPowerHandler) {
                tiles.add((IPowerHandler) tile);
            }
        }

        super.readFromNBT(tCompound);
        markDirty();
        markForRenderUpdate();
    }

    @Override
    public void writeToNBT(NBTTagCompound tCompound) {
        int added = 0;
        if (!writingToNBT) {

            for (int i = 0; i < tiles.size(); i++) {
                if (tiles.get(i) != null) {
                    TileEntity tile = (TileEntity) tiles.get(i);
                    NBTTagCompound tag = new NBTTagCompound();
                    if (tile instanceof TileEnergyPole)
                        ((TileEnergyPole) tile).setWritingToNBT(true);
                    tile.writeToNBT(tag);
                    if (tile instanceof TileEnergyPole)
                        ((TileEnergyPole) tile).setWritingToNBT(false);
                    tCompound.setTag("tile" + i, tag);
                    added++;
                }
            }
        }
        tCompound.setInteger("tilesAmount", added);
        tCompound.setInteger("energyAmount", energyAmount);

        super.writeToNBT(tCompound);
    }

    public void sendMessageToPlayer(EntityPlayer player) {
        if (!worldObj.isRemote) {
            ChatMessageUtil.sendChatMessageToPlayer(player, "");
            ChatMessageUtil.sendChatMessageToPlayer(player, "Energy: " + energyAmount);
            for (int i = 0; i < tiles.size(); i++) {
                if (tiles.get(i) != null) {
                    Block block = worldObj.getBlock(((TileEntity) tiles.get(i)).xCoord, ((TileEntity) tiles.get(i)).yCoord,
                            ((TileEntity) tiles.get(i)).zCoord);
                    ChatMessageUtil.sendChatMessageToPlayer(player, "Block: " + block.getUnlocalizedName());
                }
            }
        }
    }

    @Override
    public void onBlockNeighbourChanged() {

        reloadTiles();
        super.onBlockNeighbourChanged();
    }

    @Override
    public TileEntity getTile() {

        return this;
    }

    boolean writingToNBT = false;

    @Override
    public void setWritingToNBT(boolean bool) {

        writingToNBT = bool;
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            for (int i = 0; i < tiles.size(); i++) {
                IPowerHandler tile = tiles.get(i);
                if (tile instanceof TileEnergyPole) {
                    if (getEnergyAmount() > tile.getEnergyAmount() && tile.getEnergyAmount() < tile.getCapacity()) {
                        int amount = (getEnergyAmount() - tile.getEnergyAmount()) / 2;
                        amount = ((TileEnergyPole) tile).fillEnergy(ForgeDirection.UNKNOWN, amount, true);
                        drainEnergy(ForgeDirection.UNKNOWN, amount, true);
                    }
                    continue;
                }
                if (tile instanceof IPowerEmitter) {
                    if (getEnergyAmount() < getCapacity() && tile.getEnergyAmount() > 0) {
                        int amount = fillEnergy(ForgeDirection.UNKNOWN, tile.getEnergyAmount(), true);
                        System.out.println(amount);
                        if (((IPowerEmitter) tile).canEmitEnergy(ForgeDirection.UNKNOWN)) {
                            ((IPowerEmitter) tile).drainEnergy(ForgeDirection.UNKNOWN, amount, true);
                        }
                    }
                    continue;
                }
                if (tile instanceof IPowerAcceptor) {
                    if (tile.getEnergyAmount() < tile.getCapacity() && getEnergyAmount() > 0) {
                        int amount = ((IPowerAcceptor) tile).fillEnergy(ForgeDirection.UNKNOWN, getEnergyAmount(), true);
                        drainEnergy(ForgeDirection.UNKNOWN, amount, true);
                    }
                    continue;
                }

            }
        }
        super.updateEntity();
    }
}
