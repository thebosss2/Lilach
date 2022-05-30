package org.client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import org.entities.*;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

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
            msg.add(getPickedDate(fromDate));
            msg.add(getPickedDate(fromDate));
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

    public void pullData(LinkedList<Order> orders, LinkedList<Complaint> complaints) {
        Platform.runLater(()-> {
            showIncome(orders);
            showOrders(orders);
            showChart(complaints);
        });
    }

    private Date getPickedDate(DatePicker dp) { //get the picked localDate and convert it to Date
        Instant instant = Instant.from(dp.getValue().atStartOfDay(ZoneId.systemDefault())); //convert LocalDate to Date
        Date pickedDate = Date.from(instant);
        return pickedDate;
    }


    private void showOrders(LinkedList<Order> orders) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        Map<String, Integer> productMap = getMap(orders);
        for (Map.Entry<String, Integer> entry : productMap.entrySet())
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));

        this.ordersChart.setData(pieChartData);
    }

    private Map<String,Integer> getMap(LinkedList<Order> orders) {
        Map<String, Integer> map = new HashMap<String, Integer>();

        for(Product product : Client.products)
            map.put(((PreMadeProduct) product).getName(), 0);

        for(Order order : orders) {
            for(PreMadeProduct product : order.getPreMadeProducts())
                map.put(product.getName(), map.get(product.getName()) + product.getAmount());

            for(CustomMadeProduct customProduct : order.getCustomMadeProducts())
                for(PreMadeProduct baseProduct : customProduct.getProducts())
                    map.put(baseProduct.getName(), map.get(baseProduct.getName()) + baseProduct.getAmount());
        }
        return map;
    }

    private void showIncome(LinkedList<Order> orders) {
        int totalPrice = 0;

        for(Order order : orders)
            totalPrice += order.getPrice();

        incomeReport.setText("Income Report:\n" +
                "Total income in chosen time interval: " + totalPrice + "\n" +
                "Total orders in chosen time interval: " + orders.size() + "\n" +
                "Average income pair day: \n" +
                "toDate.getValue() = " + toDate.getValue());
    }

    private void showChart(LinkedList<Complaint> complaints) {

    }

}
