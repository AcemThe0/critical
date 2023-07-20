package acme.critical.module.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import acme.critical.module.Mod;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.utils.ChatUtils;
import acme.critical.utils.MathUtils;

public class Anticheat extends Mod {
    private BooleanSetting antibot = new BooleanSetting("AntiBot", true);
    private BooleanSetting abUUID = new BooleanSetting("ABUUID", true);
    private BooleanSetting abProfile = new BooleanSetting("ABProfile", false);
    private BooleanSetting abLatency = new BooleanSetting("ABLatency", false);
    int removedBots;
    int oldRemovedBots = 1337;

    public Anticheat() {
        super("Anticheat", "Anti-AntiCheat utils.", Category.MISC);
        addSettings(
            antibot, abUUID, abProfile, abLatency, new KeybindSetting("Key", 0)
        );
    }

    @Override
    public void onTick() {
        if (antibot.isEnabled()) {
            for (Entity entity : mc.world.getEntities()) {
                if (entity == null ||
                    !(entity instanceof PlayerEntity playerEntity))
                    continue;
                if (isBot(playerEntity)) {
                    entity.remove(Entity.RemovalReason.DISCARDED);
                    removedBots += 1;
                }
                String ending = removedBots == 1 ? "." : "s.";
                if (removedBots != oldRemovedBots)
                    ChatUtils.message(
                        "Removed " + removedBots + " Bot" + ending
                    );
                oldRemovedBots = removedBots;
            }
        }
        super.onTick();
    }

    @Override
    public void onDisable() {
        removedBots = 0;
    }

    private boolean isBot(PlayerEntity entity) {
        if ((mc.getNetworkHandler().getPlayerListEntry(entity.getUuid()) ==
                 null &&
             abUUID.isEnabled()) ||
            (mc.getNetworkHandler()
                     .getPlayerListEntry(entity.getUuid())
                     .getProfile() == null &&
             abProfile.isEnabled()) ||
            (mc.getNetworkHandler()
                     .getPlayerListEntry(entity.getUuid())
                     .getLatency() > 1 &&
             abLatency.isEnabled()))
            return true;
        return false;
    }
}
