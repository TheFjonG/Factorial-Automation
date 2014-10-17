package com.thefjong.factorialautomation.blocks.machines;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.qmunity.lib.tileentity.TileBase;
import com.thefjong.factorialautomation.blocks.BlockContainerBase;
import com.thefjong.factorialautomation.tileentities.machines.TilePump;

public class BlockPump extends BlockContainerBase {

    public BlockPump(String name, Class<? extends TileBase> tileClass) {

        super(Material.iron, name, tileClass);

    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {

        TilePump pump = (TilePump) world.getTileEntity(x, y, z);
        pump.sendMessageToPlayer(player);
        return super.onBlockActivated(world, x, y, z, player, par6, par7, par8, par9);
    }
}
