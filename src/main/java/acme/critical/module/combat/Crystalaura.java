package acme.critical.module.combat;

import acme.critical.module.Mod;
import net.minecraft.entity.Entity;
import acme.critical.utils.RotUtils;
import acme.critical.module.settings.NumberSetting;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.KeybindSetting;
import net.minecraft.entity.decoration.EndCrystalEntity;

public class Crystalaura extends Mod {
    private NumberSetting speed = new NumberSetting("Speed", 1, 10, 5, 1);
    private NumberSetting range = new NumberSetting("Range", 1, 10, 5, 0.1);
    private NumberSetting fov = new NumberSetting("FOV/2", 5, 180, 180, 0.1);
    private BooleanSetting rotation = new BooleanSetting("Rotatate", true);
    private int ticksPassed = 0;

    public Crystalaura() {
        super("Crystalaura", "Breaks crystals.", Category.COMBAT);
        addSettings(speed, range, fov, rotation, new KeybindSetting("Key", 0));
    }
    
    @Override
    public void onTick() {
        if (ticksPassed >= speed.getValueInt()) {
            ticksPassed = 0;
            breakCrystal();
        }

        ticksPassed+=1;
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    void breakCrystal() {
        for (Entity ent : mc.world.getEntities()) {
            float tfov = fov.getValueFloat()*2.f;
            float tYaw = Math.abs(RotUtils.getYawToEnt(ent)-mc.player.getYaw());
            float tPitch = Math.abs(RotUtils.getPitchToEnt(ent)- mc.player.getPitch());
            if (mc.player.distanceTo(ent) < range.getValueFloat() && ent instanceof EndCrystalEntity && tPitch<=tfov && tYaw<=tfov) {
            mc.interactionManager.attackEntity(mc.player, ent);
            }
        }
    }

}
