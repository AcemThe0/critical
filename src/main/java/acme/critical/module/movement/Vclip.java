package acme.critical.module.movement;

import net.minecraft.entity.Entity;

import acme.critical.module.Mod;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.module.settings.NumberSetting;

public class Vclip extends Mod {
    private NumberSetting distance = new NumberSetting("Distance", -9, 9, 3, 1);

    public Vclip() {
        super("Vclip", "Clips you on the Y axis.", Category.MOVEMENT);
        addSettings(distance, new KeybindSetting("Key", 0));
    }

    @Override
    public void onEnable() {
        if (mc.player.hasVehicle()) {
            Entity vehicle = mc.player.getVehicle();
            vehicle.setPosition(
                vehicle.getX(), mc.player.getY() + distance.getValueFloat(),
                vehicle.getZ()
            );
        } else {
            mc.player.setPosition(
                mc.player.getX(), mc.player.getY() + distance.getValueFloat(),
                mc.player.getZ()
            );
        }

        setEnabled(false);
    }
}
