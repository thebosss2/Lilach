package org.server;

import org.entities.Customer;
import org.entities.Order;
import org.sendemail.SendMail;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleMailing {

    public static void orderMail(Order order){
        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);

        Customer customer = order.getOrderedBy();

        Date current = new Date();

        String mail = "General";
        String subject = "Hello there";
        ses.schedule(() -> SendMail.main(new String[]{customer.getEmail(), mail, subject}), 5, TimeUnit.SECONDS);
    }

}
