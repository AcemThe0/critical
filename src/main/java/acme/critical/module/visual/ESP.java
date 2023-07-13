package acme.critical.module.visual;

import java.awt.Color;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import acme.critical.module.Mod;
import acme.critical.module.ModMan;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.ModeSetting;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.utils.ColorUtils;
import acme.critical.utils.FriendsUtils;

public class ESP extends Mod {
	private ModeSetting mode = new ModeSetting("Mode", "Glow", "Glow", "Walls");
	private BooleanSetting rainbow = new BooleanSetting("Rainbow", true);
	public BooleanSetting players = new BooleanSetting("Players", true);
	public BooleanSetting offensive = new BooleanSetting("Offensive", true);
	public BooleanSetting passive = new BooleanSetting("Passive", false);

	public ESP() {
		super("ESP", "Extrasensory perception!", Category.VISUAL);
		addSettings(mode, rainbow, players, offensive, passive, new KeybindSetting("Key", 0));
	}

	public String getMode() {
		return mode.getMode();
	}

	public int getColor(Entity entity) {
		if (!(entity instanceof PlayerEntity)) return ColorUtils.GetEntColor(entity);
		if (FriendsUtils.isFriend(entity)) return ColorUtils.friendColor;
		if (rainbow.isEnabled()) return ColorUtils.Rainbow();
		return new Color(255, 255, 255).getRGB();
	}

	public boolean walls_shouldRenderEntity(Entity entity) {
		if (!isEnabled() || mode.getMode() != "Walls") return false;
		if (entity == null) return false;
		return true;
	}
}
