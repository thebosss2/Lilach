package org.client;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.util.Duration;
import org.client.ocsf.AbstractClient;
import org.entities.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Client extends AbstractClient {

    public StoreSkeleton storeSkeleton;

    protected static LinkedList<PreMadeProduct> products = new LinkedList<PreMadeProduct>();//(LinkedList<Product>) Catalog.getProducts();

    protected static LinkedList<Order> orders = new LinkedList<Order>();

    private Controller controller;

    public Cart cart = new Cart();

    protected Guest user;

    public Client(String localhost, int i) {
        super(localhost, i);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public static void main(String[] args) {
    }

    public static int[] hourList = {8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18};

    // TODO Maybe delete
    private static Client client = null;

    public static Client getClient() {
        if (client == null) {
            client = new Client("localhost", 3000);
        }
        return client;
    }

    public StoreSkeleton getSkeleton() {
        return storeSkeleton;
    }


    @Override
    protected void handleMessageFromServer(Object msg) {     //function handles message from server
        try { //TODO cast before here the msg.
            switch (((LinkedList<Object>) msg).get(0).toString()) {       //switch with all command options sent between client and server
                case "#PULLCATALOG" -> pushToCatalog(msg);//function gets all data from server to display to client
                case "#PULLBASES" -> pushToBases(msg);//function gets all data from server to display to client
                case "#PULLORDERS" -> pushToOrders(msg);//function gets all data from server to display to client
                case "#LOGIN" -> loginClient((LinkedList<Object>) msg);
                case "#SIGNUP_AUTHENTICATION" -> authenticationReply((LinkedList<Object>) msg);
                case "#PULLSTORES" -> pushStores(msg);//function gets all data from server to display to client
                case "#PULL_COMPLAINTS" -> pushComplaints((LinkedList<Object>) msg);
                case "#UPDATE_CUSTOMER" -> this.user = (Customer)((LinkedList<Object>) msg).get(1);
                case "#DELETEORDER" -> changeBalance(msg);//function gets all data from server to display to client
                case "#PULLUSERS" -> pushUsers(msg);
                case "#FROZEN" -> Controller.sendAlert("User was FROZEN by system Admin" ,"Frozen User" , Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            System.out.println(e.getMessage());
            System.out.println("Client Error");
            e.getStackTrace();
        }
    }

    private void pushUsers(Object msg) {
        ManageAccountsController manageAccountsController = (ManageAccountsController) controller;
        manageAccountsController.pullUsersToClient((LinkedList<User>) ((LinkedList<Object>) msg).get(1));
    }

    private void pushToOrders(Object msg) {
        orders = (LinkedList<Order>) ((LinkedList<Object>) msg).get(1);
        SummaryOrdersController summaryOrdersController = (SummaryOrdersController) controller;
        summaryOrdersController.pullOrdersToClient();       //calls static function in client for display
    }

    private void changeBalance(Object msg) {
        int refund = 0;
        int price = (int) ((LinkedList<Object>) msg).get(1);
        Date date = (Date) ((LinkedList<Object>) msg).get(2);
        String hour = (String) ((LinkedList<Object>) msg).get(3);

        Date new_date = new Date();
        long diffInMillies = Math.abs(date.getTime() - new_date.getTime());
        long diff = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        diff += Integer.parseInt(hour.substring(0, 2));


        //alert+ change refund
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Order Cancellation succeeded");
        alert.setTitle("The Order Has Been Canceled");

        if (diff > 3)
        {
            refund = price;
            alert.setContentText("You have received a full refund");
        }
        else if (diff>1)
        {
            refund = price/2;
            alert.setContentText("The refund is half the order price");
        }
        else
            alert.setContentText("I'm sorry, but according to the policy you do not deserve a refund");

        ((Customer) App.client.user).setBalance(((Customer) App.client.user).getBalance() + refund);
    }

    private void pushComplaints(LinkedList<Object> msg) {
        ComplaintInspectionTableController tableController = (ComplaintInspectionTableController) controller;
        tableController.pullComplaints(FXCollections.observableArrayList( ((ArrayList<Complaint>) msg.get(1))));
    }

    private void pushToCatalog(Object msg) throws IOException { // takes data received and sends to display function
        products = (LinkedList<PreMadeProduct>) ((LinkedList<Object>) msg).get(1);
        CatalogController catalogController = (CatalogController) controller;
        catalogController.pullProductsToClient();       //calls static function in client for display
    }

    private void pushToBases(Object msg) throws IOException { // takes data received and sends to display function
        products = (LinkedList<PreMadeProduct>) ((LinkedList<Object>) msg).get(1);
        CreateCustomMadeController createCustomMadeController = (CreateCustomMadeController) controller;
        createCustomMadeController.pullProductsToClient();       //calls static function in client for display
    }

    private void authenticationReply(LinkedList<Object> msg) {
        SignUpController signUpController = (SignUpController) controller;
        if (msg.get(1).toString().equals("#USER_DOES_NOT_EXIST")) {
            List<Object> newMsg = new LinkedList<Object>();
            newMsg.add("#SIGNUP");
            newMsg.add(signUpController.createNewUser());
            try {
                this.sendToServer(newMsg);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Sign-up succeeded.");
                        //alert.getButtonTypes().clear();
                        alert.show();
                        PauseTransition pause = new PauseTransition(Duration.seconds(1));
                        pause.setOnFinished((e -> alert.close()));
                        pause.play();

                        //TODO now isntead of text, I can create a mini pane with opacity 0.
                    }
                });

            });
            /*try {
                XMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fadingPopupMessage.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                signUpController.get

                FadeTransition ft = new FadeTransition(Duration.millis(3000), page);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.play();
                ft.setFromValue(1.0);
                ft.setToValue(0.0);
                ft.play();
                Scene scene = new Scene(page);


            } catch (IOException e) {
                e.printStackTrace();
            }*/


        } else {

            Controller.sendAlert("Username already taken. Please try a new one.", "Sign-Up Failed", Alert.AlertType.WARNING);
        }
    }

    private void pushStores(Object msg) throws IOException { // takes data received and sends to display function
        CreateOrderController createOrderController;
        CEOReportController ceoReportController;

        if (controller instanceof CreateOrderController) {
            createOrderController = (CreateOrderController) controller;
            createOrderController.pullStoresToClient((LinkedList<Store>) ((LinkedList<Object>) msg).get(1));       //calls static function in client for display
        } else if (controller instanceof CEOReportController) {
            ceoReportController = (CEOReportController) controller;
            ceoReportController.pullStoresToClient((LinkedList<Store>) ((LinkedList<Object>) msg).get(1));       //calls static function in client for display
        }
        else if(controller instanceof SignUpController){
            SignUpController signUpController = (SignUpController) controller;
            signUpController.pullStoresToClient((LinkedList<Store>) ((LinkedList<Object>) msg).get(1));
        }



    }


    private void changeMenu() {

        if(this.user instanceof Customer){
            storeSkeleton.changeLeft("CustomerMenu");
            storeSkeleton.changeCenter("Catalog");
        }else if(this.user instanceof Employee){
            switch(((Employee) this.user).getRole()){
                case STORE_EMPLOYEE -> {
                    storeSkeleton.changeLeft("WorkerMenu");
                    storeSkeleton.changeCenter("EditCatalog");}
                case CUSTOMER_SERVICE -> {
                    storeSkeleton.changeLeft("CustomerServiceMenu");
                    storeSkeleton.changeCenter("ComplaintInspectionTable");}
                case STORE_MANAGER -> {
                    storeSkeleton.changeLeft("ManagerMenu");
                    storeSkeleton.changeCenter("EditCatalog");}
                case CEO -> {
                    storeSkeleton.changeLeft("ManagerMenu");
                    storeSkeleton.changeCenter("CEOReport");}
                case ADMIN -> {
                    storeSkeleton.changeLeft("AdminMenu");
                    storeSkeleton.changeCenter("ManageAccounts"); ///////Waiting on ceo freeze user FXML
                    }
            }
        } else {
            storeSkeleton.changeLeft("GuestMenu");
            storeSkeleton.changeCenter("Catalog");
        }


    }

    public void logOut() {   //TODO clean cart
        List<Object> msg = new LinkedList<Object>();
        msg.add("#LOGOUT");
        msg.add(user);
        try {
            sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loginClient(LinkedList<Object> msg) {
        if(msg.get(1).equals("ALREADYCONNECTED")){
            Controller.sendAlert("User already connected to server" ,"Double connection restricted" , Alert.AlertType.WARNING);
        }else{
            if (msg.get(1).equals("#SUCCESS")) {
                switch (msg.get(2).toString()) {
                    case "CUSTOMER" -> this.user = (Customer) msg.get(3);
                    case "EMPLOYEE" -> this.user = (Employee) msg.get(3);
                    case "GUEST" -> this.user = new Guest();
                }

                if(this.user instanceof Customer){
                    Customer customer = (Customer) this.user;
                    if(customer.getAccountType() == Customer.AccountType.MEMBERSHIP && customer.getMemberShipExpire().before(new Date())){
                        Platform.runLater(() -> updateAccountType(customer));
                    }
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        changeMenu();
                    }
                });

                //TODO add menu switch and "hello {name}".
            }
            //TODO add response to failure.
        }
    }

    @FXML
    private void updateAccountType(Customer customer) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Membership expired");
        alert.setHeaderText("Your membership has expired. Would you like to renew your subscription?");
        alert.setContentText("Note: by renewing your membership, your credit-card will be charge 100₪");
        alert.getButtonTypes().clear();
        ButtonType confirmBtn = new ButtonType("Confirm");
        ButtonType rejectBtn = new ButtonType("Reject");
        alert.getButtonTypes().setAll(confirmBtn, rejectBtn);
        Optional<ButtonType> result = alert.showAndWait();

        List<Object> msg = new LinkedList<>();
        msg.add("#UPDATE_CUSTOMER_ACCOUNT");
        msg.add(customer);
        if(result.get() == confirmBtn){
            msg.add("CONFIRMED");
            if(customer.getBalance() > 0)
                if(customer.getBalance() < 100)
                    msg.add(0);
                else
                    msg.add(customer.getBalance() - 100);
        }else{
            msg.add("REJECTED");
        }

        try {
            App.client.sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
