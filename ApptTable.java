/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinancePlus;

import Utils.DBConnection;
import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author lueva
 */
public class ApptTable {

    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        try {
            String sqlStatement = "SELECT appointmentId,customer.customerId,customerName,type,start,user.userId,userName FROM customer,appointment,user WHERE appointment.customerId=customer.customerId AND appointment.userId=user.userId";

            Connection conn = DBConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sqlStatement);
            ResultSet result = pst.executeQuery();
            while (result.next()) {
                int apptId = result.getInt("appointmentId");
                String apptType = result.getString("type");
                int customerId = result.getInt("customerId");
                int userId = result.getInt("userId");
                String customerName = result.getString("customerName");
                String userName = result.getString("userName");

                Timestamp apptTime = result.getTimestamp("start");

                LocalDateTime ldt = apptTime.toLocalDateTime();//Timezone conversion
              
                 ZoneId zone = ZoneId.of("UTC");
            
            LocalDateTime startsd = ldt.atZone(zone).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
            
            
                Appointment apptResult = new Appointment(apptId, apptType, startsd.toLocalTime(), startsd.toLocalDate(), userId, userName, customerId, customerName);
                allAppointments.add(apptResult);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAppointments;
    }

    public static void addAppt(LocalTime start, String type, Customer customerName, User userName, LocalDate date) {
        //MYSQL STATEMENT
        try {
            String sqlStatement = "INSERT INTO `appointment` VALUES (NULL, ?,?,'NA','NA','NA','NA',?,'NA',?,NOW(),NOW(),'AL',NOW(),'AL')";

            //CREATE PREPAREDSTATEMENT
            PreparedStatement pst = DBConnection.getConnection().prepareStatement(sqlStatement);

            //add question marks
            pst.setInt(1, customerName.getCustomerId());
            pst.setInt(2, userName.getUserId());
            pst.setString(3, type);
            LocalDateTime ldt = LocalDateTime.of(date, start);
            
           
             ZoneId zone = ZoneId.systemDefault();
            
            LocalDateTime startUTC = ldt.atZone(zone).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
            Timestamp ts = Timestamp.valueOf(startUTC);//Timezone conversion
            
            
            
            
           
            
             
            pst.setTimestamp(4, ts);

            pst.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void deleteAppt(int customerId) {
        try {

            //MYSQL STATEMENT
            String sql = "DELETE FROM appointment where customerId = ?";

            PreparedStatement pst1 = DBConnection.getConnection().prepareStatement(sql);

            //add question marks
            pst1.setInt(1, customerId);

            pst1.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void updateAppt(int apptId, LocalTime start, String type, Customer customerName, User userName, LocalDate date) {
        //MYSQL STATEMENT
        try {
            //MYSQL STATEMENT
            String sql = "UPDATE `appointment` SET type = ?, start = ?, customerId=?, userId=? WHERE appointmentId=?";

            PreparedStatement pst1 = DBConnection.getConnection().prepareStatement(sql);

            //add question marks
            pst1.setString(1, type);

            LocalDateTime ldt = LocalDateTime.of(date, start);
             ZoneId zone = ZoneId.systemDefault();
            
            LocalDateTime startUTC = ldt.atZone(zone).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
            Timestamp ts = Timestamp.valueOf(startUTC);//Timezone conversion
           
            pst1.setTimestamp(2, ts);

            pst1.setInt(3, customerName.getCustomerId());
            pst1.setInt(4, userName.getUserId());
            pst1.setInt(5, apptId);

            pst1.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static int getApptCount(int count) {
        try {
            String sqlStatement = "SELECT count(appointmentId) FROM appointment";
            Connection conn = DBConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sqlStatement);
            ResultSet result = pst.executeQuery();

            //result.net();
            result.next();
            count = result.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;

    }

    public static int getApptMonthCount(int monthIndex) {
        int count = 0;
        try {
            String sqlStatement = "SELECT count(distinct type) FROM appointment where month(start)=?";
            Connection conn = DBConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sqlStatement);
            pst.setInt(1, monthIndex);
            ResultSet result = pst.executeQuery();

            result.next();
            count = result.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;

    }

    public static ObservableList<Appointment> getUserSchedule(int id) {
        ObservableList<Appointment> schedule = FXCollections.observableArrayList();

        try {
            String sqlStatement = "SELECT appointmentId,customer.customerId,customerName,type,start,user.userId,userName FROM customer,appointment,user WHERE appointment.customerId=customer.customerId AND appointment.userId = ? AND appointment.userId=user.userId";

            Connection conn = DBConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sqlStatement);
            pst.setInt(1, id);
            ResultSet result = pst.executeQuery();

            while (result.next()) {

                int apptId = result.getInt("appointmentId");
                String apptType = result.getString("type");
                int customerId = result.getInt("customerId");
                int userId = result.getInt("userId");
                String customerName = result.getString("customerName");
                String userName = result.getString("userName");

                Timestamp apptTime = result.getTimestamp("start");

                LocalDateTime ldt = apptTime.toLocalDateTime();//Timezone conversion
              
                 ZoneId zone = ZoneId.of("UTC");
            
            LocalDateTime startsd = ldt.atZone(zone).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
                
                

                Appointment apptResult = new Appointment(apptId, apptType, startsd.toLocalTime(), startsd.toLocalDate(), userId, userName, customerId, customerName);
                schedule.add(apptResult);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedule;
    }

    public static ObservableList<Appointment> getNext30() {
        ObservableList<Appointment> next30Days = FXCollections.observableArrayList();

        {
            try {
                String sqlStatement = "SELECT appointmentId,customer.customerId,customerName,type,start,user.userId,userName FROM customer,appointment,user WHERE appointment.customerId=customer.customerId AND appointment.userId=user.userId AND start>now() and start<date_add(now(),interval 30 day)";

                Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sqlStatement);
                ResultSet result = pst.executeQuery();

                while (result.next()) {

                    int apptId = result.getInt("appointmentId");
                    String apptType = result.getString("type");
                    int customerId = result.getInt("customerId");
                    int userId = result.getInt("userId");
                    String customerName = result.getString("customerName");
                    String userName = result.getString("userName");

                     Timestamp apptTime = result.getTimestamp("start");

                LocalDateTime ldt = apptTime.toLocalDateTime();//Timezone conversion
              
                 ZoneId zone = ZoneId.of("UTC");
            
            LocalDateTime startsd = ldt.atZone(zone).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();

                    Appointment apptResult = new Appointment(apptId, apptType, startsd.toLocalTime(), startsd.toLocalDate(), userId, userName, customerId, customerName);
                    next30Days.add(apptResult);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return next30Days;

        }
    }

    public static ObservableList<Appointment> getNext7() {
        ObservableList<Appointment> next7Days = FXCollections.observableArrayList();

        {
            try {
                String sqlStatement = "SELECT appointmentId,customer.customerId,customerName,type,start,user.userId,userName FROM customer,appointment,user WHERE appointment.customerId=customer.customerId AND appointment.userId=user.userId AND start>now() and start<date_add(now(),interval 7 day)";

                Connection conn = DBConnection.getConnection();
                PreparedStatement pst = conn.prepareStatement(sqlStatement);
                ResultSet result = pst.executeQuery();

                while (result.next()) {

                    int apptId = result.getInt("appointmentId");
                    String apptType = result.getString("type");
                    int customerId = result.getInt("customerId");
                    int userId = result.getInt("userId");
                    String customerName = result.getString("customerName");
                    String userName = result.getString("userName");

                    
                    
                    Timestamp apptTime = result.getTimestamp("start");

                LocalDateTime ldt = apptTime.toLocalDateTime();//Timezone conversion
              
                 ZoneId zone = ZoneId.of("UTC");
            
            LocalDateTime startsd = ldt.atZone(zone).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();

                    Appointment apptResult = new Appointment(apptId, apptType, startsd.toLocalTime(), startsd.toLocalDate(), userId, userName, customerId, customerName);
                    next7Days.add(apptResult);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return next7Days;

        }
    }

    public static int getUpcomingAppointmentCount(int userId) {
        String sqlStatement = "SELECT count(*) AS count FROM appointment WHERE start > NOW() AND  start < date_add(NOW(), INTERVAL 15 MINUTE) AND userId=?";

        int count = 0;
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sqlStatement);
            pst.setInt(1, userId);
            ResultSet result = pst.executeQuery();
            
            while (result.next()) {
                count = result.getInt("count");
            }
        } catch (SQLException e) {

        }
        return count;
    }

}
