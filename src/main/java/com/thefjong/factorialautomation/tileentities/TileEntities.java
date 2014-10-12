package com.thefjong.factorialautomation.tileentities;

import com.thefjong.factorialautomation.tileentities.machines.TileConveyorBelt;
import com.thefjong.factorialautomation.tileentities.machines.TileResearchLab;

import cpw.mods.fml.common.registry.GameRegistry;


/**
 * 
 * @author The Fjong
 *
 */
public class TileEntities {
	
	public static void registerTiles(){
		GameRegistry.registerTileEntity(TileResearchLab.class, "researchlab");
		GameRegistry.registerTileEntity(TileConveyorBelt.class, "conveyorbelt");
	}
}
