package org.client;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.entities.Order;

public class OrderSummaryController extends Controller{
    private Order order;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button cancelOrder;

    @FXML
    private Text price;

    @FXML
    void cancel(ActionEvent event) {

        LinkedList<Object> msg = new LinkedList<Object>();
        msg.add("#DELETEORDER"); //get stores from db
        msg.add(order.getId());
        App.client.setController(this);//TODO maybe remove
        try {
            App.client.sendToServer(msg); //Sends a msg contains the command and the current controller
        } catch (IOException e) {
            e.printStackTrace();
        }

        App.client.storeSkeleton.changeCenter("OrderSummery");
    }

    @FXML
    void initialize() {
        assert cancelOrder != null : "fx:id=\"cancelOrder\" was not injected: check your FXML file 'OrderSummary.fxml'.";
        assert price != null : "fx:id=\"price\" was not injected: check your FXML file 'OrderSummary.fxml'.";

    }

}
