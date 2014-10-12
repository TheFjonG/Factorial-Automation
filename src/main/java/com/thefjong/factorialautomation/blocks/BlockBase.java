package com.thefjong.factorialautomation.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import com.thefjong.factorialautomation.reference.Reference;
import com.thefjong.factorialautomation.utils.CustomTabs;

import cpw.mods.fml.common.registry.GameRegistry;
/**
 * 
 * @author The Fjong
 *
 */
public class BlockBase extends Block{

	public BlockBase(Material material, String name) {
		super(material);
		setCreativeTab(CustomTabs.forBlocks);
		GameRegistry.registerBlock(this,name);
		setBlockName(name);
		setBlockTextureName(Reference.MODID + ":" + name);
	}

}
