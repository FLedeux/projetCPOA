package launch;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import metier.Periodicite;
import metier.Revue;

public class Revueframe implements Initializable{

	@FXML private VBox vbox_ajout;
	@FXML private TableView<Revue> tableau;
	@FXML private TableColumn<Revue, Integer> colqte;
	@FXML private TableColumn<Revue, String> coltitre;
	@FXML private TableColumn<Revue, Double> colprix;
	@FXML private TableColumn<Revue, String> colperio;
	@FXML private Button b_modifier;
	@FXML private Button b_supprimer;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colqte.setCellValueFactory(new PropertyValueFactory<Revue, Integer>("quantite"));
		coltitre.setCellValueFactory(new PropertyValueFactory<Revue, String>("titre"));
		colprix.setCellValueFactory(new PropertyValueFactory<Revue, Double>("tarif_numero"));
		colperio.setCellValueFactory(new PropertyValueFactory<Revue, String>("perio"));
		
		this.tableau.getItems().addAll(Launch_main.getdaos().getRevueDAO().findAll());
		
	}
	
	
	public void open_ajout() {
	
	}

	public void open_modif() {
		
	}

	public void supprimer() {
		
	}

	public void open_option() {
		
	}
}

