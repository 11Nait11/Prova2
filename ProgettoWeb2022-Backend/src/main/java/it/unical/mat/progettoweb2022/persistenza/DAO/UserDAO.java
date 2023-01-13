package it.unical.mat.progettoweb2022.persistenza.DAO;

import it.unical.mat.progettoweb2022.model.User;

import java.sql.Connection;
import java.util.List;

public interface UserDAO {

    public List<User> findAll();

    public User findByPrimaryKey(String nickname);

    public boolean saveOrUpdate(User utente);

    public void delete(User utente);
}
