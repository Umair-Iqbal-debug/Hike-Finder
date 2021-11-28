package views;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.Hike;
import model.HikingHistory;
import model.Trail;

public class HikingHistoryPane {
	private Pane root;
	private TableView<Hike> hikeTable;
	private ObservableList<Hike> hikeList;
	private TableColumn<Hike, String> trailIdCol;
	private TableColumn<Hike, Double> durationCol;
	private TableColumn<Hike, LocalDate> dateCol;
	private TableColumn<Hike, String> trailName;
	private HikingHistory history = Main.userBag.getLoggedInUser().getHikingHistory();

	private Trail selectedTrail;

	public HikingHistoryPane(Trail trail) 
	{
		this.selectedTrail = trail;
		
		initTable();
		
		Label selectedTrailLabel = new Label("Selected Trail:");
		HBox.setMargin(selectedTrailLabel, new Insets(30,10,0,10));
		
		Label trailLabel = new Label();
		if(trail!=null)trailLabel.setText(trail.getName());
		else trailLabel.setText("None");
		HBox.setMargin(trailLabel, new Insets(30,10,0,0));
		
		TextField searchTextField = new TextField();
		searchTextField.setPromptText("Search");
		HBox.setMargin(searchTextField, new Insets(30,10,0,10));
		
		TextField duration = new TextField();
		duration.setPromptText("Hrs");
		HBox.setMargin(duration, new Insets(30,10,0,10));
		duration.setMaxSize(55,25);
		
		DatePicker datePicker = new DatePicker();
		datePicker.setPromptText("Date");
		HBox.setMargin(datePicker, new Insets(30,10,0,0));
		//duration.setMaxSize(44,26);
		
		Button addHike = new Button("Add Hike");
		HBox.setMargin(addHike, new Insets(30,10,0,10));
		
		Button selectTrail = new Button("Select Trail");
		HBox.setMargin(selectTrail, new Insets(30,10,0,0));
		
		ChoiceBox<String> filter = new ChoiceBox<>();
		filter.getItems().addAll("Date","Id","Duration");
		filter.getSelectionModel().select("Id");
		HBox.setMargin(filter, new Insets(30,10,0,0));
		filter.setMaxSize(30, 30);
		
		filter.setOnAction(e->{
			String selectedFilter = filter.getSelectionModel().getSelectedItem();
			if(selectedFilter.equals("Date")) searchTextField.setPromptText("YYYY-MM-DD");
			else if(selectedFilter.equals("Id")) searchTextField.setPromptText("Search Id");
			else if(selectedFilter.equals("Duration")) searchTextField.setPromptText("Search Duration");
			else searchTextField.setPromptText("Search");
		});
		
		searchTextField.setOnKeyPressed(key -> {
			if(key.getCode().toString().equals("ENTER"))
			{
				String selectedFilter = filter.getSelectionModel().getSelectedItem();
				ArrayList<Hike> res = new ArrayList<>();
				String query = searchTextField.getText().trim();
				switch(selectedFilter)
				{
				   case "Date":
					   res = history.searchByFilter(hike -> hike.getDate().toString().equals(query));
					   break;
				   case "Id":
					   res = history.searchByFilter(hike -> hike.getTrailId().equals(query));
					   break;
				   case "Duration":
					   try {
						   double durationVal = Double.parseDouble(query);
						   res = history.searchByFilter(hike-> hike.getDuration() >= durationVal);
					   }
					   
					   catch(Exception e)
					   {
						   AlertBox.display("", "Please enter a valid number for duration");
					   }
					  
					
				}
				
				hikeList.clear();
				hikeList.addAll(res);
			}
		});
		
		selectTrail.setOnAction(UserDetailsPane.searchTrail());
		
		addHike.setOnAction(e->{
			
			if(duration.getText().isBlank() && datePicker.getValue() == null) AlertBox.display("","Please Fill All Fields");
			if(selectedTrail != null)
			{
				double durationVal;
				try
				{
					durationVal = Double.parseDouble(duration.getText());
				}
				
				catch(Exception es)
				{
					AlertBox.display("", "Please Enter A Valid Number for Duration");
					return;
				}
				
				LocalDate date = datePicker.getValue();
				
				if(date == null || date.isBefore(LocalDate.now()))
				{
					AlertBox.display("Error","Please enter a valid date");
					return;
				}
				Hike newHike = new Hike(selectedTrail.getId(),durationVal,trail);
				newHike.setDate(date);
				Main.userBag.getLoggedInUser().getHikingHistory().add(newHike);
				hikeList.add(newHike);
				duration.clear();
			}
			
			else AlertBox.display("", "Please select A Trail");
		});
		
		
		
		
		HBox bottom = new HBox(searchTextField,filter,duration,datePicker,addHike,selectTrail,selectedTrailLabel,trailLabel);
		bottom.setPrefSize(200,100);
		
		
		
		BorderPane root = new BorderPane();
		root.setMinSize(806, 451);
		root.setTop(UserDetailsPane.getMenuBar());
		root.setCenter(hikeTable);
		root.setBottom(bottom);
		this.root = root;
	}

	public Pane getRoot() {
		return root;
	}

	public void initTable() {
		hikeTable = new TableView<>();
		trailIdCol = new TableColumn<>("Trail ID");
		durationCol = new TableColumn<>("Duration");
		dateCol = new TableColumn<>("Date");
		trailName = new TableColumn<>("Trail Name");

		initColumns();

		hikeTable.getColumns().addAll(trailName, trailIdCol, durationCol, dateCol);

		Collections.sort(Main.userBag.getLoggedInUser().getHikingHistory().getHikes(), (hikeA, hikeB) -> {
			if (((Hike) hikeA).getDate().isBefore(((Hike) hikeB).getDate()))
				return -1;
			return 1;
		});
		hikeList = FXCollections.observableArrayList();
		for (Hike hike : Main.userBag.getLoggedInUser().getHikingHistory().getHikes())
			hikeList.add(hike);
		hikeTable.setItems(hikeList);
	}

	public void initColumns() {
		trailIdCol.setCellValueFactory(new PropertyValueFactory<>("trailId"));
		durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		trailName.setCellValueFactory(new PropertyValueFactory<>("trailName"));
	}
}
