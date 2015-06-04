package com.thefjong.factorialautomation.powersystem;

/**
 * 
 * @author TheFjong
 *
 */
public interface IPowerPole extends IPowerEmitter, IPowerReceiver {

    public void setWritingToNBT(boolean bool);
}
