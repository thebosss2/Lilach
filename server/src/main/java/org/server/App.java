package org.server;
import org.entities.*;
//import org.hibernate.SessionFactory;

import javax.persistence.*;
import java.io.IOException;
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

    private static Session session;

    private static SessionFactory getSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
        // Add ALL of your entities here. You can also try adding a whole package.
        configuration.addAnnotatedClass(Product.class);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

        return configuration.buildSessionFactory(serviceRegistry);
    }
    private static void generateProducts() throws Exception {
        Random random = new Random();
        double price;
        for (int i = 0; i < 5; i++) {
            String img1 = "\\src\\main\\resources\\Images" + i + ".jpg";
            Product p1 = new Product("rakefet", img1, price = random.nextInt(1000), (price - random.nextInt(500)));

            session.save(p1);
            session.flush();
        }
    }
    static List<Product> getAllProducts() throws IOException {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        query.from(Product.class);
        List<Product> data = session.createQuery(query).getResultList();
        return data;
    }


    private static Server server;
    public static void main( String[] args ) throws IOException
    {
        try {
            SessionFactory sessionFactory = getSessionFactory();
            session = sessionFactory.openSession();
            session.beginTransaction();

            generateProducts();
            session.getTransaction().commit(); // Save everything.
          
            server = new Server(3000);
            server.listen();
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
