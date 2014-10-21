package com.thefjong.factorialautomation.client.guis;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.thefjong.factorialautomation.containers.ContainerResearchLab;
import com.thefjong.factorialautomation.reference.Reference;
import com.thefjong.factorialautomation.tileentities.machines.TileResearchLab;
/**
 * 
 * @author TheFjong
 *
 */
public class GuiResearchLab extends GuiContainer {

    private static final ResourceLocation textureLocation = new ResourceLocation(Reference.MODID, "textures/gui/researchlab.png");
    @SuppressWarnings("unused")
    private final TileResearchLab tileResearchLab;

    public GuiResearchLab(InventoryPlayer playerInventory, TileResearchLab tile) {

        super(new ContainerResearchLab(playerInventory, tile));
        this.tileResearchLab = tile;
        this.ySize = 176;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(textureLocation);

        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;

        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

    }
}
