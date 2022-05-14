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

import java.time.LocalDate;

public class ReportController {

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
    void initialize(){

    }

    @FXML
    void makeReport(ActionEvent event) {

    }

}
