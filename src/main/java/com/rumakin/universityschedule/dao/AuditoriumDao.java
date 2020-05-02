package com.rumakin.universityschedule.dao;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import com.rumakin.universityschedule.models.*;

@Repository
public class AuditoriumDao extends Dao<Auditorium> {

    @Autowired
    public AuditoriumDao(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

//    public List<Auditorium> findAuditoriumOnDate(int groupId, LocalDate date) {
//        logger.debug("findAuditoriumOnDate() for groupId {} and date {}", groupId, date);
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        entityManager.getTransaction().begin();
//        String sql = "SELECT " + addAlias(ALIAS, ID) + " FROM " + TABLE + " " + ALIAS + " INNER JOIN lesson l ON "
//                + addAlias(ALIAS, ID) + "=" + addAlias("l", ID)
//                + " INNER JOIN lesson_group lg ON l.lesson_id=lg.lesson_id WHERE lg.group_id=? AND l.date=?;";
//        Object[] input = { groupId, java.sql.Date.valueOf(date) };
//        List<Integer> auditoriumsId = jdbcTemplate.queryForList(sql, input, Integer.class);
//        List<Auditorium> auditoriums = new ArrayList<>();
//        if (!auditoriumsId.isEmpty()) {
//            for (Integer id : auditoriumsId) {
//                Auditorium auditorium = find(id);
//                auditoriums.add(auditorium);
//            }
//        }
//        entityManager.getTransaction().commit();
//        logger.trace("found {} exams", auditoriums.size());
//        return auditoriums;
//    }

    @Override
    protected String getModelClassName() {
        return Auditorium.class.getSimpleName();
    }

    @Override
    protected Class<Auditorium> getEntityClass() {
        return Auditorium.class;
    }

}
