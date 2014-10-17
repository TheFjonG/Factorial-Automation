package com.thefjong.factorialautomation.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.thefjong.factorialautomation.reference.Reference;
import com.thefjong.factorialautomation.utils.CustomTabs;

import cpw.mods.fml.common.registry.GameRegistry;

/**
 *
 * @author The Fjong
 *
 */
public class ItemBase extends Item {

    protected final String name;
    private boolean hasEffect = false;

    public ItemBase(String name) {

        this.name = name;
        setCreativeTab(CustomTabs.forItems);
        GameRegistry.registerItem(this, name);
        setUnlocalizedName(Reference.MODID + "." + name);
        setTextureName(Reference.MODID + ":" + name);
    }

    public void setHasEffect() {

        hasEffect = true;
    }

    @Override
    public boolean hasEffect(ItemStack p_77636_1_) {

        return hasEffect;
    }

}
