package org.avol.jcompiler;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Durga on 7/11/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter @Getter
public class LoginVO {

    @NotNull(message = "UserName cannot be empty.")
    @Size(max = 25, min = 5, message = "UserName must be at-least 5 characters.")
    private String loginName;

    @NotBlank(message = "Password cannot be empty.")
    @Size(max = 25, min = 5, message = "Password must be at-least 5 characters.")
    private String password;

    private String email;
}