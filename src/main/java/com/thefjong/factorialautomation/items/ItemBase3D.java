package com.thefjong.factorialautomation.items;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import net.minecraft.client.renderer.texture.IIconRegister;

import com.thefjong.factorialautomation.reference.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBase3D extends ItemBase {

    @SideOnly(Side.CLIENT)
    private BufferedImage depthMask;
    @SideOnly(Side.CLIENT)
    private BufferedImage cloudMask;

    protected final boolean hasCloudMask;

    public ItemBase3D(String name, boolean hasCloudMask) {

        super(name);
        this.hasCloudMask = hasCloudMask;

        items.add(this);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister reg) {

        itemIcon = reg.registerIcon(Reference.MODID + ":" + name);
        try {
            depthMask = ImageIO.read(getClass()
                    .getResourceAsStream("/assets/" + Reference.MODID + "/textures/items/" + name + "_depth.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (hasCloudMask) {
            try {
                cloudMask = ImageIO.read(getClass().getResourceAsStream(
                        "/assets/" + Reference.MODID + "/textures/items/" + name + "_effect.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public BufferedImage getDepthMask(int meta) {

        return depthMask;
    }

    @SideOnly(Side.CLIENT)
    public BufferedImage getCloudMask(int meta) {

        return cloudMask;
    }

    // Static

    private static final List<ItemBase3D> items = new ArrayList<ItemBase3D>();

    public static List<ItemBase3D> getRegistered3DItems() {

        return new ArrayList<ItemBase3D>(items);
    }

}
