/**
 * Sample Skeleton for 'ComplaintSubmission.fxml' Controller Class
 */

package org.client;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

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

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert complaintText != null : "fx:id=\"complaintText\" was not injected: check your FXML file 'ComplaintSubmission.fxml'.";
        assert complaintTopic != null : "fx:id=\"complaintTopic\" was not injected: check your FXML file 'ComplaintSubmission.fxml'.";
        assert submitBtn != null : "fx:id=\"submitBtn\" was not injected: check your FXML file 'ComplaintSubmission.fxml'.";

        complaintTopic.getItems().addAll("Bad service", "Order didn't arrive in time",
                    "Defective product/ not what you ordered", "Payment issue", "Other");

    }

}
