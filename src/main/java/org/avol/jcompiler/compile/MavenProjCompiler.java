package org.avol.jcompiler.compile;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationOutputHandler;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.apache.maven.shared.invoker.PrintStreamHandler;
import org.avol.jcompiler.compile.exception.CompileException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Durga on 7/13/2016.
 *
 * Class builds the maven project and run the test target.
 */
public class MavenProjCompiler {

    private static final List<String> PUBLISH_GOALS = Arrays.asList("clean", "compile", "test");
    private final Invoker invoker;
    private final String projectLocation;

    public MavenProjCompiler(String projectLocation) {
        this.invoker = new DefaultInvoker();
        invoker.setMavenHome(Paths.get(System.getenv("MAVEN_HOME")).toFile());
        this.projectLocation = projectLocation;
    }

    public String build() throws MavenInvocationException, IOException, CompileException {

        InvocationRequest request = new DefaultInvocationRequest();
        if (!Paths.get(this.projectLocation, "pom.xml").toFile().exists()) {
            throw new CompileException("No pom.xml found, upload maven project in zip format.");
        }
        request.setPomFile( Paths.get(this.projectLocation, "pom.xml").toFile());
        request.setGoals( PUBLISH_GOALS);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos, true, "UTF-8");
        InvocationOutputHandler invocationOutputHandler = new PrintStreamHandler(ps, true);
        request.setOutputHandler(invocationOutputHandler);
        request.setErrorHandler(invocationOutputHandler);
        InvocationResult result = invoker.execute( request );

        //delete project after build.
        clearDataDir(this.projectLocation);
        if (result.getExitCode() != 0) {
            if (result.getExecutionException() != null) {
                throw new CompileException("Failed to build :" + result.getExecutionException());
            } else {
                throw new CompileException("Failed to build : " + result.getExitCode());
            }
        }
        return new String(baos.toByteArray());
    }

    private void clearDataDir(String projectLocation) {
        System.out.println(projectLocation);
        try {
            new File(projectLocation).deleteOnExit();
        } catch (Exception e) {
            //ignore.
        }
    }
}
