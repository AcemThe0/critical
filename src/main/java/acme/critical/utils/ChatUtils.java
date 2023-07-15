package acme.critical.utils;

import net.minecraft.text.Text;
import net.minecraft.text.MutableText;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.gui.hud.ChatHud;

public class ChatUtils {
    public static String crprefix = "\u00a72[\u00a7ecritical\u00a78]\u00a7r ";
    public static String warnprefix = "\u00a72[\u00a76WARN\u00a78]\u00a7r ";
    public static String errprefix = "\u00a72[\u00a74ERR\u00a78]\u00a7r ";

    static ClientPlayNetworkHandler nh = MinecraftClient.getInstance().getNetworkHandler();

    public static void component(Text component)
    {
        ChatHud chatHud = MinecraftClient.getInstance().inGameHud.getChatHud();
        MutableText prefix = Text.literal(crprefix);
        chatHud.addMessage(prefix.append(component));
    }

    char chatPrefix = '.';

    public static void message(String message)
    {
        component(Text.literal(message));
    }

    public static void warn(String message)
    {
        message(warnprefix + message);
    }

    public static void error(String message)
    {
        message(errprefix + message);
    }

    public static void sendMsg(String message) {
        MinecraftClient.getInstance().inGameHud.getChatHud().addToMessageHistory(message);

        if (message.startsWith("/")) nh.sendChatCommand(message.substring(1));
        else nh.sendChatMessage(message);
    }
}
