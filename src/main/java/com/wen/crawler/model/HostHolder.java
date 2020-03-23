package com.wen.crawler.model;
import org.springframework.stereotype.Component;

@Component
public class HostHolder{
    Users users;

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public void clear() {
        setUsers(null);
    }

    @Override
    public String toString() {
        return "HostHolder{" +
                "users=" + users +
                '}';
    }
}
