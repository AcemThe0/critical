package acme.critical.module.visual;

import acme.critical.module.Mod;
import acme.critical.utils.ChatUtils;
import acme.critical.utils.FakePlayer;
import acme.critical.module.settings.KeybindSetting;;

public class Fakeplayer extends Mod {
    FakePlayer fakeplayer;
    
    public Fakeplayer() {
        super("Fakeplayer", "Spawns a client-side player.", Category.VISUAL);
        addSetting(new KeybindSetting("Key", 0));
    }

    @Override
    public void onEnable() {
        fakeplayer = new FakePlayer();
        fakeplayer.spawn();
    }

    @Override
    public void onDisable() {
        fakeplayer.despawn();
    }
}
