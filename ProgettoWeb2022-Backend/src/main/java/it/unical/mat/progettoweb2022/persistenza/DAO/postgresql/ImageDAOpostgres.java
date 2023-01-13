package it.unical.mat.progettoweb2022.persistenza.DAO.postgresql;

import it.unical.mat.progettoweb2022.model.Image;
import it.unical.mat.progettoweb2022.persistenza.DAO.ImageDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImageDAOpostgres implements ImageDAO {

    private Connection conn;

    public ImageDAOpostgres (Connection conn){
        this.conn = conn;
    }

    private void setImage(Image image, ResultSet rs) throws SQLException {
        image.setId(rs.getInt("id"));
        image.setData(rs.getBytes("image"));
        image.setAd(rs.getInt("ad"));
    }

    @Override
    public List<Image> findAll()  {
        List<Image> listImages = new ArrayList<>();
        String query = "SELECT ALL FROM images;";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()) {
                Image image = new Image();
                setImage(image, rs);
                listImages.add(image);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listImages;
    }

    @Override
    public Image findByPrimaryKey(Integer id) {
        Image image = null;
        String query = "SELECT * FROM images WHERE id=?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                image = new Image();
                setImage(image, rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return image;
    }

    @Override
    public List<Image> findByAdId(Integer ad_id) {
        List<Image> listImages = new ArrayList<Image>();
        String query = "SELECT * FROM images WHERE ad = ?;";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, ad_id);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                Image image = new Image();
                setImage(image, rs);
                listImages.add(image);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listImages;
    }

    @Override
    public void saveOrUpdate(Image image) {
        if(image.getId() == null){
            String query = "INSERT INTO images VALUES(DEFAULT, ?, ?);";
            try {
                PreparedStatement st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                st.setBytes(1, image.getData());
                st.setInt(2, image.getAd());

                st.executeUpdate();
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    image.setId(rs.getInt("id"));
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }else{
            String query = "UPDATE images SET image = ?," +
                    "ad = ? WHERE id = ?;";
            try {
                PreparedStatement st = conn.prepareStatement(query);
                st.setBytes(1, image.getData());
                st.setInt(2, image.getAd());
                st.setInt(3, image.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public void delete(Image image) {
        String query = "DELETE FROM images WHERE id=?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, image.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
