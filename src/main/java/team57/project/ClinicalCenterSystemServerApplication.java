package team57.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
public class ClinicalCenterSystemServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClinicalCenterSystemServerApplication.class, args);

    }

}
