package it.unical.mat.progettoweb2022.persistenza;

import it.unical.mat.progettoweb2022.persistenza.DAO.*;
import it.unical.mat.progettoweb2022.persistenza.DAO.postgresql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DBManager {
    private static DBManager INSTANCE = null;
    Connection conn = null;

    private DBManager(){

    }

    public static DBManager getInstance(){
        if(INSTANCE == null) {
            INSTANCE = new DBManager();
        }
        return INSTANCE;
    }

    private Connection getConnection(){
        if (this.conn == null){
            try {
                this.conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/progettoWeb2022", "postgres", "postgres");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return this.conn;
    }

    public UserDAO getUserDao(){
        return new UserDAOpostgres(getConnection());
    }

    public AdDAO getAdDao() {
        return new AdDAOpostgres(getConnection());
    }

    public PropertyDAO getPropertyDao(){
        return new PropertyDAOpostgres(getConnection());
    }

    public ImageDAO getImageDao() {
        return new ImageDAOpostgres(getConnection());
    }

    public ReviewDAO getReviewDao() {
        return new ReviewDAOpostgres(getConnection());
    }

    public AuctionDAO getAuctionDao() {
        return new AuctionDAOpostgres(getConnection());
    }
}
