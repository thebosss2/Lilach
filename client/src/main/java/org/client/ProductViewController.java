package org.client;
import org.entities.PreMadeProduct;
import org.entities.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProductViewController extends Controller{

    private PreMadeProduct product;

    private int count = 1;

    @FXML
    private Label productName;

    @FXML
    private Label description;

    @FXML
    private Button addToCartBtn;

    @FXML
    private Button plusBtn;

    @FXML
    private Button minusBtn;

    @FXML
    private TextField orderCount;

    @FXML
    private ImageView image1;

    @FXML
    private ImageView image2;

    @FXML
    private ImageView image3;

    @FXML
    private ImageView image4;

    @FXML
    private ImageView mainImage;

    @FXML
    private Text price;

    @FXML
    private Text priceBeforeDiscount;

    @FXML
    void changeImage(MouseEvent event) {

        ImageView image_clicked = (ImageView) event.getTarget();
        Image image_change = image_clicked.getImage();
        mainImage.setImage(image_change);
    }

    public void setProductView (PreMadeProduct product) {
        this.product = product;
        this.productName.setText(product.getName());
        this.mainImage.setImage(product.getImage());
        this.image1.setImage(product.getImage());
        this.price.setText(Double.toString(product.getPrice()));
        if(product.getPriceBeforeDiscount() != 0)
            this.priceBeforeDiscount.setText(Double.toString(product.getPriceBeforeDiscount()));
    }

    @FXML
    void plusAction(ActionEvent event) throws InterruptedException {
        coolButtonClick((Button)event.getTarget());
        count++;
        orderCount.setText(Integer.toString(count));
    }

    @FXML
    void minusAction(ActionEvent event) throws InterruptedException {
        coolButtonClick((Button)event.getTarget());
        if (count > 1) {
            count--;
            orderCount.setText(Integer.toString(count));
        }
    }

    @FXML
    void addToCart(ActionEvent event) throws InterruptedException {
        coolButtonClick((Button)event.getTarget());

        // TODO
    }


}
