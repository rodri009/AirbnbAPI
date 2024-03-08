package isec.airbnbapi.Data.Models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("users")
public class User {
    @Id
    private String id;

    @Field
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Field
    @NotBlank(message = "username cannot be blank")
    private String username;

    @Field
    @NotBlank(message = "password cannot be blank")
    private String password;

    @Field
    private boolean logged;

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.logged = false;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public void updateUser(User user){
        this.name = user.name;
        this.username = user.username;
        this.password = user.password;
    }
}
