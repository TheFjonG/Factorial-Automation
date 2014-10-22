package com.thefjong.factorialautomation.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

import com.thefjong.factorialautomation.client.render.IconProvider;
import com.thefjong.factorialautomation.reference.ReferenceBlocks;

public class BlockSteam extends BlockFluidClassic {
    FluidSteam steam;
    
    public BlockSteam(Fluid fluid) {
        super(fluid, Material.snow);
        steam = (FluidSteam) fluid;
       setBlockName(ReferenceBlocks.BOILER_NAME);
    }
    @Override
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return IconProvider.clouds_block;
    }
    
    
}
