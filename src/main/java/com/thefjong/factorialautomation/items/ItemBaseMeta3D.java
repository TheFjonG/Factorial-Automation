package com.thefjong.factorialautomation.items;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

import com.thefjong.factorialautomation.reference.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBaseMeta3D extends ItemBase3D {

    @SideOnly(Side.CLIENT)
    private IIcon[] itemIcons;
    @SideOnly(Side.CLIENT)
    private BufferedImage[] depthMasks;
    @SideOnly(Side.CLIENT)
    private BufferedImage[] cloudMasks;
    private int amount = 0;

    public ItemBaseMeta3D(String name, int amount, boolean hasCloudMask) {

        super(name, hasCloudMask);
        setHasSubtypes(true);

        this.amount = amount;

        itemIcons = new IIcon[amount];
        depthMasks = new BufferedImage[amount];
        cloudMasks = new BufferedImage[amount];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int damage) {

        return itemIcons[MathHelper.clamp_int(damage, 0, amount - 1)];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BufferedImage getDepthMask(int damage) {

        return depthMasks[MathHelper.clamp_int(damage, 0, amount - 1)];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BufferedImage getCloudMask(int damage) {

        return cloudMasks[MathHelper.clamp_int(damage, 0, amount - 1)];
    }

    @SideOnly(Side.CLIENT)
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {

        for (int i = 0; i < amount; i++)
            list.add(new ItemStack(item, 1, i));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {

        return super.getUnlocalizedName() + "." + MathHelper.clamp_int(stack.getItemDamage(), 0, amount);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister register) {

        for (int i = 0; i < amount; i++) {
            itemIcons[i] = register.registerIcon(Reference.MODID + ":" + name + "_" + i);
            try {
                depthMasks[i] = ImageIO.read(getClass().getResourceAsStream(
                        "/assets/" + Reference.MODID + "/textures/items/" + name + "_" + i + "_depth.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (hasCloudMask) {
                try {
                    cloudMasks[i] = ImageIO.read(getClass().getResourceAsStream(
                            "/assets/" + Reference.MODID + "/textures/items/" + name + "_" + i + "_effect.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
