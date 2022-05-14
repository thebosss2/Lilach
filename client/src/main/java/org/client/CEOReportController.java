package org.client;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import org.entities.Store;

public class CEOReportController extends Controller{

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
    private ComboBox<Store> storePicker;

    @FXML
    private DatePicker toDate1;

    @FXML
    private DatePicker toDate2;


    @FXML
    void initialize() {
        //storePicker.getItems().add(s1);
        //.getItems().add(s2);
    }


    @FXML
    void makeReport1(ActionEvent event) throws InterruptedException {
        coolButtonClick((Button)event.getTarget());

    }

    @FXML
    void makeReport2(ActionEvent event) throws InterruptedException {
        coolButtonClick((Button)event.getTarget());

    }

}
