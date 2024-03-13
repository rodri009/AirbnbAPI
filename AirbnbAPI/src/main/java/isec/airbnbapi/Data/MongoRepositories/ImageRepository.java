package isec.airbnbapi.Data.MongoRepositories;

import isec.airbnbapi.Data.Models.Booking;
import isec.airbnbapi.Data.Models.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageRepository extends MongoRepository<Image, String> {
}
