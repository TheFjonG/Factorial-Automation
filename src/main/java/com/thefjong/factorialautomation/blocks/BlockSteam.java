package com.thefjong.factorialautomation.blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

import com.thefjong.factorialautomation.client.render.IconProvider;
import com.thefjong.factorialautomation.reference.ReferenceBlocks;

public class BlockSteam extends BlockFluidClassic {
    FluidSteam steam;
    
    public BlockSteam(Fluid fluid) {
        super(fluid, Material.water);
        steam = (FluidSteam) fluid;
       setBlockName(ReferenceBlocks.BOILER_NAME);
       
    }
    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return IconProvider.clouds_block;
    }
    int ticker = 0;
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        
       
        
        super.updateTick(world, x, y, z, rand);
    }
}
