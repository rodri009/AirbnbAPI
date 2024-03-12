package isec.airbnbapi.Controllers;

import isec.airbnbapi.Data.Models.*;
import isec.airbnbapi.Data.MongoRepositories.BookingRepository;
import isec.airbnbapi.Data.MongoRepositories.UserRepository;
import isec.airbnbapi.Utils.Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public UserController(UserRepository userRepository, BookingRepository bookingRepository){
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody User user) {
        try {
            Encrypt encrypt = new Encrypt();
            String encPswd = encrypt.encrypt(user.getPassword());

            this.userRepository.save(new User(user.getName(), user.getUsername(), encPswd, user.getRole()));
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
            // check if there are bookings that contains the property
            List<Booking> bookings = this.bookingRepository.findAll();
            List<String> bookingIdsToDelete = new ArrayList<>();
            for(Booking booking : bookings) {
                if(booking.getIdUser().equals(id) && !booking.getBookingState().equals(BookingStateEnum.FINISHED)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There are unfinished bookings associated with this user!");
                }
                else if (booking.getIdUser().equals(id)) {
                    bookingIdsToDelete.add(booking.getId());
                }
            }

            // removes the bookings
            for(String bookingId : bookingIdsToDelete) {
                this.bookingRepository.deleteById(bookingId);
            }
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

    @PutMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        try {
            List<User> users = this.userRepository.findAll();
            for(User user : users) {
                if(user.getUsername().equals(login.getUsername())) {
                    // encrypt password
                    Encrypt encrypt = new Encrypt();
                    String encPswd = encrypt.encrypt(login.getPassword());

                    if(user.getPassword().equals(encPswd)) {
                        // login
                        user.setLogged(true);
                        this.userRepository.save(user);
                        return ResponseEntity.ok("Login successfully!");
                    }
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong password for user with the username: '" + login.getUsername() + "'!");
                }
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Couldn't find any user with the username: '" + login.getUsername() + "'!");
        } catch (Exception e) {
            System.out.println("[ERROR] - " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/logout/{id}")
    public ResponseEntity<?> logout(@PathVariable String id) {
        try {
            Optional<User> userOpt = this.userRepository.findById(id);
            if (userOpt.isPresent()) {
                User receivedUser = userOpt.get();
                receivedUser.setLogged(false);
                this.userRepository.save(receivedUser);
                return ResponseEntity.ok("Logout successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Couldn't find any user with the id: '" + id + "'!");
            }
        } catch (Exception e) {
            System.out.println("[ERROR] - " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
