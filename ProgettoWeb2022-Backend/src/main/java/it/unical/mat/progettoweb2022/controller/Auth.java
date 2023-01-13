package it.unical.mat.progettoweb2022.controller;

import it.unical.mat.progettoweb2022.Protocol;
import it.unical.mat.progettoweb2022.model.User;
import it.unical.mat.progettoweb2022.persistenza.DAO.UserDAO;
import it.unical.mat.progettoweb2022.persistenza.DBManager;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin("http://localhost:4200")
public class Auth {

    @PostMapping("/doLogin")
    @ResponseBody
    public Object doLogin(HttpServletRequest req,HttpServletResponse resp,@RequestParam String nickname, @RequestParam String password){
        System.out.println(nickname);
        System.out.println(password);

        User user = DBManager.getInstance().getUserDao().findByPrimaryKey(nickname);
        System.out.println(user.toString());
        if(user.getId() != null){
            if(BCrypt.checkpw(password, user.getPassword())){
                if(!user.getBanned()) {
                    HttpSession session = req.getSession();
                    session.setAttribute("user", user);
                    session.setAttribute("sessionId", session.getId());
                    req.getServletContext().setAttribute(session.getId(), session);
                    return req.getSession().getId();
                }else{
                    return Protocol.USER_BANNED;
                }
            }else{
                return Protocol.INCORRECT_PASSWORD;
            }
        }else{
            return Protocol.USER_NOT_EXISTS;
        }
    }

    @PostMapping("/doRegister")
    @ResponseBody
    public Object doRegister(HttpServletRequest req,HttpServletResponse resp,
                             @RequestParam String nickname,
                             @RequestParam String name,
                             @RequestParam String lastname,
                             @RequestParam String email,
                             @RequestParam String age,
                             @RequestParam String password){

        UserDAO dao = DBManager.getInstance().getUserDao();
        if(dao.findByPrimaryKey(nickname) == null){
            User user = new User();
            user.setNickname(nickname);
            user.setName(name);
            user.setLastname(lastname);
            user.setEmail(email);
            user.setAge(Integer.parseInt(age));
            user.setPassword(password);
            user.setRole("user");
            user.setBanned(false);
            System.out.println("UTENTE CREATO");
            System.out.println(user);
            dao.saveOrUpdate(user);
            System.out.println("INSERITO");
            System.out.println(user);
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            session.setAttribute("sessionId", session.getId());
            req.getServletContext().setAttribute(session.getId(), session);
            return session.getId();
        }else{
            return Protocol.USER_EXISTS;
        }
    }

    @GetMapping("/checkLoggedIn")
    @ResponseBody
    @CrossOrigin("http://localhost:4200/")
    public boolean checkLoggedIn(HttpServletRequest req, HttpServletResponse resp, @RequestParam String sessionId){
        System.out.println("APPENA LOGGATO" + sessionId);
        ServletContext context = req.getServletContext();
        Object user = context.getAttribute(sessionId);
        if(user != null){
            return true;
        }
        return false;
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest req, HttpServletResponse resp, @RequestParam String sessionId){
        req.getSession().removeAttribute("user");
        req.getSession().removeAttribute("sessionId");
        req.getServletContext().removeAttribute(req.getSession().getId());
        try {
            resp.sendRedirect("http://localhost:4200");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
