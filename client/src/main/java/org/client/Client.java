package org.client;

import org.client.ocsf.AbstractClient;
import org.client.CatalogController;
import java.io.IOException;
import java.util.LinkedList;

public class Client extends AbstractClient {

    private StoreSkeleton storeSkeleton;

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
    protected void handleMessageFromServer(Object msg){
        msg = ((LinkedList<Object>) msg);
        switch(((LinkedList<Object>) msg).getFirst().toString()){
            case "#PULLCATALOG"-> CatalogController.pullProductsToClient(msg);
        }


        /*System.out.println(" Client Received Message: " + msg.toString());*/

    }


}
