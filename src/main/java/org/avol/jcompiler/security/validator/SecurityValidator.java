package org.avol.jcompiler.security.validator;

import org.apache.commons.collections.CollectionUtils;
import org.avol.jcompiler.api.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Durga on 7/12/2016.
 */
@Component
public class SecurityValidator {

    @Autowired
    private Validator validator;

    public Set<String> validateLoginVO(final LoginVO loginVO) {
        Set<ConstraintViolation<LoginVO>> violations =  validator.validate(loginVO);
        Set<String> errors = null;
        if (CollectionUtils.isNotEmpty(violations)) {
            errors = new HashSet<String>();
            for (final ConstraintViolation<LoginVO> violation : violations) {
                errors.add(violation.getMessage());
            }
        }
        return errors;
    }
}
