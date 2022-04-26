package org.client;

import org.entities.Product;
import org.entities.Catalog;
import org.client.Client;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Sample Skeleton for 'Catalog.fxml' Controller Class
 */

    import java.io.IOException;
    import java.net.URL;
    import java.util.*;

    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.image.Image;
    import javafx.scene.layout.FlowPane;


public class CatalogController extends Controller {

    //All products in the catalog.
    protected static List<Product> products = Catalog.getProducts();


    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="mainPane"
    protected FlowPane mainPane; // Value injected by FXMLLoader

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert mainPane != null : "fx:id=\"mainPane\" was not injected: check your FXML file 'Catalog.fxml'.";
        try {
            ArrayList<Object> msg = new ArrayList<Object>();
            msg.add("#PULLCATALOG");
           // msg.add(this);
            App.client.setController(this);
            App.client.sendToServer(msg); //Sends a msg contains the command and the controller for the catalog.
            //App.client.wait();
            System.out.println(App.client.toString());
            System.out.println(App.client.getInetAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*pullProductsToClient("#PULLCATALOG");*/
    }

    public void setCatalog(StoreSkeleton skeleton) {

        this.setSkeleton(skeleton);
        try {
            //mainPane.getChildren().clear();
            for (Product product : products) {
                displayProduct(product);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param product
     * @throws IOException Function adding instance of pre-made product to the screen.
     *                     Note to self: VERY IMPORTANT to load before using the "getController" method (else you'll get null).
     */
    protected void displayProduct(Product product) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("product.fxml"));
        mainPane.getChildren().add(fxmlLoader.load());  //Adds new product pane to the screen.
        ProductController controller = fxmlLoader.getController();
        controller.setSkeleton(this.getSkeleton());
        controller.setProduct(product, this);
    }


    public void pullProductsToClient(List<Product> data) throws IOException {
        /*products = data;
        for (Product product : products) {
            this.displayProduct(product);
            System.out.println("Hello there");
        }
        System.out.println("Hello there");
*/
        this.displayProduct(new Product());


/*        for(int i=0;i<((LinkedList<?>) msg).size();i++){
            Product p=new Product((Product) ((LinkedList<Object>) msg).get(i));
            products.add(p);
        }*/

    }
}
