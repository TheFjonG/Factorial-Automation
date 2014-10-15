package com.thefjong.factorialautomation;

import org.apache.logging.log4j.Logger;

import com.qmunity.lib.util.QLog;
import com.thefjong.factorialautomation.blocks.Blocks;
import com.thefjong.factorialautomation.handlers.GuiHandler;
import com.thefjong.factorialautomation.items.Items;
import com.thefjong.factorialautomation.proxies.CommonProxy;
import com.thefjong.factorialautomation.reference.Reference;
import com.thefjong.factorialautomation.tileentities.TileEntities;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

/**
 * 
 * @author The Fjong
 *
 */
@Mod(modid = Reference.MODID, version = Reference.VERSION, name = Reference.NAME)
public class FactorialAutomation{
    @Instance(Reference.MODID)
    public static FactorialAutomation instance;

    @SidedProxy(clientSide = Reference.PROXY_LOCATION + ".ClientProxy", serverSide = Reference.PROXY_LOCATION + ".CommonProxy")
    public static CommonProxy proxy;
    public static Logger log = QLog.logger;

    @EventHandler
    private void preinit(FMLPreInitializationEvent event){

        Blocks.init();
        Items.init();
        Items.registerInit();
        TileEntities.registerTiles();
        
        proxy.preInit();

    }

    @EventHandler
    public void init(FMLInitializationEvent event){
        proxy.registerEventHandlers();
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
    }

    @EventHandler
    private void postinit(FMLPostInitializationEvent event){

    }
}