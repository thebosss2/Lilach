package org.server;

import org.entities.Product;
import javax.persistence.*;
import org.server.ocsf.AbstractServer;
import org.server.ocsf.ConnectionToClient;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

public class Server extends AbstractServer {

    public Server(int port) {
        super(port);
    }

    private static Session session;

    private static SessionFactory getSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
        // Add ALL of your entities here. You can also try adding a whole package.
        configuration.addAnnotatedClass(Product.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    /**
     * Msg contains at least a command (string) for the switch to handle.
     */
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {

        System.out.println("Hello server");
        try {
            /*switch(((LinkedList<Object>) msg).getFirst().toString()){
                case "#PULLCATALOG"->pullProducts(((ArrayList<Object>) msg) ,client);
            }*/
            System.out.println("Hello there server");
            pullProducts(((ArrayList<Object>) msg) ,client);
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }



    private static void pullProducts(List<Object> msg, ConnectionToClient client) throws IOException{
        System.out.println("Sending products");
        List<Product> products = null ;//= getAllProducts();
        msg.add(products);
        System.out.println(client.getId());
        System.out.println(client.getInetAddress());
        System.out.println(client.getName());
        client.sendToClient(msg);
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

    private static List<Product> getAllProducts() throws IOException {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        query.from(Product.class);
        List<Product> data = session.createQuery(query).getResultList();
        return data;
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
