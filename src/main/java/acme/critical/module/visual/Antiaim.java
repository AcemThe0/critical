package acme.critical.module.visual;

import acme.critical.module.Mod;
import acme.critical.module.settings.KeybindSetting;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class Antiaim extends Mod {
    int rotation = 0;

    public Antiaim() {
        super("AntiAim", "Moves your head.", Category.VISUAL);
        addSetting(new KeybindSetting("Key", 0));
    }


    @Override
    public void onTick() {
        float actualYaw = mc.player.getYaw();
        mc.player.setHeadYaw(actualYaw+180);
        mc.player.setBodyYaw(actualYaw+180);

        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(actualYaw+180, 90, mc.player.isOnGround()));
    }
}
