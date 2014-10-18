package com.thefjong.factorialautomation;

import com.thefjong.factorialautomation.blocks.ModBlocks;
import org.apache.logging.log4j.Logger;

import com.thefjong.factorialautomation.handlers.GuiHandler;
import com.thefjong.factorialautomation.handlers.IProxyHandler;
import com.thefjong.factorialautomation.items.Items;
import com.thefjong.factorialautomation.part.PartManager;
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
public class FactorialAutomation {

    @Instance(Reference.MODID)
    public static FactorialAutomation instance;

    @SidedProxy(clientSide = Reference.PROXY_LOCATION + ".ClientProxy", serverSide = Reference.PROXY_LOCATION + ".CommonProxy")
    public static IProxyHandler proxy;
    public static Logger log;

    @EventHandler
    private void preInit(FMLPreInitializationEvent event) {

        ModBlocks.init();
        Items.init();
        Items.registerInit();
        TileEntities.registerTiles();

        proxy.preInit();

        log = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        proxy.registerEventHandlers();
        proxy.registerRenders();
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        PartManager.registerParts();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
