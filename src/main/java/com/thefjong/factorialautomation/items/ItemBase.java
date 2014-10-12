package com.thefjong.factorialautomation.items;

import com.thefjong.factorialautomation.reference.Reference;
import com.thefjong.factorialautomation.utils.CustomGameRegistry;
import com.thefjong.factorialautomation.utils.CustomTabs;

import net.minecraft.item.Item;
/**
 * 
 * @author The Fjong
 *
 */
public class ItemBase extends Item{

	public ItemBase(String name){
		setCreativeTab(CustomTabs.forItems);
		CustomGameRegistry.registerItem(this, name);
		setUnlocalizedName(Reference.MODID + "." + name);
		setTextureName(Reference.MODID + ":" + name);
	}
	
	
	
	
}
