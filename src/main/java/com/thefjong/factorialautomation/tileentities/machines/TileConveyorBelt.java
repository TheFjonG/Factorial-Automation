package com.thefjong.factorialautomation.tileentities.machines;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import uk.co.qmunity.lib.tileentity.TileBase;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 
 * @author MineMaarten
 * 
 */
public class TileConveyorBelt extends TileBase {

    private static final double MIN_ITEM_DISTANCE = 0.2001;
    private static final int MAX_STACK_SIZE = 1;// If increasing, check logic.
    public final List<BeltStack> beltStacks = new ArrayList<BeltStack>();
    public ForgeDirection entryDirection = ForgeDirection.NORTH;
    private Section section = Section.STRAIGHT;
    @SideOnly(Side.CLIENT)
    private static RenderItem customRenderItem;
    private static EntityItem renderedItem;

    public enum Section {
        STRAIGHT, LEFT, RIGHT
    }

    @Override
    public void updateEntity() {

        super.updateEntity();
        Iterator<BeltStack> stacks = beltStacks.iterator();
        while (stacks.hasNext()) {
            BeltStack stack = stacks.next();
            if (stack.update()) {
                if (!worldObj.isRemote) {
                    ForgeDirection d = getFacingDirection().getOpposite();
                    TileEntity te = worldObj.getTileEntity(xCoord + d.offsetX, yCoord + d.offsetY, zCoord + d.offsetZ);
                    if (te instanceof TileConveyorBelt) {
                        TileConveyorBelt belt = (TileConveyorBelt) te;
                        double oldProgress = stack.progress;
                        if (belt.entryDirection == d) {
                            stack.progress = 0;
                            stack.isOnFirstPart = true;
                            if (belt.addBeltStack(stack)) {
                                stacks.remove();
                                sendUpdatePacket();
                                continue;
                            }
                        }
                        stack.progress = oldProgress;
                        stack.isOnFirstPart = false;
                        stack.setConveyor(this);
                    }
                }

            }
        }
        // sendUpdatePacket();
    }

    public boolean addItem(EntityItem item) {

        if (addItem(item.getEntityItem(), item.posX, item.posY, item.posZ)) {
            if (item.getEntityItem().stackSize <= 0) {
                item.setDead();
            }
            return true;
        } else {
            return false;
        }

    }

    public boolean addItem(ItemStack item, double x, double y, double z) {

        if (worldObj.isRemote)
            return false;
        int offX = x - xCoord < 0.5 ? -1 : 1;
        int offZ = z - zCoord < 0.5 ? -1 : 1;
        double progress = 0.25;
        boolean leftSide = true;
        if (entryDirection.offsetX == -offX) {
            ForgeDirection rot = entryDirection.getRotation(ForgeDirection.DOWN);
            leftSide = rot.offsetZ == offZ;
        } else if (entryDirection.offsetZ == -offZ) {
            ForgeDirection rot = entryDirection.getRotation(ForgeDirection.DOWN);
            leftSide = rot.offsetX == offX;
        } else if (getFacingDirection().offsetX == -offX) {
            ForgeDirection rot = getFacingDirection().getOpposite().getRotation(ForgeDirection.DOWN);
            leftSide = rot.offsetZ == offZ;
            progress = 0.75;
        } else if (getFacingDirection().offsetZ == -offZ) {
            ForgeDirection rot = getFacingDirection().getOpposite().getRotation(ForgeDirection.DOWN);
            leftSide = rot.offsetX == offX;
            progress = 0.75;
        }
        BeltStack stack = new BeltStack(this, item.copy().splitStack(MAX_STACK_SIZE), leftSide);
        stack.progress = progress;

        if (addBeltStack(stack)) {
            item.stackSize -= MAX_STACK_SIZE;
            return true;
        } else {
            return false;
        }

    }

    private boolean addBeltStack(BeltStack stack) {

        // QLog.info("stack: " + stack.progress + " , " + stack.isOnLeftSide);
        stack.setConveyor(this);
        if (stack.getMaxMovement() > 0) {
            beltStacks.add(stack);
            sendUpdatePacket();
            return true;

        } else {
            return false;
        }
    }

    protected double getSpeed() {

        return 0.05D;
    }

    @Override
    public void onBlockNeighbourChanged() {

        super.onBlockNeighbourChanged();

        ForgeDirection dir = getFacingDirection().getOpposite();
        ForgeDirection prevDir = entryDirection;
        for (int i = 2; i < 6; i++) {
            ForgeDirection d = ForgeDirection.getOrientation(i);
            if (d == getFacingDirection().getOpposite())
                continue;
            TileEntity te = worldObj.getTileEntity(xCoord + d.offsetX, yCoord, zCoord + d.offsetZ);
            if (te instanceof TileConveyorBelt && ((TileConveyorBelt) te).getFacingDirection() == d) {
                dir = d.getOpposite();
                if (dir == prevDir)
                    break;
            }
        }
        setEntryDirection(dir);
    }

    public Section getSection() {

        return section;
    }

    private void setEntryDirection(ForgeDirection dir) {

        if (dir != entryDirection) {
            entryDirection = dir;

            ForgeDirection d = entryDirection.getRotation(ForgeDirection.DOWN);
            for (int i = 0; i < 3; i++) {
                if (d == getFacingDirection()) {
                    section = i == 0 ? Section.RIGHT : i == 1 ? Section.STRAIGHT : Section.LEFT;
                    break;
                }
                d = d.getRotation(ForgeDirection.DOWN);
            }
            if (worldObj != null && !worldObj.isRemote)
                sendUpdatePacket();
        }
    }

    @Override
    public List<ItemStack> getDrops() {

        List<ItemStack> drops = super.getDrops();
        for (BeltStack stack : beltStacks) {
            drops.add(stack.stack);
        }
        return drops;
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {

        super.writeToNBT(tag);

    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {

        super.readFromNBT(tag);

    }

    @Override
    public void writeToPacketNBT(NBTTagCompound tag) {

        super.writeToPacketNBT(tag);

        NBTTagList tagList = new NBTTagList();
        for (BeltStack stack : beltStacks) {
            NBTTagCompound stackTag = new NBTTagCompound();
            stack.writeToNBT(stackTag);
            tagList.appendTag(stackTag);
        }
        tag.setTag("beltStacks", tagList);

        tag.setByte("entryDir", (byte) entryDirection.ordinal());
    }

    @Override
    public void readFromPacketNBT(NBTTagCompound tag) {

        super.readFromPacketNBT(tag);

        NBTTagList tagList = tag.getTagList("beltStacks", 10);
        beltStacks.clear();
        for (int i = 0; i < tagList.tagCount(); i++) {
            beltStacks.add(loadBeltStackFromNBT(tagList.getCompoundTagAt(i)));
        }

        setEntryDirection(ForgeDirection.getOrientation(tag.getByte("entryDir")));
    }

    private BeltStack loadBeltStackFromNBT(NBTTagCompound tag) {

        BeltStack stack = new BeltStack(this, ItemStack.loadItemStackFromNBT(tag.getCompoundTag("stack")), tag.getBoolean("isOnLeftSide"));
        stack.isOnFirstPart = tag.getBoolean("isOnFirstPart");
        stack.progress = tag.getDouble("progress");
        return stack;
    }

    public static class BeltStack {

        public ItemStack stack;
        public boolean isOnFirstPart;
        public boolean isOnLeftSide;
        public double progress;
        private double oldProgress;
        private TileConveyorBelt conveyorBelt;

        public BeltStack(TileConveyorBelt conveyor, ItemStack stack, boolean isOnLeftSide) {

            this.stack = stack;
            this.isOnLeftSide = isOnLeftSide;
            isOnFirstPart = true;
            conveyorBelt = conveyor;
        }

        public void setConveyor(TileConveyorBelt conveyor) {

            conveyorBelt = conveyor;
        }

        public boolean update() {

            oldProgress = progress;
            // if (!conveyorBelt.worldObj.isRemote) {
            double move = getMaxMovement();
            if (move > 0)
                isOnFirstPart = progress(move);
            // }
            return progress >= 1;
        }

        public boolean progress(double progression) {

            progress += progression;
            if (isOnFirstPart) {
                boolean onFirstPart = true;
                switch (conveyorBelt.getSection()) {
                case LEFT:
                    if (progress >= (isOnLeftSide ? 0.25 : 0.75)) {
                        progress = 1 - progress;
                        onFirstPart = false;
                    }
                    break;
                case STRAIGHT:
                    if (progress >= 0.5) {
                        onFirstPart = false;
                    }
                    break;
                case RIGHT:
                    if (progress >= (isOnLeftSide ? 0.75 : 0.25)) {
                        progress = 1 - progress;
                        onFirstPart = false;
                    }
                    break;
                }
                if (!onFirstPart) {
                    oldProgress = progress -= progression;
                    return false;
                }
            }
            return isOnFirstPart;
        }

        public double getMaxMovement() {

            double maxMove = Math.min(conveyorBelt.getSpeed(), 1 - progress);
            for (BeltStack s : conveyorBelt.beltStacks) {
                if (s != this && s.isOnLeftSide == isOnLeftSide) {
                    if (isOnFirstPart && !s.isOnFirstPart) {

                        double oldProgress = progress;
                        double oldOldProgress = this.oldProgress;
                        if (!progress(maxMove)) {
                            double dX = s.progress - progress - MIN_ITEM_DISTANCE;
                            if (dX < 0) {
                                maxMove += dX;
                            }
                        }
                        progress = oldProgress;
                        this.oldProgress = oldOldProgress;
                        if (maxMove <= 0) {
                            return 0;
                        }

                    }
                    if (isOnFirstPart == s.isOnFirstPart) {
                        double dX = s.progress - progress;
                        if (dX >= 0) {
                            maxMove = Math.min(maxMove, dX - MIN_ITEM_DISTANCE);
                            if (maxMove <= 0) {
                                return 0;
                            }
                        }
                    }
                }
            }
            return maxMove;
        }

        public void writeToNBT(NBTTagCompound tag) {

            NBTTagCompound stackTag = new NBTTagCompound();
            stack.writeToNBT(stackTag);
            tag.setTag("stack", stackTag);
            tag.setBoolean("isOnFirstPart", isOnFirstPart);
            tag.setBoolean("isOnLeftSide", isOnLeftSide);
            tag.setDouble("progress", progress);
        }

        @SideOnly(Side.CLIENT)
        public void render(float partialTick) {

            if (customRenderItem == null) {
                customRenderItem = new RenderItem() {

                    @Override
                    public boolean shouldBob() {

                        return false;
                    };

                };
                customRenderItem.setRenderManager(RenderManager.instance);

                renderedItem = new EntityItem(FMLClientHandler.instance().getWorldClient());
                renderedItem.hoverStart = 0.0F;
            }

            renderedItem.setEntityItemStack(stack);

            double renderProgress = (oldProgress + (progress - oldProgress) * partialTick) * 2 - 1;
            ForgeDirection heading = isOnFirstPart ? conveyorBelt.entryDirection : conveyorBelt.getFacingDirection().getOpposite();

            GL11.glPushMatrix();
            GL11.glTranslated(heading.offsetX * renderProgress * 0.5, -0.35, heading.offsetZ * renderProgress * 0.5);
            heading = heading.getRotation(ForgeDirection.UP);
            GL11.glTranslated((isOnLeftSide ? -1 : 1) * heading.offsetX * 0.25, 0, (isOnLeftSide ? -1 : 1) * heading.offsetZ * 0.25);

            GL11.glPushMatrix();
            if (stack.stackSize > 5) {
                GL11.glScaled(0.8, 0.8, 0.8);
            }
            if (!(stack.getItem() instanceof ItemBlock)) {
                GL11.glScaled(0.8, 0.8, 0.8);
                GL11.glTranslated(0, -0.15, 0);
            }

            customRenderItem.doRender(renderedItem, 0, 0, 0, 0, 0);
            GL11.glPopMatrix();

            GL11.glPopMatrix();
        }
    }

}