package com.thefjong.factorialautomation.client.render;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;

import com.thefjong.factorialautomation.reference.Reference;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class IconProvider {

    public static IIcon clouds_item;
    public static IIcon clouds_block;

    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent event) {

        TextureMap reg = event.map;

        if (reg.getTextureType() == 0) {// Block
            clouds_block = reg.registerIcon(Reference.MODID + ":misc/clouds");
        } else {// Item
            clouds_item = reg.registerIcon(Reference.MODID + ":misc/clouds");
        }
    }

}
