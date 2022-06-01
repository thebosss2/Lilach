package org.server;

import javafx.scene.control.Alert;
import org.entities.*;
import org.server.ocsf.AbstractServer;
import org.server.ocsf.ConnectionToClient;

import javax.naming.NamingEnumeration;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Server extends AbstractServer {

    public Server(int port) {
        super(port);
    }

    @Override
    /**
     * Msg contains at least a command (string) for the switch to handle.
     */
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {     //handles commands from client for info

        try {
            switch (((LinkedList<Object>) msg).get(0).toString()) {   //switch to see what client wants from server
                case "#PULLCATALOG" -> pullProducts(((LinkedList<Object>) msg), client);  //display updated catalog version
                case "#PULLBASES" -> pullProducts(((LinkedList<Object>) msg), client);  //display updated catalog version
                case "#SAVE" -> updateProduct((LinkedList<Object>) msg);           //save change to product details
                case "#ADD" -> addProduct((LinkedList<Object>) msg);           // add product to the DB
                case "#LOGIN" -> loginServer((LinkedList<Object>)msg,client);
                case "#SIGNUP_AUTHENTICATION" -> authinticateUser((LinkedList<Object>) msg, client);
                case "#SIGNUP" -> signUpServer(((LinkedList<Object>)msg),client);
                case "#PULLSTORES" -> pullStores(((LinkedList<Object>) msg), client);  //display updated catalog version
                case "#SAVEORDER" -> saveOrderServer(((LinkedList<Object>)msg),client);
                case "#LOGOUT" -> logoutServer((LinkedList<Object>) msg, client);
                case "#COMPLAINT" -> addComplaint((LinkedList<Object>) msg);
                case "#PULL_COMPLAINTS" -> pullOpenComplaints(client);
                case "#CLOSE_COMPLAINT" -> closeComplaintAndCompensate((LinkedList<Object>) msg/*,client*/);
                case "#UPDATE_CUSTOMER_ACCOUNT" -> updateCustomerAccount((LinkedList<Object>) msg, client);
                case "#DELETEORDER" -> deleteOrder((LinkedList<Object>) msg, client);
                case "#PULLORDERS" -> pullOrders((LinkedList<Object>) msg, client);
                case "#PULLUSERS" -> pullUsers((LinkedList<Object>) msg, client);
                case "#DELETEPRODUCT" -> deleteProduct((LinkedList<Object>) msg, client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //todo!!!!!!!!
    private void deleteProduct(LinkedList<Object> msg, ConnectionToClient client) {
        App.session.beginTransaction();
        PreMadeProduct product = (PreMadeProduct) (msg).get(1);
        App.session.delete(App.session.find(PreMadeProduct.class, product.getId()));       //evict current product details from database
        App.session.flush();
        App.session.getTransaction().commit(); // Save everything.
    }


    private void pullUsers(LinkedList<Object> msg, ConnectionToClient client) throws IOException {
        List<User> users = App.getAllUsers();
        List<Object>  msgToClient = new LinkedList<Object>();
        msgToClient.add(msg.get(0).toString());
        msgToClient.add(users);
        client.sendToClient(msgToClient);
    }

    private void pullOrders(LinkedList<Object> msg, ConnectionToClient client) throws IOException {
        List<Order> orders = App.getAllOrders();
        List<Object> msgToClient = new LinkedList<Object>();
        msgToClient.add(msg.get(0).toString());
        msgToClient.add(orders);
        client.sendToClient(msgToClient);
    }

    private void changeBalance(Order order, ConnectionToClient client) {
        int refund = 0;
        int price = order.getPrice();
        Date date = order.getDeliveryDate();
        String hour =order.getDeliveryHour() ;
        Customer customer = order.getOrderedBy();
        Date new_date = new Date();
        long diffInMillies = Math.abs(date.getTime() - new_date.getTime());
        long diff = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        diff += Integer.parseInt(hour.substring(0, 2));


        String addition;
/*        //alert+ change refund
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Order Cancellation succeeded");
        alert.setTitle("The Order Has Been Canceled");*/

        if (diff > 3)
        {
            refund = price;
            addition="You have received a full refund";
        }
        else if (diff>1)
        {
            refund = price/2;
            addition="The refund is half the order price";
        }
        else
            addition="I'm sorry, but according to the policy you do not deserve a refund";

        updateBalance(customer,App.session.find(Customer.class,customer.getSerialId()).getBalance() + refund);

        List<Object> newMsg = new LinkedList<Object>();
        newMsg.add("#DELETEORDER");
        newMsg.add("Order Cancellation succeeded" + addition);
        newMsg.add("The Order Has Been Canceled");
        newMsg.add(App.session.find(Customer.class,customer.getSerialId()));
        try {
            client.sendToClient(newMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void deleteOrder(LinkedList<Object> msg, ConnectionToClient client) {
        int id = (int) msg.get(1);
        Order order = App.session.find(Order.class, id);

        App.session.beginTransaction();
        App.session.evict(order);       //evict current product details from database
        order.setDelivered(Order.Status.CANCELED);
        App.session.merge(order);           //merge into database with updated info
        App.session.flush();
        App.session.getTransaction().commit(); // Save everything.
        changeBalance(order,client);
    }
 private void updateCustomerAccount(LinkedList<Object> msg, ConnectionToClient client) {
        Customer customer = (Customer) msg.get(1);
        if(msg.get(2).toString().equals("CONFIRMED")){
            updateAccount(customer, Customer.AccountType.MEMBERSHIP);
            updateBalance(customer, (int) msg.get(3));
        } else{
            updateAccount(customer, Customer.AccountType.CHAIN);
        }

        List<Object> newMsg = new LinkedList<>();
        newMsg.add("#UPDATE_CUSTOMER");
        newMsg.add(customer);
        try {
            client.sendToClient(newMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateAccount(Customer customer, Customer.AccountType type) {
        App.session.beginTransaction();
        App.session.evict(customer);       //evict current product details from database
        if(type == Customer.AccountType.MEMBERSHIP)
            customer.setMemberShipExpire();
        else
            customer.setAccountType(type);
        App.session.merge(customer);           //merge into database with updated info
        App.session.flush();
        App.session.getTransaction().commit(); // Save everything.
    }

    private void closeComplaintAndCompensate(LinkedList<Object> msg/*,ConnectionToClient client*/) {
        Complaint complaint = (Complaint) msg.get(1);
        closeComplaint(complaint);
        if(msg.get(2).equals("COMPENSATED"))
            updateBalance(complaint.getCustomer(),App.session.find(Customer.class,complaint.getCustomer()).getBalance() + (int) msg.get(3));
/*        msg.clear();
        msg.add("#ComplaintCompleted");
        try {
            client.sendToClient(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    private void updateBalance(Customer customer, int balance){
        App.session.beginTransaction();
        App.session.evict(customer);       //evict current product details from database
        //customer.setBalance(balance);/////////////////TODO can evict after change because SQL works with primary key
        ///TODO make this Generic func
        customer.setBalance(balance);
        App.session.merge(customer);           //merge into database with updated info
        App.session.flush();
        App.session.getTransaction().commit(); // Save everything.
    }

    private void closeComplaint(Complaint complaint) {
        App.session.beginTransaction();
        App.session.evict(complaint);       //evict current product details from database
        complaint.setStatus(false);
        App.session.merge(complaint);           //merge into database with updated info
        App.session.flush();
        App.session.getTransaction().commit(); // Save everything.
    }


    private void pullOpenComplaints(ConnectionToClient client) throws IOException {
        List<Complaint> complaints = App.getAllOpenComplaints();
        List<Object> msg = new LinkedList<>();
        msg.add("#PULL_COMPLAINTS");
        msg.add(complaints);
        client.sendToClient(msg);
    }

    private static <T> void addNewInstance(T obj){
        App.session.beginTransaction();
        App.session.save(obj);
        App.session.flush();
        App.session.getTransaction().commit();
    }


    private void addComplaint(LinkedList<Object> msg) {
        addNewInstance((Complaint) msg.get(1));
    }


    private void signUpServer(LinkedList<Object> msg, ConnectionToClient client) throws IOException {
        addNewInstance((Customer) msg.get(1));
        User user = (Customer) msg.get(1);
        msg.clear();
        msg.add("#LOGIN");
        msg.add(user.getUserName());
        msg.add(user.getPassword());
        loginServer(msg,client);
    }

    private void saveOrderServer(LinkedList<Object> msg, ConnectionToClient client) {
        Order order = (Order) msg.get(1);
        App.session.beginTransaction();
        for(PreMadeProduct p : order.getPreMadeProducts()) {
            App.session.save(p);   //saves and flushes to database
            App.session.flush();
        }
        for(CustomMadeProduct p : order.getCustomMadeProducts()) {
            for (PreMadeProduct pre : p.getProducts()) {
                App.session.save(pre);   //saves and flushes to database
                App.session.flush();
            }
            App.session.save(p);
        }
        App.session.save(order);
        App.session.flush();
        App.session.getTransaction().commit();
        updateBalance(order.getOrderedBy(), order.getOrderedBy().getBalance());
        msg.clear();
        msg.add("#UPDATEBALANCE");
        msg.add(order.getOrderedBy());
        try {
            client.sendToClient(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Checks if the username asked by new signup exists.
    private void authinticateUser(LinkedList<Object> msg, ConnectionToClient client) throws IOException {
        List<User> users = App.getAllUsers();
        List<Object> newMsg = new LinkedList<Object>();
        newMsg.add(msg.get(0));
        for(User user : users){
            if(user.getUserName().equals(msg.get(1).toString()) || (user.getID().equals(msg.get(2)) && (user instanceof Customer))){
                newMsg.add("#USER_EXISTS"); //checks if username or user id already exists
                client.sendToClient(newMsg);
                return;
            }
        }//TODO check if ID already exists and email
        newMsg.add("#USER_DOES_NOT_EXIST");
        client.sendToClient(newMsg);
    }

    private static void updateProduct(Object msg) throws IOException {        //update product details func
        App.session.beginTransaction();
        PreMadeProduct productBefore = (PreMadeProduct) ((LinkedList<Object>) msg).get(1);
        PreMadeProduct productAfter = (PreMadeProduct) ((LinkedList<Object>) msg).get(2);

        App.session.evict(productBefore);       //evict current product details from database
        changeParam(productBefore, productAfter);   //func changes product to updates details
        App.session.merge(productBefore);           //merge into database with updated info
        App.session.flush();
        App.session.getTransaction().commit(); // Save everything.
        List<Object> newMsg = new LinkedList<Object>();
        newMsg.add("#REFRESH");     // TODO refresh to all users
        newMsg.add(App.getAllProducts());
        App.server.sendToAllClients(newMsg);
    }

    private static void changeParam(PreMadeProduct p, PreMadeProduct p2) {     //changes details
        p.setName(p2.getName());
        p.setPrice(p2.getPrice());
        p.setDiscount(p2.getDiscount());
        p.setImage(p2.getByteImage());
        p.setPriceBeforeDiscount(p2.getPriceBeforeDiscount());
        if(p.getType()== PreMadeProduct.ProductType.CUSTOM_CATALOG){
            p.setMainColor(p2.getMainColor());
        }
    }

    public static void orderArrived(Order order, Order.Status status){
        App.session.beginTransaction();
        App.session.evict(order);
        order.setDelivered(status);
        App.session.merge(order);
        App.session.flush();
        App.session.getTransaction().commit();
    }

    private static void pullProducts(List<Object> msg, ConnectionToClient client) throws IOException {       //func pulls products from server
        List<PreMadeProduct> products = App.getAllProducts();
        String commandToClient = msg.get(0).toString();
        List<Object> msgToClient = new LinkedList<Object>();
        msgToClient.add(commandToClient);
        msgToClient.add(products);
        client.sendToClient(msgToClient);
    }

    private static void pullStores(List<Object> msg, ConnectionToClient client) throws IOException {       //func pulls products from server
        List<Store> stores = App.getAllStores();
        String commandToClient = "#PULLSTORES";
        List<Object> msgToClient = new LinkedList<Object>();
        msgToClient.add(commandToClient);
        msgToClient.add(stores);
        client.sendToClient(msgToClient);
    }

    private void addProduct(LinkedList<Object> msg) throws IOException {
        App.session.beginTransaction();
        PreMadeProduct product = (PreMadeProduct) ((LinkedList<Object>) msg).get(1);
        App.session.save(product);   //saves and flushes to database
        App.session.flush();
        App.session.getTransaction().commit();
        List<Object> newMsg = new LinkedList<Object>();
        newMsg.add("#REFRESH");     // TODO refresh to all users
        newMsg.add(App.getAllProducts());
        App.server.sendToAllClients(newMsg);
    }

    private void updateConnected(User user,Boolean connected){
        App.session.beginTransaction();
        App.session.evict(user);       //evict current product details from database
        user.setConnected(connected);
        App.session.merge(user);           //merge into database with updated info
        App.session.flush();
        App.session.getTransaction().commit(); // Save everything.
    }

    private void loginServer(LinkedList<Object> msg,ConnectionToClient client) throws IOException {
        List<User> users = App.getAllUsers();
        for (User user : users) {
            if (user.getUserName().equals(msg.get(1)) && user.getPassword().equals(msg.get(2))) {
                if (!user.getFrozen()) {
                    synchronized (this) {
                        if (!user.getConnected()) {
                            updateConnected(user, true);
                            msg.remove(2);
                            msg.remove(1);
                            msg.add("#SUCCESS");
                            if (user instanceof Customer) {
                                msg.add("CUSTOMER");
                            } else if (user instanceof Employee) {
                                msg.add("EMPLOYEE");
                            }
                            msg.add(user);
                            client.sendToClient(msg);
                            return;
                        }else{
                            msg.clear();
                            msg.add("#LOGIN");
                            msg.add("ALREADYCONNECTED");
                            client.sendToClient(msg);
                            return;
                        }
                    }
                }else {
                    List<Object> newMsg = new LinkedList<Object>();
                    newMsg.add("#ERROR");
                    newMsg.add( "User was FROZEN by system Admin" );
                    newMsg.add("Frozen User");
                    client.sendToClient(newMsg);
                    return;
                }
            }
        }
        List<Object> newMsg = new LinkedList<Object>();
        newMsg.add("#ERROR");
        newMsg.add( "UserName or Password do not match");
        newMsg.add("Login Error");
        client.sendToClient(newMsg);
    }

    private void logoutServer(LinkedList<Object> msg, ConnectionToClient client){

        updateConnected((User) msg.get(1),false);
        msg.remove(1);
        msg.remove(0);
        msg.add("#LOGIN");
        msg.add("#SUCCESS");
        msg.add("GUEST");
        try {
            client.sendToClient(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    protected synchronized void clientDisconnected(ConnectionToClient client) { //is client disconnected
        // TODO Auto-generated method stub

        System.out.println("Client Disconnected.");
        super.clientDisconnected(client);
    }

    @Override
    protected void clientConnected(ConnectionToClient client) {     //is client connected
        super.clientConnected(client);
        System.out.println("Client connected: " + client.getInetAddress());
    }

    public static void main(String[] args) throws IOException {


        if (args.length != 1) {
            System.out.println("Required argument: <port>");
        } else {

            Server server = new Server(Integer.parseInt(args[0]));
            server.listen();
        }
    }
}

