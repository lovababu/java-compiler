package org.avol.jcompiler.security.respository.impl;

import org.avol.jcompiler.security.entities.LoginEntity;
import org.avol.jcompiler.security.respository.SecurityRepository;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by Durga on 7/11/2016.
 */
@Repository
public class SecurityRepositoryImpl implements SecurityRepository {

    @Inject
    private SessionFactory sessionFactory;

    @Override
    public boolean isAuthenticated(LoginEntity loginEntity) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery(FETCH_USER_BY_NAME_AND_PWD);
        query.setString("LOGIN_NAME", loginEntity.getLoginName());
        query.setString("PASSWORD", loginEntity.getPassword());
        BigInteger count = (BigInteger) query.uniqueResult();
        return count.longValue() > 0;
    }

    @Override
    public String register(LoginEntity loginEntity) {
        Serializable id = sessionFactory.getCurrentSession().save(loginEntity);
        return id.toString();
    }

    @Override
    public boolean isUserExist(String loginName) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery(IS_USER_EXIST);
        query.setString("LOGIN_NAME", loginName);
        BigInteger count = (BigInteger) query.uniqueResult();
        return count.longValue() > 0;
    }
}
