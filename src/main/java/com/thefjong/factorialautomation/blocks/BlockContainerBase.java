package com.thefjong.factorialautomation.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;

import com.qmunity.lib.block.BlockTileBase;
import com.qmunity.lib.tileentity.TileBase;
import com.thefjong.factorialautomation.FactorialAutomation;
import com.thefjong.factorialautomation.reference.Reference;
import com.thefjong.factorialautomation.utils.CustomTabs;
import com.thefjong.factorialautomation.utils.GuiIDs;

import cpw.mods.fml.common.registry.GameRegistry;

/**
 * 
 * @author The Fjong
 *
 */
public class BlockContainerBase extends BlockTileBase implements ITileEntityProvider{

    public BlockContainerBase(Material material, String name, Class<? extends TileBase> tileClass){
        super(material, tileClass);
        setCreativeTab(CustomTabs.forBlocks);
        GameRegistry.registerBlock(this, name);
        setBlockName(name);
        setBlockTextureName(Reference.MODID + ":" + name);
    }

    public void setGuiId(GuiIDs guiId){
        setGuiId(guiId.ordinal());
    }

    @Override
    protected Object getModInstance(){
        return FactorialAutomation.instance;
    }

    @Override
    protected String getModId(){
        return Reference.MODID;
    }

}
