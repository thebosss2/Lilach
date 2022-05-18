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

    @FXML
    private Label type;

    public void setSummeryCustomProduct(CustomMadeProduct product) {
        this.price.setText(String.valueOf(product.getPrice()));
        switch(product.getItemType()) {
            case FLOWER_ARRANGEMENT:
                this.type.setText("Custom flower arrangement:");
                break;
            case BLOOMING_POT:
                this.type.setText("Custom blooming pot:");
                break;
            case BRIDES_BOUQUET:
                this.type.setText("Custom bride bouquet:");
                break;
            case BOUQUET:
                this.type.setText("Custom bouquet:");
        }
        for(int i=0; i < (product.getProducts()).size(); i++){
            this.description.setText(this.description.getText() + product.getProducts().get(i).getName() + ", ");
        }
    }

    public Text getDescription() {
        return description;
    }

    public Text getPrice() {
        return price;
    }
}
