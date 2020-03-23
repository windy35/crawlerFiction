package com.wen.crawler.service;

import com.wen.crawler.model.Users;

import java.util.List;

public interface UsersService extends BaseService<Users,Long>{

    Users isExistUser(String username, String password, int role);

    Users selectByName(String username);
}
