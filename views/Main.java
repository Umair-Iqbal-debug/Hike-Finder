package views;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.TrailBag;
import model.UserBag;
import utils.BackUpRestoreTools;

public class Main extends Application 
{
	 
	public static Stage window;
	public static UserBag userBag;
	public static TrailBag trailBag;
	
	public static void main(String[] args) 
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		userBag = BackUpRestoreTools.restoreUserBag();
		trailBag = BackUpRestoreTools.restoreTrailBag();
		trailBag.setUserBag(userBag);

		 window = primaryStage;
		 
		 
		 window.setOnCloseRequest(e->{
			 
			 boolean choice =  ConfirmationBox.display("","Are you sure you want to exit ?");
			 e.consume();
			 try 
			 {
				 if(userBag.isLoggedIn())
				 {
					if(choice) {
						userBag.logout();
					}
					
				 }
			 }
			 
			 catch(Exception exc)
			 {
				 
			 }
			 
			 finally
			 {
				 if(choice) window.close();
				 BackUpRestoreTools.backupUserBag(userBag);
				 BackUpRestoreTools.backupTrailBag(trailBag);
			 }
		 });
		 
		 
		 
		 window.setResizable(false);
		 Pane signInPane = new SignInPane(userBag).getRoot();
		 Scene scene = new Scene(signInPane,400,400);
		 primaryStage.setTitle("Hike App");
		 primaryStage.setScene(scene);
		 primaryStage.show();
	}
}
