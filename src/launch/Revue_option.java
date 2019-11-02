package launch;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import metier.Abonnement;
import metier.Periodicite;
import metier.Revue;

public class Revue_option implements Initializable{
	
	@FXML private TextField tf_titre;
	@FXML private TextField tf_tarif;
	@FXML private ComboBox<Periodicite> cbb_perio;
	@FXML private Button b_classement_periodicite;
	@FXML private Button b_voir_abonnement;
	@FXML private Button b_trie_periodicte;
	@FXML private Button b_trie_titre;
	@FXML private Button b_trie_tarif;
		  private static Button b_voir_abonnement_interaction;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        this.cbb_perio.setItems( FXCollections.observableArrayList(Launch_main.getdaos().getPeriodiciteDAO().findAll()));		
        
        tf_tarif.textProperty().addListener((observable, oldValue, newValue)->{
        	try {
        		Double.valueOf(newValue);
        		this.b_trie_tarif.setDisable(false);
        	}
        	catch(Exception e) {
        		this.b_trie_tarif.setDisable(true);
        	}
        });
        
        tf_titre.textProperty().addListener((observable, oldValue, newValue)->{
        	if(newValue.isEmpty()) {
        		this.b_trie_titre.setDisable(true);
        	}
        	else this.b_trie_titre.setDisable(false);
        });
        
        if(Revueframe.getselecteditem()==null) {
        	this.b_voir_abonnement.setDisable(true);
        }
        
		b_voir_abonnement_interaction=this.b_voir_abonnement;	
	}
	
	
	public void classement_periodicite() {
		Revueframe.gettableview().getItems().clear();
		Revueframe.gettableview().getItems().addAll(Launch_main.getdaos().getRevueDAO().Classement_periodicite());
	}
	
	public void trie_periodicite() {
		Revueframe.gettableview().getItems().clear();
		Revueframe.gettableview().getItems().addAll(Launch_main.getdaos().getRevueDAO().GetByPerio(new Revue(0,"","",0.0,"",cbb_perio.getValue())));
	}
	
	public void trie_titre() {
		Revueframe.gettableview().getItems().clear();
		Revueframe.gettableview().getItems().addAll(Launch_main.getdaos().getRevueDAO().getByTitre(new Revue(0,tf_titre.getText(),"",0.0,"",new Periodicite(0,""))));
	}
	
	public void trie_tarif() {
		Revueframe.gettableview().getItems().clear();
		Revueframe.gettableview().getItems().addAll(Launch_main.getdaos().getRevueDAO().GetByTarif(new Revue(0,"","",Double.valueOf(this.tf_tarif.getText()),"", new Periodicite(0,""))));
	}
	
	public void voir_abonnement() {
		ArrayList<Abonnement> liste = Launch_main.getdaos().getAbonnementDAO().GetByIDRevue(new Abonnement(null,Revueframe.getselecteditem(),"01/01/2000","01/01/2000"));
		URL fxmlURL=getClass().getResource("../fxml/abonnementframe.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
		Abonnementframe.load_Abonnement(liste, fxmlLoader);
	}
	
	@FXML
	public void test_numerique(KeyEvent key) {
		if((!Character.isDigit(key.getCharacter().charAt(0))&&(key.getCharacter().charAt(0)!='.')) || ((key.getCharacter().charAt(0)=='.')&&(tf_tarif.getText().contains("."))))
				key.consume();	
	}
	
	
	@FXML	
	public void test_combobox(ActionEvent e) {
		if(cbb_perio.getValue()!=null) {
			this.b_trie_periodicte.setDisable(false);
		}
		else {
			this.b_classement_periodicite.setDisable(true);
		}
	}
	
	public static Button getb_voir_abonnement() {
		return b_voir_abonnement_interaction;
	}

}
