package views;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import model.Address;
import model.Trail;
import model.Trail.Level;
import model.Trail.TrailType;
import utils.BackUpRestoreTools;


public class TrailCreateUpdatePane 
{
	private Pane root;
	
	
	public TrailCreateUpdatePane(Trail trail)
	{
		Label top;
		if(trail == null) top = new Label("Create New Trail");
		else top = new Label("Update Trail");
		top.setStyle("-fx-background-color: #a9a9a9;");
		top.setFont(new Font("Arial",22));
		top.setPrefSize(600, 56);
		top.setAlignment(Pos.CENTER);
		top.setScaleX(1);
		
		Label name = new Label("Trail Name");
		name.setLayoutX(29);
		name.setLayoutY(96);
		
		TextField nameTextField = new TextField();
		nameTextField.setPromptText("Trail Name ");
		nameTextField.setLayoutX(29);
		nameTextField.setLayoutY(119);
		
		Label length = new Label("Trail Length");
		length.setLayoutX(29);
		length.setLayoutY(156);
		
		
		TextField trailLengthTextField = new TextField();
		trailLengthTextField.setPromptText("Trail Length");
		trailLengthTextField.setLayoutX(29);
		trailLengthTextField.setLayoutY(174);
		
		Label elevationGain = new Label("Elevation Gain");
		elevationGain.setLayoutX(29);
		elevationGain.setLayoutY(213);
		
		
		TextField elevationGainTextField = new TextField();
		elevationGainTextField.setPromptText("Elevation");
		elevationGainTextField.setLayoutX(29);
		elevationGainTextField.setLayoutY(231);
		
		Label streetNumber = new Label("Street Number");
		streetNumber.setLayoutX(29);
		streetNumber.setLayoutY(266);
		
		
		TextField streetNumberTextField = new TextField();
		streetNumberTextField.setPromptText("Street Number");
		streetNumberTextField.setLayoutX(29);
		streetNumberTextField.setLayoutY(284);
		
		Label streetName = new Label("Street Name");
		streetName.setLayoutX(226);
		streetName.setLayoutY(97);
		
		TextField streetNameTextField = new TextField();
		streetNameTextField.setPromptText("Street Name ");
		streetNameTextField.setLayoutX(226);
		streetNameTextField.setLayoutY(119);
		
		Label city = new Label("Street Name");
		city.setLayoutX(226);
		city.setLayoutY(156);
		
		
		TextField cityTextField = new TextField();
		cityTextField.setPromptText("City");
		cityTextField.setLayoutX(226);
		cityTextField.setLayoutY(174);
		
		Label state = new Label("State");
		state.setLayoutX(226);
		state.setLayoutY(213);
		
		
		TextField stateTextField = new TextField();
		stateTextField.setPromptText("State");
		stateTextField.setLayoutX(226);
		stateTextField.setLayoutY(231);
		
		Label zip = new Label("Zip Code");
		zip.setLayoutX(226);
		zip.setLayoutY(266);
		
		
		TextField zipTextField = new TextField();
		zipTextField.setPromptText("Street Number");
		zipTextField.setLayoutX(226);
		zipTextField.setLayoutY(284);
		
		
		Label routeTypeLabel = new Label("Route Type");
		routeTypeLabel.setLayoutX(426);
		routeTypeLabel.setLayoutY(97);
		
		
		ChoiceBox<String> routeType = new ChoiceBox<>();
		routeType.getItems().addAll("Loop","Out and Back","Point to Point");
		routeType.getSelectionModel().select(0);
		routeType.setLayoutX(426);
		routeType.setLayoutY(119);
		routeType.setPrefSize(150, 26);
		
		Label difficultyLabel = new Label("Difficulty");
		difficultyLabel.setLayoutX(426);
		difficultyLabel.setLayoutY(156);
		
		
		ChoiceBox<String>  difficulty = new ChoiceBox<>();
		difficulty.getItems().addAll("Easy","Moderate","Hard");
		difficulty.getSelectionModel().select(0);
		difficulty.setLayoutX(426);
		difficulty.setLayoutY(174);
		difficulty.setPrefSize(150, 26);
		
		Button createNewTrail = new Button("Create New Trail");
		createNewTrail.setLayoutX(426);
		createNewTrail.setLayoutY(230);
		createNewTrail.setPrefSize(150, 26);
		
		Button updateTrail = new Button("Update Trail");
		updateTrail.setLayoutX(426);
		updateTrail.setLayoutY(230);
		updateTrail.setPrefSize(150, 26);
		
		
		Button back = new Button("Back");
		back.setLayoutX(426);
		back.setLayoutY(283);
		back.setPrefSize(150, 26);
		
		
		// event handler for create new trail
		
		createNewTrail.setOnAction(e->{
			
			String trailName = nameTextField.getText();
			String streetNum = streetNumberTextField.getText();
			String trailStreetName = streetNameTextField.getText();
			String cityName = cityTextField.getText();
			String stateName = stateTextField.getText();
			String zipName = zipTextField.getText();
			
			if(isEmpty(trailName,streetNum,trailStreetName,cityName,stateName,zipName))
			{
				AlertBox.display("Error", "Please fill all Fields!");
	    		return;
			}
        	double trailLength,elevation_gain;
        	Address address;
        	TrailType trailType;
        	Level level = getLevel(difficulty.getSelectionModel().getSelectedItem());
        	
        	
        	address = new Address(streetNum,trailStreetName,cityName,
        			stateName,zipName);
        	
        	
        	trailType = Trail.getTrailTypeFromString(routeType.getSelectionModel().getSelectedItem());
        	
        	try 
        	{
        		 trailLength = Double.parseDouble(trailLengthTextField.getText());
        	}
        	
        	catch(Exception es)
        	{
        		AlertBox.display("message", "Please enter a valid number for length!");
        		return;
        	}
        	
        	try 
        	{
        		 elevation_gain = Double.parseDouble(elevationGainTextField.getText());
        	}
        	
        	catch(Exception es)
        	{
        		AlertBox.display("message", "Please enter a valid number for elevation gain!");
        		return;
        	}
        	
        	Trail newTrail = new Trail(trailName,address,trailLength,elevation_gain,trailType,level);
        	Main.trailBag.insert(newTrail);
        	BackUpRestoreTools.backupTrailBag(Main.trailBag);
        	
      	Pane searchTrailPane = new SearchTrailPane().getRoot();
		Scene scene = new Scene(searchTrailPane, 800, 500);
	Main.window.setScene(scene);
		Main.window.show();
		});
		
		
		
updateTrail.setOnAction(e->{
			
			String trailName = nameTextField.getText().trim();
			String streetNum = streetNumberTextField.getText().trim();
			String trailStreetName = streetNameTextField.getText().trim();
			String cityName = cityTextField.getText().trim();
			String stateName = stateTextField.getText().trim();
			String zipName = zipTextField.getText().trim();
			Address address = trail.getAddress();
			TrailType trailType = Trail.getTrailTypeFromString(routeType.getSelectionModel().getSelectedItem());
        	Level level = getLevel(difficulty.getSelectionModel().getSelectedItem());
        	String trailLength =trailLengthTextField.getText();
        	String elevation = elevationGainTextField.getText();
			
			if(!trailName.isBlank())
				trail.setName(trailName);
			if(!streetNum.isBlank())
				address.setStreetNumber(streetNum);
			if(!trailStreetName.isBlank())
				address.setStreetName(trailStreetName);
			if(!cityName.isBlank())
				address.setCity(cityName);
			if(!stateName.isBlank())
				address.setState(stateName);
			if(!zipName.isBlank())
				address.setZip(zipName);
			trail.setType(trailType);
			trail.setDifficulty_level(level);
			
			if(!trailLength.isBlank())
			{
				if(isValid(trailLength))
					trail.setLength(Double.parseDouble(trailLength));
				else 
				{
					AlertBox.display("message", "Please enter a valid number for length!");
					return;
				} 
			}
			
			if(!elevation.isBlank())
			{
				if(isValid(elevation))
					trail.setLength(Double.parseDouble(elevation));
				else 
				{
					AlertBox.display("message", "Please enter a valid number for elevation!");
					return;
				} 
			}
			
			
			
			Pane searchTrailPane = new SearchTrailPane().getRoot();
			Scene scene = new Scene(searchTrailPane, 800, 500);
			Main.window.setScene(scene);
			Main.window.show();
		});

        back.setOnAction(UserDetailsPane.searchTrail());
		
		
		
		
		
		AnchorPane root = new AnchorPane(top,name,nameTextField,length,trailLengthTextField,elevationGain,elevationGainTextField,streetNumber,
				streetNumberTextField,streetName,streetNameTextField,city,cityTextField,state,stateTextField,zip,zipTextField,routeTypeLabel,
				routeType,difficultyLabel,difficulty
				);
		if(trail != null) root.getChildren().add(updateTrail);
		else root.getChildren().add(createNewTrail);
		root.getChildren().add(back);
		root.setMinSize(600, 400);
		
		this.root = root;
	}
	
	public Level getLevel(String level)
	{
		if(level.equalsIgnoreCase("easy"))
			return Level.EASY;
		else if(level.equalsIgnoreCase("moderate"))
			return Level.MODERATE;
		return Level.HARD;
	}
	
	public static boolean isEmpty(String ...fields)
    {
    	for(String field: fields)
    	{
    		if(field.isBlank()) return true;
    	}
    	
    	return false;
    }
	
	private boolean isValid(String length) 
	{
		try
		{
			Double.parseDouble(length);
			return true;
		}
		
		catch(Exception e)
		{
			return false;
		}
		
	}
	
	public Pane getRoot()
	{
		return root;
	}
}
