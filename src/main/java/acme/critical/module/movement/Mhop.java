package acme.critical.module.movement;

import acme.critical.module.Mod;
import acme.critical.module.settings.NumberSetting;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.KeybindSetting;

public class Mhop extends Mod {
    public NumberSetting speed = new NumberSetting("Speed", 0, 5, 0.1, 0.1);
    public NumberSetting jumpStr = new NumberSetting("Height", 0, 5, 0.5, 0.1);
    public BooleanSetting jump = new BooleanSetting("AutoJump", true);

    public Mhop() {
        super("Hop", "MonkeyHop!", Category.MOVEMENT);
        addSettings(speed, jumpStr, jump, new KeybindSetting("Key", 0));
    }

    @Override
    public void onTick() {
        mc.player.airStrafingSpeed = speed.getValueFloat();
            if (jump.isEnabled() && (mc.player.forwardSpeed != 0 && mc.player.isOnGround() || mc.player.sidewaysSpeed != 0 && mc.player.isOnGround())) mc.player.addVelocity(0, jumpStr.getValueFloat(), 0);
        super.onTick();
    }
}
