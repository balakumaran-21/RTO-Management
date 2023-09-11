package application;

import java.io.IOException;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dbManagement.*;

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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import rtoManagement.Owner;
import rtoManagement.Registration;
import rtoManagement.TransferRequest;
import rtoManagement.Vehicle;

public class SignInUserController implements Initializable{
	
	int id;
	String name;
	Parent root;
	Scene scene;
	Stage stage;
	
	@FXML
	VBox regRenwalVBox, viewRegBox, viewVehicleRejectBox, rejectRenewalVBox, transferRejectsVBox ;
	@FXML
	Label welcomeLabel, regnoTlabel, newOwnerLabel;
	@FXML
	Pane paneProfile, paneTransferRequest, paneRenewal, paneVehicleReg, paneViewReg, paneViewVehicleRejects, rejectedRenewalPane, transferRejectsPane;
	@FXML
	Button profileButton, renewalButton, transferButton, viewRegButton, regVehicleButton, viewVehicleRejectButton, viewRenewalRejectButton, viewTransferRejectButton;
	@FXML
	private ChoiceBox<String> vehicleType, fuelType, regNoChoiceBox;
	@FXML
	private ChoiceBox<Owner> ownerFieldChoiceBox;
	@FXML
	private TextField yearField,customNumberField;
	@FXML
	private Label infoLabel, ownerAddressLabel, ownerAgeLabel, ownerContactLabel, ownerNameLabel, ownerStateLabel, ownerDistrictLabel;
	@FXML
	private Pane reRegisterNo;
	@FXML
	private Button registerButton, reRegisterButton;
	@FXML
	private Label nameLabel, addressLabel, ageLabel, stateLabel, contactLabel, districtLabel;
	@FXML
	private Pane transferDetailsPane;
	@FXML
	private TextField nameField, ageField, stateField, contactField ,districtField;
    @FXML
    private TextArea addressField;

	
	
	Registration registration;
	Vehicle vehicle;
	String reg_no;
	
	private void clearField(TextField t) {
		t.setText(null);
	}
	
	private void clearField(ChoiceBox c) {
		c.setValue(null);
	}
	
	private void clearField(TextArea t) {
		t.setText(null);
	}
	
	public void displayWelcomeMessage(String name, int id) {
		this.name = name;
		this.id = id;
		welcomeLabel.setText("Welcome "+name +" (●'◡'●)");
		ownerFieldChoiceBox.getItems().addAll(populateOwnersList(id));
		regNoChoiceBox.getItems().addAll(populateRegsitrationsList(id));
	}
	
	String[] vehicleTypes = {"Two-Wheeler","Four-Wheeler","Light-Weight-Vehicles","Heavy-Weight-Vehicles"};
	String[] fuelTypes = {"Petrol","Diesel","CNG","Electric"};
	Owner[] ownersList = populateOwnersList(id);
	String[] registrationsList = populateRegsitrationsList(id);

	public Owner[] populateOwnersList(int id){
		ArrayList<Owner> owners = null; 
		try {
			System.out.println(id);
			owners = TransferRequestsDAO.showAllOwners(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Owner[] ownersList = new Owner[owners.size()];
		for(int i=0; i < owners.size();i++) {
			ownersList[i] =  owners.get(i);
		}
		return ownersList;
		
	}
	
	
	private String[] populateRegsitrationsList(int id) {
		
		ArrayList<String> regNos = null;
		try {
			regNos = TransferRequestsDAO.showAllRegistrations(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String[] regNoList = new String[regNos.size()];
		for(int i = 0; i < regNos.size(); i++) {
			regNoList[i] = regNos.get(i);
		}
		return regNoList;
		
	}


	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		
		try {
			renderProfileDetails();
			renderViewRejectedRequests();
			renderRejectedTransferRequests();
		} catch (SQLException | IOException e) {
			System.out.println("");
		}
		regnoTlabel.autosize();
		ownerFieldChoiceBox.autosize();
		vehicleType.getItems().addAll(vehicleTypes);
		fuelType.getItems().addAll(fuelTypes);
		reRegisterNo.setVisible(false);
		reRegisterButton.setVisible(false);
		transferDetailsPane.setVisible(false);
	}
	
	private void renderProfileDetails() throws SQLException {
		
		Owner owner = OwnerDAO.fetchOwnerDetails(id);
		nameField.setPromptText(owner.getName());
		ageField.setPromptText(new Integer(owner.getAge()).toString());
		contactField.setPromptText(owner.getContactNo());
		stateField.setPromptText(owner.getState());
		districtField.setPromptText(owner.getDistrict());
		addressField.setPromptText(owner.getAddress());
		
	}

	
	/******************* Render Panes and respective actions based on button clicks ***********************/
	
	public void handleClicks(ActionEvent event) throws SQLException, IOException {
		if(event.getSource() == profileButton) {
			profileButton.getStyleClass().add("button-clicked");
			renewalButton.getStyleClass().remove("button-clicked");
			transferButton.getStyleClass().remove("button-clicked");
			viewRegButton.getStyleClass().remove("button-clicked");
			regVehicleButton.getStyleClass().remove("button-clicked");
			viewVehicleRejectButton.getStyleClass().remove("button-clicked");
			viewRenewalRejectButton.getStyleClass().remove("button-clicked");
			viewTransferRejectButton.getStyleClass().remove("button-clicked");
			paneProfile.toFront();
			renderProfileDetails(event);
		}
		else if(event.getSource() == renewalButton) {
			renewalButton.getStyleClass().add("button-clicked");
			profileButton.getStyleClass().remove("button-clicked");
			transferButton.getStyleClass().remove("button-clicked");
			viewRegButton.getStyleClass().remove("button-clicked");
			regVehicleButton.getStyleClass().remove("button-clicked");
			viewVehicleRejectButton.getStyleClass().remove("button-clicked");
			viewRenewalRejectButton.getStyleClass().remove("button-clicked");
			viewTransferRejectButton.getStyleClass().remove("button-clicked");
			renderRenewableRegistrations(event);
			paneRenewal.toFront();
		}
		else if(event.getSource() == transferButton) {
			transferButton.getStyleClass().add("button-clicked");
			profileButton.getStyleClass().remove("button-clicked");
			renewalButton.getStyleClass().remove("button-clicked");
			viewRegButton.getStyleClass().remove("button-clicked");
			regVehicleButton.getStyleClass().remove("button-clicked");
			viewVehicleRejectButton.getStyleClass().remove("button-clicked");
			viewRenewalRejectButton.getStyleClass().remove("button-clicked");
			viewTransferRejectButton.getStyleClass().remove("button-clicked");
			paneTransferRequest.toFront();
		}
		else if(event.getSource() == viewRegButton) {
			viewRegButton.getStyleClass().add("button-clicked");
			profileButton.getStyleClass().remove("button-clicked");
			renewalButton.getStyleClass().remove("button-clicked");
			transferButton.getStyleClass().remove("button-clicked");
			regVehicleButton.getStyleClass().remove("button-clicked");
			viewVehicleRejectButton.getStyleClass().remove("button-clicked");
			viewRenewalRejectButton.getStyleClass().remove("button-clicked");
			viewTransferRejectButton.getStyleClass().remove("button-clicked");
			paneViewReg.toFront();
			renderViewRegistrations(event);
		}
		else if(event.getSource() == regVehicleButton) {
			regVehicleButton.getStyleClass().add("button-clicked");
			profileButton.getStyleClass().remove("button-clicked");
			renewalButton.getStyleClass().remove("button-clicked");
			transferButton.getStyleClass().remove("button-clicked");
			viewRegButton.getStyleClass().remove("button-clicked");
			viewVehicleRejectButton.getStyleClass().remove("button-clicked");
			viewRenewalRejectButton.getStyleClass().remove("button-clicked");
			viewTransferRejectButton.getStyleClass().remove("button-clicked");
			paneVehicleReg.toFront();
		}
		else if(event.getSource() == viewVehicleRejectButton) {
			viewVehicleRejectButton.getStyleClass().add("button-clicked");
			profileButton.getStyleClass().remove("button-clicked");
			renewalButton.getStyleClass().remove("button-clicked");
			transferButton.getStyleClass().remove("button-clicked");
			viewRegButton.getStyleClass().remove("button-clicked");
			regVehicleButton.getStyleClass().remove("button-clicked");
			viewRenewalRejectButton.getStyleClass().remove("button-clicked");
			viewTransferRejectButton.getStyleClass().remove("button-clicked");
			paneViewVehicleRejects.toFront();
			renderViewRejectedRequests(event);
		}
		else if(event.getSource() == viewRenewalRejectButton) {
			viewRenewalRejectButton.getStyleClass().add("button-clicked");
			profileButton.getStyleClass().remove("button-clicked");
			renewalButton.getStyleClass().remove("button-clicked");
			transferButton.getStyleClass().remove("button-clicked");
			viewRegButton.getStyleClass().remove("button-clicked");
			regVehicleButton.getStyleClass().remove("button-clicked");
			viewVehicleRejectButton.getStyleClass().remove("button-clicked");
			viewTransferRejectButton.getStyleClass().remove("button-clicked");
			rejectedRenewalPane.toFront();
			renderRejectedRenewableRequests(event);
		}
		else if(event.getSource() == viewTransferRejectButton) {
			viewTransferRejectButton.getStyleClass().add("button-clicked");
			profileButton.getStyleClass().remove("button-clicked");
			renewalButton.getStyleClass().remove("button-clicked");
			transferButton.getStyleClass().remove("button-clicked");
			viewRegButton.getStyleClass().remove("button-clicked");
			regVehicleButton.getStyleClass().remove("button-clicked");
			viewVehicleRejectButton.getStyleClass().remove("button-clicked");
			viewRenewalRejectButton.getStyleClass().remove("button-clicked");
			transferRejectsPane.toFront();
			renderRejectedTransferRequests(event);
		}
	}
	
	/******************************************************************************************************/
	
	/************************** Profile details code ******************************************************/
	
	public void renderProfileDetails(ActionEvent event) throws SQLException{
		
		Owner owner = OwnerDAO.fetchOwnerDetails(id);
		nameField.setPromptText(owner.getName());
		ageField.setPromptText(new Integer(owner.getAge()).toString());
		contactField.setPromptText(owner.getContactNo());
		stateField.setPromptText(owner.getState());
		districtField.setPromptText(owner.getDistrict());
		addressField.setPromptText(owner.getAddress());
		
	}
	
	private String textFieldValue(TextField t, String variable){
		variable = (t.getText().isBlank())?t.getPromptText():t.getText();
		return variable;
	}
	
	private int textFieldValue(TextField t, int var) {
		var = (t.getText().isBlank())?new Integer(t.getPromptText()):new Integer(t.getText());
		return var;
	}
	
	private String textFieldValue(TextArea t, String var) {
		var = (t.getText().isBlank())?t.getPromptText():t.getText();
		return var;
	}
	
	public void updateProfileDetails(ActionEvent event) throws SQLException {
		
		String name = null, contactNo = null, state = null, district = null,address = null;
		int age = 0;
		
		name = textFieldValue(nameField, name);
		age = textFieldValue(ageField, age);
		contactNo = textFieldValue(contactField, contactNo);
		state = textFieldValue(stateField, state);
		district = textFieldValue(districtField, district);
		address = textFieldValue(addressField, address);
		
		Alert updationAlert = new Alert(AlertType.CONFIRMATION);
		updationAlert.setTitle("Updaion Alert");
		updationAlert.setHeaderText("Are you sure you want to update the details");
		
		if(updationAlert.showAndWait().get() == ButtonType.OK) {			
			OwnerDAO.updateOwnerDetails(id, name, age, contactNo, state, district, address);
			updationAlert = new Alert(AlertType.INFORMATION);
			updationAlert.setTitle("Updation successfull");
			updationAlert.setHeaderText("User information updated successfully");
			if(updationAlert.showAndWait().get() == ButtonType.OK) {
				renderProfileDetails(event);
			}
		}
		
		clearField(nameField);
		clearField(ageField);
		clearField(contactField);
		clearField(stateField);
		clearField(districtField);
		clearField(addressField);
	}
	
	


	/******************************************************************************************************/
	
	/**************************** Called when view Registrations button is clicked ************************/
	
	public void renderViewRegistrations() throws SQLException, IOException {
		
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
	
	/******************************************************************************************************/
	
	/********************************* Called when view  Reject Requests **********************************/
	
	public void renderViewRejectedRequests() throws SQLException, IOException {

		ArrayList<Registration> rejectRegistrations = RegistrationDAO.viewOwnerRejects(id);
		ArrayList<Vehicle> rejectVehicles = RegistrationDAO.viewVehicleRejects(id);
		ArrayList<String> rejectRemarks = RegistrationDAO.getRemarks(id);

		viewVehicleRejectBox.getChildren().clear();
		for(int i = 0; i < rejectRegistrations.size(); i++) {
			Registration registration = rejectRegistrations.get(i);
    		Vehicle vehicle = rejectVehicles.get(i);
    		String remark = rejectRemarks.get(i);
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewVehicleRejectRequestsCard.fxml"));
    		Pane viewRejectsCard = loader.load();
    		ViewVehicleRejectRequestsCardController viewRejectRequestsCardController = loader.getController();
    		viewRejectRequestsCardController.setData(registration,vehicle,remark);
    		viewVehicleRejectBox.getChildren().add(viewRejectsCard);
    		
		}
	}
	
	public void renderViewRejectedRequests(ActionEvent event) throws SQLException, IOException {

		ArrayList<Registration> rejectRegistrations = RegistrationDAO.viewOwnerRejects(id);
		ArrayList<Vehicle> rejectVehicles = RegistrationDAO.viewVehicleRejects(id);
		ArrayList<String> rejectRemarks = RegistrationDAO.getRemarks(id);

		viewVehicleRejectBox.getChildren().clear();
		for(int i = 0; i < rejectRegistrations.size(); i++) {
			Registration registration = rejectRegistrations.get(i);
    		Vehicle vehicle = rejectVehicles.get(i);
    		String remark = rejectRemarks.get(i);
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewVehicleRejectRequestsCard.fxml"));
    		Pane viewRejectsCard = loader.load();
    		ViewVehicleRejectRequestsCardController viewRejectRequestsCardController = loader.getController();
    		viewRejectRequestsCardController.setData(rejectRegistrations.get(i),rejectVehicles.get(i),rejectRemarks.get(i));
    		viewVehicleRejectBox.getChildren().add(viewRejectsCard);
		}
		
	}
	
	/******************************************************************************************************/
	
	/************************** Functions of requesting new registration **********************************/
	
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
			VehicleRequestsDAO.updateAdminSideRequests(registration,vehicle);
			continueRegAlert = new Alert(AlertType.INFORMATION);
			continueRegAlert.setTitle("Request Sucessfull");
			continueRegAlert.setHeaderText("Vehicle request has been raised sucessfully\n"
					+ "Wait for admin's approval");
			continueRegAlert.showAndWait();
			clearField(vehicleType);
			clearField(fuelType);
			clearField(yearField);
			
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
			clearField(vehicleType);
			clearField(fuelType);
			clearField(yearField);
			clearField(customNumberField);
		}else {
			return;
		}
		
	}
	
	/******************************************************************************************************/
	
	/******************************************* Create Transfer request **********************************/
	
	public void ChooseOwnerAndRegNo(ActionEvent event) {
		
		transferDetailsPane.setVisible(true);
		Owner owner = ownerFieldChoiceBox.getValue();
		regnoTlabel.setText(regNoChoiceBox.getValue());
		newOwnerLabel.setText(owner.getName());
		
		nameLabel.setText("Name: " +owner.getName());
		ageLabel.setText("Age: "+new Integer(owner.getAge()).toString());
		contactLabel.setText("Contact number: "+owner.getContactNo());
		stateLabel.setText("State: "+owner.getState());
		districtLabel.setText("District: "+owner.getDistrict());
		addressLabel.setText("Address: "+owner.getAddress());
		
	}
	
	public void raiseTransferRequest(ActionEvent event) throws SQLException {
		String regNo = regnoTlabel.getText();
		int newOwnerID = ownerFieldChoiceBox.getValue().getOwnerID();
		int currOwnerID = id;
		
		if(TransferRequestsDAO.alreadyPending(new TransferRequest(currOwnerID, newOwnerID, regNo))) {
			Alert requestPresent = new Alert(AlertType.WARNING);
			requestPresent.setTitle("Request raised Already");
			requestPresent.setHeaderText("The transfer request has been raised already\n"
					+ "Wait for admin's approval");
			requestPresent.showAndWait();
			return;
		}
		
		Alert requestConfirmation = new Alert(AlertType.CONFIRMATION);
		requestConfirmation.setTitle("Transfer Request confirmation");
		requestConfirmation.setHeaderText("Do you want to raise a transfer request with the following details");
		requestConfirmation.setContentText("Click OK if you have cross verified with the respective owner details");
		Alert requestSucess = new Alert(AlertType.INFORMATION);
		
		if(requestConfirmation.showAndWait().get() == ButtonType.OK) {
			
			TransferRequest transferRequest = new TransferRequest(currOwnerID, newOwnerID, regNo);
			TransferRequestsDAO.requestTranferToAdmin(transferRequest);
			
			requestSucess.setTitle("Request successfull");
			requestSucess.setHeaderText("The request for transferring "+regNo+" is raised");
			requestSucess.setContentText("Wait for admin's approval");
			if(requestSucess.showAndWait().get() == ButtonType.OK) {
				transferDetailsPane.setVisible(false);
			}
			
		}
		else {
			
			requestSucess.setTitle("Request failed");
			requestSucess.setHeaderText("The request for transferring "+regNo+" failed");
			requestSucess.showAndWait();
			
		}	
		
	}
	
	


	/******************************************************************************************************/
	
	/********************** Get Details of registrations that are to be renewed ***************************/
	
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
	
	/******************************************************************************************************/
	
	/************************* Render rejected renewable requests *****************************************/
	public void renderRejectedRenewableRequests() throws SQLException, IOException {
		
		ArrayList<Registration> registrations = RenewalRequestDAO.showRejectedRenewableRegistrationsToUser(id);
		ArrayList<Vehicle> vehicles  = RenewalRequestDAO.showRejectedRenewableVehiclesToUser(id);
		ArrayList<String> remarks = RenewalRequestDAO.showRejectedRenewableRemarksToUser(id);
		rejectRenewalVBox.getChildren().clear();
		
		for(int i = 0; i < registrations.size(); i++) {
			Registration registration = registrations.get(i);
			Vehicle vehicle = vehicles.get(i);
			String remark = remarks.get(i);
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("RenewalRejectCard.fxml"));
    		Pane viewRejectsCard = loader.load();
    		RenewalRejectCardController renewalRejectCardController = loader.getController();
    		renewalRejectCardController.setData(registration,vehicle,remark);
    		rejectRenewalVBox.getChildren().add(viewRejectsCard);
		}
		
	}
	public void renderRejectedRenewableRequests(ActionEvent event) throws SQLException, IOException {
		
		ArrayList<Registration> registrations = RenewalRequestDAO.showRejectedRenewableRegistrationsToUser(id);
		ArrayList<Vehicle> vehicles  = RenewalRequestDAO.showRejectedRenewableVehiclesToUser(id);
		ArrayList<String> remarks = RenewalRequestDAO.showRejectedRenewableRemarksToUser(id);
		rejectRenewalVBox.getChildren().clear();
		
		for(int i = 0; i < registrations.size(); i++) {
			Registration registration = registrations.get(i);
			Vehicle vehicle = vehicles.get(i);
			String remark = remarks.get(i);
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("RenewalRejectCard.fxml"));
    		Pane viewRejectsCard = loader.load();
    		RenewalRejectCardController renewalRejectCardController = loader.getController();
    		renewalRejectCardController.setData(registration,vehicle,remark);
    		rejectRenewalVBox.getChildren().add(viewRejectsCard);
		}
		
	}
	
	/******************************************************************************************************/
	
	/************************************** Render rejected transfer requests *****************************/
	
	public void renderRejectedTransferRequests() throws SQLException, IOException {
		
		ArrayList<TransferRequest> transferRequests = TransferRequestsDAO.showAllRejectedTransferRequests(id);
		ArrayList<String> remarks = TransferRequestsDAO.showAllRejectedRemarksTransferRequests(id);
		transferRejectsVBox.getChildren().clear();
		for(int i = 0; i < transferRequests.size();i++) {
			
			TransferRequest transferRequest = transferRequests.get(i);
			String remark = remarks.get(i);
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("RejectTransferRequestCard.fxml"));
    		Pane viewRejectsCard = loader.load();
    		RejectTransferRequestCardController rejectTransferRequestCardController = loader.getController();
    		rejectTransferRequestCardController.setData(transferRequest,remark);
    		transferRejectsVBox.getChildren().add(viewRejectsCard);
			
		}
		
	}
	
	public void renderRejectedTransferRequests(ActionEvent event) throws SQLException, IOException {
		
		ArrayList<TransferRequest> transferRequests = TransferRequestsDAO.showAllRejectedTransferRequests(id);
		ArrayList<String> remarks = TransferRequestsDAO.showAllRejectedRemarksTransferRequests(id);
		transferRejectsVBox.getChildren().clear();
		for(int i = 0; i < transferRequests.size();i++) {
			
			TransferRequest transferRequest = transferRequests.get(i);
			String remark = remarks.get(i);
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("RejectTransferRequestCard.fxml"));
    		Pane viewRejectsCard = loader.load();
    		RejectTransferRequestCardController rejectTransferRequestCardController = loader.getController();
    		rejectTransferRequestCardController.setData(transferRequest,remark);
    		transferRejectsVBox.getChildren().add(viewRejectsCard);
			
		}
		
	}
	
	/******************************************************************************************************/
	
	/******************************** Logout Button action block *******************************************/
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
	/******************************************************************************************************/
}
