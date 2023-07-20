package acme.critical.module.misc;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

import acme.critical.module.Mod;
import acme.critical.module.settings.KeybindSetting;
import acme.critical.utils.ChatUtils;
import acme.critical.utils.FriendsUtils;

public class Friend extends Mod {
    public static boolean friendEnabled = false;

    public Friend() {
        super("Friend", "Middle click friend.", Category.MISC);
        addSetting(new KeybindSetting("Key", 0));
    }

    @Override
    public void onEnable() {
        friendEnabled = true;
    }

    @Override
    public void onDisable() {
        friendEnabled = false;
    }

    public static void middleClickFriend() {
        Boolean added = false;
        String addedstr;
        if (mc.targetedEntity != null && mc.targetedEntity instanceof
                                             PlayerEntity) {
            String entityName = mc.targetedEntity.getEntityName();

            if (!FriendsUtils.friends.contains(entityName)) {
                FriendsUtils.addFriend(entityName);
                added = true;
            } else if (FriendsUtils.friends.contains(entityName)) {
                FriendsUtils.delFriend(entityName);
            }

            addedstr = added ? " \u00a7aAdded" : " \u00a74Removed";
            ChatUtils.message(entityName + addedstr);
        } else {
            ChatUtils.error("Look at player!");
        }
    }
}
