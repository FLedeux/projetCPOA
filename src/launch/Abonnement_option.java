package launch;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import metier.Abonnement;
import metier.Client;
import metier.Revue;

public class Abonnement_option implements Initializable{

	@FXML private ComboBox<Client> cbb_client;
	@FXML private ComboBox<Revue> cbb_revue;
	@FXML private Button b_client_revue;
	@FXML private Button b_client;
	@FXML private Button b_revue;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
        this.cbb_client.setItems( FXCollections.observableArrayList(Launch_main.getdaos().getClientDAO().findAll()));	
        this.cbb_revue.setItems( FXCollections.observableArrayList(Launch_main.getdaos().getRevueDAO().findAll()));		
        
        if(Abonnementframe.getselecteditem()!=null) {
        	cbb_client.setValue(Abonnementframe.getselecteditem().getClient());
        	cbb_revue.setValue(Abonnementframe.getselecteditem().getRevue());
        }
        else {
        	b_client_revue.setDisable(true);
        	b_client.setDisable(true);
        	b_revue.setDisable(true);	
        }
	}
	
	public void abonnement_en_cours() {
		Abonnementframe.gettableview().getItems().clear();
		Abonnementframe.gettableview().getItems().addAll(Launch_main.getdaos().getAbonnementDAO().Abonnement_en_cours());
	}
	
	public void recherche_client_revue() {
		Abonnementframe.gettableview().getItems().clear();
		Abonnementframe.gettableview().getItems().addAll(Launch_main.getdaos().getAbonnementDAO().GetByClientEtRevue(new Abonnement(cbb_client.getValue(),cbb_revue.getValue(),"01/01/2000","01/01/2000")));
	}
	
	public void recherche_client() {
		Abonnementframe.gettableview().getItems().clear();
		Abonnementframe.gettableview().getItems().addAll(Launch_main.getdaos().getAbonnementDAO().GetByIDClient(new Abonnement(cbb_client.getValue(),null,"01/01/2000","01/01/2000")));
	}
	
	public void recherche_revue() {
		Abonnementframe.gettableview().getItems().clear();
		Abonnementframe.gettableview().getItems().addAll(Launch_main.getdaos().getAbonnementDAO().GetByIDRevue(new Abonnement(null,cbb_revue.getValue(),"01/01/2000","01/01/2000")));
	}
	
	public void check_client() {
		if(cbb_client.getValue()==null) {
			b_client_revue.setDisable(true);
        	b_client.setDisable(true);
		}
		else {
			b_client_revue.setDisable(cbb_revue.getValue()==null);
        	b_client.setDisable(false);
		}
	}
	
	public void check_revue() {
		if(cbb_revue.getValue()==null) {
			b_client_revue.setDisable(true);
        	b_revue.setDisable(true);
		}
		else {
			b_client_revue.setDisable(cbb_client.getValue()==null);
        	b_revue.setDisable(false);
		}
	}

}
