package isec.airbnbapi.Data.Models;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "email cannot be blank")
    private String email;

    @Field
    @NotBlank(message = "password cannot be blank")
    private String password;

    @Field
    private boolean logged;

    @Field
    private RoleEnum role;

    public User(String name, String username, String email, String password, RoleEnum role) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.logged = false;
        this.role = role;
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

    public RoleEnum getRole() {
        return role;
    }

    public String getEmail() {
        return email;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void updateUser(User user){
        this.name = user.name;
        this.username = user.username;
        this.password = user.password;
    }
}
