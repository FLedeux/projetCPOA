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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import metier.Client;
import metier.Periodicite;
import metier.Revue;

public class Launch_client implements Initializable {

	@FXML	private Label lbl_display;
	@FXML	private TextField  tf_nom;
	@FXML	private TextField tf_prenom;
	@FXML	private TextField tf_num;
	@FXML	private TextField  tf_nom_rue;
	@FXML	private TextField  tf_code_postal;
	@FXML	private TextField  tf_ville;
	@FXML	private TextField  tf_pays;
	@FXML 	private Button b_creer;
			private Client c;
		
		public void creation() {
			//TODO manque la partie check si duplicata
			try {
				if(Clientframe.getmodification()) {
					Client c = new Client(this.c.getId(),this.tf_nom.getText(),this.tf_prenom.getText(),this.tf_num.getText(),this.tf_nom_rue.getText(),this.tf_code_postal.getText(),this.tf_ville.getText(),this.tf_pays.getText());
					Launch_main.getdaos().getClientDAO().update(c);
				}
				else {
					Client c = new Client(0,this.tf_nom.getText(),this.tf_prenom.getText(),this.tf_num.getText(),this.tf_nom_rue.getText(),this.tf_code_postal.getText(),this.tf_ville.getText(),this.tf_pays.getText());
					Launch_main.getdaos().getClientDAO().create(c);	
					lbl_display.setText(c.toString());
				}
			}
			catch(Exception e) {
		        Alert alert = new Alert(AlertType.INFORMATION);
		        if(Periodiciteframe.getmodification())
		        	alert.setTitle("tentative de modification");
		        else alert.setTitle("tentative d'ajout");
		        alert.setHeaderText(null);
		        alert.setContentText(e.getMessage()); 
		        alert.showAndWait();
			}
			Clientframe.gettableview().getItems().clear();//recharge le tableau
			Clientframe.gettableview().getItems().addAll(Launch_main.getdaos().getClientDAO().findAll());
			if(Clientframe.getmodification()) {
				Clientframe.getvbox().getChildren().clear();
			}
			else {
				this.tf_nom.setText("");
				this.tf_prenom.setText("");
				this.tf_num.setText("");
				this.tf_nom_rue.setText("");
				this.tf_code_postal.setText("");
				this.tf_ville.setText("");
				this.tf_pays.setText("");
			}

		}

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
	        this.tf_nom.textProperty().addListener((observable, oldValue, newValue)->{
	        	if (newValue.isEmpty()||this.check_un_vide(this.tf_nom))//regarde si tous le schamps autre que celui qui a une nouvelle valeur sont vide+ la nouvelle valeur
	        		this.b_creer.setDisable(true);
	        	
	        	else if(!this.check_un_vide()) //si tous les champs sont rempli
	        		this.b_creer.setDisable(false);	
	        });
	        
	        this.tf_prenom.textProperty().addListener((observable, oldValue, newValue)->{
	        	if (newValue.isEmpty()||this.check_un_vide(this.tf_prenom))//regarde si tous le schamps autre que celui qui a une nouvelle valeur sont vide+ la nouvelle valeur
	        		this.b_creer.setDisable(true);
	        	
	        	else if(!this.check_un_vide()) //si tous les champs sont rempli
	        		this.b_creer.setDisable(false);	
	        });
	        
	        this.tf_num.textProperty().addListener((observable, oldValue, newValue)->{
	        	if (newValue.isEmpty()||this.check_un_vide(this.tf_num))//regarde si tous le schamps autre que celui qui a une nouvelle valeur sont vide+ la nouvelle valeur
	        		this.b_creer.setDisable(true);
	        	
	        	else if(!this.check_un_vide()) //si tous les champs sont rempli
	        		this.b_creer.setDisable(false);	
	        });
	        
	        this.tf_nom_rue.textProperty().addListener((observable, oldValue, newValue)->{
	        	if (newValue.isEmpty()||this.check_un_vide(this.tf_nom_rue))//regarde si tous le schamps autre que celui qui a une nouvelle valeur sont vide+ la nouvelle valeur
	        		this.b_creer.setDisable(true);
	        	
	        	else if(!this.check_un_vide()) //si tous les champs sont rempli
	        		this.b_creer.setDisable(false);	
	        });
	        
	        this.tf_code_postal.textProperty().addListener((observable, oldValue, newValue)->{
	        	if (newValue.isEmpty()||this.check_un_vide(this.tf_code_postal))//regarde si tous le schamps autre que celui qui a une nouvelle valeur sont vide+ la nouvelle valeur
	        		this.b_creer.setDisable(true);
	        	
	        	else if(!this.check_un_vide()) //si tous les champs sont rempli
	        		this.b_creer.setDisable(false);	
	        });
	        
	        this.tf_ville.textProperty().addListener((observable, oldValue, newValue)->{
	        	if (newValue.isEmpty()||this.check_un_vide(this.tf_ville))//regarde si tous le schamps autre que celui qui a une nouvelle valeur sont vide+ la nouvelle valeur
	        		this.b_creer.setDisable(true);
	        	
	        	else if(!this.check_un_vide()) //si tous les champs sont rempli
	        		this.b_creer.setDisable(false);	
	        });
	        
	        this.tf_pays.textProperty().addListener((observable, oldValue, newValue)->{
	        	if (newValue.isEmpty()||this.check_un_vide(this.tf_pays))//regarde si tous le schamps autre que celui qui a une nouvelle valeur sont vide+ la nouvelle valeur
	        		this.b_creer.setDisable(true);
	        	
	        	else if(!this.check_un_vide()) //si tous les champs sont rempli
	        		this.b_creer.setDisable(false);	
	        });
	        
	        if(Clientframe.getmodification()) {
	        	c = Clientframe.getselecteditem();
	        	this.tf_nom.setText(this.c.getNom());
	        	this.tf_prenom.setText(this.c.getPrenom());
	        	this.tf_num.setText(this.c.getNo_rue());
	        	this.tf_nom_rue.setText(this.c.getVoie());
	        	this.tf_code_postal.setText(this.c.getCode_postal());
	        	this.tf_ville.setText(this.c.getVille());
	        	this.tf_pays.setText(this.c.getPays());
	        }
		}
		
		public boolean check_un_vide(TextField non_check) {
			boolean test=false;
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