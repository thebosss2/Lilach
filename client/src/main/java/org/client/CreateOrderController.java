package org.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.entities.Store;

import java.io.IOException;
import java.util.LinkedList;

public class CreateOrderController extends Controller{

    @FXML
    private DatePicker TADateText;

    @FXML
    private TextArea TAGreetingText;

    @FXML
    private ComboBox<String> TAHourPicker;

    @FXML
    private ComboBox<Store> TAStorePicker;

    @FXML
    private Button TASubmitBtn;

    @FXML
    private Tab TATab;

    @FXML
    private TextField giftEmailText;

    @FXML
    private TextArea giftGreetingText;

    @FXML
    private ComboBox<String> giftHourPicker;

    @FXML
    private TextField giftPhoneText;

    @FXML
    private TextField giftReceiverAddressText;

    @FXML
    private TextField giftReceiverNameText;

    @FXML
    private TextField giftReceiverPhoneText;

    @FXML
    private DatePicker giftShippingDateText;

    @FXML
    private Button giftSubmitBtn;

    @FXML
    private Tab giftTab;

    @FXML
    private TextField selfAddressText;

    @FXML
    private TextField selfEmailText;

    @FXML
    private TextArea selfGreetingText;

    @FXML
    private ComboBox<String> selfHourPicker;

    @FXML
    private TextField selfPhoneText;

    @FXML
    private DatePicker selfShippingDateText;

    @FXML
    private Button selfSubmitBtn;

    @FXML
    private Tab selfTab;

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        LinkedList<Object> msg = new LinkedList<Object>();
        msg.add("#PULLSTORES");
        App.client.setController(this);
        try {
            App.client.sendToServer(msg); //Sends a msg contains the command and the controller for the catalog.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void TASubmitOrder(ActionEvent event) {

    }

    @FXML
    void giftSubmitOrder(ActionEvent event) {

    }

    @FXML
    void selfSubmitOrder(ActionEvent event) {

    }

    @FXML
    void tabClicked(ActionEvent event) {

    }

}
