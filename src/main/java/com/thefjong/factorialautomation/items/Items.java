package com.thefjong.factorialautomation.items;

import net.minecraft.item.Item;

import com.thefjong.factorialautomation.reference.ReferenceItems;

import cpw.mods.fml.common.registry.GameRegistry;

public class Items {

    public static Item brainfragment;
    public static Item brain;
    public static Item knowledgemeter;
    public static Item inventorshat;
    public static Item sciencepack;
    public static Item upgradespeed;
    public static Item upgradeeffectivity;
    public static Item upgradeproductivity;

    public static Item multipart;

    public static void init() {

        sciencepack = new ItemBaseMeta(ReferenceItems.SCIENCE_PACK_NAME, 4);

        upgradespeed = new ItemBaseMeta3D(ReferenceItems.UPGRADE_SPEED_NAME, 3, true);
        upgradeeffectivity = new ItemBaseMeta3D(ReferenceItems.UPGRADE_EFFECTIVITY_NAME, 3, true);
        upgradeproductivity = new ItemBaseMeta3D(ReferenceItems.UPGRADE_PRODUCTIVITY_NAME, 3, true);

        multipart = new ItemPart(ReferenceItems.MULTIPART_NAME);
        GameRegistry.registerItem(multipart, ReferenceItems.MULTIPART_NAME);
    }

    public static void registerInit() {

    }
}