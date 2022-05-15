package org.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.entities.Employee;

public class ManagerMenuController extends WorkerMenuController {

    @FXML
    private Button reportsBtn;

    @FXML
    void goToReports(ActionEvent event) throws InterruptedException {       // loads edit catalog view for worker
        coolMenuClick((Button) event.getTarget());

        //if(((Employee)App.client.user).role == CEO)
        this.getSkeleton().changeCenter("Report");
        //else
        //    this.getSkeleton().changeCenter("StoreReport");
    }

    @Override
    protected void coolMenuClick(Button button) throws InterruptedException {

        editCatalogBtn.setStyle("-fx-background-color: #9bc98c");
        reportsBtn.setStyle("-fx-background-color: #9bc98c");
        button.setStyle("-fx-background-color: #62a74d");
    }

}

