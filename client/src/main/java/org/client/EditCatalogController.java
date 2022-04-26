package org.client;

import org.entities.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditCatalogController extends CatalogController {


    @FXML
    private Button addProduct;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="mainPane"
    private FlowPane mainPane; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert mainPane != null : "fx:id=\"mainPane\" was not injected: check your FXML file 'Catalog.fxml'.";
        try {
            ArrayList<Object> msg = new ArrayList<Object>();
            msg.add("#PULLCATALOG");
            App.client.setController(this);
            App.client.sendToServer(msg); //Sends a msg contains the command and the controller for the catalog.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setCatalog(StoreSkeleton skeleton) {

        this.setSkeleton(skeleton);
        try {
            displayAddItem();
            //mainPane.getChildren().clear();
            for (Product product : products)
                displayProduct(product);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void displayAddItem() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addItem.fxml"));
        mainPane.getChildren().add(fxmlLoader.load());  //Adds new product pane to the screen.
        AddItemController controller = fxmlLoader.getController();
        controller.setSkeleton(this.getSkeleton());
    }

}