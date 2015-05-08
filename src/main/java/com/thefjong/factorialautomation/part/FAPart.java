package com.thefjong.factorialautomation.part;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import uk.co.qmunity.lib.part.IPart;
import uk.co.qmunity.lib.part.IPartCollidable;
import uk.co.qmunity.lib.part.IPartOccluding;
import uk.co.qmunity.lib.part.IPartSelectable;
import uk.co.qmunity.lib.part.IPartUpdateListener;
import uk.co.qmunity.lib.part.PartBase;
import uk.co.qmunity.lib.raytrace.QMovingObjectPosition;
import uk.co.qmunity.lib.raytrace.RayTracer;
import uk.co.qmunity.lib.vec.Vec3d;
import uk.co.qmunity.lib.vec.Vec3dCube;
import uk.co.qmunity.lib.vec.Vec3i;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 
 * @author Amadornes
 *
 */
public abstract class FAPart extends PartBase implements IPartSelectable, IPartCollidable, IPartOccluding/*, IPartRenderable*/, IPartUpdateListener {

    public abstract String getUnlocalizedName();

    private PartInfo partInfo;

    @Override
    public ItemStack getItem() {

        if (partInfo == null)
            partInfo = PartManager.getPartInfo(getType());

        return partInfo.getItem().copy();
    }

    // @Override
    public boolean renderStatic(Vec3i translation, RenderBlocks renderer, int pass) {

        return false;
    }

    @Override
    public void renderDynamic(Vec3d translation, double delta, int pass) {

    }

    @Override
    public boolean shouldRenderOnPass(int pass) {

        return pass == 0;
    }

    @Override
    public List<Vec3dCube> getOcclusionBoxes() {

        return new ArrayList<Vec3dCube>();
    }

    @Override
    public void addCollisionBoxesToList(List<Vec3dCube> boxes, Entity entity) {

    }

    @Override
    public QMovingObjectPosition rayTrace(Vec3d start, Vec3d end) {

        return RayTracer.instance().rayTraceCubes(this, start, end);
    }

    @Override
    public List<Vec3dCube> getSelectionBoxes() {

        return new ArrayList<Vec3dCube>();
    }

    @Override
    public ItemStack getPickedItem(QMovingObjectPosition mop) {

        return getItem();
    }

    @SideOnly(Side.CLIENT)
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {

    }

    @Override
    public void onPartChanged(IPart part) {

    }

    @Override
    public void onNeighborBlockChange() {

    }

    @Override
    public void onNeighborTileChange() {

    }

    @Override
    public void onAdded() {

    }

    @Override
    public void onRemoved() {

    }

    @Override
    public void onLoaded() {

    }

    @Override
    public void onUnloaded() {

    }

    // @Override
    public boolean onActivated(EntityPlayer player, ItemStack item) {

        return false;
    }

    // @Override
    public void onClicked(EntityPlayer player, ItemStack item) {

    }

    public void addWailaInfo(List<String> info) {

    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {

    }

}
