package com.thefjong.factorialautomation.part.pipe;

import java.util.Arrays;
import java.util.List;

import uk.co.qmunity.lib.vec.Vec3d;
import uk.co.qmunity.lib.vec.Vec3dCube;

import com.thefjong.factorialautomation.part.FAPart;
import com.thefjong.factorialautomation.reference.Reference;
import com.thefjong.factorialautomation.reference.ReferenceParts;
/**
 * 
 * @author Amadornes
 *
 */
public class PartPipe extends FAPart {

    @Override
    public String getType() {

        return ReferenceParts.PIPE;
    }

    @Override
    public String getUnlocalizedName() {

        return Reference.MODID + ".part." + getType();
    }

    @Override
    public void renderDynamic(Vec3d translation, double delta, int pass) {

    }

    @Override
    public List<Vec3dCube> getSelectionBoxes() {

        return Arrays.asList(new Vec3dCube(0, 0, 0, 1, 1, 1));
    }

}
