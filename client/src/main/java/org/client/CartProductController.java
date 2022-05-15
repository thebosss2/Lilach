package org.client;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.entities.CustomMadeProduct;
import org.entities.PreMadeProduct;
import org.entities.Product;

public class CartProductController extends Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text description;


    @FXML
    private Button Duplicate;

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
    void duplicate(ActionEvent event) {
        if (product instanceof PreMadeProduct)
            App.client.cart.insertProduct(new PreMadeProduct(((PreMadeProduct) product).getName(), product.getByteImage(), product.getPrice(), ((PreMadeProduct) product).getPriceBeforeDiscount()));
        else{
            CustomMadeProduct p= new CustomMadeProduct(new LinkedList<PreMadeProduct>(((CustomMadeProduct)product).getProducts()),product.getPrice(), product.getByteImage());
            App.client.cart.insertProduct(p);
        }

        //TODO refresh
    }

    @FXML
    void remove(ActionEvent event) {
        App.client.cart.removeProduct(product.getId());

        //TODO refresh
    }

    public void setCartProduct(Product product) {
        this.product = product;
        image.setImage(product.getImage());
        price.setText("Price: " + product.getPrice() + "₪");


        if (product instanceof PreMadeProduct) {
            product_name.setText(((PreMadeProduct) product).getName());
            description.setText("");

        } else //CustomMadeProduct
        {
            product_name.setText("Custom made product " + Integer.toString(product.getId()));

        }

    }

}
