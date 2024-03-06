package isec.airbnbapi.Controllers;

import isec.airbnbapi.Data.Models.User;
import isec.airbnbapi.Data.MongoRepositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody User user) {
        try {
            this.userRepository.save(new User(user.getName(), user.getNumber()));
            return ResponseEntity.ok("User Added!");
        } catch (Exception e) {
            System.out.println("[ERROR] - " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getUsers() {
        try {
            List<User> users = this.userRepository.findAll();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            System.out.println("[ERROR] - " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsers(@PathVariable String id) {
        try {
            Optional<User> userOpt = this.userRepository.findById(id);
            return (userOpt.isPresent() ? ResponseEntity.ok(userOpt.get()) : ResponseEntity.ok("Couldn't find any user with the id: '" + id + "'!"));
        } catch (Exception e) {
            System.out.println("[ERROR] - " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        try {
            Optional<User> userOpt = this.userRepository.findById(id);
            this.userRepository.deleteById(id);
            String msg = (userOpt.isPresent() ? "User with id '" + id + "' was deleted!" : "Couldn't find any user with the id: '" + id + "'!");
            return ResponseEntity.ok(msg);
        } catch (Exception e) {
            System.out.println("[ERROR] - " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There was an error trying to delete the user with id: '" + id + "'!");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody User user) {
        try {
            Optional<User> userOpt = this.userRepository.findById(id);
            if (userOpt.isPresent()) {
                User receivedUser = userOpt.get();
                receivedUser.updateUser(user);
                this.userRepository.save(receivedUser);
                return ResponseEntity.ok(receivedUser);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Couldn't find any user with the id: '" + id + "'!");
            }
        } catch (Exception e) {
            System.out.println("[ERROR] - " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
