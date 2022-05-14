package org.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class WorkerMenuController extends Controller {
    @FXML
    protected Button editCatalogBtn;

    @FXML
    void goToEditCatalog(ActionEvent event) throws InterruptedException {       // loads edit catalog view for worker
        coolMenuClick((Button) event.getTarget());
        this.getSkeleton().changeCenter("EditCatalog");
    }

    protected void coolMenuClick(Button button) throws InterruptedException {
        editCatalogBtn.setStyle("-fx-background-color: #9bc98c");
        button.setStyle("-fx-background-color: #62a74d");
    }
}
