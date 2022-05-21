/**
 * Sample Skeleton for 'SignUp.fxml' Controller Class
 */

package org.client;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private PasswordField passwordText; // Value injected by FXMLLoader

    @FXML // fx:id="popup"
    public Text popup; // Value injected by FXMLLoader

    @FXML // fx:id="signUpBtn"
    private Button signUpBtn; // Value injected by FXMLLoader

    @FXML // fx:id="usernameText"
    private TextField usernameText; // Value injected by FXMLLoader

    @FXML // fx:id="IdText"
    private TextField idText; // Value injected by FXMLLoader

    @FXML // fx:id="PhoneNumberText"
    private TextField phoneNumberText; // Value injected by FXMLLoader

    Pattern pattern1 = Pattern.compile(".{0,10}");
    TextFormatter<String> formatter1 = new TextFormatter<String>( change -> {
        change.setText(change.getText().replaceAll("[^0-9.,]", ""));
        return pattern1.matcher(change.getControlNewText()).matches() ? change : null;
    });

    Pattern pattern2 = Pattern.compile(".{0,9}");
    TextFormatter<String> formatter2 = new TextFormatter<String>(change-> {
        change.setText(change.getText().replaceAll("[^0-9.,]", ""));
        return pattern2.matcher(change.getControlNewText()).matches() ? change : null;
    });

    Pattern pattern3 = Pattern.compile(".{0,16}");
    TextFormatter<String> formatter3 = new TextFormatter<String>(change-> {
        change.setText(change.getText().replaceAll("[^0-9.,]", ""));
        return pattern3.matcher(change.getControlNewText()).matches() ? change : null;
    });

    @FXML
    void enterSignup(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER)
            signUpClicked(new ActionEvent());
    }

    @FXML
    void signUpClicked(ActionEvent event) {
        if(checkFieldsNotEmpty()){
            sendAlert("One or more fields are empty!");
            return;
        }
        if(!idAndEmailCheck()){
            return;
        }
        App.client.setController(this);
        List<Object> msg = new LinkedList<Object>();
        msg.add("#SIGNUP_AUTHENTICATION");
        msg.add(usernameText.getText().toString());
        msg.add(idText.getText().toString());        //TODO add id here for validation check
        try {
            coolButtonClick(signUpBtn);
            App.client.sendToServer(msg);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sendAlert(String error) {
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

    private boolean idAndEmailCheck() {
        String email = emailText.getText().toString();
        String compMail=email;
        int countAt = compMail.length() - compMail.replace("@", "").length();
        if(countAt!=1 || email.indexOf("@")==0)
            return false;
        if(idText.getText().length()!=9)
            return false;
        if(usernameText.getText().contains(" "))
        {
            sendAlert("username cannot have spaces");
            return false;
        }
        return true;
    }

    private boolean checkFieldsNotEmpty() {
        return fullNameText.getText().isEmpty() || usernameText.getText().isEmpty() || passwordText.getText().isEmpty() ||idText.getText().isEmpty()||
                emailText.getText().isEmpty() || creditCardText.getText().isEmpty() || accountType.getSelectionModel().isEmpty() || phoneNumberText.getText().isEmpty();
    }

    public Customer createNewUser(){    //TODO add id
        return new Customer(idText.getText(),fullNameText.getText(),
                usernameText.getText(),
                passwordText.getText(),
                emailText.getText(),
                phoneNumberText.getText(),
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
        assert idText != null : "fx:id=\"idText\" was not injected: check your FXML file 'SignUp.fxml'.";
        assert phoneNumberText != null : "fx:id=\"phoneNumberText\" was not injected: check your FXML file 'SignUp.fxml'.";
        assert accountType != null : "fx:id=\"accountType\" was not injected: check your FXML file 'SignUp.fxml'.";
        assert creditCardText != null : "fx:id=\"creditCardText\" was not injected: check your FXML file 'SignUp.fxml'.";
        assert defaultStore != null : "fx:id=\"defaultStore\" was not injected: check your FXML file 'SignUp.fxml'.";
        assert emailText != null : "fx:id=\"emailText\" was not injected: check your FXML file 'SignUp.fxml'.";
        assert fullNameText != null : "fx:id=\"fullNameText\" was not injected: check your FXML file 'SignUp.fxml'.";
        assert mainPane != null : "fx:id=\"mainPane\" was not injected: check your FXML file 'SignUp.fxml'.";
        assert passwordText != null : "fx:id=\"passwordText\" was not injected: check your FXML file 'SignUp.fxml'.";
        assert popup != null : "fx:id=\"popup\" was not injected: check your FXML file 'SignUp.fxml'.";
        assert signUpBtn != null : "fx:id=\"signUpBtn\" was not injected: check your FXML file 'SignUp.fxml'.";
        assert usernameText != null : "fx:id=\"usernameText\" was not injected: check your FXML file 'SignUp.fxml'.";
        accountType.getItems().addAll("Store", "Chain", "Membership");

        phoneNumberText.setTextFormatter(formatter1);
        idText.setTextFormatter(formatter2);
        creditCardText.setTextFormatter(formatter3);

    }

}


