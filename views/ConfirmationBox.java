package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmationBox 
{
	private static boolean res = false;
	
	public static boolean display(String title, String message)
	{
		Stage window = new Stage();
		
		// Modality is an enum that says whether events will be blocked from other stages or not
		window.initModality(Modality.APPLICATION_MODAL);
		
		
		
		Label titleLabel = new Label("Message");
		HBox.setMargin(titleLabel, new Insets(10,5,0,5));
		titleLabel.setTextFill(Color.WHITE);
		HBox titleBox = new HBox(titleLabel);
		titleBox.setPrefSize(284, 42);
		titleBox.setStyle("-fx-background-color: #0000FF;");
		
		Label messageLabel = new Label(message);
		messageLabel.setAlignment(Pos.CENTER);
		HBox.setMargin(messageLabel, new Insets(20,0,0,0));
		messageLabel.setStyle("-fx-font-size: 10;");
		ImageView errorIcon = new ImageView(new Image("data/pictures/error-icon.png"));
		errorIcon.setPreserveRatio(true);
		errorIcon.setFitWidth(33);
		errorIcon.setFitHeight(35);
		HBox.setMargin(errorIcon, new Insets(5,5,0,5));
		HBox messageBox = new HBox(errorIcon,messageLabel);
		messageBox.setPrefSize(284, 59);
		
        Button yesButton = new Button("YES");
        VBox.setMargin(yesButton, new Insets(0,0,0,180));
        yesButton.setPrefSize(62, 26);
        
        yesButton.setOnAction(e-> {
        	res = true;
        	window.close();
        });
        
        Button noButton = new Button("NO");
        VBox.setMargin(noButton, new Insets(0,0,0,180));
        noButton.setPrefSize(62, 26);
        
        noButton.setOnAction(e-> {
        	res = false;
        	window.close();
        });
        
        
        
    
		
		VBox root = new VBox();
		root.setPrefSize(418, 159);
		root.getChildren().addAll(titleBox,messageBox,yesButton,noButton);
		
		Scene scene = new Scene(root);
		
		window.setScene(scene);
		window.setResizable(false);
		window.showAndWait();
		
		return res;
	}
}
