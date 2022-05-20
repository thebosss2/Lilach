package org.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class EmployeeViewController {

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
    private ComboBox<?> role;

    @FXML
    private Button saveBtn;

    @FXML
    private Text status;

    @FXML
    private Button statusBtn;

    @FXML
    private ComboBox<?> store;

    @FXML
    private TextField username;

    @FXML
    void changeStatus(ActionEvent event) {

    }

    @FXML
    void saveChanges(ActionEvent event) {

    }

}