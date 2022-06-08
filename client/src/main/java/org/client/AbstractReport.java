package org.client;

import javafx.scene.control.DatePicker;
import org.entities.CustomMadeProduct;
import org.entities.Order;
import org.entities.PreMadeProduct;
import org.entities.Product;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * this class made for making a generic report controller, by implementing
 * function that helps to provide a report, like getting the number of days between
 * two dates, converting from Date object to LocalDate, etc.
 */

public abstract class AbstractReport extends Controller {

    /**
     *
     * @param d1 first date
     * @param d2 second date
     * @return number of days between two dates
     */

    static int numOfDays(Date d1, Date d2) {
        long difference_In_Time = d2.getTime() - d1.getTime();
        return ((int) TimeUnit.MILLISECONDS.toDays(difference_In_Time) % 365) + 1;
    }

    static int numOfDays(LocalDate d1, LocalDate d2) {
        return numOfDays(localDateToDate(d1), localDateToDate(d2));
    }

    static int numOfDays(DatePicker d1, DatePicker d2) {
        return numOfDays(localDateToDate(d1.getValue()), localDateToDate(d2.getValue()));
    }

    /**
     *
     * @param startDate first date
     * @param endDate second date
     * @return list of all dates between startDate and endDate
     */

    public static List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {

        endDate = addLocalDate(endDate, 1);
        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        return IntStream.iterate(0, i -> i + 1).limit(numOfDaysBetween)
                .mapToObj(i -> startDate.plusDays(i))
                .collect(Collectors.toList());
    }

    public static LocalDate dateToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date localDateToDate(LocalDate date) { //get the picked localDate and convert it to Date
        Instant instant = Instant.from(date.atStartOfDay(ZoneId.systemDefault())); //convert LocalDate to Date
        Date pickedDate = Date.from(instant);
        return pickedDate;
    }

    public static Date addDays(Date date, int daysToAdd) {
        Date d;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, daysToAdd); //minus number would decrement the days
        d = cal.getTime();
        return cal.getTime();
    }

    public static LocalDate addLocalDate(DatePicker toDate, int daysToAdd) {
        return dateToLocalDate(addDays(localDateToDate(toDate.getValue()), daysToAdd));
    }

    public static LocalDate addLocalDate(LocalDate toDate, int daysToAdd) {
        return dateToLocalDate(addDays(localDateToDate(toDate), daysToAdd));
    }

    /**
     * getMap function making a map from all orders that it receives
     * @param orders all relevant orders
     * @return a Map object that his key is the name of product and
     * his value is the number of products that sold
     */

    public Map<String, Integer> getMap(LinkedList<Order> orders) {
        Map<String, Integer> map = new HashMap<String, Integer>();

        for (Product product : Client.products)
            map.put(((PreMadeProduct) product).getName(), 0);

        for (Order order : orders) {
            for (PreMadeProduct product : order.getPreMadeProducts())
                map.put(product.getName(), map.get(product.getName()) + product.getAmount());

            for (CustomMadeProduct customProduct : order.getCustomMadeProducts())
                for (PreMadeProduct baseProduct : customProduct.getProducts())
                    map.put(baseProduct.getName(), map.get(baseProduct.getName()) + baseProduct.getAmount());
        }
        return map;
    }

    public boolean dateAreEqual(LocalDate date1, LocalDate date2) {
        return date1.atStartOfDay().isEqual(date2.atStartOfDay());
    }
}
