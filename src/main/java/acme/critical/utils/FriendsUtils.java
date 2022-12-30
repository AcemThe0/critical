package acme.critical.utils;

import java.util.List;
import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public class FriendsUtils {
    public static ArrayList<String> friends = new ArrayList<String>();

    public static boolean isFriend(String name) {
        if (friends.indexOf(name) != -1) return true;
        return false;
    }

    public static boolean isFriend(Entity entity) {
        if (entity instanceof PlayerEntity && isFriend(entity.getName().getString())) return true;
        return false;
    }
}
