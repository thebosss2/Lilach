package org.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import org.entities.Complaint;
import org.entities.Order;
import org.entities.User;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;

public class ReportController extends Controller{

    @FXML
    private StackedBarChart<?, ?> complaintChart;

    @FXML
    private NumberAxis complaintsAxis1;

    @FXML
    private CategoryAxis daysAxis1;

    @FXML
    private DatePicker fromDate;

    @FXML
    private Label incomeReport;

    @FXML
    private Button makeReportBtn;

    @FXML
    private PieChart ordersChart;

    @FXML
    private DatePicker toDate;

    @FXML
    void initialize() {
        displayDates(fromDate, LocalDate.now(), true);
    }

    @FXML
    void makeReport(ActionEvent event) throws InterruptedException {
        coolButtonClick((Button) event.getTarget());
        if(isInvalid())
            sendAlert("Must pick time interval to make a report!", "Date Missing", Alert.AlertType.ERROR);

        else{   // send request to server to pull data for report, with store and date interval
            LinkedList<Object> msg = new LinkedList<Object>();
            msg.add("#PULL_MANAGER_REPORT"); //get stores from db
            msg.add( ((User)App.client.user).getStore() );
            msg.add(fromDate.getValue());
            msg.add(toDate.getValue());
            App.client.setController(this);
            try {
                App.client.sendToServer(msg); //Sends a msg contains the command and the current controller
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void changedFromDate (ActionEvent event) throws InterruptedException {
        toDate.setDisable(false);
        displayDates(toDate, fromDate.getValue(), LocalDate.now());
    }

    public void changedToDate (ActionEvent event) throws InterruptedException {
        toDate.setDisable(false);
        displayDates(fromDate, toDate.getValue(), true);
    }

    public boolean isInvalid(){
        return toDate.isDisabled() || toDate.getValue() == null;
    }

    public static void pullData(LinkedList<Order> orders, LinkedList<Complaint> complaints) {
        System.out.println("pulling data...");
        for(Order order : orders) {
            System.out.println("order id:" + order.getId() + ", order store: " + order.getStore().getName() +
                    ", order Date: " + order.getDeliveryDate().toString());
        }

        for(Complaint complaint : complaints) {
            System.out.println("complaint id:" + complaint.getId() + ", order store: " + complaint.getStore().getName() +
                    ", order Date: " + complaint.getDate().toString());
        }

    }

    private void showOrders() {

    }

    private void showIncome() {

    }

    private void showChart() {

    }

}
