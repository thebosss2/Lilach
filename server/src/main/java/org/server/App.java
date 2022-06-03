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


    private static void generateEntities() throws Exception {       //generates all entities
        //--------------------STORES-----------------------------------------------------
        List<Store> stores = new LinkedList<Store>();
        stores=generateStores();
        //--------------------END-OF-STORES----------------------------------------------

        //--------------------CUSTOMERS-AND-COMPLAINTS-----------------------------------
        List<Customer> customers= new LinkedList<Customer>();
        customers=generateCustomers(stores);

        List<Complaint> complaints = new LinkedList<Complaint>();
        complaints=generateComplaints(stores,customers);
        //--------------------END-OF-CUSTOMERS-AND-COMPLAINTS----------------------------

        //--------------------EMPLOYEES--------------------------------------------------
        List<Employee> employees = new LinkedList<Employee>();
        employees=generateEmployees(stores);
        //--------------------END-OF-EMPLOYEES-------------------------------------------

        //--------------------FLOWERS----------------------------------------------------
        List<PreMadeProduct> products = new LinkedList<PreMadeProduct>();
        products = generateProducts();
        //--------------------END-OF-FLOWERS---------------------------------------------

        //--------------------EXAMPLE-FOR-EMAIL-DELIVERY---------------------------------
        Customer cust = new Customer("23465", "Sagii","Sagii","Sagii","sagiman14@gmail.com","56346","credit", Customer.AccountType.MEMBERSHIP,stores.get(stores.size()-1));
        Date date = new Date();
        date.setYear(date.getYear() - 2);
        cust.setMemberShipExpireTODELETE(date);
        cust.setBalance(150);
        session.save(cust);
        session.flush();
     
        Complaint c = new Complaint(cust ,new Date(122,04,5) ,"I WANT MONEY", Complaint.Topic.PAYMENT,stores.get(stores.size()-1));
        session.save(c);
        session.flush();
        //--------------------END-OF-EXAMPLE-FOR-EMAIL-DELIVERY--------------------------
    }

    private static List<Store> generateStores() throws Exception {       //generates new products
        List<Store> stores = new LinkedList<Store>();
        String[] storeNames = new String[]{"Lilac Haifa", "Lilac Tel-Aviv", "Lilac Be'er Sheva", "Lilac Rehovot", "Lilac Jerusalem", "Lilac Eilat"};
        String[] storeAddress = new String[]{"Grand Canyon Haifa - Derech Simha Golan 54", "Azrieli Mall - Derech Menachem Begin 132", "Big Beer Sheva - Derekh Hebron 21",
                "Rehovot Mall - Bilu St 2", "Malcha Mall - Derech Agudat Sport Beitar 1", "Kanyon ha-Ir - HaMelacha St 12"};
        for(int i=0;i<storeNames.length;i++){
            Store store = new Store(storeNames[i], storeAddress[i]);
            stores.add(store);
            session.save(store);   //saves and flushes to database
            session.flush();
        }
        Store chain = new Store("Chain", "address");
        stores.add(chain);
        session.save(chain);   //saves and flushes to database
        session.flush();
        return stores;
    }
    private static List<PreMadeProduct> generateProducts() throws Exception {       //generates new products
        Random random = new Random();
        List<PreMadeProduct> products = new LinkedList<PreMadeProduct>();
        String[] flowerNames = new String[]{"SunFlower","Calanit","Shibolet","Rose","Rakefet","Lilach","Lily","Tulip","Pickachu","Charmander","Thanos","PushPush","Runlater","Clean Install","Orchid"};
        for(int i=0;i< flowerNames.length;i++){
            var img = loadImageFromResources(String.format("Flower%s.jpg",i));
            PreMadeProduct p = new PreMadeProduct(flowerNames[i], img, random.nextInt(30)+1,"this is a " + flowerNames[i] +" Flower", 0,false);
            products.add(p);
            session.save(p);   //saves and flushes to database
            session.flush();
        }
        return products;
    }
    private static List<Customer> generateCustomers(List<Store> s) throws Exception {       //generates new products
        List<Customer> customers = new LinkedList<Customer>();
        String[] customerId = new String[]{"123456789","234567891","345678912","456789123","567891234","678912345","789123456","891234567"};
        String[] customerNames = new String[]{"user","Ash Ketchum", "Obi-Wan Kenobi", "Cynthia", "Amity Blight", "Mariette Cheng", "Matt", "Augustus Porter"};
        String[] customerUserNames = new String[]{"user","pokemon_master", "Jedi_master", "Cynthi", "Cotton_Candy", "Ladybug", "Wii1", "TOH"};
        String[] customerEmails = new String[]{"user@gmail.com","Ash@gmail.com","Obi-Wan@gmail.com","Cynthia@gmail.com","Amity@gmail.com","Mariette@gmail.com","Matt@gmail.com", "Augustus@gmail.com"};
        int storeN;
        for(int i=0;i< customerNames.length;i++) {
            if (i < s.size())
                storeN = i;
            else
                storeN = s.size()-1;
            Customer cust = new Customer(customerId[i], customerNames[i], customerUserNames[i], "pass", customerEmails[i], "0522245484", "5434456321581234", Customer.AccountType.MEMBERSHIP, s.get(storeN));
            cust.setBalance(50*(new Random().nextInt(10)));
            customers.add(cust);
            session.save(cust);
            session.flush();
        }
        return customers;
    }
    private static List<Employee> generateEmployees(List<Store> s) throws Exception {       //generates new products
        List<Employee> employees = new LinkedList<Employee>();
        String[] employeeId = new String[]{"987654321","876543219","765432198","654321987","543219876","432198765","321987654","219876543","334574567","345234556",
        "534563456","345634564","332141234","567856786","653294462","870767907","567944332"};
        String[] employeeNames = new String[]{"Itai","Sagi","Gal","Tahel","Yahav","May", "Lillian", "Nellie", "Chantelle", "Tia", "Christine", "Hayley", "Alice", "Wanda", "Tara", "Rose", "Ruby"};
        String[] employeeUserNames = new String[]{"itai","sagi","gal","tahel","yahav","may", "lilly", "nella", "chantelle", "tia", "christa", "tookYourChildren", "Halle", "Ali", "Scarlet", "Tara", "Rose"};
        String[] employeeEmails = new String[]{"Itai@gmail.com", "Sagi@gmail.com", "Gal@gmail.com", "Tahel@gmail.com", "Yahav@gmail.com", "May@gmail.com",
        "Lillian@gmail.com", "Nellie@gmail.com", "Chantelle@gmail.com", "Tia@gmail.com", "Christa@gmail.com", "tookYourChildren@gmail.com", "Halle@gmail.com", "Ali@gmail.com", "Scarlet@gmail.com", "Tara@gmail.com", "Rose@gmail.com"};
        int storeN;
        for(int i=0;i< employeeNames.length;i++){
            storeN=i%7;
            Employee emp = new Employee(employeeId[i], employeeNames[i], employeeUserNames[i], employeeUserNames[i], employeeEmails[i], "052224548"+i, Employee.Role.values()[(i%2==0)?0:2], s.get(storeN));
            if(emp.getRole()== Employee.Role.STORE_EMPLOYEE)
                emp.getStore().addEmployees(emp);
            else if(emp.getStore().getStoreManager()==null)
                   emp.getStore().setStoreManager(emp);
            employees.add(emp);
            session.save(emp);
            session.flush();
        }


        Employee cService = new Employee("465364524", "Karen", "karen", "karen", "KarenAnakin@gmail.com", "0522245342", Employee.Role.values()[1], s.get(s.size()-1));
        employees.add(cService);
        session.save(cService);
        session.flush();
        Employee ceo = new Employee("345623411", "TonyStark", "ironman", "ironman", "TonyStark@gmail.com", "0522245483", Employee.Role.values()[3], s.get(s.size()-1));
        employees.add(ceo);
        session.save(ceo);
        session.flush();
        Employee admin = new Employee("796079534", "Anakin Skywalker", "younglingSlayer", "younglingSlayer", "Anakin@gmail.com", "0522245483", Employee.Role.values()[4], s.get(s.size()-1));
        employees.add(admin);
        session.save(admin);
        session.flush();




        return employees;
    }
    private static List<Complaint> generateComplaints(List<Store> s,List<Customer> c) throws Exception {       //generates new products
        List<Complaint> complaints = new LinkedList<Complaint>();
        int storeN;
        String[] complaintsDiscription = new String[]{"Hello, a couple of days ago I went to your store in Haifa, and the receptionist Shlomit was being rude to me. \n Thanks.",
                "Dear customer support, my order has arrived 2 hours later then what I asked for and ruined the surprise party.",
                "Hello, I ordered 2 tulips but got only 1. I'd like to get refunded for that.",
                "Dear Customer Support, I tried to buy with my visa and it didn't work, and then after multiple tries it charged me twice.",
                "Hello there, I ordered from your chain, and didn't receive what I wanted.",
                "Hello there, I ordered from your chain, and didn't receive what I desired.",
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb",
                "cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc",
                "dddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd",
                "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",
                "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff",
                "gggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg",
                "hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh",
                "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii",
                "jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj",
                "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk",
                "llllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll",
                "mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm",
                "nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn",
                "oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo",
                "pppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp",
                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq",
                "rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr",
                "ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss",
                "tttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt",
                "uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu",
                "vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv",
                "wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww",
                "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
                "yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy",
                "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"};
        for(int i=0;i< complaintsDiscription.length;i++){
            if(i<s.size())
                storeN=i%s.size();
            if(i< complaintsDiscription.length){
                Complaint comp = new Complaint(c.get(i%c.size()) ,new Date(),complaintsDiscription[i], Complaint.Topic.values()[i%Complaint.Topic.values().length/*Math.min(i, Complaint.Topic.values().length-1)*/], s.get(i%s.size()));
                complaints.add(comp);
                session.save(comp);
                session.flush();
            }/*else if(i== complaintsDiscription.length){
                Complaint comp = new Complaint(c.get(i) ,new Date(),complaintsDiscription[complaintsDiscription.length-1], Complaint.Topic.OTHER, s.get(storeN));
                complaints.add(comp);
                session.save(comp);
                session.flush();
            }*/
        }
        return complaints;
    }

    private static void generateBaseCustomMadeProduct() throws Exception {       //generates new base products
        Random random = new Random();
        int price;
        int num_products = 10; //change according to the real
        String[] colors = {"Red","Pink","Yellow","White","Pink","White","White","Green","Blue","Green","Green"};
        String[] names = {"Red Rose","Pink Plants", "Sunflower","White Plants","Pink Rose","White Rose","White Flower","Leaves1","Blue Flower","Leaves2","Leaves3"};
        for (int i = 0; i <= num_products; i++) {
            var img = loadImageFromResources(String.format("base%s.jpg", i));
            PreMadeProduct p = new PreMadeProduct(names[i], img, price = random.nextInt(15)+1,price+random.nextInt(15),false,colors[i]);
            session.save(p);   //saves and flushes to database
            session.flush();
        }
    }

    private static List<PreMadeProduct> getAllBaseCustomMadeProduct() throws IOException {
        List<PreMadeProduct> products = getAllProducts(), baseProducts = new LinkedList<PreMadeProduct>();
        for(PreMadeProduct product : products)
            if(product.getType() == PreMadeProduct.ProductType.CUSTOM_CATALOG)
                baseProducts.add(product);

        return baseProducts;
    }

    private static List<CustomMadeProduct> getCustomMadeProductList() throws IOException {
        List<CustomMadeProduct> custom = new LinkedList<CustomMadeProduct>();
        int price = 0, rand;

        // make 10 customMadeProducts,
        for(int i = 0; i < 10; i++) {
            // make a customMadeProduct form 3-10 random baseCustomMadeProducts
            custom.add(new CustomMadeProduct(getBaseProductList(), price, "path",
                    CustomMadeProduct.ItemType.BLOOMING_POT));
        }
        return custom;
    }

    private static List<PreMadeProduct> getBaseProductList() throws IOException {
        List<PreMadeProduct> baseProducts = getAllBaseCustomMadeProduct();
        LinkedList<Integer> randomNumbers = new LinkedList<Integer>();
        LinkedList<PreMadeProduct> productsForCustom = new LinkedList<PreMadeProduct>();
        Random random = new Random();
        int rand;

        for(int j = 0; j < random.nextInt(7) + 3; j++) {
            do {
                rand = random.nextInt(11);
            } while(randomNumbers.contains(rand));

            PreMadeProduct base = baseProducts.get(rand);
            base.setAmount(random.nextInt(5) + 1);
            productsForCustom.add(base);
        }
        return productsForCustom;
    }

    private static List<PreMadeProduct> getPreMadeProductList() throws IOException {
        LinkedList<Integer> randomNumbers = new LinkedList<Integer>();
        LinkedList<PreMadeProduct> productsList = new LinkedList<PreMadeProduct>();
        List<PreMadeProduct> allProducts = getAllProducts();
        Random random = new Random();
        int rand;


        for(PreMadeProduct product : allProducts)
            if(product.getType() == PreMadeProduct.ProductType.CUSTOM_CATALOG)
                allProducts.remove(product);

        for(int j = 0; j < (random.nextInt(6) + 1); j++) {
            do {
                rand = random.nextInt(11);
            } while(randomNumbers.contains(rand));

            PreMadeProduct randomProduct = allProducts.get(rand);
            randomProduct.setAmount(random.nextInt(5) + 1);
            randomProduct = new PreMadeProduct(randomProduct);
            randomProduct.setOrdered(true);
            productsList.add(randomProduct);
        }

        return productsList;
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
        return new LinkedList<>(complaints);
    }

    static List<Complaint> getAllComplaints() throws IOException{
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Complaint> customerQuery = builder.createQuery(Complaint.class);
        customerQuery.from(Complaint.class);
        List<Complaint> complaints = session.createQuery(customerQuery).getResultList();
        return new LinkedList<>(complaints);
    }


    public static byte[] loadImageFromResources(String imageName) throws IOException {
        var stream = App.class.getClassLoader().getResourceAsStream(String.format("Images/%s", imageName));

        return Objects.requireNonNull(stream).readAllBytes();
    }

    protected static Server server;

    public static void main(String[] args) throws IOException {
        try {

            SessionFactory sessionFactory = getSessionFactory();        //calls and creates session factory
            session = sessionFactory.openSession(); //opens session
            session.beginTransaction();       //transaction for generation
            generateEntities();             //generate
            //generateStores();
            generateBaseCustomMadeProduct();
            session.getTransaction().commit(); // Save everything.*/

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
