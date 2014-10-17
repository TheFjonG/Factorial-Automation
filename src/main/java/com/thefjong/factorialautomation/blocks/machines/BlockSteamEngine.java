package com.thefjong.factorialautomation.blocks.machines;

import net.minecraft.block.material.Material;

import com.qmunity.lib.tileentity.TileBase;
import com.thefjong.factorialautomation.blocks.BlockContainerBase;

public class BlockSteamEngine extends BlockContainerBase {

    public BlockSteamEngine(String name, Class<? extends TileBase> tileClass) {

        super(Material.iron, name, tileClass);

    }

}
