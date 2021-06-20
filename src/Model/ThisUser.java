package Model;

import Model.DataTypes.User.User;

public class ThisUser {
    private static User thisUser;
    private static byte[] profilePhotoData;
    private ThisUser() {

    }

    public static void init(User user) {
        ThisUser.thisUser = user;
    }

    public static User getThisUser() {
        return thisUser;
    }

    public static byte[] getProfilePhotoData() {
        return profilePhotoData;
    }

    public static void setProfilePhotoData(byte[] profilePhotoData) {
        ThisUser.profilePhotoData = profilePhotoData;
    }
}
