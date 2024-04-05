package critisys.res.manager.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    
    
}
