package com.thefjong.factorialautomation.part;

import uk.co.qmunity.lib.part.IPart;
import uk.co.qmunity.lib.part.IPartFactory;
/**
 * 
 * @author Amadornes
 *
 */
public class PartFactory implements IPartFactory {

    @Override
    public IPart createPart(String type, boolean client) {

        PartInfo info = PartManager.getPartInfo(type);

        if (info == null)
            return null;

        return info.create();
    }
}
