package acme.critical.module.movement;

import acme.critical.module.Mod;
import acme.critical.module.settings.KeybindSetting;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.OnGroundOnly;

public class Nofall extends Mod {

    public Nofall() {
        super("Nofall", "Prevent fall damage.", Category.MOVEMENT);
        addSetting(new KeybindSetting("Key", 0));
    }

    //Add checks as to not spam packets
    @Override
    public void onTick() {
            if (mc.player.fallDistance > 2) mc.player.networkHandler.sendPacket(new OnGroundOnly(true));
        super.onTick();
    }
}
