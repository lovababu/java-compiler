package org.avol.jcompiler.compile.service;

import org.apache.maven.shared.invoker.MavenInvocationException;
import org.avol.jcompiler.compile.exception.CompileException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Durga on 7/12/2016.
 */
public interface CompileService {
    String compileSource(InputStream inputStream, String fileName) throws CompileException, MavenInvocationException, IOException;
}
