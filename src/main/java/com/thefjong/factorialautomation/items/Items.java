package com.thefjong.factorialautomation.items;

import com.thefjong.factorialautomation.reference.ReferenceItems;

import net.minecraft.item.Item;

public class Items{
	
	public static Item BrainFragment;
	public static Item Brain;
	public static Item KnowledgeMeter;
	public static Item InventorsHat;
	public static Item SciencePack;
	public static Item Upgrades;
	
	public static void init(){
		
		SciencePack = new ItemSciencePack(ReferenceItems.SCIENCE_PACK_NAME);
		Upgrades = new ItemUpgrades(ReferenceItems.UPGRADES_NAME);
	}
	
	public static void registerInit(){
		
	}
}