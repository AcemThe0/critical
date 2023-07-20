package acme.critical.module.misc;

import acme.critical.module.Mod;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.utils.ChatUtils;
import acme.critical.utils.MathUtils;

public class CoordsSaver extends Mod {
    int messagesSent = 0;

    public CoordsSaver() {
        super("CoordsSaver", "Displays your death coordinates.", Category.MISC);
        addSetting(new KeybindSetting("Key", 0));
    }

    @Override
    public void onTick() {
        if (this.isEnabled() && !(mc.player.isAlive()) && messagesSent < 1) {
            ChatUtils.message(String.format(
                "You died at X: %d; Y: %d; Z: %d", (int)mc.player.getX(),
                (int)mc.player.getY(), (int)mc.player.getZ()
            ));
            messagesSent += 1;
        }
        if (mc.player.isAlive() && messagesSent >= 1) {
            messagesSent = 0;
        }
        super.onTick();
    }
}
