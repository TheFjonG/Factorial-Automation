package com.thefjong.factorialautomation.powersystem;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * 
 * @author TheFjong
 *
 */
public interface IPowerEmitter extends IPowerHandler {

    /**
     * @param Direction
     * @return Whether the emitter can emit from specified side
     */
    public boolean canEmitEnergy(ForgeDirection direction);

    /**
     * @param from
     * @param maxDrain
     * @param doDrain
     * @return Amount drained
     */
    public int drainEnergy(ForgeDirection from, int maxDrain, boolean doDrain);

    /**
     * @param from
     * @return Whether the emitter emit automatically or not
     */
    public boolean automatedEmit(ForgeDirection from);
}
