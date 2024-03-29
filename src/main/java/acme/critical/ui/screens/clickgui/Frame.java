package acme.critical.ui.screens.clickgui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import acme.critical.module.Mod;
import acme.critical.module.Mod.Category;
import acme.critical.module.ModMan;
import acme.critical.module.client.Clickgui;
import acme.critical.ui.screens.clickgui.setting.Component;
import acme.critical.utils.Render2DUtils;

public class Frame extends Window {
    public int x, y, width, height, dragX, dragY;
    public Category category;

    public boolean dragging, extended;
    public static int draggedElements;
    private List<ModuleButton> buttons;

    protected MinecraftClient mc = MinecraftClient.getInstance();

    public Frame(Category category, int x, int y, int width, int height) {
        super(category.name, x, y, width, height);

        int offset = height;

        for (Mod mod : ModMan.INSTANCE.getModulesInCategory(category)) {
            super.addButton(new ModuleButton(mod, this, offset));
            offset += height;
        }
    }
}
