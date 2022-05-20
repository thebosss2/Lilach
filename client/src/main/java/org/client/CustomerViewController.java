package org.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class CustomerViewController {

    @FXML
    private Label balance;

    @FXML
    private DatePicker birth;

    @FXML
    private TextField email;

    @FXML
    private Label id;

    @FXML
    private TextField name;

    @FXML
    private TextField password;

    @FXML
    private Text status;

    @FXML
    private Button statusBtn;

    @FXML
    private TextField username;

    @FXML
    void changeStatus(ActionEvent event) {

    }

    @FXML
    void saveChanges(ActionEvent event) {

    }

}
