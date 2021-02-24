/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinancePlus;

import Utils.DBConnection;
import Utils.DBQuery;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 *
 * @author lueva
 */
public class FinancePlusApp extends Application {

    private static Connection conn = null;
    private Statement stmt = null;
    private String dbUser = null;
    private String dbPass = null;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/FinancePlus/MainScreen.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {

        //Locale.setDefault(new Locale("es"));
        ResourceBundle rb = ResourceBundle.getBundle("FinancePlus/languages", Locale.getDefault());

        System.out.println(rb.getString("username") + " " + rb.getString("password"));
        System.out.println(rb.getString("welcome"));
        System.out.println(rb.getString("exit") + " " + rb.getString("login"));

        DBConnection.startConnection();

        conn = DBConnection.getConnection();

        Statement stmt;

        ResultSet rs = null;
        try {

            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT country FROM country");

            while (rs.next()) {
                String country = rs.getString(1);
                System.out.println(country);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        }

        launch(args);
        DBConnection.closeConnection();
    }

}
    

