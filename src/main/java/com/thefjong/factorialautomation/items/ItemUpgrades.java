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

public class ItemUpgrades extends ItemBase {
	@SideOnly(Side.CLIENT)
	public IIcon[] itemIcons;
	
	public ItemUpgrades(String name) {
		super(name);
		setHasSubtypes(true);
		setMaxDamage(0);
		itemIcons = new IIcon[9];
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int damage) {
		int meta = MathHelper.clamp_int(damage, 0, 8);
		return itemIcons[meta];
	}
	
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < 9; i++){
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int meta = MathHelper.clamp_int(stack.getItemDamage(), 0, 8);
		return super.getUnlocalizedName() + "." + meta;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister register) {
		
		itemIcons[0] = register.registerIcon(Reference.MODID + ":" + "speedupgrade" + "_0");
		itemIcons[1] = register.registerIcon(Reference.MODID + ":" + "speedupgrade" + "_1");
		itemIcons[2] = register.registerIcon(Reference.MODID + ":" + "speedupgrade" + "_2");
		
		itemIcons[3] = register.registerIcon(Reference.MODID + ":" + "effectivityupgrade" + "_0");
		itemIcons[4] = register.registerIcon(Reference.MODID + ":" + "effectivityupgrade" + "_1");	
		itemIcons[5] = register.registerIcon(Reference.MODID + ":" + "effectivityupgrade" + "_2");	
		
		itemIcons[6] = register.registerIcon(Reference.MODID + ":" + "productivityupgrade" + "_0");
		itemIcons[7] = register.registerIcon(Reference.MODID + ":" + "productivityupgrade" + "_1");
		itemIcons[8] = register.registerIcon(Reference.MODID + ":" + "productivityupgrade" + "_2");
		
	}
	
	
}
