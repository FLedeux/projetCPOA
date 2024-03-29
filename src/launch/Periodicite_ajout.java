package launch;

import java.net.URL;
import java.util.ResourceBundle;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import metier.Periodicite;

public class Periodicite_ajout  implements Initializable{

	@FXML private TextField tf_periodicite;
	@FXML private Button b_creer;
	@FXML private Label lbl_display;
	private Periodicite p;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		 
		tf_periodicite.textProperty().addListener((observable, oldValue, newValue)->{
	        	if (newValue.isEmpty()) this.b_creer.setDisable(true);
	        	else this.b_creer.setDisable(false);
	        }
		);
		if(Periodiciteframe.getmodification()) {
			this.p=Periodiciteframe.getselecteditem();
			this.tf_periodicite.setText(p.getNom());
			this.b_creer.setText("modifier");
		}
	}

	
	public void creation() {
		try {
			if(Periodiciteframe.getmodification()) {
				Periodicite p = new Periodicite(this.p.getId(),this.tf_periodicite.getText());
				Launch_main.getdaos().getPeriodiciteDAO().update(p);
			}
			else {
				Periodicite p = new Periodicite(0,this.tf_periodicite.getText());		
				Launch_main.getdaos().getPeriodiciteDAO().create(p);	
				lbl_display.setText(p.toString());
			}
		}
		catch(Exception e) {
	        Alert alert = new Alert(AlertType.INFORMATION);
	        if(Periodiciteframe.getmodification())
	        	alert.setTitle("tentative de modification");
	        else alert.setTitle("tentative d'ajout");
	        alert.setHeaderText(null);
	        alert.setContentText(e.getMessage()); 
	        alert.showAndWait();
		}
		Periodiciteframe.gettableview().getItems().clear();//recharge le tableau
		Periodiciteframe.gettableview().getItems().addAll(Launch_main.getdaos().getPeriodiciteDAO().findAll());
		if(Periodiciteframe.getmodification()) {
			Periodiciteframe.getvbox().getChildren().clear();
		}
		else this.tf_periodicite.setText("");
	}

}
