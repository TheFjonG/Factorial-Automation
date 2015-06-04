package com.thefjong.factorialautomation.powersystem;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * 
 * @author TheFjong
 *
 */
public interface IPowerReceiver extends IPowerHandler {

    /**
     * @param Direction
     * @return Whether the receiver can receive from the specified side
     */
    public boolean canReceiveEnergy(ForgeDirection direction);

    /**
     * @param Direction
     * @param MaxEnergy
     * @param Simulate
     * @return Energy Amount that the block has actually taken
     */
    public int receiveEnergy(ForgeDirection from, int maxEnergy, boolean simulate);
}
