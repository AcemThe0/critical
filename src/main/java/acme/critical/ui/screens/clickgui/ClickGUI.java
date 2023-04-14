package acme.critical.ui.screens.clickgui;

import java.util.List;
import java.util.ArrayList;
import net.minecraft.text.Text;
import acme.critical.module.Mod.Category;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import acme.critical.module.client.Clickgui;
import acme.critical.profile.Profile;
import acme.critical.profile.files.ThemeFile;

public class ClickGUI extends Screen {
    public static final ClickGUI INSTANCE = new ClickGUI();

    //private List<Frame> frames;
    private List<Window> frames;
    //private SearchWindow searchwindow;

    private ClickGUI() {
        super(Text.literal("ClickGUI"));

        frames = new ArrayList<>();

        int offset = 10;
        for (Category category : Category.values()) {
            frames.add(new Frame(category, offset, 15, 80, 15));
            offset += 80;
        }

	frames.add(new SearchWindow(offset, 15, 80, 15));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (Clickgui.reloadTheme.isEnabled()) {
            Clickgui.reloadTheme.setEnabled(false);
            Profile.INSTANCE.load(ThemeFile.class);
        }

        renderBackground(matrices);
        for (Window frame : frames) {
            frame.render(matrices, mouseX, mouseY, delta);
            frame.updatePosition(mouseX, mouseY);
        }
    super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (Window frame : frames) {
            frame.mouseClicked(mouseX, mouseY, button);
        }
    return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for (Window frame : frames) {
            frame.mouseReleased(mouseX, mouseY, button);
        }
    return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        for (Window frame : frames) {
            frame.keyPressed(keyCode);
        }
    return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
