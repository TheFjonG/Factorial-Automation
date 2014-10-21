package com.thefjong.factorialautomation.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

import com.thefjong.factorialautomation.reference.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
/**
 * 
 * @author Amadornes
 *
 */
public class ItemBaseMeta extends ItemBase {

    @SideOnly(Side.CLIENT)
    private IIcon[] itemIcons;
    private int amount = 0;

    public ItemBaseMeta(String name, int amount) {

        super(name);
        setHasSubtypes(true);

        this.amount = amount;

        itemIcons = new IIcon[amount];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int damage) {

        return itemIcons[MathHelper.clamp_int(damage, 0, amount - 1)];
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

        for (int i = 0; i < amount; i++)
            itemIcons[i] = register.registerIcon(Reference.MODID + ":" + name + "_" + i);
    }

}
