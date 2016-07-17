package org.avol.jcompiler.security.respository;

import org.avol.jcompiler.security.entities.LoginEntity;

/**
 * Created by Durga on 7/11/2016.
 */
public interface SecurityRepository {

    String FETCH_USER_BY_NAME_AND_PWD = "SELECT COUNT(*) FROM USER_DETAILS WHERE LOGIN_NAME = :LOGIN_NAME " +
            "AND PASSWORD = :PASSWORD";

    String IS_USER_EXIST = "SELECT COUNT(*) FROM USER_DETAILS WHERE LOGIN_NAME = :LOGIN_NAME";

    boolean isAuthenticated(LoginEntity loginEntity);

    String register(LoginEntity loginEntity);

    boolean isUserExist(String loginName);
}
