package launch;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import factory.DAOFactory;
import factory.Persistance;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import metier.Client;
import metier.Periodicite;
import metier.Revue;

public class Launch_client  extends Application implements Initializable {

	@FXML	private Label lbl_display;
	@FXML	private TextField  tf_nom;
	@FXML	private TextField tf_prenom;
	@FXML	private TextField tf_num;
	@FXML	private TextField  tf_nom_rue;
	@FXML	private TextField  tf_code_postal;
	@FXML	private TextField  tf_ville;
	@FXML	private TextField  tf_pays;
	@FXML 	private Button b_creer;
			private static DAOFactory daos;
		
		
		
		@Override 
		public void start(Stage primaryStage) {
			try {
				URL fxmlURL=getClass().getResource("../fxml/client.fxml");
				FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
				Node root = fxmlLoader.load();
				Scene scene = new Scene((VBox) root, 600, 400);
				primaryStage.setScene(scene);
				primaryStage.setTitle("Gestion des revues");
				primaryStage.show();
				} catch (Exception e) {
					e.printStackTrace();
					}
			}
		
		
		
		
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

		
		public void creation() {
			Client c = new Client(0,this.tf_nom.getText(),this.tf_prenom.getText(),this.tf_num.getText(),this.tf_nom_rue.getText(),this.tf_code_postal.getText(),this.tf_ville.getText(),this.tf_pays.getText());
			daos.getClientDAO().create(c);
			lbl_display.setText(c.toString());
		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
	        this.tf_nom.textProperty().addListener((observable, oldValue, newValue)->{
	        	if (newValue.isEmpty()||this.check_un_vide(this.tf_nom))
	        		this.b_creer.setDisable(true);
	        	
	        	else if(!this.check_un_vide()) //si tous les champs sont rempli
	        		this.b_creer.setDisable(false);	
	        });
	        
	        this.tf_prenom.textProperty().addListener((observable, oldValue, newValue)->{
	        	if (newValue.isEmpty()||this.check_un_vide(this.tf_prenom))
	        		this.b_creer.setDisable(true);
	        	
	        	else if(!this.check_un_vide()) //si tous les champs sont rempli
	        		this.b_creer.setDisable(false);	
	        });
	        
	        this.tf_num.textProperty().addListener((observable, oldValue, newValue)->{
	        	if (newValue.isEmpty()||this.check_un_vide(this.tf_num))
	        		this.b_creer.setDisable(true);
	        	
	        	else if(!this.check_un_vide()) //si tous les champs sont rempli
	        		this.b_creer.setDisable(false);	
	        });
	        
	        this.tf_nom_rue.textProperty().addListener((observable, oldValue, newValue)->{
	        	if (newValue.isEmpty()||this.check_un_vide(this.tf_nom_rue))
	        		this.b_creer.setDisable(true);
	        	
	        	else if(!this.check_un_vide()) //si tous les champs sont rempli
	        		this.b_creer.setDisable(false);	
	        });
	        
	        this.tf_code_postal.textProperty().addListener((observable, oldValue, newValue)->{
	        	if (newValue.isEmpty()||this.check_un_vide(this.tf_code_postal))
	        		this.b_creer.setDisable(true);
	        	
	        	else if(!this.check_un_vide()) //si tous les champs sont rempli
	        		this.b_creer.setDisable(false);	
	        });
	        
	        this.tf_ville.textProperty().addListener((observable, oldValue, newValue)->{
	        	if (newValue.isEmpty()||this.check_un_vide(this.tf_ville))
	        		this.b_creer.setDisable(true);
	        	
	        	else if(!this.check_un_vide()) //si tous les champs sont rempli
	        		this.b_creer.setDisable(false);	
	        });
	        
	        this.tf_pays.textProperty().addListener((observable, oldValue, newValue)->{
	        	if (newValue.isEmpty()||this.check_un_vide(this.tf_pays))
	        		this.b_creer.setDisable(true);
	        	
	        	else if(!this.check_un_vide()) //si tous les champs sont rempli
	        		this.b_creer.setDisable(false);	
	        });
		}
		
		public boolean check_un_vide(TextField non_check) {
			boolean test=false;;
			if (this.tf_code_postal!=non_check) {
				test= test||this.tf_code_postal.getText().isEmpty();
			}
			if (this.tf_nom!=non_check) {
				test= test||this.tf_nom.getText().isEmpty();
			}
			if (this.tf_prenom!=non_check) {
				test= test||this.tf_prenom.getText().isEmpty();
			}
			if (this.tf_num!=non_check) {
				test= test||this.tf_num.getText().isEmpty();
			}
			if (this.tf_nom_rue!=non_check) {
				test= test||this.tf_nom_rue.getText().isEmpty();
			}
			if (this.tf_pays!=non_check) {
				test= test||this.tf_pays.getText().isEmpty();
			}
			if (this.tf_ville!=non_check) {
				test= test||this.tf_ville.getText().isEmpty();
			}
			return test;
		}
			
		public boolean check_un_vide() {
				return this.tf_code_postal.getText().isEmpty()||this.tf_nom.getText().isEmpty()||this.tf_prenom.getText().isEmpty()||this.tf_num.getText().isEmpty()||this.tf_nom_rue.getText().isEmpty()||this.tf_pays.getText().isEmpty()||this.tf_ville.getText().isEmpty();
			}
}