package acme.critical.module.visual;

import acme.critical.module.Mod;
import net.minecraft.client.gui.screen.DeathScreen;
import acme.critical.module.settings.KeybindSetting;

public class Nodeath extends Mod {
    private boolean dead;

    public Nodeath() {
        super("NoDeath", "Removes death effects.", Category.VISUAL);
        addSetting(new KeybindSetting("Key", 0));
    }

    @Override
    public void onTick() {
        if (mc.player.isDead()) {
            dead = true;
            mc.player.setHealth(20f);
            mc.setScreen(null);
        }
    }

    @Override
    public void onDisable() {
        if (dead) {
            mc.player.setHealth(0f);
            mc.setScreen(new DeathScreen(null, mc.world.getLevelProperties().isHardcore()));
        }
        dead = false;
    }
}
