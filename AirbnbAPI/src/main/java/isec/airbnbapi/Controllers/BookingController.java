package isec.airbnbapi.Controllers;

import isec.airbnbapi.Data.Models.Booking;
import isec.airbnbapi.Data.Models.BookingStateEnum;
import isec.airbnbapi.Data.Models.Property;
import isec.airbnbapi.Data.Models.User;
import isec.airbnbapi.Data.MongoRepositories.BookingRepository;
import isec.airbnbapi.Data.MongoRepositories.PropertyRepository;
import isec.airbnbapi.Data.MongoRepositories.UserRepository;
import isec.airbnbapi.Utils.Encrypt;
import isec.airbnbapi.Utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("bookings")
public class BookingController {
    private final BookingRepository bookingRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;


    public BookingController(BookingRepository bookingRepository, PropertyRepository propertyRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAllBookings() {
        try {
            List<Booking> bookings = this.bookingRepository.findAll();
            return ResponseEntity.ok(bookings);
        } catch (Exception e) {
            System.out.println("[ERROR] - " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<?> getAllBookingsFromUser(@PathVariable String userId) {
        try {
            List<Booking> bookings = this.bookingRepository.findAll();
            List<Booking> bookingsUser = new ArrayList<>();
            for (Booking booking : bookings) {
                if(booking.getIdUser().equals(userId)) {
                    bookingsUser.add(booking);
                }
            }
            return ResponseEntity.ok(bookingsUser);
        } catch (Exception e) {
            System.out.println("[ERROR] - " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("property/{propertyId}")
    public ResponseEntity<?> getAllBookingsFromProperty(@PathVariable String propertyId) {
        try {
            List<Booking> bookings = this.bookingRepository.findAll();
            List<Booking> bookingsProperty = new ArrayList<>();
            for (Booking booking : bookings) {
                if(booking.getIdProperty().equals(propertyId)) {
                    bookingsProperty.add(booking);
                }
            }
            return ResponseEntity.ok(bookingsProperty);
        } catch (Exception e) {
            System.out.println("[ERROR] - " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> addBooking(@RequestBody Booking booking) {

        // check if dates are valid
        if(booking.getCheckinDate().after(booking.getCheckoutDate())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Check-in date cannot be after the check-out date");
        } else if(booking.getCheckoutDate().before(booking.getCheckinDate())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Check-out date cannot be before the check-in date");
        } else if(booking.getCheckinDate().equals(booking.getCheckoutDate())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Check-out and check-in date cannot be the same");
        }

        try {
            // check if the property exists
            Optional<Property> propertyOptional = this.propertyRepository.findById(booking.getIdProperty());
            if(!propertyOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Couldn't find any property with the id: '" + booking.getIdProperty() + "'!");
            }

            // check if the user exists
            Optional<User> userOptional = this.userRepository.findById(booking.getIdUser());
            if(!userOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Couldn't find any user with the id: '" + booking.getIdUser() + "'!");
            }

            // check if the property is available for that date
            if(!isPropertyAvailable(booking)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This property is not available for booking in those dates!");
            }

            this.bookingRepository.save(new Booking(
                    booking.getIdProperty(),
                    booking.getIdUser(),
                    booking.getCheckinDate(),
                    booking.getCheckoutDate(),
                    booking.getTotalPrice()));
            return ResponseEntity.ok("Booking created!");
        } catch (Exception e) {
            System.out.println("[ERROR] - " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("aprove/{id}")
    public ResponseEntity<?> aproveBooking(@PathVariable String id) {
        try {
            Optional<Booking> bookingOptional = this.bookingRepository.findById(id);
            if (bookingOptional.isPresent()) {
                Booking receivedBooking = bookingOptional.get();
                receivedBooking.setBookingState(BookingStateEnum.ACCEPTED);
                this.bookingRepository.save(receivedBooking);
                return ResponseEntity.ok("Booking accepted!");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Couldn't find any booking with the id: '" + id + "'!");
            }
        } catch (Exception e) {
            System.out.println("[ERROR] - " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("decline/{id}")
    public ResponseEntity<?> declineBooking(@PathVariable String id) {
        try {
            Optional<Booking> bookingOptional = this.bookingRepository.findById(id);
            if (bookingOptional.isPresent()) {
                Booking receivedBooking = bookingOptional.get();
                receivedBooking.setBookingState(BookingStateEnum.DECLINED);
                this.bookingRepository.save(receivedBooking);
                return ResponseEntity.ok("Booking declined!");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Couldn't find any booking with the id: '" + id + "'!");
            }
        } catch (Exception e) {
            System.out.println("[ERROR] - " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable String id) {
        try {
            Optional<Booking> bookingOptional = this.bookingRepository.findById(id);
            this.bookingRepository.deleteById(id);
            String msg = (bookingOptional.isPresent() ? "Booking with id '" + id + "' was deleted!" : "Couldn't find any Booking with the id: '" + id + "'!");
            return ResponseEntity.ok(msg);
        } catch (Exception e) {
            System.out.println("[ERROR] - " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There was an error trying to delete the Booking with id: '" + id + "'!");
        }
    }

    private boolean isPropertyAvailable(Booking possibleBooking) {

        try {
            List<Booking> bookings = this.bookingRepository.findAll();
            for (Booking booking : bookings) {
                if(booking.getIdProperty().equals(possibleBooking.getIdProperty())) {

                    if(booking.getBookingState().equals(BookingStateEnum.DECLINED))
                        continue;

                    boolean flag1 = Utils.isDateWithinRange(booking.getCheckinDate(), booking.getCheckoutDate(), possibleBooking.getCheckinDate());
                    boolean flag2 = Utils.isDateWithinRange(booking.getCheckinDate(), booking.getCheckoutDate(), possibleBooking.getCheckoutDate());

                    if(flag1 || flag2) return false;
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("[ERROR] - " + e);
            return false;
        }
    }

}
