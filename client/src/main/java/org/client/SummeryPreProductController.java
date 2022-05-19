package org.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.entities.PreMadeProduct;

public class SummeryPreProductController extends Controller{

    @FXML
    private Label name;

    @FXML
    private Label price;


    public void setSummeryPreProduct(String name, int price) {
        this.name.setText(name);
        this.price.setText(String.valueOf(price));
    }

    public Label getName() {
        return name;
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public Label getPrice() {
        return price;
    }

    public void setPrice(Label price) {
        this.price.setText(String.valueOf(price));
    }

    public void setProduct(PreMadeProduct product) {
        name.setText(product.getName());
        price.setText(Integer.toString(product.getPrice()));
    }
}
