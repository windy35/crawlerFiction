package com.wen.crawler.serviceimpl;


import com.wen.crawler.dao.LoginTokenDao;
import com.wen.crawler.model.LoginToken;
import com.wen.crawler.service.LoginTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginTokenServiceImpl implements LoginTokenService {
    @Autowired
    LoginTokenDao loginTokenDao;

    @Override
    public long insertOne(LoginToken entity) {
        return loginTokenDao.save(entity).getUsersId();
    }

    @Override
    public void deleteOne(LoginToken entity) {
        loginTokenDao.delete(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        loginTokenDao.deleteById(aLong);
    }

    @Override
    public LoginToken getById(Long aLong) {
        return loginTokenDao.getOne(aLong);
    }

    @Override
    public List<LoginToken> getAll() {
        return loginTokenDao.findAll();
    }

    @Override
    public void updateOne(LoginToken entity) {
        loginTokenDao.saveAndFlush(entity);
    }

    @Override
    public void updateTicketStatus(boolean status,String ticket) {
        loginTokenDao.updateTicketStatus(status,ticket);
    }

    @Override
    public LoginToken selectByToken(String token) {
        return loginTokenDao.selectByToken(token);
    }

    @Override
    public LoginToken selectByUsersName(String users_name) {
        return loginTokenDao.selectByUsersName(users_name);
    }
}
