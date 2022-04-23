package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class WorkerMenuController extends GuestMenuController{
    @FXML
    private Button editCatalogBtn;

    @FXML
    void goToEditCatalog(ActionEvent event) throws InterruptedException {
        coolButtonClick((Button)event.getTarget());
        this.getSkeleton().changeCenter("editCatalog");
    }
}
