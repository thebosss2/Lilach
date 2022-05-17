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
/*
    public void setSummeryCustomProduct(CustomMadeProduct product) {
        switch(product.getItemType()) {
            case FLOWER_ARRANGEMENT:
                this.type.setText();
                break;
            case BLOOMING_POT:
                // code block
                break;
            case BRIDES_BOUQUET:
                // code block
                break;
            default: //BOUQUET
                // code block
        }
        for(int i=0; i < (product.getProducts()).size(); i++){
            this.description.setText(this.description.getText() + product.getProducts().get(i).getName() + ", ");
            this.price.setText(String.valueOf(product.getPrice()));
        }
    }
*/
    public Text getDescription() {
        return description;
    }

    public Text getPrice() {
        return price;
    }
}
