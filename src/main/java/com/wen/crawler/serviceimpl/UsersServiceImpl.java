package com.wen.crawler.serviceimpl;

import com.wen.crawler.dao.UsersDao;
import com.wen.crawler.model.Users;
import com.wen.crawler.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    UsersDao usersDao;

    @Override
    public long insertOne(Users users) {
        return usersDao.save(users).getUsersId();
    }

    @Override
    public void deleteOne(Users users) {
        usersDao.delete(users);
    }

    @Override
    public List<Users> getAll() {
        return usersDao.findAll();
    }

    @Override
    public void updateOne(Users users) {
        usersDao.saveAndFlush(users);
    }

    @Override
    public Users isExistUser(String username, String password, int role) {
        return usersDao.isExistUser(username,password,role);
    }

    @Override
    public Users selectByName(String username) {
        return usersDao.selectByName(username);
    }

    @Override
    public void deleteById(Long aLong) {
        usersDao.deleteById(aLong);
    }

    @Override
    public Users getById(Long aLong) {
        return usersDao.getOne(aLong);
    }
}
