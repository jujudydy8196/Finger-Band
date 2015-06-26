package ntu.csie.fingerBand.server.databean;

/**
 * Created by CarsonWang on 2015/6/16.
 */
public class User {

    private String name;
    private String id;
    private String picture;

    public User(String id, String name, String picture) {
        this.id = id;
        this.name = name;
        this.picture = picture;
    }

    @Override
    public String toString() {
        return String.format("User {id:%s, name:%s, picture_url:%s}", this.id, this.name, this.picture);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
