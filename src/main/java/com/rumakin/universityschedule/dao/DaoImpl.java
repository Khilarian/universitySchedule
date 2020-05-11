package com.rumakin.universityschedule.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class DaoImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements Dao<T, ID> {

    private final EntityManager entityManager;

    public DaoImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

}
