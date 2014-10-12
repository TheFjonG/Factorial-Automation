package com.thefjong.factorialautomation.handlers;

import com.thefjong.factorialautomation.utils.GuiIDs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
/**
 * 
 * @author The Fjong
 *
 */
public class GuiHandler implements IGuiHandler {

	@SuppressWarnings("unused")
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,int x, int y, int z) {
		TileEntity ent = world.getTileEntity(x, y, z);
		switch (GuiIDs.values()[ID]) {

	
			
		default:
			break;
		}
		return null;
	}

	@SuppressWarnings("unused")
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,int x, int y, int z) {
		TileEntity ent = world.getTileEntity(x, y, z);

		switch (GuiIDs.values()[ID]) {

		
			
		default:
			break;
		}
		return null;
	}

}
