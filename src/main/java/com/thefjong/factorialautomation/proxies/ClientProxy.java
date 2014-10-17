package com.thefjong.factorialautomation.proxies;

import net.minecraft.client.model.ModelBiped;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import com.thefjong.factorialautomation.client.render.IconProvider;
import com.thefjong.factorialautomation.client.render.RenderItem3D;
import com.thefjong.factorialautomation.items.ItemBase3D;

import cpw.mods.fml.common.FMLCommonHandler;

/**
 *
 * @author The Fjong
 *
 */
public class ClientProxy extends CommonProxy {

    @Override
    public ModelBiped getModelBiped(String Type) {

        registerRenders();

        return null;
    }

    @Override
    public void registerRenders() {

        RenderItem3D renderer = new RenderItem3D();
        for (ItemBase3D item : ItemBase3D.getRegistered3DItems())
            MinecraftForgeClient.registerItemRenderer(item, renderer);
    }

    @Override
    public void registerEventHandlers() {

        super.registerEventHandlers();

        IconProvider iconProvider = new IconProvider();
        FMLCommonHandler.instance().bus().register(iconProvider);
        MinecraftForge.EVENT_BUS.register(iconProvider);
    }

}
