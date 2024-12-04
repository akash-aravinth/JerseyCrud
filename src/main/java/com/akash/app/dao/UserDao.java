package com.akash.app.dao;

import com.akash.app.DBConnection;
import com.akash.app.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
        User user = new User();
        try {
            connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(getUserById);
            statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();
            if (rs == null){
                System.out.println("User Not Available");
            }
            while (rs.next()){
                user.setId(rs.getInt("userId"));
                user.setName(rs.getString("userName"));
                user.setEmail(rs.getString("email"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
}
