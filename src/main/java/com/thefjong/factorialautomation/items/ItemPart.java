package com.thefjong.factorialautomation.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.qmunity.lib.item.ItemMultipart;
import com.qmunity.lib.part.IPart;
import com.thefjong.factorialautomation.part.FAPartFace;
import com.thefjong.factorialautomation.part.PartInfo;
import com.thefjong.factorialautomation.part.PartManager;
import com.thefjong.factorialautomation.reference.Reference;
import com.thefjong.factorialautomation.utils.CustomTabs;
/**
 * 
 * @author Amadornes
 *
 */
public class ItemPart extends ItemMultipart {

    public ItemPart(String name) {

        setUnlocalizedName(Reference.MODID + "." + name);
        setCreativeTab(CustomTabs.forItems);
    }

    @Override
    public String getUnlocalizedName(ItemStack item) {

        return super.getUnlocalizedName() + PartManager.getPartType(item);
    }

    @Override
    public boolean getHasSubtypes() {

        return true;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void getSubItems(Item unused, CreativeTabs tab, List l) {

        for (PartInfo i : PartManager.getRegisteredParts())
            l.add(i.getItem());
    }

    @Override
    public String getCreatedPartType(ItemStack item, EntityPlayer player, World world, MovingObjectPosition mop) {

        return PartManager.getPartType(item);
    }

    @Override
    public IPart createPart(ItemStack item, EntityPlayer player, World world, MovingObjectPosition mop) {

        IPart part = super.createPart(item, player, world, mop);

        if (part instanceof FAPartFace)
            ((FAPartFace) part).setFace(ForgeDirection.getOrientation(mop.sideHit).getOpposite());

        return part;
    }

}
