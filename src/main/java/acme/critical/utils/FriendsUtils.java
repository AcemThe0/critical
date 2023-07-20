package acme.critical.utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import acme.critical.profile.Profile;
import acme.critical.profile.files.FriendsFile;

public class FriendsUtils {
    public static ArrayList<String> friends = new ArrayList<String>();

    public static void addFriend(String name) {
        friends.add(name);
        Profile.INSTANCE.save(FriendsFile.class);
    }

    public static void delFriend(String name) {
        friends.remove(name);
        Profile.INSTANCE.save(FriendsFile.class);
    }

    public static boolean isFriend(String name) {
        if (friends.indexOf(name) != -1)
            return true;
        return false;
    }

    public static boolean isFriend(Entity entity) {
        if (entity instanceof PlayerEntity &&
            isFriend(entity.getName().getString()))
            return true;
        return false;
    }
}
