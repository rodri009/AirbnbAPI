package isec.airbnbapi.Controllers;

import isec.airbnbapi.Data.Models.Booking;
import isec.airbnbapi.Data.Models.BookingStateEnum;
import isec.airbnbapi.Data.Models.Image;
import isec.airbnbapi.Data.Models.User;
import isec.airbnbapi.Data.MongoRepositories.ImageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("images")
public class ImageController {
    private final ImageRepository imageRepository;

    public ImageController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @PostMapping("property/{idProperty}")
    public ResponseEntity<String> addImage(@RequestBody MultipartFile file, @PathVariable String idProperty) {
        try {
            byte[] fileContent = file.getBytes();

            this.imageRepository.save(new Image(idProperty, fileContent));
            return ResponseEntity.ok("image Added!");
        } catch (Exception e) {
            System.out.println("[ERROR] - " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("property/{idProperty}")
    public ResponseEntity<?> getImagesFromProperty(@PathVariable String idProperty) {
        try {
            List<Image> images = this.imageRepository.findAll();
            List<Image> propertyImages = new ArrayList<>();
            for(Image image : images) {
                if(image.getIdProperty().equals(idProperty)) {
                    propertyImages.add(image);
                }
            }
            return ResponseEntity.ok(propertyImages);
        } catch (Exception e) {
            System.out.println("[ERROR] - " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getImages() {
        try {
            List<Image> images = this.imageRepository.findAll();

            return ResponseEntity.ok(images);
        } catch (Exception e) {
            System.out.println("[ERROR] - " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable String id) {
        try {
            Optional<Image> imageOptional = this.imageRepository.findById(id);
            this.imageRepository.deleteById(id);
            String msg = (imageOptional.isPresent() ? "Image with id '" + id + "' was deleted!" : "Couldn't find any image with the id: '" + id + "'!");
            return ResponseEntity.ok(msg);
        } catch (Exception e) {
            System.out.println("[ERROR] - " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There was an error trying to delete the image with id: '" + id + "'!");
        }
    }
}
