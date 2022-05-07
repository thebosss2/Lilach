package org.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class WorkerMenuController extends GuestMenuController {
    @FXML
    private Button editCatalogBtn;

    @FXML
    void goToEditCatalog(ActionEvent event) throws InterruptedException {       // loads edit catalog view for worker
        coolButtonClick((Button) event.getTarget());
        this.getSkeleton().changeCenter("EditCatalog");
    }

    @Override
    protected void coolButtonClick(Button button) throws InterruptedException {
        cartBtn.setStyle("-fx-background-color: #9bc98c");
        catalogBtn.setStyle("-fx-background-color: #9bc98c");
        editCatalogBtn.setStyle("-fx-background-color: #9bc98c");
        button.setStyle("-fx-background-color: #62a74d");
    }
}
