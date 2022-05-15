package org.client;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.entities.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;

public class CreateOrderController extends Controller{

    @FXML
    private DatePicker TADate;

    @FXML
    private TextArea TAGreetingText;

    @FXML
    private ComboBox<String> TAHourPicker;

    @FXML
    private ComboBox<String> TAStorePicker;

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
    private DatePicker giftShippingDate;

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
    private DatePicker selfShippingDate;

    @FXML
    private Button selfSubmitBtn;

    @FXML
    private Tab selfTab;

    private LinkedList<Store> stores = new LinkedList<Store>();

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        displayDates(selfShippingDate, LocalDate.now(), false);
        displayDates(giftShippingDate, LocalDate.now(), false);
        displayDates(TADate, LocalDate.now(), false);
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
        //Order order = new Order(App.client.cart)
    }

    @FXML
    void giftSubmitOrder(ActionEvent event) {

    }

    @FXML
    void selfSubmitOrder(ActionEvent event) {

    }

    @FXML
    void tabClicked(Event event) {

    }

    public void pullStoresToClient(LinkedList<Store> stores){
        this.stores = stores;
        for(Store s : stores)
            TAStorePicker.getItems().add(s.getName());
    }

}
