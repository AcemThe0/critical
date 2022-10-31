package acme.critical.module.movement;

import net.minecraft.util.Hand;
import acme.critical.module.Mod;
import net.minecraft.item.BlockItem;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.hit.BlockHitResult;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.KeybindSetting;

public class Scaffold extends Mod {
    public BooleanSetting lock = new BooleanSetting("Lock", true);
    public BooleanSetting swing = new BooleanSetting("Swing", true);

    public Scaffold() {
        super("Scaffold", "Places blocks under you.", Category.MOVEMENT);
        addSettings(lock, swing, new KeybindSetting("Key", 0));
    }

    @Override
    public void onTick() {
        int originalSlot = 0;
        BlockPos blockPos = mc.player.getBlockPos().down();

        if (!lock.isEnabled()) originalSlot = mc.player.getInventory().selectedSlot;

        for (int i = 0; i < 9; i++) {
            if (mc.player.getInventory().getStack(i).getItem() instanceof BlockItem) mc.player.getInventory().selectedSlot = i;
        }
        if (mc.world.getBlockState(blockPos).getMaterial().isReplaceable()) {
            mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, new BlockHitResult(Vec3d.of(blockPos), Direction.DOWN, blockPos, false));
            if (swing.isEnabled()) mc.player.swingHand(Hand.MAIN_HAND);
        }

        if (!lock.isEnabled()) mc.player.getInventory().selectedSlot = originalSlot;
        super.onTick();
    }
}
