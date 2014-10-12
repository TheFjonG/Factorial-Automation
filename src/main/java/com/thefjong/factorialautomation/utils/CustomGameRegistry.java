package com.thefjong.factorialautomation.utils;

import java.util.ArrayList;
import java.util.List;

import com.thefjong.factorialautomation.reference.Reference;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.registry.GameRegistry;
/**
 * 
 * @author The Fjong
 *
 */
public class CustomGameRegistry {

	public static List<Block> RegisteredBlocks = new ArrayList<Block>();
	public static List<Item> RegisteredItems = new ArrayList<Item>();
	public static List<TileEntity> RegisteredTiles = new ArrayList<TileEntity>();
	
	public static void registerBlock(Block block, String name){
		
		RegisteredBlocks.add(block);
		GameRegistry.registerBlock(block,Reference.MODID + "." + name);
	}
	

	
	public static void registerBlock(Block block,  Class<? extends ItemBlock> itemBlock, String name){
		
		RegisteredBlocks.add(block);
		GameRegistry.registerBlock(block, itemBlock, Reference.MODID + "." + name);
	}
	

	public static void registerItem(Item item, String name){
		
		RegisteredItems.add(item);
		GameRegistry.registerItem(item, Reference.MODID + "." + name);
	}
	

	public static void registerTileEntity(TileEntity tileentity, Class<? extends TileEntity> tile, String name){
		
		RegisteredTiles.add(tileentity);
		GameRegistry.registerTileEntity(tile, name);
	}
	
}
