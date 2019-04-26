package com.minelittlepony.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import com.minelittlepony.MineLittlePony;
import com.minelittlepony.client.render.entities.MobRenderers;
import com.minelittlepony.common.client.gui.GuiHost;
import com.minelittlepony.common.client.gui.IGuiGuest;
import com.minelittlepony.common.client.gui.element.Label;
import com.minelittlepony.common.client.gui.element.Slider;
import com.minelittlepony.common.client.gui.element.Toggle;
import com.minelittlepony.settings.PonyConfig;
import com.minelittlepony.settings.PonyLevel;
import com.minelittlepony.settings.PonyConfig.PonySettings;

/**
 * In-Game options menu.
 *
 */
public class GuiPonySettings implements IGuiGuest {

    private static final String OPTIONS_PREFIX = "minelp.options.";

    private static final String PONY_LEVEL = OPTIONS_PREFIX + "ponylevel";

    private static final String MOB_PREFIX = "minelp.mobs.";

    private PonyConfig config;

    public GuiPonySettings() {
        config = MineLittlePony.getInstance().getConfig();
    }

    @Override
    public void initGui(GuiHost host) {
        final int LEFT = host.width / 10;
        final int RIGHT = host.mustScroll() ? LEFT : host.width - host.width / 3 - 16;

        int row = host.mustScroll() ? 0 : 32;

        if (!host.mustScroll()) {
            host.addButton(new Label(host.width / 2, 12).setCentered()).getStyle().setText(getTitle());
        }

        host.addButton(new Label(LEFT, row += 15)).getStyle().setText(PONY_LEVEL);
        host.addButton(new Slider(LEFT, row += 15, 0, 2, config.getPonyLevel().ordinal())
                .onChange(v -> {
                    PonyLevel level = PonyLevel.valueFor(v);
                    config.setPonyLevel(level);
                    return (float)level.ordinal();
                })
                .setFormatter(value -> I18n.format(PONY_LEVEL + "." + PonyLevel.valueFor(value).name().toLowerCase())));

        if (GuiScreen.isCtrlKeyDown() && GuiScreen.isShiftKeyDown()) {
            host.addButton(new Label(LEFT, row += 30)).getStyle().setText("minelp.debug.scale");
            host.addButton(new Slider(LEFT, row += 15, 0.1F, 3, config.getGlobalScaleFactor())
                    .onChange(v -> {
                        config.setGlobalScaleFactor(v);
                        return config.getGlobalScaleFactor();
                    })
                    .setFormatter(value -> I18n.format("minelp.debug.scale.value", I18n.format(describeCurrentScale(value)))));
        }

        row += 15;
        host.addButton(new Label(LEFT, row += 15)).getStyle().setText(OPTIONS_PREFIX + "options");
        for (PonySettings i : PonySettings.values()) {
            host.addButton(new Toggle(LEFT, row += 20, i.get()))
                .onChange(i)
                .getStyle().setText(OPTIONS_PREFIX + i.name().toLowerCase());
        }

        if (host.mustScroll()) {
            row += 15;
        } else {
            row = 32;
        }

        host.addButton(new Label(RIGHT, row += 15)).getStyle().setText(MOB_PREFIX + "title");
        for (MobRenderers i : MobRenderers.values()) {
            host.addButton(new Toggle(RIGHT, row += 20, i.get()))
                .onChange(i)
                .getStyle().setText(MOB_PREFIX + i.name().toLowerCase());
        }
    }

    public String describeCurrentScale(float value) {
        if (value >= 3) {
            return I18n.format("minelp.debug.scale.meg");
        }
        if (value == 2) {
            return I18n.format("minelp.debug.scale.max");
        }
        if (value == 1) {
            return I18n.format("minelp.debug.scale.mid");
        }
        if (value == 0.9F) {
            return I18n.format("minelp.debug.scale.sa");
        }
        if (value <= 0.1F) {
            return I18n.format("minelp.debug.scale.min");
        }
        return String.format("%f", value);
    }

    @Override
    public boolean render(GuiHost host, int mouseX, int mouseY, float partialTicks) {
        host.drawDefaultBackground();
        return true;
    }

    @Override
    public void onGuiClosed(GuiHost host) {
        config.save();
    }

    @Override
    public String getTitle() {
        return OPTIONS_PREFIX + "title";
    }
}
