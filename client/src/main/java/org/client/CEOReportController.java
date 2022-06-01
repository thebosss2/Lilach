package org.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.*;
import org.entities.Store;
import org.entities.User;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class CEOReportController extends AbstractReport {

    final static int TODATE = 0, FROMDATE = 1, SCREEN = 2;

    @FXML
    private Label companyIncome1;

    @FXML
    private Label companyIncome2;

    @FXML
    private PieChart companyOrders1;

    @FXML
    private PieChart companyOrders2;

    @FXML
    private StackedBarChart<?, ?> complaintChart1;

    @FXML
    private StackedBarChart<?, ?> complaintChart2;

    @FXML
    private NumberAxis complaintsAxis1;

    @FXML
    private NumberAxis complaintsAxis2;

    @FXML
    private CategoryAxis daysAxis1;

    @FXML
    private CategoryAxis daysAxis2;

    @FXML
    private DatePicker fromDate1;

    @FXML
    private DatePicker fromDate2;

    @FXML
    private Label incomeReport1;

    @FXML
    private Label incomeReport2;

    @FXML
    private Button makeReportBtn1;

    @FXML
    private Button makeReportBtn2;

    @FXML
    private PieChart ordersChart1;

    @FXML
    private PieChart ordersChart2;

    @FXML
    private ComboBox<String> storePicker;

    @FXML
    private DatePicker toDate1;

    @FXML
    private DatePicker toDate2;

    private List<Store> stores = new LinkedList<Store>();


    @FXML
    void initialize() {
        displayDates(fromDate1, LocalDate.now(), true);
        displayDates(fromDate2, LocalDate.now(),true);
        this.stores = App.client.getStores();
        for (Store s : stores)
            storePicker.getItems().add(s.getName());
    }


    @FXML
    void makeReport(ActionEvent event) throws InterruptedException {
        coolButtonClick((Button) event.getTarget());

        List<Object> list = getScreen(event);
        DatePicker toDate = (DatePicker) list.get(TODATE), fromDate = (DatePicker) list.get(FROMDATE);
        String screen = (String) list.get(SCREEN);

        if(isInvalid(toDate))
            sendAlert("Must pick time interval to make a report!", "Date Missing", Alert.AlertType.ERROR);

        else{   // send request to server to pull data for report, with store and date interval
            LinkedList<Object> msg = new LinkedList<Object>();
            msg.add("#PULL_CEO_REPORT"); //get stores from db
            msg.add(screen);
            msg.add( ((User)App.client.user).getStore() );
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

    public void changedFromDate (ActionEvent event) throws InterruptedException {
        List<Object> list = getScreen(event);
        DatePicker toDate = (DatePicker) list.get(TODATE), fromDate = (DatePicker) list.get(FROMDATE);
        toDate.setDisable(false);

        if(numOfDays(fromDate.getValue(), LocalDate.now()) <= 31)
            displayDates(toDate, fromDate.getValue(), LocalDate.now());

        else
            displayDates(toDate ,fromDate.getValue(), addLocalDate(fromDate, 30));
    }

    public void changedToDate (ActionEvent event) throws InterruptedException {
        List<Object> list = getScreen(event);
        DatePicker toDate = (DatePicker) list.get(TODATE), fromDate = (DatePicker) list.get(FROMDATE);
        toDate.setDisable(false);
        displayDates(fromDate, addLocalDate(toDate, -30), toDate.getValue());
    }

    public boolean isInvalid(DatePicker dp){
        return dp.isDisabled() || dp.getValue() == null;
    }

    public List<Object> getScreen(ActionEvent event){
        Node node = (Node) event.getTarget();
        LinkedList<Object> list = new LinkedList<>();
        if(node.getId().equals("toDate1") || node.getId().equals("fromDate1")
                || node.getId().equals("makeReportBtn1")){
            list.add(toDate1);
            list.add(fromDate1);
            list.add("FirstScreen");
        }
        else {
            list.add(toDate2);
            list.add(fromDate2);
            list.add("SecondScreen");
        }

        return list;
    }
}
