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
    private ImageView image;

    @FXML
    private Button minus;

    @FXML
    private Button plus;

    @FXML
    private Text price;

    @FXML
    private Text priceBeforeDiscount;

    @FXML
    private Text product_name;

    @FXML
    void addProduct(ActionEvent event) {
        price.setText(Integer.toString(Integer.parseInt(price.getText())+1));
    }

    @FXML
    void goToProductView(MouseEvent event) {

    }

    @FXML
    void minusProduct(ActionEvent event) {
        price.setText(Integer.toString(Integer.parseInt(price.getText())-1));
    }

    @FXML
    void initialize() {
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'CustomMadeBaseProduct.fxml'.";
        assert minus != null : "fx:id=\"minus\" was not injected: check your FXML file 'CustomMadeBaseProduct.fxml'.";
        assert plus != null : "fx:id=\"plus\" was not injected: check your FXML file 'CustomMadeBaseProduct.fxml'.";
        assert price != null : "fx:id=\"price\" was not injected: check your FXML file 'CustomMadeBaseProduct.fxml'.";
        assert priceBeforeDiscount != null : "fx:id=\"priceBeforeDiscount\" was not injected: check your FXML file 'CustomMadeBaseProduct.fxml'.";
        assert product_name != null : "fx:id=\"product_name\" was not injected: check your FXML file 'CustomMadeBaseProduct.fxml'.";

    }

}
