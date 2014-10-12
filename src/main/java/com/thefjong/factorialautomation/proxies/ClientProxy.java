package com.thefjong.factorialautomation.proxies;

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
	
}
