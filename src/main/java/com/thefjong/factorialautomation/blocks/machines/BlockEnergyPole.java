package com.thefjong.factorialautomation.blocks.machines;

import net.minecraft.block.material.Material;

import com.qmunity.lib.tileentity.TileBase;
import com.thefjong.factorialautomation.blocks.BlockContainerBase;
/**
 * 
 * @author TheFjong
 *
 */
public class BlockEnergyPole extends BlockContainerBase {

    public BlockEnergyPole(String name, Class<? extends TileBase> tileClass) {

        super(Material.iron, name, tileClass);

    }

}
