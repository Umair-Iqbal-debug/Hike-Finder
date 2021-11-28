package views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.Password;
import model.User;
import utils.BackUpRestoreTools;

public class SignUpPane 
{
	private Pane root;
	
	// layoutX="76.0" layoutY="183.0" prefHeight="25.0" prefWidth="336.0" promptText="Last Name"
	public SignUpPane()
	{
		//Title label for sign up
		Label signUpLabel = new Label("Sign Up");
		signUpLabel.setLayoutX(78.0);
		signUpLabel.setLayoutY(32.0);
		signUpLabel.setPrefHeight(27.0);
		signUpLabel.setPrefWidth(79.0);
		signUpLabel.setStyle("-fx-font-size: 22;");
		
		//Anchorpane for signUpLabel
		AnchorPane signUpLabelPane = new AnchorPane(signUpLabel);
		signUpLabelPane.setPrefHeight(82.0);
		signUpLabelPane.setPrefWidth(520.0);
		signUpLabelPane.setStyle("-fx-background-color:  #a9a9a9;");
		
		
		// textfields and labels for sign up page
		
		Label firstNameLabel = new Label("First Name:");
		firstNameLabel.setLayoutX(76.0);
		firstNameLabel.setLayoutY(95.0);
		
		TextField firstNameTextField = new TextField();
		firstNameTextField.setPromptText("First Name");
		firstNameTextField.setLayoutX(76.0);
		firstNameTextField.setLayoutY(119.0);
		firstNameTextField.setPrefHeight(25.0);
		firstNameTextField.setPrefWidth(336.0);
		
		
		Label lastNameLabel = new Label("Last Name:");
		lastNameLabel.setLayoutX(76.0);
		lastNameLabel.setLayoutY(159.0);
		
		TextField lastNameTextField = new TextField();
		lastNameTextField.setPromptText("Last Name");
		lastNameTextField.setLayoutX(76.0);
		lastNameTextField.setLayoutY(183.0);
		lastNameTextField.setPrefHeight(25.0);
		lastNameTextField.setPrefWidth(336.0);
		
		Label emailLabel = new Label("Email:");
		emailLabel.setLayoutX(76.0);
		emailLabel.setLayoutY(217.0);
		
		TextField emailTextField = new TextField();
		emailTextField.setPromptText("Email");
		emailTextField.setLayoutX(76.0);
		emailTextField.setLayoutY(241.0);
		emailTextField.setPrefHeight(25.0);
		emailTextField.setPrefWidth(336.0);
		
		Label usernameLabel = new Label("username:");
		usernameLabel.setLayoutX(76.0);
		usernameLabel.setLayoutY(279.0);
		
		TextField usernameTextField = new TextField();
		usernameTextField.setPromptText("username");
		usernameTextField.setLayoutX(76.0);
		usernameTextField.setLayoutY(303.0);
		usernameTextField.setPrefHeight(25.0);
		usernameTextField.setPrefWidth(336.0);
		
		
		Label passwordLabel = new Label("Password:");
		passwordLabel.setLayoutX(76.0);
		passwordLabel.setLayoutY(342.0);
		
		PasswordField passwordTextField = new PasswordField();
		passwordTextField.setPromptText("Password");
		passwordTextField.setLayoutX(76.0);
		passwordTextField.setLayoutY(367.0);
		passwordTextField.setPrefHeight(25.0);
		passwordTextField.setPrefWidth(336.0);
		
		

		//sign up button 
		Button signUpButton = new Button("Create a New Account");
		signUpButton.setLayoutX(76.0);
		signUpButton.setLayoutY(441.0);
		signUpButton.setPrefHeight(25.0);
		signUpButton.setPrefWidth(336.0);
		
		passwordTextField.setOnKeyPressed(e->{
			if(e.getCode().toString().equals("ENTER")) {
				signUpButton.fire();
			}
		});
		
		// log in button goes back to log in screen once clicked
		
		Button logInButton = new Button("Log In");
		logInButton.setLayoutX(76.0);
		logInButton.setLayoutY(476.0);
		logInButton.setPrefHeight(25.0);
		logInButton.setPrefWidth(336.0);
		
		// adding event handler to logInButton to make it go back to log In screen
		logInButton.setOnAction(userDetails());
		signUpButton.setOnAction(e->{
			String username = usernameTextField.getText();
	    	String password = passwordTextField.getText();
	    	String email = emailTextField.getText();
	    	String firstname = firstNameTextField.getText();
	    	String lastname = lastNameTextField.getText();
	    	
	 
	    	
	    	if(isEmpty(username,password,email,firstname,lastname))
	    	{
	    		AlertBox.display("Error", "Please fill all Fields!");
	    		return;
	    	}
	    	
	    	else if(Main.userBag.searchByUsername(username)!=null) 
	    	{
	    		AlertBox.display("Error", "Username has been taken!");
	    		return;
	    	}
	    	
	    	else if(!Password.isValidPassword(password)) 
	    	{
	    		AlertBox.display("Error", "Password should have atleast 6 characters, 1 uppercase, 1 lowercase and 1 number");
	    		return;
	    	}
	    	
	    	//accountCreated();
	    	 
	    	User user = new User(firstNameTextField.getText(),lastNameTextField.getText(),emailTextField.getText(),usernameTextField.getText(),
	    			passwordTextField.getText(),"");
	    	Main.userBag.insert(user);
	    	BackUpRestoreTools.backupUserBag(Main.userBag);
	    	
	    	boolean correctLogin = Main.userBag.login(usernameTextField.getText(), passwordTextField.getText());
	    	
	    	 Pane homepage = new Homepage().getRoot();
			 Scene scene = new Scene(homepage);
			 Main.window.setScene(scene);
			 Main.window.show();
		});
			
		
		
		// adding event handler to sign Up button to ma
		
		
		AnchorPane root = new AnchorPane(signUpLabelPane,firstNameLabel,firstNameTextField,lastNameLabel,lastNameTextField,emailLabel
				,emailTextField,usernameLabel,usernameTextField,passwordLabel,passwordTextField,signUpButton,logInButton);
		root.setPrefHeight(525);
		root.setPrefWidth(525);
		
		
		this.root = root;
		
	}

	public Pane getRoot() 
	{
		return this.root;
	}
	
	public static EventHandler<ActionEvent> userDetails()
	{
		return  e->{
			 Pane signInPane = new SignInPane(Main.userBag).getRoot();
			 Scene scene = new Scene(signInPane,400,400);
			 Main.window.setScene(scene);
			 Main.window.show();
		};
	}
	
	public static boolean isEmpty(String ...fields)
    {
    	for(String field: fields)
    	{
    		if(field.isBlank()) return true;
    	}
    	
    	return false;
    }
}
