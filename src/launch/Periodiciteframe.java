package launch;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import metier.Periodicite;

public class Periodiciteframe implements Initializable{

	@FXML private VBox vbox_ajout;
	@FXML private TableView<Periodicite> tableau;
	@FXML private TableColumn<Periodicite, String> colNom;
	@FXML private Button b_modifier;
	@FXML private Button b_supprimer;
	private static boolean modification=false;
	private static Periodicite p;
	private static VBox vboxtest;
	private static TableView<Periodicite> tableviewtest;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colNom.setCellValueFactory(new PropertyValueFactory<Periodicite, String>("nom"));
		this.tableau.getItems().addAll(Launch_main.getdaos().getPeriodiciteDAO().findAll());
	
		tableau.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		    	b_modifier.setDisable(false);
		    	b_supprimer.setDisable(false);
		    	p = this.tableau.getSelectionModel().getSelectedItem();
		    }
		});
	}
	
	private void load_vbox_ajout() throws IOException {
		URL fxmlURL=getClass().getResource("../fxml/periodicite.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
		VBox vboxset = fxmlLoader.load();
		this.vbox_ajout.getChildren().clear();
		this.vbox_ajout.getChildren().add(vboxset);
		vboxtest=this.vbox_ajout;
		tableviewtest=this.tableau;
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
		Periodicite p = this.tableau.getSelectionModel().getSelectedItem();
		try{
			Launch_main.getdaos().getPeriodiciteDAO().delete(p);
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
	
	public void reload_tableau() {
		this.tableau.getItems().clear();
		this.tableau.getItems().addAll(Launch_main.getdaos().getPeriodiciteDAO().findAll());
	}
	
	public static boolean getmodification() {
		return modification;
	}
	
	public static Periodicite getselecteditem() {
		return p;
	}
	
	public static TableView<Periodicite> gettableview(){
		return tableviewtest;
	}
	
	public static VBox getvbox() {
		return vboxtest;
	}
	
	
}
