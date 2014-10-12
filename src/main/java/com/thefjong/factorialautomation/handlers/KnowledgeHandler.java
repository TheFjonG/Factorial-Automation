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
	
	public String _playerName;
	public PlayerKnowledge _playerKnowledge;
	
	public KnowledgeHandler(String playerName){
		
		if(MinecraftServer.getServer() == null) return;
		
		World world = MinecraftServer.getServer().worldServers[0];
		PlayerKnowledge playerKnowledge = (PlayerKnowledge) world.loadItemData(PlayerKnowledge.class, playerName);
		
		if(playerKnowledge == null){
			playerKnowledge = new PlayerKnowledge(playerName);
			world.setItemData(playerName, playerKnowledge);
			System.out.println(playerKnowledge.toString());
		}
			_playerName = playerName;
			_playerKnowledge = playerKnowledge;
			_playerKnowledge.markDirty();
	}
	
	public PlayerKnowledge getPlayerProfile(){
		
		return _playerKnowledge;
	}
	public double getKnowledgeXP(){	
		if(MinecraftServer.getServer() == null) return 0;
		return _playerKnowledge.getKnowledgeXP();
	}
	
	public int getKnowledgeRank(){
		if(MinecraftServer.getServer() == null) return 0;
		return _playerKnowledge.getKnowledgeRank();
	}
	
	public void setKnowledgeXP(double xp){
		if(MinecraftServer.getServer() == null) return;
		_playerKnowledge.knowledgeXP = xp;
		_playerKnowledge.markDirty();
	}
	
	public void setKnowledgeRank(int rank){
		if(MinecraftServer.getServer() == null) return;
		_playerKnowledge.knowledgeRank = rank;
		_playerKnowledge.markDirty();
	}
	
	public void addKnowledgeXP(double xp){
		if(MinecraftServer.getServer() == null) return;
		_playerKnowledge.knowledgeXP += xp;
		_playerKnowledge.markDirty();
		UpdateXP();
	}
	
	public void addKnowledgeRank(int rank){
		if(MinecraftServer.getServer() == null) return;
		_playerKnowledge.knowledgeRank += rank;
		_playerKnowledge.markDirty();
	}
	public void markDirty(){
		_playerKnowledge.markDirty();
	}
	public void UpdateXP(){
		if(getKnowledgeXP() >= getKnowledgeRank()*600){
			setKnowledgeXP(0);
			setKnowledgeRank(getKnowledgeRank()+1);
		}
	}
	@Override
	public String toString(){
	
		return String.format("[Player Name] %s , [Player Knowledge XP] %s , [Player Knowledge Rank] %s", _playerName, _playerKnowledge.getKnowledgeXP(), _playerKnowledge.getKnowledgeRank());
	}
}
