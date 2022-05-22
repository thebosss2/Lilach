package org.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class CustomerViewController {

    @FXML
    private TextField balance;

    @FXML
    private DatePicker birth;

    @FXML
    private TextField email;

    @FXML
    private TextField id;

    @FXML
    private TextField name;

    @FXML
    private TextField password;

    @FXML
    private Button saveBtn;

    @FXML
    private Text status;

    @FXML
    private Button statusBtn;

    @FXML
    private ComboBox<String> store;

    @FXML
    private ComboBox<String> userType;

    @FXML
    private TextField username;

    @FXML
    void changeStatus(ActionEvent event) {
        if(this.status.getText().equals("Active")) {
            this.status.setText("Inactive");
            this.status.setStyle("-fx-text-inner-color: red;");
        }
        else
            this.status.setText("Active");
        this.status.setStyle("-fx-text-inner-color: green;");
    }

    @FXML
    void saveChanges(ActionEvent event) {

    }

}

