package org.entities;
import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "employees")
public class Employee extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public enum Role { STORE_EMPLOYEE, CUSTOMER_SERVICE, STORE_MANAGER, CEO, ADMIN}
    @Column(name = "employee_role")
    private Role role;
    public Employee(String userID, String name, String userName, String password, String email, String phone, Role role) {
        super(userID, name, userName, password,  email, phone);
        this.role = role;
    }

    public Employee() {
        super();
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getRoleString() {
        switch(this.role) {
            case STORE_EMPLOYEE:
                return "Store Employee";

            case CUSTOMER_SERVICE:
                return "Customer Service";

            case CEO:
                return "CEO";

            case STORE_MANAGER:
                return "Store Manager";

            default:
                return "Admin";
        }
    }
}
