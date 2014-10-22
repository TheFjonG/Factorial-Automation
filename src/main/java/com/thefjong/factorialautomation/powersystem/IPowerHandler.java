package com.thefjong.factorialautomation.powersystem;

import net.minecraft.world.World;
/**
 * 
 * @author TheFjong
 *
 */
public interface IPowerHandler {
    
    /**
     * @return Current Energy Stored
     */
    public int getEnergyAmount();
    
    /**
     * @return Max Capacity
     */
    public int getCapacity();
    
    /**
     * @return worldObj;
     */
    public World getWorldObj();
}
