/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinancePlus;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author lueva
 */
public class Appointment {
   int apptId;
  String apptType;
  LocalTime apptTime;
  LocalDate apptDate;
  String userName;
  int userId;
  int customerId;
  String customerName;
public static ObservableList<String>ApptTypes=FXCollections.observableArrayList("Real Estate","Stocks/Bonds","General");
public static ObservableList<String>ApptMonths=FXCollections.observableArrayList("January","February","March","April","May","June","July","August","September","October","November","December");

    public Appointment(int apptId,String apptType, LocalTime apptTime,LocalDate apptDate, int userId,String userName, int customerId,String customerName) {
        this.apptId= apptId;
        this.apptType = apptType;
        this.apptTime = apptTime;
        this.apptDate = apptDate;
        this.userName = userName;
        this.customerName = customerName;
        this.userId= userId;
        this.customerId = customerId;
    }

   



public int getApptId(){
    return apptId;
}
public int getUserId(){
    return userId;
}

public LocalDate getApptDate() {
    
        return apptDate;
    }
/*
public void setApptDate(String apptDate) {
        this.apptDate = appDate;
    }
*/
    public String getApptType() {
        return apptType;
    }

    public void setApptType(String apptType) {
        this.apptType = apptType;
    }

    public LocalTime getApptTime() {
       
        return apptTime;
    }

    public void setApptTime(LocalTime apptTime) {
        this.apptTime = apptTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
   


    @Override
    public String toString(){
        return ((apptDate.toString()+ " " + apptTime.toString()));
    }


}