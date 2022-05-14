package org.client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class CartController extends Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button clean_cart;

    @FXML
    private Button create_order;

    @FXML
    private ScrollPane scroll_pane;

    @FXML
    private VBox vbox;

    @FXML
    void cleanCart(ActionEvent event) {

    }

    @FXML
    void createOrder(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert clean_cart != null : "fx:id=\"clean_cart\" was not injected: check your FXML file 'Cart.fxml'.";
        assert create_order != null : "fx:id=\"create_order\" was not injected: check your FXML file 'Cart.fxml'.";
        assert scroll_pane != null : "fx:id=\"scroll_pane\" was not injected: check your FXML file 'Cart.fxml'.";
        assert vbox != null : "fx:id=\"vbox\" was not injected: check your FXML file 'Cart.fxml'.";

        App.client.setController(this);

        FXMLLoader fxmlLoader;
        for(int i=0; i<App.client.cart.getProducts().size(); i++)
        {
            fxmlLoader = new FXMLLoader(getClass().getResource("Product.fxml"));
            vbox.getChildren().add(fxmlLoader.load());  //Adds new product pane to the screen.
            ProductController controller = fxmlLoader.getController();
            controller.setSkeleton(this.getSkeleton());
            controller.setProduct(App.client.cart.getProducts().get(i));
        }
    }

    public void setCart(StoreSkeleton skeleton) {    //sets Cart skeleton
        this.setSkeleton(skeleton);
    }


}
