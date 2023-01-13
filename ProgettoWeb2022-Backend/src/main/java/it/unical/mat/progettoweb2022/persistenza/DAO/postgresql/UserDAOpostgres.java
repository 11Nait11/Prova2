package it.unical.mat.progettoweb2022.persistenza.DAO.postgresql;

import it.unical.mat.progettoweb2022.model.User;
import it.unical.mat.progettoweb2022.persistenza.DAO.UserDAO;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOpostgres implements UserDAO {
    Connection conn;

    public UserDAOpostgres(Connection conn){
        this.conn = conn;
    }


    @Override
    public List<User> findAll() {
        List<User> users = null;
        String query = "SELECT * FROM users";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            users = new ArrayList<User>();
            while(rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setLastname(rs.getString("lastname"));
                user.setAge(rs.getInt("age"));
                user.setNickname(rs.getString("nickname"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                user.setBanned(rs.getBoolean("isbanned"));
                user.setTelephone(rs.getString("telephone"));
                user.setState(rs.getString("state"));
                user.setCountry(rs.getString("country"));
                user.setAddress(rs.getString("address"));
                user.setPostalCode(rs.getString("postalCode"));

                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public User findByPrimaryKey(String nickname) {
        User user = null;
        String query = "SELECT * FROM users WHERE nickname =?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, nickname);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setLastname(rs.getString("lastname"));
                user.setAge(rs.getInt("age"));
                user.setNickname(rs.getString("nickname"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                user.setBanned(rs.getBoolean("isbanned"));
                user.setTelephone(rs.getString("telephone"));
                user.setState(rs.getString("state"));
                user.setCountry(rs.getString("country"));
                user.setAddress(rs.getString("address"));
                user.setPostalCode(rs.getString("postalCode"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public boolean saveOrUpdate(User user) {
        String query;
        if(user.getId() != null) { //UPDATE if exists
            query = "UPDATE users SET " +
                    " name = ?," +
                    " lastname = ?," +
                    " age = ?," +
                    " password = ?," +
                    " email = ?," +
                    " role = ?," +
                    " isbanned = ?," +
                    " telephone = ?," +
                    " state = ?," +
                    " country = ?," +
                    " address = ?," +
                    " postalCode = ?" +
                    " WHERE nickname = ?";

            try {
                PreparedStatement st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                st.setString(1, user.getName());
                st.setString(2, user.getLastname());
                st.setInt(3, user.getAge());
                st.setString(4, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
                st.setString(5, user.getEmail());
                st.setString(6, user.getRole());
                st.setBoolean(7, user.getBanned());
                st.setString(8, user.getTelephone());
                st.setString(9, user.getState());
                st.setString(10, user.getCountry());
                st.setString(11, user.getAddress());
                st.setString(12, user.getPostalCode());
                st.setString(13, user.getNickname());

                st.executeUpdate();
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    user.setId(rs.getInt("id"));
                    user.setRole(rs.getString("role"));
                    user.setBanned(rs.getBoolean("isbanned"));
                }
            } catch (SQLException e) {
                return false;
            }

        }else{ //INSERT if not exists
            query = "INSERT INTO users VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ? , ? , ? , ?, ?)";
            try {
                PreparedStatement st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                st.setString(1, user.getName());
                st.setString(2, user.getLastname());
                st.setInt(3, user.getAge());
                st.setString(4, user.getNickname());
                st.setString(5, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
                st.setString(6, user.getEmail());
                st.setString(7, user.getRole());
                st.setBoolean(8, false);
                st.setString(9, user.getTelephone());
                st.setString(10, user.getState());
                st.setString(11, user.getCountry());
                st.setString(12, user.getAddress());
                st.setString(13, user.getPostalCode());


                st.executeUpdate();
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    user.setId(rs.getInt(1));
                }
            } catch (SQLException e) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void delete(User user) {
        String query = "DELETE FROM users WHERE nickname = ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, user.getNickname());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
