package launch;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import metier.Abonnement;
import metier.Client;
import metier.Periodicite;
import metier.Revue;

public class Abonnementframe implements Initializable{
	@FXML private VBox vbox_ajout;
	@FXML private TableView<Abonnement> tableau;
	@FXML private TableColumn<Abonnement, Client> colclient;
	@FXML private TableColumn<Abonnement, Revue> colrevue;
	@FXML private TableColumn<Abonnement, LocalDate> coldated;
	@FXML private TableColumn<Abonnement, LocalDate> coldatef;
	@FXML private Button b_supprimer;
	@FXML private Button b_modifier;
	private static boolean modification=false;
	private static Abonnement a;
	private static VBox vbox_interaction;
	private static TableView<Abonnement> tableau_interaction;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		colclient.setCellValueFactory(new PropertyValueFactory<Abonnement, Client>("client"));
		colrevue.setCellValueFactory(new PropertyValueFactory<Abonnement, Revue>("revue"));
		coldated.setCellValueFactory(new PropertyValueFactory<Abonnement, LocalDate>("date_debut"));
		coldatef.setCellValueFactory(new PropertyValueFactory<Abonnement, LocalDate>("date_fin"));

		this.tableau.getItems().addAll(Launch_main.getdaos().getAbonnementDAO().findAll());
	
		tableau.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		    	b_modifier.setDisable(false);
		    	b_supprimer.setDisable(false);
		    	a = this.tableau.getSelectionModel().getSelectedItem();
		    }
		});
		vbox_interaction=this.vbox_ajout;
		tableau_interaction=this.tableau;
	}
	
	private void load_vbox_ajout() throws IOException {
		URL fxmlURL=getClass().getResource("../fxml/abonnement.fxml");
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
		Abonnement a = tableau.getSelectionModel().getSelectedItem();
		Launch_main.getdaos().getAbonnementDAO().delete(a);
		this.reload_tableau();
	}
	
	public void open_option() {
		try {
			URL fxmlURL=getClass().getResource("../fxml/abonnement_option.fxml");
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
		this.tableau.getItems().addAll(Launch_main.getdaos().getAbonnementDAO().findAll());
	}
	
	public static boolean getmodification() {
		return modification;
	}
	
	public static Abonnement getselecteditem() {
		return a;
	}
	
	public static TableView<Abonnement> gettableview(){
		return tableau_interaction;
	}
	
	public static VBox getvbox() {
		return vbox_interaction;
	}
	
	public static void load_Abonnement(ArrayList<Abonnement> liste,FXMLLoader fxmlLoader) {
		try {
			VBox e = fxmlLoader.load();
			Launch_main.getVBox().getChildren().clear();
			Launch_main.getVBox().getChildren().add(e);
			gettableview().getItems().clear();
			gettableview().getItems().addAll(liste);
		}
		catch(IOException e) {
			System.out.println(e);
		}		
	}

	
	
	
}
