package com.wen.crawler.dao;

import com.wen.crawler.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeDao extends JpaRepository<Notice,Long> {

}
