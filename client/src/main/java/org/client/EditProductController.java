package org.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
    void clickedSave(ActionEvent event) throws InterruptedException {
        coolButtonClick((Button) event.getTarget());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save Product Confirmation");
        alert.setHeaderText("You're about to save changes!");
        alert.setContentText("Are you sure?");

        if(alert.showAndWait().get() == ButtonType.OK){
            saveChanges();
            this.globalSkeleton.changeCenter("editCatalog");
        }
    }

    private void coolButtonClick(Button button) throws InterruptedException{
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            button.setStyle("-fx-background-color: #8c73ea");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            button.setStyle("-fx-background-color: #c6acef");
        });
    }

    void saveChanges(){
        // TODO
    }

}

