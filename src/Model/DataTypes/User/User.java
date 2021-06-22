package Model.DataTypes.User;

import Model.DataTypes.Post.Posts;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class User implements Serializable {
    public static final long serialVersionUID = 500000L;
    private final String username;
    private String password;
    private String firstName;
    private String lastName;
    private String birthDate;
    private Gender gender;
    private boolean hasPhoto;
    private String photoFormat;
    private int lastPostIndex = 0;
    private Map<Integer, Posts> posts = new ConcurrentHashMap<>();
    private Map<String, User> followers = new ConcurrentHashMap<>();
    private Map<String, User> followings = new ConcurrentHashMap<>();

    public User(String username, String password, String firstName, String lastName, String birthDate, Gender gender, boolean hasPhoto, Map<Integer, Posts> posts,
                Map<String, User> followers, Map<String, User> followings, int lastPostIndex) {
        this(username, password, firstName, lastName, birthDate, gender, hasPhoto);
        this.posts = posts;
        this.followers = followers;
        this.followings = followings;
        this.lastPostIndex = lastPostIndex;
    }

    public User(String username, String password, String firstName, String lastName, String birthDate, Gender gender, boolean hasPhoto) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.hasPhoto = hasPhoto;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean hasPhoto() {
        return hasPhoto;
    }

    public void setHasPhoto(boolean hasPhoto) {
        this.hasPhoto = hasPhoto;
    }

    public void addPost(Posts post) {
        post.setIndex(lastPostIndex);
        posts.put(lastPostIndex, post);
        lastPostIndex++;
    }

    public void addFollower(User user) {
        followers.put(user.getUsername(), user);
    }

    public void addFollowing(User user) {
        followings.put(user.getUsername(), user);
    }

    public Map<Integer, Posts> getPosts() {
        return posts;
    }

    public Map<String, User> getFollowers() {
        return followers;
    }

    public Map<String, User> getFollowings() {
        return followings;
    }

    public String getPhotoFormat() {
        return photoFormat;
    }

    public void setPhotoFormat(String photoFormat) {
        this.photoFormat = photoFormat;
    }


    public int getLastPostIndex() {
        return lastPostIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return hasPhoto == user.hasPhoto && getLastPostIndex() == user.getLastPostIndex() && getUsername().equals(user.getUsername()) && getPassword().equals(user.getPassword()) && Objects.equals(getFirstName(), user.getFirstName()) && Objects.equals(getLastName(), user.getLastName()) && Objects.equals(getBirthDate(), user.getBirthDate()) && getGender() == user.getGender() && Objects.equals(getPhotoFormat(), user.getPhotoFormat());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), getFirstName(), getLastName(), getBirthDate(), getGender(), hasPhoto, getPhotoFormat(), getLastPostIndex());
    }
}
