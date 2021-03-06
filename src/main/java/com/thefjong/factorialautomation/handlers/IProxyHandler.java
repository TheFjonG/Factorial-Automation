package com.thefjong.factorialautomation.handlers;
/**
 * 
 * @author TheFjong
 *
 */
public interface IProxyHandler {

    public abstract void registerRenders();

    public abstract void registerKeybindings();

    public abstract void registerEventHandlers();

    public abstract void registerTileEntities();

    public void preInit();
}
