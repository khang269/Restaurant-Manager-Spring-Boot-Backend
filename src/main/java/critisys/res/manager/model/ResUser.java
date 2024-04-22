package critisys.res.manager.model;

import java.util.Date;
import java.util.List;

import io.jsonwebtoken.lang.Arrays;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "ResUser")
public class ResUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    // For user that log in with google authentication
    @Column(unique = true, nullable = false)
    private String email = "";

    // For user that log in with username and password
    @Column(unique = true, nullable = true)
    private String name;

    @Column
    private String password = "";

    @Column
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @Column
    @Temporal(TemporalType.DATE)
    private Date updatedDate = new Date();

    @ElementCollection(targetClass = Role.class)
    @CollectionTable(name = "ResRole", joinColumns = @JoinColumn(name = "ownerId"))
    @Enumerated(EnumType.STRING)
    private List<Role> roles = Arrays.asList(new Role[]{Role.VISITOR});

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
    public enum Role {  
        ADMIN, MANAGER, STORAGE, COUNTER, WAITER, VISITOR, CUSTOMER;
    }
}
