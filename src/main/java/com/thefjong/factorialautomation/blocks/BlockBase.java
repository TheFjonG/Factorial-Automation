package com.thefjong.factorialautomation.blocks;

import com.thefjong.factorialautomation.reference.Reference;
import com.thefjong.factorialautomation.utils.CustomGameRegistry;
import com.thefjong.factorialautomation.utils.CustomTabs;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
/**
 * 
 * @author The Fjong
 *
 */
public class BlockBase extends Block{

	public BlockBase(Material material, String name) {
		super(material);
		setCreativeTab(CustomTabs.forBlocks);
		CustomGameRegistry.registerBlock(this,name);
		setBlockName(name);
		setBlockTextureName(Reference.MODID + ":" + name);
	}

}
