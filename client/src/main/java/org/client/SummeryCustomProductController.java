package org.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import org.entities.CustomMadeProduct;

public class SummeryCustomProductController extends Controller{

    @FXML
    private Text description;

    @FXML
    private Text price;


    public void setSummeryCustomProduct(CustomMadeProduct product) {
        for(int i=0; i < (product.getProducts()).size(); i++){
            this.description.setText(this.description.getText() + product.getProducts().get(i).getName() + ", ");
            this.price.setText(String.valueOf(Integer.parseInt(String.valueOf(price))+product.getProducts().get(i).getPrice()));
        }
    }

    public Text getDescription() {
        return description;
    }

    public Text getPrice() {
        return price;
    }
}
