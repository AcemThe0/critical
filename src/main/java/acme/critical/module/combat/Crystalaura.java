package acme.critical.module.combat;

import java.util.List;
import java.util.ArrayList;
import acme.critical.module.Mod;


public class Crystalaura extends Mod {
    public Crystalaura() {
        super("Crystalaura", "Attempts to explode enemies", Category.COMBAT);
    }
/*
    @Override
    public void onTick() {
        if (this.isEnabled() && mc.world != null && mc.world.getEntities() != null) {
            List<Entity> targets = new ArrayList<>();
            Entity target;

            for (Entity entity : mc.world.getEntities()) {
                if (entity instanceof LivingEntity && entity != mc.player && !FriendsUtils.friends.contains(entity.getEntityName()) && mc.player.distanceTo(entity) <= range.getValueInt()) {
                    if (players.isEnabled() && !entity.isPlayer()) {;}
                    else {targets.add(entity);}
                }
                targets.sort(Comparator.comparingDouble(ent -> mc.player.distanceTo(entity)));
            }
        }
        super.onTick();
    }
*/
}
