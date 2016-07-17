package org.avol.jcompiler.compile.resource;

import org.avol.jcompiler.api.JCompilerResponse;
import org.avol.jcompiler.compile.exception.CompileException;
import org.avol.jcompiler.compile.service.CompileService;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

/**
 * Created by Durga on 7/11/2016.
 */
@Path("/compile")
public class CompileResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompileResource.class);

    @Autowired
    private CompileService compileService;

    @POST
    @Consumes(value = MediaType.MULTIPART_FORM_DATA)
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response compile(@FormDataParam("file") InputStream is,
                            @FormDataParam("file") FormDataContentDisposition disposition) throws Exception {
        String output = null;
        try {
            String fileName = disposition.getFileName();
            if (fileName.endsWith(".java") || fileName.endsWith(".zip")) {
                final String s = compileService.compileSource(is, fileName);
                output = s;
                LOGGER.info("Compiled Output: {}", output);
            } else {
                throw new CompileException(Response.Status.BAD_REQUEST.getStatusCode(),
                        "Invalid file uploaded.");
            }

        } catch (CompileException e) {
            throw new CompileException(Response.Status.BAD_REQUEST.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            throw e;
        }
        return Response.status(Response.Status.OK).encoding("UTF-8").entity(JCompilerResponse.builder()
                .withStatusCode(Response.Status.OK.getStatusCode())
                .withMessage(output).build()).type(MediaType.APPLICATION_JSON_TYPE)
                .build();
    }
}
