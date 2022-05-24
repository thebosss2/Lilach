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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

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
/*        configuration.addAnnotatedClass(Report.class);
        configuration.addAnnotatedClass(Cart.class);*/


        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();        //pull session factory config from hibernate properties
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static void generateProducts() throws Exception {       //generates new products
        Random random = new Random();
        int price;
        Store store = new Store("Chain", "address");
        session.save(store);   //saves and flushes to database
        session.flush();
        for (int i = 0; i < 5; i++) {
            var img1 = loadImageFromResources(String.format("Flower%s.jpg", i));
            PreMadeProduct p1 = new PreMadeProduct("Flower" + i, img1, price = random.nextInt(1000),"asdasd", (price - random.nextInt(500)));
            Customer cust = new Customer("23465", "name","user","pass","mail","56346","credit", Customer.AccountType.MEMBERSHIP,store);
            Complaint c = new Complaint(cust ,new Date(),"bad bad bad", Complaint.Topic.BAD_SERVICE);
            session.save(cust);
            session.flush();
            session.save(c);
            session.flush();
            session.save(p1);   //saves and flushes to database
            session.flush();
        }
        Employee emp = new Employee("4563456","Sagi","Sagi","Sagi","Sagi","4563456",Employee.Role.STORE_EMPLOYEE);
        session.save(emp);
        session.flush();
        Employee man = new Employee("345634576","Itai","Itai","Itai","Itai","12341234",Employee.Role.STORE_MANAGER);
        session.save(man);
        session.flush();
        Employee Ad = new Employee("4563456","Gal ","Gal","Gal","Sagi","4563456",Employee.Role.ADMIN);
        session.save(Ad);
        session.flush();
        Employee Ce = new Employee("4563456","Tahel","Tahel","Tahel","Sagi","4563456",Employee.Role.CEO);
        session.save(Ce);
        session.flush();
        Employee Serv = new Employee("4563456","Yahav ","Yahav","Yahav","Sagi","4563456",Employee.Role.CUSTOMER_SERVICE);
        session.save(Serv);
        session.flush();

        Customer cust = new Customer("23465", "Sagii","Sagii","Sagii","sagiman14@gmail.com","56346","credit", Customer.AccountType.MEMBERSHIP,store);
        Date date = new Date();
        date.setYear(date.getYear() - 2);
        cust.setMemberShipExpireTODELETE(date);
        cust.setBalance(150);
        session.save(cust);
        session.flush();

        Order ord = new Order(null,null,cust,456,new Date(122,04,24,11,49),"12","123123"," ");
        session.save(ord);
        session.flush();

        Complaint c = new Complaint(cust ,new Date(122,04,5) ,"I WANT MONEY", Complaint.Topic.PAYMENT);
        session.save(c);
        session.flush();
    }

    private static void generateStores() throws Exception {       //generates new products
        for (int i = 0; i < 5; i++) {
            Store store = new Store("store" + i, "address" + i);
            session.save(store);   //saves and flushes to database
            session.flush();
        }

    }

    private static void generateBaseCustomMadeProduct() throws Exception {       //generates new base products
        Random random = new Random();
        int price;
        int num_products = 10; //change according to the real
        String[] colors = {"Red","Pink","Yellow","White","Pink","White","White","Green","Blue","Green","Green"};
        for (int i = 0; i <= num_products; i++) {
            var img = loadImageFromResources(String.format("base%s.jpg", i));
            PreMadeProduct p = new PreMadeProduct("Base Product " + i, img, price = random.nextInt(100),random.nextInt(50),colors[i]);
            session.save(p);   //saves and flushes to database
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
        LinkedList<User> list = new LinkedList<>();
        list.addAll(getAllCustomers());
        list.addAll(getAllEmployees());
        return list;
    }

    static List<Customer> getAllCustomers() throws IOException{
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Customer> customerQuery = builder.createQuery(Customer.class);
        customerQuery.from(Customer.class);
        List<Customer> customers = session.createQuery(customerQuery).getResultList();
        return new LinkedList<>(customers);
    }

    static List<Employee> getAllEmployees() throws IOException{
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> customerQuery = builder.createQuery(Employee.class);
        customerQuery.from(Employee.class);
        List<Employee> employees = session.createQuery(customerQuery).getResultList();
        return new LinkedList<>(employees);
    }

    static List<Order> getAllOrders() throws IOException{
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Order> orderQuery = builder.createQuery(Order.class);
        orderQuery.from(Order.class);
        List<Order> orders = session.createQuery(orderQuery).getResultList();
        return new LinkedList<Order>(orders);
    }

    static List<Complaint> getAllOpenComplaints() throws IOException{
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Complaint> customerQuery = builder.createQuery(Complaint.class);
        customerQuery.from(Complaint.class);
        List<Complaint> complaints = session.createQuery(customerQuery).getResultList();
        complaints.removeIf(complaint -> !complaint.getStatus());
        return complaints;
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
            generateBaseCustomMadeProduct();
            session.getTransaction().commit(); // Save everything.

            ScheduleMailing.main(null);

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
