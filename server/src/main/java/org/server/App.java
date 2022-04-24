package org.server;
import org.entities.*;
import javax.persistence.*;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App
{


/*    private static Session session;

    private static SessionFactory getSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
        // Add ALL of your entities here. You can also try adding a whole package.
        configuration.addAnnotatedClass(Car.class);
        configuration.addAnnotatedClass(Person.class);
        configuration.addAnnotatedClass(Car_image.class);
        configuration.addAnnotatedClass(Garage.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static void generateCars() throws Exception {
        Random random = new Random();
        List<Garage> garages = getAllGarages();
        for (int i = 0; i < 5; i++) {
            Car car1 = new Car("MOO-" + random.nextInt(), 100000, 2000 + random.nextInt(19));
            Car car2 = new Car("MOO-" + random.nextInt(), 100000, 2000 + random.nextInt(19));
            Person person1= new Person("itai","zeitouni","1234","dgfhd");
            Person person2 = new Person("Tahel" + Integer.toString(i), "Lazar", Integer.toString(random.nextInt(99999)), "t" + Integer.toString(i) + "@gmail.com");
            Car_image img1 = new Car_image("\\src\\main\\resources\\Images"+ i + ".jpg" );
            Car_image img2 = new Car_image("\\src\\main\\resources\\Images"+ i+5 + ".jpg" );
            car1.setImg(img1);
            person1.addCars(car1);
            car2.setImg(img2);
            person2.addCars(car2);
            for (int j=0; j< (random.nextInt(2)+1);j++){
                car1.addGarage(garages.get(random.nextInt(3)));
                person1.addGarage(garages.get(random.nextInt(3)));
                car2.addGarage(garages.get(random.nextInt(3)));
                person2.addGarage(garages.get(random.nextInt(3)));
            }
            session.save(img1);
            session.save(car1);
            session.save(person1);
            session.save(img2);
            session.save(car2);
            session.save(person2);



 *//*
 * The call to session.flush() updates the DB immediately without ending the transaction.
 * Recommended to do after an arbitrary unit of work.
 * MANDATORY to do if you are saving a large amount of data - otherwise you may get
cache errors.
 *//*
            session.flush();
        }
    }
    private static void generateGarages() throws Exception {
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            Garage garage = new Garage("Derech Haatzmaut " + random.nextInt(999), "0" + Integer.toString(random.nextInt(999999999)));
            session.save(garage);



 *//*
 * The call to session.flush() updates the DB immediately without ending the transaction.
 * Recommended to do after an arbitrary unit of work.
 * MANDATORY to do if you are saving a large amount of data - otherwise you may get
cache errors.
 *//*
            session.flush();
        }
    }
    private static List<Garage> getAllGarages() throws Exception {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Garage> query = builder.createQuery(Garage.class);
        query.from(Garage.class);
        List<Garage> data = session.createQuery(query).getResultList();
        return data;
    }
    private static List<Car> getAllCars() throws Exception {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Car> query = builder.createQuery(Car.class);
        query.from(Car.class);
        List<Car> data = session.createQuery(query).getResultList();
        return data;
    }

    private static void printAllCars() throws Exception {
        System.out.println("Cars:");
        List<Car> cars = getAllCars();
        for (Car car : cars) {
            car.printCarInfo();
*//*            System.out.print("Id: ");
            System.out.print(car.getId());
            System.out.print(", License plate: ");
            System.out.print(car.getLicensePlate());
            System.out.print(", Price: ");
            System.out.print(car.getPrice());
            System.out.print(", Year: ");
            System.out.print(car.getYear());
            System.out.print('\n');*//*
        }
    }
    private static void printAllGarages() throws Exception {
        System.out.println("Garages:");
        List<Garage> garages = getAllGarages();
        for (Garage garage : garages) {
            System.out.println("Address: " + garage.getAddress());
            List<Car> cars = garage.getCars();
            System.out.println("Number of cars in garage: "+ cars.size());
            System.out.println("Cars:");
            for (Car car : cars) {
                System.out.print("Id: ");
                System.out.print(car.getId());
                System.out.print(", License plate: ");
                System.out.print(car.getLicensePlate());
                System.out.print(", Price: ");
                System.out.print(car.getPrice());
                System.out.print(", Year: ");
                System.out.print(car.getYear());
                System.out.print('\n');
            }
        }
    }*/







    private static Server server;
    public static void main( String[] args ) throws IOException
    {

        server = new Server(3000);
        server.listen();
    }
}
