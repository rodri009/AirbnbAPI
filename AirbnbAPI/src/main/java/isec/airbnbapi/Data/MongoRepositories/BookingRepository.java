package isec.airbnbapi.Data.MongoRepositories;

import isec.airbnbapi.Data.Models.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingRepository extends MongoRepository<Booking, String> {
}
