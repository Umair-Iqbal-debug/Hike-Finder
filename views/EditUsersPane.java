package views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.User;
import utils.BackUpRestoreTools;

public class EditUsersPane {

	private Pane root;
	
	private TableView<User> userTable;

	private TableColumn<User, String> firstNameCol;

	private TableColumn<User, String> lastNameCol;

	private TableColumn<User, String> emailCol;

	private TableColumn<User, String> usernameCol;

	private TableColumn<User, String> adminCol;
	
	private ObservableList<User> users;

	public EditUsersPane() 
	{
		initUserList();
		initColumns();
		
		Label usernameLabel = new Label("Username:");
		TextField usernameTextField = new TextField();
		VBox.setMargin(usernameTextField,new Insets(5,10,5,10));
		VBox.setMargin(usernameLabel,new Insets(5,0,5,0));
		usernameTextField.setPromptText("Username");
		
		Insets buttonMargin = new Insets(5,10,5,10);
		
		Button search = new Button("Search");
		VBox.setMargin(search, buttonMargin);
		search.setPrefWidth(178);
		
		search.setOnAction(e->{
			User user = Main.userBag.searchByUsername(usernameTextField.getText());
			users.clear();
			users.add(user);
		});
		
		Button refresh = new Button("Refresh");
		refresh.setPrefWidth(178);
		VBox.setMargin(refresh, buttonMargin);
		
		refresh.setOnAction(e->{
			
			users.clear();
			
			for(User user: Main.userBag)
				users.add(user);
		});
		
		
		Button deleteUser = new Button("Delete");
		deleteUser.setPrefWidth(178);
		VBox.setMargin(deleteUser, buttonMargin);
		
		deleteUser.setOnAction(e->{
			
			User selectedUser = userTable.getSelectionModel().getSelectedItem();
			boolean choice = ConfirmationBox.display(null, "Are You Sure You want to delete " + selectedUser.getUsername());
			if(choice) {
				users.remove(selectedUser);
				AlertBox.display("", selectedUser.getUsername()+ " was removed");
				Main.userBag.removeByName(selectedUser.getUsername());
			}
			
			BackUpRestoreTools.backupUserBag(Main.userBag);
		});
		
		Button makeAdmin = new Button("Make Admin");
		makeAdmin.setPrefWidth(178);
		VBox.setMargin(makeAdmin, buttonMargin);
		
		makeAdmin.setOnAction(e->{
			User user = userTable.getSelectionModel().getSelectedItem();
			if(user != null)
			{
				user.setAdmin(true);
				refresh.fire();
				AlertBox.display("", user.getUsername() +" has been promoted to admin");
				
			}
			else AlertBox.display("User not selected", "Please select a User");
			
			
			BackUpRestoreTools.backupUserBag(Main.userBag);
		});
		
		
		Button removeAdmin = new Button("Dismiss Admin");
		removeAdmin.setPrefWidth(178);
		VBox.setMargin(removeAdmin, buttonMargin);
		
		removeAdmin.setOnAction(e->{
			User user = userTable.getSelectionModel().getSelectedItem();
			if(user != null)
			{
				user.setAdmin(false);
				refresh.fire();				
				AlertBox.display("", user.getUsername() +" is no longer an admin");
			}
			
			else AlertBox.display("User not selected", "Please select a User");
			
			BackUpRestoreTools.backupUserBag(Main.userBag);
		});
		
		
		
		
		VBox vbox = new VBox(usernameLabel,usernameTextField,search,deleteUser,makeAdmin,removeAdmin,refresh);
		
		BorderPane.setMargin(userTable, new Insets(5,5,5,5));
		
		BorderPane root = new BorderPane();
		root.setPrefSize(700, 400);
		root.setCenter(userTable);
		root.setTop(UserDetailsPane.getMenuBar());
		root.setRight(vbox);
		this.root = root;
		
	}
	
	public void initUserList()
	{
		users = FXCollections.observableArrayList();
		for(User user: Main.userBag)
			users.add(user);
		userTable = new TableView<>();
		
	}
	
	public Pane getRoot()
	{
		return this.root;
	}
	
	public void initColumns()
	{
		
		firstNameCol = new TableColumn<>("First Name");
		lastNameCol = new TableColumn<>("Last Name");
		usernameCol = new TableColumn<>("Username");
		emailCol = new TableColumn<>("Email");
		adminCol = new TableColumn<>("Admin");
		
		userTable.getColumns().setAll(firstNameCol,lastNameCol,usernameCol,emailCol,adminCol);
		
		firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		adminCol.setCellValueFactory(new PropertyValueFactory<>("adminStatus"));
		
		userTable.setItems(users);
		
	}
	
	public void delete(int row)
	{
		if(row >=0)
		{
			userTable.getItems().remove(row);
			userTable.getSelectionModel().clearSelection();
		}
	}
	
	public void add(User user)
	{
		userTable.getItems().add(user);
	}
	
	
	
}
