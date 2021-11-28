package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.User;
import model.UserBag;

public class SignInPane 
{
	private Pane root;

	public SignInPane(UserBag userBag)
	{
		
		//creating sign in label
		Label signInLabel= new Label("Sign In");
		signInLabel.setAlignment(Pos.CENTER);
		signInLabel.setLayoutX(-25.0);
		signInLabel.setLayoutY(-2.0);
		signInLabel.setPrefHeight(76);
		signInLabel.setPrefWidth(427.0);
		signInLabel.setStyle("-fx-font-size: 22;"
				+ "-fx-background-color: #a9a9a9;");
		// creating buttons for log in and 
		Button signInButton = new Button("Log In");
		signInButton.setLayoutX(128.0);
		signInButton.setLayoutY(254.0);
		signInButton.setPrefHeight(26.0);
		signInButton.setPrefWidth(150.0);
		Button signUpButton = new Button("Sign Up"); 
		signUpButton.setLayoutX(128.0);
		signUpButton.setLayoutY(300.0);
		signUpButton.setPrefHeight(26.0);
		signUpButton.setPrefWidth(150.0);
		
		//creating textfields for username and passwords
		TextField usernameTextField = new TextField();
		usernameTextField.setPromptText("USERNAME");
		usernameTextField.setMinWidth(149);
		usernameTextField.setMinHeight(25);
		usernameTextField.setLayoutX(125.0);
		usernameTextField.setLayoutY(152.0);
		
		PasswordField passwordTextField = new PasswordField();
		passwordTextField.setPromptText("PASSWORD");
		passwordTextField.setMinWidth(149);
		passwordTextField.setMinHeight(25);
		passwordTextField.setLayoutX(125.0);
		passwordTextField.setLayoutY(200.0);
		passwordTextField.setTooltip(new Tooltip("Password should be atleast 6 characters with atleast one number, one uppercase letter"
				+ " and one lowercase letter"));
		
		passwordTextField.setOnKeyPressed(e->{
			if(e.getCode().toString().equals("ENTER")) {
				signInButton.fire();
			}
		});
		AnchorPane pane = new AnchorPane(signInLabel,usernameTextField,passwordTextField,signInButton,signUpButton);
		
		// Event Handlers for sign in and sign up button
		
		signUpButton.setOnAction(e ->{
			 Pane signUpPane = new SignUpPane().getRoot();
			 Scene scene = new Scene(signUpPane,525,525);
			 Main.window.setScene(scene);
			 Main.window.show();
		});
		
		signInButton.setOnAction(e->{
			String username = usernameTextField.getText();
			String password = passwordTextField.getText();
			boolean loginSuccess = Main.userBag.login(username, password);
			User user = Main.userBag.getLoggedInUser();
			
			if(loginSuccess)
			{
				 Pane homepage = new Homepage().getRoot();
				 Scene scene = new Scene(homepage);
				 Main.window.setScene(scene);
				 Main.window.show(); 
			} 
			
			else if(user == null) AlertBox.display("Error", "User not found!");
			else AlertBox.display("Error", "Incorrect password entered!");
		});
		
		//creating VBOx 
		Pane root = pane;
		this.root = root;
	}
	
	public Pane getRoot()
	{
		return this.root;
	}
	
	
	
	
	
}
