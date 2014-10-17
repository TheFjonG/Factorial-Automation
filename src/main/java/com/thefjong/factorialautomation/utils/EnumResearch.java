package com.thefjong.factorialautomation.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public enum EnumResearch {
    ;

    private EnumResearch[] researchNeeded;
    private ResourceLocation texture;
    private Block[] blocksDiscovered;
    private Item[] itemsDiscovered;

    private EnumResearch(EnumResearch[] researchNeeded, ResourceLocation texture, Block[] blocksDiscovered, Item[] itemsDiscovered) {

        this.researchNeeded = researchNeeded;
        this.texture = texture;
        this.blocksDiscovered = blocksDiscovered;
        this.itemsDiscovered = itemsDiscovered;
    }

    public EnumResearch[] getResearchNeeded() {

        return researchNeeded;
    }

    public ResourceLocation getTexture() {

        return texture;
    }

    public Block[] getBlocksDiscovered() {

        return blocksDiscovered;
    }

    public Item[] getItemsDiscoItems() {

        return itemsDiscovered;
    }

    @Override
    public String toString() {

        return String.format("[ResearchNeeded] %s , [texture] %s , [BlockDiscovered] %s , [ItemsDiscovered] %s", researchNeeded, texture,
                blocksDiscovered, itemsDiscovered);
    }
}