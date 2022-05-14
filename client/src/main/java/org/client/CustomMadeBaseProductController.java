package org.client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class CustomMadeBaseProductController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button add_to_product;

    @FXML
    private ImageView image;

    @FXML
    private Text price;

    @FXML
    private Text priceBeforeDiscount;

    @FXML
    private Text product_name;

    @FXML
    void addToCart(ActionEvent event) {

    }

    @FXML
    void goToProductView(MouseEvent event) {

    }


    @FXML
    void initialize() {
        assert add_to_product != null : "fx:id=\"add_to_product\" was not injected: check your FXML file 'CustomMadeBaseProduct.fxml'.";
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'CustomMadeBaseProduct.fxml'.";
        assert price != null : "fx:id=\"price\" was not injected: check your FXML file 'CustomMadeBaseProduct.fxml'.";
        assert priceBeforeDiscount != null : "fx:id=\"priceBeforeDiscount\" was not injected: check your FXML file 'CustomMadeBaseProduct.fxml'.";
        assert product_name != null : "fx:id=\"product_name\" was not injected: check your FXML file 'CustomMadeBaseProduct.fxml'.";

    }

}
