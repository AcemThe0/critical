package acme.critical.module.movement;

import acme.critical.Critical;
import acme.critical.module.Mod;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import acme.critical.module.settings.ModeSetting;
import acme.critical.event.events.EventClientMove;
import acme.critical.module.settings.NumberSetting;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.event.eventbus.CriticalSubscribe;

public class Flight extends Mod {
    private ModeSetting mode = new ModeSetting("Mode", "Velocity", "Velocity", "Saki", "Elytra", "Boat", "NoClip");
    private NumberSetting speed = new NumberSetting("Speed", 0.0, 5, 0.4, 0.1);

    public Flight() {
        super("Flight", "Allows you to fly.", Category.MOVEMENT);
        addSettings(mode, speed, new KeybindSetting("Key", 0));
    }

    @Override
    public void onTick() {
        switch(mode.getMode()) {
            case "Velocity":
                mc.player.setVelocity(0, 0, 0);
                mc.player.airStrafingSpeed = speed.getValueFloat();
                if (mc.options.jumpKey.isPressed()) mc.player.setVelocity(0, speed.getValueFloat(), 0);
                if (mc.options.sneakKey.isPressed()) mc.player.setVelocity(0, speed.getValueFloat()*-1, 0);
            break;
            case "Saki":
                if (mc.options.jumpKey.isPressed()) mc.player.jump();
            break;
            case "Elytra":
            mc.player.setVelocity(0, 0, 0);
            if (mc.player.forwardSpeed != 0 || mc.player.sidewaysSpeed != 0) {
            mc.player.updateVelocity(speed.getValueFloat(), new Vec3d(mc.player.sidewaysSpeed, 0, mc.player.forwardSpeed));
            }
            if (mc.options.jumpKey.isPressed()) mc.player.setVelocity(0, speed.getValueFloat(), 0);
            if (mc.options.sneakKey.isPressed()) mc.player.setVelocity(0, speed.getValueFloat()*-1, 0);
            break;
            case "Boat":
                if(mc.player.hasVehicle()) {
                    Entity boat = mc.player.getVehicle();
                    if (mc.options.jumpKey.isPressed()) boat.addVelocity(0, speed.getValueFloat(), 0);
                }
            break;
            case "NoClip":
            if (mc.cameraEntity.isInsideWall()) mc.getCameraEntity().noClip = true;
            mc.player.setVelocity(mc.player.getVelocity().x, 0, mc.player.getVelocity().z);
            if (mc.options.jumpKey.isPressed()) mc.player.addVelocity(0, 0.5, 0);
            if (mc.options.sneakKey.isPressed()) mc.player.addVelocity(0, -0.5, 0);
            mc.player.setOnGround(false);
            mc.player.getAbilities().flying = true;
            break;
        }
        super.onTick();
    }

    @Override
    public void onEnable() {
        Critical.INSTANCE.eventBus.subscribe(this);
    }

    @Override
    public void onDisable() {
        Critical.INSTANCE.eventBus.unsubscribe(this);
    }

    @CriticalSubscribe
    public void onClientMove(EventClientMove event) {
        mc.player.noClip = true;
    }

}
