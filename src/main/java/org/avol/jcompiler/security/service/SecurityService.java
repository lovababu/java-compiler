package org.avol.jcompiler.security.service;

import org.avol.jcompiler.security.entities.LoginEntity;
import org.avol.jcompiler.security.exception.SecurityException;

/**
 * Created by Durga on 7/11/2016.
 */
public interface SecurityService {

    boolean isAuthenticated(LoginEntity loginEntity) throws SecurityException;

    String register(LoginEntity loginEntity) throws SecurityException;
}
