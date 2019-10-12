package com.minelittlepony.client.model.entities;

import net.minecraft.client.model.Cuboid;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;

import com.minelittlepony.client.model.races.ModelAlicorn;

public class ModelIllagerPony<T extends IllagerEntity> extends ModelAlicorn<T> {

    public ModelIllagerPony() {
        super(false);
    }

    @Override
    public void setAngles(T illager, float move, float swing, float ticks, float headYaw, float headPitch, float scale) {
        super.setAngles(illager, move, swing, ticks, headYaw, headPitch, scale);

        IllagerEntity.State pose = illager.getState();

        boolean rightHanded = illager.getMainArm() == Arm.RIGHT;
        float mult = rightHanded ? 1 : -1;
        Cuboid arm = getArm(illager.getMainArm());

        if (pose == IllagerEntity.State.ATTACKING) {
            // vindicator attacking
            float f = MathHelper.sin(getSwingAmount() * (float) Math.PI);
            float f1 = MathHelper.sin((1 - (1 - getSwingAmount()) * (1 - getSwingAmount())) * (float) Math.PI);

            float cos = MathHelper.cos(ticks * 0.09F) * 0.05F + 0.05F;
            float sin = MathHelper.sin(ticks * 0.067F) * 0.05F;

            rightArm.roll = cos;
            leftArm.roll  = cos;

            rightArm.yaw = 0.15707964F;
            leftArm.yaw = -0.15707964F;

            arm.pitch = -1.8849558F + MathHelper.cos(ticks * 0.09F) * 0.15F;
            arm.pitch += f * 2.2F - f1 * 0.4F;

            rightArm.pitch += sin;
            leftArm.pitch  -= sin;
        } else if (pose == IllagerEntity.State.SPELLCASTING) {
            // waving arms!
            // rightArm.rotationPointZ = 0;
            arm.pitch = (float) (-.75F * Math.PI);
            arm.roll = mult * MathHelper.cos(ticks * 0.6662F) / 4;
            arm.yaw = mult * 1.1F;
        } else if (pose == IllagerEntity.State.BOW_AND_ARROW) {
            aimBow(arm, ticks);
        }
    }
}