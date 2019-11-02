package launch;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import metier.Abonnement;
import metier.Client;
import metier.Revue;

public class Abonnement_ajout implements Initializable{

	@FXML private ComboBox<Client> cbb_client;
	@FXML private ComboBox<Revue> cbb_revue;
	@FXML private DatePicker dp_debut;
	@FXML private DatePicker dp_fin;
	@FXML private Button b_creer;
	@FXML private Label lbl_display;
		  private Abonnement a;

	
	
	
	
	
	
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		 
		a = Abonnementframe.getselecteditem();
		
        this.cbb_client.setItems( FXCollections.observableArrayList(Launch_main.getdaos().getClientDAO().findAll()));	
        this.cbb_revue.setItems( FXCollections.observableArrayList(Launch_main.getdaos().getRevueDAO().findAll()));
        
        if(Abonnementframe.getmodification()) {
        	cbb_client.setValue(a.getClient());
        	cbb_revue.setValue(a.getRevue());
        	dp_debut.setValue(a.getDate_debut());
        	dp_fin.setValue(a.getDate_fin());
        	cbb_client.setDisable(true);
        	cbb_revue.setDisable(true);
        	b_creer.setText("modifier");
        	
        }
	}


	
	public void check() {
		if((this.cbb_client.getValue()!=null)&&(this.cbb_revue.getValue()!=null)&&(this.dp_debut.getValue()!=null)&&(this.dp_fin.getValue()!=null)&&(this.dp_fin.getValue().isAfter(this.dp_debut.getValue())))
			this.b_creer.setDisable(false);
		else this.b_creer.setDisable(true);
	}
	
	public void creation() {
		try {
			if(Abonnementframe.getmodification()) {
				Abonnement a = new Abonnement(this.a.getClient(),this.a.getRevue(),dp_debut.getValue(),dp_fin.getValue());
				Launch_main.getdaos().getAbonnementDAO().update(a);
			}
			else {
				Abonnement a = new Abonnement(cbb_client.getValue(),cbb_revue.getValue(),dp_debut.getValue(),dp_fin.getValue());
				Launch_main.getdaos().getAbonnementDAO().create(a);
			}
			
		}
		catch(Exception e){
	        Alert alert = new Alert(AlertType.INFORMATION);
	        if(Periodiciteframe.getmodification())
	        	alert.setTitle("tentative de modification");
	        else alert.setTitle("tentative d'ajout");
	        alert.setHeaderText(null);
	        alert.setContentText(e.getMessage()); 
	        alert.showAndWait();
		}
		Abonnementframe.gettableview().getItems().clear();//recharge le tableau
		Abonnementframe.gettableview().getItems().addAll(Launch_main.getdaos().getAbonnementDAO().findAll());
		if(Abonnementframe.getmodification()) {
			Abonnementframe.getvbox().getChildren().clear();
		}
		else {
			cbb_client.setValue(null);
			cbb_revue.setValue(null);
			dp_debut.setValue(null);
			dp_fin.setValue(null);
		}
	}

}
