/**
 * Sample Skeleton for 'ComplaintInspectionTable.fxml' Controller Class
 */

package org.client;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.entities.Complaint;
import org.entities.Customer;
import org.entities.User;

public class ComplaintInspectionTableController extends Controller{

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="dateCol"
    private TableColumn<Complaint, Date> dateCol; // Value injected by FXMLLoader

    @FXML // fx:id="inspectBtnsCol"
    private TableColumn<Complaint, Button> inspectBtnsCol; // Value injected by FXMLLoader

    @FXML // fx:id="nameCol"
    private TableColumn<Complaint, String> nameCol; // Value injected by FXMLLoader

    @FXML // fx:id="statusCol"
    private TableColumn<Complaint, String> statusCol; // Value injected by FXMLLoader

    @FXML // fx:id="tableView"
    private TableView<Complaint> tableView; // Value injected by FXMLLoader

    @FXML // fx:id="topicCol"
    private TableColumn<Complaint, Complaint.Topic> topicCol; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert dateCol != null : "fx:id=\"dateCol\" was not injected: check your FXML file 'ComplaintInspectionTable.fxml'.";
        assert inspectBtnsCol != null : "fx:id=\"inspectBtnsCol\" was not injected: check your FXML file 'ComplaintInspectionTable.fxml'.";
        assert nameCol != null : "fx:id=\"nameCol\" was not injected: check your FXML file 'ComplaintInspectionTable.fxml'.";
        assert statusCol != null : "fx:id=\"statusCol\" was not injected: check your FXML file 'ComplaintInspectionTable.fxml'.";
        assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'ComplaintInspectionTable.fxml'.";
        assert topicCol != null : "fx:id=\"topicCol\" was not injected: check your FXML file 'ComplaintInspectionTable.fxml'.";

        //redundant
        Customer cust = new Customer("23465", "Sagi","user","pass","mail","56346","credit", Customer.AccountType.MEMBERSHIP);

        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        topicCol.setCellValueFactory(new PropertyValueFactory<>("topic"));

        statusCol.setCellValueFactory(cellData -> {
            boolean status = cellData.getValue().getStatus();
            return new ReadOnlyStringWrapper(status ? "Open" : "Closed");
        });

        nameCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCustomer().getName()));

        ObservableList<Complaint> complaints = FXCollections.observableArrayList();

        //TODO add function to get all complaints from the database and use here.
        for(int i = 0; i < 5; i++){
            Complaint complaint = new Complaint(cust, new Date(), "This is a complaint "+i, Complaint.Topic.OTHER);
            complaints.add(complaint);
        }
        addButtonToTable();

        tableView.setItems(complaints);
        //tableView.setRoot(root);

    }

    private void addButtonToTable() {
        TableColumn<Complaint, Void> colBtn = new TableColumn("Inspect complaint");

        Callback<TableColumn<Complaint, Void>, TableCell<Complaint, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Complaint, Void> call(final TableColumn<Complaint, Void> param) {
                final TableCell<Complaint, Void> cell = new TableCell<>() {

                    private final Button btn = new Button("Action");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Complaint complaint = getTableView().getItems().get(getIndex());
                            goToComplaintInspection(complaint);
                            //TODO pass complaint given to the ComplaintInspection screen.
                        });
                        btn.setStyle("-fx-background-color:  #c6acef");
                        btn.setText("Inspect");
                    }



                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        tableView.getColumns().add(colBtn);

    }

    private void goToComplaintInspection(Complaint complaint) {
        ComplaintInspectionController controller = (ComplaintInspectionController)
                this.getSkeleton().changeCenter("ComplaintInspection");
        controller.setComplaint(complaint);
    }

}
