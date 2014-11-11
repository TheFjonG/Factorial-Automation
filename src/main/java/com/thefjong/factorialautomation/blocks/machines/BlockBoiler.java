package com.thefjong.factorialautomation.blocks.machines;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.qmunity.lib.tileentity.TileBase;
import com.thefjong.factorialautomation.blocks.BlockContainerBase;
import com.thefjong.factorialautomation.tileentities.machines.TileBoiler;
import com.thefjong.factorialautomation.utils.GuiIDs;
/**
 * 
 * @author TheFjong
 *
 */
public class BlockBoiler extends BlockContainerBase {

    public BlockBoiler(String name, Class<? extends TileBase> tileClass) {

        super(Material.iron, name, tileClass);
        setGuiId(GuiIDs.BOILER);
    }
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
        ((TileBoiler)world.getTileEntity(x, y, z)).sendMessage(player);
        world.markBlockForUpdate(x, y, z);
        return super.onBlockActivated(world, x, y, z, player, par6, par7, par8, par9);
    }
}
