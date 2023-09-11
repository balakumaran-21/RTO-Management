package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dbManagement.RenewalRequestDAO;
import dbManagement.TransferRequestsDAO;
import dbManagement.VehicleRequestsDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import rtoManagement.Owner;
import rtoManagement.Vehicle;
import rtoManagement.Registration;
import rtoManagement.TransferRequest;

public class GeneratedReportController implements Initializable{
		
	Owner owner;
	
	@FXML
	private VBox newRegVBox, renewRegVBox, ownerShipVBox;

    @FXML
    private ChoiceBox<String> regStatus, renewalStatus, transferStatus;
    
    @FXML
    private Label userIdLabel, userAddressLabel, userAgeLabel, userContactLabel, userDistrictLabel, userNameLabel, userStateLabel;
	
    String[] statusList = {"All","Pending","Accepted","Rejected"};
    
    
    @Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
    	
    	regStatus.getItems().addAll(statusList);
    	renewalStatus.getItems().addAll(statusList);
    	transferStatus.getItems().addAll(statusList);
    	regStatus.setValue(statusList[0]);
    	renewalStatus.setValue(statusList[0]);
    	transferStatus.setValue(statusList[0]);
    	
	}
    
	public void setOwnerdetails(Owner owner) throws IOException, SQLException {
		this.owner = owner;
		setData();
		renderAllNewRegDetails();
		renderAllRenewalRegDetails();
		renderAllTransferRequests();
	}
    
	public Owner getOwnerDetails() {
		return owner;
	}
	
	private void setData() {
		
		userIdLabel.setText(new Integer(owner.getOwnerID()).toString());
		userNameLabel.setText(owner.getName());
		userAgeLabel.setText(""+owner.getAge());
		userContactLabel.setText(owner.getContactNo());
		userStateLabel.setText(owner.getState());
		userDistrictLabel.setText(owner.getDistrict());
		userAddressLabel.setText(owner.getAddress());
		
	}
	
	
	public void renderAllNewRegDetails() throws IOException, SQLException {
		
		ArrayList<Registration> registrations = VehicleRequestsDAO.getRegistrationDetails(owner.getOwnerID());
		ArrayList<Vehicle> vehicles = VehicleRequestsDAO.getVehicleDetails(owner.getOwnerID());
		newRegVBox.getChildren().clear();
    	for( int i = 0; i < registrations.size(); i++) {
    		Registration registration = registrations.get(i);
    		Vehicle vehicle = vehicles.get(i);
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistrationCard.fxml"));
    		Pane newRegcard = loader.load();
    		RegistrationCardController registrationCardController = loader.getController();
    		registrationCardController.setData(registration,vehicle);
    		newRegVBox.getChildren().add(newRegcard);
    	}
    	
	}
	
	public void renderNewRegDetails(ActionEvent event) throws SQLException, IOException {
		
		if(regStatus.getValue().equals("All")) {
			renderAllNewRegDetails();
			return;
		}
		ArrayList<Registration> registrations = VehicleRequestsDAO.getRegistrationDetails(owner.getOwnerID(),regStatus.getValue());
		ArrayList<Vehicle> vehicles = VehicleRequestsDAO.getVehicleDetails(owner.getOwnerID(), regStatus.getValue());
		
		if(registrations.isEmpty()) {
			newRegVBox.getChildren().clear();
			Label label = new Label("No registrations of status "+ regStatus.getValue());
			label.setTranslateX(320);
			label.setTranslateY(200);
			label.setTextFill(Color.valueOf("#dc75f8"));
			newRegVBox.getChildren().add(label);
			return;
		}
		
		newRegVBox.getChildren().clear();
    	for( int i = 0; i < registrations.size(); i++) {
    		Registration registration = registrations.get(i);
    		Vehicle vehicle = vehicles.get(i);
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistrationCard.fxml"));
    		Pane newRegcard = loader.load();
    		RegistrationCardController registrationCardController = loader.getController();
    		registrationCardController.setData(registration,vehicle);
    		newRegVBox.getChildren().add(newRegcard);
    	}
	}

	public void renderAllRenewalRegDetails() throws IOException, SQLException {
		
		ArrayList<Registration> registrations = RenewalRequestDAO.getRegistrationDetails(owner.getOwnerID());
		ArrayList<Vehicle> vehicles = RenewalRequestDAO.getVehicleDetails(owner.getOwnerID());
		renewRegVBox.getChildren().clear();
    	for( int i = 0; i < registrations.size(); i++) {
    		Registration registration = registrations.get(i);
    		Vehicle vehicle = vehicles.get(i);
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("RenewalCard.fxml"));
    		Pane newRegcard = loader.load();
    		RenewalCardController renewalCardController = loader.getController();
    		renewalCardController.setData(registration,vehicle);
    		renewRegVBox.getChildren().add(newRegcard);
    	}
    	
	}
	
	public void renderRenewalRegDetails(ActionEvent event) throws SQLException, IOException {
		
		if(renewalStatus.getValue().equals("All")) {
			renderAllRenewalRegDetails();
			return;
		}
		ArrayList<Registration> registrations = RenewalRequestDAO.getRegistrationDetails(owner.getOwnerID(),renewalStatus.getValue());
		ArrayList<Vehicle> vehicles = RenewalRequestDAO.getVehicleDetails(owner.getOwnerID(), renewalStatus.getValue());
		
		if(registrations.isEmpty()) {
			renewRegVBox.getChildren().clear();
			Label label = new Label("No registrations of status "+ renewalStatus.getValue());
			label.setTranslateX(320);
			label.setTranslateY(200);
			label.setTextFill(Color.valueOf("#dc75f8"));
			renewRegVBox.getChildren().add(label);
			return;
		}
		
		renewRegVBox.getChildren().clear();
    	for( int i = 0; i < registrations.size(); i++) {
    		Registration registration = registrations.get(i);
    		Vehicle vehicle = vehicles.get(i);
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("RenewalCard.fxml"));
    		Pane newRegcard = loader.load();
    		RenewalCardController renewalCardController = loader.getController();
    		renewalCardController.setData(registration,vehicle);
    		renewRegVBox.getChildren().add(newRegcard);
    	}
	}
	
	
	public void renderAllTransferRequests() throws SQLException, IOException {
		ArrayList<TransferRequest> transferRequests = TransferRequestsDAO.getAllTransferRequests(owner.getOwnerID());
		ownerShipVBox.getChildren().clear();
		for(TransferRequest transferRequest : transferRequests) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("TransferCard.fxml"));
			Pane tranferReq = loader.load();
			TransferCardController transferCardController = loader.getController();
			transferCardController.setData(transferRequest);
			ownerShipVBox.getChildren().add(tranferReq);
		}
	}
	
	public void renderTransferRequests(ActionEvent event) throws IOException, SQLException {
		
		if(transferStatus.getValue().equals("All")) {
			renderAllTransferRequests();
			return;
		}
		
		ArrayList<TransferRequest> transferRequests = TransferRequestsDAO.getTransferRequests(owner.getOwnerID(),transferStatus.getValue());
		if(transferRequests.isEmpty()) {
				ownerShipVBox.getChildren().clear();
				Label label = new Label("No requests of status "+ transferStatus.getValue());
				label.setTranslateX(320);
				label.setTranslateY(200);
				label.setTextFill(Color.valueOf("#dc75f8"));
				ownerShipVBox.getChildren().add(label);
				return;
		}
		ownerShipVBox.getChildren().clear();
		for(TransferRequest transferRequest : transferRequests) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("TransferCard.fxml"));
			Pane tranferReq = loader.load();
			TransferCardController transferCardController = loader.getController();
			transferCardController.setData(transferRequest);
			ownerShipVBox.getChildren().add(tranferReq);
		}
	}
	
}
