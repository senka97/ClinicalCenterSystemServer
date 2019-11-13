package team57.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import team57.project.model.ClinicAdmin;

import java.sql.Time;
import java.time.LocalDateTime;

@SpringBootApplication
public class ClinicalCenterSystemServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClinicalCenterSystemServerApplication.class, args);
        //ClinicAdmin a = ClinicAdmin.builder().clinicId("email").time("sdsdsd").build();
      //  System.out.println(a);

    }

}
