package launch;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import factory.DAOFactory;
import factory.Persistance;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Launch_periodicite extends Application implements Initializable{

	@FXML private TextField tf_periodicite;
	@FXML private Button b_creer;
	private static DAOFactory daos;
	
	
	public static void main(String[] args) {
		System.out.println("s�l�ctionner le mode de donn�es : 1 pour Liste M�moire, 2 pour SQL");
		int i=0;
		Scanner sc = new Scanner(System.in);
		do {
			i=sc.nextInt();
		} while(i!=1&&i!=2);
		if(i==1) daos = DAOFactory.getDAOFactory(Persistance.ListeMemoire);
		else daos = DAOFactory.getDAOFactory(Persistance.MYSQL);
		launch(args);

	}
	
	
	
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		 
		tf_periodicite.textProperty().addListener((observable, oldValue, newValue)->{
	        	if (newValue.isEmpty())this.b_creer.setDisable(true);
	        });
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			URL fxmlURL=getClass().getResource("../fxml/periodicite.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			Scene scene = new Scene((VBox) root, 600, 400);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Gestion des periodicites");
			primaryStage.show();
			} catch (Exception e) {
				e.printStackTrace();
				}
		
	}

}
