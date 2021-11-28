package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import model.User;

public class Homepage 
{
	private Pane root;
	
	public Homepage()
	{
		User loggedInUser = Main.userBag.getLoggedInUser();
		Label label = new Label("Welcome, " + loggedInUser.getFirstName() + " " + loggedInUser.getLastName());
		label.setFont(new Font("Arial",24));
		HBox.setMargin(label, new Insets(150,0,0,0));
		label.setAlignment(Pos.CENTER);
		label.setPrefSize(579, 35);
		
		
		MenuBar menuBar = UserDetailsPane.getMenuBar();
		
		HBox box = new HBox(label);
		
		BorderPane root = new BorderPane();
		root.setTop(menuBar);
		root.setCenter(box);
		root.setStyle("-fx-background-color:#a9a9a9;");
		root.setMinSize(600, 400);
		
		this.root = root;
	}
	
	public Pane getRoot()
	{
		return root;
	}
}
