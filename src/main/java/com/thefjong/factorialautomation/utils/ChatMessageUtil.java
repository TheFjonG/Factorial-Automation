package com.thefjong.factorialautomation.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class ChatMessageUtil {
	public static long lastMessageTime = 0;

	
	public static void sendChatMessageToPlayer(EntityPlayer player, String message){
		
		player.addChatComponentMessage(new ChatComponentText(message));
	}
	
	
}
