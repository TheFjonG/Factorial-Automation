package com.thefjong.factorialautomation.blocks.machines;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.qmunity.lib.tileentity.TileBase;
import com.thefjong.factorialautomation.blocks.BlockContainerBase;
import com.thefjong.factorialautomation.tileentities.machines.TileEnergyPole;
import com.thefjong.factorialautomation.utils.GuiIDs;
/**
 * 
 * @author TheFjong
 *
 */
public class BlockEnergyPole extends BlockContainerBase {

    public BlockEnergyPole(String name, Class<? extends TileBase> tileClass) {

        super(Material.iron, name, tileClass);
        
    }
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
        ((TileEnergyPole)world.getTileEntity(x, y, z)).sendMessageToPlayer(player);
        return super.onBlockActivated(world, x, y, z, player, par6, par7, par8, par9);
    }
    
    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        ((TileEnergyPole)world.getTileEntity(x, y, z)).reloadTiles();
        super.onBlockAdded(world, x, y, z);
    }
    
}
