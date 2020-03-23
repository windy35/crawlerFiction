package com.wen.crawler.dao;

import com.wen.crawler.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeDao extends JpaRepository<Type,Long> {
}
