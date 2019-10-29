package launch;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import metier.Periodicite;

public class Periodiciteframe implements Initializable{

	@FXML private VBox vbox_ajout;
	@FXML private TableView<Periodicite> tableau;
	@FXML private TableColumn<Periodicite, String> colNom;
	
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colNom.setCellValueFactory(new PropertyValueFactory<Periodicite, String>("nom"));
		this.tableau.getColumns().setAll(colNom);
		this.tableau.getItems().addAll(Launch_main.getdaos().getPeriodiciteDAO().findAll());
	}
	
}
