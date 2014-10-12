package com.thefjong.factorialautomation.blocks.machines;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.thefjong.factorialautomation.blocks.BlockContainerBase;
import com.thefjong.factorialautomation.handlers.KnowledgeHandler;
import com.thefjong.factorialautomation.tileentities.machines.TileResearchLab;
import com.thefjong.factorialautomation.utils.GuiIDs;

public class BlockResearchLab extends BlockContainerBase{
	
	@SuppressWarnings("rawtypes")
	public BlockResearchLab(String name, Class TileEntity) {
		super(Material.iron, name, TileEntity);
		
	}

	@Override
	public GuiIDs getGuiID() {
		
		return GuiIDs.RESEARCH_TABLE;
	}
	
	
	@Override
	public void onBlockPlacedBy(World world, int x,int y, int z, EntityLivingBase living,ItemStack stack) {
		TileResearchLab tile = (TileResearchLab) world.getTileEntity(x, y, z);
		if(tile != null && tile instanceof TileResearchLab){
			if(living instanceof EntityPlayer ){				
				EntityPlayer player = (EntityPlayer) living;
				tile.setPlayerOwner(new KnowledgeHandler(player.getDisplayName()));
			}else{
				tile.setPlayerOwner(new KnowledgeHandler("null"));
			}
		}
		super.onBlockPlacedBy(world, x, y, z,living, stack);
	}
	
	
}
