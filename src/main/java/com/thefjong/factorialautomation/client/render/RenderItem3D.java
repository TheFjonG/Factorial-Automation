package com.thefjong.factorialautomation.client.render;

import java.awt.image.BufferedImage;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import uk.co.qmunity.lib.render.RenderHelper;
import uk.co.qmunity.lib.vec.Vec3dCube;

import com.thefjong.factorialautomation.items.ItemBase3D;

public class RenderItem3D implements IItemRenderer {

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {

        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {

        return true;
    }

    private Map<Entry<Item, Integer>, Integer> lists = new HashMap<Entry<Item, Integer>, Integer>();

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... extraData) {

        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationItemsTexture);

        ItemBase3D i = (ItemBase3D) item.getItem();
        IIcon base = i.getIconFromDamage(item.getItemDamage());
        BufferedImage depth = i.getDepthMask(item.getItemDamage());
        double width = base.getIconWidth();
        double height = base.getIconHeight();

        float lX = OpenGlHelper.lastBrightnessX;
        float lY = OpenGlHelper.lastBrightnessY;

        int l = getListForItem(item);

        GL11.glPushMatrix();
        {
            if (l == -1) {
                l = GL11.glGenLists(2);
                GL11.glNewList(l, GL11.GL_COMPILE);
                {
                    if (depth != null) {
                        BufferedImage effect = i.getCloudMask(item.getItemDamage());
                        if (effect != null) {
                            IIcon icon = IconProvider.clouds_item;

                            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
                            GL11.glBegin(GL11.GL_QUADS);
                            for (int x = 0; x < width; x++) {
                                for (int y = 0; y < height; y++) {
                                    int drgb = depth.getRGB(x, y);
                                    // Extract red from the pixel depth
                                    int depthVal = (drgb & 0xFF);
                                    // Turn it into the actual item depth
                                    double d = (depthVal / 256D) / 16D;

                                    int rgb = effect.getRGB(x, y);
                                    int alpha = (rgb >> 24) & 0x000000FF;
                                    int red = (rgb >> 16) & 0x000000FF;
                                    int green = (rgb >> 8) & 0x000000FF;
                                    int blue = (rgb) & 0x000000FF;

                                    GL11.glColor4d(red / 128D, green / 128D, blue / 128D, alpha / 256D);

                                    // Draw cube
                                    RenderHelper.drawTexturedCube(new Vec3dCube(0.5 - d, y / height, x / width, 0.5 + d, (y + 1) / height,
                                            (x + 1) / width), icon.getInterpolatedU((16 * x) / width), icon.getInterpolatedV((16 * y)
                                            / height), icon.getInterpolatedU((16 * (x + 1)) / width), icon.getInterpolatedV((16 * (y + 1))
                                            / height));

                                    GL11.glColor4d(1, 1, 1, 1);
                                }
                            }
                        }
                        GL11.glEnd();
                        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lX, lY);
                    }
                }
                GL11.glEndList();
                GL11.glNewList(l + 1, GL11.GL_COMPILE);
                {
                    GL11.glBegin(GL11.GL_QUADS);
                    for (int x = 0; x < width; x++) {
                        for (int y = 0; y < height; y++) {
                            int drgb = depth.getRGB(x, y);
                            // Extract red from the pixel depth
                            int depthVal = (drgb & 0xFF);
                            // Turn it into the actual item depth
                            double d = (depthVal / 256D) / 16D;

                            // Draw cube
                            RenderHelper.drawTexturedCube(new Vec3dCube(0.5 - d, y / height, x / width, 0.5 + d, (y + 1) / height, (x + 1)
                                            / width), base.getInterpolatedU((16 * x) / width), base.getInterpolatedV((16 * y) / height),
                                    base.getInterpolatedU((16 * (x + 1)) / width), base.getInterpolatedV((16 * (y + 1)) / height));
                        }
                    }
                    GL11.glEnd();
                }
                GL11.glEndList();

                setListForItem(item, l);
            }
        }
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        {

            switch (type) {
                case ENTITY:
                    break;
                case EQUIPPED:
                    GL11.glTranslated(0, 0.5, 0);
                    break;
                case EQUIPPED_FIRST_PERSON:
                    GL11.glTranslated(-0.75, 1.25, 0.75);
                    GL11.glRotated(-30, 0, 1, 0);
                    GL11.glRotated(-30, 1, 0, 0);
                    break;
                case INVENTORY:
                    GL11.glRotated(-45, 0, 1, 0);
                    GL11.glRotated(15, 0, 0, 1);
                    GL11.glScaled(2, 2, 2);
                    break;
                default:
                    break;
            }

            GL11.glScaled(0.75, 0.75, 0.75);
            GL11.glTranslated(-0.5, -0.5, -0.5);

            if (type == ItemRenderType.INVENTORY) {
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            }

            GL11.glCallList(l);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lX + 10, lY + 10);
            GL11.glCallList(l + 1);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lX, lY);

            if (type == ItemRenderType.INVENTORY)
                GL11.glDisable(GL11.GL_BLEND);
        }
        GL11.glPopMatrix();

    }

    private int getListForItem(ItemStack item) {

        for (Entry<Item, Integer> i : lists.keySet())
            if (item.getItem() == i.getKey() && item.getItemDamage() == i.getValue())
                return lists.get(i);

        return -1;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void setListForItem(ItemStack item, int list) {

        lists.put(new AbstractMap.SimpleEntry(item.getItem(), item.getItemDamage()), list);
    }
}