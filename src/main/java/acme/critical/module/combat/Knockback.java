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
    private NumberSetting velXZ = new NumberSetting("XZ", -100, 100, 0, 0.1);
    private NumberSetting velY = new NumberSetting("Y", -100, 100, 0, 0.1);

    public Knockback() {
        super("Knockback", "Modify taken knockback!", Category.COMBAT);
        addSettings(velXZ, velY, new KeybindSetting("Key", 0));
    }
    
    @CriticalSubscribe
    public void readPacket(EventPacket.Send event) {
        if (event.getPacket() instanceof EntityVelocityUpdateS2CPacket) {
            EntityVelocityUpdateS2CPacket packet = (EntityVelocityUpdateS2CPacket) event.getPacket();

            if (packet.getId() == mc.player.getId()) {
                float XZ = velXZ.getValueFloat()/100;
                float Y = velY.getValueFloat()/100;
                
                double packetVelX = (packet.getVelocityX()/8000d - mc.player.getVelocity().x)*XZ;
                double packetVelY = (packet.getVelocityY()/8000d - mc.player.getVelocity().y)*Y;
                double packetVelZ = (packet.getVelocityZ()/8000d - mc.player.getVelocity().z)*XZ;

                packet.velocityX = (int)(packetVelX*8000+mc.player.getVelocity().x*8000);
                packet.velocityY = (int)(packetVelY*8000+mc.player.getVelocity().y*8000);
                packet.velocityZ = (int)(packetVelZ*8000+mc.player.getVelocity().z*8000);
            }
        } else if (event.getPacket() instanceof ExplosionS2CPacket) {
            ExplosionS2CPacket packet = (ExplosionS2CPacket) event.getPacket();
            float XZ = velXZ.getValueFloat()/100;
            float Y = velY.getValueFloat()/100;

            packet.playerVelocityX = (float)(packet.getPlayerVelocityX()*XZ);
            packet.playerVelocityY = (float)(packet.getPlayerVelocityY()*Y);
            packet.playerVelocityZ = (float)(packet.getPlayerVelocityZ()*XZ);
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
