package com.thefjong.factorialautomation.handlers;

import com.thefjong.factorialautomation.utils.PlayerKnowledge;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

/**
 * 
 * @author The Fjong
 *
 */
public class KnowledgeHandler {

    public String playerName;
    public PlayerKnowledge playerKnowledge;

    public KnowledgeHandler(String playerName) {

        if (MinecraftServer.getServer() == null)
            return;

        World world = MinecraftServer.getServer().worldServers[0];
        PlayerKnowledge playerKnowledge = (PlayerKnowledge) world.loadItemData(PlayerKnowledge.class, playerName);

        if (playerKnowledge == null) {

            playerKnowledge = new PlayerKnowledge(playerName);
            world.setItemData(playerName, playerKnowledge);
            System.out.println(playerKnowledge.toString());
        }
        this.playerName = playerName;
        this.playerKnowledge = playerKnowledge;
        this.playerKnowledge.markDirty();
    }

    public PlayerKnowledge getPlayerProfile() {

        return playerKnowledge;
    }

    public double getKnowledgeXP() {

        if (MinecraftServer.getServer() == null)
            return 0;
        return playerKnowledge.getKnowledgeXP();
    }

    public int getKnowledgeRank() {

        if (MinecraftServer.getServer() == null)
            return 0;
        return playerKnowledge.getKnowledgeRank();
    }

    public void setKnowledgeXP(double xp) {

        if (MinecraftServer.getServer() == null)
            return;
        playerKnowledge.knowledgeXP = xp;
        playerKnowledge.markDirty();
    }

    public void setKnowledgeRank(int rank) {

        if (MinecraftServer.getServer() == null)
            return;
        playerKnowledge.knowledgeRank = rank;
        playerKnowledge.markDirty();
    }

    public void addKnowledgeXP(double xp) {

        if (MinecraftServer.getServer() == null)
            return;
        playerKnowledge.knowledgeXP += xp;
        playerKnowledge.markDirty();
        UpdateXP();
    }

    public void addKnowledgeRank(int rank) {

        if (MinecraftServer.getServer() == null)
            return;
        playerKnowledge.knowledgeRank += rank;
        playerKnowledge.markDirty();
    }

    public void markDirty() {

        playerKnowledge.markDirty();
    }

    public void UpdateXP() {

        if (getKnowledgeXP() >= getKnowledgeRank() * 600) {
            setKnowledgeXP(0);
            setKnowledgeRank(getKnowledgeRank() + 1);
        }
    }

    public String getDisplayName() {

        return playerName;
    }

    @Override
    public String toString() {

        return String.format("[Player Name] %s , [Player Knowledge XP] %s , [Player Knowledge Rank] %s", playerName,
                playerKnowledge.getKnowledgeXP(), playerKnowledge.getKnowledgeRank());
    }
}
