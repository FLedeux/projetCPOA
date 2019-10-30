package launch;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import metier.Client;

public class Client_option implements Initializable{

	@FXML private TextField tf_nom;
	@FXML private TextField tf_prenom;
	@FXML private Button b_recherche_nom_prenom;
	@FXML private Button b_recherche_nom;
	@FXML private Button b_recherche_abonnement;
	private static Button b_recherche_abonnement_interaction;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(Clientframe.getselecteditem()!=null)
			b_recherche_abonnement.setDisable(false);
		
		tf_nom.textProperty().addListener((observable, oldValue, newValue)->{
        	if (!newValue.isEmpty()) {
        		this.b_recherche_nom.setDisable(false);
        		this.b_recherche_nom_prenom.setDisable(this.tf_prenom.getText().isEmpty());
        	}
        	else {
        		this.b_recherche_nom.setDisable(true);
        		this.b_recherche_nom_prenom.setDisable(true);
        	}	
        });
		
		tf_prenom.textProperty().addListener((observable, oldValue, newValue)->{
				this.b_recherche_nom_prenom.setDisable(newValue.isEmpty()||(this.tf_nom.getText().isEmpty()));
		});
		b_recherche_abonnement_interaction=this.b_recherche_abonnement;
	}

	public void recherche_nom_prenom() {
		ArrayList<Client> ALclient =Launch_main.getdaos().getClientDAO().GetByNomPrenom(new Client(0,this.tf_nom.getText(),this.tf_prenom.getText(),"","","","",""));
		Clientframe.gettableview().getItems().clear();
		Clientframe.gettableview().getItems().addAll(ALclient);
	}
	
	public void recherche_nom() {
		ArrayList<Client> ALclient =Launch_main.getdaos().getClientDAO().GetByNom(new Client(0,this.tf_nom.getText(),"","","","","",""));
		Clientframe.gettableview().getItems().clear();
		Clientframe.gettableview().getItems().addAll(ALclient);
	}
	
	public void trie_alphabetique() {
		Clientframe.gettableview().getItems().clear();
		Clientframe.gettableview().getItems().addAll(Launch_main.getdaos().getClientDAO().Trie_Alphabetique());
	}
	
	public void trie_ville() {
		Clientframe.gettableview().getItems().clear();
		Clientframe.gettableview().getItems().addAll(Launch_main.getdaos().getClientDAO().Trie_Ville());
	}
	
	public void voir_abonnement() {
		//TODO pas encore pret
	}
	
	public static Button getb_recherche_abonnement() {
		return b_recherche_abonnement_interaction;
	}
	
	
}
