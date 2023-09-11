package application;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import dbManagement.OwnerDAO;
import dbManagement.QueriesList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import rtoManagement.Owner;
import rtoManagement.Registration;
import rtoManagement.Vehicle;

public class VehicleRegRequestCardController {
	
	private Parent root;
	private Scene scene;
	private Stage stage;
	
	@FXML
	private Label idLabel, regDateLabel, expDateLabel, vehicleTypeLabel, fuelTypeLabel, manYearLabel, regNoLabel;
	
	public void setData(Registration registration, Vehicle vehicle) {
		
		registration.getInfo();
		vehicle.getInfo();
		
		idLabel.setText(new Integer(registration.getOwnerID()).toString());
    	regDateLabel.setText(registration.getDateOfreg().toString());
    	expDateLabel.setText(registration.getExpiryDate().toString());
    	vehicleTypeLabel.setText(vehicle.getVehicleType());
    	fuelTypeLabel.setText(vehicle.getFuelType());
    	manYearLabel.setText(new Integer(vehicle.getManufactureYear()).toString());;
    	regNoLabel.setText(registration.getRegNo());
    	
    }
	
	private Registration getRegistrationData() {
		LocalDate date;
		int ownerID = new Integer(idLabel.getText());
    	Date reg_date = Date.valueOf(regDateLabel.getText());
    	Date expiry_date =  Date.valueOf(expDateLabel.getText());
    	String regNo = regNoLabel.getText();
    	Registration registration = new Registration(ownerID,reg_date,expiry_date,regNo);
    	
    	registration.getInfo();

    	return registration;
	}
	
	private Vehicle getVehicleData() {
		String vehicleType = vehicleTypeLabel.getText();
    	String fuelType = fuelTypeLabel.getText();
    	int manYear = new Integer(manYearLabel.getText());
    	
    	Vehicle vehicle = new Vehicle(vehicleType, fuelType, manYear);
    	
    	vehicle.getInfo();
    	
		return vehicle;
	}
	
	
	
	public void viewMore(ActionEvent event) throws SQLException, IOException {
		
		Vehicle vehicle = getVehicleData();
		Registration registration = getRegistrationData();
		Owner owner = OwnerDAO.fetchOwnerDetails(registration.getOwnerID());
		owner.setOwnerID(registration.getOwnerID());
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewMoreDetails.fxml"));
		root = loader.load();
		ViewMoreDetailsController viewMore = loader.getController();
		viewMore.setDetails(owner, registration, vehicle);
		scene = new Scene(root);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
		
	}
	
}
