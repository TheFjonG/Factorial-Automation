package com.thefjong.factorialautomation.proxies;

import com.thefjong.factorialautomation.client.render.RenderConveyorBelt;
import com.thefjong.factorialautomation.client.render.RenderPipe;
import com.thefjong.factorialautomation.tileentities.machines.TileConveyorBelt;
import com.thefjong.factorialautomation.tileentities.machines.TilePipe;

import cpw.mods.fml.client.registry.ClientRegistry;
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
	    ClientRegistry.bindTileEntitySpecialRenderer(TileConveyorBelt.class, new RenderConveyorBelt());
	    RenderingRegistry.registerBlockHandler(new RenderPipe());
	    ClientRegistry.bindTileEntitySpecialRenderer(TilePipe.class, new RenderPipe());
	}
}
