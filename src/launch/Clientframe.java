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

public class Clientframe implements Initializable{

	@FXML private VBox vbox_ajout;
	@FXML private TableView<Client> tableau;
	@FXML private TableColumn<Client, String> colnom;
	@FXML private TableColumn<Client, String> colprenom;
	@FXML private TableColumn<Client, String> colnum_rue;
	@FXML private TableColumn<Client, String> colnom_rue;
	@FXML private TableColumn<Client, String> colcode_postal;
	@FXML private TableColumn<Client, String> colville;
	@FXML private TableColumn<Client, String> colpays;
	@FXML private Button b_modifier;
	@FXML private Button b_supprimer;
	@FXML private Button b_option;
	private static boolean modification=false;
	private static Client c;
	private static VBox vbox_interaction;
	private static TableView<Client> tableau_interaction;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colnom.setCellValueFactory(new PropertyValueFactory<Client, String>("nom"));
		colprenom.setCellValueFactory(new PropertyValueFactory<Client, String>("prenom"));
		colnum_rue.setCellValueFactory(new PropertyValueFactory<Client, String>("no_rue"));
		colnom_rue.setCellValueFactory(new PropertyValueFactory<Client, String>("voie"));
		colcode_postal.setCellValueFactory(new PropertyValueFactory<Client, String>("code_postal"));
		colville.setCellValueFactory(new PropertyValueFactory<Client, String>("ville"));
		colpays.setCellValueFactory(new PropertyValueFactory<Client, String>("pays"));

		this.tableau.getItems().addAll(Launch_main.getdaos().getClientDAO().findAll());
	
		tableau.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		    	b_modifier.setDisable(false);
		    	b_supprimer.setDisable(false);
		    	c = this.tableau.getSelectionModel().getSelectedItem();
		    	if(Client_option.getb_recherche_abonnement()!=null)
		    		Client_option.getb_recherche_abonnement().setDisable(false);
		    }
		});		
	}

	
	private void load_vbox_ajout() throws IOException {
		URL fxmlURL=getClass().getResource("../fxml/client.fxml");
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
	
	public void open_modifier() {
		try {
			modification=true;
			load_vbox_ajout();
		}
		catch(IOException e) {
			System.out.println(e);
		}
	}
	
	
	public void supprimer() {
		Client c = this.tableau.getSelectionModel().getSelectedItem();
		try{
			Launch_main.getdaos().getClientDAO().delete(c);
			//TODO eviter la suppression d'un elément qui est utilisé
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
		URL fxmlURL=getClass().getResource("../fxml/client_option.fxml");
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
		this.tableau.getItems().addAll(Launch_main.getdaos().getClientDAO().findAll());
	}
	
	public static boolean getmodification() {
		return modification;
	}
	
	public static Client getselecteditem() {
		return c;
	}
	
	public static TableView<Client> gettableview(){
		return tableau_interaction;
	}
	
	public static VBox getvbox() {
		return vbox_interaction;
	}
	
	
}
