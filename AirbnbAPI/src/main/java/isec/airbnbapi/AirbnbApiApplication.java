package isec.airbnbapi;

import isec.airbnbapi.Data.Models.User;
import isec.airbnbapi.Data.MongoRepositories.UserRepository;
import isec.airbnbapi.Utils.Encrypt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootApplication
@EnableMongoRepositories
public class AirbnbApiApplication {

    private final UserRepository userRepository;

    public AirbnbApiApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AirbnbApiApplication.class, args);
    }

}
