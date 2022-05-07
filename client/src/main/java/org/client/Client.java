package org.client;

import org.client.ocsf.AbstractClient;
import org.entities.Product;

import java.io.IOException;
import java.util.LinkedList;

public class Client extends AbstractClient {

    private StoreSkeleton storeSkeleton;

    protected static LinkedList<Product> products = new LinkedList<Product>();//(LinkedList<Product>) Catalog.getProducts();

    private Controller controller;

    public Client(String localhost, int i) {
        super(localhost, i);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public static void main(String[] args) {


    }

    // TODO Maybe delete
    private static Client client = null;

    public static Client getClient() {
        if (client == null) {
            client = new Client("localhost", 3000);
        }
        return client;
    }

    @Override
    protected void handleMessageFromServer(Object msg) {     //function handles message from server
        try {
            switch (((LinkedList<Object>) msg).get(0).toString()) {       //switch with all command options sent between client and server
                case "#PULLCATALOG" -> {
                    pushToCatalog(msg);
                }         //function gets all data from server to display to client
            }
        } catch (Exception e) {
            System.out.println("Client Error");
            e.getStackTrace();
        }

    }

    private void pushToCatalog(Object msg) throws IOException { // takes data received and sends to display function
        products = (LinkedList<Product>) ((LinkedList<Object>) msg).get(1);
        CatalogController catalogController = (CatalogController) controller;
        catalogController.pullProductsToClient();       //calls static function in client for display
    }


}
