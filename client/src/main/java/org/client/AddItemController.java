package org.client;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import org.entities.Product;

public class AddItemController extends ItemController{

    public void goToAddItemPage (MouseEvent event) throws InterruptedException {
        clickOnProductEffect(event);
        Controller controller = null;
        controller = this.getSkeleton().changeCenter("AddProduct");
    }
}
