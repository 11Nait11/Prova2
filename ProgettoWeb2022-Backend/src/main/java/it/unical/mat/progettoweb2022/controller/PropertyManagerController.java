package it.unical.mat.progettoweb2022.controller;

import it.unical.mat.progettoweb2022.model.Property;
import it.unical.mat.progettoweb2022.model.User;
import it.unical.mat.progettoweb2022.persistenza.DBManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class PropertyManagerController {

    @PostMapping("/newProperty")
    public void addProperty(HttpServletRequest req , @RequestBody ArrayList<Property> properties) {
        System.out.println("SALVO");
        //recupero User loggato dalla session
        HttpSession session = req.getSession();
        User u=(User) session.getAttribute("user");

        if(u!=null) {
            for (Property i : properties) {

                if (i != null) {
                    i.setUser(u.getNickname());
                    i.setLatitude("10.10.01.01");
                    i.setLongitude("10.01.0.02");
                    DBManager.getInstance().getPropertyDao().saveOrUpdate(i);
                }
            }
        }
    }

    @PostMapping("/deleteProperty")
    public void removeProperty(@RequestBody ArrayList<Integer> properties) {
        for(Integer pId : properties) {
            if (pId != null) {
                Property property = new Property();
                property.setId(pId);
                System.out.println("ID DA ELIMINARE : " +pId);
                DBManager.getInstance().getPropertyDao().delete(property);
            }
        }
    }
}

