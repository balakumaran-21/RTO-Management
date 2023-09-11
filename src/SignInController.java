package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import dbManagement.UserAuthentication;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignInController implements Initializable{
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	Button signInButton;
	@FXML 
	TextField usernameField;
	@FXML
	PasswordField passField;
	@FXML
	Label warningLabel;
	
	@Override
	public void initialize(URL url, ResourceBundle resourseBundle) {
		
		focusedColor(usernameField);
		focusedColor(passField);
		
	}
	
	private void focusedColor(TextField t) {
		
		t.focusedProperty().addListener((observable,oldValue,newValue) ->{
			if(newValue) {
				t.getStyleClass().add("textfield-focused");
			}else{
				t.getStyleClass().remove("textfield-focused");
			}
		});
		
	}
	
	private void focusedColor(PasswordField t) {
		
		t.focusedProperty().addListener((observable,oldValue,newValue) ->{
			if(newValue) {
				t.getStyleClass().add("textfield-focused");
			}else{
				t.getStyleClass().remove("textfield-focused");
			}
		});
		
	}
	
	// Switching scene from sign In to sign Up page
	public void switchToSignUpPage(ActionEvent event) throws IOException {
		System.out.println("Clicked sign up from sign in page");
		root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
	
	public void signIn(ActionEvent event) throws IOException, SQLException {
		System.out.println("Sign In from sign in scene clicked");
		int id = 0;
		String username = usernameField.getText();
		String password = passField.getText();
		
		if(UserAuthentication.ValidateUser(username,password)) {
			warningLabel.setText("");
			id = UserAuthentication.getUserID(username, password);
			String name = null;
			if(UserAuthentication.getUserType(id).equals("User")) {
				//User Login
				name = UserAuthentication.getOwnerName(id);
				FXMLLoader loader = new FXMLLoader(getClass().getResource("SignInUser.fxml"));
				root = loader.load();
				SignInUserController signInUserController = loader.getController();
				signInUserController.displayWelcomeMessage(name, id);
				signInUserController.renderProfileDetails(event);
				
			}else {
				// Admin Login
				name = UserAuthentication.getAdminName(id);
				FXMLLoader loader = new FXMLLoader(getClass().getResource("SignInAdmin.fxml"));
				root = loader.load();
				SignInAdminController signInAdminController = loader.getController();
				signInAdminController.displayWelcomeMessage(name, id);
				
			}
		}
		else {
			warningLabel.setText("Invalid login credentials");
			return;
		}
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	
	
}
