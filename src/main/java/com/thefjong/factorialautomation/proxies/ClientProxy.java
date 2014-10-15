package com.thefjong.factorialautomation.proxies;

import com.thefjong.factorialautomation.client.render.RenderConveyorBelt;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.model.ModelBiped;

/**
 * 
 * @author The Fjong
 *
 */
public class ClientProxy extends CommonProxy{
	
	@Override
	public ModelBiped getModelBiped(String Type){
		registerRenders();
		
		
		return null;
	}
	@Override
	public void registerRenders(){
		
	}
	@Override
	public void preInit(){
	    RenderingRegistry.registerBlockHandler(new RenderConveyorBelt());
	}
}
