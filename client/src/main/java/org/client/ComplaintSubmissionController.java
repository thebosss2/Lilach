/**
 * Sample Skeleton for 'ComplaintSubmission.fxml' Controller Class
 */

package org.client;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.entities.Complaint;
import org.entities.Customer;
import org.entities.Store;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class ComplaintSubmissionController extends Controller {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="complaintText"
    private TextArea complaintText; // Value injected by FXMLLoader

    @FXML // fx:id="complaintTopic"
    private ComboBox<String> complaintTopic; // Value injected by FXMLLoader

    @FXML // fx:id="submitBtn"
    private Button submitBtn; // Value injected by FXMLLoader

    @FXML // fx:id="storePick"
    private ComboBox<String> storePick; // Value injected by FXMLLoader

    private List<Store> stores = new LinkedList<Store>();

    /**
     * Getting the selected store in the combobox.
     * @return
     */
    Store getSelectedStore() {
        Store pickedStore = new Store();
        for (Store s : stores)
            if (s.getName().equals(storePick.getValue()))
                pickedStore = s;
        return pickedStore;
    }

    /**
     * Checking if one of the fields is empty.
     * @return
     */
    private boolean checkEmpty() {
        if (complaintText.getText().isEmpty() || complaintTopic.getValue().equals("Set Complaint Topic") || storePick.getValue().equals("Set Store")) {
            sendAlert("Some fields have not been filled", " Empty or Missing Fields", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    /**
     * Sending the complaint to the server.
     * @param event
     */
    @FXML
    void sendComplaint(ActionEvent event) {

        if (checkEmpty()) {
            List<Object> msg = new LinkedList<>();
            msg.add("#COMPLAINT");
            Complaint complaint = new Complaint((Customer) App.client.user, new Date(), complaintText.getText(), Complaint.convertToTopic(complaintTopic.getValue()), getSelectedStore());
            msg.add(complaint);
            try {
                App.client.sendToServer(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Complaint submitted, moving to catalog.");
                alert.show();
                PauseTransition pause = new PauseTransition(Duration.seconds(2.5));
                pause.setOnFinished((e -> {
                    alert.close();
                    goToCatalog();
                }));
                pause.play();
            });
        }
    }

    /**
     * Changing the screen to Catalog screen.
     */
    @FXML
    void goToCatalog() {
        ObservableList<Node> buttons = ((VBox)((AnchorPane) this.getSkeleton().mainScreen.getLeft()).getChildren().get(0)).getChildren();

        for (Node node : buttons) {
            if (node.getId().equals("complaintBtn"))
                node.setStyle("-fx-background-color: #9bc98c");

            if (node.getId().equals("catalogBtn"))
                node.setStyle("-fx-background-color: #62a74d");
        }

        this.getSkeleton().changeCenter("Catalog");
    }

    /**
     * Setting the stores to the fxml.
     */
    private void getStores() {
        this.stores = App.client.getStores();
        storePick.getItems().add("Set Store");
        storePick.setValue("Set Store");
        for (Store s : stores)
            storePick.getItems().add(s.getName());
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert complaintText != null : "fx:id=\"complaintText\" was not injected: check your FXML file 'ComplaintSubmission.fxml'.";
        assert complaintTopic != null : "fx:id=\"complaintTopic\" was not injected: check your FXML file 'ComplaintSubmission.fxml'.";
        assert submitBtn != null : "fx:id=\"submitBtn\" was not injected: check your FXML file 'ComplaintSubmission.fxml'.";
        assert storePick != null : "fx:id=\"storePick\" was not injected: check your FXML file 'ComplaintSubmission.fxml'.";
        complaintTopic.getItems().addAll("Bad service", "Order didn't arrive in time",
                "Defective product/ not what you ordered", "Payment issue", "Other");
        complaintTopic.setValue("Set Complaint Topic");
        getStores();

    }

}
