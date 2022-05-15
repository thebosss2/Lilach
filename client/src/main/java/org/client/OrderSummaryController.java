package org.client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class OrderSummaryController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button cancelOrder;

    @FXML
    private Text price;

    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert cancelOrder != null : "fx:id=\"cancelOrder\" was not injected: check your FXML file 'OrderSummary.fxml'.";
        assert price != null : "fx:id=\"price\" was not injected: check your FXML file 'OrderSummary.fxml'.";

    }

}
