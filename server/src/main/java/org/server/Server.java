package org.server;

import java.util.Random;

import org.entities.PreMadeProduct;
import org.entities.Product;
import javax.persistence.*;
import org.server.App;

import org.server.ocsf.AbstractServer;
import org.server.ocsf.ConnectionToClient;


import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.LinkedList;

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
                switch(((LinkedList<Object>) msg).get(0).toString()){   //switch to see what client wants from server
                    case "#PULLCATALOG" -> {pullProducts(((LinkedList<Object>) msg) ,client);}  //display updated catalog version
                    case "#SAVE" -> {updateProduct((LinkedList<Object>)msg);}           //save change to product details

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void updateProduct(Object msg)throws IOException{        //update product details func
        App.session.beginTransaction();
        PreMadeProduct productBefore = (PreMadeProduct) ((LinkedList<Object>)msg).get(1);
        PreMadeProduct productAfter = (PreMadeProduct) ((LinkedList<Object>)msg).get(2);

        App.session.evict(productBefore);       //evict current product details from database
        changeParam(productBefore, productAfter);   //func changes product to updates details
        App.session.merge(productBefore);           //merge into database with updated info
        App.session.flush();
        App.session.getTransaction().commit(); // Save everything.
    }
    //TODO check image update
    private static void changeParam(PreMadeProduct p, PreMadeProduct p2){     //changes details
        p.setName(p2.getName());
        p.setPrice(p2.getPrice());
        p.setPriceBeforeDiscount(p2.getPriceBeforeDiscount());
    }



    private static void pullProducts(List<Object> msg, ConnectionToClient client) throws IOException{       //func pulls products from server
        List<PreMadeProduct> products = App.getAllProducts();
        String commandToClient = "#PULLCATALOG";
        List<Object> msgToClient = new LinkedList<Object>();
        msgToClient.add(commandToClient);
        msgToClient.add(products);
        client.sendToClient(msgToClient);
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

