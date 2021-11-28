package views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Address;
import model.Trail;
import model.Trail.Level;
import model.Trail.TrailType;
import model.User;
import utils.BackUpRestoreTools;

public class SearchTrailPane {
	private Pane root;

	private TableView<Trail> trailTable;

	private TableColumn<Trail, String> nameCol = new TableColumn<>("Name");

	private TableColumn<Trail, Double> lengthCol = new TableColumn<>("Length (mi)");

	private TableColumn<Trail, Double> elevationGainCol = new TableColumn<>("Elevation (ft)");

	private TableColumn<Trail, Address> addressCol = new TableColumn<>("Address");

	private TableColumn<Trail, TrailType> typeCol = new TableColumn<>("Type");

	private TableColumn<Trail, Level> levelCol = new TableColumn<>("Difficulty");

	private TableColumn<Trail, String> idCol = new TableColumn<>("ID");
	
	private ObservableList<Trail> trailList;

	public SearchTrailPane() {

		TextField searchTextField = new TextField();
		searchTextField.setPromptText("Search Trails");
		HBox.setMargin(searchTextField, new Insets(15, 0, 0, 15));
		

		ChoiceBox<String> filterChoiceBox = getFilterChoiceBox();
		HBox.setMargin(filterChoiceBox, new Insets(15, 0, 0, 10));
		
		Insets buttonInsets = new Insets(15,0,0,10);
		Button searchButton = new Button("Search");
		HBox.setMargin(searchButton, buttonInsets);
		
		Button addToHike = new Button("Add To Hike");
		HBox.setMargin(addToHike, buttonInsets);
		
		Button deleteButton = new Button("Delete Trail");
		HBox.setMargin(deleteButton, buttonInsets);
		
		Button addTrailButton = new Button("Add New Trail");
		HBox.setMargin(addTrailButton, buttonInsets);
		
		Button updateTrailButton = new Button("Update Trail");
		HBox.setMargin(updateTrailButton, buttonInsets);

		HBox searchFieldBox = new HBox(searchTextField, filterChoiceBox,searchButton,addToHike);
		VBox.setMargin(searchFieldBox, new Insets(0,0,15,0));
		
		if(Main.userBag.getLoggedInUser().isAdmin())
			searchFieldBox.getChildren().addAll(deleteButton,addTrailButton,updateTrailButton);
		
		
		addToHike.setOnAction(e->{
			
			Trail selectedTrail = trailTable.getSelectionModel().getSelectedItem();
			
			if(selectedTrail!=null)
			{
				Pane hikeHistoryPane = new HikingHistoryPane(selectedTrail).getRoot();
				Scene scene = new Scene(hikeHistoryPane);
				Main.window.setScene(scene);
				Main.window.show();
			}
			
			else AlertBox.display("Message", "Please select a Trail!");
			
		});
		
		
		deleteButton.setOnAction(e->{
			Trail selectedTrail = trailTable.getSelectionModel().getSelectedItem();
			
			if(selectedTrail!=null)
			{
				Main.trailBag.removeById(selectedTrail.getId());
				BackUpRestoreTools.backupTrailBag(Main.trailBag);
				trailList.remove(selectedTrail);
			}
			
		});
		
		addTrailButton.setOnAction(e->{
			 Pane userDetailsPane = new TrailCreateUpdatePane(null).getRoot();
			 Scene scene = new Scene(userDetailsPane);
			 Main.window.setScene(scene);
			 Main.window.show();
		});
		
		updateTrailButton.setOnAction(e->{
			
			Trail selectedUser = trailTable.getSelectionModel().getSelectedItem();
			if(selectedUser!=null)
			{
				 Pane userDetailsPane = new TrailCreateUpdatePane(selectedUser).getRoot();
				 Scene scene = new Scene(userDetailsPane);
				 Main.window.setScene(scene);
				 Main.window.show();
			}
			
			else AlertBox.display("Message", "Please select a Trail!");
			 
		});
		
		searchTextField.setOnKeyPressed(e->{
			if(e.getCode().toString().equals("ENTER")) {
				searchButton.fire();
			}
		});
		
		searchButton.setOnAction(e->{
		String filter = filterChoiceBox.getValue();
		Trail[] trailFound = new Trail[1];
		String query = searchTextField.getText().trim();

		
		if(filter.equals("ID"))
		{
			trailFound[0] = Main.trailBag.searchById(query);
		}
		
		else if(filter.equals("EVERYTHING"))
		{
			if(query.length() >= 3)
			{
				//trailFound = Main.trailBag.searchByFilter(trail -> trail.toString().toLowerCase().indexOf(query.toLowerCase()) > -1);
				String[] queries = query.toLowerCase().split(" ");
				trailFound = Main.trailBag.searchByFilter(trail->{
					for(String word: queries)
						if(!(trail.toString().toLowerCase().indexOf(word) > -1)) return false;
					return true;
				});
			}
				
				
			else AlertBox.display("", "Please enter a word greater than 3 characters");
		}
		
		else if(filter.equals("NAME"))
		{
			if(query.length() >= 3)
			trailFound = Main.trailBag.searchByFilter(trail -> trail.getName().toLowerCase().indexOf(query.toLowerCase()) > -1);
			else AlertBox.display("", "Please enter a word greater than 3 characters");
		}
		
		else if(filter.equals("CITY"))
		{
			trailFound = Main.trailBag.searchByFilter(trail->trail.getAddress().getCity().equals(query));
		}
		
		else if(filter.equals("STATE"))
		{
			trailFound = Main.trailBag.searchByFilter(trail->trail.getAddress().getState().equals(query));
		}
		
		else if(filter.equals(">=LENGTH"))
		{
			try 
			{
				trailFound = Main.trailBag.searchByFilter(trail->trail.getLength()>=Double.parseDouble(query));
			} 
			catch (Exception exception) 
			{
				AlertBox.display("Error", "Please Enter A Valid Number!");
				return;
			}
		}
		
		else if(filter.equals("<LENGTH"))
		{
			try 
			{
				trailFound = Main.trailBag.searchByFilter(trail->trail.getLength()<Double.parseDouble(query));
			} 
			catch (Exception exception) 
			{
				AlertBox.display("Error", "Please Enter A Valid Number!");
				return;
			}
		}
		
		else if(filter.equals(">=ELEVATION"))
		{
			try 
			{
				trailFound = Main.trailBag.searchByFilter(trail->trail.getElevation_gain()>=Double.parseDouble(query));
			} 
			catch (Exception exception) 
			{
				AlertBox.display("Error", "Please Enter A Valid Number!");
				return;
			}
		}
		
		else if(filter.equals("<ELEVATION"))
		{
			try 
			{
				trailFound = Main.trailBag.searchByFilter(trail->trail.getElevation_gain()<Double.parseDouble(query));
			} 
			catch (Exception exception) 
			{
				AlertBox.display("Error", "Please Enter A Valid Number!");
				return;
			}
		}
		
		else if(filter.equals("EASY"))
		{
		  trailFound = Main.trailBag.searchByFilter(trail -> trail.getDifficulty_level().equals(Level.EASY));
		  
		}
		
		else if(filter.equals("MODERATE"))
		{
		  trailFound = Main.trailBag.searchByFilter(trail -> trail.getDifficulty_level().equals(Level.MODERATE));
		  
		}
		
		else if(filter.equals("HARD"))
		{
		  trailFound = Main.trailBag.searchByFilter(trail -> trail.getDifficulty_level().equals(Level.HARD));
		  
		}
		
    	
    	trailList.clear();
    	trailList.addAll(trailFound);
		});

		// Table to display trails
		trailTable = new TableView<>();
		trailTable.getColumns().addAll(nameCol, lengthCol, elevationGainCol, addressCol, typeCol, levelCol, idCol);
		initTableColumns();
		
		trailList= FXCollections.observableArrayList();
		for(Trail trail: Main.trailBag)
			trailList.add(trail);
		
		trailTable.setItems(trailList);
		
		
		

		VBox mainContent = new VBox(searchFieldBox, trailTable);

		BorderPane root = new BorderPane();
		root.setCenter(mainContent);
		root.setTop(UserDetailsPane.getMenuBar());
		this.root = root;
	}

	public Pane getRoot() 
	{
		return root;
	}

	public ChoiceBox<String> getFilterChoiceBox() 
	{
		ChoiceBox<String> filterChoiceBox = new ChoiceBox<>();
		filterChoiceBox.getItems().addAll("ID","NAME","CITY", "STATE",">=LENGTH","<LENGTH",  ">=ELEVATION", "<ELEVATION","EASY","MODERATE","HARD","EVERYTHING");
		
		filterChoiceBox.setValue("EVERYTHING");
		return filterChoiceBox;
	}
	
	public void initTableColumns()
	{
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		lengthCol.setCellValueFactory(new PropertyValueFactory<>("length"));
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		elevationGainCol.setCellValueFactory(new PropertyValueFactory<>("elevation_gain"));
		addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
		levelCol.setCellValueFactory(new PropertyValueFactory<>("difficulty_level"));
		typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
	}
	
	
}
