package com.thefjong.factorialautomation.proxies;

import com.thefjong.factorialautomation.handlers.IProxyHandler;
import com.thefjong.factorialautomation.handlers.ModEventHandler;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.model.ModelBiped;
import net.minecraftforge.common.MinecraftForge;

/**
 * 
 * @author The Fjong
 *
 */
public class CommonProxy implements IProxyHandler{
	
	
	public ModelBiped getModelBiped(String Type){
		return null;
	}
	
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
