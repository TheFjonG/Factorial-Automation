package com.thefjong.factorialautomation.utils;

import net.minecraft.nbt.NBTTagCompound;

/**
 * 
 * @author The Fjong
 *
 */
public class PlayerKnowledge extends net.minecraft.world.WorldSavedData {

    public double knowledgeXP;
    public int knowledgeRank;
    public String playerName;

    public PlayerKnowledge(String playerName) {

        super(playerName);
        this.playerName = playerName;
    }

    @Override
    public void readFromNBT(NBTTagCompound tCompound) {

        knowledgeXP = tCompound.getDouble("knowledgeXP");
        knowledgeRank = tCompound.getInteger("knowledgeRank");
        playerName = tCompound.getString("playerName");
    }

    @Override
    public void writeToNBT(NBTTagCompound tCompound) {

        tCompound.setDouble("knowledgeXP", knowledgeXP);
        tCompound.setInteger("knowledgeRank", knowledgeRank);
        tCompound.setString("playerName", playerName);

    }

    public void resetAllStats() {

        knowledgeXP = 0;
        knowledgeRank = 0;
    }

    public double getKnowledgeXP() {

        return knowledgeXP;
    }

    public int getKnowledgeRank() {

        return knowledgeRank;
    }

    @Override
    public String toString() {

        return String.format("[Player Name] %s , [Player Knowledge XP] %s , [Player Knowledge Rank] %s", playerName, getKnowledgeXP(),
                getKnowledgeRank());
    }
}
