package com.minelittlepony.client.render.layer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.util.Arm;

import com.minelittlepony.client.model.races.ModelAlicorn;
import com.minelittlepony.client.render.IPonyRender;

public class LayerHeldItemIllager<T extends IllagerEntity, M extends ModelAlicorn<T>> extends LayerHeldPonyItem<T, M> {

    public LayerHeldItemIllager(IPonyRender<T,M> livingPony) {
        super(livingPony);
    }

    @Override
    public void render(MatrixStack stack, VertexConsumerProvider renderContext, int lightUv, T entity, float limbDistance, float limbAngle, float tickDelta, float age, float headYaw, float headPitch) {
        if (shouldRender(entity)) {
            super.render(stack, renderContext, lightUv, entity, limbDistance, limbAngle, tickDelta, age, headYaw, headPitch);
        }
    }

    @Override
    protected void renderArm(Arm arm, MatrixStack stack) {
        getModel().getArm(arm).rotate(stack);
    }

    public boolean shouldRender(T entity) {
        return entity.getState() != IllagerEntity.State.CROSSED;
    }
}
