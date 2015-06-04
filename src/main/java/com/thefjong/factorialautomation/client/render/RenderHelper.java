package com.thefjong.factorialautomation.client.render;

import org.lwjgl.opengl.GL11;

import uk.co.qmunity.lib.vec.Vec3dCube;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderHelper extends uk.co.qmunity.lib.client.render.RenderHelper{
    
    /**
     * Copied from the RenderHelper of Qlib
     * @author K-4U 
     */
    public static final void drawVertexWithTexture(double x, double y, double z, float tx, float ty) {

        GL11.glTexCoord2f(tx, ty);
        GL11.glVertex3d(x, y, z);
    }
    
    
    /**
     * Copied from the RenderHelper of Qlib
     * @author Amadornes
     */
    public static void drawTexturedCube(Vec3dCube cube, float minU, float minV, float maxU, float maxV) {

        // Top side:
        GL11.glNormal3d(0, 1, 0);
        drawVertexWithTexture(cube.getMinX(), cube.getMaxY(), cube.getMaxZ(), minU, minV);
        drawVertexWithTexture(cube.getMaxX(), cube.getMaxY(), cube.getMaxZ(), maxU, minV);
        drawVertexWithTexture(cube.getMaxX(), cube.getMaxY(), cube.getMinZ(), maxU, maxV);
        drawVertexWithTexture(cube.getMinX(), cube.getMaxY(), cube.getMinZ(), minU, maxV);

        // Bottom side:
        GL11.glNormal3d(0, -1, 0);
        drawVertexWithTexture(cube.getMaxX(), cube.getMinY(), cube.getMaxZ(), minU, minV);
        drawVertexWithTexture(cube.getMinX(), cube.getMinY(), cube.getMaxZ(), maxU, minV);
        drawVertexWithTexture(cube.getMinX(), cube.getMinY(), cube.getMinZ(), maxU, maxV);
        drawVertexWithTexture(cube.getMaxX(), cube.getMinY(), cube.getMinZ(), minU, maxV);

        // Draw west side:
        GL11.glNormal3d(-1, 0, 0);
        drawVertexWithTexture(cube.getMinX(), cube.getMinY(), cube.getMaxZ(), maxU, minV);
        drawVertexWithTexture(cube.getMinX(), cube.getMaxY(), cube.getMaxZ(), maxU, maxV);
        drawVertexWithTexture(cube.getMinX(), cube.getMaxY(), cube.getMinZ(), minU, maxV);
        drawVertexWithTexture(cube.getMinX(), cube.getMinY(), cube.getMinZ(), minU, minV);

        // Draw east side:
        GL11.glNormal3d(1, 0, 0);
        drawVertexWithTexture(cube.getMaxX(), cube.getMinY(), cube.getMinZ(), maxU, minV);
        drawVertexWithTexture(cube.getMaxX(), cube.getMaxY(), cube.getMinZ(), maxU, maxV);
        drawVertexWithTexture(cube.getMaxX(), cube.getMaxY(), cube.getMaxZ(), minU, maxV);
        drawVertexWithTexture(cube.getMaxX(), cube.getMinY(), cube.getMaxZ(), minU, minV);

        // Draw north side
        GL11.glNormal3d(0, 0, -1);
        drawVertexWithTexture(cube.getMinX(), cube.getMinY(), cube.getMinZ(), maxU, minV);
        drawVertexWithTexture(cube.getMinX(), cube.getMaxY(), cube.getMinZ(), maxU, maxV);
        drawVertexWithTexture(cube.getMaxX(), cube.getMaxY(), cube.getMinZ(), minU, maxV);
        drawVertexWithTexture(cube.getMaxX(), cube.getMinY(), cube.getMinZ(), minU, minV);

        // Draw south side
        GL11.glNormal3d(0, 0, 1);
        drawVertexWithTexture(cube.getMinX(), cube.getMinY(), cube.getMaxZ(), minU, minV);
        drawVertexWithTexture(cube.getMaxX(), cube.getMinY(), cube.getMaxZ(), maxU, minV);
        drawVertexWithTexture(cube.getMaxX(), cube.getMaxY(), cube.getMaxZ(), maxU, maxV);
        drawVertexWithTexture(cube.getMinX(), cube.getMaxY(), cube.getMaxZ(), minU, maxV);
    }
}
