package org.client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.entities.Complaint;
import org.entities.Order;
import org.entities.User;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Map;

public class ReportController extends AbstractReport {

    @FXML
    private StackedBarChart<String, Number> complaintChart;

    @FXML
    private NumberAxis complaintsAxis;

    @FXML
    private CategoryAxis daysAxis;

    @FXML
    private DatePicker fromDate;

    @FXML
    private Label incomeReport;

    @FXML
    private Label ordersReport;

    @FXML
    private Label salesNum;

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

    /**
     * makeReport function activates if pressing the make report button, first it makes
     * sure that a valid time interval and store has been inserted, and then sends to the server
     * a request for all orders and complaints that are relevant for the request.
     * (sends to the server the time interval and store)
     * @param event irrelevant
     * @throws InterruptedException
     */

    @FXML
    void makeReport(ActionEvent event) throws InterruptedException {
        coolButtonClick((Button) event.getTarget());
        salesNum.setText("");
        if (isInvalid())
            sendAlert("Must pick time interval to make a report!", "Date Missing", Alert.AlertType.ERROR);

        else {   // send request to server to pull data for report, with store and date interval
            LinkedList<Object> msg = new LinkedList<Object>();
            msg.add("#PULL_MANAGER_REPORT"); //get stores from db
            msg.add(((User) App.client.user).getStore());
            msg.add(getPickedDate(fromDate));
            msg.add(addDays(getPickedDate(toDate), 1));
            App.client.setController(this);
            try {
                App.client.sendToServer(msg); //Sends a msg contains the command and the current controller
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * changedFromDate and changedToDate functions activates when the user changes the DatePicker value,
     * then they display the right data that the user could choose from on the other DatePicker.
     */

    public void changedFromDate(ActionEvent event) throws InterruptedException {
        toDate.setDisable(false);
        if (numOfDays(fromDate.getValue(), LocalDate.now()) <= 31)
            displayDates(toDate, fromDate.getValue(), LocalDate.now());

        else
            displayDates(toDate, fromDate.getValue(), addLocalDate(fromDate, 30));
    }

    public void changedToDate(ActionEvent event) throws InterruptedException {
        toDate.setDisable(false);
        displayDates(fromDate, addLocalDate(toDate, -30), toDate.getValue());
    }

    /**
     * pullData function called after server sends the orders and complaints to make the report,
     * then it calls other functions to display each component of the report.
     * @param orders all the relevant orders from server
     * @param complaints all the relevant complaints from server
     */

    public void pullData(LinkedList<Order> orders, LinkedList<Complaint> complaints) {
        Platform.runLater(() -> {
            int daysNum = numOfDays(getPickedDate(fromDate), getPickedDate(toDate));
            showOrders(orders);
            showIncome(orders, daysNum);
            showChart(complaints);
        });
    }

    /**
     * showOrders function gets all relevant orders, then it gets a map from getMap that maps from product name
     * to the amount the store sold, and from that data, displaying it with the PieChart.
     * also add a "handle" function to the chart that displays the amount that the product sold when clicking
     * on the PieChart.
     * @param orders orders from server
     */

    private void showOrders(LinkedList<Order> orders) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        Map<String, Integer> productMap = getMap(orders);
        for (Map.Entry<String, Integer> entry : productMap.entrySet())
            if (entry.getValue() > 0)
                pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));

        this.ordersChart.setData(pieChartData);
        for (final PieChart.Data data : ordersChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    salesNum.setText(data.getName() + " number of sales: " + String.valueOf((int) data.getPieValue()));
                }
            });
        }
    }

    /**
     * showIncome function calculates number pf orders and average and displays it.
     */

    private void showIncome(LinkedList<Order> orders, int daysNum) {
        int totalPrice = 0;
        float avgPrice = 0, avgOrders = (float) orders.size() / daysNum;

        for (Order order : orders)
            totalPrice += order.getPrice();

        avgPrice = (float) totalPrice / daysNum;

        incomeReport.setText("Total income: ₪" + totalPrice + "\n" +
                "Average income: ₪" + String.format("%.2f", avgPrice));
        ordersReport.setText("Total orders: " + orders.size() + "\n" +
                "Average orders: " + String.format("%.2f", avgOrders));
    }

    /**
     * the showChart function gets all relevant complaints, then sorting them by complaint type and
     * displays it on the XYChart
     * @param complaints
     */

    private void showChart(LinkedList<Complaint> complaints) {
        complaintChart.getData().clear();
        LinkedList<XYChart.Series<String, Number>> seriesLinkedList = new LinkedList<XYChart.Series<String, Number>>();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        for (Complaint.Topic topic : Complaint.getAllTopics()) {
            XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
            series.setName(Complaint.topicToString(topic));
            seriesLinkedList.add(series);

            for (LocalDate date : getDatesBetween(fromDate.getValue(), toDate.getValue())) {
                int numOfComp = 0;
                for (Complaint complaint : complaints) {
                    if (dateAreEqual(dateToLocalDate(complaint.getDate()), date) &&
                            complaint.getTopic() == topic)
                        numOfComp += 1;
                }

                series.getData().add(new XYChart.Data<>(formatter.format(localDateToDate(date)), numOfComp));
            }
        }
        complaintChart.getData().addAll(seriesLinkedList.get(0), seriesLinkedList.get(1), seriesLinkedList.get(2)
                , seriesLinkedList.get(3), seriesLinkedList.get(4));
    }

    /**
     * @return if the time interval data filled validly
     */

    public boolean isInvalid() {
        return toDate.isDisabled() || toDate.getValue() == null;
    }
}
