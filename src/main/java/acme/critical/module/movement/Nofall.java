package acme.critical.module.movement;

import acme.critical.module.Mod;
import acme.critical.module.settings.ModeSetting;
import acme.critical.module.settings.KeybindSetting;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.OnGroundOnly;

public class Nofall extends Mod {
    private ModeSetting mode = new ModeSetting("Mode", "Packet", "Packet", "Slow");
    int ticksFallen;

    public Nofall() {
        super("Nofall", "Prevent fall damage.", Category.MOVEMENT);
        addSettings(mode, new KeybindSetting("Key", 0));
    }


    @Override
    public void onTick() {
            switch(mode.getMode()) {
                case "Packet":
                    if (mc.player.fallDistance > 2) {
                        //Add checks as to not spam packets
                        mc.player.networkHandler.sendPacket(new OnGroundOnly(true));
                    }
                break;
                case "Slow":
                    if (mc.player.fallDistance > 2) {
                        ticksFallen +=1;
                        if (ticksFallen > 8) {
                            mc.player.setVelocity(0, 0.01, 0);
                            ticksFallen = 0;
                        }
                    }
                break;
            }

        super.onTick();
    }
}
