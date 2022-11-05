package acme.critical.module.movement;

import acme.critical.module.Mod;
import acme.critical.module.settings.ModeSetting;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.KeybindSetting;

public class Auto extends Mod {
    public BooleanSetting jump = new BooleanSetting("Jump", false);
    public BooleanSetting sneak = new BooleanSetting("Sneak", true);
    public BooleanSetting walk = new BooleanSetting("Walk", false);
    public BooleanSetting sprint = new BooleanSetting("Sprint", false);
    public ModeSetting sprintM = new ModeSetting("Sprint", "Vanilla", "Vanilla", "Omni");

    public Auto() {
        super("Auto", "Automatically move", Category.MOVEMENT);
        addSettings(jump, sneak, walk, sprint, sprintM, new KeybindSetting("Key", 0));
    }

    @Override
    public void onTick() {
        if (jump.isEnabled() && !mc.options.jumpKey.isPressed() && mc.player.isOnGround()) mc.options.jumpKey.setPressed(true);
        if (sneak.isEnabled() && !mc.options.sneakKey.isPressed()) mc.options.sneakKey.setPressed(true);
        if (walk.isEnabled() && !mc.options.forwardKey.isPressed()) mc.options.forwardKey.setPressed(true);
        if (sprint.isEnabled()) {
            switch(sprintM.getMode()) {
                case "Vanilla":
                    mc.options.sprintKey.setPressed(true);
                break;
                case "Omni":
                    mc.player.setSprinting(true);
                break;
            }
        }

        super.onTick();
    }

    @Override
    public void onDisable() {
        mc.options.forwardKey.setPressed(false);
        mc.options.sprintKey.setPressed(false);
        mc.options.sneakKey.setPressed(false);
        mc.options.jumpKey.setPressed(false);
    }
}
