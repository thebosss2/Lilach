/**
 * Sample Skeleton for 'ComplaintSubmission.fxml' Controller Class
 */

package org.client;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.util.Duration;
import org.entities.Complaint;
import org.entities.Customer;

public class ComplaintSubmissionController extends Controller{

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

    @FXML
    void sendComplaint(ActionEvent event) {

        //TODO add topic not empty or text.

        List<Object> msg = new LinkedList<>();
        msg.add("#COMPLAINT");
        Complaint complaint = new Complaint((Customer)App.client.user, new Date(), complaintText.getText(), Complaint.convertToTopic(complaintTopic.getValue()));
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
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished((e -> {
                alert.close();
                this.getSkeleton().changeCenter("Catalog");}));
            pause.play();

        });
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert complaintText != null : "fx:id=\"complaintText\" was not injected: check your FXML file 'ComplaintSubmission.fxml'.";
        assert complaintTopic != null : "fx:id=\"complaintTopic\" was not injected: check your FXML file 'ComplaintSubmission.fxml'.";
        assert submitBtn != null : "fx:id=\"submitBtn\" was not injected: check your FXML file 'ComplaintSubmission.fxml'.";

        complaintTopic.getItems().addAll("Bad service", "Order didn't arrive in time",
                    "Defective product/ not what you ordered", "Payment issue", "Other");

    }

}
