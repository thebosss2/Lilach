package org.server;

import org.entities.CustomMadeProduct;
import org.entities.PreMadeProduct;
import org.entities.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Hello world!
 */
public class App {

    public static Session session;// encapsulation make public function so this can be private

    private static SessionFactory getSessionFactory() throws HibernateException {       //creates session factory for database use
        Configuration configuration = new Configuration();
        // Add ALL of your entities here. You can also try adding a whole package.
        configuration.addAnnotatedClass(Product.class);
        configuration.addAnnotatedClass(PreMadeProduct.class);
        configuration.addAnnotatedClass(CustomMadeProduct.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();        //pull session factory config from hibernate properties
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static void generateProducts() throws Exception {       //generates new products
        Random random = new Random();
        int price;
        for (int i = 0; i < 5; i++) {
            var img1 = loadImageFromResources(String.format("Flower%s.jpg", i));
            PreMadeProduct p1 = new PreMadeProduct("Flower" + i, img1, price = random.nextInt(1000), (price - random.nextInt(500)));

            session.save(p1);   //saves and flushes to database
            session.flush();
        }
    }
    private static void generateStores() throws Exception {       //generates new products
        Random random = new Random();
        int price;
        for (int i = 0; i < 5; i++) {
            var img1 = loadImageFromResources(String.format("Flower%s.jpg", i));
            PreMadeProduct p1 = new PreMadeProduct("Flower" + i, img1, price = random.nextInt(1000), (price - random.nextInt(500)));

            session.save(p1);   //saves and flushes to database
            session.flush();
        }
    }

    ///TODO make generic func--------------------------------------------------------------------------------------------------------------
    static List<PreMadeProduct> getAllProducts() throws IOException {      //pulls all products from database
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<PreMadeProduct> query = builder.createQuery(PreMadeProduct.class);
        query.from(PreMadeProduct.class);
        List<PreMadeProduct> data = session.createQuery(query).getResultList();
        LinkedList<PreMadeProduct> list = new LinkedList<PreMadeProduct>();
        for (PreMadeProduct product : data) {     //converts arraylist to linkedlist
            list.add(product);
        }
        return list;
    }

    public static byte[] loadImageFromResources(String imageName) throws IOException {
        var stream = App.class.getClassLoader().getResourceAsStream(String.format("Images/%s", imageName));

        return Objects.requireNonNull(stream).readAllBytes();
    }

    private static Server server;

    public static void main(String[] args) throws IOException {
        try {

            SessionFactory sessionFactory = getSessionFactory();        //calls and creates session factory
            session = sessionFactory.openSession(); //opens session
            session.beginTransaction();       //transaction for generation
            generateProducts();             //generate
            generateStores();
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
                while (!server.isClosed()) ;
                session.close();
                session.getSessionFactory().close();
            }
        }

    }
}
