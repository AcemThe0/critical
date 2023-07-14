package acme.critical.ui.screens.clickgui.setting;

import net.minecraft.client.MinecraftClient;
import acme.critical.module.settings.Setting;
import net.minecraft.client.gui.DrawContext;
import acme.critical.ui.screens.clickgui.ModuleButton;

public class Component {
    public int offset;
    public Setting setting;
    public ModuleButton parent;

    protected MinecraftClient mc = MinecraftClient.getInstance();

    public Component(Setting setting, ModuleButton parent, int offset) {
        this.setting = setting;
        this.parent = parent;
        this.offset = offset;
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

    }

    public void mouseClicked(double mouseX, double mouseY, int button) {

    }

    public void mouseReleased(double mouseX, double mouseY, int button) {

    }

    public void keyPressed(int key) {

    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > parent.parent.x && mouseX < parent.parent.x + parent.parent.width && mouseY > parent.parent.y + parent.offset + offset && mouseY < parent.parent.y + parent.offset + offset + parent.parent.height;
    }
}
