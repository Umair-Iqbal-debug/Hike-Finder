package views;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Password;
import model.User;
import utils.BackUpRestoreTools;

public class UserDetailsPane {
//	 <ColumnConstraints hgrow="SOMETIMES" maxWidth="166.4" minWidth="10.0" prefWidth="95.19998016357422" />
//     <ColumnConstraints hgrow="SOMETIMES" maxWidth="167.4000198364258" minWidth="0.0" prefWidth="155.4000198364258" />
//   </columnConstraints>
	private Pane root;

	public UserDetailsPane() {
		User loggedIn = Main.userBag.getLoggedInUser();
		File file = new File(loggedIn.getPicture());
		Image img;
		if(file.exists())
			{
			  img = new Image(loggedIn.getPicture());
			
			}
		else img = new Image("data/pictures/Profile-ICon.png");

		ImageView profilePicture = new ImageView(img);
		profilePicture.setFitHeight(150);
		profilePicture.setFitWidth(200);
		profilePicture.setPreserveRatio(true);
		profilePicture.setPickOnBounds(true);

		HBox imageBox = new HBox(profilePicture);
		imageBox.setPrefHeight(150.0);
		imageBox.setPrefWidth(202.0);

		Button changeProfilePicture = new Button("Change Profile Picture");
		changeProfilePicture.setPrefHeight(26.0);
		changeProfilePicture.setPrefWidth(162.0);
		changeProfilePicture.setAlignment(Pos.CENTER);
		VBox.setMargin(changeProfilePicture, new Insets(10));
		
		changeProfilePicture.setOnAction(e->{
			Stage stage = Main.window;
	    	FileChooser fileChooser = new FileChooser();
	    	fileChooser.setTitle("Open image");
	    	File filePath = fileChooser.showOpenDialog(stage);
	    	
	    	
	    		BufferedImage bufferedImage;
				try {
					bufferedImage = ImageIO.read(filePath);
					Image image = SwingFXUtils.toFXImage(bufferedImage, null);
					String path = filePath.getPath();
					String relativePath = getRelativePath(path);
					Main.userBag.getLoggedInUser().setPicture(relativePath);
					System.out.println(relativePath);
					profilePicture.setImage(image);
				}
				
				catch (Exception es) 
				{
					AlertBox.display("", "Select a valid picture!");
				}
				
		});

		// Labels for displaying user information in grid

		Label firstNameTitleLabel = new Label("First Name");
		Label lastNameTitleLabel = new Label("Last Name");
		Label emailTitleLabel = new Label("Email");
		Label usernameTitleLabel = new Label("Username");
		Label passwordTitleLabel = new Label("Password");
		Label isAdminTitleLabel = new Label("Admin");
		
		Button showPass = new Button("s");
		

		Label firstNameLabel = new Label(loggedIn.getFirstName());
		firstNameLabel.setPrefWidth(146);
		Label lastNameLabel = new Label(loggedIn.getLastName());
		lastNameLabel.setPrefWidth(155);
		Label emailLabel = new Label(loggedIn.getEmail());
		emailLabel.setPrefWidth(162);
		Label usernameLabel = new Label(loggedIn.getUsername());
		usernameLabel.setPrefWidth(151);
		Label passwordLabel = new Label(concealed(loggedIn.getPassword()));
		passwordLabel.setPrefWidth(149);
		Label isAdminLabel = new Label(loggedIn.isAdmin() ? "Yes" : "No");
		isAdminLabel.setPrefWidth(121);

		centerLabel(firstNameLabel, lastNameLabel, emailLabel, usernameLabel, passwordLabel, isAdminLabel);
		
		showPass.setPrefSize(0, 0);
		showPass.setOnMousePressed(e->passwordLabel.setText(loggedIn.getPassword()));
		showPass.setOnMouseReleased(e->passwordLabel.setText(concealed(loggedIn.getPassword())));

		GridPane userInfoGridPane = new GridPane();
		userInfoGridPane.setPrefHeight(242.0);
		userInfoGridPane.setPrefWidth(191.0);
		// Title labels
		userInfoGridPane.add(firstNameTitleLabel, 0, 0);
		userInfoGridPane.add(lastNameTitleLabel, 0, 1);
		userInfoGridPane.add(emailTitleLabel, 0, 2);
		userInfoGridPane.add(usernameTitleLabel, 0, 3);
		userInfoGridPane.add(passwordTitleLabel, 0, 4);
		userInfoGridPane.add(isAdminTitleLabel, 0, 5);
		// user information
		userInfoGridPane.add(firstNameLabel, 1, 0);
		userInfoGridPane.add(lastNameLabel, 1, 1);
		userInfoGridPane.add(emailLabel, 1, 2);
		userInfoGridPane.add(usernameLabel, 1, 3);
		userInfoGridPane.add(passwordLabel, 1, 4);
		userInfoGridPane.add(showPass, 1, 4);
		userInfoGridPane.add(isAdminLabel, 1, 5);

		// columns constraints
		ColumnConstraints col1 = new ColumnConstraints(10, 95.19998016357422, 166.4);
		col1.setHgrow(Priority.SOMETIMES);
		col1.setPercentWidth(-1);
		col1.setFillWidth(true);
		ColumnConstraints col2 = new ColumnConstraints(0, 155.4000198364258, 167.4);
		col2.setHgrow(Priority.SOMETIMES);
		col2.setPercentWidth(-1);
		col2.setFillWidth(true);

		userInfoGridPane.getColumnConstraints().addAll(col1, col2);
//		
//		// creating and setting row constraints
		RowConstraints row1 = new RowConstraints(10, 30, 35);
		row1.setVgrow(Priority.SOMETIMES);
		row1.setFillHeight(true);
		RowConstraints row2 = new RowConstraints(10, 30, 35);
		row2.setVgrow(Priority.SOMETIMES);
		row2.setFillHeight(true);
		RowConstraints row3 = new RowConstraints(10, 30, 48);
		row3.setVgrow(Priority.SOMETIMES);
		row3.setFillHeight(true);
		RowConstraints row4 = new RowConstraints(10, 30, 35);
		row4.setVgrow(Priority.SOMETIMES);
		row4.setFillHeight(true);

		RowConstraints row5 = new RowConstraints(10, 28, 48);
		row5.setVgrow(Priority.SOMETIMES);
		row5.setFillHeight(true);

		userInfoGridPane.getRowConstraints().addAll(row1, row2, row3, row4, row5);
		userInfoGridPane.setGridLinesVisible(false);

		userInfoGridPane.setPadding(new Insets(10));
		VBox.setMargin(userInfoGridPane, new Insets(0, 0, 0, 0));

		Button signOutButton = new Button("Sign Out");
		signOutButton.setPrefSize(178, 26);
		VBox.setMargin(signOutButton, new Insets(15, 0, 0, 0));
		signOutButton.setOnAction(signOutHandler());

		Button deleteMyAcc = new Button("Delete my Account");
		deleteMyAcc.setPrefSize(178, 26);
		VBox.setMargin(deleteMyAcc, new Insets(10, 0, 5, 0));
		deleteMyAcc.setOnAction(deleteAccountHandler());

		VBox vbox = new VBox(imageBox, changeProfilePicture, userInfoGridPane, signOutButton, deleteMyAcc);
		vbox.setPadding(new Insets(10, 2, 3, 10));
		vbox.setPrefSize(226, 550.0);
		vbox.setStyle("-fx-background-color:  #a9a9a9;");
		BorderPane.setAlignment(vbox, Pos.CENTER);

		// <SplitPane orientation="VERTICAL" prefHeight="200.0" prefWidth="160.0"
		// BorderPane.alignment="CENTER">

		Label updateUserDetailLabel = new Label("Update User Details");
		updateUserDetailLabel.setPrefSize(303, 43);
		updateUserDetailLabel.setAlignment(Pos.CENTER);
		VBox.setMargin(updateUserDetailLabel, new Insets(10));

		Insets labelMarginInsets = new Insets(5, 90, 5, 60);
		Insets textFieldInsets = new Insets(5, 90, 20, 60);
		Insets updateButtonInsets = new Insets(10,90,5,60);

		Label updatefirstNameLabel = new Label("First Name:");
		VBox.setMargin(updatefirstNameLabel, labelMarginInsets);

		TextField firstNameTextField = new TextField();
		VBox.setMargin(firstNameTextField, textFieldInsets);
		firstNameTextField.setPromptText("First Name");

		Label updatelastNameLabel = new Label("Last Name:");
		VBox.setMargin(updatelastNameLabel, labelMarginInsets);
		

		TextField lastNameTextField = new TextField();
		VBox.setMargin(lastNameTextField, textFieldInsets);
		lastNameTextField.setPromptText("Last Name");

		Label updateEmailLabel = new Label("Email:");
		VBox.setMargin(updateEmailLabel, labelMarginInsets);

		TextField emailTextField = new TextField();
		VBox.setMargin(emailTextField, textFieldInsets);
		emailTextField.setPromptText("Email");

		Label updateusernameLabel = new Label("Username:");
		VBox.setMargin(updateusernameLabel, labelMarginInsets);

		TextField usernameTextField = new TextField();
		VBox.setMargin(usernameTextField, textFieldInsets);
		usernameTextField.setPromptText("Username");

		Label updatepasswordLabel = new Label("Password:");
		VBox.setMargin(updatepasswordLabel, labelMarginInsets);

		PasswordField passwordTextField = new PasswordField();
		VBox.setMargin(passwordTextField, new Insets(5, 90, 10, 60));
		passwordTextField.setPromptText("Password");
		
		Button updateButton = new Button("Update Details");
		VBox.setMargin(updateButton, updateButtonInsets);
		updateButton.setPrefSize(219, 26);
		
		Button searchTrails = new Button("Search Trails");
		VBox.setMargin(searchTrails, updateButtonInsets);
		searchTrails.setPrefSize(219, 26);

		VBox detailBox = new VBox(updateUserDetailLabel,updatefirstNameLabel,firstNameTextField,updatelastNameLabel,lastNameTextField,
				updateEmailLabel,emailTextField,updateusernameLabel,usernameTextField,updatepasswordLabel,passwordTextField,updateButton,searchTrails);
		SplitPane updateDetailsPane = new SplitPane(detailBox);
		
		//setting event handler for search Trails
		searchTrails.setOnAction(searchTrail());
		//event handler for update details button 
		updateButton.setOnAction(e->{
			String firstName = firstNameTextField.getText().trim();
	    	String lastName = lastNameTextField.getText().trim();
	    	String email = emailTextField.getText().trim();
	    	String password = passwordTextField.getText().trim();
	    	String username = usernameTextField.getText().trim();
	    	User loggedInUser = Main.userBag.getLoggedInUser();
	    	
	    	
	    	if(!firstName.isBlank())
	    		loggedInUser.setFirstName(firstName);
	    	if(!lastName.isBlank())
	    		loggedInUser.setLastName(lastName);
	    	if(!email.isBlank())
	    		loggedInUser.setEmail(email);
	    	if(Password.isValidPassword(password))
	    		loggedInUser.setPassword(password);
	    	else
	    	{
	    		if(!password.isBlank())
	    		{
	    			AlertBox.display("Error", "Password should have atleast 6 characters, 1 uppercase, 1 lowercase and 1 number");
	        		return;
	    		}
	    		
	    	}
	    	
	    	if(Main.userBag.searchByUsername(username) == null && !username.isBlank())
	    		loggedInUser.setUsername(username);
	    	else
	    	{
	    		if(!username.isBlank())
	    		{
	    			AlertBox.display("Error", "Username has been taken!");
	        		return;
	    		}
	    		
	    	}
	    	
	        updateLabels(firstNameLabel,lastNameLabel,usernameLabel,emailLabel,passwordLabel);
	        clear(firstNameTextField,lastNameTextField,usernameTextField,passwordTextField,emailTextField);
	    	//BackUpRestoreTools.backupUserBag(Main.userBag);
	    	
	    });

		BorderPane root = new BorderPane();
		root.setLeft(vbox);
		root.setTop(getMenuBar());
		root.setCenter(updateDetailsPane);
		this.root = root;
	}

	public Pane getRoot() {
		return this.root;
	}

	public void centerLabel(Label... labels) {
		for (Label label : labels)
			label.setAlignment(Pos.CENTER);
	}

	public static MenuBar getMenuBar() {
		Menu menu = new Menu("MENU");
		MenuItem signOut = new MenuItem("Sign Out");
		MenuItem updateUserDetails = new MenuItem("Update Account");
		MenuItem viewHikeHistory = new MenuItem("View Hike History");
		MenuItem deleteYourAccount = new MenuItem("Delete Your Account");
		MenuItem searchTrail = new MenuItem("Search for Trails");
		MenuItem goToHomePage = new MenuItem("Homepage");
		menu.getItems().addAll(updateUserDetails,goToHomePage,viewHikeHistory,signOut,deleteYourAccount,searchTrail);
		
		signOut.setOnAction(signOutHandler());
		searchTrail.setOnAction(searchTrail());
		deleteYourAccount.setOnAction(deleteAccountHandler());
		updateUserDetails.setOnAction(goToUserDetails());
		
		goToHomePage.setOnAction(e->{
			Pane homepage = new Homepage().getRoot();
			Main.window.setScene(new Scene(homepage));
			Main.window.show();
		});
		
		viewHikeHistory.setOnAction(e->{
			Pane hikeHistoryPane = new HikingHistoryPane(null).getRoot();
			Scene scene = new Scene(hikeHistoryPane);
			Main.window.setScene(scene);
			Main.window.show();
		});
		

		Menu adminMenu = new Menu("ADMIN");
		MenuItem editUsers = new MenuItem("Edit Users");
		MenuItem editTrails = new MenuItem("Edit Trails");
		adminMenu.getItems().addAll(editUsers,editTrails);
		
		editUsers.setOnAction(goToEditUsersPane());
		editTrails.setOnAction(searchTrail());

		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(menu);
		if(Main.userBag.getLoggedInUser().isAdmin()) menuBar.getMenus().add(adminMenu);

		return menuBar;
	}

	public static EventHandler<ActionEvent> signOutHandler() {
		return e -> {
			// logout who ever is logged In and return to log in screen
			Main.userBag.logout();
			BackUpRestoreTools.backupUserBag(Main.userBag);
			Pane signInPane = new SignInPane(Main.userBag).getRoot();
			Scene scene = new Scene(signInPane, 400, 400);
			Main.window.setScene(scene);
			Main.window.show();
		};
	}
	
	
	public static EventHandler<ActionEvent> searchTrail() {
		return e -> {
			// logout who ever is logged In and return to log in screen
			Pane searchTrailPane = new SearchTrailPane().getRoot();
			Scene scene = new Scene(searchTrailPane, 800, 500);
			Main.window.setScene(scene);
			Main.window.show();
		};
	}

	public static EventHandler<ActionEvent> deleteAccountHandler() 
	{
		return e -> {
			
			boolean choice = ConfirmationBox.display("","Are you sure you want to delete you account?" );
			
			if(choice)
			{
				// delete account of who ever is logged In and return to log in screen
				Main.userBag.removeByName(Main.userBag.getLoggedInUser().getUsername());
				BackUpRestoreTools.backupUserBag(Main.userBag);
				Pane signInPane = new SignInPane(Main.userBag).getRoot();
				Scene scene = new Scene(signInPane, 400, 400);
				Main.window.setScene(scene);
				Main.window.show();
			}
			
		};
		
	}
	
	public static EventHandler<ActionEvent> goToUserDetails()
	{
		return e ->{
			 Pane userDetailsPane = new UserDetailsPane().getRoot();
			 Scene scene = new Scene(userDetailsPane,551,557);
			 Main.window.setScene(scene);
			 Main.window.show();
		};
		 
	}
	
	public static EventHandler<ActionEvent> goToEditUsersPane()
	{
		return e ->{
			if(Main.userBag.getLoggedInUser().isAdmin())
			{
				Pane editUsersPane = new EditUsersPane().getRoot();
				Scene scene = new Scene(editUsersPane);
				Main.window.setScene(scene);
				Main.window.show();
			}
			
			else AlertBox.display("Error", "Must Be An Admin!");
			
		};
	}
	public void clear(TextField...fields)
	{
		for(TextField field: fields)
			field.clear();
	}
	
	public void updateLabels(Label firstNameLabel, Label lastNameLabel, Label usernameLabel,Label emailLabel,Label passwordLabel)
	{
		User loggedInUser = Main.userBag.getLoggedInUser();
		firstNameLabel.setText(loggedInUser.getFirstName());
    	lastNameLabel.setText(loggedInUser.getLastName());
    	emailLabel.setText(loggedInUser.getEmail());
    	usernameLabel.setText(loggedInUser.getUsername());
    	passwordLabel.setText(loggedInUser.getPassword());
	}
	
	public String getRelativePath(String absolutePath)
	{
		int start = 0; 
		
		for(int i =0; i < absolutePath.length();i++)
		{
			if(absolutePath.charAt(i) == '\\') start = i;
		}
		
		return "data/pictures/"+absolutePath.substring(start+1);
	}
	
	
	 public String concealed(String string)
	    {
	    	StringBuilder sb = new StringBuilder();
	    	
	    	for(int i = 0; i < string.length(); i++)
	    	{
	    		sb.append("*");
	    	}
	    	
	    	return sb.toString();
	    }
	

}
