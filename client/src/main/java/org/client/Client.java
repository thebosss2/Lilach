package org.client;

import org.client.ocsf.AbstractClient;

public class Client extends AbstractClient {
    public Client(String localhost, int i) {
        super(localhost,i);
    }

    public static void main(String[] args) {


    }
    private static Client client = null;

    public static Client getClient() {
        if (client == null) {
            client = new Client("localhost", 3000);
        }
        return client;
    }

    @Override
    protected void handleMessageFromServer(Object msg) {

        System.out.println(" Client Received Message: " + msg.toString());

    }
}
