/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view_controller;

import DAO.AddressDao;
import DAO.AppointmentDao;
import DAO.CityDao;
import DAO.CountryDao;
import DAO.CustomerDao;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Address;
import model.Appointment;
import model.City;
import model.Country;
import model.Customer;
import model.User;
import utilities.DBConnection;
import utilities.DBQuery;
import utilities.Time;

/**
 * FXML Controller class
 *
 * @author brown
 */
public class MainScreenController implements Initializable {

    @FXML
    private TableView<Appointment> appointments_table;
    @FXML
    private RadioButton Week_View;
    @FXML
    private ToggleGroup ViewByWeekMonth;
    @FXML
    private RadioButton Month_View;
    @FXML
    private TextField name;
    @FXML
    private TextField phone;
    @FXML
    private TextField zip;
    @FXML
    private TextField state;
    @FXML
    private TextField street;
    @FXML
    private TextField city;
    @FXML
    private Button cancel;
    @FXML
    private Button save;
    @FXML
    private Label user_fields_label;

    
    private boolean disabled_customer_data = true;
    @FXML
    private Label error_customer_label;
    @FXML
    private Label username_label;
    @FXML
    private ListView<String> customers_list;
    
    ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    ObservableList<String> allCustomerNames = FXCollections.observableArrayList();
    ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    private User u;
    
    private int currentUserId;
    int selectedindex;
    int lastClicked = -1;
    int appointmentlastClicked = -1;
    
    @FXML
    private TextField country;
    @FXML
    private TableColumn<Appointment, LocalDateTime> start;
    @FXML
    private TableColumn<Appointment, String> title;
    @FXML
    private TableColumn<Appointment, LocalDateTime> end;
    @FXML
    private TableColumn<Appointment, String> customerNameTable;
    @FXML
    private TableColumn<Appointment, String> type;
    @FXML
    private TableColumn<Appointment, String> location;
    @FXML
    private TableColumn<Appointment, String> description;
    @FXML
    private TableColumn<Appointment, String> contact;
    @FXML
    private TableColumn<Appointment, String> url;
    private String userName;
    private String loginTime;
    @FXML
    private TextField active;
    @FXML
    private Button add_customer;
    @FXML
    private Button update_customer;
    @FXML
    private AnchorPane modifyAppointmentView;
    @FXML
    private TextField appointmentCustomerName;
    @FXML
    private TextField appointmentTitle;
    @FXML
    private TextField appointmentLocation;
    @FXML
    private TextField appointmentContact;
    @FXML
    private TextField appointmentType;
    @FXML
    private TextField appointmentURL;
    @FXML
    private Button appointmentCancel;
    @FXML
    private Button appointmentSave;
    @FXML
    private TextArea appointmentDescription;
    @FXML
    private DatePicker startDate;
    @FXML
    private ComboBox<Integer> startHour;
    @FXML
    private ComboBox<Integer> startMinute;
    @FXML
    private ComboBox<String> startAMPM;
    @FXML
    private DatePicker endDate;
    @FXML
    private ComboBox<Integer> endHour;
    @FXML
    private ComboBox<Integer> endMinute;
    @FXML
    private ComboBox<String> endAMPM;
    @FXML
    private Label error_appointment_label;
    @FXML
    private Button add_appointment;
    @FXML
    private Button update_appointment;
    @FXML
    private Label timeUntilNextAppointment;
    @FXML
    private RadioButton All_View;
    
    public MainScreenController(int uId, String userName) {
        currentUserId = uId;
        this.userName = userName;
    }
    
    
    
    
    /**
     * Initializes the controller class.
     */    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        // Is ArrayIndexOutOfBoundsException: -1 because the listener?
        
// Need to fix this time -------------------------------------------------------------        
        loginTime = Time.currentDateTime();
        long time = AppointmentDao.getNextAppointmentStartsIn(currentUserId);
        System.out.println(time + " minutes until next meeting...");
        if (time <= 15){
            timeUntilNextAppointment.setText(time + " minutes until next Appointment!");
        }
// ------------------------------------------------------------------------------------------                
        startDate.setValue(LocalDate.now());
        endDate.setValue(LocalDate.now());
        
        for(int i=1; i<13; i++){
            startHour.getItems().add(i);            
            endHour.getItems().add(i);
        }
                for(int i=0; i<60; i++){
            startMinute.getItems().add(i);           
            endMinute.getItems().add(i);            
        }
        startAMPM.getItems().addAll("AM", "PM");
        endAMPM.getItems().addAll("AM", "PM");
        
        
        set_customers_list();
        set_appointment_table();
        set_username_label("Logged in as: " + userName);
        //listen for selection model
        
        customers_list.getSelectionModel().selectedItemProperty().addListener((v, oldvalue, newvalue) -> {
            set_selection_fields();
            //System.out.println(newvalue);
                });
        
        add_customer.setOnAction(e->lastClicked=1);
        update_customer.setOnAction(e->lastClicked=2);
        
        add_appointment.setOnAction(e->appointmentlastClicked=1);
        update_appointment.setOnAction(e->appointmentlastClicked=2);                
        
    }
    
    public void set_appointment_table() {
        
        //Sets up appointment table
        
        RadioButton selectedRadioButton = (RadioButton) ViewByWeekMonth.getSelectedToggle();
        String toogleGroupValue = selectedRadioButton.getText();
        
        
        if(toogleGroupValue.equalsIgnoreCase("All")){
            System.out.println("You have ALL selected......");       
            ObservableList<Appointment> list = AppointmentDao.getAllAppointmentsJoined(currentUserId);        
            allAppointments = list;
        }
        else if(toogleGroupValue.equalsIgnoreCase("Week")){
            System.out.println("You have WEEK selected.......");
            ObservableList<Appointment> list = AppointmentDao.getWeekAppointmentsJoined(currentUserId);        
            allAppointments = list;            
        }
        else if(toogleGroupValue.equalsIgnoreCase("Month")){
            System.out.println("You have Month selected......");
            ObservableList<Appointment> list = AppointmentDao.getMonthAppointmentsJoined(currentUserId);        
            allAppointments = list;            
        }
        
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));        
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        customerNameTable.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        url.setCellValueFactory(new PropertyValueFactory<>("url")); 
        
        
        DateFormat dateFormat = DateFormat.getDateInstance();
        SimpleDateFormat dateForm = new SimpleDateFormat("hh:mm a  |  MMM, dd YYYY");
        
        
        
        appointments_table.setItems(allAppointments);
        
        
    }

    public void set_selection_fields() {
        clear_customer_fields();
        
        
        selectedindex = customers_list.getSelectionModel().getSelectedIndex();        
        //System.out.println(selectedindex);
        
        // set selection fields to selected customer
        Customer selectedCustomer = allCustomers.get(selectedindex);
        String customerName = selectedCustomer.getCustomerName();
        String customerActive = "No";
        if(selectedCustomer.isActive()) {
            customerActive = "Yes";
        }
        int addressId = selectedCustomer.getAddressId();
        
        System.out.println(addressId);
        
        ObservableList<Address> selectedAddressList = AddressDao.getAddress(addressId);
        Address selectedAddress = selectedAddressList.get(0);
        String street = selectedAddress.getAddress();
        // address2 can be used for a State
        String address2 = selectedAddress.getAddress2();
        String postalCode = selectedAddress.getPostalCode();
        String phone = selectedAddress.getPhone();
        
        int cityId = selectedAddress.getCityId();
        ObservableList<City> selectedCityList = CityDao.getCity(cityId);
        City selectedCity = selectedCityList.get(0);
        String city = selectedCity.getCity();
        
        int countryId = selectedCity.getCountryId();
        ObservableList<Country> selectedCountryList = CountryDao.getCountry(countryId);
        Country selectedCountry = selectedCountryList.get(0);
        String country = selectedCountry.getCountry();
        
                
        
        name.setText(customerName);
        this.street.setText(street);
        zip.setText(postalCode);
        this.phone.setText(phone);
        this.city.setText(city);
        state.setText(address2);
        this.country.setText(country);
        active.setText(customerActive); 
        
        appointmentCustomerName.setText(customerName);
        
    }

    public void set_customers_list() {
        //Sets up customer list        
        ObservableList<Customer> list = CustomerDao.getAllCustomers();
        
        allCustomers = list;
        for (int i = 0; i < list.size(); i++){
            Customer customerDB = list.get(i);
            String customerNameDB = customerDB.getCustomerName();            
            allCustomerNames.add(customerNameDB);
        }
        
        customers_list.setItems(allCustomerNames);
        
    }
    
    public void set_username_label(String text){
        username_label.setText(text);
    }
    
// ---------------------------------------------------------------------    
    public void set_userId(int uId){
        currentUserId = uId;
        System.out.println("In set_userId : " + uId);
    }
    
       

    @FXML
    private void Add_User_Clicked(javafx.scene.input.MouseEvent event) {        
        user_fields_label.setVisible(true);
        user_fields_label.setText("Fill in fields to add a new customer");
        
        active.setPromptText("yes or no");
        
        enable_customer_fields();
        save.setText("Save");
        save.setVisible(true);
        cancel.setVisible(true);
    }

    @FXML
    private void Delete_User_Clicked(javafx.scene.input.MouseEvent event) {
        Customer selectedCustomer = allCustomers.get(selectedindex);
        
        AppointmentDao.removeAppointmentsWithCustomerId(selectedCustomer.getCustomerId());
        appointments_table.getItems().clear();
        allAppointments.clear();
        set_appointment_table();        
        
        boolean customerDeleted = CustomerDao.deleteSelectedCustomer(selectedCustomer);
        if(customerDeleted) {
            clear_customer_fields();                
            allCustomerNames.clear();
            set_customers_list();
            
            if(allCustomers.size()>0){
                customers_list.getSelectionModel().select(0);
                
            }
        }        
        
    }

    @FXML
    private void Add_Appointment_Clicked(javafx.scene.input.MouseEvent event) throws SQLException {
        appointments_table.setVisible(false);
        modifyAppointmentView.setVisible(true);
        
        startHour.setValue(3);
        endHour.setValue(4);
        startMinute.setValue(30);
        endMinute.setValue(30);
        startAMPM.setValue("PM");
        endAMPM.setValue("PM");
        
    }
    
    @FXML
    private void Update_Appointment_Clicked(javafx.scene.input.MouseEvent event) {
        
        Appointment selectedAppointment = appointments_table.getSelectionModel().getSelectedItem();

                
        int customerId = selectedAppointment.getCustomerId();        
        int customerIndex = getCustomerIndex(customerId);  
        customers_list.getSelectionModel().select(customerIndex); // -----
                
        appointmentTitle.setText(selectedAppointment.getTitle());
        appointmentDescription.setText(selectedAppointment.getDescription());
        appointmentLocation.setText(selectedAppointment.getLocation());
        appointmentContact.setText(selectedAppointment.getContact());
        appointmentType.setText(selectedAppointment.getType());
        appointmentURL.setText(selectedAppointment.getUrl());
        
        //dateStringtoLocalDate
        LocalDateTime startCal = selectedAppointment.getStart();
        LocalDateTime endCal = selectedAppointment.getEnd();

        

        int sHour = startCal.getHour();
        String sampm;
        if(sHour < 12){
            sampm = "AM";
        }
        else {
            sampm = "PM";
        }
        
        if(sHour == 0){
            sHour = 12;
        }
        else if (sHour > 12){
            sHour = sHour - 12;
        }
        
        int eHour = endCal.getHour();       
        String eampm;
        if(eHour < 12){
            eampm = "AM";
        }
        else {
            eampm = "PM";
        }
        
        if(eHour == 0){
            eHour = 12;
        }
        else if (eHour > 12){
            eHour = eHour - 12;
        }        
        
        
        startDate.setValue(startCal.toLocalDate()); 
        startHour.setValue(sHour);
        startMinute.setValue(startCal.getMinute());
        startAMPM.setValue(sampm);
        
        endDate.setValue(endCal.toLocalDate()); 
        endHour.setValue(eHour);
        endMinute.setValue(endCal.getMinute());
        endAMPM.setValue(eampm);
        
        appointments_table.setVisible(false);
        modifyAppointmentView.setVisible(true);
        
    }

    @FXML
    private void Delete_Appointment_Clicked(javafx.scene.input.MouseEvent event) {
        Appointment selectedAppointment = appointments_table.getSelectionModel().getSelectedItem();
        AppointmentDao.deleteAppointment(selectedAppointment.getAppointmentId());
                
        appointments_table.getItems().clear();
        allAppointments.clear();
        set_appointment_table();        
        
    }

    private void disable_customer_fields() {
            name.setDisable(true);
            phone.setDisable(true);
            zip.setDisable(true);
            state.setDisable(true);
            street.setDisable(true);
            city.setDisable(true);
            country.setDisable(true);
            active.setDisable(true);
            disabled_customer_data = true;
    }

    private void enable_customer_fields() {
            name.setDisable(false);
            phone.setDisable(false);
            zip.setDisable(false);
            state.setDisable(false);
            street.setDisable(false);
            city.setDisable(false);
            country.setDisable(false);
            active.setDisable(false);
            disabled_customer_data = false;
    }

    @FXML
    private void Update_User_Clicked(javafx.scene.input.MouseEvent event) {        
        // if update user is clicked enable text feilds..
        if (disabled_customer_data == true) {
            enable_customer_fields();
        }
        save.setText("Update");
        save.setVisible(true);
        cancel.setVisible(true);
        
        user_fields_label.setVisible(true);
        user_fields_label.setText("Update Customers Information");
            
    }

    private void clear_customer_fields() {
        name.clear();
        phone.clear();
        zip.clear();
        state.clear();
        street.clear();
        city.clear();
        country.clear();
        active.clear();
        
        disable_customer_fields();
        save.setVisible(false);
        cancel.setVisible(false);
    }

    @FXML
    private void cancel_update(javafx.scene.input.MouseEvent event) {
        clear_customer_fields();
        set_selection_fields();
        user_fields_label.setVisible(true);
        user_fields_label.setText("Selected Customer Information");
    }

    private boolean check_valid_customer_fields() {
        TextField user_fields[] = {name, phone, zip, state, street, city, country, active};
        
        for (int i=0; i < user_fields.length; i++) {
            if (user_fields[i].getText().isEmpty()) {
                // Invalid input
                error_customer_label.setVisible(true);
                error_customer_label.setText("Error: All fields must be entered.");                
                return false;
            }
        }
        error_customer_label.setVisible(false);
        return true;        
    }
    
    private void saveCustomer() {

        String customerName = name.getText();
        String phone = this.phone.getText();
        String street = this.street.getText();
        String city =this.city.getText();
        String state = this.state.getText();
        String country = this.country.getText();
        String zip = this.zip.getText();

        int active = 0;
        String activeStr = this.active.getText().toLowerCase();
        if(activeStr.equals("yes")){
            active = 1;
        }

        CustomerDao.addCustomer(userName, customerName, phone, street, city, state, country, zip, active);                
    }
    
    private void updateCustomer() {

        int selectedCustomerId = allCustomers.get(selectedindex).getCustomerId();
        String customerName = name.getText();
        String phone = this.phone.getText();
        String street = this.street.getText();
        String city =this.city.getText();
        String state = this.state.getText();
        String country = this.country.getText();
        String zip = this.zip.getText();

        int active = 0;
        String activeStr = this.active.getText().toLowerCase();
        if(activeStr.equals("yes")){
            active = 1;
        }

        CustomerDao.updateCustomer(selectedCustomerId, userName, customerName, phone, street, city, state, country, zip, active);        

        
    }
    
    @FXML
    private void save_user_data(javafx.scene.input.MouseEvent event) {
    
// TODO: If statement to see if Add User or Update User was last selected. ===========================
// send username as a parameter
        
        
        if (check_valid_customer_fields()) {
            
            if(lastClicked == 1) {
                saveCustomer();
                customers_list.getSelectionModel().select(allCustomers.size()-1);
                                
            }
            else if (lastClicked == 2) {
                updateCustomer();
            }
            
            allCustomers.clear();
            
//        Will this help?  Platform.runLater(() -> { });  https://noblecodemonkeys.com/switching-to-the-gui-thread-in-javafx/
            
// ------------------------------------------------------------------
            allCustomerNames.clear(); // The error is here........
//--------------------------------------------------------------------- 

            set_customers_list();
            // Must add customer to the list and reset the page to view it
            
            disable_customer_fields();
            save.setVisible(false);
            cancel.setVisible(false);
        
            user_fields_label.setVisible(true);
            user_fields_label.setText("Selected Customer Information");        
        }        
        else {
            System.out.println("false... You need to enter something dude.");
        }

        
        
        /* I must prevent entering nonexistent or invalid customer data 
        
           Reject and present an error message if...
           DONE - If a text field is blank
           If a a number is given instead of a string for name
           If a string is given instead of a number for number for phone
           If the phone number is too few or too many digits.
        */

    }
    
    private boolean check_valid_appointment_fields() {
        TextField user_fields[] = {appointmentCustomerName, appointmentType};
        
        if(startDate.getValue() == null || endDate.getValue() == null) {
            error_appointment_label.setVisible(true);
            error_appointment_label.setText("Please choose a start and end date.");
            return false;
        }
        else if(appointmentCustomerName.getText().isEmpty()){
            error_appointment_label.setVisible(true);
            error_appointment_label.setText("Error: Customer must be selected.");
            return false;
        }
        else if(appointmentType.getText().isEmpty()){
            error_appointment_label.setVisible(true);
            error_appointment_label.setText("Error: Appointment Type must be entered.");
            return false;
        }        

        error_appointment_label.setVisible(false);
        return true;        
    }
    
    private void set_optional_empty_appointment_fields() {
        TextField user_fields[] = {appointmentTitle, appointmentLocation, appointmentContact, appointmentURL};
        
        for (int i=0; i < user_fields.length; i++) {
            if (user_fields[i].getText().isEmpty() || appointmentDescription.getText().isEmpty()) {

            }           
        }
        if(appointmentTitle.getText().isEmpty()) {
            appointmentTitle.setText("N/A");
        }        
        if(appointmentDescription.getText().isEmpty()) {
            appointmentDescription.setText("N/A");
        }
        if(appointmentLocation.getText().isEmpty()) {
            appointmentLocation.setText("N/A");
        }
        if(appointmentContact.getText().isEmpty()) {
            appointmentContact.setText("N/A");
        }
        if(appointmentURL.getText().isEmpty()) {
            appointmentURL.setText("N/A");
        }
    }    

    @FXML
    private void appointmentCancel(javafx.scene.input.MouseEvent event) {


        modifyAppointmentView.setVisible(false);                
        appointments_table.setVisible(true);
        error_appointment_label.setVisible(false);

    }
    
    private void saveAppointment() {

        int selectedCustomerId = allCustomers.get(selectedindex).getCustomerId();
        int userId = currentUserId;
        String appointmentTitle = this.appointmentTitle.getText();
        String appointmentDescription = this.appointmentDescription.getText();
        String appointmentLocation = this.appointmentLocation.getText();
        String appointmentContact = this.appointmentContact.getText();
        String appointmentType = this.appointmentType.getText();
        String appointmentUrl = this.appointmentURL.getText();
        
        LocalDate sDate = startDate.getValue();
        int sHour = startHour.getValue();
        int sMinute = startMinute.getValue();
        String sAMPM = startAMPM.getValue();
        LocalDate eDate = endDate.getValue();
        int eHour = endHour.getValue();
        int eMinute = endMinute.getValue();
        String eAMPM = endAMPM.getValue();
        
        String startTime = Time.dateToUTCString(sDate, sHour, sMinute, sAMPM);
        String endTime = Time.dateToUTCString(eDate, eHour, eMinute, eAMPM);        
        
        AppointmentDao.setAppointment(selectedCustomerId, userId, appointmentTitle, appointmentDescription, appointmentLocation, appointmentContact, appointmentType, appointmentUrl, startTime, endTime);       
               
        
    }
    
    public int getCustomerIndex(int customerId) {
        for(int i=0; i<allCustomers.size(); i++){
            Customer customer = allCustomers.get(i);
            if(customer.getCustomerId() == customerId) {
                return i;
            }
        }
        return -1;
    }
    
    public void updateAppointment(){
        
        Appointment selectedAppointment = appointments_table.getSelectionModel().getSelectedItem();
        
        int customerId = selectedAppointment.getCustomerId();
        String customerName = CustomerDao.getCustomerName(customerId);
        
        int selectedAppointmentId = selectedAppointment.getAppointmentId();
 //----------------------- allCustomers is the index 
        int customerIndex = getCustomerIndex(customerId);
  
//        customers_list.getSelectionModel().select(customerIndex); // -----
        customers_list.getSelectionModel().select(customerIndex); // -----
// ------------------       
        
        //appointmentCustomerName.setText(customerName);        
        
        int selectedCustomerId = allCustomers.get(selectedindex).getCustomerId();
        int userId = currentUserId;
        String appointmentTitle = this.appointmentTitle.getText();
        String appointmentDescription = this.appointmentDescription.getText();
        String appointmentLocation = this.appointmentLocation.getText();
        String appointmentContact = this.appointmentContact.getText();
        String appointmentType = this.appointmentType.getText();
        String appointmentUrl = this.appointmentURL.getText();
        
        LocalDate sDate = startDate.getValue();
        int sHour = startHour.getValue();
        int sMinute = startMinute.getValue();
        String sAMPM = startAMPM.getValue();
        LocalDate eDate = endDate.getValue();
        int eHour = endHour.getValue();
        int eMinute = endMinute.getValue();
        String eAMPM = endAMPM.getValue();
        
        String startTime = Time.dateToUTCString(sDate, sHour, sMinute, sAMPM);
        String endTime = Time.dateToUTCString(eDate, eHour, eMinute, eAMPM);        
        
        
        
        AppointmentDao.updateAppointment(selectedAppointmentId, selectedCustomerId, userId, appointmentTitle, appointmentDescription, appointmentLocation, appointmentContact, appointmentType, appointmentUrl, startTime, endTime);
    }

    @FXML
    private void appointmentSave(javafx.scene.input.MouseEvent event) {
        
        if(check_valid_appointment_fields()){
            set_optional_empty_appointment_fields();
            if(appointmentlastClicked == 1){
                saveAppointment();
                // do something like this customers_list.getSelectionModel().select(allCustomers.size()-1);
            }
            else if(appointmentlastClicked == 2){
                updateAppointment();
            }
                        
            appointments_table.getItems().clear();
            allAppointments.clear();
            set_appointment_table();
            
            
            modifyAppointmentView.setVisible(false);                
            appointments_table.setVisible(true);
            error_appointment_label.setVisible(false);
        }        
    }

    @FXML
    private void Toggle_Week_Clicked(javafx.scene.input.MouseEvent event) {
        appointments_table.getItems().clear();
        allAppointments.clear();
        set_appointment_table();         
    }
    
    @FXML
    private void Toggle_Month_Clicked(javafx.scene.input.MouseEvent event) {
        appointments_table.getItems().clear();
        allAppointments.clear();
        set_appointment_table();         
    }

    @FXML
    private void Toggle_All_Clicked(javafx.scene.input.MouseEvent event) {
        appointments_table.getItems().clear();
        allAppointments.clear();
        set_appointment_table();         
    }

    
}



// if meeting end is before the start produce error.
