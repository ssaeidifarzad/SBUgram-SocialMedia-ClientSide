package Model;

import Model.DataTypes.User.SafeUserData;

import java.util.HashMap;
import java.util.Map;

public class OtherUser {
    private static final Map<String, SafeUserData> safeUserDataMap = new HashMap<>();
    private final SafeUserData safeUser;

    public OtherUser(SafeUserData safeUser) {
        this.safeUser = safeUser;
        safeUserDataMap.put(safeUser.getUsername(), safeUser);
    }

    public SafeUserData getSafeUser() {
        return safeUser;
    }

    public static Map<String, SafeUserData> getSafeUserDataMap() {
        return safeUserDataMap;
    }
}
