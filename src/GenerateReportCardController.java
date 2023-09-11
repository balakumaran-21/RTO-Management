package application;

import java.io.IOException;
import java.sql.SQLException;

import dbManagement.VehicleRequestsDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import rtoManagement.Owner;

public class GenerateReportCardController {
	
	Parent root;
	Stage stage;
	Scene scene;
	
	@FXML
	Label idLabel, nameLabel, countLabel;
	
	Owner owner;
	
	public void setData(Owner owner) throws SQLException {
		this.owner = owner;
		idLabel.setText(new Integer(owner.getOwnerID()).toString());
		nameLabel.setText(owner.getName());
		countLabel.setText(new Integer(VehicleRequestsDAO.countOfRegisteredVehicles(owner.getOwnerID())).toString());
		
	}
	
	public void generateReport(ActionEvent event) throws IOException, SQLException{
		
		FXMLLoader loader = new FXMLLoader((getClass().getResource("GeneratedReportPage.fxml")));
		root = loader.load();
		GeneratedReportController generatedReportController = loader.getController();
		generatedReportController.setOwnerdetails(owner);
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
}
