package com.minelittlepony.client.model.races;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

import com.minelittlepony.client.model.armour.ModelPonyArmour;
import com.minelittlepony.client.model.armour.ArmourWrapper;
import com.minelittlepony.model.BodyPart;
import com.minelittlepony.model.armour.IEquestrianArmour;

public class ModelZebra<T extends LivingEntity> extends ModelEarthPony<T> {

    public ModelZebra(boolean useSmallArms) {
        super(useSmallArms);
    }

    @Override
    public IEquestrianArmour<?> createArmour() {
        return new ArmourWrapper<>(Armour::new);
    }

    @Override
    public void transform(BodyPart part, MatrixStack stack) {
        applyLongNeck(part, stack);
        super.transform(part, stack);
    }

    class Armour extends ModelPonyArmour<T> {
        @Override
        public void transform(BodyPart part, MatrixStack stack) {
            applyLongNeck(part, stack);
            super.transform(part, stack);
        }
    }

    static void applyLongNeck(BodyPart part, MatrixStack stack) {
        if (part == BodyPart.HEAD || part == BodyPart.NECK) {
            stack.translate(0, -0.1F, 0);
        }
        if (part == BodyPart.NECK) {
             stack.scale(1, 1.3F, 1);
        }
    }
}
