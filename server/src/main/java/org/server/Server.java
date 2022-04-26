package org.server;

import java.util.Random;

import org.entities.Product;
import javax.persistence.*;


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
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {


        try {

                switch(((LinkedList<Object>) msg).get(0).toString()){
                    case "#PULLCATALOG" -> {pullProducts(((LinkedList<Object>) msg) ,client);}
            }

           /*    pullProducts(((LinkedList<Object>) msg) ,client);*/
        }
        catch (IOException e) {
            e.printStackTrace();
        }





    }

    private static void pullProducts(List<Object> msg, ConnectionToClient client) throws IOException{

//        List<Product> products = null ;//= getAllProducts();
//        msg.add(products);
//        System.out.println(client.getId());
//        System.out.println(client.getInetAddress());
//        System.out.println(client.getName());
//        client.sendToClient(msg);
        
        List<Product> products = App.getAllProducts();
        String commandToClient = "#PULLCATALOG";
        List<Object> msgToClient = new LinkedList<Object>();
        msgToClient.add(commandToClient);
        msgToClient.add(products);
        client.sendToClient(msgToClient);

    }


    @Override
    protected synchronized void clientDisconnected(ConnectionToClient client) {
        // TODO Auto-generated method stub

        System.out.println("Client Disconnected.");
        super.clientDisconnected(client);
    }
    @Override
    protected void clientConnected(ConnectionToClient client) {
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

