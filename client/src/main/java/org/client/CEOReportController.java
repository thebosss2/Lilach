package org.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.*;
import org.entities.Store;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;

public class CEOReportController extends Controller {

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

    private LinkedList<Store> stores = new LinkedList<Store>();


    @FXML
    void initialize() {
        displayDates(fromDate1, LocalDate.now(), true);
        displayDates(fromDate2, LocalDate.now(),true);
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
    void makeReport1(ActionEvent event) throws InterruptedException {
        coolButtonClick((Button) event.getTarget());

    }

    @FXML
    void makeReport2(ActionEvent event) throws InterruptedException {
        coolButtonClick((Button) event.getTarget());

    }

    public void pullStoresToClient(LinkedList<Store> stores) {
        this.stores = stores;
        for (Store s : stores)
            storePicker.getItems().add(s.getName());
    }


    public void changedFromDate1 (ActionEvent event) throws InterruptedException {
        toDate1.setDisable(false);
        displayDates(toDate1, fromDate1.getValue(), LocalDate.now());
    }

    public void changedFromDate2 (ActionEvent event) throws InterruptedException {
        toDate2.setDisable(false);
        displayDates(toDate2, fromDate2.getValue(), LocalDate.now());
    }


    public void changedToDate1 (ActionEvent event) throws InterruptedException {
        toDate1.setDisable(false);
        displayDates(fromDate1, toDate1.getValue(), true);
    }

    public void changedToDate2 (ActionEvent event) throws InterruptedException {
        toDate2.setDisable(false);
        displayDates(fromDate2, toDate2.getValue(), true);
    }

}
