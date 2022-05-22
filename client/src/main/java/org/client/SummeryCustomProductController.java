package org.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import org.entities.CustomMadeProduct;

public class SummeryCustomProductController extends Controller{

    @FXML
    private Label count;

    @FXML
    private Text description;

    @FXML
    private Label type;

    @FXML
    private Label price;

    @FXML
    private Label totalPrice;


    public void setSummeryCustomProduct(CustomMadeProduct product) {
        this.price.setText(String.valueOf(product.getPrice()));
        this.count.setText(String.valueOf(product.getAmount()));
        this.totalPrice.setText(String.valueOf(product.getAmount() * product.getPrice()));
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
        this.description.setText(this.description.getText().substring(0,this.description.getText().length()-2));
    }

    public Text getDescription() {
        return description;
    }

    public Label getPrice() {
        return price;
    }

    public void setProduct(CustomMadeProduct product) {
        price.setText(Integer.toString(product.getPrice()));

//        if(product.getItemType()== CustomMadeProduct.ItemType.FLOWER_ARRANGEMENT)
        type.setText(product.getItemType().toString());
    }
}
