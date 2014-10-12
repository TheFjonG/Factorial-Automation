package com.thefjong.factorialautomation.utils;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
/**
 * 
 * @author The Fjong
 *
 */
public class CustomTabs{

	public static CreativeTabs forBlocks = new CreativeTabs(CreativeTabs.getNextID(),"sciencecraft.blocks") {
		
		@Override
		public Item getTabIconItem() {
			
			
			return Items.apple;
		}
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void displayAllReleventItems(List blocks) {
			
			if(CustomGameRegistry.RegisteredBlocks != null){
				
				for(int i = 0; i < CustomGameRegistry.RegisteredBlocks.size(); i++){
					
					blocks.add(new ItemStack(CustomGameRegistry.RegisteredBlocks.get(i)));
				}
			}
			
		};
	};

	public static CreativeTabs forItems = new CreativeTabs(CreativeTabs.getNextID(),"sciencecraft.items") {
		
		@Override
		public Item getTabIconItem() {
			
			return Items.apple;
		}
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void displayAllReleventItems(List items) {
			
			if(CustomGameRegistry.RegisteredItems != null){
				
				for(int i = 0; i < CustomGameRegistry.RegisteredItems.size(); i++){
					items.add(new ItemStack(CustomGameRegistry.RegisteredItems.get(i)));
				}
			}
		};
	};
	
	
}
