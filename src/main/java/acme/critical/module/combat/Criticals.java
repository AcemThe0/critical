package acme.critical.module.combat;

import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

import acme.critical.Critical;
import acme.critical.event.eventbus.CriticalSubscribe;
import acme.critical.event.events.EventPacket;
import acme.critical.module.Mod;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.module.settings.ModeSetting;

import io.netty.buffer.Unpooled;

public class Criticals extends Mod {
    private ModeSetting mode =
        new ModeSetting("Type", "Packet", "Packet", "Jump", "MiniJump");

    public Criticals() {
        super("Criticals", "Attempts to deal critical hits.", Category.COMBAT);
        addSettings(mode, new KeybindSetting("Key", 0));
    }

    @CriticalSubscribe
    public void sendPacket(EventPacket.Send event) {
        if (event.getPacket() instanceof PlayerInteractEntityC2SPacket &&
            mc.player.isOnGround()) {
            PlayerInteractEntityC2SPacket packet =
                (PlayerInteractEntityC2SPacket)event.getPacket();
            if (getInteractType(packet) == interactType.ATTACK) {
                doCrit(mode.getMode());
            }
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

    private void doCrit(String mode) {
        double x = mc.player.getX();
        double y = mc.player.getY();
        double z = mc.player.getZ();

        switch (mode) {
        case "Packet":
            doPos(x, y + 0.0625D, z, true);
            doPos(x, y, z, false);
            doPos(x, y + 1.1E-5D, z, false);
            doPos(x, y, z, false);
            break;

        case "Jump":
            mc.player.networkHandler.sendPacket(
                new PlayerMoveC2SPacket.PositionAndOnGround(
                    x, y + 0.42, z, false
                )
            );
            mc.player.networkHandler.sendPacket(
                new PlayerMoveC2SPacket.PositionAndOnGround(
                    x, y + 0.65, z, false
                )
            );
            mc.player.networkHandler.sendPacket(
                new PlayerMoveC2SPacket.PositionAndOnGround(
                    x, y + 0.72, z, false
                )
            );
            mc.player.networkHandler.sendPacket(
                new PlayerMoveC2SPacket.PositionAndOnGround(
                    x, y + 0.53, z, false
                )
            );
            mc.player.networkHandler.sendPacket(
                new PlayerMoveC2SPacket.PositionAndOnGround(
                    x, y + 0.32, z, false
                )
            );
            break;

        case "MiniJump":
            mc.player.networkHandler.sendPacket(
                new PlayerMoveC2SPacket.PositionAndOnGround(
                    x, y + 0.0773, z, false
                )
            );
            mc.player.networkHandler.sendPacket(
                new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, false)
            );
            break;
        }
    }

    public void doPos(double x, double y, double z, boolean onGround) {
        mc.player.networkHandler.sendPacket(
            new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, onGround)
        );
    }

    public static interactType
    getInteractType(PlayerInteractEntityC2SPacket packet) {
        PacketByteBuf packetBuf = new PacketByteBuf(Unpooled.buffer());
        packet.write(packetBuf);

        packetBuf.readVarInt();
        return packetBuf.readEnumConstant(interactType.class);
    }

    public enum interactType { ATTACK, INTERACT, INTERACT_AT }
}
