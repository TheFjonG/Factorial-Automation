package com.thefjong.factorialautomation.handlers;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;

import com.thefjong.factorialautomation.items.Items;
import com.thefjong.factorialautomation.utils.ChatMessageUtil;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
/**
 * 
 * @author TheFjong
 *
 */
public class ModEventHandler {

    Random random = new Random();

    @SubscribeEvent
    public void onPlayerRespawnEvent(PlayerRespawnEvent event) {

        KnowledgeHandler playerKnowledge = new KnowledgeHandler(event.player.getDisplayName());
        playerKnowledge.setKnowledgeXP(0);
        // TODO: Convert to lang
        event.player.addChatComponentMessage(new ChatComponentText("You've lost some brain cells caused by the damage. Brain XP: "
                + (int) playerKnowledge.getKnowledgeXP()));
    }

    @SubscribeEvent
    public void onEntityLivingDeath(LivingDeathEvent event) {

        if (event.entityLiving instanceof EntityZombie && event.source.getDamageType().equals("player")) {
            if (random.nextInt(3) == 0) {

                EntityItem drop = new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ,
                        new ItemStack(Items.brainfragment));
                event.entity.worldObj.spawnEntityInWorld(drop);
            }
        }
        if (event.entityLiving instanceof EntityZombie && event.source.getSourceOfDamage() instanceof EntityArrow) {
            if (((EntityArrow) event.source.getSourceOfDamage()).shootingEntity != null) {
                if (((EntityArrow) event.source.getSourceOfDamage()).shootingEntity instanceof EntityPlayer) {
                    if (random.nextInt(3) == 0) {

                        EntityItem drop = new EntityItem(event.entity.worldObj, event.entity.posX, event.entity.posY, event.entity.posZ,
                                new ItemStack(Items.brainfragment));
                        event.entity.worldObj.spawnEntityInWorld(drop);
                    }
                }
            }
        }

    }

    @SubscribeEvent
    public void onPlayerUseItemEvent(PlayerUseItemEvent.Finish event) {

        if (event.item != null && event.item.getItem() == Items.brain && !event.entityPlayer.worldObj.isRemote) {
            KnowledgeHandler playerKnowledge = new KnowledgeHandler(event.entityPlayer.getDisplayName());
            if (playerKnowledge.getKnowledgeRank() <= 20) {

                playerKnowledge.addKnowledgeXP(30);

                // TODO:Convert to lang.
                ChatMessageUtil.sendChatMessageToPlayer(event.entityPlayer, "You now have all the zombies' knowledge and inventions!");
            } else {
                // TODO:Convert to lang.
                ChatMessageUtil.sendChatMessageToPlayer(event.entityPlayer,
                        "You're loaded with inventions. You need to find a way to press more inventions in!");

            }
        }
    }

}
