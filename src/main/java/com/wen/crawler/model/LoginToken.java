package com.wen.crawler.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "LoginToken")
public class LoginToken implements Serializable {
    private static final long serialVersionUID = 7419229779731522702L;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Id
    @Column(name = "usersId")
    private long usersId;

    @Column(name = "expired")
    private Date expired;

    @Column(name = "status",columnDefinition = "TINYINT(1) DEFAULT '0'")
    private boolean status;

    @Column(name = "token",length = 50)
    private String token;

    @Column(name = "lastLoginTime")
    private Date lastLoginTime;

    public long getUsersId() {
        return usersId;
    }

    public void setUsersId(long usersId) {
        this.usersId = usersId;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }


    @Override
    public String toString() {
        return "LoginToken{" +
                "usersId=" + usersId +
                ", expired=" + expired +
                ", status=" + status +
                ", token='" + token + '\'' +
                ", lastLoginTime=" + lastLoginTime +
                '}';
    }
}
