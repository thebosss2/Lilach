package org.client;

import org.client.ocsf.AbstractClient;
import org.client.CatalogController;
import org.entities.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Client extends AbstractClient {

    private StoreSkeleton storeSkeleton;

    protected static LinkedList<Product> products = new LinkedList<Product>();//(LinkedList<Product>) Catalog.getProducts();

    private Controller controller;

    public Client(String localhost, int i) {
        super(localhost,i);
    }

    public void setController(Controller controller){
        this.controller = controller;
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
        try{
            switch(((LinkedList<Object>) msg).get(0).toString()){
                case "#PULLCATALOG"-> {pushToCatalog(msg);}
            }
        }catch (Exception e){
            System.out.println("Hello client error");
            e.getStackTrace();
        }

    }

    private void pushToCatalog(Object msg) throws IOException {
        products = (LinkedList<Product>)((LinkedList<Object>)msg).get(1);
        CatalogController catalogController = (CatalogController) controller;
        catalogController.pullProductsToClient();
    }


}
