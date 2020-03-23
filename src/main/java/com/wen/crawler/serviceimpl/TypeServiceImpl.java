package com.wen.crawler.serviceimpl;

import com.wen.crawler.dao.TypeDao;
import com.wen.crawler.model.Type;
import com.wen.crawler.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    TypeDao typeDao;

    @Override
    public long insertOne(Type type) {
        return typeDao.save(type).getTypeId();
    }

    @Override
    public void deleteOne(Type type) {
        typeDao.delete(type);
    }

    @Override
    public List<Type> getAll() {
        return typeDao.findAll();
    }

    @Override
    public void updateOne(Type type) {
        typeDao.saveAndFlush(type);
    }

    @Override
    public void deleteById(Long aLong) {
        typeDao.deleteById(aLong);
    }

    @Override
    public Type getById(Long aLong) {
        return typeDao.getOne(aLong);
    }
}
