package org.avol.jcompiler.app.exceptionmapper;

import org.avol.jcompiler.api.JCompilerResponse;
import org.avol.jcompiler.compile.exception.CompileException;
import org.avol.jcompiler.security.exception.SecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by Durga on 7/14/2016.
 */
@Provider
public class JCompilerExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JCompilerExceptionMapper.class);

    @Override
    public Response toResponse(Exception exception) {
        LOGGER.error("Exception occurred:", exception);
        if (exception instanceof SecurityException) {
            SecurityException securityException = (SecurityException) exception;
            return Response.status(securityException.getStatusCode()).entity(
                    JCompilerResponse.builder().withStatusCode(securityException.getStatusCode())
                            .withMessage(exception.getMessage())
                            .build())
                    .build();
        } else if (exception instanceof CompileException) {
            CompileException compileException = (CompileException) exception;
            return Response.status(compileException.getStatusCode()).entity(
                    JCompilerResponse.builder().withStatusCode(compileException.getStatusCode())
                            .withMessage(exception.getMessage())
                            .build())
                    .build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                    JCompilerResponse.builder().withStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                            .withMessage("We are experiencing with some technical problem, please try after some time.")
                            .build())
                    .build();
        }
    }
}
