package it.unical.mat.progettoweb2022.persistenza.DAO;

import it.unical.mat.progettoweb2022.model.Auction;

import java.sql.SQLException;
import java.util.List;

public interface AuctionDAO {
    public List<Auction> findAll();

    public Auction findByPrimaryKey(Integer id);

    public void saveOrUpdate(Auction auction) throws SQLException;

    public boolean delete(Auction ad);

    public Auction findByAd(Integer id);
}
