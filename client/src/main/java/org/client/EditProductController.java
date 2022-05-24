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

public class EditProductController extends Controller {

    private Product product;

    FileChooser fileChooser = new FileChooser();

    private String newImagePath = null;

    private int imageChanged = 0;

    @FXML
    private Button changeImageBtn;

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

    void setProductView(PreMadeProduct product) {
        this.product = product;
        this.nameText.setText(product.getName());
        this.mainImage.setImage(product.getImage());
        this.priceText.setText(Integer.toString(product.getPrice()));
        this.descriptionText.setText(product.getDescription());
        if (product.getPriceBeforeDiscount() != 0)
            this.priceBeforeDiscountText.setText(Integer.toString(product.getPriceBeforeDiscount()));
    }

    @FXML
    void changeImage(ActionEvent event) throws InterruptedException {
        coolButtonClick((Button) event.getTarget());
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            imageChanged++;
            newImagePath = selectedFile.getAbsolutePath();
            mainImage.setImage(new Image(newImagePath));
        }
    }

    @FXML
    void clickedSave(ActionEvent event) throws InterruptedException {
        coolButtonClick((Button) event.getTarget());
        if(alertMsg("Edit Product","change this product!" , checkProduct())) {
            saveChanges();
            this.globalSkeleton.changeCenter("EditCatalog");
        }
    }

    private boolean checkProduct() {
        if(nameText.getText().isEmpty() || priceText.getText().isEmpty() ||
                priceBeforeDiscountText.getText().isEmpty() || descriptionText.getText().isEmpty())
            return true;
        if(nameText.getText().matches ("^[a-zA-Z0-9_ ]*$")  && priceText.getText().matches("^[0-9]*$") &&
                priceBeforeDiscountText.getText().matches("^[0-9]*$"))
            return false;
        return true;
    }

    void saveChanges() {     //function creates new product and sends save command to server
        String save = "#SAVE";
        LinkedList<Object> msg = new LinkedList<Object>();  //msg has string message with all data in next nodes
        PreMadeProduct p;


        if (imageChanged > 0)
            p = new PreMadeProduct(nameText.getText(), newImagePath, Integer.parseInt(priceText.getText()),
                    descriptionText.getText(),Integer.parseInt(priceBeforeDiscountText.getText()));

        else
            p = new PreMadeProduct(nameText.getText(), product.getByteImage(), Integer.parseInt(priceText.getText()),
                    descriptionText.getText(),Integer.parseInt(priceBeforeDiscountText.getText()));

        msg.add(save);          // adds #SAVE command for server
        msg.add(product);       //adds data to msg list
        msg.add(p);             //
        App.client.setController(this);
        try {
            App.client.sendToServer(msg); //Sends a msg contains the command and the controller for the catalog.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}