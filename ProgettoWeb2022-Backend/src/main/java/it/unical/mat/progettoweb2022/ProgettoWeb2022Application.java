package it.unical.mat.progettoweb2022;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@ServletComponentScan
@CrossOrigin("http://localhost:4200")
public class ProgettoWeb2022Application {

    public static void main(String[] args) {
        SpringApplication.run(ProgettoWeb2022Application.class, args);
    }

}
