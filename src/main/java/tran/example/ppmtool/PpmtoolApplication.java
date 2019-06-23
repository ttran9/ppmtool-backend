package tran.example.ppmtool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PpmtoolApplication {

    public static void main(String[] args) {
        if(System.getProperty("JWT_PRIVATE_KEY") == null) {
            System.out.println("we have a problem!!!!>!!");
        }
        SpringApplication.run(PpmtoolApplication.class, args);
    }

}
