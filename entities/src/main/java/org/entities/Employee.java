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
    public Employee(String name, String userName, String password, String email, Date birth, Role role) {
        super(name, userName, password,  email, birth);
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
}
