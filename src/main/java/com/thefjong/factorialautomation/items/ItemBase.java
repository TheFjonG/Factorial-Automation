package com.thefjong.factorialautomation.items;

import net.minecraft.item.Item;

import com.thefjong.factorialautomation.reference.Reference;
import com.thefjong.factorialautomation.utils.CustomTabs;

import cpw.mods.fml.common.registry.GameRegistry;
/**
 * 
 * @author The Fjong
 *
 */
public class ItemBase extends Item{

	public ItemBase(String name){
		setCreativeTab(CustomTabs.forItems);
		GameRegistry.registerItem(this, name);
		setUnlocalizedName(Reference.MODID + "." + name);
		setTextureName(Reference.MODID + ":" + name);
	}
	
	
	
	
}
