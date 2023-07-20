package acme.critical.module.misc;

import net.minecraft.client.MinecraftClient;

import acme.critical.mixin.MinecraftClientAccessor;
import acme.critical.module.Mod;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.module.settings.NumberSetting;
import acme.critical.utils.MiscUtils;

public class Autoclicker extends Mod {
    private BooleanSetting lClick = new BooleanSetting("LClick", false);
    private NumberSetting lDelay = new NumberSetting("LDelay", 1, 60, 40, 1);
    private BooleanSetting rClick = new BooleanSetting("RClick", true);
    private NumberSetting rDelay = new NumberSetting("RDelay", 1, 60, 1, 1);

    private short lClickTimer = 0;
    private short rClickTimer = 0;

    private MinecraftClient mc = MinecraftClient.getInstance();

    public Autoclicker() {
        super(
            "Autoclicker", "Automatically click mouse buttons.", Category.MISC
        );
        addSettings(
            lClick, lDelay, rClick, rDelay, new KeybindSetting("Key", 0)
        );
    }

    @Override
    public void onTick() {
        if (lClick.isEnabled()) {
            lClickTimer += 1;
            if (lClickTimer == lDelay.getValueInt()) {
                MiscUtils.attack(true);
            }
            if (lClickTimer > lDelay.getValueInt()) {
                MiscUtils.attack(false);
                lClickTimer = 0;
            }
        }
        if (rClick.isEnabled()) {
            rClickTimer += 1;
            if (rClickTimer == rDelay.getValueInt()) {
                MiscUtils.use(true);
            }
            if (rClickTimer > rDelay.getValueInt()) {
                MiscUtils.use(false);
                rClickTimer = 0;
            }
        }
    }

    @Override
    public void onEnable() {
        lClickTimer = 0;
        MiscUtils.attack(false);
        rClickTimer = 0;
        MiscUtils.use(false);
    }

    @Override
    public void onDisable() {
        lClickTimer = 0;
        MiscUtils.attack(false);
        rClickTimer = 0;
        MiscUtils.use(false);
    }
}
