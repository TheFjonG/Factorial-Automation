package com.thefjong.factorialautomation.client.guis;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.thefjong.factorialautomation.containers.ContainerBoiler;
import com.thefjong.factorialautomation.reference.Reference;
import com.thefjong.factorialautomation.tileentities.machines.TileBoiler;
/**
 * 
 * @author TheFjong
 *
 */
public class GuiBoiler extends GuiContainer {

    private static final ResourceLocation textureLocation_0 = new ResourceLocation(Reference.MODID, "textures/gui/boiler_0.png");
    private static final ResourceLocation textureLocation_1 = new ResourceLocation(Reference.MODID, "textures/gui/boiler_1.png");
    private static final ResourceLocation textureLocation_2 = new ResourceLocation(Reference.MODID, "textures/gui/boiler_2.png");
    private final TileBoiler tileBoiler;

    public GuiBoiler(InventoryPlayer playerInventory, TileBoiler tile) {

        super(new ContainerBoiler(playerInventory, tile));
        this.tileBoiler = tile;
        this.ySize = 176;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        //Background
        mc.renderEngine.bindTexture(textureLocation_1);
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        // liquids
        if(tileBoiler.waterTank.getFluid() != null){
            mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
            IIcon waterIcon = tileBoiler.waterTank.getFluid().getFluid().getIcon();
            if(waterIcon != null)
                drawTexturedModelRectFromIcon(x+6, y+53-tileBoiler.getWaterProgressScaled(47), waterIcon, 134,tileBoiler.getWaterProgressScaled(47));
        }
        mc.renderEngine.bindTexture(textureLocation_1);
        drawTexturedModalRect(x+45, y, 45, 0, 95, 50);
        
        if(tileBoiler.steamTank.getFluid() != null){
            mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
            IIcon steamIcon = tileBoiler.steamTank.getFluid().getFluid().getIcon();
            if(steamIcon != null)
                drawTexturedModelRectFromIcon(x+155, y+24+31-tileBoiler.getSteamProgressScaled(31), steamIcon, 31,tileBoiler.getSteamProgressScaled(31));
        }
        
        //Forground
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(textureLocation_0);
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }
}
