package com.minelittlepony.client.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import com.minelittlepony.pony.IPony;

public final class DebugBoundingBoxRenderer {

    public static <T extends LivingEntity> void render(IPony pony, EntityRenderer<T> renderer, T entity, MatrixStack stack, VertexConsumerProvider renderContext, float tickDelta) {
        MinecraftClient mc = MinecraftClient.getInstance();

        if (!mc.getEntityRenderManager().shouldRenderHitboxes() || entity.squaredDistanceTo(mc.player) > 70) {
            return;
        }

        Vec3d offset = renderer.getPositionOffset(entity, tickDelta);

        stack.push();
        stack.translate(-offset.x, -offset.y, -offset.z);

        float yaw = MathHelper.lerp(tickDelta, entity.prevBodyYaw, entity.bodyYaw);

        stack.multiply(Vector3f.NEGATIVE_Y.getDegreesQuaternion(yaw));

        Box boundingBox = pony.getComputedBoundingBox(entity);

        Vec3d pos = entity.getPos();

        double x = - MathHelper.lerp(tickDelta, entity.lastRenderX, pos.x);
        double y = - MathHelper.lerp(tickDelta, entity.lastRenderY, pos.y);
        double z = - MathHelper.lerp(tickDelta, entity.lastRenderZ, pos.z);

        VertexConsumer vertices = renderContext.getBuffer(RenderLayer.getLines());

        WorldRenderer.drawBox(stack, vertices, boundingBox.offset(x, y, z), 1, 1, 0, 1);
        stack.pop();
    }
}
