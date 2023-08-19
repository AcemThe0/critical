package acme.critical.profile.files;

import java.lang.StringBuilder;

import acme.critical.profile.ProfileFile;
import acme.critical.utils.FriendsUtils;

public class FriendsFile extends ProfileFile {
    public FriendsFile() { super("friends.csv"); }

    @Override
    public String onSave() {
        StringBuilder sb = new StringBuilder();
        for (String string : FriendsUtils.friends)
            sb.append(string + "\n");
        return sb.toString();
    }

    @Override
    public void onLoad(String string) {
        String[] newfriends = string.split("\n");
        FriendsUtils.friends.clear();
        for (String player : newfriends)
            FriendsUtils.friends.add(player);
    }
}
