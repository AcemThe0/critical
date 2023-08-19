package acme.critical.module.combat;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;

import acme.critical.module.Mod;
import acme.critical.module.settings.KeybindSetting;

public class Totem extends Mod {
    int totems;

    public Totem() {
        super("Totem", "Replaces your totem.", Category.COMBAT);
        addSetting(new KeybindSetting("Key", 0));
    }

    @Override
    public void onTick() {
        int i;
        Boolean found = false;
        Item itm = Items.TOTEM_OF_UNDYING;

        if (!mc.player.getOffHandStack().getItem().equals(itm)) {
            for (i = 9; i <= 44; i++) {
                if (mc.player.getInventory().getStack(i).getItem().equals(itm
                    )) {
                    found = true;
                    break;
                }
            }
            if (found) {
                mc.interactionManager.clickSlot(
                    mc.player.currentScreenHandler.syncId, i, 0,
                    SlotActionType.PICKUP, mc.player
                );
                mc.interactionManager.clickSlot(
                    mc.player.currentScreenHandler.syncId, 45, 0,
                    SlotActionType.PICKUP, mc.player
                );
            }
        }
        super.onTick();
    }
}
