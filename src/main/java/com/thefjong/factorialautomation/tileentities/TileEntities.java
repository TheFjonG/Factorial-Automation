package com.thefjong.factorialautomation.tileentities;

import com.thefjong.factorialautomation.reference.ReferenceBlocks;
import com.thefjong.factorialautomation.tileentities.machines.TileConveyorBelt;
import com.thefjong.factorialautomation.tileentities.machines.TileEnergyPole;
import com.thefjong.factorialautomation.tileentities.machines.TilePipe;
import com.thefjong.factorialautomation.tileentities.machines.TilePump;
import com.thefjong.factorialautomation.tileentities.machines.TileResearchLab;

import cpw.mods.fml.common.registry.GameRegistry;

/**
 * 
 * @author The Fjong
 *
 */
public class TileEntities {

    public static void registerTiles() {

        GameRegistry.registerTileEntity(TileResearchLab.class, ReferenceBlocks.RESEARCH_LAB_NAME);
        GameRegistry.registerTileEntity(TileConveyorBelt.class, ReferenceBlocks.CONVEYOR_BELT_NAME);

        GameRegistry.registerTileEntity(TilePump.class, ReferenceBlocks.PUMP_NAME);
        GameRegistry.registerTileEntity(TilePipe.class, ReferenceBlocks.PIPE_NAME);
        GameRegistry.registerTileEntity(TileEnergyPole.class, ReferenceBlocks.ENERGY_POLE_NAME);

    }
}
