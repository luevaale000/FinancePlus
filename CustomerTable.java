/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinancePlus;

import Utils.DBConnection;
import Utils.DBQuery;
import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;


/**
 *
 * @author lueva
 */
public class CustomerTable {
  
   


    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> allCustomers=FXCollections.observableArrayList();
        try{
       
            String sqlStatement="SELECT customerId,customerName,address.addressId,address,cityId,postalCode,phone FROM customer,address WHERE customer.addressId=address.addressId";          
           
            Connection conn = DBConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sqlStatement);
            ResultSet result=pst.executeQuery();
             while(result.next()){
               int customerId=result.getInt("customerId");
                String customerName=result.getString("customerName");            
               int addressId=result.getInt("addressId");
               String address=result.getString("address");
               int cityId=result.getInt("cityId");
               int postalCode=result.getInt("postalCode");
               String phone=result.getString("phone");            
               
               
               
             
                Customer customerResult= new Customer(customerId,customerName,addressId,address,cityId,postalCode,phone);
                allCustomers.add(customerResult);
                
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return allCustomers;
    } 
    
    public static ObservableList<City> getAllCities() {
        ObservableList<City> allCities=FXCollections.observableArrayList();
        try{
       
            String sqlStatement="SELECT * FROM city";          
           
            Connection conn = DBConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sqlStatement);
            ResultSet result=pst.executeQuery();
             while(result.next()){
               int cityId=result.getInt("cityId");
                String city=result.getString("city");            
               
             
                City cityResult= new City(cityId,city);
                allCities.add(cityResult);
                
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return allCities;
    } 
    
    
    
    public static void addCustomer(String address, int cityId, String postalCode, String phone,String customerName)
    {
        //MYSQL STATEMENT
    try {String sqlStatement="INSERT INTO `address` VALUES (NULL, ?,'',?,?,?,NOW(),'AL',NOW(),'AL')";          
           
        //CREATE PREPAREDSTATEMENT
        PreparedStatement pst = DBConnection.getConnection().prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
        
        //add question marks
        pst.setString(1, address);
        
        pst.setInt(2, cityId);
        pst.setString(3, postalCode);
        pst.setString(4, phone);
 
        
        pst.execute();
        ResultSet rs = pst.getGeneratedKeys();
        rs.next();
        
        int addressId = rs.getInt(1);
        
        //MYSQL STATEMENT
        String sql="INSERT INTO `customer` VALUES (NULL, ?,?,1,NOW(),'AL',NOW(),'AL')";
        
                PreparedStatement pst1 = DBConnection.getConnection().prepareStatement(sql);

                        
        //add question marks
        pst1.setString(1, customerName);
        pst1.setInt(2, addressId);
       
        
              pst1.execute();
        
    } catch (SQLException e) {
            e.printStackTrace();
        }

    }
public static void deleteCustomer(int customerId, int addressId){
         try {

        
        //MYSQL STATEMENT
        String sql="DELETE FROM appointment where customerId = ?";
        
                PreparedStatement pst1 = DBConnection.getConnection().prepareStatement(sql);
                       
        //add question marks
        pst1.setInt(1, customerId);      
        
              pst1.execute();
  
    //MYSQL STATEMENT
        String sql2="DELETE FROM customer where customerId = ?";
        
                PreparedStatement pst2 = DBConnection.getConnection().prepareStatement(sql2);
                       
        //add question marks
        pst2.setInt(1, customerId);      
        
              pst2.execute();
                         
 
         //MYSQL STATEMENT
         String sqlStatement="DELETE FROM `address` WHERE addressId = ?";          
           
        //CREATE PREPAREDSTATEMENT
        PreparedStatement pst = DBConnection.getConnection().prepareStatement(sqlStatement);
        
        //add question marks
        pst.setInt(1, addressId);

         
        pst.execute();
                     
              
    } catch (SQLException e) {
            e.printStackTrace();
        }

}    

public static void updateCustomer(String address, int cityId, String postalCode, String phone,String customerName, int customerId, int addressId){
    //MYSQL STATEMENT
    try {String sqlStatement="UPDATE `customer` SET customerName = ? WHERE customerId = ?";          
           
        //CREATE PREPAREDSTATEMENT
        PreparedStatement pst = DBConnection.getConnection().prepareStatement(sqlStatement);
        
        //add question marks
        pst.setString(1, customerName);
        pst.setInt(2, customerId);
       
 
        
        pst.execute();
  
        //MYSQL STATEMENT
        String sql="UPDATE `address` SET address = ?, cityId = ?, postalCode = ?, phone = ? WHERE addressId = ?";
        
                PreparedStatement pst1 = DBConnection.getConnection().prepareStatement(sql);

                        
        //add question marks
        pst1.setString(1, address);
        pst1.setInt(2, cityId);
        pst1.setString(3, postalCode);
        pst1.setString(4, phone);
        pst1.setInt(5, addressId);
        
              pst1.execute();
        
    } catch (SQLException e) {
            e.printStackTrace();
        }
    
    
    
}
    
}

    



