package com.thefjong.factorialautomation.blocks;

import com.thefjong.factorialautomation.client.render.IconProvider;

import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class FluidSteam extends Fluid{
    
    public IIcon icon = IconProvider.clouds_block;
    
    public FluidSteam(String fluidName) {
        super(fluidName);
        setStillIcon((IIcon) icon);
        setFlowingIcon((IIcon) icon);
        setGaseous(true);
        setDensity(-1000);
        setViscosity(500);
        setTemperature(100);
        setBlock(ModBlocks.blockSteam);
    }
   
    @Override
    public IIcon getIcon() {
        
        return this.getBlock().getIcon(0, 0);
    }
    
    @Override
    public IIcon getStillIcon() {
        
        return this.getBlock().getIcon(0, 0);
    }
    @Override
    public IIcon getFlowingIcon() {
       
        return this.getBlock().getIcon(0, 0);
    }
    
    @Override
    public IIcon getIcon(FluidStack stack) {
       
        return this.getBlock().getIcon(0, 0);
    }
    @Override
    public IIcon getIcon(World world, int x, int y, int z) {
        
        return this.getBlock().getIcon(0, 0);
    }
}
