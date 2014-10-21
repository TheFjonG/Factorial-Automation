package com.thefjong.factorialautomation.handlers;

import com.thefjong.factorialautomation.client.guis.GuiResearchLab;
import com.thefjong.factorialautomation.containers.ContainerResearchLab;
import com.thefjong.factorialautomation.tileentities.machines.TileResearchLab;
import com.thefjong.factorialautomation.utils.GuiIDs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

/**
 * 
 * @author TheFjong
 *
 */
public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        TileEntity tile = world.getTileEntity(x, y, z);

        switch (GuiIDs.values()[ID]) {
        case RESEARCH_TABLE: {
            return new ContainerResearchLab(player.inventory, (TileResearchLab) tile);
        }

        default:
            break;
        }
        return null;
    }

    @SuppressWarnings("unused")
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        TileEntity tile = world.getTileEntity(x, y, z);

        switch (GuiIDs.values()[ID]) {
        case RESEARCH_TABLE: {
            return new GuiResearchLab(player.inventory, (TileResearchLab) tile);

        }

        default:
            break;
        }
        return null;
    }

}
