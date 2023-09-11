package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import rtoManagement.Registration;
import rtoManagement.Vehicle;

public class RenewalRejectCardController {
	
	@FXML
	private Label idLabel, regDateLabel, expDateLabel, regNoLabel, vehicleTypeLabel, fuelTypeLabel, manYearLabel, remarks;
	
	public void setData(Registration registration,Vehicle vehicle,String remark) {
		
		idLabel.setText(new Integer(registration.getOwnerID()).toString());
    	regDateLabel.setText(registration.getDateOfreg().toString());
    	expDateLabel.setText(registration.getExpiryDate().toString());
    	regNoLabel.setText(registration.getRegNo());
    	vehicleTypeLabel.setText(vehicle.getVehicleType());
    	fuelTypeLabel.setText(vehicle.getFuelType());
    	manYearLabel.setText(new Integer(vehicle.getManufactureYear()).toString());
    	remarks.setText(remark);
    	
    }
	
}
