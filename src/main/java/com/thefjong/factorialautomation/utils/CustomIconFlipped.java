package com.thefjong.factorialautomation.utils;

import net.minecraft.util.IIcon;

public class CustomIconFlipped implements IIcon {

    private final IIcon baseIcon;

    private int iconWidth = 0;
    private int iconHeight = 0;
    private float minU = 0;
    private float maxU = 0;
    private float minV = 0;
    private float maxV = 0;

    public CustomIconFlipped(IIcon icon) {

        this.baseIcon = icon;

        iconWidth = this.baseIcon.getIconWidth();
        iconHeight = this.baseIcon.getIconHeight();
        minU = this.baseIcon.getMinU();
        maxU = this.baseIcon.getMaxU();
        minV = this.baseIcon.getMinV();
        maxV = this.baseIcon.getMaxV();
    }

    public int getIconWidth() {

        return iconWidth;
    }

    public void setIconWidth(int width) {

        iconWidth = width;
    }

    public int getIconHeight() {

        return this.baseIcon.getIconHeight();
    }

    public void setIconHeight(int height) {

        iconHeight = height;
    }

    public float getMinU() {

        return minU;
    }

    public void setMinU(float minU) {

        this.minU = minU;
    }

    public float getMaxU() {

        return maxU;
    }

    public void setMaxU(float maxU) {

        this.maxU = maxU;
    }

    public float getInterpolatedU(double p_94214_1_) {

        float f = this.getMaxU() - this.getMinU();
        return this.getMinU() + f * ((float) p_94214_1_ / 16.0F);
    }

    public float getMinV() {

        return minV;
    }

    public void setMinV(float minV) {

        this.minV = minV;
    }

    public float getMaxV() {

        return maxV;
    }

    public void setMaxV(float maxV) {

        this.maxV = maxV;
    }

    public float getInterpolatedV(double p_94207_1_) {

        float f = this.getMaxV() - this.getMinV();
        return this.getMinV() + f * ((float) p_94207_1_ / 16.0F);
    }

    public String getIconName() {

        return this.baseIcon.getIconName();
    }

}
