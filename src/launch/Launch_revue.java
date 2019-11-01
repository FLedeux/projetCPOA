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
import metier.Periodicite;
import metier.Revue;

public class Launch_revue implements Initializable {

@FXML	private Label lbl_display;
@FXML	private TextField  tf_titre;
@FXML	private TextField tf_description;
@FXML	private TextField tf_tarif;
@FXML	private ComboBox<Periodicite> cbb_perio;
@FXML 	private Button b_creer;
		private Revue r;
	
	public void creation() {
		try {
			if(Revueframe.getmodification()) {
				Revue r = new Revue(this.r.getId(),this.tf_titre.getText(),this.tf_description.getText(),Double.valueOf(this.tf_tarif.getText()),this.tf_titre.getText()+".png",this.cbb_perio.getValue());
				Launch_main.getdaos().getRevueDAO().update(r);
			}
			else {
				Revue r = new Revue(0,tf_titre.getText(),tf_description.getText(),Double.valueOf(tf_tarif.getText()),tf_titre.getText()+".png",cbb_perio.getValue());
				Launch_main.getdaos().getRevueDAO().create(r);
				lbl_display.setText(r.toString());	
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
		Revueframe.gettableview().getItems().clear();//recharge le tableau
		Revueframe.gettableview().getItems().addAll(Launch_main.getdaos().getRevueDAO().findAll());
		if(Revueframe.getmodification()) {
			Revueframe.getvbox().getChildren().clear();
		}
		else {
			this.tf_titre.setText("");
			this.tf_description.setText("");
			this.tf_tarif.setText("");
			this.cbb_perio.setValue(null);
		}
	}
	
	
	
	@FXML
	public void test_numerique(KeyEvent key) {
		if((!Character.isDigit(key.getCharacter().charAt(0))&&(key.getCharacter().charAt(0)!='.')) || ((key.getCharacter().charAt(0)=='.')&&(tf_tarif.getText().contains("."))))
				key.consume();	
	}
	
	
	@FXML	
	public void test_combobox(ActionEvent e) {
		if((cbb_perio.getValue()!=null)&&(!tf_titre.getText().isEmpty())&&(!tf_tarif.getText().isEmpty())) {
			b_creer.setDisable(false);
		}
		else b_creer.setDisable(true);
	}
			




	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        this.cbb_perio.setItems( FXCollections.observableArrayList(Launch_main.getdaos().getPeriodiciteDAO().findAll()));		

        tf_tarif.textProperty().addListener((observable, oldValue, newValue)->{
        	try {//regarde si on peut convertir ce qui est dans tf_tarif en double
        		Double.valueOf(newValue);
        		if((!tf_titre.getText().isEmpty())&&(cbb_perio.getValue()!=null)) 
            		this.b_creer.setDisable(false);
        	}
        	catch(Exception e) {//si il peut pas, on ne peut pas creer
        		this.b_creer.setDisable(true);
        	}
        });
        
        tf_titre.textProperty().addListener((observable, oldValue, newValue)->{
        	if (newValue.isEmpty())
        		this.b_creer.setDisable(true);
        	
        	else if((!tf_tarif.getText().isEmpty())&&(cbb_perio.getValue()!=null)) 
        		this.b_creer.setDisable(false);
        	else this.b_creer.setDisable(true);
        });
        
        
        tf_titre.textProperty().addListener((observable, oldValue, newValue)->{
        	if (newValue.length()>400) {
        		tf_titre.setText( newValue.subSequence(0, 399).toString());
        	}
        	
        });
        
        //initialization en cas de modification
        if(Revueframe.getmodification()) {
        	r=Revueframe.getselecteditem();
        	this.tf_titre.setText(r.getTitre());
        	this.tf_description.setText(r.getDescription());
        	this.cbb_perio.setValue(r.getPerio());
        	this.tf_tarif.setText(String.valueOf(r.getTarif_numero()));
        	this.b_creer.setText("modifier");
        }
	}
	

}
	