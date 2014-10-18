/*
 * This file is part of Blue Power. Blue Power is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. Blue Power is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along
 * with Blue Power. If not, see <http://www.gnu.org/licenses/>
 */
package com.thefjong.factorialautomation.part;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.qmunity.lib.part.PartRegistry;
import com.thefjong.factorialautomation.part.pipe.PartPipe;

public class PartManager {

    private static Map<String, PartInfo> parts = new HashMap<String, PartInfo>();

    public static void registerPart(Class<? extends FAPart> clazz, Object... arguments) {

        if (clazz == null)
            return;

        PartInfo info = new PartInfo(clazz, arguments);

        if (info.getType() == null)
            return;

        parts.put(info.getType(), info);
    }

    public static PartInfo getPartInfo(String type) {

        if (parts.containsKey(type))
            return parts.get(type);
        return null;
    }

    public static String getPartType(ItemStack item) {

        try {
            NBTTagCompound tag = item.getTagCompound();
            return tag.getString("id");
        } catch (Exception ex) {
        }
        return null;
    }

    public static FAPart createPart(ItemStack item) {

        return getPartInfo(getPartType(item)).create();
    }

    public static FAPart getExample(ItemStack item) {

        return getExample(getPartType(item));
    }

    public static FAPart getExample(String type) {

        return getPartInfo(type).getExample();
    }

    public static List<PartInfo> getRegisteredParts() {

        List<PartInfo> l = new ArrayList<PartInfo>();

        for (String s : parts.keySet())
            l.add(parts.get(s));

        return l;
    }

    public static void registerParts() {

        // Register part factory
        PartRegistry.registerFactory(new PartFactory());

        // Register parts
        registerPart(PartPipe.class);

        // registerPart(class, arguments);
    }
}
