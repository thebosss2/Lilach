package org.client;
import javafx.scene.image.Image;
import org.entities.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import org.server.App.*;

import java.io.IOException;
import java.util.LinkedList;

public class EditProductController extends Controller{

    private Product product;

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

    void setProductView(Product product){
        this.product = product;
        this.nameText.setText(product.getName());
        this.mainImage.setImage(product.getImage());
        this.priceText.setText(Double.toString(product.getPrice()));
        if(product.getPriceBeforeDiscount() != 0)
            this.priceBeforeDiscountText.setText(Double.toString(product.getPriceBeforeDiscount()));
    }

    @FXML
    void clickedSave(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save Product Confirmation");
        alert.setHeaderText("You're about to save changes!");
        alert.setContentText("Are you sure?");

        if(alert.showAndWait().get() == ButtonType.OK){
            saveChanges();
            this.globalSkeleton.changeCenter("editCatalog");
        }
    }

    void saveChanges(){
        String save="#SAVE";
        LinkedList<Object> msg = new LinkedList<Object>();

        Product p = new Product(this.nameText.getText(), this.product.getByteImage(),  Double.parseDouble(this.priceText.getText()), Double.parseDouble(this.priceBeforeDiscountText.getText()));
        //need to update image

        msg.add(save);
        msg.add(product);
        msg.add(p);
        App.client.setController(this);
        try {
            App.client.sendToServer(msg); //Sends a msg contains the command and the controller for the catalog.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

