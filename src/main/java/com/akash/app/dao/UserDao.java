package com.akash.app.dao;

import com.akash.app.DBConnection;
import com.akash.app.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDao {
    private Connection connection;


    private String getUser = "select * from users";
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try{
            connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(getUser);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("userId"));
                user.setName(rs.getString("userName"));
                user.setEmail(rs.getString("email"));
                users.add(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }

    private String getUserById = "select * from users where userId = ?";
    public User getUserById(int id) {
        User user = null;
        try {
            connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(getUserById);
            statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();
            if (rs == null){
                System.out.println("User Not Available");
            }
            while (rs.next()){
                user = new User();
                user.setId(rs.getInt("userId"));
                user.setName(rs.getString("userName"));
                user.setEmail(rs.getString("email"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    private String updateById = "UPDATE users SET userName = ?,email = ? WHERE userId = ?;";
    public boolean updateUserById(@PathParam("id") int id, User user) {
        User newUser = null;
        int result = 0;
        try{
            connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(updateById);
            statement.setString(1,user.getName());
            statement.setString(2,user.getEmail());
            statement.setInt(3,id);
            result = statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result > 0;
    }
    private String deleteById = "DELETE FROM users WHERE userId = ?";
    public boolean deleteUser(int id){
        int result = 0;
        try {
            connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(deleteById);
            statement.setInt(1,id);
            result = statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result > 0;
    }

    private String addUser = "INSERT INTO users(userName,email) values(?,?)";
    public boolean addUser(User user) {
        int result = 0;
        try {
            connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(addUser);
            statement.setString(1,user.getName());
            statement.setString(2,user.getEmail());
            result = statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result > 0;
    }

    private String patchUser = "UPDATE users SET userName = ? WHERE userId = ?";
    public boolean patchUser(int id, String name) {
        int result = 0;
        try{
            connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(patchUser);
            statement.setString(1,name);
            statement.setInt(2,id);
            result = statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result > 0;
    }
}
