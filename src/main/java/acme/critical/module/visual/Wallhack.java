package acme.critical.module.visual;

import java.util.List;
import java.util.ArrayList;
import acme.critical.module.Mod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import acme.critical.module.settings.KeybindSetting;

public class Wallhack extends Mod {
    private List<Entity> invisibleEntities = new ArrayList<>();

    public Wallhack() {
        super("Wallhack", "Renders entities through walls.", Category.VISUAL);
        addSetting(new KeybindSetting("Key", 0));
        get = this;
    }


    @Override
    public void onTick() {
        mc.world.getEntities().forEach(entity -> {
            if (entity.isInvisible() && !invisibleEntities.contains(entity)) {
                invisibleEntities.add(entity);
                entity.setInvisible(false);
            }
        });
    super.onTick();
    }

    @Override
    public void onDisable() {
        invisibleEntities.forEach(entity -> {entity.setInvisible(true);});
        invisibleEntities.clear();
    }

    public boolean shouldRenderEntity(Entity entity) {
        if (!isEnabled()) return false;
        if (entity == null) return false;
        
        return true;
    }

    public static Wallhack get;
}
