package acme.critical.module.movement;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.BlockItem;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import acme.critical.Critical;
import acme.critical.module.Mod;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.module.settings.ModeSetting;

public class Scaffold extends Mod {
    private BooleanSetting lock = new BooleanSetting("Lock", true);
    private BooleanSetting swing = new BooleanSetting("Swing", true);
    private ModeSetting type =
        new ModeSetting("Type", "Single", "Single", "Cycle");

    int slot;
    int originalSlot = 0;
    int blockSlot = 0;

    List<Integer> blocks = new ArrayList<Integer>();

    public Scaffold() {
        super("Scaffold", "Places blocks under you.", Category.MOVEMENT);
        addSettings(lock, swing, type, new KeybindSetting("Key", 0));
    }

    @Override
    public void onTick() {
        BlockPos blockPos = mc.player.getBlockPos().down();
        blocks.clear();

        if (!lock.isEnabled())
            originalSlot = mc.player.getInventory().selectedSlot;

        for (slot = 0; slot < 9; slot++) {
            // get all the blocks in the hotbar
            if (mc.player.getInventory().getStack(slot).getItem() instanceof
                BlockItem) {
                blocks.add(slot);
            }
        }

        if (mc.world.getBlockState(blockPos).isReplaceable()) {
            if (!blocks.isEmpty()) {
                if (blockSlot >= blocks.size())
                    blockSlot = 0;

                if (type.getMode() == "Single") {
                    slot = blocks.get(0);
                } else if (type.getMode() == "Cycle") {
                    slot = blocks.get(blockSlot);
                }

                mc.player.getInventory().selectedSlot = slot;
                mc.interactionManager.interactBlock(
                    mc.player, Hand.MAIN_HAND,
                    new BlockHitResult(
                        Vec3d.of(blockPos), Direction.DOWN, blockPos, false
                    )
                );
                if (swing.isEnabled())
                    mc.player.swingHand(Hand.MAIN_HAND);
                blockSlot++;
            }
        }

        if (!lock.isEnabled())
            mc.player.getInventory().selectedSlot = originalSlot;
        super.onTick();
    }
}
