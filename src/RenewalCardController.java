package application;

import java.sql.SQLException;

import dbManagement.RenewalRequestDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import rtoManagement.Registration;
import rtoManagement.Vehicle;

public class RenewalCardController {

	@FXML
    private Label expDateLabel, fuelTypeLabel, manufactureYearLabel, regDateLabel, regNoLabel, regStatusLabel, remarkLabel, remarkTLabel, vehicleTypeLabel;
    
    public void setData(Registration registration, Vehicle vehicle) throws SQLException {
    	
    	regDateLabel.setText(registration.getDateOfreg().toString());
    	expDateLabel.setText(registration.getExpiryDate().toString());
    	vehicleTypeLabel.setText(vehicle.getVehicleType());
    	fuelTypeLabel.setText(vehicle.getFuelType());
    	manufactureYearLabel.setText(""+vehicle.getManufactureYear());
    	regNoLabel.setText(registration.getRegNo());
    	regStatusLabel.setText(RenewalRequestDAO.getStatus(registration.getRegNo()));
    	if(regStatusLabel.getText().equals("Rejected")) {
    		remarkTLabel.setVisible(true);
    		remarkLabel.setVisible(true);
    		remarkLabel.setText(RenewalRequestDAO.getRemark(registration.getRegNo()));
    	}else {
    		remarkLabel.setVisible(false);
    		remarkTLabel.setVisible(false);    		
    	}
    }
}
