package com.thefjong.factorialautomation.proxies;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import com.thefjong.factorialautomation.client.render.IconProvider;
import com.thefjong.factorialautomation.client.render.RenderConveyorBelt;
import com.thefjong.factorialautomation.client.render.RenderItem3D;
import com.thefjong.factorialautomation.client.render.RenderPipe;
import com.thefjong.factorialautomation.items.ItemBase3D;
import com.thefjong.factorialautomation.tileentities.machines.TileConveyorBelt;
import com.thefjong.factorialautomation.tileentities.machines.TilePipe;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenders() {

        RenderItem3D renderer = new RenderItem3D();
        for (ItemBase3D item : ItemBase3D.getRegistered3DItems())
            MinecraftForgeClient.registerItemRenderer(item, renderer);

        RenderingRegistry.registerBlockHandler(new RenderConveyorBelt());
        ClientRegistry.bindTileEntitySpecialRenderer(TileConveyorBelt.class, new RenderConveyorBelt());
        RenderingRegistry.registerBlockHandler(new RenderPipe());
        ClientRegistry.bindTileEntitySpecialRenderer(TilePipe.class, new RenderPipe());
    }

    @Override
    public void registerEventHandlers() {

        super.registerEventHandlers();

        IconProvider iconProvider = new IconProvider();
        FMLCommonHandler.instance().bus().register(iconProvider);
        MinecraftForge.EVENT_BUS.register(iconProvider);
    }
}
