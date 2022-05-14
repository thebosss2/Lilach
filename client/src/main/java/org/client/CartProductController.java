package org.client;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.entities.CustomMadeProduct;
import org.entities.PreMadeProduct;
import org.entities.Product;

public class CartProductController extends Controller{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text description;

    @FXML
    private ImageView image;

    @FXML
    private Text price;

    @FXML
    private Text product_name;

    private Product product;

    @FXML
    void initialize() {
        assert description != null : "fx:id=\"description\" was not injected: check your FXML file 'cartProduct.fxml'.";
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'cartProduct.fxml'.";
        assert price != null : "fx:id=\"price\" was not injected: check your FXML file 'cartProduct.fxml'.";
        assert product_name != null : "fx:id=\"product_name\" was not injected: check your FXML file 'cartProduct.fxml'.";
    }

    @FXML
    void remove(ActionEvent event) {

    }

    public void setCartProduct(Product product) {
        this.product = product;
        image.setImage(product.getImage());
        price.setText(product.getPrice() + "₪");


        if(product instanceof PreMadeProduct){
            product_name.setText(((PreMadeProduct)product).getName());
            description.setText("");

        }
        else //CustomMadeProduct
        {
            product_name.setText("Custom made product " + Integer.toString(product.getId()));

        }

    }

}
