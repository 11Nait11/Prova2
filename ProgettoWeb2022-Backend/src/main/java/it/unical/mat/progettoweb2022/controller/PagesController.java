package it.unical.mat.progettoweb2022.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin("http://localhost:4200")
public class PagesController {

    @GetMapping ("/login")
    public String login(){
        return "loginPage";
    }


    @GetMapping ("/register")
    public String register() {
        return "registerPage";
    }
}
