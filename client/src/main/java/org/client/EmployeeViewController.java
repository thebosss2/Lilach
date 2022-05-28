package org.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.entities.Employee;
import org.entities.Store;

import java.util.LinkedList;

public class EmployeeViewController extends Controller {

    @FXML
    private TextField email;

    @FXML
    private TextField id;

    @FXML
    private TextField name;

    @FXML
    private TextField password;

    @FXML
    private ComboBox<String> rolePicker;

    @FXML
    private Button saveBtn;

    @FXML
    private Text status;

    @FXML
    private Button statusBtn;

    @FXML
    private ComboBox<String> storePicker;

    @FXML
    private TextField username;

    private Employee employee;

    private LinkedList<Store> stores = new LinkedList<Store>();

    //initialize
    public void setEmployee(Employee employee, LinkedList<Store> stores) {
        this.employee = employee;
        this.stores = stores;
        username.setText(employee.getUserName());
        password.setText(employee.getPassword());
        email.setText(employee.getEmail());
        name.setText(employee.getName());
        id.setText(employee.getUserID());
        storePicker.setValue(employee.getStore().getName());
        if (employee.getFrozen())
            setInactive();
        else
            setActive();

        setStores();
        setRoles();
    }

    private void setStores() {
        for (Store s : stores) {
            storePicker.getItems().add(s.getName());
        }
        storePicker.setValue(employee.getStore().getName());
    }

    private void setRoles() {
        String[] types = Employee.getAllRoles();
        for (String type : types)
            rolePicker.getItems().add(type);

        rolePicker.setValue(employee.getRoleToString());
    }

    @FXML
    void changeStatus(ActionEvent event) {
        if (this.status.getText().equals("Active")) setInactive();
        else setActive();

    }

    @FXML
    private void setInactive() {
        this.status.setText("Inactive");
        this.status.setStyle("-fx-text-inner-color: red;");
    }

    @FXML
    private void setActive() {
        this.status.setText("Active");
        this.status.setStyle("-fx-text-inner-color: green;");
    }

    @FXML
    void clickedSaveEmployee(ActionEvent event) {
        if (alertMsg("Save User", "save an employee's account", isEmployeeInvalid())) {
            saveChanges(); //if the fields are all valid- save the order
            App.client.getSkeleton().changeCenter("ManageAccounts"); //and head back to catalog
        }
    }

    private boolean isEmployeeInvalid() {
        return !emailValid() ||
                this.username.getText().isEmpty() ||
                this.password.getText().isEmpty() ||
                this.name.getText().isEmpty() ||
                this.id.getText().isEmpty() ||
                !id.getText().matches("^[0-9]*$") ||
                id.getText().length() != 9 ||
                !name.getText().matches("^[a-zA-Z_ ]*$") ||
                !username.getText().matches("^[0-9a-zA-Z_]*$") ||
                !password.getText().matches("^[0-9a-zA-Z_]*$");
    }

    private boolean emailValid() {
        String email = this.email.getText().toString();
        String compMail = email;
        int countAt = compMail.length() - compMail.replace("@", "").length();
        return countAt == 1 && email.indexOf("@") != 0 && !this.email.getText().isEmpty();
    }

    @FXML
    void saveChanges() {
        employee.setRole(employee.getStringToRole(this.rolePicker.getValue()));
        for (Store s : stores) {
            if (s.getName().equals(storePicker.getValue()))
                employee.setStore(s);
        }
        employee.setUserName(this.username.getText());
        employee.setPassword(this.password.getText());
        employee.setEmail(this.email.getText());
        employee.setName(this.name.getText());
        employee.setUserID(this.id.getText());
        employee.setFrozen(!this.status.getText().equals("Active"));

        //todo save to db
    }

}