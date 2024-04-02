package isec.airbnbapi.Data.Models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.web.multipart.MultipartFile;

public class PropertyRequest {
    private String name;

    private String description;

    private Integer nrBedrooms;

    private Integer nrBeds;

    private Integer nrToilets;

    private Integer capacity; // nr de pessoas

    private Integer pricePerNight;

    private String location;

    private MultipartFile image;

    private String adminId;

    public PropertyRequest(String name, String description, Integer nrBedrooms, Integer nrBeds, Integer nrToilets, Integer capacity, Integer pricePerNight, String location, MultipartFile image, String adminId) {
        this.name = name;
        this.description = description;
        this.nrBedrooms = nrBedrooms;
        this.nrBeds = nrBeds;
        this.nrToilets = nrToilets;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
        this.location = location;
        this.image = image;
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getNrBedrooms() {
        return nrBedrooms;
    }

    public Integer getNrBeds() {
        return nrBeds;
    }

    public Integer getNrToilets() {
        return nrToilets;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Integer getPricePerNight() {
        return pricePerNight;
    }

    public String getLocation() {
        return location;
    }

    public MultipartFile getImage() {
        return image;
    }

    public String getAdminId() {
        return adminId;
    }
}
