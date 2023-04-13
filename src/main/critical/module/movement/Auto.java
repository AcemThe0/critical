package acme.critical.module.movement;

import acme.critical.module.Mod;
import acme.critical.module.settings.ModeSetting;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.KeybindSetting;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;

public class Auto extends Mod {
    private BooleanSetting walk = new BooleanSetting("Walk", false);
    private BooleanSetting sneak = new BooleanSetting("Sneak", true);
    private BooleanSetting sprint = new BooleanSetting("Sprint", false);
    private ModeSetting sprintM = new ModeSetting("Sprint", "Vanilla", "Vanilla", "Omni");
    private ModeSetting sneakM = new ModeSetting("Sneak", "Packet", "Packet", "Vanilla");

    public Auto() {
        super("Auto", "Automatically move", Category.MOVEMENT);
        addSettings(walk, sneak, sneakM, sprint, sprintM, new KeybindSetting("Key", 0));
    }

    @Override
    public void onTick() {
        if (walk.isEnabled() && !mc.options.forwardKey.isPressed()) mc.options.forwardKey.setPressed(true);

        if (sneak.isEnabled() && !mc.options.sneakKey.isPressed()) {
            if (sneakM.getMode() == "Packet") mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY));
            if (sneakM.getMode() == "Vanilla") mc.options.sneakKey.setPressed(true);
        }
        if (sprint.isEnabled()) {
            if (sprintM.getMode() == "Vanilla") mc.options.sprintKey.setPressed(true);
            if (sprintM.getMode() == "Omni") mc.player.setSprinting(true);
        }

        super.onTick();
    }

    @Override
    public void onDisable() {
        mc.options.forwardKey.setPressed(false);
        mc.options.sprintKey.setPressed(false);
        mc.player.networkHandler.sendPacket(new ClientCommandC2SPacket(mc.player, ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY));
        mc.options.sneakKey.setPressed(false);
    }
}
