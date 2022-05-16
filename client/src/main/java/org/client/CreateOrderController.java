package org.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import org.entities.*;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
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
    private Pane TACartPane;

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
    private Pane giftCartPane;

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
    private Pane selfCartPane;

    @FXML
    private Tab selfTab;

    private LinkedList<Store> stores = new LinkedList<Store>();

    @FXML
    void initialize() throws IOException {

        //initialize all datePickers:
        displayDates(selfShippingDate, LocalDate.now(), false);
        displayDates(giftShippingDate, LocalDate.now(), false);
        displayDates(TADate, LocalDate.now(), false);

        //initialize store combobox:
        LinkedList<Object> msg = new LinkedList<Object>();
        msg.add("#PULLSTORES"); //get stores from db
        App.client.setController(this);
        try {
            App.client.sendToServer(msg); //Sends a msg contains the command and the controller for the catalog.
        } catch (IOException e) {
            e.printStackTrace();
        }

        //initialize order summery
        displaySummery();
    }

    protected void displayPreProduct(PreMadeProduct product, Pane pane) throws IOException {       //func displays an item on pane
        FXMLLoader fxmlLoader;
        //display for customer or catalog view
        fxmlLoader = new FXMLLoader(getClass().getResource("SummeryPreProduct.fxml"));
        pane.getChildren().add(fxmlLoader.load());  //Adds new product pane to the screen.
        SummeryPreProductController controller = fxmlLoader.getController();
        controller.setSummeryPreProduct(product.getName(),product.getPrice());

    }

    //TODO ORGANIZE WHEN I GET THE CUSTOMPROD
    public void displaySummery() throws IOException { //function is called to display all products from cart
        var img1 = "C:\\Users\\Tahel\\Documents\\Lilach\\server\\src\\main\\resources\\Images\\Flower0.jpg";
        PreMadeProduct p = new PreMadeProduct("Flower", img1,11, 12);
        //CustomMadeProduct c =
        CreateOrderController createOrderController = this;
        Platform.runLater(new Runnable() {      //runlater used to wait for server and client threads to finish
            @Override
            public void run() {
                for (Product product : App.client.cart.getProducts()) {
                    try {
                    if(product instanceof PreMadeProduct)
                        displayPreProduct((PreMadeProduct)product, createOrderController.selfCartPane);
                    else
                        ;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
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

    public void pullStoresToClient(LinkedList<Store> stores){
        this.stores = stores;
        for(Store s : stores)
            TAStorePicker.getItems().add(s.getName());
    }

}
