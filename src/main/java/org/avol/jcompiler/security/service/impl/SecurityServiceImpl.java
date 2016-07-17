package org.avol.jcompiler.security.service.impl;

import org.avol.jcompiler.security.entities.LoginEntity;
import org.avol.jcompiler.security.exception.SecurityException;
import org.avol.jcompiler.security.respository.SecurityRepository;
import org.avol.jcompiler.security.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Durga on 7/11/2016.
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    private static Logger LOGGER = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Autowired
    private SecurityRepository securityRepository;

    @Override
    @Transactional(readOnly = true)
    public boolean isAuthenticated(LoginEntity loginEntity) throws SecurityException {
        boolean flag = securityRepository.isAuthenticated(loginEntity);
        if (flag) {
            LOGGER.info("User " + loginEntity.getLoginName() + " is authenticated.");
            return flag;
        } else {
            throw new SecurityException("Invalid credentials supplied.");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String register(LoginEntity loginEntity) throws SecurityException {
        if (!securityRepository.isUserExist(loginEntity.getLoginName())) {
            return securityRepository.register(loginEntity);
        } else {
            throw new SecurityException("UseName " + loginEntity.getLoginName() + " Already registered.");
        }
    }
}
