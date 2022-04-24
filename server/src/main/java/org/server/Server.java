package org.server;

import org.server.ocsf.AbstractServer;
import org.server.ocsf.ConnectionToClient;

import java.io.IOException;

public class Server extends AbstractServer {

    public Server(int port) {
        super(port);
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        System.out.println("Received Message: " + msg.toString());
        try {
            client.sendToClient("server "+msg.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
