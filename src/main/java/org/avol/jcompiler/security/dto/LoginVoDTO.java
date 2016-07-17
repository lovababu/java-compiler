package org.avol.jcompiler.security.dto;

import org.avol.jcompiler.api.LoginVO;
import org.avol.jcompiler.security.entities.LoginEntity;
import org.springframework.beans.BeanUtils;

/**
 * Created by Durga on 7/12/2016.
 *
 * Data transfer object, transfer the data from LoginVO to LoginEntity and vice versa.
 */
public class LoginVoDTO {

    public static LoginEntity loginEntity(LoginVO loginVO) {
        LoginEntity loginEntity = new LoginEntity();
        BeanUtils.copyProperties(loginVO, loginEntity);
        return loginEntity;
    }

    public static LoginVO loginVO(LoginEntity loginEntity) {
        LoginVO loginVO = new LoginVO();
        BeanUtils.copyProperties(loginEntity, loginVO);
        return loginVO;
    }
}
