package fingerBand;

/**
 * Created by CarsonWang on 2015/6/16.
 */
public class User {

    private String name;
    private String id;

    public User(String id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("User {id:%s, name:%s}", this.id, this.name);
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

}
