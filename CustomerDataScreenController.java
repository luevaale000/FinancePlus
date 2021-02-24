/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinancePlus;

import Utils.DBConnection;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
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
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lueva
 */
public class CustomerDataScreenController implements Initializable {

    @FXML
    private Button logout;
    @FXML
    private Button calendar;
    @FXML
    private Button appt;
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn<Customer, String> idCol;
    @FXML
    private TableColumn<Customer, String> nameCol;
    @FXML
    private TableColumn<Customer, String> addressCol;
    @FXML
    private TableColumn<Customer, String> phoneCol;
    @FXML
    private Button add;
    @FXML
    private Button delete;
    @FXML
    private Button update;
    @FXML
    private TableColumn<Customer, String> zipCol;

    ObservableList<Customer> Customers = FXCollections.observableArrayList();
    ObservableList<City> City = FXCollections.observableArrayList();
    ObservableList<Address> Address = FXCollections.observableArrayList();
    @FXML
    private TextField nameInput;
    @FXML
    private TextField addressInput;
    @FXML
    private TextField zipInput;
    @FXML
    private TextField phoneInput;
    private TextField updateId;
    @FXML
    private TextField updateName;
    @FXML
    private TextField updateAddress;
    @FXML
    private TextField updateZip;
    @FXML
    private TextField updatePhone;
    @FXML
    private ComboBox<City> cityIdComboBox;
    @FXML
    private ComboBox<City> updateCityIdComboBox;

    /**
     * Initializes the controller class.
     */
    Customer selected;
    @FXML
    private Label resultsLBL;
    @FXML
    private ComboBox<Customer> selectCustBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        idCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));

        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        zipCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        try {
            Customers.addAll(CustomerTable.getAllCustomers());

        } catch (Exception ex) {
            Logger.getLogger(CustomerDataScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        customerTable.setItems(Customers);

        try {
            City.addAll(CustomerTable.getAllCities());

        } catch (Exception ex) {
            Logger.getLogger(CustomerDataScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

        cityIdComboBox.setItems(City);

        updateCityIdComboBox.setItems(City);

        selectCustBox.setItems(Customers);

    }

    @FXML
    private void updateCustomer() {
        
        
        if(updateName.getText().isEmpty() || updateAddress.getText().isEmpty() || updateZip.getText().isEmpty() || updatePhone.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Alert");
                alert.setHeaderText("You have a text field that is empty!");
                alert.setContentText("More data required");

                alert.showAndWait();
                return;
        }
        
        if(updateCityIdComboBox.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Alert");
                alert.setHeaderText("You have not selected Customer City!");
                alert.setContentText("City Selection Required");

                alert.showAndWait();
                return;
            
        }
        try {
            String address = updateAddress.getText();

            int cityId = updateCityIdComboBox.getValue().getCityId();

            String postalCode = updateZip.getText();
            String phone = updatePhone.getText();
            String customerName = updateName.getText();

            int customerId = selectCustBox.getValue().getCustomerId();
            int addressId = selectCustBox.getValue().getAddressId();
            CustomerTable.updateCustomer(address, cityId, postalCode, phone, customerName, customerId, addressId);

            Customers.clear();
            Customers.addAll(CustomerTable.getAllCustomers());
            customerTable.setItems(Customers);

            updateAddress.clear();
            updatePhone.clear();
            updateZip.clear();
            updateName.clear();
        } catch (Exception ex) {
            Logger.getLogger(CustomerDataScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void addCustomer(ActionEvent event) throws IOException {

        if(nameInput.getText().isEmpty() || addressInput.getText().isEmpty() || zipInput.getText().isEmpty() || phoneInput.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Alert");
                alert.setHeaderText("You have a text field that is empty!");
                alert.setContentText("More data required");

                alert.showAndWait();
                return;
        }
        
        if(cityIdComboBox.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Alert");
                alert.setHeaderText("You have not selected Customer City!");
                alert.setContentText("City Selection Required");

                alert.showAndWait();
                return;
            
        }
        try {

            String address = addressInput.getText();

            int cityId = cityIdComboBox.getValue().getCityId();

            String postalCode = zipInput.getText();
            String phone = phoneInput.getText();
            String customerName = nameInput.getText();
            CustomerTable.addCustomer(address, cityId, postalCode, phone, customerName);

            Customers.clear();
            Customers.addAll(CustomerTable.getAllCustomers());

        } catch (Exception ex) {

            Logger.getLogger(CustomerDataScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        customerTable.setItems(Customers);

        addressInput.clear();
        phoneInput.clear();
        zipInput.clear();
        nameInput.clear();

    }

    @FXML
    private void deleteCustomer(ActionEvent event) throws SQLException {

        try {
            customerTable.getSelectionModel().getSelectedItem();

            //int cityId =customerTable.getSelectionModel().getSelectedItem().getCityId();
            int addressId = customerTable.getSelectionModel().getSelectedItem().getAddressId();

            int customerId = customerTable.getSelectionModel().getSelectedItem().getCustomerId();

            CustomerTable.deleteCustomer(customerId, addressId);

            Customers.clear();

            Customers.addAll(CustomerTable.getAllCustomers());

        } catch (Exception ex) {
            Logger.getLogger(CustomerDataScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        customerTable.setItems(Customers);

    }

    @FXML
    private void mainScreen(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));

        Scene tableViewScene = new Scene(tableViewParent);

        Stage addPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addPartStage.setScene(tableViewScene);
        addPartStage.show();
    }

    @FXML
    private void customerCalendar(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("customerCalendar.fxml"));

        Scene tableViewScene = new Scene(tableViewParent);

        Stage addPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addPartStage.setScene(tableViewScene);
        addPartStage.show();
    }

    @FXML
    private void apptDataScreen(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("ApptDataScreen.fxml"));

        Scene tableViewScene = new Scene(tableViewParent);

        Stage addPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        addPartStage.setScene(tableViewScene);
        addPartStage.show();
    }

    @FXML
    public void updateCombo(ActionEvent actionEvent) {
        onPull(null);
    }

    public void onPull(ActionEvent actionEvent) {

        StringBuilder sb = new StringBuilder("");
        StringBuilder sb2 = new StringBuilder("");
        StringBuilder adr = new StringBuilder("");
        StringBuilder zip = new StringBuilder("");
        StringBuilder phn = new StringBuilder("");

        Customer select = selectCustBox.getValue();

        if (select == null) {
            sb2.append("CB: null");
        } else {

            for (City c : updateCityIdComboBox.getItems()) {
                if (c.getCityId() == select.getCityId()) {
                    updateCityIdComboBox.setValue(c);
                    break;
                }
            }
            sb2.append(select.getCustomerName());
            adr.append(select.getAddress());
            zip.append(select.getPostalCode());
            phn.append(select.getPhone());
        }

        resultsLBL.setText(sb.toString());

        updateName.setText(sb2.toString());
        updateAddress.setText(adr.toString());
        updatePhone.setText(phn.toString());
        updateZip.setText(zip.toString());

    }
}
