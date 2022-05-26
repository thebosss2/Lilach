package org.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AdminMenuController extends Controller {

    @FXML
    private Button editCatalogBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button manageAccountsBtn;

    @FXML
    void goToEditCatalog(ActionEvent event) {

    }

    @FXML
    void goToManageAccounts(ActionEvent event) {
        this.getSkeleton().changeCenter("ManageAccounts");
    }

    @FXML
    void logOut(ActionEvent event) {

    }
}
