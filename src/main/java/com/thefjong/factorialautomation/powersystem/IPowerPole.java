package com.thefjong.factorialautomation.powersystem;

/**
 * 
 * @author TheFjong
 *
 */
public interface IPowerPole extends IPowerEmitter, IPowerAcceptor {

    public void setWritingToNBT(boolean bool);
}
