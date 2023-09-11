package application;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import dbManagement.RenewalRequestDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import rtoManagement.Registration;
import rtoManagement.Vehicle;

public class RenewRegCardController {
	Parent root;
	Stage stage;
	Scene scene;

	@FXML
	private Label expDateLabel, idLabel, regDateLabel, regNoLabel, vehicleTypeLabel, fuelTypeLabel, manYearLabel;
	@FXML
	private Button renewalDetailsButton;
    
	public void setData(Registration registration,Vehicle vehicle) {
    	
		idLabel.setText(new Integer(registration.getOwnerID()).toString());
    	regDateLabel.setText(registration.getDateOfreg().toString());
    	expDateLabel.setText(registration.getExpiryDate().toString());
    	regNoLabel.setText(registration.getRegNo());
    	vehicleTypeLabel.setText(vehicle.getVehicleType());
    	fuelTypeLabel.setText(vehicle.getFuelType());
    	manYearLabel.setText(new Integer(vehicle.getManufactureYear()).toString());
    	
    }
    
    public Registration getRegistrationData() {
    	
    	int ownerID = Integer.parseInt(idLabel.getText());
    	Date regDate = Date.valueOf(regDateLabel.getText());
    	Date expDate = Date.valueOf(expDateLabel.getText());
    	String regNo = regNoLabel.getText();
    	Registration registration = new Registration(ownerID, regDate, expDate, regNo);
    	return registration;
    	
    }
    
    public Vehicle getVehicleData() {
    	
    	int manYear = Integer.parseInt(manYearLabel.getText());
    	String vehicleType = vehicleTypeLabel.getText();
    	String fuelType = fuelTypeLabel.getText();		
    	Vehicle vehicle = new Vehicle(vehicleType,fuelType,manYear);
    	return vehicle;
    	
    }
    
    public void requestRenewal(ActionEvent event) throws SQLException, IOException {
    	Alert renewalReqAlert =new Alert(AlertType.CONFIRMATION);
    	renewalReqAlert.setTitle("Renewal request alert");
    	renewalReqAlert.setHeaderText("You are about to request for renewal of your registration");
    	renewalReqAlert.setContentText("Click OK to continue CANCEL to exit");
    	if(renewalReqAlert.showAndWait().get() == ButtonType.OK) {
    		if(RenewalRequestDAO.isPresent(getRegistrationData().getRegNo())) {
    			System.out.println("found");
    			renewalReqAlert =new Alert(AlertType.WARNING);  
    			renewalReqAlert.setTitle("Request Raised already");
    			renewalReqAlert.setHeaderText("Renewal request has been raised already\n"
    					+ "Wait for admin's approval");
    			renewalReqAlert.showAndWait();
    		}
    		else if(RenewalRequestDAO.isRejected(getRegistrationData().getRegNo())) {
    			renewalReqAlert =new Alert(AlertType.INFORMATION);
    			RenewalRequestDAO.updateStatusPending(getRegistrationData().getRegNo());    
    			renewalReqAlert.setTitle("Renewal request Sucessfull");
    			renewalReqAlert.setHeaderText("Renewal request raised successfully\n"
    					+ "Wait for admin's approval");
    			renewalReqAlert.showAndWait();
    		}
    		else {    			
    			renewalReqAlert =new Alert(AlertType.INFORMATION);
    			RenewalRequestDAO.updateRenewalRequestsToAdmin(getRegistrationData(), getVehicleData());    
    			renewalReqAlert.setTitle("Renewal request Sucessfull");
    			renewalReqAlert.setHeaderText("Renewal request raised successfully\n"
    					+ "Wait for admin's approval");
    			renewalReqAlert.showAndWait();
    		}
    	}else {
    		renewalReqAlert = new Alert(AlertType.INFORMATION);
    		renewalReqAlert.setTitle("Renewal request cancelled");
    		renewalReqAlert.setHeaderText("Your Renewal request has been cancelled");
    	}
    }
    
}
