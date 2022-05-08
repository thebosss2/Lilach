package org.server;
import org.entities.*;
//import org.hibernate.SessionFactory;

import javax.persistence.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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
import org.server.ocsf.ConnectionToClient;

/**
 * Hello world!
 *
 */
public class App
{

   public static Session session;// encapsulation make public function so this can be private

    private static SessionFactory getSessionFactory() throws HibernateException {       //creates session factory for database use
        Configuration configuration = new Configuration();
        // Add ALL of your entities here. You can also try adding a whole package.
        //configuration.addPackage(org.entities);
        configuration.addAnnotatedClass(Product.class);
        configuration.addAnnotatedClass(PreMadeProduct.class);
        configuration.addAnnotatedClass(CustomMadeProduct.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();        //pull session factory config from hibernate properties
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static void generateProducts() throws Exception {       //generates new products
        Random random = new Random();
        int price;
        List<PreMadeProduct> list = new LinkedList<PreMadeProduct>();
        for (int i = 0; i < 5; i++) {
            String img1 = "C:\\Users\\Itai\\Desktop\\Dropbox\\Homework\\SoftwareEngineering\\Lilach\\client\\src\\main\\resources\\Images\\Flower" + i + ".jpg";
            PreMadeProduct p1 = new PreMadeProduct("Flower" + i, img1, price = random.nextInt(1000), (price - random.nextInt(500)));
            list.add(p1);
            session.save(p1);   //saves and flushes to database
            session.flush();
        }
        CustomMadeProduct p = new CustomMadeProduct(list,900,"C:\\\\Users\\\\Itai\\\\Desktop\\\\Dropbox\\\\Homework\\\\SoftwareEngineering\\\\Lilach\\\\client\\\\src\\\\main\\\\resources\\\\Images\\\\Flower0.jpg");
        CustomMadeProduct p2 = new CustomMadeProduct(list,900,"C:\\\\Users\\\\Itai\\\\Desktop\\\\Dropbox\\\\Homework\\\\SoftwareEngineering\\\\Lilach\\\\client\\\\src\\\\main\\\\resources\\\\Images\\\\Flower0.jpg");

        session.save(p);   //saves and flushes to database
        session.flush();
        session.save(p2);
        session.flush();
    }


    ///TODO make generic func--------------------------------------------------------------------------------------------------------------
    static List<PreMadeProduct> getAllProducts() throws IOException {      //pulls all products from database
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<PreMadeProduct> query = builder.createQuery(PreMadeProduct.class);
        query.from(PreMadeProduct.class);
        List<PreMadeProduct> data =  session.createQuery(query).getResultList();
        LinkedList<PreMadeProduct> list = new LinkedList<PreMadeProduct>();
        for(PreMadeProduct product: data){     //converts arraylist to linkedlist
            list.add(product);
        }
        return list;
    }


    private static Server server;
    public static void main( String[] args ) throws IOException
    {
        try {

            SessionFactory sessionFactory = getSessionFactory();        //calls and creates session factory
            session = sessionFactory.openSession(); //opens session
            session.beginTransaction();       //transaction for generation
            generateProducts();             //generate
            session.getTransaction().commit(); // Save everything.

            server = new Server(3000);      //builds server
            server.listen();                    //listens to client
        } catch (Exception e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            System.err.println("An error occured, changes have been rolled back.");
            e.printStackTrace();
        } finally {
            if (session != null) {
                while(!server.isClosed());
                session.close();
                session.getSessionFactory().close();
            }
        }

    }
}
