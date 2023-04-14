package acme.critical.module.combat;

import acme.critical.Critical;
import acme.critical.module.Mod;
import acme.critical.event.events.EventPacket;
import acme.critical.module.settings.NumberSetting;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.event.eventbus.CriticalSubscribe;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;

public class Knockback extends Mod {
    public Knockback() {
        super("Knockback", "Modify taken knockback!", Category.COMBAT);
        addSetting( new KeybindSetting("Key", 0));
    }
    
    @CriticalSubscribe
    public void readPacket(EventPacket.Send event) {
        if (event.getPacket() instanceof EntityVelocityUpdateS2CPacket) {
            EntityVelocityUpdateS2CPacket packet = (EntityVelocityUpdateS2CPacket) event.getPacket();

            if (packet.getId() == mc.player.getId()) {
                packet.velocityX = 0;
                packet.velocityY = 0;
                packet.velocityZ = 0;
            }
        } else if (event.getPacket() instanceof ExplosionS2CPacket) {
            ExplosionS2CPacket packet = (ExplosionS2CPacket) event.getPacket();

            packet.playerVelocityX = 0.0f;
            packet.playerVelocityY = 0.0f;
            packet.playerVelocityZ = 0.0f;
        }
    }

    @Override
    public void onEnable() {
        Critical.INSTANCE.eventBus.subscribe(this);
    }

    @Override
    public void onDisable() {
        Critical.INSTANCE.eventBus.unsubscribe(this);
    }

}
