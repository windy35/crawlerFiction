package com.wen.crawler.service;

import java.util.List;

public interface BaseService<T,ID> {
    long insertOne(T entity);

    void deleteOne(T entity);

    void deleteById(ID id);

    T getById(ID id);

    List<T>getAll();

    void updateOne(T entity);
}
