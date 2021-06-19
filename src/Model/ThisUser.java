package Model;

import Model.DataTypes.User.User;

public class ThisUser {
    private static User thisUser;

    private ThisUser() {

    }

    public static void init(User user) {
        ThisUser.thisUser = user;
    }

    public static User getThisUser() {
        return thisUser;
    }
}
