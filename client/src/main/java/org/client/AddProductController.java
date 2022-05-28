package org.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.entities.PreMadeProduct;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

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

        if(alertMsg("Add Product","add a product!" , isProductInvalid())) {
            addProduct();
            globalSkeleton.changeCenter("EditCatalog");
        }

    }

    private boolean isProductInvalid() {
        if(nameText.getText().isEmpty() || priceText.getText().isEmpty() ||
                priceBeforeDiscountText.getText().isEmpty() || descriptionText.getText().isEmpty() || imageAdded == 0)
            return true;
        if(nameText.getText().matches ("^[a-zA-Z0-9_ ]*$")  && priceText.getText().matches("^[0-9]*$") &&
                priceBeforeDiscountText.getText().matches("^[0-9]*$"))
            return false;
        return true;
    }

    private void addProduct() { //create a new product with information from worker, then save on DB
        String add = "#ADD";
        LinkedList<Object> msg = new LinkedList<Object>();  //msg has string message with all data in next nodes
        PreMadeProduct p = new PreMadeProduct(this.nameText.getText(), newImagePath, Integer.parseInt(this.priceText.getText()),
                descriptionText.getText(),Integer.parseInt(this.priceBeforeDiscountText.getText()),false);

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
