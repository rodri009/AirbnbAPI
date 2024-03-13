package isec.airbnbapi.Data.Models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document("images")
public class Image {
    @Id
    private String id;

    @Field
    @NotBlank(message = "idProperty cannot be blank")
    private String idProperty;

    @Field
    @NotNull(message = "image cannot be blank")
    private byte[] image;

    public Image(String idProperty, @NotNull(message = "image cannot be blank") byte[] image) {
        this.idProperty = idProperty;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getIdProperty() {
        return idProperty;
    }

    public byte[] getImage() {
        return image;
    }

    public void setIdProperty(String idProperty) {
        this.idProperty = idProperty;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
