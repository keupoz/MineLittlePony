package com.minelittlepony.client.model.components;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

import com.minelittlepony.client.MineLittlePony;
import com.minelittlepony.client.util.render.Part;
import com.minelittlepony.model.ICapitated;
import com.minelittlepony.model.IPart;
import com.minelittlepony.mson.api.model.MsonPart;
import com.minelittlepony.pony.meta.Gender;

import java.util.UUID;

public class PonySnout implements IPart {

    public boolean isHidden = false;

    private ModelPart mare;
    private ModelPart stallion;

    private final ICapitated<ModelPart> head;

    public <T extends Model & ICapitated<ModelPart>> PonySnout(T pony) {
        this(pony, 0, 0, 0);
    }

    public <T extends Model & ICapitated<ModelPart>> PonySnout(T pony, int x, int y, int z) {
        head = pony;

        mare = new Part(pony).offset(HEAD_CENTRE_X + x, HEAD_CENTRE_Y + y, HEAD_CENTRE_Z + z + 0.25F);
        stallion = new Part(pony).offset(HEAD_CENTRE_X + x, HEAD_CENTRE_Y + y, HEAD_CENTRE_Z + z);

        pony.getHead().addChild(stallion);
        pony.getHead().addChild(mare);
    }

    public void rotate(float x, float y, float z) {
        ((MsonPart)mare).rotate(x, y, z);
        ((MsonPart)stallion).rotate(x, y, z);
    }

    @Override
    public void init(float yOffset, float stretch) {
        mare.around(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z)
            .tex(10, 14).south(-2, 2, -5, 4, 2, stretch)
            .tex(11, 13).south(-1, 1, -5, 2, 1, stretch)
            .tex(9, 14)   .top(-2, 2, -5, 1, 1, stretch)
            .tex(14, 14)  .top( 1, 2, -5, 1, 1, stretch)
            .tex(11, 12)  .top(-1, 1, -5, 2, 1, stretch)
            .tex(18, 7).bottom(-2, 4, -5, 4, 1, stretch)
            .tex(9, 14)  .west(-2, 2, -5, 2, 1, stretch)
            .tex(14, 14) .east( 2, 2, -5, 2, 1, stretch)
            .tex(11, 12) .west(-1, 1, -5, 1, 1, stretch)
            .tex(12, 12) .east( 1, 1, -5, 1, 1, stretch);
        stallion.around(HEAD_RP_X, HEAD_RP_Y + yOffset, HEAD_RP_Z)
                .tex(10, 13).south(-2, 1, -5, 4, 3, stretch)
                .tex(10, 13)  .top(-2, 1, -5, 4, 1, stretch)
                .tex(18, 7).bottom(-2, 4, -5, 4, 1, stretch)
                .tex(10, 13) .west(-2, 1, -5, 3, 1, stretch)
                .tex(13, 13) .east( 2, 1, -5, 3, 1, stretch);
    }

    @Override
    public void renderPart(MatrixStack stack, VertexConsumer vertices, int overlayUv, int lightUv, float red, float green, float blue, float alpha, UUID interpolatorId) {
    }

    public void setGender(Gender gender) {
        boolean show = !head.hasHeadGear() && !isHidden && MineLittlePony.getInstance().getConfig().snuzzles.get();

        mare.visible = (show && gender.isMare());
        stallion.visible = (show && gender.isStallion());
    }
}
