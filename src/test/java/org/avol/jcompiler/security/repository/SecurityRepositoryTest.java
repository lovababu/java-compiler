package org.avol.jcompiler.security.repository;

import org.avol.jcompiler.app.config.DbConfig;
import org.avol.jcompiler.security.entities.LoginEntity;
import org.avol.jcompiler.security.respository.SecurityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Durga on 7/11/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DbConfig.class)
@Transactional
public class SecurityRepositoryTest {

    @Autowired
    private SecurityRepository repository;

    @Test
    public void testRespository() {
        assertNotNull(repository);
    }

    @Test
    public void testIsAuthenticated() {
        LoginEntity loginEntity = new LoginEntity();
        loginEntity.setLoginName("srilekha");
        loginEntity.setPassword("avol");
        assertTrue(repository.isAuthenticated(loginEntity));
    }

    @Test
    public void testRegister() {
        LoginEntity loginEntity = new LoginEntity();
        loginEntity.setLoginName("srilekha");
        loginEntity.setEmail("sriramya@gma.com");
        loginEntity.setPassword("aaaa");
        assertNotNull(repository.register(loginEntity));
    }
}
