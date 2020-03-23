package com.wen.crawler.model;

import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Users")
@Proxy(lazy=false)
public class Users implements Serializable {
    private static final long serialVersionUID = 7419229779731522702L;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Id
    @Column(name = "usersId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long usersId;

    @Column(name = "userName",columnDefinition = "VARCHAR(30) NOT NULL")
    private String userName;

    @Column(name = "nickName",length = 30)
    private String nickName;

    @Column(name = "password",columnDefinition = "VARCHAR(50) NOT NULL")
    private String password;

    @Column(name = "gender",columnDefinition = "TINYINT(1) DEFAULT '0'")
    private boolean gender;

    @Column(name = "role",columnDefinition = "INT(11) DEFAULT '0'")
    private int role;

    @Column(name = "email",length = 30)
    private String email;

    @Column(name = "QQ",length = 20)
    private String qq;

    @Column(name = "weChat",length = 20)
    private String weChat;

    @Column(name = "phone",length = 11)
    private String phone;

    @Column(name = "bookCount",columnDefinition = "bigint default 0")
    private long bookCount;

    public long getBookCount() {
        return bookCount;
    }

    public void setBookCount(long bookCount) {
        this.bookCount = bookCount;
    }

    public long getUsersId() {
        return usersId;
    }

    public void setUsersId(long usersId) {
        this.usersId = usersId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public String toString() {
        return "Users{" +
                "usersId=" + usersId +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                ", gender=" + gender +
                ", role=" + role +
                ", email='" + email + '\'' +
                ", qq='" + qq + '\'' +
                ", weChat='" + weChat + '\'' +
                ", phone='" + phone + '\'' +
                ", bookCount=" + bookCount +
                '}';
    }
}
