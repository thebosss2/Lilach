package org.server;

import org.entities.*;
import org.server.ocsf.AbstractServer;
import org.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.CheckedOutputStream;

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
                case "#SAVE" -> updateProduct((LinkedList<Object>) msg);           //save change to product details
                case "#ADD" -> addProduct((LinkedList<Object>) msg);           // add product to the DB
                case "#LOGIN" -> loginServer((LinkedList<Object>)msg,client);
                case "#SIGNUP_AUTHENTICATION" -> authinticateUser((LinkedList<Object>) msg, client);
                case "#SIGNUP" -> signUpServer(((LinkedList<Object>)msg),client);
                case "#PULLSTORES" -> pullStores(((LinkedList<Object>) msg), client);  //display updated catalog version
                case "#SAVEORDER" -> saveOrderServer(((LinkedList<Object>)msg),client);
                case "#LOGOUT" -> logoutServer((LinkedList<Object>) msg, client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void signUpServer(LinkedList<Object> msg, ConnectionToClient client) {
        Customer customer = (Customer) msg.get(1);
        App.session.beginTransaction();
        App.session.save(customer);
        App.session.flush();
        App.session.getTransaction().commit();
    }

    private void saveOrderServer(LinkedList<Object> msg, ConnectionToClient client) {
        Order order = (Order) msg.get(1);
        App.session.beginTransaction();
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
            if(user.getUserName().equals(msg.get(1).toString())){
                newMsg.add("#USER_EXISTS");
                client.sendToClient(newMsg);
                return;
            }
        }
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

    private static void pullProducts(List<Object> msg, ConnectionToClient client) throws IOException {       //func pulls products from server
        List<PreMadeProduct> products = App.getAllProducts();
        String commandToClient = "#PULLCATALOG";
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

