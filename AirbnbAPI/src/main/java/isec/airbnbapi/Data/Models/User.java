package isec.airbnbapi.Data.Models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("users")
public class User {
    @Id
    private String id;

    @Field
    private String name;

    @Field
    private Integer number;

    public User(String name, Integer number) {
        super();
        this.name = name;
        this.number = number;
    }


    //SETTERS
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    //GETTERS
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getNumber() {
        return number;
    }

    public void updateUser(User user){
        this.name = user.name;
        this.number = user.number;
    }
}
