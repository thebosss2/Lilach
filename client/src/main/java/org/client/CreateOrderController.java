package org.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import org.entities.*;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
    private FlowPane TACartPane;

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
    private FlowPane giftCartPane;

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
    private FlowPane selfCartPane;

    @FXML
    private Tab selfTab;

    @FXML
    private Label TADiscountLabel;

    @FXML
    private Label giftDiscountLabel;

    @FXML
    private Label selfDiscountLabel;

    @FXML
    private Label TAFinalPriceLabel;

    @FXML
    private Label giftFinalPriceLabel;

    @FXML
    private Label selfFinalPriceLabel;

    @FXML
    private Label TAPriceBeforeLabel;

    @FXML
    private Label giftPriceBeforeLabel;

    @FXML
    private Label selfPriceBeforeLabel;

    private LinkedList<Store> stores = new LinkedList<Store>();

    @FXML
    void initialize() throws IOException {

        //initialize all datePickers:
        displayDates(selfShippingDate, LocalDate.now(), false);
        displayDates(giftShippingDate, LocalDate.now(), false);
        displayDates(TADate, LocalDate.now(), false);

        //initialize hour combobox:
        for(int i=0; i< Client.hourList.length; i++) {
            TAHourPicker.getItems().add(String.valueOf(Client.hourList[i]) +":00");
            selfHourPicker.getItems().add(String.valueOf(Client.hourList[i]) +":00");
            giftHourPicker.getItems().add(String.valueOf(Client.hourList[i]) +":00");
        }

        // disable hour pickers until a date is chosen
        TAHourPicker.setDisable(true);
        selfHourPicker.setDisable(true);
        giftHourPicker.setDisable(true);

        //initialize store combobox
        getStores();

        //initialize order summery
        displaySummery();

        //initialize discount and final price
        setPrices();
    }

    private void getStores() {
        if(((Customer)(App.client.user)).getAccountType() == Customer.AccountType.STORE)  //if there is certain store for this costumer
            TAStorePicker.setDisable(true); //disable the combobox
        else{ //get stores for the combobox from db
            LinkedList<Object> msg = new LinkedList<Object>();
            msg.add("#PULLSTORES"); //get stores from db
            App.client.setController(this);
            try {
                App.client.sendToServer(msg); //Sends a msg contains the command and the current controller
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void pullStoresToClient(LinkedList<Store> stores){ //when server send stores
        this.stores = stores;
        for(Store s : stores)
            TAStorePicker.getItems().add(s.getName());
    }

    //TODO ADD CUSTOM PRODUCT
    public void displaySummery() throws IOException { //function is called to display all products from cart
        CreateOrderController createOrderController = this;
        for (Product product : App.client.cart.getProducts()) {
            try {
                if(product instanceof PreMadeProduct) {
                    displayPreProduct((PreMadeProduct) product, createOrderController.selfCartPane);
                    displayPreProduct((PreMadeProduct) product, createOrderController.giftCartPane);
                    displayPreProduct((PreMadeProduct) product, createOrderController.TACartPane);
                }
                else
                    ;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    protected void displayPreProduct(PreMadeProduct product, FlowPane pane) throws IOException {       //func displays an item on pane
        FXMLLoader fxmlLoader;
        //display for customer or catalog view
        fxmlLoader = new FXMLLoader(getClass().getResource("SummeryPreProduct.fxml"));
        pane.getChildren().add(fxmlLoader.load());  //Adds new product pane to the screen.
        SummeryPreProductController controller = fxmlLoader.getController();
        controller.setSummeryPreProduct(product.getName(),product.getPrice());

    }

    private void setPrices() {
        TAPriceBeforeLabel.setText(String.valueOf(App.client.cart.getTotalCost()));
        giftPriceBeforeLabel.setText(String.valueOf(App.client.cart.getTotalCost()));
        selfPriceBeforeLabel.setText(String.valueOf(App.client.cart.getTotalCost()));

        if(((Customer)(App.client.user)).getAccountType() == Customer.AccountType.MEMBERSHIP && App.client.cart.getTotalCost()>=50){
            //if the user is member and have order larger than 50 nis- we give 10% discount
            TADiscountLabel.setText("10%");
            giftDiscountLabel.setText("10%");
            selfDiscountLabel.setText("10%");
            TAFinalPriceLabel.setText(String.valueOf((int)(0.9 * App.client.cart.getTotalCost())));
            giftFinalPriceLabel.setText(String.valueOf((int)(0.9 * App.client.cart.getTotalCost())+20)); //also added shipping fee
            selfFinalPriceLabel.setText(String.valueOf((int)(0.9 * App.client.cart.getTotalCost())+20));
        }
        else { //no discount for no members
            TAFinalPriceLabel.setText(String.valueOf(App.client.cart.getTotalCost()));
            giftFinalPriceLabel.setText(String.valueOf(App.client.cart.getTotalCost()+20)); // added shipping fee
            selfFinalPriceLabel.setText(String.valueOf(App.client.cart.getTotalCost()+20));
        }
    }

    @FXML
    Store getSelectedStore() {
        Store pickedStore = new Store();
        for(Store s: stores) {
            if (s.getName().equals(TAStorePicker.getValue()))
                pickedStore = s;
        }
        return pickedStore;
    }



    private void enableHours(ComboBox<String> HourPicker, DatePicker datePicker) {
        HourPicker.setDisable(false); //enable the combobox
        if (datePicker.getValue().atStartOfDay().isEqual(LocalDate.now().atStartOfDay())){ //if the required date is today
            LocalTime now = LocalTime.now();
            HourPicker.getItems().clear();
            for(int i = 0; i< Client.hourList.length; i++)  //the combobox will contain all hours after current hour
                if(now.getHour() < Client.hourList[i])
                    HourPicker.getItems().add(String.valueOf(Client.hourList[i]) +":00");
        }
        else //if the required date is not today
            for(int i=0; i< Client.hourList.length; i++) //the combobox will contain all hours
                HourPicker.getItems().add(String.valueOf(Client.hourList[i]) + ":00");
    }

    @FXML
    void selfEnableHours(ActionEvent event) {
        enableHours(selfHourPicker, selfShippingDate);
    }

    @FXML
    void TAEnableHours(ActionEvent event) {
        enableHours(TAHourPicker, TADate);
    }

    @FXML
    void giftEnableHours(ActionEvent event) {
        enableHours(giftHourPicker, giftShippingDate);
    }

    @FXML
    void TASubmitOrder(ActionEvent event) {

        LinkedList<PreMadeProduct> preList = new LinkedList<>();
        LinkedList<CustomMadeProduct> customList = new LinkedList<>();

        for (Product product : App.client.cart.getProducts()) {//extract products list to PreMadeProduct list and CustomMadeProduct list
            if (product instanceof PreMadeProduct)
                preList.add((PreMadeProduct) product);
            else
                customList.add((CustomMadeProduct) product);
        }

        Instant instant = Instant.from(TADate.getValue().atStartOfDay(ZoneId.systemDefault())); //convert LocalDate to Date
        Date pickedDate = Date.from(instant);

        Order order = new Order(preList,customList, (Customer) App.client.user, App.client.cart.getTotalCost(), getSelectedStore(), pickedDate, TAHourPicker.getValue());
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


}
