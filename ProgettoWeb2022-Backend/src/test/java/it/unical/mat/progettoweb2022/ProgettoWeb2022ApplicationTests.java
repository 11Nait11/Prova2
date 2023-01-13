package it.unical.mat.progettoweb2022;

import it.unical.mat.progettoweb2022.model.*;
import it.unical.mat.progettoweb2022.persistenza.DBManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.*;
import java.io.*;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
class ProgettoWeb2022ApplicationTests extends JFrame{


    @Test
    void propertyDaoWorks() throws SQLException {
        Auction a= new Auction();
        a.setId(5);
        a.setAd_id(36);
        a.setCurrentPrice(500);
        a.setWinner("");
        a.setEndTime("2038-01-19 03:14:07");
        DBManager.getInstance().getAuctionDao().saveOrUpdate(a);
    }
}
