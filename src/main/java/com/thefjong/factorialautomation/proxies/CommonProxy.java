package com.thefjong.factorialautomation.proxies;

import net.minecraftforge.common.MinecraftForge;

import com.thefjong.factorialautomation.handlers.ModEventHandler;

import cpw.mods.fml.common.FMLCommonHandler;

/**
 * 
 * @author The Fjong
 *
 */
public class CommonProxy{

    public void preInit(){

    }

    public void registerEventHandlers(){
        ModEventHandler eventHook = new ModEventHandler();
        FMLCommonHandler.instance().bus().register(eventHook);
        MinecraftForge.EVENT_BUS.register(eventHook);
    }

    public void registerTileEntities(){
        // TODO Auto-generated method stub

    }
}
