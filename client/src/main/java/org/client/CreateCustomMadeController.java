package org.client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class CreateCustomMadeController extends Controller{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button AddToCart;

    @FXML
    private Button sort;

    @FXML
    private ComboBox<String> sortColor;

    @FXML
    private ComboBox<String> sortType;

    @FXML
    void addToCart(ActionEvent event) {

    }

    @FXML
    void sort(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert AddToCart != null : "fx:id=\"AddToCart\" was not injected: check your FXML file 'CreateCustomMade.fxml'.";
        assert sort != null : "fx:id=\"sort\" was not injected: check your FXML file 'CreateCustomMade.fxml'.";
        assert sortColor != null : "fx:id=\"sortColor\" was not injected: check your FXML file 'CreateCustomMade.fxml'.";
        assert sortType != null : "fx:id=\"sortType\" was not injected: check your FXML file 'CreateCustomMade.fxml'.";

        sortType.getItems().add("Bridal Bouquet");
        sortType.getItems().add("Flowerpot");
        sortType.getItems().add("Flower");

        sortColor.getItems().add("White");
        sortColor.getItems().add("Green");
        sortColor.getItems().add("Pink");
        sortColor.getItems().add("Blue");

    }

}
