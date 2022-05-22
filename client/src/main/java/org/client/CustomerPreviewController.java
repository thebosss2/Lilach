package org.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class CustomerPreviewController extends ItemController {


    @FXML
    private Text debt;

    @FXML
    private Button freezeBtn;

    @FXML
    private Text id;

    @FXML
    private Text name;

    @FXML
    private Pane pane;

    @FXML
    private Text password;

    @FXML
    private Text type;

    @FXML
    private Text username;

    @FXML
    void changeStatus(ActionEvent event) {

    }

    @FXML
    void goToCustomerView(MouseEvent event) {

    }
}
