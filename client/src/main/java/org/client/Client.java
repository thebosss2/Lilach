package org.client;

import org.client.ocsf.AbstractClient;
import org.entities.*;

import java.io.IOException;
import java.util.LinkedList;

public class Client extends AbstractClient {

    private StoreSkeleton storeSkeleton;

    protected static LinkedList<PreMadeProduct> products = new LinkedList<PreMadeProduct>();//(LinkedList<Product>) Catalog.getProducts();

    private Controller controller;

    public Cart cart= new Cart();
    protected Guest user;

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
                case "#PULLCATALOG" -> pushToCatalog(msg);//function gets all data from server to display to client
                case "#LOGIN" -> loginClient((LinkedList<Object>) msg);
                case "#PULLSTORES" -> pushStores(msg);//function gets all data from server to display to client
            }
        } catch (Exception e) {
            System.out.println("Client Error");
            e.getStackTrace();
        }
    }

    private void pushToCatalog(Object msg) throws IOException { // takes data received and sends to display function
        products = (LinkedList<PreMadeProduct>) ((LinkedList<Object>) msg).get(1);
        CatalogController catalogController = (CatalogController) controller;
        catalogController.pullProductsToClient();       //calls static function in client for display
    }

    private void loginClient(LinkedList<Object> msg) {
        if (msg.get(1).equals("#SUCCESS")) {
            switch (msg.get(3).toString()) {
                case "CUSTOMER" -> this.user = (Customer) msg.get(2);
                case "EMPLOYEE" -> this.user = (Employee) msg.get(2);
                case "GUEST" -> this.user = new Guest();
            }
        }
    }

    private void pushStores(Object msg) throws IOException { // takes data received and sends to display function
        CreateOrderController createOrderController;
        CEOReportController ceoReportController;

        if (controller instanceof CreateOrderController) {
            createOrderController = (CreateOrderController)controller;
            createOrderController.pullStoresToClient((LinkedList<Store>) ((LinkedList<Object>) msg).get(1));       //calls static function in client for display
        }
        else if(controller instanceof CEOReportController) {
            ceoReportController = (CEOReportController) controller;
            ceoReportController.pullStoresToClient((LinkedList<Store>) ((LinkedList<Object>) msg).get(1));       //calls static function in client for display
        }


    }



}
