package com.thefjong.factorialautomation.blocks;

import com.thefjong.factorialautomation.client.render.IconProvider;

import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;

public class FluidSteam extends Fluid{
    
    public IIcon icon = IconProvider.clouds_block;;
    
    public FluidSteam(String fluidName) {
        super(fluidName);
        setStillIcon((IIcon) icon);
        setFlowingIcon((IIcon) icon);
        setGaseous(true);
        setDensity(0);
        setTemperature(100);
        setBlock(ModBlocks.blockSteam);
        
    }
   
}
