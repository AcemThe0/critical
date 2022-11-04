package acme.critical.module.visual;

import acme.critical.module.Mod;
import acme.critical.utils.FakePlayer;

public class Fakeplayer extends Mod {
    private FakePlayer fakeplayer;
    public Fakeplayer() {
        super("Fakeplayer", "Spawns a client-side player.", Category.VISUAL);
    }

    @Override
    public void onEnable() {
        fakeplayer = new FakePlayer();
    }

    @Override
    public void onDisable() {
        fakeplayer.despawn();
    }
}
