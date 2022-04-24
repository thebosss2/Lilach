package org.server;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class AddItemController extends ItemController{

    public void goToAddItemPage (MouseEvent event) throws InterruptedException {
        clickOnProductEffect(event);
        //todo!!!!!!
//        ProductViewController productView = null;
//        Controller controller = null;
//        StoreSkeleton skeleton = this.getSkeleton();
//        controller = skeleton.changeCenter("productview");
//        productView = (ProductViewController) controller;
//        productView.setProduct(this.product);
    }
}
