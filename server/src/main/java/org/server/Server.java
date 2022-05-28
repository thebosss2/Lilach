package org.server;

import org.entities.*;
import org.server.ocsf.AbstractServer;
import org.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

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


    private void deleteOrder(LinkedList<Object> msg, ConnectionToClient client) {
        int id = (int) msg.get(1);
        Order order = App.session.find(Order.class, id);
        App.session.beginTransaction();
        App.session.evict(order);       //evict current product details from database
        order.setDelivered(Order.Status.CANCELED);
        App.session.merge(order);           //merge into database with updated info
        App.session.flush();
        App.session.getTransaction().commit(); // Save everything.
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
            updateBalance(complaint.getCustomer(),complaint.getCustomer().getBalance () + (int) msg.get(3));
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


    private void signUpServer(LinkedList<Object> msg, ConnectionToClient client) {
        addNewInstance((Customer) msg.get(1));
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
        newMsg.add("#USER_NOT_EXISTS");
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
    }

    private static void changeParam(PreMadeProduct p, PreMadeProduct p2) {     //changes details
        p.setName(p2.getName());
        p.setPrice(p2.getPrice());
        p.setImage(p2.getByteImage());
        p.setPriceBeforeDiscount(p2.getPriceBeforeDiscount());
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

    private void addProduct(LinkedList<Object> msg) {
        App.session.beginTransaction();
        PreMadeProduct product = (PreMadeProduct) ((LinkedList<Object>) msg).get(1);
        App.session.save(product);   //saves and flushes to database
        App.session.flush();
        App.session.getTransaction().commit();
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
        for(User user : users){
            if(user.getUserName().equals(msg.get(1)) && user.getPassword().equals(msg.get(2))){
                synchronized (this){
                    if(!user.getConnected()){
                        updateConnected(user,true);
                        msg.remove(2);
                        msg.remove(1);
                        msg.add("#SUCCESS");
                        if(user instanceof Customer) {
                            msg.add("CUSTOMER");
                        }else if (user instanceof Employee) {
                            msg.add("EMPLOYEE");
                        }
                        msg.add(user);
                        client.sendToClient(msg);
                    }
                }
            }
        }
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

