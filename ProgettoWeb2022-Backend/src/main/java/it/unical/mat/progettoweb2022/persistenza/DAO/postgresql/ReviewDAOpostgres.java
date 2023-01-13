package it.unical.mat.progettoweb2022.persistenza.DAO.postgresql;

import it.unical.mat.progettoweb2022.model.Review;
import it.unical.mat.progettoweb2022.persistenza.DAO.ReviewDAO;
import it.unical.mat.progettoweb2022.persistenza.DBManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAOpostgres implements ReviewDAO {

    private Connection conn = null;

    public ReviewDAOpostgres(Connection conn){
        this.conn = conn;
    }

    @Override
    public List<Review> findAll() {
        List<Review> reviewList = null;
        String query = "SELECT * FROM reviews";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            reviewList = new ArrayList<>();
            while(rs.next()){
                Review review = new Review();
                review.setId(rs.getInt("id"));
                review.setTitle(rs.getString("string"));
                review.setVote(rs.getInt("vote"));
                review.setUser(DBManager.getInstance().getUserDao().findByPrimaryKey(rs.getString("user")));
                review.setAd(DBManager.getInstance().getAdDao().findByPrimaryKey(rs.getInt("ad")));
                review.setDescription(rs.getString("description"));

                reviewList.add(review);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return reviewList;
    }

    @Override
    public Review findByPrimaryKey(Integer id) {
        Review review = null;
        String query = "SELECT * FROM reviews WHERE id=?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                review = new Review();
                review.setId(rs.getInt("id"));
                review.setTitle(rs.getString("title"));
                review.setVote(rs.getInt("vote"));
                review.setUser(DBManager.getInstance().getUserDao().findByPrimaryKey(rs.getString("user")));
                review.setAd(DBManager.getInstance().getAdDao().findByPrimaryKey(rs.getInt("ad")));
                review.setDescription(rs.getString("description"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return review;
    }

    @Override
    public List<Review> findByAdId(Integer id) {
        List<Review> reviewList = null;
        String query = "SELECT * FROM reviews WHERE ad=?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            reviewList = new ArrayList();
            while(rs.next()) {
                Review review = new Review();
                review.setId(rs.getInt("id"));
                review.setTitle(rs.getString("title"));
                review.setVote(rs.getInt("vote"));
                review.setUser(DBManager.getInstance().getUserDao().findByPrimaryKey(rs.getString("user")));
                review.setAd(DBManager.getInstance().getAdDao().findByPrimaryKey(rs.getInt("ad")));
                review.setDescription(rs.getString("description"));

                reviewList.add(review);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return reviewList;
    }

    @Override
    public boolean saveOrUpdate(Review review) {
        if(review.getId() != null){
            String query = "UPDATE reviews SET title = ?," +
                    " vote = ?," +
                    " user = ?," +
                    " ad = ?," +
                    " description = ? WHERE id = ?";

            try {
                PreparedStatement st = conn.prepareStatement(query);
                st.setString(1, review.getTitle());
                st.setInt(2, review.getVote());
                st.setString(3, review.getUser().getNickname());
                st.setInt(4, review.getAd().getId());
                st.setString(5, review.getDescription());
                st.setInt(6, review.getId());
            } catch (SQLException e) {
                return false;
            }
            return true;
        }else{
            System.out.println("vado in insert");
            String query = "INSERT INTO reviews VALUES (DEFAULT, ?, ?, ?, ?, ?);";
            try {
                PreparedStatement st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                st.setString(1, review.getTitle());
                st.setInt(2, review.getVote());
                st.setString(3, review.getUser().getNickname());
                st.setInt(4, review.getAd().getId());
                st.setString(5, review.getDescription());

                st.executeUpdate();
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    review.setId(rs.getInt("id"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    @Override
    public boolean delete(Integer reviewId) {
        String query = "DELETE FROM reviews WHERE id = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1,reviewId);
            st.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
}
