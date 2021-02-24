/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinancePlus;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import static java.util.Calendar.AM;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lueva
 */
public class ApptDataScreenController implements Initializable {

    @FXML
    private Button back;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button updateAppt;
    @FXML
    private Button deleteAppt;
    @FXML
    private Button scheduleAppt;
    @FXML
    private ComboBox<String> apptTypeComboBox;
    @FXML
    private ComboBox<LocalTime> apptTimeComboBox;
    @FXML
    private ComboBox<Customer> selectCustomerComboBox;
    @FXML
    private ComboBox<User> selectUserComboBox;
    @FXML
    private ComboBox<Appointment> selectApptCB;
    @FXML
    private ComboBox<Customer> updateCustCB;
    @FXML
    private ComboBox<User> updateUserCB;
    @FXML
    private DatePicker updateDatePicker;
    ObservableList<Appointment> Appt = FXCollections.observableArrayList();
    ObservableList<Customer> Customers = FXCollections.observableArrayList();
    ObservableList<User> Users = FXCollections.observableArrayList();
    ObservableList<LocalTime> time = FXCollections.observableArrayList();
    private ArrayList<Appointment> appointmentList = null;

    @FXML
    private TableColumn<Appointment, String> custNameCol;
    @FXML
    private TableColumn<Appointment, String> apptTypeCol;
    @FXML
    private TableColumn<Appointment, String> timeCol;
    @FXML
    private TableColumn<Appointment, String> userCol;
    private TableColumn<Appointment, Integer> custIdCol;
    @FXML
    private TableView<Appointment> apptTable;
    @FXML
    private TableColumn<Appointment, String> DateCol;
    @FXML
    private ComboBox<String> updateTypeCB;
    @FXML
    private ComboBox<LocalTime> apptModTime;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DateCol.setCellValueFactory(new PropertyValueFactory<>("apptDate"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("apptType"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("apptTime"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("userName"));

        try {

            Appt.addAll(ApptTable.getAllAppointments());

        } catch (Exception ex) {
            Logger.getLogger(ApptDataScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        apptTable.setItems(Appt);

        try {
            Customers.addAll(CustomerTable.getAllCustomers());

        } catch (Exception ex) {
            Logger.getLogger(CustomerDataScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {

            Users.addAll(User.getAllUsers());

        } catch (Exception ex) {
            Logger.getLogger(ApptDataScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(17, 0);

        while (start.isBefore(end)) {
            time.add(start);
            start = start.plusHours(1);

        }
        apptTimeComboBox.setItems(time);
        apptModTime.setItems(time);

        apptTypeComboBox.setItems(Appointment.ApptTypes);

        selectUserComboBox.setItems(Users);

        selectCustomerComboBox.setItems(Customers);

        selectApptCB.setItems(ApptTable.getAllAppointments());

        updateUserCB.setItems(Users);
        updateCustCB.setItems(Customers);
        updateTypeCB.setItems(Appointment.ApptTypes);

    }

    @FXML
    private void deleteAppt(ActionEvent event) throws SQLException {

        try {
            apptTable.getSelectionModel().getSelectedItem();

            int customerId = apptTable.getSelectionModel().getSelectedItem().getCustomerId();

            ApptTable.deleteAppt(customerId);

            Appt.clear();

            Appt.addAll(ApptTable.getAllAppointments());

        } catch (Exception ex) {
            Logger.getLogger(ApptDataScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        apptTable.setItems(Appt);

    }

    @FXML
    private void addAppt(ActionEvent event) throws IOException {

        try {

            LocalDate date = datePicker.getValue();
            String type = apptTypeComboBox.getSelectionModel().getSelectedItem();
            LocalTime start = apptTimeComboBox.getSelectionModel().getSelectedItem();
            Customer customerName = selectCustomerComboBox.getSelectionModel().getSelectedItem();
            User userName = selectUserComboBox.getSelectionModel().getSelectedItem();
            LocalDateTime startTime = LocalDateTime.of(date, start);

            ObservableList<Appointment> apptList = ApptTable.getAllAppointments();

            //Lambda Expression used to improve code efficiency and more concise
            ObservableList<Appointment> filteredList = apptList.filtered(a -> {
                if (a.getUserId() == userName.getUserId()) {
                    return true;
                }
                return false;

            });

            //CODE TO PREVENT SCHEDULING OVERLAPPING APPTS
            for (Appointment appt : filteredList) {
                LocalDateTime aStart = LocalDateTime.of(appt.getApptDate(), appt.getApptTime());
                if (startTime.isEqual(aStart)) {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Appointment Alert");
                    alert.setHeaderText("This Time Overlaps With A Current Appointment!");
                    alert.setContentText("Change Time");

                    alert.showAndWait();
                    return;
                }

            }

            ApptTable.addAppt(start, type, customerName, userName, date);

            Appt.clear();
            Appt.addAll(ApptTable.getAllAppointments());
            selectApptCB.setItems(ApptTable.getAllAppointments());


        } catch (Exception ex) {
            Logger.getLogger(ApptDataScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        apptTable.setItems(Appt);
    }

    @FXML
    private void updateAppt(ActionEvent event) throws IOException {
        
        if(Appt==null){
            Appt.clear();
            return;
        }
        try {

            
            Appointment appt = selectApptCB.getValue();

            LocalDate date = updateDatePicker.getValue();

            String type = updateTypeCB.getValue();
            LocalTime start = apptModTime.getSelectionModel().getSelectedItem();

            Customer customerName = updateCustCB.getValue();

            User userName = updateUserCB.getValue();
            LocalDateTime startTime = LocalDateTime.of(date, start);

            ObservableList<Appointment> apptList = ApptTable.getAllAppointments();

            //Lambda Expression used to improve code efficiency and more concise
            ObservableList<Appointment> filteredList = apptList.filtered(a -> {
                if (a.getUserId() == userName.getUserId()) {
                    return true;
                }
                return false;

            });
            //CODE TO PREVENT SCHEDULING OVERLAPPING APPTS
            for (Appointment ap : filteredList) {
                LocalDateTime aStart = LocalDateTime.of(ap.getApptDate(), ap.getApptTime());
                if (ap.getApptId() == appt.getApptId()) {
                    continue;
                }
                if (aStart.isEqual(startTime)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Appointment Alert");
                    alert.setHeaderText("This Time Overlaps With A Current Appointment!");
                    alert.setContentText("Change Time");

                    alert.showAndWait();
                    return;
                }
            }

            ApptTable.updateAppt(appt.getApptId(), start, type, customerName, userName, date);

            Appt.clear();
            Appt.addAll(ApptTable.getAllAppointments());

        } catch (Exception ex) {
            Logger.getLogger(ApptDataScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        apptTable.setItems(Appt);

    }

    @FXML
    private void customerDataScreen(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("customerDataScreen.fxml"));

        Scene tableViewScene = new Scene(tableViewParent);

        Stage addPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addPartStage.setScene(tableViewScene);
        addPartStage.show();
    }

    @FXML
    private void setModValues(ActionEvent event
    ) {
        
        Appointment appt = selectApptCB.getValue();

        updateDatePicker.setValue(appt.getApptDate());

        updateTypeCB.setValue(appt.getApptType());

        apptModTime.setValue(appt.getApptTime());

        for (Customer c : updateCustCB.getItems()) {
            if (c.getCustomerId() == appt.getCustomerId()) {
                updateCustCB.setValue(c);
                break;
            }
        }
        for (User u : updateUserCB.getItems()) {
            if (u.getUserId() == appt.getUserId()) {
                updateUserCB.setValue(u);
                break;
            }
        }

    }


}
