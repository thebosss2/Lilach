/**
 * Sample Skeleton for 'SignUp.fxml' Controller Class
 */

package org.client;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.entities.Customer;
import org.entities.Store;

public class SignUpController extends Controller{

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="accountType"
    private ChoiceBox<String> accountType = new ChoiceBox<>(); // Value injected by FXMLLoader

    @FXML // fx:id="birthdatePicker"
    private DatePicker birthdatePicker; // Value injected by FXMLLoader

    @FXML // fx:id="creditCardText"
    private TextField creditCardText; // Value injected by FXMLLoader

    @FXML // fx:id="defaultStore"
    private ComboBox<Store> defaultStore = new ComboBox<Store>(); // Value injected by FXMLLoader

    @FXML // fx:id="emailText"
    private TextField emailText; // Value injected by FXMLLoader

    @FXML // fx:id="fullNameText"
    private TextField fullNameText; // Value injected by FXMLLoader

    @FXML // fx:id="mainPane"
    private AnchorPane mainPane; // Value injected by FXMLLoader

    @FXML // fx:id="passwordText"
    private TextField passwordText; // Value injected by FXMLLoader

    @FXML // fx:id="popup"
    public Text popup; // Value injected by FXMLLoader

    @FXML // fx:id="signUpBtn"
    private Button signUpBtn; // Value injected by FXMLLoader

    @FXML // fx:id="usernameText"
    private TextField usernameText; // Value injected by FXMLLoader

    @FXML
    void signUpClicked(ActionEvent event) {
        if(checkFieldsNotEmpty()){
            sendAlert("One or more fields are empty!");
            return;
        }
        App.client.setController(this);
        List<Object> msg = new LinkedList<Object>();
        msg.add("#SIGNUP_AUTHENTICATION");
        msg.add(usernameText.getText().toString());
        try {
            coolButtonClick(signUpBtn);
            App.client.sendToServer(msg);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendAlert(String error) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Sign-up failed");
                    alert.setHeaderText(error);
                    //alert.setContentText("Are you sure?");
                    alert.showAndWait().get();
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        });


    }

    private boolean checkFieldsNotEmpty() {
        return fullNameText.getText().isEmpty() || usernameText.getText().isEmpty() || passwordText.getText().isEmpty() ||
                emailText.getText().isEmpty() || creditCardText.getText().isEmpty() || accountType.getSelectionModel().isEmpty() || birthdatePicker.getValue() == null;
    }

    public Customer createNewUser(){
        return new Customer(fullNameText.getText(),
                usernameText.getText(),
                passwordText.getText(),
                emailText.getText(),
                Date.from(birthdatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                creditCardText.getText(),
                coonvertToAccountType(accountType.getValue()));
    }

    private Customer.AccountType coonvertToAccountType(String accountType) {
        return switch (accountType){
            case "Store" -> Customer.AccountType.STORE;
            case "Chain" -> Customer.AccountType.CHAIN;
            default -> Customer.AccountType.MEMBERSHIP;
        };
    }

    public void setPopupInMiddle(){
        popup.setX(mainPane.getWidth() / 2);
        popup.setY(mainPane.getHeight() / 2);
    }
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert accountType != null : "fx:id=\"accountType\" was not injected: check your FXML file 'SignUp.fxml'.";
        assert birthdatePicker != null : "fx:id=\"birthdatePicker\" was not injected: check your FXML file 'SignUp.fxml'.";
        assert creditCardText != null : "fx:id=\"creditCardText\" was not injected: check your FXML file 'SignUp.fxml'.";
        assert defaultStore != null : "fx:id=\"defaultStore\" was not injected: check your FXML file 'SignUp.fxml'.";
        assert emailText != null : "fx:id=\"emailText\" was not injected: check your FXML file 'SignUp.fxml'.";
        assert fullNameText != null : "fx:id=\"fullNameText\" was not injected: check your FXML file 'SignUp.fxml'.";
        assert mainPane != null : "fx:id=\"mainPane\" was not injected: check your FXML file 'SignUp.fxml'.";
        assert passwordText != null : "fx:id=\"passwordText\" was not injected: check your FXML file 'SignUp.fxml'.";
        assert popup != null : "fx:id=\"popup\" was not injected: check your FXML file 'SignUp.fxml'.";
        assert signUpBtn != null : "fx:id=\"signUpBtn\" was not injected: check your FXML file 'SignUp.fxml'.";
        assert usernameText != null : "fx:id=\"usernameText\" was not injected: check your FXML file 'SignUp.fxml'.";

        birthdatePicker.setPromptText("dd-MM-yyyy");

        accountType.getItems().addAll("Store", "Chain", "Membership");
    }

}


