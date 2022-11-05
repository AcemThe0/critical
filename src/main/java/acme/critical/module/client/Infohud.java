package acme.critical.module.client;

import acme.critical.module.Mod;
import acme.critical.module.settings.BooleanSetting;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;

public class Infohud extends Mod {
	private BooleanSetting doDrawPos = new BooleanSetting("Draw Pos", true);
	private BooleanSetting doDrawPosAlt = new BooleanSetting("Draw Nether Pos", true);

	private MinecraftClient mc = MinecraftClient.getInstance();

	private ArrayList<String> TextLines = new ArrayList();
	private String dim = "";
	private String otherdim = "";
	private String printedPos = "";
	private String printedPosAlt = "";
	private Vec3d playerPos = new Vec3d(0, 0, 0);
	private Vec3d playerPosAlt = new Vec3d(0, 0, 0);

	public Infohud() {
		super("InfoHud", "Show additional info in hud.", Category.CLIENT);
		addSettings(doDrawPos, doDrawPosAlt);
	}

	public void onTick() {
		// format player position
		playerPos = mc.player.getPos();
		printedPos = String.format(
			"Pos: %.01f, %.01f, %.01f",
			playerPos.x, playerPos.y, playerPos.z
		);

		// format player nether position
		dim = mc.world.getRegistryKey().getValue().getPath();
		switch (dim) {
			case "the_nether":
				otherdim = "Overworld";
				break;
			case "the_end":
				otherdim = "N/A";
				break;
			default:
				otherdim = "Nether";
		}

		if (otherdim == "Nether") {
			playerPosAlt = playerPos.multiply(new Vec3d(0.125d, 1.0d, 0.125d));
		} else {
			playerPosAlt = playerPos.multiply(new Vec3d(8.0d, 1.0d, 8.0d));
		}

		printedPosAlt = String.format(
			"Pos (%s): %.01f, %.01f, %.01f",
			otherdim, playerPosAlt.x, playerPosAlt.y, playerPosAlt.z
		);

		// add everything to TextLines
		// these will be drawn in reverse order
		TextLines.clear();
		if (doDrawPosAlt.isEnabled() & otherdim != "N/A")
			TextLines.add(printedPosAlt);
		if (doDrawPos.isEnabled())
			TextLines.add(printedPos);
	}

	public void onRender2D(MatrixStack matrices, float tickDelta) {
		int scaledWidth = mc.getWindow().getScaledWidth();
		int scaledHeight = mc.getWindow().getScaledHeight();

		int i = 1;
		for (String string : TextLines) {
			mc.textRenderer.drawWithShadow(
				matrices, string,
				scaledWidth - mc.textRenderer.getWidth(string),
				scaledHeight - mc.textRenderer.fontHeight*i,
				-1
			);
			i++;
		}
	}
}
