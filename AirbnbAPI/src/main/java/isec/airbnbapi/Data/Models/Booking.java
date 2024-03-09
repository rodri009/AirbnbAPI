package isec.airbnbapi.Data.Models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document("bookings")
public class Booking {

    @Id
    private String id;

    @Field
    @NotBlank(message = "idPropety cannot be blank")
    private String idProperty;

    @Field
    @NotBlank(message = "idUser cannot be blank")
    private String idUser;

    @Field
    private BookingStateEnum bookingState;

    @Field
    @NotNull(message = "checkinDate cannot be null")
    private Date checkinDate;

    @Field
    @NotNull(message = "checkoutDate cannot be null")
    private Date checkoutDate;

    @Field
    @NotNull(message = "totalPrice cannot be null")
    private Integer totalPrice;

    public Booking(String idProperty, String idUser, Date checkinDate, Date checkoutDate, Integer totalPrice) {
        this.idProperty = idProperty;
        this.idUser = idUser;
        this.bookingState = BookingStateEnum.PENDING;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return id;
    }
    public String getIdProperty() {
        return idProperty;
    }

    public String getIdUser() {
        return idUser;
    }

    public BookingStateEnum getBookingState() {
        return bookingState;
    }

    public Date getCheckinDate() {
        return checkinDate;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setIdProperty(String idProperty) {
        this.idProperty = idProperty;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public void setBookingState(BookingStateEnum bookingState) {
        this.bookingState = bookingState;
    }

    public void setCheckinDate(Date checkinDate) {
        this.checkinDate = checkinDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }
}
