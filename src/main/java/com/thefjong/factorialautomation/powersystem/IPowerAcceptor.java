package com.thefjong.factorialautomation.powersystem;

import net.minecraftforge.common.util.ForgeDirection;
/**
 * 
 * @author TheFjong
 *
 */
public interface IPowerAcceptor extends IPowerHandler{
    
    /**
     * @param direction
     * @return Whether the accepter accept or not
     */
    public boolean canAcceptEnergy(ForgeDirection direction);
    
    
    /**
     * @param from
     * @param maxFill
     * @param doDrain
     * @return Amount accepted
     */
    public int fillEnergy(ForgeDirection from, int maxFill, boolean doFill);
}
