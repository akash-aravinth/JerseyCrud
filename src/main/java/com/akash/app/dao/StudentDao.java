package com.akash.app.dao;

import com.akash.app.DBConnection;
import com.akash.app.model.Student;
import com.akash.app.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    private Connection connection;

    private String getStudentByNameEmail = "SELECT * FROM students WHERE studentName = ? and studentEmail = ?;";
    public Student getUserByEmailName(String name,String email){
        Student student = null;
        try{
            connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(getStudentByNameEmail);
            statement.setString(1,name);
            statement.setString(2,email);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                student = new Student();
                student.setStudentId(rs.getInt("studentId"));
                student.setStudentName(rs.getString("studentName"));
                student.setStudentEmail(rs.getString("studentEmail"));
                System.out.println(student.getStudentName());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return student;
    }

    private String getStudents = "SELECT * FROM students;";
    public List<Student> getAllUsers() {
        List<Student> students = new ArrayList<>();
        try{
            connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(getStudents);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Student student = new Student();
                student.setStudentId(rs.getInt("studentId"));
                student.setStudentName(rs.getString("studentName"));
                student.setStudentEmail(rs.getString("studentEmail"));
                students.add(student);
                System.out.println(student.getStudentName());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return students;
    }

    private String addStudent = "INSERT INTO students(studentName,studentEmail) values(?,?);";
    public boolean addUser(String studentName, String studentEmail) {
        int result = 0;
        try {
            connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(addStudent);
            statement.setString(1,studentName);
            statement.setString(2,studentEmail);
            result = statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result > 0;
    }
}
