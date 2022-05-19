/**
 * Sample Skeleton for 'ComplaintInspection.fxml' Controller Class
 */

package org.client;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.entities.Complaint;

public class ComplaintInspectionController extends Controller{

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="complaintText"
    private TextArea complaintText; // Value injected by FXMLLoader

    private Complaint complaint;

    public void setComplaint(Complaint complaint) {
        this.complaint = complaint;
    }
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert complaintText != null : "fx:id=\"complaintText\" was not injected: check your FXML file 'ComplaintInspection.fxml'.";
        Platform.runLater(() -> complaintText.setText(complaint.getCompText()));

    }

}
