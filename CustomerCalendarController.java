/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinancePlus;

import static FinancePlus.ApptTable.getUserSchedule;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
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
import static javafx.scene.control.Alert.AlertType.ERROR;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lueva
 */
public class CustomerCalendarController implements Initializable {

    @FXML
    private Button back;
    @FXML
    private Button viewAllAppt;
    @FXML
    private Label totLabel;
    @FXML
    private Label TotLabelCnt;
    ObservableList<Appointment> getAllAppointments = FXCollections.observableArrayList();
    ObservableList<Appointment> getNext7 = FXCollections.observableArrayList();
    ObservableList<User> Users = FXCollections.observableArrayList();
    ObservableList<Appointment> Appt = FXCollections.observableArrayList();
    ObservableList<Customer> Customers = FXCollections.observableArrayList();
    ObservableList<String> ApptMonths = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
    ObservableList<Appointment> next30Days = FXCollections.observableArrayList();
    ObservableList<Appointment> next7Days = FXCollections.observableArrayList();
    ObservableList<Appointment> schedule = FXCollections.observableArrayList();
    @FXML
    private ComboBox<User> userCB;
    @FXML
    private TableView<Appointment> userScheduleTbl;
    @FXML
    private ComboBox<String> monthCB;
    @FXML
    private Label apptMonthLbl;
    @FXML
    private TableColumn<Customer, String> apptDateCol;
    @FXML
    private TableColumn<Customer, String> apptTimeCol;
    @FXML
    private TableColumn<Customer, String> customerCol;

    private TextArea userTxt;
    @FXML
    private RadioButton viewNext7;
    @FXML
    private RadioButton viewNext30;
    @FXML
    private ToggleGroup toggle;
    @FXML
    private TableView<Appointment> testTable;
    @FXML
    private TableColumn<Customer, String> testApptCol;
    @FXML
    private TableColumn<Customer, String> testCustCol;
    @FXML
    private TableColumn<Customer, String> testApptDate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        apptTimeCol.setCellValueFactory(new PropertyValueFactory<>("apptTime"));
        apptDateCol.setCellValueFactory(new PropertyValueFactory<>("apptDate"));

        try {
            Appt.addAll(ApptTable.getAllAppointments());

        } catch (Exception ex) {
            Logger.getLogger(CustomerCalendarController.class.getName()).log(Level.SEVERE, null, ex);
        }
        userScheduleTbl.setItems(Appt);

        try {

            Users.addAll(User.getAllUsers());

        } catch (Exception ex) {
            Logger.getLogger(CustomerCalendarController.class.getName()).log(Level.SEVERE, null, ex);
        }

        userCB.setItems(Users);
        monthCB.setItems(Appointment.ApptMonths);

        //Lambda Practice
        //GeneralInterface sum = (n1,n2) -> n1 + n2;
        //System.out.println(sum.calculateSum(2, 5));
        //GeneralInterface message = () System.out.println("You have an appointment soon!");
        //message.displayMessage();
    }

    //report of my choice-displaying total number of appts
    @FXML
    private void calculateAll(ActionEvent event) {

        int apptCount = ApptTable.getApptCount(1);
        System.out.println(apptCount);
        TotLabelCnt.setText(String.valueOf(apptCount));

    }

    @FXML
    private void calculateMonth(ActionEvent event) {

        int index = monthCB.getSelectionModel().getSelectedIndex() + 1;
        int monthCount = ApptTable.getApptMonthCount(index);
        System.out.println(monthCount);
        apptMonthLbl.setText(String.valueOf(monthCount));

    }

    @FXML
    private void customerDataScreen(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("customerDataScreen.fxml"));

        Scene tableViewScene = new Scene(tableViewParent);

        Stage addPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addPartStage.setScene(tableViewScene);
        addPartStage.show();
    }

    public void onPull(ActionEvent actionEvent) {

        /*
        StringBuilder sb = new StringBuilder("");
        StringBuilder sb2 = new StringBuilder("");
        StringBuilder adr = new StringBuilder("");
        StringBuilder zip = new StringBuilder("");
        StringBuilder phn = new StringBuilder("");

        
        User select = userCB.getValue();

        if (select == null) {
            sb2.append("CB: null");
        } else {

            for (Appointment d : userScheduleTbl.getItems()) {
                if (d.getCustomerName() == select.getUserName()) {
                    
                    updateCityIdComboBox.setValue(c);
                    break;
                }
            }

            sb2.append(select.getCustomerName);
            adr.append(select.getAddress());
            zip.append(select.getPostalCode());
            phn.append(select.getPhone());
        }

        resultsLBL.setText(sb.toString());

        
        updateName.setText(sb2.toString());
        updateAddress.setText(adr.toString());
        updatePhone.setText(phn.toString());
        updateZip.setText(zip.toString());
         */
    }

    @FXML
    private void getUserSched(ActionEvent event) {

        try {
            userCB.getSelectionModel().getSelectedItem();

            int userId = userCB.getSelectionModel().getSelectedItem().getUserId();
            ApptTable.getUserSchedule(userId);

            schedule.clear();

            schedule.addAll(ApptTable.getUserSchedule(userId));

        } catch (Exception ex) {
            Logger.getLogger(CustomerCalendarController.class.getName()).log(Level.SEVERE, null, ex);
        }
        userScheduleTbl.setItems(schedule);

    }

    @FXML
    private void calculateNext7(ActionEvent event) {
        if (viewNext7.isSelected()) {
            try {

                next7Days.addAll(ApptTable.getNext7());
            } catch (Exception ex) {
                Logger.getLogger(CustomerCalendarController.class.getName()).log(Level.SEVERE, null, ex);
            }
            testApptCol.setCellValueFactory(new PropertyValueFactory<>("apptTime"));
            testCustCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            testApptDate.setCellValueFactory(new PropertyValueFactory<>("apptDate"));

            testTable.setItems(ApptTable.getNext7());
        }

    }

    @FXML
    private void calculateNext30(ActionEvent event) {
        if (viewNext30.isSelected()) {
            try {

                next30Days.addAll(ApptTable.getNext30());
            } catch (Exception ex) {
                Logger.getLogger(CustomerCalendarController.class.getName()).log(Level.SEVERE, null, ex);
            }
            testApptCol.setCellValueFactory(new PropertyValueFactory<>("apptTime"));
            testCustCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            testApptDate.setCellValueFactory(new PropertyValueFactory<>("apptDate"));
            testTable.setItems(ApptTable.getNext30());
        }
    }

   
}
