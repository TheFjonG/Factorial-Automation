package com.thefjong.factorialautomation.proxies;

import net.minecraftforge.common.MinecraftForge;

import com.thefjong.factorialautomation.handlers.IProxyHandler;
import com.thefjong.factorialautomation.handlers.ModEventHandler;

import cpw.mods.fml.common.FMLCommonHandler;

/**
 * 
 * @author The Fjong
 *
 */
public class CommonProxy implements IProxyHandler{
	
	
	public void registerRenders(){
		
	}

	@Override
	public void registerKeybindings() {
	
		
	}

	@Override
	public void registerEventHandlers() {
		ModEventHandler eventHook = new ModEventHandler();
        FMLCommonHandler.instance().bus().register(eventHook);
        MinecraftForge.EVENT_BUS.register(eventHook);	
	}

	@Override
	public void registerTileEntities() {
		
		
	}

    @Override
    public void preInit() {
       
        
    }
}
