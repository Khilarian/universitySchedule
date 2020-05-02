package com.rumakin.universityschedule.dao;

import java.util.List;

import javax.persistence.*;
import javax.persistence.criteria.*;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.rumakin.universityschedule.exceptions.*;

public abstract class Dao<T> {

    protected final EntityManagerFactory entityManagerFactory;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected Dao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    protected abstract Class<?> getEntityClass();

    protected abstract String getModelClassName();

    protected String addAlias(String alias, String text) {
        return alias + "." + text;
    }

    public void add(T entity) {
        logger.debug("add() {}", entity);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    public List<T> addAll(List<T> entities) {
        logger.debug("addAll() {}", entities);
        for (T entity : entities) {
            add(entity);
        }
        logger.trace("addAll() was complete for {} objects", entities.size());
        return entities;
    }

    @SuppressWarnings("unchecked")
    public T find(int id) {
        logger.debug("find() '{}'", id);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        T result = (T) entityManager.find(getEntityClass(), id);
        if (result == null) {
            throw new DaoException(getModelClassName() + " with id " + id + "was not found.");
        }
        logger.trace("found {}", result);
        return result;
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        logger.debug("findAll()");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = (CriteriaQuery<T>) criteriaBuilder.createQuery(getEntityClass());
        Root<T> rootEntry = (Root<T>) criteriaQuery.from(getEntityClass());
        CriteriaQuery<T> all = criteriaQuery.select(rootEntry);
        TypedQuery<T> allQuery = entityManager.createQuery(all);
        List<T> result = allQuery.getResultList();
        logger.trace("findAll() found {} entries.", result.size());
        return result;
    }

    @SuppressWarnings("unchecked")
    public void delete(int id) {
        logger.debug("delete() '{}'", id);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        T entity = (T) entityManager.find(getEntityClass(), id);
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    public void update(T entity) {
        logger.debug("update() {}", entity);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }

}
