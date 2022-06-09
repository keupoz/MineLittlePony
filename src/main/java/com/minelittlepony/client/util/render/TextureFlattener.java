package com.minelittlepony.client.util.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.*;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import com.google.common.base.Preconditions;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;

import java.io.IOException;
import java.util.List;

public class TextureFlattener {

    public static void flatten(List<Identifier> textures, Identifier output) {
        Preconditions.checkArgument(textures.size() > 0, "Must have at least one image to flatten");
        MinecraftClient.getInstance().getTextureManager().registerTexture(output, new ResourceTexture(output) {
            @Override
            public void load(ResourceManager resManager) throws IOException {
                NativeImage image = null;

                for (int i = 0; i < textures.size(); i++) {
                    TextureData data = TextureData.load(resManager, textures.get(0));
                    data.checkException();
                    if (image == null) {
                        image = data.getImage();
                    } else {
                        copyOver(data.getImage(), image);
                    }
                }

                if (!RenderSystem.isOnRenderThreadOrInit()) {
                    final NativeImage i = image;
                    RenderSystem.recordRenderCall(() -> upload(i));
                } else {
                    upload(image);
                }
            }

            private void upload(NativeImage image) {
                TextureUtil.prepareImage(getGlId(), 0, image.getWidth(), image.getHeight());
                image.upload(0, 0, 0, 0, 0, image.getWidth(), image.getHeight(), false, false, false, true);
            }
        });
    }

    public static void copyOver(NativeImage from, NativeImage to) {
        copyOver(from, to, 0, 0,
                Math.min(from.getWidth(), to.getWidth()),
                Math.min(from.getHeight(), to.getHeight())
        );
    }

    public static void copyOver(NativeImage from, NativeImage to, int x, int y, int w, int h) {
        for (; x < w; x++) {
            for (; y < h; y++) {
                copy(from, to, x, y);
            }
        }
    }

    public static void copy(NativeImage from, NativeImage to, int x, int y) {
        int color = from.getColor(x, y);
        if (NativeImage.getAlpha(color) > 0) {
            to.setColor(x, y, color);
        }
    }
}