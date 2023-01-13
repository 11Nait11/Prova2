package it.unical.mat.progettoweb2022.controller;


import it.unical.mat.progettoweb2022.model.Property;
import it.unical.mat.progettoweb2022.model.User;
import it.unical.mat.progettoweb2022.persistenza.DBManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/properties")
public class PropertyService extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        //Prendo user
        User u= (User) session.getAttribute("user");
        String nickname = u.getNickname();

        //Prendo sessionId
        String sessionId = session.getId();

        List<Property> properties= DBManager.getInstance().getPropertyDao().findByOwner(nickname);

        req.setAttribute("propertiesList",properties);
        req.setAttribute("sessionId",sessionId);
        req.setAttribute("nickname",nickname);

        RequestDispatcher dispacher = req.getRequestDispatcher("views/properties.html");
        dispacher.forward(req, resp);
    }
}
