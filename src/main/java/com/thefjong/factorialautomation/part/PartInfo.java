package com.thefjong.factorialautomation.part;

import java.lang.reflect.Constructor;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.thefjong.factorialautomation.items.Items;

public class PartInfo {

    private String type;
    private FAPart example;
    private ItemStack item;

    private Constructor<? extends FAPart> constructor;
    private Object[] arguments;

    public PartInfo(Class<? extends FAPart> clazz, Object... arguments) {

        generateConstructor(clazz, arguments);

        example = create();
        type = example.getType();

        item = new ItemStack(Items.multipart);
        item.stackTagCompound = new NBTTagCompound();
        item.stackTagCompound.setString("id", type);
    }

    private void generateConstructor(Class<? extends FAPart> clazz, Object... arguments) {

        try {
            Class<?>[] argsClasses = new Class<?>[arguments.length];
            for (int i = 0; i < arguments.length; i++)
                argsClasses[i] = arguments[i].getClass();

            constructor = clazz.getConstructor(argsClasses);
            constructor.setAccessible(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public FAPart create() {

        try {
            return constructor.newInstance(arguments);
        } catch (Exception ex) {
        }

        return null;
    }

    public String getType() {

        return type;
    }

    public FAPart getExample() {

        return example;
    }

    public ItemStack getItem() {

        return item;
    }

}
