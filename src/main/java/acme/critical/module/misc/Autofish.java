package acme.critical.module.misc;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.util.math.Vec3d;

import acme.critical.Critical;
import acme.critical.event.eventbus.CriticalSubscribe;
import acme.critical.event.events.EventPacket;
import acme.critical.mixinterface.KeyBindingInterface;
import acme.critical.module.Mod;
import acme.critical.module.settings.NumberSetting;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.utils.ChatUtils;

public class Autofish extends Mod {
	private static enum FishStates {
		IDLE, CAST, FISH, REEL
	}
	FishStates curState = FishStates.IDLE;

	private int timer = 0;

	private NumberSetting maxTime = new NumberSetting("Max time", 1, 60, 23, 1);
	private NumberSetting detectRange = new NumberSetting("Detect Rad", 0, 15, 9, 0.5);

	public Autofish() {
		super("Autofish", "Automatically fish.", Category.MISC);
		addSettings(maxTime, detectRange, new KeybindSetting("Key", 0));
	}

	@Override
	public void onTick() {
		if (curState == FishStates.CAST) {
			timer++;
			if (timer == 15) {
				mc.options.useKey.setPressed(true);
			} else if (timer > 15) {
				((KeyBindingInterface) (Object) mc.options.useKey).release();
				timer = 0;
				curState = FishStates.FISH;
			}
		}
		if (curState == FishStates.FISH) {
			timer++;
			if (timer > maxTime.getValueInt()*20) {
				curState = FishStates.REEL;
				timer = 0;
			}
		}
		if (curState == FishStates.REEL) {
			timer++;
			if (timer == 5) {
				mc.options.useKey.setPressed(true);
			} else if (timer > 5) {
				((KeyBindingInterface) (Object) mc.options.useKey).release();
				timer = 0;
				curState = FishStates.CAST;
			}
		}
	}

	@Override
	public void onEnable() {
		Critical.INSTANCE.eventBus.subscribe(this);
		curState = FishStates.CAST;
	}

	@Override
	public void onDisable() {
		Critical.INSTANCE.eventBus.unsubscribe(this);
		curState = FishStates.IDLE;
	}

	@CriticalSubscribe
	public void recievePacket(EventPacket.Read event) {
		if (curState != FishStates.FISH || !(event.getPacket() instanceof PlaySoundS2CPacket)) return;
		PlaySoundS2CPacket packet = (PlaySoundS2CPacket) event.getPacket();
		if(!packet.getSound().getId().getPath().equals("entity.fishing_bobber.splash")) return;

		Vec3d vec = new Vec3d(packet.getX(), packet.getY(), packet.getZ());
		double dist = vec.distanceTo(mc.player.fishHook.getPos());

		if (dist <= detectRange.getValue()) {
			timer = 0;
			curState = FishStates.REEL;
		}
	}
}
