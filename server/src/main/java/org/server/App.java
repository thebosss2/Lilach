package org.server;

import org.entities.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.IOException;
import java.util.*;

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
        configuration.addAnnotatedClass(Guest.class);
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Customer.class);
        configuration.addAnnotatedClass(Employee.class);
        configuration.addAnnotatedClass(Complaint.class);
        configuration.addAnnotatedClass(Order.class);
        configuration.addAnnotatedClass(Store.class);


        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();        //pull session factory config from hibernate properties
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static void generateProducts() throws Exception {       //generates new products
        Random random = new Random();
        int price;
        for (int i = 0; i < 5; i++) {
            var img1 = loadImageFromResources(String.format("Flower%s.jpg", i));
            PreMadeProduct p1 = new PreMadeProduct("Flower" + i, img1, price = random.nextInt(1000), (price - random.nextInt(500)));
            Customer cust = new Customer("name","user","pass","mail",new Date(),"credit", Customer.AccountType.MEMBERSHIP);
            Complaint c = new Complaint(cust ,new Date(),"bad bad bad", Complaint.Topic.BAD_SERVICE);
            session.save(cust);
            session.flush();
            session.save(c);
            session.flush();
            session.save(p1);   //saves and flushes to database
            session.flush();
        }
        Employee emp = new Employee("Sagi","Sagi","Sagi","Sagi",new Date(),Employee.Role.STORE_EMPLOYEE);
        session.save(emp);
        session.flush();
    }

    private static void generateStores() throws Exception {       //generates new products
        Random random = new Random();
        int price;
        for (int i = 0; i < 5; i++) {
            Store store = new Store("store" + i, "address" + i);
            session.save(store);   //saves and flushes to database
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

    ///TODO make generic func--------------------------------------------------------------------------------------------------------------
    static List<Store> getAllStores() throws IOException {      //pulls all stores from database
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Store> query = builder.createQuery(Store.class);
        query.from(Store.class);
        List<Store> data = session.createQuery(query).getResultList();
        LinkedList<Store> list = new LinkedList<Store>();
        for (Store store : data) {     //converts arraylist to linkedlist
            list.add(store);
        }
        return list;
    }
    static List<User> getAllUsers() throws IOException {      //pulls all products from database
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Customer> customerQuery = builder.createQuery(Customer.class);
        customerQuery.from(Customer.class);
        List<Customer> customers = session.createQuery(customerQuery).getResultList();
        LinkedList<User> list = new LinkedList<>(customers);
        CriteriaQuery<Employee> employeeQuery = builder.createQuery(Employee.class);
        employeeQuery.from(Employee.class);
        List<Employee> employees = session.createQuery(employeeQuery).getResultList();
        list.addAll(employees);
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
