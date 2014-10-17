package com.thefjong.factorialautomation.utils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.thefjong.factorialautomation.items.Items;

/**
 *
 * @author The Fjong
 *
 */
public class CustomTabs {

    public static CreativeTabs forBlocks = new CreativeTabs(CreativeTabs.getNextID(), "sciencecraft.blocks") {

        @Override
        public Item getTabIconItem() {

            return Items.sciencepack;
        }

    };

    public static CreativeTabs forItems = new CreativeTabs(CreativeTabs.getNextID(), "sciencecraft.items") {

        @Override
        public ItemStack getIconItemStack() {

            return new ItemStack(Items.upgradeproductivity, 1, 2);
        }

        @Override
        public Item getTabIconItem() {

            return null;
        };

    };

}
