package acme.critical.module.misc;

import acme.critical.module.Mod;
import net.minecraft.entity.Entity;
import acme.critical.utils.ChatUtils;
import net.minecraft.entity.player.PlayerEntity;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.KeybindSetting;

public class Antibot extends Mod {
    private BooleanSetting announce = new BooleanSetting("Announce", true);
    int removedBots;
    int oldRemovedBots = 1337;

    public Antibot() {
        super("AntiBot", "Attempts to remove bots.", Category.MISC);
        addSettings(announce, new KeybindSetting("Key", 0));
    }

    @Override
    public void onTick() {
        for (Entity entity : mc.world.getEntities()) {
            if (entity == null || !(entity instanceof PlayerEntity playerEntity)) continue;
            if (isBot(playerEntity)) {
                entity.remove(Entity.RemovalReason.DISCARDED);
                removedBots +=1;
            }
            String ending = removedBots == 1 ? "." : "s.";
            if (announce.isEnabled() && removedBots != oldRemovedBots) ChatUtils.message("Removed " + removedBots + " Bot" + ending);
            oldRemovedBots = removedBots;
            removedBots = 0;
        }
    super.onTick();
    }

    private boolean isBot(PlayerEntity entity) {
        if (mc.getNetworkHandler().getPlayerListEntry(entity.getUuid()) == null ||
            mc.getNetworkHandler().getPlayerListEntry(entity.getUuid()).getProfile() == null ||
            mc.getNetworkHandler().getPlayerListEntry(entity.getUuid()).getLatency() > 1) return true;
        return false;
    }
}
