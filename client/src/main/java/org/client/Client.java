package org.client;

import org.client.ocsf.AbstractClient;
import org.client.CatalogController;
import org.entities.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Client extends AbstractClient {

    private StoreSkeleton storeSkeleton;

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
        System.out.println("Hello from client");;


        //msg = ((LinkedList<Object>) msg);
        try{
            switch(((ArrayList<Object>) msg).get(0).toString()){
                case "#PULLCATALOG"-> {pushToCatalog(msg);}
            }
        }catch (Exception e){
            System.out.println("Hello client error");
            e.getStackTrace();
        }




        /*System.out.println(" Client Received Message: " + msg.toString());*/

    }

    private void pushToCatalog(Object msg) throws IOException {
        System.out.println("Hello from push1");
        CatalogController catalogController = (CatalogController) controller;
        System.out.println("Hello from push2");
        catalogController.pullProductsToClient((ArrayList<Product>)(((ArrayList<Object>)msg).get(1)));
        System.out.println("Hello from push3");
    }


}
