package org.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import org.entities.Store;

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

    }

    public void changedFromDate (ActionEvent event) throws InterruptedException {
        toDate.setDisable(false);
        displayDates(toDate, fromDate.getValue(), LocalDate.now());
    }

    public void changedToDate (ActionEvent event) throws InterruptedException {
        toDate.setDisable(false);
        displayDates(fromDate, toDate.getValue(), true);
    }

}
