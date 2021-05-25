/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.awt.List;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Model;
import it.polito.tdp.imdb.model.RegistaNumero;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaAffini"
    private Button btnCercaAffini; // Value injected by FXMLLoader

    @FXML // fx:id="boxAnno"
    private ComboBox<String> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxRegista"
    private ComboBox<Director> boxRegista; // Value injected by FXMLLoader

    @FXML // fx:id="txtAttoriCondivisi"
    private TextField txtAttoriCondivisi; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	String input = boxAnno.getValue();
    	
    	if(input == null) {
    		txtResult.appendText("ERRORE: scegliere un anno per creare il grafo");
    		return;
    	}
    	
    	model.creaGrafo(Integer.parseInt(input));
    	
    	txtResult.appendText("GRAFO CREATO:\n");
    	txtResult.appendText("#VERTICI: " + model.getVertex().size()+"\n");
    	txtResult.appendText("#ARCHI: " + model.getSizeEdge());
    	
    	this.boxRegista.getItems().setAll(model.getVertex());
    	this.boxRegista.setDisable(false);
    	this.btnAdiacenti.setDisable(false);
    }

    @FXML
    void doRegistiAdiacenti(ActionEvent event) {
    	txtResult.clear();
    	Director dir = this.boxRegista.getValue();
    	if(dir == null) {
    		txtResult.appendText("ERRORE: selezionare un regista");
    	}
    	
    	txtResult.appendText("REGISTI ADIACENTI A: " + dir.toString());
    	for(RegistaNumero rn: model.getAffini(dir)) {
    		txtResult.appendText("\n" + rn.toString());
    	}
    	
    	this.txtAttoriCondivisi.setDisable(false);
    	this.btnCercaAffini.setDisable(false);
    }

    @FXML
    void doRicorsione(ActionEvent event) {

    	txtResult.clear();
    	
    	if(!isValid()) {
    		return;
    	}
    	
    	Director dir = this.boxRegista.getValue();
    	int c = Integer.parseInt(this.txtAttoriCondivisi.getText());
    	
    	txtResult.appendText("LISTA REGISTI ASSOCIATI A: " + dir );
    	
    	for(Director d: model.getPercorso(dir, c)) {
    		txtResult.appendText("\n" + d);
    	}
    	
    	txtResult.appendText("\n\nTOTALE ATTORI = " + model.getNAttori());
    }

    private boolean isValid() {
		String input = this.txtAttoriCondivisi.getText();
		
		if(input == null) {
			txtResult.appendText("ERRORE: inserire un numero di attori.");
			return false;
		}else {
			try {
				Integer.parseInt(input);
			}catch(NumberFormatException nfe) {
				txtResult.appendText("ERRORE: inserire un numero intero non una stringa");
				return false;
			}
		}
		return true;
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaAffini != null : "fx:id=\"btnCercaAffini\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxRegista != null : "fx:id=\"boxRegista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAttoriCondivisi != null : "fx:id=\"txtAttoriCondivisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
   public void setModel(Model model) {
    	
    	this.model = model;
    	this.boxAnno.getItems().setAll(model.getAnni());
    }
    
}
