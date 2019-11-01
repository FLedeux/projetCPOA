package launch;

import java.io.IOException;
import java.net.URL;

import factory.DAOFactory;
import factory.Persistance;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Launch_main extends Application{
	
	@FXML private MenuItem mi_sql;
	@FXML private MenuItem mi_liste;
	@FXML private VBox vbox_container;
	private static DAOFactory daos;
	private static VBox vbox_interaction;

	
	public static void main(String[] args) {
		daos = DAOFactory.getDAOFactory(Persistance.MYSQL);
		launch(args);

	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			URL fxmlURL=getClass().getResource("../fxml/mainframe.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			Scene scene = new Scene((VBox) root, 600, 800);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Gestion des periodicites");
			primaryStage.show();
			
			} catch (Exception e) {
				e.printStackTrace();
			}		
	}
	
	
	public void MySQL_click() {
		this.daos = DAOFactory.getDAOFactory(Persistance.MYSQL);
		this.mi_sql.setDisable(true);
		this.mi_liste.setDisable(false);
		this.vbox_container.getChildren().clear();
	}
	
	public void ListeMemoire_click() {
		this.daos = DAOFactory.getDAOFactory(Persistance.ListeMemoire);
		this.mi_liste.setDisable(true);
		this.mi_sql.setDisable(false);
		this.vbox_container.getChildren().clear();
	}
	
	
	public void abonnement_click() {
		try {
			URL fxmlURL=getClass().getResource("../fxml/abonnementframe.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			VBox e = fxmlLoader.load();
			this.vbox_container.getChildren().clear();
			this.vbox_container.getChildren().add(e);
		}
		catch(IOException e) {
			System.out.println(e);
		}
	}
	
	public void client_click() {
		try {
			URL fxmlURL=getClass().getResource("../fxml/clientframe.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			VBox e = fxmlLoader.load();
			this.vbox_container.getChildren().clear();
			this.vbox_container.getChildren().add(e);
			vbox_interaction = this.vbox_container;
		}
		catch(IOException e) {
			System.out.println(e);
		}

	}
	
	public void Periodicite_click() {
		try {
			URL fxmlURL=getClass().getResource("../fxml/periodiciteframe.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			VBox e = fxmlLoader.load();
			this.vbox_container.getChildren().clear();
			this.vbox_container.getChildren().add(e);
		}
		catch(IOException e) {
			System.out.println(e);
		}

	}
	
	public void Revue_click() {
		try {
			URL fxmlURL=getClass().getResource("../fxml/revueframe.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			VBox e = fxmlLoader.load();
			this.vbox_container.getChildren().clear();
			this.vbox_container.getChildren().add(e);
			vbox_interaction = this.vbox_container;
		}
		catch(IOException e) {
			System.out.println(e);
		}

	}
	
	public static DAOFactory getdaos() {
		return daos;
	}
	
	public static VBox getVBox() {
		return vbox_interaction;
	}
	

}
