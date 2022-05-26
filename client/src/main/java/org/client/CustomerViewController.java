package org.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.entities.Customer;
import org.entities.Store;

import java.util.LinkedList;

public class CustomerViewController extends Controller{

    @FXML
    private TextField balance;

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
    private ComboBox<String> storePicker;

    @FXML
    private ComboBox<String> typePicker;

    @FXML
    private TextField username;

    private Customer customer;

    private LinkedList<Store> stores = new LinkedList<Store>();
    
    @FXML
    void changeStatus(ActionEvent event) {
        if(this.status.getText().equals("Active")) {
            this.status.setText("Inactive");
            this.status.setStyle("-fx-text-inner-color: red;");
        }
        else {
            this.status.setText("Active");
            this.status.setStyle("-fx-text-inner-color: green;");
        }
    }

    @FXML
    void saveChanges(ActionEvent event) {

    }

    public void setCustomer(Customer customer, LinkedList<Store> stores) {
        this.customer = customer;
        this.stores = stores;
        username.setText(customer.getUserName());
        password.setText(customer.getPassword());
        email.setText(customer.getEmail());
        name.setText(customer.getName());
        id.setText(customer.getID());
        balance.setText(Integer.toString(customer.getBalance()));

        //TODO status for customer
        //status.setText(customer.getStatus());

        setStores();
        setTypes();
    }

    private void setStores() {
        for (Store s : stores)
            if(!s.getName().equals("Chain"))
                storePicker.getItems().add(s.getName());

        storePicker.setValue(customer.getStore().getName());
    }

    private void setTypes() {
        String[] types = Customer.getAllTypes();
        for (String type : types)
            typePicker.getItems().add(type);

        typePicker.setValue(customer.getTypeString());
    }
}

