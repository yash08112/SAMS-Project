package com.sams.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDAO<T, ID> {
    void save(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    boolean deleteById(ID id);

    boolean update(T entity);
}

