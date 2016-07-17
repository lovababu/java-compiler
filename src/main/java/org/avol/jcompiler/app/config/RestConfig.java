package org.avol.jcompiler.app.config;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * Created by Durga on 7/11/2016.
 */
@ApplicationPath(value = "/jcompiler")
public class RestConfig extends ResourceConfig{

    public RestConfig() {
        packages(true, "org.avol.jcompiler.security",
                "org.avol.jcompiler.compile",
                "org.avol.jcompiler.app.exceptionmapper");
        register(MultiPartFeature.class);
    }
}
