package application;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import dbManagement.RenewalRequestDAO;
import dbManagement.VehicleRequestsDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import rtoManagement.Vehicle;
import rtoManagement.Registration;
public class RegistrationCardController {

    @FXML
    private Label expDateLabel, fuelTypeLabel, manufactureYearLabel, regDateLabel, regNoLabel, regStatusLabel, remarkLabel, remarkTLabel, vehicleTypeLabel, renewalRequiredLabel;
    
    
    
    public void setData(Registration registration, Vehicle vehicle) throws SQLException {
    	Date exp_date = registration.getExpiryDate();
    	LocalDate curr_date = null;
    	int curr_year = curr_date.now().getYear();
    	int curr_month = curr_date.now().getMonthValue();
    	int curr_day = curr_date.now().getDayOfMonth();
    	int exp_year = exp_date.getYear()+1900;
    	int exp_month = exp_date.getMonth()+1;
    	int exp_day = exp_date.getDate();
    	renewalRequiredLabel.setVisible(false);
    	if((curr_year > exp_year || curr_year >= exp_year && curr_month > exp_month || curr_year >= exp_year && curr_month >= exp_month && curr_day > exp_day) && !RenewalRequestDAO.isPresent(registration.getRegNo())) {
    		renewalRequiredLabel.setVisible(true);
    	}
    	
    	regDateLabel.setText(registration.getDateOfreg().toString());
    	expDateLabel.setText(registration.getExpiryDate().toString());
    	vehicleTypeLabel.setText(vehicle.getVehicleType());
    	fuelTypeLabel.setText(vehicle.getFuelType());
    	manufactureYearLabel.setText(""+vehicle.getManufactureYear());
    	regNoLabel.setText(registration.getRegNo());
    	regStatusLabel.setText(VehicleRequestsDAO.getStatus(registration.getRegNo()));
    	if(regStatusLabel.getText().equals("Rejected")) {
    		remarkTLabel.setVisible(true);
    		remarkLabel.setVisible(true);
    		remarkLabel.setText(VehicleRequestsDAO.getRemark(registration.getRegNo()));
    	}else {
    		remarkLabel.setVisible(false);
    		remarkTLabel.setVisible(false);    		
    	}
    }
	
}
