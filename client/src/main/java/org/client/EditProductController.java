package org.client;
import org.entities.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

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
        // TODO
    }

}

