package ua.mephisto.task.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.mephisto.task.dao.CountryDao;
import ua.mephisto.task.model.Country;

/**
 * Created by mephisto on 27.10.15.
 */
@Repository
@Transactional
public class CountryDaoImpl implements CountryDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory=sessionFactory;
    }



    public void save(Country country) {
        getCurrentSession().save(country);
    }

    public void update(Country country) {
        getCurrentSession().update(country);
    }

    public void delete(Country country) {
        getCurrentSession().delete(country);
    }
    public Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }
}
