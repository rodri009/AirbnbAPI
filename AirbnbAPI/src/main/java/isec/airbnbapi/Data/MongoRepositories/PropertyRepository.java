package isec.airbnbapi.Data.MongoRepositories;

import isec.airbnbapi.Data.Models.Property;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PropertyRepository extends MongoRepository<Property, String> {
}
