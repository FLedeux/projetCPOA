package launch;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import metier.Client;
import metier.Periodicite;
import metier.Revue;

public class Revueframe implements Initializable{

	@FXML private VBox vbox_ajout;
	@FXML private TableView<Revue> tableau;
	@FXML private TableColumn<Revue, Integer> colqte;
	@FXML private TableColumn<Revue, String> coltitre;
	@FXML private TableColumn<Revue, Double> colprix;
	@FXML private TableColumn<Revue, Periodicite> colperio;
	@FXML private Button b_modifier;
	@FXML private Button b_supprimer;
	private static boolean modification=false;
	private static Revue r;
	private static VBox vbox_interaction;
	private static TableView<Revue> tableau_interaction;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colqte.setCellValueFactory(new PropertyValueFactory<Revue, Integer>("quantite"));
		coltitre.setCellValueFactory(new PropertyValueFactory<Revue, String>("titre"));
		colprix.setCellValueFactory(new PropertyValueFactory<Revue, Double>("tarif_numero"));
		colperio.setCellValueFactory(new PropertyValueFactory<Revue, Periodicite>("perio"));
		
		
		this.tableau.getItems().addAll(Launch_main.getdaos().getRevueDAO().findAll());
		
		tableau.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		    	b_modifier.setDisable(false);
		    	b_supprimer.setDisable(false);
		    	r = this.tableau.getSelectionModel().getSelectedItem();
		    	if(Revue_option.getb_voir_abonnement()!=null)
		    		Revue_option.getb_voir_abonnement().setDisable(false);
		    }
		});		
	}
	

	private void load_vbox_ajout() throws IOException {
		URL fxmlURL=getClass().getResource("../fxml/revue.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
		VBox vboxset = fxmlLoader.load();
		this.vbox_ajout.getChildren().clear();
		this.vbox_ajout.getChildren().add(vboxset);
		vbox_interaction=this.vbox_ajout;
		tableau_interaction=this.tableau;
	}
	
	
	public void open_ajout() {
		try {
			modification=false;
			load_vbox_ajout();
			
		}
		catch(IOException e) {
			System.out.println(e);
		}
	}

	public void open_modif() {
		try {
			modification=true;
			load_vbox_ajout();
			
		}
		catch(IOException e) {
			System.out.println(e);
		}
	}

	public void supprimer() {
		try{
			Launch_main.getdaos().getRevueDAO().delete(r);//TODO eviter suppression élément utiliser dans abonnement
			//eviter la suppression d'un elément qui est utilisé
		}
		catch(Exception e) {
	        Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("tentative de suppression");
	        alert.setHeaderText(null);
	        alert.setContentText(e.getMessage()); 
	        alert.showAndWait();
		}
		this.reload_tableau();
	}

	public void open_option() {
		try {
			URL fxmlURL=getClass().getResource("../fxml/revue_option.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			VBox vboxset = fxmlLoader.load();
			this.vbox_ajout.getChildren().clear();
			this.vbox_ajout.getChildren().add(vboxset);
			vbox_interaction=this.vbox_ajout;
			tableau_interaction=this.tableau;
			}
			catch(IOException e) {
				System.out.println(e);
			}
	}
	
	public void reload_tableau() {
		this.tableau.getItems().clear();
		this.tableau.getItems().addAll(Launch_main.getdaos().getRevueDAO().findAll());
	}
	
	public static boolean getmodification() {
		return modification;
	}
	
	public static Revue getselecteditem() {
		return r;
	}
	
	public static TableView<Revue> gettableview(){
		return tableau_interaction;
	}
	
	public static VBox getvbox() {
		return vbox_interaction;
	}
	
}

