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
import org.entities.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReportController extends Controller{

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
        toDate.setDisable(false);
        if(numOfDays(fromDate.getValue(), LocalDate.now()) <= 31)
            displayDates(toDate, fromDate.getValue(), LocalDate.now());

        else
            displayDates(toDate, addLocalDate(fromDate, 30), LocalDate.now());
    }

    public void changedToDate (ActionEvent event) throws InterruptedException {
        toDate.setDisable(false);
        displayDates(fromDate, addLocalDate(toDate, -30), toDate.getValue());
    }

    public boolean isInvalid(){
        return toDate.isDisabled() || toDate.getValue() == null;
    }

    public void pullData(LinkedList<Order> orders, LinkedList<Complaint> complaints) {
        Platform.runLater(()-> {
            int daysNum = numOfDays(getPickedDate(fromDate), getPickedDate(toDate));
            showOrders(orders);
            showIncome(orders, daysNum);
            showChart(complaints, daysNum);
        });
    }


    private void showOrders(LinkedList<Order> orders) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        Map<String, Integer> productMap = getMap(orders);
        for (Map.Entry<String, Integer> entry : productMap.entrySet())
            if(entry.getValue() > 0)
                pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));

        this.ordersChart.setData(pieChartData);
        for(final PieChart.Data data : ordersChart.getData()){
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    salesNum.setText(data.getName() + " number of sales: " + String.valueOf((int)data.getPieValue()));
                }
            });
        }
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

    private void showIncome(LinkedList<Order> orders, int daysNum) {
        int totalPrice = 0;
        float avgPrice = 0, avgOrders = (float) orders.size() / daysNum;

        for(Order order : orders)
            totalPrice += order.getPrice();

        avgPrice = (float) totalPrice / daysNum;

        incomeReport.setText("Total income: ₪" + totalPrice + "\n" +
                "Average income: ₪" + String.format("%.2f", avgPrice));
        ordersReport.setText("Total orders: " + orders.size() + "\n" +
                "Average orders: " + String.format("%.2f", avgOrders));
    }

    private void showChart(LinkedList<Complaint> complaints, int daysNum) {
        complaintChart.getData().clear();
        LinkedList<XYChart.Series<String, Number>> seriesLinkedList = new LinkedList<XYChart.Series<String, Number>>();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        for(Complaint.Topic topic : Complaint.getAllTopics() ){
            XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
            series.setName(Complaint.topicToString(topic));
            seriesLinkedList.add(series);

            for(LocalDate date : getDatesBetween(fromDate.getValue(), toDate.getValue())){
                int numOfComp = 0;
                for(Complaint complaint : complaints) {
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

    static int numOfDays(Date d1, Date d2) {
        long difference_In_Time = d2.getTime() - d1.getTime();
        return  ((int) TimeUnit.MILLISECONDS.toDays(difference_In_Time) % 365) + 1;
    }

    static int numOfDays(LocalDate d1, LocalDate d2) {
        return numOfDays(localDateToDate(d1), localDateToDate(d2));
    }

    static int numOfDays(DatePicker d1, DatePicker d2) {
        return numOfDays(localDateToDate(d1.getValue()), localDateToDate(d2.getValue()));
    }

    public static List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {

        endDate = addLocalDate(endDate, 1);
        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        return IntStream.iterate(0, i -> i + 1).limit(numOfDaysBetween)
                .mapToObj(i -> startDate.plusDays(i))
                .collect(Collectors.toList());
    }

    public static LocalDate dateToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date localDateToDate(LocalDate date) { //get the picked localDate and convert it to Date
        Instant instant = Instant.from(date.atStartOfDay(ZoneId.systemDefault())); //convert LocalDate to Date
        Date pickedDate = Date.from(instant);
        return pickedDate;
    }

    private boolean dateAreEqual(LocalDate date1, LocalDate date2) {
        return date1.atStartOfDay().isEqual(date2.atStartOfDay());
    }

    public static Date addDays(Date date, int daysToAdd)
    {
        Date d;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, daysToAdd); //minus number would decrement the days
        d = cal.getTime();
        return cal.getTime();
    }

    public static LocalDate addLocalDate(DatePicker toDate, int daysToAdd) {
        return dateToLocalDate(addDays(localDateToDate(toDate.getValue()), daysToAdd));
    }

    public static LocalDate addLocalDate(LocalDate toDate, int daysToAdd) {
        return dateToLocalDate(addDays(localDateToDate(toDate), daysToAdd));
    }
}
