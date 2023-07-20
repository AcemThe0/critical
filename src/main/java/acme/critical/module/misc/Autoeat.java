package acme.critical.module.misc;

import acme.critical.module.Mod;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.module.settings.NumberSetting;
import acme.critical.utils.ChatUtils;
import acme.critical.utils.MiscUtils;

public class Autoeat extends Mod {
    private NumberSetting hunger = new NumberSetting("Hunger", 0, 20, 12, 1);
    private NumberSetting health = new NumberSetting("Health", 0, 20, 18, 1);

    private boolean isEating = false;
    private int slot = 0;
    private int lastSlot = 0;

    public Autoeat() {
        super("Autoeat", "Automatically fill hunger", Category.MISC);
        addSettings(hunger, health, new KeybindSetting("Key", 0));
    }

    public void onTick() {
        if (!isEating && shouldEat()) {
            // start eating
            slot = findFood();
            if (slot == 255) {
                ChatUtils.error("No food in hotbar!");
                toggle();
                return;
            }
            lastSlot = mc.player.getInventory().selectedSlot;
            mc.player.getInventory().selectedSlot = slot;
            MiscUtils.use(true);
            isEating = true;
            return;
        }
        if (isEating) {
            if (!shouldEat()) {
                // stop eating
                MiscUtils.use(false);
                mc.player.getInventory().selectedSlot = lastSlot;
                isEating = false;
                return;
            }
            mc.player.getInventory().selectedSlot = slot;
        }
    }

    public boolean getIsEating() { return isEating; }

    private int findFood() {
        for (int s = 0; s < 9; s++) {
            if (mc.player.getInventory().getStack(s).getItem().isFood())
                return s;
        }
        // no food found
        return 255;
    }

    private boolean shouldEat() {
        if (mc.player.getHungerManager().getFoodLevel() <
                hunger.getValueInt() ||
            (mc.player.getHealth() < health.getValueInt() &&
             mc.player.getHungerManager().getFoodLevel() < 20))
            return true;
        return false;
    }
}
