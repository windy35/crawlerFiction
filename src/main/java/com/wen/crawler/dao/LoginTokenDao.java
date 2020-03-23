package com.wen.crawler.dao;

import com.wen.crawler.model.LoginToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LoginTokenDao extends JpaRepository<LoginToken,Long> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE  login_token SET status = ?1 where token =?2",  nativeQuery= true)
    void updateTicketStatus(boolean status,String ticket);

    @Query(value = "SELECT * FROM login_token WHERE token = ?1",  nativeQuery= true)
    LoginToken selectByToken(String token);

    @Query(value = "SELECT * FROM login_token WHERE users_name = ?1",  nativeQuery= true)
    LoginToken selectByUsersName(String users_name);
}
