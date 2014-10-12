package com.thefjong.factorialautomation.blocks;

import com.thefjong.factorialautomation.blocks.machines.BlockResearchLab;
import com.thefjong.factorialautomation.reference.ReferenceBlocks;
import com.thefjong.factorialautomation.tileentities.machines.TileConveyorBelt;
import com.thefjong.factorialautomation.tileentities.machines.TileResearchLab;

import net.minecraft.block.Block;


/**
 * 
 * @author The Fjong
 *
 */
public class Blocks {
	
	public static Block ResearchTable;
	public static Block conveyorBelt;
	
	public static void init(){
		
		ResearchTable = new BlockResearchLab(ReferenceBlocks.RESEARCH_LAB_NAME, TileResearchLab.class);
		conveyorBelt = new BlockConveyorBelt(ReferenceBlocks.CONVEYOR_BELT_NAME,TileConveyorBelt.class);
	}	
	
	
	public static void registerBlocks(){
		
	
	}
}
