package com.thefjong.factorialautomation.utils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.thefjong.factorialautomation.items.Items;
/**
 * 
 * @author The Fjong
 *
 */
public class CustomTabs{

	public static CreativeTabs forBlocks = new CreativeTabs(CreativeTabs.getNextID(),"sciencecraft.blocks") {
		
		@Override
		public Item getTabIconItem() {
			
			
			return Items.SciencePack;
		}
		
	};

	public static CreativeTabs forItems = new CreativeTabs(CreativeTabs.getNextID(),"sciencecraft.items") {
		
		@Override
		public Item getTabIconItem() {
			return Items.Upgrades;
		}
		
	};
	
	
}
