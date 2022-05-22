package org.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "complaints")
public class Complaint implements Serializable {    //only for customers
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Customer customer;
    @Column(name="dateOfSubmit")
    private Date date;
    private String compText;
    private Boolean appStatus=true;        // true= complaint filed successfully- yet to be inspected, false = complaint fulfilled
    private Boolean completedOnTime=false;
    public enum Topic{BAD_SERVICE, LATE_ARRIVAL, BAD_PRODUCT, PAYMENT, OTHER}
    private Topic topic;


    public static Complaint.Topic convertToTopic(String topic) {
        return switch (topic){
            case "Bad service" -> Complaint.Topic.BAD_SERVICE;
            case "Order didn't arrive in time" -> Complaint.Topic.LATE_ARRIVAL;
            case "Defective product/ not what you ordered" -> Complaint.Topic.BAD_PRODUCT;
            case "Payment issue" -> Complaint.Topic.PAYMENT;
            default -> Complaint.Topic.OTHER;
        };
    }

    public Complaint(Customer customer, Date date, String compText, Topic topic) {
        this.customer = customer;
        this.date = date;
        this.compText = compText;
        this.topic = topic;
    }

    public Complaint() {

    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getCompText() {
        return compText;
    }

    public void setCompText(String compText) {
        compText = compText;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Boolean getStatus() {
        return appStatus;
    }

    public void setStatus(Boolean status) {
        this.appStatus = status;
    }

    public Boolean getCompletedOnTime() {
        return completedOnTime;
    }

    public void setCompletedOnTime(Boolean completedOnTime) {
        this.completedOnTime = completedOnTime;
    }

    public Topic getTopic() {
        return topic;
    }
}
