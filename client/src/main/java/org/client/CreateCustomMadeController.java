package org.client;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CreateCustomMadeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button AddToCart;

    @FXML
    void addToCart(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert AddToCart != null : "fx:id=\"AddToCart\" was not injected: check your FXML file 'CreateCustomMade.fxml'.";

    }

}
