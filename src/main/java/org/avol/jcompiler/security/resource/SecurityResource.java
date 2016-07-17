package org.avol.jcompiler.security.resource;

import org.apache.commons.collections.CollectionUtils;
import org.avol.jcompiler.api.JCompilerResponse;
import org.avol.jcompiler.api.LoginVO;
import org.avol.jcompiler.security.dto.LoginVoDTO;
import org.avol.jcompiler.security.exception.SecurityException;
import org.avol.jcompiler.security.service.SecurityService;
import org.avol.jcompiler.security.validator.SecurityValidator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

/**
 * Created by Durga on 7/11/2016.
 */
@Path("/security")
@Produces(value = MediaType.APPLICATION_JSON)
public class SecurityResource {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private SecurityValidator securityValidator;

    @POST
    @Path("/login")
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Response login(LoginVO loginVO) throws SecurityException {
        Response response = null;
        Set<String> errors = securityValidator.validateLoginVO(loginVO);
        if (CollectionUtils.isEmpty(errors)) {
            try {
                securityService.isAuthenticated(LoginVoDTO.loginEntity(loginVO));
                response = Response.status(Response.Status.OK).entity(JCompilerResponse.builder()
                        .withStatusCode(Response.Status.OK.getStatusCode())
                        .withMessage("Logged in Successfully.").build())
                        .build();
            } catch (SecurityException e) {
                throw new SecurityException(Response.Status.BAD_REQUEST.getStatusCode(), e.getMessage());
            }
        } else {
            throw new SecurityException(Response.Status.BAD_REQUEST.getStatusCode(), errors.toString());
        }
        return response;
    }

    @POST
    @Path("/register")
    @Consumes(value = MediaType.APPLICATION_JSON)
    public Response register(LoginVO loginVO) throws SecurityException {
        Response response = null;
        String id = null;
        Set<String> errors = securityValidator.validateLoginVO(loginVO);
        if (CollectionUtils.isEmpty(errors)) {
            try {
                id = securityService.register(LoginVoDTO.loginEntity(loginVO));
                response = Response.status(Response.Status.CREATED).entity(JCompilerResponse.builder()
                        .withStatusCode(Response.Status.CREATED.getStatusCode())
                        .withMessage(id + " Registered Successfully.").build())
                        .build();
            } catch (SecurityException e) {
                throw new SecurityException(Response.Status.BAD_REQUEST.getStatusCode(), e.getMessage());
            }
        } else {
            throw new SecurityException(Response.Status.BAD_REQUEST.getStatusCode(), errors.toString());
        }
        return response;
    }
}
