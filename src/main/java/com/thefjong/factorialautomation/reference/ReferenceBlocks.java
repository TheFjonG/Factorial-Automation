package com.thefjong.factorialautomation.reference;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * 
 * @author The Fjong
 *
 */
public class ReferenceBlocks {
	
	public static final String RESEARCH_LAB_NAME = "researchlab";
	public static final String CONVEYOR_BELT_NAME = "conveyorbelt";
	
	
	public static class ConveyorBelt extends ReferenceBlocks{
		public static final int SOUTH_METADATA_INTEGER = ForgeDirection.SOUTH.ordinal();
		public static final int NORTH_METADATA_INTEGER = ForgeDirection.NORTH.ordinal();
		public static final int EAST_METADATA_INTEGER = ForgeDirection.EAST.ordinal();
		public static final int WEST_METADATA_INTEGER = ForgeDirection.WEST.ordinal();
		
		/** 
		 * 	 |
		 *---|---
		 * 	 |
		 */
		
		//LEFT
		public static final int LEFT_TO_DOWN_METADATA_INTEGER = 6;
		public static final int LEFT_TO_UP_METADATA_INTEGER = 7;
		//RIGHT
		public static final int RIGHT_TO_DOWN_METADATA_INTEGER = 8;
		public static final int RIGHT_TO_UP_METADATA_INTEGER = 9;
		//DOWN
		public static final int DOWN_TO_RIGHT_METADATA_INTEGER = 10;
		public static final int DOWN_TO_LEFT_METADATA_INTEGER = 11;
		//Up
		public static final int UP_TO_RIGHT_METADATA_INTEGER = 12;
		public static final int UP_TO_LEFT_METADATA_INTEGER = 13;
		
	}
}
