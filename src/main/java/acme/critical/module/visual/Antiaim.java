package acme.critical.module.visual;

import acme.critical.module.Mod;
import acme.critical.module.settings.ModeSetting;
import acme.critical.module.settings.KeybindSetting;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class Antiaim extends Mod {
    private ModeSetting mode = new ModeSetting("Mode", "Behind", "Behind", "Spin", "Headless");
    int rotation = 0;

    public Antiaim() {
        super("AntiAim", "Moves your head.", Category.VISUAL);
        addSettings(mode, new KeybindSetting("Key", 0));
    }

    @Override
    public void onTick() {
        switch (mode.getMode()) {
            case "Behind":
                float actualYaw = mc.player.getYaw();
                mc.player.setHeadYaw(actualYaw+180);
                mc.player.setBodyYaw(actualYaw+180);
                mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(actualYaw+180, 90, mc.player.isOnGround()));
            break;
            case "Spin":
                if (rotation < 360) {rotation += 45;}
                else {rotation = 0;};
                mc.player.setHeadYaw(rotation);
                mc.player.setBodyYaw(rotation);
                mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(rotation, mc.player.getPitch(), mc.player.isOnGround()));
            break;
            case "Headless":
                mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(mc.player.getYaw(), 180, mc.player.isOnGround()));
            break;
        }
    }
}
