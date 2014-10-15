package com.thefjong.factorialautomation.proxies;

import com.thefjong.factorialautomation.client.render.RenderConveyorBelt;

import cpw.mods.fml.client.registry.RenderingRegistry;

/**
 * 
 * @author The Fjong
 *
 */
public class ClientProxy extends CommonProxy{
	@Override
	public void registerRenders(){
		
	}
	@Override
	public void preInit(){
	    RenderingRegistry.registerBlockHandler(new RenderConveyorBelt());
	}
}
