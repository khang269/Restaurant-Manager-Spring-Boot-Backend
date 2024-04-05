package critisys.res.manager.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

//https://stackoverflow.com/questions/11905956/jpa-foreign-key-annotation
@Entity
@Table(name = "ResRole")
public class ResRole {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "resUserId")
    private ResUser resUser;

    @Enumerated(EnumType.ORDINAL)
    private Role role = Role.VISITOR;

    @Column
    @Temporal(TemporalType.DATE)
    private Date createdDate = new Date();

    @Column
    @Temporal(TemporalType.DATE)
    private Date updatedDate = new Date();

    public enum Role {  
        SUPER_ADMIN, ADMIN, MANAGER, STORAGE, COUNTER, WAITER, VISITOR, CUSTOMER;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ResUser getResUser() {
        return resUser;
    }

    public void setResUser(ResUser resUser) {
        this.resUser = resUser;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

