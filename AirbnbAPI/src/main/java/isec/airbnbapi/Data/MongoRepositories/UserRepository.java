package isec.airbnbapi.Data.MongoRepositories;

import isec.airbnbapi.Data.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

}
