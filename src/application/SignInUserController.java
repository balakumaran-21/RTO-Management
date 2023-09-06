package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dbManagement.OwnerDAO;
import dbManagement.RegistrationDAO;
import dbManagement.RenewalRequestDAO;
import dbManagement.VehicleRequestsDAO;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import rtoManagement.Owner;
import rtoManagement.Registration;
import rtoManagement.Vehicle;

public class SignInUserController implements Initializable{
	
	int id;
	String name;
	Parent root;
	Scene scene;
	Stage stage;
	
	@FXML
	VBox regRenwalVBox, viewRegBox, viewRejectBox ;
	@FXML
	Label welcomeLabel;
	@FXML
	Pane paneProfile, paneTransfer, paneRenewal, paneVehicleReg, paneViewReg, paneViewRejects;
	@FXML
	Button profileButton, renewalButton, transferButton, viewRegButton, regVehicleButton, viewRejectButton;	
	@FXML
	private ChoiceBox<String> vehicleType, fuelType;
	@FXML
	private TextField yearField,customNumberField;
	@FXML
	private Label infoLabel, ownerAddressLabel, ownerAgeLabel, ownerContactLabel, ownerNameLabel, ownerStateLabel, ownerDistrictLabel;
	@FXML
	private Pane reRegisterNo;
	@FXML
	private Button registerButton, reRegisterButton;
	
	Registration registration;
	Vehicle vehicle;
	
	String[] vehicleTypes = {"Two-Wheeler","Four-Wheeler","Light-Weight-Vehicles","Heavy-Weight-Vehicles"};
	String[] fuelTypes = {"Petrol","Diesel","CNG","Electric"};
	String reg_no;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		vehicleType.getItems().addAll(vehicleTypes);
		fuelType.getItems().addAll(fuelTypes);
		reRegisterNo.setVisible(false);
		reRegisterButton.setVisible(false);
	}
	
	public void displayWelcomeMessage(String name, int id) {
		welcomeLabel.setText("Welcome "+name +" (●'◡'●)");
		this.name = name;
		this.id = id;
	}
	
	/******************* Render Panes and respective actions based on button clicks *****************/
	
	public void handleClicks(ActionEvent event) throws SQLException, IOException {
		if(event.getSource() == profileButton) {
			paneProfile.toFront();
			renderProfileDetails(event);
		}
		else if(event.getSource() == renewalButton) {
			renderRenewableRegistrations(event);
			paneRenewal.toFront();
		}
		else if(event.getSource() == transferButton) {
			paneTransfer.toFront();
		}
		else if(event.getSource() == viewRegButton) {
			paneViewReg.toFront();
			renderViewRegistrations(event);
		}
		else if(event.getSource() == regVehicleButton) {
			paneVehicleReg.toFront();
		}
		else if(event.getSource() == viewRejectButton) {
			paneViewRejects.toFront();
			renderViewRejectedRequests(event);
		}
	}
	
	/***************************************************************************/
	
	public void viewRegisteredVehicles(ActionEvent event) throws SQLException {
		ArrayList<Registration> registrations = RegistrationDAO.viewOwnerRegistrations(id);
		System.out.println("Welcome "+this.name+" your ID is: "+this.id);
		System.out.println("Vehicles registered by your name: ");
		for(Registration registration:registrations) {
			registration.getInfo();
		}
	}
	
	
	/************************** Profile details code *********************************************/
	
	public void renderProfileDetails(ActionEvent event) throws SQLException{
		Owner owner = OwnerDAO.fetchOwnerDetails(id);
		ownerNameLabel.setText("Name:" + owner.getName());
		ownerAgeLabel.setText("Age:" + owner.getAge());
		ownerContactLabel.setText("Contact Number: " + owner.getContactNo());
		ownerStateLabel.setText("State: " + owner.getState());
		ownerDistrictLabel.setText("District: " + owner.getDistrict());
		ownerAddressLabel.setText("Address: "+ owner.getAddress());
	}
	
	/**********************************************************************************************/
	
	/**************************** Called when view Registrations button is clicked 
	 * @throws IOException ****************/
	
	public void renderViewRegistrations(ActionEvent event) throws SQLException, IOException {
		ArrayList<Registration> ownerRegistrations = RegistrationDAO.viewOwnerRegistrations(id);
		ArrayList<Vehicle> ownerVehicles = RegistrationDAO.viewVehicleRegistrations(id);
		viewRegBox.getChildren().clear();
		for(int i = 0; i < ownerRegistrations.size(); i++) {
			Registration registration = ownerRegistrations.get(i);
    		Vehicle vehicle = ownerVehicles.get(i);
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewRegCard.fxml"));
    		HBox viewRegCard = loader.load();
    		ViewRegCardController viewRegCardController = loader.getController();
    		viewRegCardController.setData(registration,vehicle);
    		viewRegBox.getChildren().add(viewRegCard);
		}
		
	}
	
	/*********************************************************************************************/
	
	/********************************* View Reject Requests *************************************/
	
	public void renderViewRejectedRequests(ActionEvent event) throws SQLException, IOException {
		System.out.println("Rejected requests");
		ArrayList<Registration> rejectRegistrations = RegistrationDAO.viewOwnerRejects(id);
		ArrayList<Vehicle> rejectVehicles = RegistrationDAO.viewVehicleRejects(id);
		viewRejectBox.getChildren().clear();
		for(int i = 0; i < rejectRegistrations.size(); i++) {
			Registration registration = rejectRegistrations.get(i);
    		Vehicle vehicle = rejectVehicles.get(i);
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewRejectRequestsCard.fxml"));
    		HBox viewRejectsCard = loader.load();
    		ViewRejectRequestsCardController viewRejectRequestsCardController = loader.getController();
    		viewRejectRequestsCardController.setData(registration,vehicle);
    		viewRejectBox.getChildren().add(viewRejectsCard);
		}
	}
	
	/********************************************************************************************/
	
	/************************** Functions of requesting new registration *******************/
	
	public void raiseRequest(ActionEvent event) throws SQLException, IOException {
		//Request registration
		System.out.println(event.getSource());
		String vehicle_type = vehicleType.getValue();
		String  fuel_type = fuelType.getValue();
		int yearOfManufacture = Integer.parseInt(yearField.getText());
		vehicle = new Vehicle(vehicle_type,fuel_type,yearOfManufacture);
		registration = new Registration(id);
		VehicleRequestsDAO.setRequestfromUser(registration,vehicle);
		registration.getInfo();
		vehicle.getInfo();
		
		Alert continueRegAlert = new Alert(AlertType.CONFIRMATION);
		continueRegAlert.setTitle("Registration Alert");
		continueRegAlert.setHeaderText("Your Registration number is: "+ registration.getRegNo());
		continueRegAlert.setContentText("Click CANCEL to register with fancy number\n"
				+ "You can edit only the last four digits.");
		
		if(continueRegAlert.showAndWait().get() == ButtonType.OK) {
			System.out.println("Registration sucessfull");
			VehicleRequestsDAO.updateAdminSideRequests(registration,vehicle);
		}else{
			registerButton.setVisible(false);
			reRegisterNo.setVisible(true);
			reRegisterButton.setVisible(true);
		}
		
	}
	
	public void raiseReRequest(ActionEvent event) throws IOException, SQLException {
		// Registration when user enters a fancy number
		String number = customNumberField.getText();
		
		if(customNumberField.getText().equals("")) {
			infoLabel.setText("Every field is required");
			return;
		}else {
			infoLabel.setText("");
		}
		
		if(number.length() != 4) {
			infoLabel.setText("The number must exactly be 4 digits");
			return;
		}else {
			infoLabel.setText("");
		}
			
		Alert continueRegAlert = new Alert(AlertType.INFORMATION);
		continueRegAlert.setTitle("Registration Information");
		reg_no = registration.getRegNo().substring(0,5)+number;
		registration.setRegNo(reg_no);
		continueRegAlert.setHeaderText("Your Registration number is: "+ registration.getRegNo());
		continueRegAlert.setContentText("You request has been submitted. Wait until admin's approval");
		
		if(continueRegAlert.showAndWait().get() == ButtonType.OK) {
			System.out.println("Registration sucessfull");
			VehicleRequestsDAO.updateAdminSideRequests(registration,vehicle);
		}else {
			return;
		}
		
	}
	
	/*****************************************************************************************************/
	
	/********************** Get Details of registrations that are to be renewed **************************/
	
	public void renderRenewableRegistrations(ActionEvent event) throws IOException, SQLException {
		
		ArrayList<Registration> renewableRegistrations = RenewalRequestDAO.showExpiredRegistrations(id);
		ArrayList<Vehicle> renewableVehicles = RenewalRequestDAO.showExpiredVehicles(id);
		regRenwalVBox.getChildren().clear();
		for(int i = 0; i < renewableRegistrations.size(); i++) {
			Registration registration = renewableRegistrations.get(i);
    		Vehicle vehicle = renewableVehicles.get(i);
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("RenewRegCard.fxml"));
    		HBox renewRegCard = loader.load();
    		RenewRegCardController renewRegCardController = loader.getController();
    		renewRegCardController.setData(registration,vehicle);
    		regRenwalVBox.getChildren().add(renewRegCard);
		}
		
	}
	
	/****************************************************************************************************/
	
	/********************************Logout Button action block*******************************************/
	public void logout(ActionEvent event) throws IOException {

		Alert logoutAlert = new Alert(AlertType.CONFIRMATION);
		logoutAlert.setTitle("Logout");
		logoutAlert.setHeaderText("You are about to logout");
		logoutAlert.setContentText("Your information might not be saved");
		
		if(logoutAlert.showAndWait().get() == ButtonType.OK) {
			root = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();			
		}
	
	}
	/*********************************************************************************************/
}
