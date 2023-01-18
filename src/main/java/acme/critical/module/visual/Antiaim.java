package acme.critical.module.visual;

import acme.critical.Critical;
import acme.critical.module.Mod;
import acme.critical.event.events.EventPacket;
import acme.critical.module.settings.ModeSetting;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.event.eventbus.CriticalSubscribe;
import acme.critical.mixin.PlayerPositionLookS2CPacketAccessor;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;


public class Antiaim extends Mod {
    private ModeSetting mode = new ModeSetting("Mode", "Behind", "Behind", "Spin", "Headless");
    private BooleanSetting spin = new BooleanSetting("Spin", true);
    private BooleanSetting noForce = new BooleanSetting("NoForce", true); //I need better names and or descriptions for settings
    int rotation = 0;

    public Antiaim() {
        super("AntiAim", "Moves your head.", Category.VISUAL);
        addSettings(mode, spin, noForce, new KeybindSetting("Key", 0));
    }

    @CriticalSubscribe
    public void receivePacket(EventPacket.Read event) {
        if (event.packet instanceof PlayerPositionLookS2CPacket) {
            ((PlayerPositionLookS2CPacketAccessor) event.packet).setYaw(mc.player.getYaw());
            ((PlayerPositionLookS2CPacketAccessor) event.packet).setPitch(mc.player.getPitch());
        }
    }

    @Override
    public void onTick() {
        if (spin.isEnabled()) {
            switch (mode.getMode()) {
                case "Behind":
                    float actualYaw = mc.player.getYaw();
                    mc.player.setHeadYaw(actualYaw+180);
                    mc.player.setBodyYaw(actualYaw+180);
                    mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(actualYaw+180, 90, mc.player.isOnGround()));
                break;
                case "Spin":
                    if (rotation < 360) {rotation += 45;}
                    else {rotation = 0;};
                    mc.player.setHeadYaw(rotation);
                    mc.player.setBodyYaw(rotation);
                    mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(rotation, mc.player.getPitch(), mc.player.isOnGround()));
                break;
                case "Headless":
                    mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(mc.player.getYaw(), 180, mc.player.isOnGround()));
                break;
            }
        }
    }

    @Override
    public void onEnable() {
        if(noForce.isEnabled()) Critical.INSTANCE.eventBus.subscribe(this);
    }

    @Override
    public void onDisable() {
        Critical.INSTANCE.eventBus.unsubscribe(this);
    }
}
