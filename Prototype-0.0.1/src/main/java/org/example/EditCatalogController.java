package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    
    @Override
    public void setCatalog(StoreSkeleton skeleton){

        this.setSkeleton(skeleton);
        try{
            //mainPane.getChildren().clear();
            for (Product product : products) {
                displayPreMadeProduct(product);
            }


        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
