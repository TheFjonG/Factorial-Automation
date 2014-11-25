package com.thefjong.factorialautomation.part;

import uk.co.qmunity.lib.part.IPartFace;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
/**
 * 
 * @author Amadornes
 *
 */
public abstract class FAPartFace extends FAPart implements IPartFace {

    private ForgeDirection face = null;

    @Override
    public ForgeDirection getFace() {

        return face;
    }

    @Override
    public boolean canStay() {

        return getWorld().isSideSolid(getX() + getFace().offsetX, getY() + getFace().offsetY, getZ() + getFace().offsetZ, getFace().getOpposite());
    }

    public void setFace(ForgeDirection face) {

        this.face = face;
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {

        tag.setInteger("face", face.ordinal());
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {

        face = ForgeDirection.getOrientation(tag.getInteger("face"));
    }

    @Override
    public void writeUpdateToNBT(NBTTagCompound tag) {

        tag.setInteger("face", face.ordinal());
    }

    @Override
    public void readUpdateFromNBT(NBTTagCompound tag) {

        face = ForgeDirection.getOrientation(tag.getInteger("face"));
    }

}
