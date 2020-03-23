package com.wen.crawler.service;

import com.wen.crawler.model.LoginToken;

public interface LoginTokenService extends BaseService <LoginToken,Long> {
    void updateTicketStatus(boolean status,String ticket);

    LoginToken selectByToken(String token);

    LoginToken selectByUsersName(String users_name);
}
