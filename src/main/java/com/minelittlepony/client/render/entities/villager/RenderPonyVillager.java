package com.minelittlepony.client.render.entities.villager;

import com.minelittlepony.client.model.entities.ModelVillagerPony;
import com.minelittlepony.util.resources.ITextureSupplier;

import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.VillagerEntity;

public class RenderPonyVillager extends AbstractVillagerRenderer<VillagerEntity, ModelVillagerPony<VillagerEntity>> {

    private static final String TYPE = "villager";
    private static final ITextureSupplier<String> FORMATTER = ITextureSupplier.formatted("minelittlepony", "textures/entity/villager/%s.png");

    public RenderPonyVillager(EntityRenderDispatcher manager, EntityRendererRegistry.Context context) {
        super(manager, new ModelVillagerPony<>(), TYPE, FORMATTER);
    }

    @Override
    public void scale(VillagerEntity villager, MatrixStack stack, float ticks) {
        super.scale(villager, stack, ticks);
        stack.scale(BASE_MODEL_SCALE, BASE_MODEL_SCALE, BASE_MODEL_SCALE);
    }

}
