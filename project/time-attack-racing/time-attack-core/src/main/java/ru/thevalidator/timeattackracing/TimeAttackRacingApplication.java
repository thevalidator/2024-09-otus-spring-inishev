package ru.thevalidator.timeattackracing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.thevalidator.timeattackracing.auth.config.JwtLifetimeProperties;
import ru.thevalidator.timeattackracing.auth.config.KeyProperties;

@SpringBootApplication
@EnableConfigurationProperties({KeyProperties.class, JwtLifetimeProperties.class})
public class TimeAttackRacingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimeAttackRacingApplication.class, args);
        System.out.println("""
                        
                        ############################################################################################
                        #                                                                                          #
                        #        API documentation can be found by this url: http://localhost:8080/api-docs        #
                        #                                                                                          #
                        ############################################################################################
                        """);
    }

}
