package launch;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import factory.DAOFactory;
import factory.Persistance;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import metier.Abonnement;
import metier.Client;
import metier.Revue;

public class Launch_abonnement extends Application implements Initializable{

	@FXML private ComboBox<Client> cbb_client;
	@FXML private ComboBox<Revue> cbb_revue;
	@FXML private DatePicker dp_debut;
	@FXML private DatePicker dp_fin;
	@FXML private Button b_creer;
	@FXML	private Label lbl_display;
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
		 
        this.cbb_client.setItems( FXCollections.observableArrayList(daos.getClientDAO().findAll()));	
        this.cbb_revue.setItems( FXCollections.observableArrayList(daos.getRevueDAO().findAll()));
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			URL fxmlURL=getClass().getResource("../fxml/abonnement.fxml");
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
	
	public void check() {
		if((this.cbb_client.getValue()!=null)&&(this.cbb_revue.getValue()!=null)&&(this.dp_debut.getValue()!=null)&&(this.dp_fin.getValue()!=null)&&(this.dp_fin.getValue().isAfter(this.dp_debut.getValue())))
			this.b_creer.setDisable(false);
		else this.b_creer.setDisable(true);
	}
	
	public void creation() {
		Abonnement a = new Abonnement(this.cbb_client.getValue().getId(),this.cbb_revue.getValue().getId(),this.dp_debut.getValue(),this.dp_fin.getValue());
		this.daos.getAbonnementDAO().create(a);
		lbl_display.setText(a.toString());
	}

}
