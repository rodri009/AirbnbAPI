package isec.airbnbapi.Data.Models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Document("properties")
public class Property {
    @Id
    private String id;

    @Field
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Field
    @NotBlank(message = "Description cannot be blank")
    private String description;

    @Field
    @NotNull(message = "Number cannot be null")
    private Integer nrBedrooms;

    @Field
    @NotNull(message = "Number cannot be null")
    private Integer nrBeds;

    @Field
    @NotNull(message = "Number cannot be null")
    private Integer nrToilets;

    @Field
    @NotNull(message = "Number cannot be null")
    private Integer capacity; // nr de pessoas

    @Field
    @NotNull(message = "Number cannot be null")
    private Integer pricePerNight;

    @Field
    @NotBlank(message = "Location cannot be blank")
    private String location;

    @Field
    @NotNull(message = "image cannot be blank")
    private byte[] image;

    @Field
    @NotBlank(message = "adminId cannot be blank")
    private String adminId;

    public Property(String name, String description, Integer nrBedrooms, Integer nrBeds, Integer nrToilets, Integer capacity, Integer pricePerNight, String location, byte[] image, String adminId) {
        super();
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

    public String getId() {
        return id;
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

    public byte[] getImage() {
        return image;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNrBedrooms(Integer nrBedrooms) {
        this.nrBedrooms = nrBedrooms;
    }

    public void setNrBeds(Integer nrBeds) {
        this.nrBeds = nrBeds;
    }

    public void setNrToilets(Integer nrToilets) {
        this.nrToilets = nrToilets;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void setPricePerNight(Integer pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    @Override
    public String toString() {
        return "Property{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", nrBedrooms=" + nrBedrooms +
                ", nrBeds=" + nrBeds +
                ", nrToilets=" + nrToilets +
                ", capacity=" + capacity +
                ", pricePerNight=" + pricePerNight +
                ", location='" + location + '\'' +
                '}';
    }

    public void updateProperty(Property property) {
        this.name = property.name;
        this.description = property.description;
        this.nrBedrooms = property.nrBedrooms;
        this.nrBeds = property.nrBeds;
        this.nrToilets = property.nrToilets;
        this.capacity = property.capacity;
        this.pricePerNight = property.pricePerNight;
        this.location = property.location;
        this.adminId = property.adminId;
    }
}
