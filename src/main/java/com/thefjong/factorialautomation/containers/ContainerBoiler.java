/* Parts of this file is copied from BluePower.
 * Parts copied by TheFjong.
 * License: GNU General Public License 3 and after.
 * 
 * This file is part of Blue Power.
 *
 *     Blue Power is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Blue Power is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Blue Power.  If not, see <http://www.gnu.org/licenses/>
 */
package com.thefjong.factorialautomation.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

import com.thefjong.factorialautomation.tileentities.machines.TileBoiler;

/**
 ****************************************************
 * Parts copied from ContainerDeployer from BluePower
 * 
 * @author MineMaarten 
 * **************************************************
 * 
 * @author TheFjong
 */
public class ContainerBoiler extends Container {

    private final TileBoiler tileBoiler;

    public ContainerBoiler(InventoryPlayer playerInventory, TileBoiler tile) {

        this.tileBoiler = tile;

       
       
        addSlotToContainer(new Slot(tileBoiler, 0, 84, 57));
        
        bindPlayerInventory(playerInventory);
    }

    protected void bindPlayerInventory(InventoryPlayer invPlayer) {

        // Render inventory
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 94 + i * 18));
            }
        }

        // Render hotbar
        for (int j = 0; j < 9; j++) {
            addSlotToContainer(new Slot(invPlayer, j, 8 + j * 18, 152));
        }

    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {

        return tileBoiler.isUseableByPlayer(player);
    }

}
