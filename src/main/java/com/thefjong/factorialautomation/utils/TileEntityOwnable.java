package com.thefjong.factorialautomation.utils;

import uk.co.qmunity.lib.tileentity.TileBase;
import net.minecraft.nbt.NBTTagCompound;

import com.thefjong.factorialautomation.handlers.KnowledgeHandler;
/**
 *  
 * @author TheFjong
 * 
 */
public class TileEntityOwnable extends TileBase {

    public KnowledgeHandler playerOwner = null;
    public String playerOwnerName;

    public void setPlayerOwner(KnowledgeHandler owner) {

        playerOwnerName = owner.getPlayerProfile().playerName;
        playerOwner = owner;
    }

    public KnowledgeHandler getPlayerOwner() {

        return playerOwner;
    }

    @Override
    public void readFromNBT(NBTTagCompound tCompound) {

        playerOwnerName = tCompound.getString("playerOwnerName");
        super.readFromNBT(tCompound);
    }

    @Override
    public void writeToNBT(NBTTagCompound tCompound) {

        tCompound.setString(playerOwnerName, "playerOwnerName");
        super.writeToNBT(tCompound);
    }

    @Override
    protected void onTileLoaded() {

        playerOwner = new KnowledgeHandler(playerOwnerName);
        super.onTileLoaded();
    }

}
