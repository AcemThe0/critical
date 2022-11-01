package acme.critical.module.movement;

import net.minecraft.util.Hand;
import acme.critical.module.Mod;
import net.minecraft.item.BlockItem;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.hit.BlockHitResult;
import acme.critical.module.settings.ModeSetting;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.KeybindSetting;

public class Scaffold extends Mod {
    public BooleanSetting lock = new BooleanSetting("Lock", true);
    public BooleanSetting swing = new BooleanSetting("Swing", true);
    public ModeSetting type = new ModeSetting("Type", "Normal", "Normal", "Mixed");

    Boolean shouldMix = false;
    int slot, mainSlot;
    int originalSlot = 0;

    public Scaffold() {
        super("Scaffold", "Places blocks under you.", Category.MOVEMENT);
        addSettings(lock, swing, type, new KeybindSetting("Key", 0));
    }


    @Override
    public void onTick() {
        BlockPos blockPos = mc.player.getBlockPos().down();

        if (!lock.isEnabled()) originalSlot = mc.player.getInventory().selectedSlot;

        for (slot = 0; slot < 9; slot++) {
            if (!shouldMix && mc.player.getInventory().getStack(slot).getItem() instanceof BlockItem) {
                mc.player.getInventory().selectedSlot = slot;
                int mainSlot = slot; slot=9;
            }
            if (type.getMode() == "Mixed" && shouldMix && slot != mainSlot && mc.player.getInventory().getStack(slot).getItem() instanceof BlockItem) mc.player.getInventory().selectedSlot = slot;
        }

        if (mc.world.getBlockState(blockPos).getMaterial().isReplaceable()) {
            mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, new BlockHitResult(Vec3d.of(blockPos), Direction.DOWN, blockPos, false));
            if (swing.isEnabled()) mc.player.swingHand(Hand.MAIN_HAND);
            shouldMix = !shouldMix;
        }

        if (!lock.isEnabled()) mc.player.getInventory().selectedSlot = originalSlot;
        super.onTick();
    }
}
