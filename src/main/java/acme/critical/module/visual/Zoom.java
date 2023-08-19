package acme.critical.module.visual;

import acme.critical.mixin.SimpleOptionMixin;
import acme.critical.module.Mod;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.module.settings.NumberSetting;

public class Zoom extends Mod {
    private NumberSetting scale = new NumberSetting("Scale", 1, 10, 2, 1);
    private int origFov;
    private double origSens;

    public Zoom() {
        super("Zoom", "Zoomer lamao", Category.VISUAL);
        addSettings(scale, new KeybindSetting("Key", 0));
    }

    @Override
    public void onEnable() {
        origFov = mc.options.getFov().getValue();
        origSens = mc.options.getMouseSensitivity().getValue();

        ((SimpleOptionMixin)(Object)mc.options.getFov())
            .forceSetValue((int)(origFov / scale.getValue()));
        mc.options.getMouseSensitivity().setValue(origSens / scale.getValue());
    }

    @Override
    public void onDisable() {
        mc.options.getFov().setValue(origFov);
        mc.options.getMouseSensitivity().setValue(origSens);
    }
}
