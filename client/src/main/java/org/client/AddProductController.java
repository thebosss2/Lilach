package org.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.entities.PreMadeProduct;
import org.entities.Product;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AddProductController extends Controller {

    int imageAdded = 0;

    FileChooser fileChooser = new FileChooser();

    String newImagePath = null;

    @FXML
    private Button addImageBtn;

    @FXML
    private TextArea descriptionText;

    @FXML
    private ImageView mainImage;

    @FXML
    private TextField nameText;

    @FXML
    private TextField priceBeforeDiscountText;

    @FXML
    private TextField priceText;

    @FXML
    private Button saveBtn;

    @FXML
    void addImage(ActionEvent event) throws InterruptedException {
        coolButtonClick((Button) event.getTarget());
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            imageAdded++;
            newImagePath = selectedFile.getAbsolutePath();
            mainImage.setImage(new Image(newImagePath));
        }
    }

    @FXML
    void clickedAdd(ActionEvent event) throws InterruptedException {
        coolButtonClick((Button) event.getTarget());
        Alert alert;

        if (nameText.getText().equals("") || priceText.getText().equals("")
                || priceBeforeDiscountText.getText().equals("") || descriptionText.getText().equals("")
                || imageAdded == 0) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Missing Values");
            alert.setHeaderText("One or more of the fields are missing");
            alert.showAndWait();
        } else {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Add Product Confirmation");
            alert.setHeaderText("You're about to add a product!");
            alert.setContentText("Are you sure?");

            if (alert.showAndWait().get() == ButtonType.OK) {
                addProduct();
                this.globalSkeleton.changeCenter("EditCatalog");
            }
        }
    }

    private void addProduct() { //create a new product with information from worker, then save on DB
        String add = "#ADD";
        LinkedList<Object> msg = new LinkedList<Object>();  //msg has string message with all data in next nodes
        PreMadeProduct p = new PreMadeProduct(this.nameText.getText(), newImagePath, Integer.parseInt(this.priceText.getText()),
                Integer.parseInt(this.priceBeforeDiscountText.getText()));

        msg.add(add);          // adds #ADD command for server
        msg.add(p);             //adds data to msg list
        App.client.setController(this);
        try {
            App.client.sendToServer(msg); //Sends a msg contains the command and the controller for the catalog.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
