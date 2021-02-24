/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinancePlus;

import Utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author lueva
 */
public class User {
    int userId;
    String userName;

    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public static ObservableList<User> getAllUsers() {
        ObservableList<User> allUsers=FXCollections.observableArrayList();
        try{
       
            String sqlStatement="SELECT * FROM user";          
           
            Connection conn = DBConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sqlStatement);
            ResultSet result=pst.executeQuery();
             while(result.next()){
        
                String userName=result.getString("userName"); 
                int userId =result.getInt("userId");
            
               
             
                User userResult= new User(userId,userName);
                allUsers.add(userResult);
                
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return allUsers;
    } 
    
    
    
    
   @Override
    public String toString(){
        return ((userName)); 
    }
    
}
