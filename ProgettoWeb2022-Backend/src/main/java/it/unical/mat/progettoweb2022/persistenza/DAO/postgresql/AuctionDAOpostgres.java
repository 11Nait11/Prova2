package it.unical.mat.progettoweb2022.persistenza.DAO.postgresql;

import it.unical.mat.progettoweb2022.model.Auction;
import it.unical.mat.progettoweb2022.persistenza.DAO.AuctionDAO;

import java.sql.*;
import java.util.List;

public class AuctionDAOpostgres implements AuctionDAO {
    private Connection conn;

    public AuctionDAOpostgres(Connection conn){
        this.conn = conn;
    }

    @Override
    public List<Auction> findAll() {
        return null;
    }


    @Override
    public Auction findByAd(Integer ad) {

        System.out.println("eccomi "+ad);
        String query = "select * from auctions  where  ad_id= ?";
        Auction au = new Auction();

        try {
            PreparedStatement st = conn.prepareStatement(query);

            st.setInt(1, ad);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                au.setId(rs.getInt("id"));
                au.setEndTime(rs.getString("end_time"));
                au.setCurrentPrice(rs.getInt("current_price"));
                au.setWinner(rs.getString("winner"));
                au.setAd_id(rs.getInt("ad_id"));
                au.setNumOfferte(rs.getInt("num_offerte"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return au;
    }


    @Override
    public Auction findByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public void saveOrUpdate(Auction ac) throws SQLException {
        if (ac.getId() == null) {
            var endTime = ac.getEndTime();
            Timestamp t = Timestamp.valueOf(endTime);
            System.out.println("valore : " + t);
            String insertStr = "INSERT INTO auctions VALUES (default, ?, ?, ?, ?)";
            PreparedStatement st;
            try {
                st = conn.prepareStatement(insertStr, Statement.RETURN_GENERATED_KEYS);


                st.setTimestamp(1, t);
                System.out.println("errore");
                st.setInt(2, ac.getCurrentPrice());
                st.setString(3, ac.getWinner());
                st.setInt(4, ac.getAd_id());



                st.executeUpdate();
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    ac.setId(rs.getInt("id"));
                }

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        } else {
            System.out.println("else");
            String updateStr = "UPDATE auctions set current_price = ? "
                    + "where ad_id = ?";

            PreparedStatement st;
            try {
                st = conn.prepareStatement(updateStr);
                st.setLong(2, ac.getAd_id());
                st.setInt(1,ac.getCurrentPrice());

                st.executeUpdate();

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }




    @Override
    public boolean delete(Auction ac) {
        String query = "DELETE FROM auctions WHERE ad_id =?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, ac.getAd_id());
            st.executeQuery();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
}
