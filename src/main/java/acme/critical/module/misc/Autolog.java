package acme.critical.module.misc;

import net.minecraft.network.packet.s2c.play.DisconnectS2CPacket;
import net.minecraft.text.Text;

import acme.critical.module.Mod;
import acme.critical.module.settings.BooleanSetting;
import acme.critical.module.settings.NumberSetting;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.utils.ChatUtils;

public class Autolog extends Mod {
	public NumberSetting logHealth = new NumberSetting("LogHealth", 0, 20, 15, 1);

	public Autolog() {
		super("Autolog", "Disconnect when conditions are met.", Category.MISC);
		addSettings(logHealth, new KeybindSetting("Key", 0));
	}

	public void onTick() {
		String logMessage = shouldLog();
		if (logMessage == null) return;
		
		toggle();
		mc.getNetworkHandler().onDisconnect(new DisconnectS2CPacket(Text.literal(
			ChatUtils.crprefix + "Autolog: " + logMessage
		)));
	}

	private String shouldLog() {
		if (mc.player.getHealth() < logHealth.getValueInt())
			return "Health < " + logHealth.getValueInt();
		return null;
	}
}
