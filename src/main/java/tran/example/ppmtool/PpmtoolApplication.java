package tran.example.ppmtool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class PpmtoolApplication {

    public static void main(String[] args) {
        for(Map.Entry<Object, Object> entry: System.getProperties().entrySet()) {
            System.out.println(entry.getKey() + " ==> " + entry.getValue());
        }

        for(Map.Entry<String, String> entry: System.getenv().entrySet()) {
            System.out.println(entry.getKey() + " ==> " + entry.getValue());
        }
        SpringApplication.run(PpmtoolApplication.class, args);
    }

}
