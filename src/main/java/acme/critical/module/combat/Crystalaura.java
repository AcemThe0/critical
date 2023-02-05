package acme.critical.module.combat;

import acme.critical.module.Mod;
import net.minecraft.entity.Entity;
import acme.critical.module.settings.NumberSetting;
import acme.critical.module.settings.KeybindSetting;
import net.minecraft.entity.decoration.EndCrystalEntity;

public class Crystalaura extends Mod {
    private NumberSetting speed = new NumberSetting("Speed", 1, 10, 5, 1);
    private NumberSetting range = new NumberSetting("Range", 1, 10, 5, 0.1);
    private int ticksPassed = 0;

    public Crystalaura() {
        super("Crystalaura", "Breaks crystals.", Category.COMBAT);
        addSettings(speed, range, new KeybindSetting("Key", 0));
    }
    
    @Override
    public void onTick() {
        if (ticksPassed >= speed.getValueInt()) {
            ticksPassed = 0;

            for (Entity ent : mc.world.getEntities()) {
                if (mc.player.distanceTo(ent) < range.getValueFloat() && ent instanceof EndCrystalEntity) {
                    mc.interactionManager.attackEntity(mc.player, ent);
                }
            }
        }

        ticksPassed+=1;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

}
