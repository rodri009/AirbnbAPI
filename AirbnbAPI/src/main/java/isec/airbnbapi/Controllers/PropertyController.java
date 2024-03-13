package isec.airbnbapi.Controllers;

import isec.airbnbapi.Data.Models.*;
import isec.airbnbapi.Data.MongoRepositories.BookingRepository;
import isec.airbnbapi.Data.MongoRepositories.ImageRepository;
import isec.airbnbapi.Data.MongoRepositories.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("properties")
public class PropertyController {
    private final PropertyRepository propertyRepository;
    private final BookingRepository bookingRepository;
    private final ImageRepository imageRepository;
    @Autowired
    public PropertyController(PropertyRepository propertyRepository, BookingRepository bookingRepository, ImageRepository imageRepository) {
        this.propertyRepository = propertyRepository;
        this.bookingRepository = bookingRepository;
        this.imageRepository = imageRepository;
    }

    @PostMapping
    public ResponseEntity<String> addProperty(@RequestBody Property property) {
        try {
            this.propertyRepository.save(new Property(
                        property.getName(),
                        property.getDescription(),
                        property.getNrBedrooms(),
                        property.getNrBeds(),
                        property.getNrToilets(),
                        property.getCapacity(),
                        property.getPricePerNight(),
                        property.getLocation()
                    )
            );

            return ResponseEntity.ok("Property Added!");
        } catch (Exception e) {
            System.out.println("[ERROR] - " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @GetMapping
    public ResponseEntity<?> getProperties() {
        try {
            List<Property> properties = this.propertyRepository.findAll();
            return ResponseEntity.ok(properties);
        } catch (Exception e) {
            System.out.println("[ERROR] - " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProperty(@PathVariable String id) {
        try {
            Optional<Property> propertyOptional = this.propertyRepository.findById(id);
            return (propertyOptional.isPresent() ? ResponseEntity.ok(propertyOptional.get()) : ResponseEntity.ok("Couldn't find any property with the id: '" + id + "'!"));
        } catch (Exception e) {
            System.out.println("[ERROR] - " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProperty(@PathVariable String id) {
        try {
            // delete the bookings that contains the property
            List<Booking> bookings = this.bookingRepository.findAll();
            List<String> bookingIdsToDelete = new ArrayList<>();
            for(Booking booking : bookings) {
                if(booking.getIdProperty().equals(id) && !booking.getBookingState().equals(BookingStateEnum.FINISHED)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There are unfinished bookings associated with this property!");
                }
                else if (booking.getIdProperty().equals(id)) {
                    bookingIdsToDelete.add(booking.getId());
                }
            }

            // removes the bookings
            for(String bookingId : bookingIdsToDelete) {
                this.bookingRepository.deleteById(bookingId);
            }

            // removes the images associated
            List<Image> images = this.imageRepository.findAll();
            for(Image image : images) {
                if(image.getIdProperty().equals(id)) {
                    this.imageRepository.deleteById(image.getId());
                }
            }

            Optional<Property> propertyOptional = this.propertyRepository.findById(id);
            this.propertyRepository.deleteById(id);
            String msg = (propertyOptional.isPresent() ? "Property with id '" + id + "' was deleted!" : "Couldn't find any property with the id: '" + id + "'!");
            return ResponseEntity.ok(msg);
        } catch (Exception e) {
            System.out.println("[ERROR] - " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There was an error trying to delete the property with id: '" + id + "'!");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProperty(@PathVariable String id, @RequestBody Property property) {
        try {
            Optional<Property> propertyOptional = this.propertyRepository.findById(id);
            if (propertyOptional.isPresent()) {
                Property receivedProperty = propertyOptional.get();
                receivedProperty.updateProperty(property);
                this.propertyRepository.save(receivedProperty);
                return ResponseEntity.ok("Property updated!");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Couldn't find any property with the id: '" + id + "'!");
            }
        } catch (Exception e) {
            System.out.println("[ERROR] - " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
