package acme.critical.module.misc;

import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.DisconnectS2CPacket;
import net.minecraft.text.Text;

import acme.critical.module.Mod;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.module.settings.NumberSetting;
import acme.critical.utils.ChatUtils;
import acme.critical.utils.FriendsUtils;

public class Autolog extends Mod {
    public NumberSetting logHealth =
        new NumberSetting("Min Health", 0, 20, 15, 1);
    public BooleanSetting logPlayer = new BooleanSetting("Players", false);

    public Autolog() {
        super("Autolog", "Log when conditions are met.", Category.MISC);
        addSettings(logHealth, logPlayer, new KeybindSetting("Key", 0));
    }

    public void onTick() {
        String logMessage = shouldLog();
        if (logMessage == null)
            return;

        toggle();
        mc.getNetworkHandler().onDisconnect(
            new DisconnectS2CPacket(Text.literal("Autolog: " + logMessage))
        );
    }

    private String shouldLog() {
        if (mc.player.getHealth() < logHealth.getValueInt())
            return "Health < " + logHealth.getValueInt();
        if (logPlayer.isEnabled()) {
            for (Entity ent : mc.world.getEntities()) {
                if (ent != mc.player && ent.isPlayer() &&
                    !FriendsUtils.isFriend(ent))
                    return "Player " + ent.getName().getString() +
                        " entered view";
            }
        }
        return null;
    }
}
