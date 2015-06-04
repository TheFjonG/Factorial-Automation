package com.thefjong.factorialautomation.powersystem;

import net.minecraft.tileentity.TileEntity;

/**
 * 
 * @author TheFjong
 *
 */
public interface IPowerHandler {

    /**
     * @return Current Energy Stored
     */
    public int getEnergyStored();

    /**
     * @return Capacity
     */
    public int getCapacity();

    /**
     * @return The TileEntity
     */
    public TileEntity getTile();
}
