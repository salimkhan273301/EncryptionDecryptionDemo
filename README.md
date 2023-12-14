import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "myapp")
public class MyAppConfig {
    private String username;
    private String password;

    // Getters and setters

    // Use getters in your application where you need username and password
}


myapp.username=myUsername
myapp.password=myPassword


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    private final MyAppConfig myAppConfig;

    @Autowired
    public MyController(MyAppConfig myAppConfig) {
        this.myAppConfig = myAppConfig;
    }

    @GetMapping("/credentials")
    public String getCredentials() {
        return "Username: " + myAppConfig.getUsername() + ", Password: " + myAppConfig.getPassword();
    }
}


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MySpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(MySpringBootApplication.class, args);
    }
}
