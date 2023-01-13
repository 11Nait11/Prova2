package it.unical.mat.progettoweb2022.persistenza.DAO.postgresql;

import it.unical.mat.progettoweb2022.model.Property;
import it.unical.mat.progettoweb2022.persistenza.DAO.PropertyDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PropertyDAOpostgres implements PropertyDAO {

    private Connection conn = null;

    public PropertyDAOpostgres(Connection conn) {
        this.conn = conn;
    }

    private void setProperty(Property property, ResultSet rs) throws SQLException {
        property.setId(rs.getInt("id"));
        property.setType(rs.getString("type"));
        property.setMq(rs.getDouble("mq"));
        property.setLatitude(rs.getString("latitude"));
        property.setLongitude(rs.getString("longitude"));
        property.setUser(rs.getString("owner"));
        property.setAd(rs.getInt("ad"));
    }

    @Override
    public List<Property> findAll() {
        List<Property> propertyList = null;
        String query = "SELECT * FROM properties";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            propertyList = new ArrayList<Property>();
            while (rs.next()) {
                Property property = new Property();
                setProperty(property, rs);
                propertyList.add(property);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return propertyList;
    }

    @Override
    public Property findByPrimaryKey(Integer id) {
        Property property = null;
        String query = "SELECT * FROM properties WHERE id = ?;";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                property = new Property();
                setProperty(property, rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return property;
    }

    @Override
    public void saveOrUpdate(Property property) {
        if (property.getId() == null) {
            String query = "INSERT INTO properties VALUES(DEFAULT, ?, ?, ?, ?, ?, NULL);";
            try {
                PreparedStatement st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                st.setString(1, property.getType());
                st.setDouble(2, property.getMq());
                st.setString(3, property.getLatitude());
                st.setString(4, property.getLongitude());
                st.setString(5, property.getUser());

                st.executeUpdate();
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    property.setId(rs.getInt("id"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } else {
            String query = "UPDATE properties SET type = ?," +
                    " mq = ?," +
                    " latitude = ?," +
                    " longitude = ?," +
                    " owner = ?," +
                    " ad = ?" +
                    " WHERE id =?;";
            try {
                PreparedStatement st = conn.prepareStatement(query);
                st.setString(1, property.getType());
                st.setDouble(2, property.getMq());
                st.setString(3, property.getLatitude());
                st.setString(4, property.getLongitude());
                st.setString(5, property.getUser());
                st.setInt(6, property.getAd());
                st.setInt(5, property.getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void delete(Property property) {
        String query = "DELETE FROM properties WHERE id=?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, property.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void deleteByAd(Integer id){
        String query = "DELETE FROM properties WHERE ad =?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }

    @Override
    public List<Property> findByOwner(String owner) {
        System.out.println("IL NICK è  nome: " + owner);
        List<Property> properties = new ArrayList<>();
        String query = "SELECT * FROM properties WHERE owner =?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, owner);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                Property p = new Property();
                setProperty(p, rs);

                properties.add(p);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    public Property findByAdId(Integer id){
        Property property = null;
        String query = "SELECT * FROM properties WHERE ad =?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                property = new Property();
                setProperty(property, rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return property;
    }
}
