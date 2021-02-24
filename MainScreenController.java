/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinancePlus;

import Utils.DBConnection;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author lueva
 */
public class MainScreenController implements Initializable {

    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    @FXML
    private Label label;
    @FXML
    private Button loginButton;
    @FXML
    private Button exit;
    private ResourceBundle bundle;
    @FXML
    private Label welcome;
    @FXML
    private Label username;
    @FXML
    private Label password;
    @FXML
    private Label trademark;
    @FXML
    private TextField userTxt;
    @FXML
    private TextField userPass;
    @FXML
    private static User currentUser;
    ObservableList<Appointment> apptIn15 = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = ResourceBundle.getBundle("FinancePlus/languages", Locale.getDefault());
        welcome.setText(bundle.getString("welcome"));
        username.setText(bundle.getString("username"));
        password.setText(bundle.getString("password"));
        loginButton.setText(bundle.getString("login"));
        exit.setText(bundle.getString("exit"));

        //label.setText("Test");
        // int alert = ApptTable.getUpcomingAppointmentCount(1);
        //  System.out.println("You have an appointment" + alert);
        //label.setText(String.valueOf(alert));
    }

    @FXML
    private void loginButton(ActionEvent event) throws IOException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String query = "SELECT * FROM U05QrM.user WHERE userName=? and password=?";
            Connection conn = DBConnection.getConnection();

            pst = conn.prepareStatement(query);
            pst.setString(1, userTxt.getText());
            pst.setString(2, userPass.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("userId");
                Stage addPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                addPartStage.hide();
                
                //15 min alert
                int upcomingAppt = ApptTable.getUpcomingAppointmentCount(userId);

                if (upcomingAppt>0) {

                    Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Appointment Alert");
                alert.setHeaderText("You have an Appointment in 15 Minutes");
                alert.setContentText("Get Ready!");

                alert.showAndWait();
                    
                    // System.out.println("You have an appointment" + alert);
                    //label.setText(String.valueOf(alert));
                }

                label.setText("Login Successful!");
                Parent tableViewParent = FXMLLoader.load(getClass().getResource("/FinancePlus/customerDataScreen.fxml"));

                Scene tableViewScene = new Scene(tableViewParent);

                addPartStage.setScene(tableViewScene);
                addPartStage.show();
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle(bundle.getString("deny_title"));
                alert.setHeaderText(bundle.getString("deny_message"));
                alert.setContentText(bundle.getString("deny_content"));

                alert.showAndWait();
                label.setText(bundle.getString("deny_content"));
                userTxt.setText("");
                userPass.setText("");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(
                    new File("Login.txt"),
                    true /* append = true */));
  
            pw.append("User Logged int at: \n");
            pw.println(ZonedDateTime.now());
                  
            pw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    

    }

    @FXML
    private void exitProgramButton(ActionEvent event) {
        Platform.exit();
    }



}
