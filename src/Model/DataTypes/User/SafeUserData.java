package Model.DataTypes.User;

import Model.DataTypes.Post.Posts;

import java.io.Serializable;
import java.util.ArrayList;

public class SafeUserData implements Serializable {
    public static final long serialVersionUID = 98700000L;

    private final String username;
    private final String firstName;
    private final String lastName;
    private final String birthDate;
    private final boolean hasPhoto;
    private String photoFormat;
    private final ArrayList<Posts> posts;

    public SafeUserData(String username, String firstName, String lastName, String birthDate, boolean hasPhoto, ArrayList<Posts> posts) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.hasPhoto = hasPhoto;
        this.posts = posts;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public boolean isHasPhoto() {
        return hasPhoto;
    }

    public String getPhotoFormat() {
        return photoFormat;
    }

    public ArrayList<Posts> getPosts() {
        return posts;
    }

    public void setPhotoFormat(String photoFormat) {
        this.photoFormat = photoFormat;
    }
}