package com.thefjong.factorialautomation.items;

import java.util.List;

import com.thefjong.factorialautomation.reference.Reference;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSciencePack extends ItemBase {
	@SideOnly(Side.CLIENT)
	public IIcon[] itemIcons;
	
	public ItemSciencePack(String name) {
		super(name);
		setHasSubtypes(true);
		setMaxDamage(0);
		itemIcons = new IIcon[4];
		
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int damage) {
		int meta = MathHelper.clamp_int(damage, 0, 3);
		return itemIcons[meta];
	}
	
	@Override
	public boolean hasEffect(ItemStack p_77636_1_) {
		
		return true;
	}
	
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
		for(int i = 0; i < 4; i++){
			list.add(new ItemStack(item, 1, i));
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int meta = MathHelper.clamp_int(stack.getItemDamage(), 0, 3);
		return super.getUnlocalizedName() + "." + meta;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister register) {
		
		itemIcons[0] = register.registerIcon(Reference.MODID + ":" + "sciencepack" + "_0");
		itemIcons[1] = register.registerIcon(Reference.MODID + ":" + "sciencepack" + "_1");
		itemIcons[2] = register.registerIcon(Reference.MODID + ":" + "sciencepack" + "_2");		
		itemIcons[3] = register.registerIcon(Reference.MODID + ":" + "sciencepack" + "_3");
		
		
	}
	

}
