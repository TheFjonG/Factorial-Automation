package com.thefjong.factorialautomation.blocks;

import com.thefjong.factorialautomation.blocks.machines.BlockBoiler;
import com.thefjong.factorialautomation.blocks.machines.BlockEnergyPole;
import com.thefjong.factorialautomation.blocks.machines.BlockPipe;
import com.thefjong.factorialautomation.blocks.machines.BlockPump;
import com.thefjong.factorialautomation.blocks.machines.BlockResearchLab;
import com.thefjong.factorialautomation.blocks.machines.BlockSteamEngine;
import com.thefjong.factorialautomation.reference.ReferenceBlocks;
import com.thefjong.factorialautomation.tileentities.machines.TileBoiler;
import com.thefjong.factorialautomation.tileentities.machines.TileConveyorBelt;
import com.thefjong.factorialautomation.tileentities.machines.TileEnergyPole;
import com.thefjong.factorialautomation.tileentities.machines.TilePipe;
import com.thefjong.factorialautomation.tileentities.machines.TilePump;
import com.thefjong.factorialautomation.tileentities.machines.TileResearchLab;
import com.thefjong.factorialautomation.tileentities.machines.TileSteamEngine;

import net.minecraft.block.Block;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * 
 * @author TheFjong, MineMaarten, Amadornes, Dmillerw
 *
 */
public class ModBlocks {

    public static Block ResearchTable;
    public static Block conveyorBelt;

    // Electricity
    public static Block pump;
    public static Block pipe;
    public static Block boiler;
    public static Block steamEngine;
    public static Block energyPole;

    // Fluids
    public static Fluid fluidSteam;

    public static void init() {

        ResearchTable = new BlockResearchLab(ReferenceBlocks.RESEARCH_LAB_NAME, TileResearchLab.class);
        conveyorBelt = new BlockConveyorBelt(ReferenceBlocks.CONVEYOR_BELT_NAME, TileConveyorBelt.class);

        // Electricity
        pump = new BlockPump(ReferenceBlocks.PUMP_NAME, TilePump.class);
        pipe = new BlockPipe(ReferenceBlocks.PIPE_NAME, TilePipe.class);
        boiler = new BlockBoiler(ReferenceBlocks.BOILER_NAME, TileBoiler.class);
        steamEngine = new BlockSteamEngine(ReferenceBlocks.STEAM_ENGINE_NAME, TileSteamEngine.class);
        energyPole = new BlockEnergyPole(ReferenceBlocks.ENERGY_POLE_NAME, TileEnergyPole.class);

        // Fluids
        fluidSteam = new Fluid(ReferenceBlocks.STEAM_FLUID_NAME);
    }

    public static void registerBlocks() {

        FluidRegistry.registerFluid(fluidSteam);

    }
}
