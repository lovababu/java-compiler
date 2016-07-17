package org.avol.jcompiler.security.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import java.util.Date;

/**
 * Created by Durga on 7/11/2016.
 */
@Entity
@Table(name = "USER_DETAILS",
        uniqueConstraints = {@UniqueConstraint(name = "LOGIN_NAME_UK",
                columnNames = {"LOGIN_NAME"})})
public class LoginEntity {
    @Id
    @Column(name = "LOGIN_NAME", unique = true, nullable = false)
    private String loginName;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "JOIN_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date joinedDate;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getJoinedDate() {
        return joinedDate != null ? (Date) joinedDate.clone() : null;
    }

    public void setJoinedDate(Date joinedDate) {
        this.joinedDate = joinedDate;
    }
}
