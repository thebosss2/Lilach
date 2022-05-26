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

public class EmployeeViewController extends Controller{

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

    public void setEmployee(Employee employee, LinkedList<Store> stores) {
        this.employee = employee;
        this.stores = stores;
        username.setText(employee.getUserName());
        password.setText(employee.getPassword());
        email.setText(employee.getEmail());
        name.setText(employee.getName());
        id.setText(employee.getID());

        //TODO getStore()
        //storePicker.setValue(employee.getSore());

        //TODO status for customer
        //status.setText(customer.getStatus());

        setStores();
        setRoles();

    }

    private void setStores() {
        for (Store s : stores) {
            if (!s.getName().equals("Chain"))
                storePicker.getItems().add(s.getName());
        }

        //TODO getStore()
        //storePicker.setValue(employee.getStoreString());
    }

    private void setRoles() {
        String[] types = Employee.getAllRoles();
        for (String type : types)
            rolePicker.getItems().add(type);

        rolePicker.setValue(employee.getRoleString());
    }
}