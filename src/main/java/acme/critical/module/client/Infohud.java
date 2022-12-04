package acme.critical.module.client;

import java.util.ArrayList;
import net.minecraft.text.Text;
import acme.critical.module.Mod;
import net.minecraft.entity.Entity;
import net.minecraft.world.GameMode;
import acme.critical.utils.ChatUtils;
import net.minecraft.util.math.Vec3d;
import net.minecraft.text.MutableText;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.network.PlayerListEntry;
import acme.critical.module.settings.BooleanSetting;

public class Infohud extends Mod {
	private BooleanSetting doDrawPos = new BooleanSetting("Pos", true);
	private BooleanSetting doDrawPosAlt = new BooleanSetting("Nether Pos", true);
	private BooleanSetting doPing = new BooleanSetting("Ping", true);
	private BooleanSetting doSpeed = new BooleanSetting("Speed", true);
	public static BooleanSetting doTablist = new BooleanSetting("Gamemodes", true);

	private MinecraftClient mc = MinecraftClient.getInstance();

	public static ArrayList<String> spectators = new ArrayList<String>();
	private ArrayList<String> TextLines = new ArrayList();
	private String dim = "";
	private String otherdim = "";
	private String printedPos = "";
	private String printedPosAlt = "";
	private String printedSpeed = "";
	private String printedPing = "";
	private Vec3d playerPos = new Vec3d(0, 0, 0);
	private Vec3d playerPosAlt = new Vec3d(0, 0, 0);
	private Vec3d playerVec = new Vec3d(0, 0, 0);

	public Infohud() {
		super("InfoHud", "Show additional info in hud.", Category.CLIENT);
		addSettings(doDrawPos, doDrawPosAlt, doSpeed, doPing, doTablist);
	}

	public void onTick() {
		// format player position
		playerPos = mc.player.getPos();
		printedPos = String.format(
			"Pos: %.01f, %.01f, %.01f",
			playerPos.x, playerPos.y, playerPos.z
		);

		// format player nether position
		// strange switch case should hopefully prevent issues with servers that
		// change the overworld DIM name
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

		// format player speed
		playerVec = new Vec3d(
			playerPos.x - mc.player.prevX,
			0,
			playerPos.z - mc.player.prevZ
		).multiply(20);
		printedSpeed = String.format(
			"Speed: %.01f",
			Math.abs(Math.sqrt(playerVec.x * playerVec.x + playerVec.z * playerVec.z))
		);

		// format player ping
		printedPing = String.format("Ping: %d", mc.getNetworkHandler().getPlayerListEntry(mc.player.getUuid()) == null ? 0 : mc.getNetworkHandler().getPlayerListEntry(mc.player.getUuid()).getLatency());

		// add everything to TextLines
		// these will be drawn in reverse order
		TextLines.clear();
		if (doDrawPosAlt.isEnabled() & otherdim != "N/A")
			TextLines.add(printedPosAlt);
		if (doDrawPos.isEnabled())
			TextLines.add(printedPos);
		if (doSpeed.isEnabled())
			TextLines.add(printedSpeed);
		if (doPing.isEnabled())
			TextLines.add(printedPing);
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

	public Text displayGamemode(PlayerListEntry player) {
		Text name = player.getDisplayName();
		name = name==null ? Text.literal(player.getProfile().getName()) : player.getDisplayName();
		GameMode gamemode = player.getGameMode();
		String gamemodeText = "???";

		if (gamemode != null) {
			gamemodeText = switch (gamemode) {
				case SPECTATOR -> "SP";
				case SURVIVAL -> "SV";
				case CREATIVE -> "CR";
				case ADVENTURE -> "AD";
			};
		}
		MutableText text = Text.literal("");
		text.append(name);
		text.append(" [" + gamemodeText + "]");
		name = text;

		return name;
		
	}
}
