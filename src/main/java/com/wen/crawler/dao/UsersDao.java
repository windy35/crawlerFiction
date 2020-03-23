package com.wen.crawler.dao;

import com.wen.crawler.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersDao extends JpaRepository<Users,Long> {
    @Query(value = "SELECT * FROM users WHERE user_name = ?1 AND password = ?2 AND role = ?3", nativeQuery = true)
    Users isExistUser(String username, String password, int role);

    @Query(value = "SELECT * FROM users WHERE user_name = ?1", nativeQuery = true)
    Users selectByName(String username);
}
