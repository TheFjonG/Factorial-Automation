package com.thefjong.factorialautomation.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
/**
 *  
 * @author TheFjong
 * 
 */
public class ChatMessageUtil {


    public static void sendChatMessageToPlayer(EntityPlayer player, String message) {

        player.addChatComponentMessage(new ChatComponentText(message));
    }

}
