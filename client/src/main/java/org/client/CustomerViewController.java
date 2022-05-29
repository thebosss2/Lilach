package org.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import org.entities.Customer;
import org.entities.Employee;
import org.entities.Store;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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


    public void setCustomer(Customer customer, LinkedList<Store> stores) {
        this.customer = customer;
        this.stores = stores;
        username.setText(customer.getUserName());
        password.setText(customer.getPassword());
        email.setText(customer.getEmail());
        name.setText(customer.getName());
        id.setText(customer.getUserID());
        balance.setText(Integer.toString(customer.getBalance()));
        storePicker.setValue(customer.getStore().getName());
        if (customer.getFrozen()) setInactive();
        else setActive();
        setStores();
        setTypes();
    }


    @FXML
    private void setInactive() {
        this.status.setText("Inactive");
        this.status.setFill(Paint.valueOf("RED"));
    }

    @FXML
    private void setActive() {
        this.status.setText("Active");
        this.status.setFill(Paint.valueOf("GREEN"));
    }

    @FXML
    void changeStatus(ActionEvent event) {
        if (this.status.getText().equals("Active")) setInactive();
        else setActive();
    }

    @FXML
    void clickedSaveCustomer(ActionEvent event) {
        if (alertMsg("Save User", "save an customer's account", isCustomerInvalid())) {
            saveChanges(); //if the fields are all valid- save the order
            App.client.getSkeleton().changeCenter("ManageAccounts"); //and head back to catalog
        }
    }

    @FXML
    void saveChanges() {
        Store store = new Store();
        for (Store s : stores) {
            if (s.getName().equals(storePicker.getValue()))
                store = s;
        }
        Customer cus = new Customer(this.id.getText(), this.name.getText(), this.username.getText(),
                this.password.getText(), this.email.getText(), customer.getPhoneNum(), customer.getCreditCard(),
                customer.getStringToType(this.typePicker.getValue()), store, !this.status.getText().equals("Active"));

        List<Object> msg = new LinkedList<Object>();
        msg.add("#SAVECUSTOMER");          // adds #SAVE command for server
        msg.add(customer);       //adds data to msg list
        msg.add(cus);             //
        App.client.setController(this);
        try {
            App.client.sendToServer(msg); //Sends a msg contains the command and the controller for the catalog.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean isCustomerInvalid() {
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
        String email = this.email.getText();
        String compMail = email;
        int countAt = compMail.length() - compMail.replace("@", "").length();
        return countAt == 1 && email.indexOf("@") != 0 && !this.email.getText().isEmpty();
    }

    private void setStores() {
        for (Store s : stores) {
            storePicker.getItems().add(s.getName());
        }
        storePicker.setValue(customer.getStore().getName());
    }



    private void setTypes() {
        String[] types = Customer.getAllTypes();
        for (String type : types)
            typePicker.getItems().add(type);

        typePicker.setValue(customer.getTypeToString());
    }
}

