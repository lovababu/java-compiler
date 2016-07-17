package org.avol.jcompiler.compile;

import org.avol.jcompiler.compile.exception.CompileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Created by Durga on 7/12/2016.
 */
public class CompileSourceCode {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompileSourceCode.class);

    protected static String sourceDataDirectory;

    public CompileSourceCode(String sourceDataDirectory) {
        this.sourceDataDirectory = sourceDataDirectory;
    }

    public Set<String> compile(String fileName, String sourceCodeAsString) throws IOException, CompileException {
        LOGGER.info("Java class Name: {}", fileName);
        Set<String> compilationErrors = new LinkedHashSet<>();
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        JavaFileObject file = new SourceCode(fileName, sourceCodeAsString);

        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(file);
        JavaCompiler.CompilationTask task = compiler.getTask(null, null, diagnostics, null, null, compilationUnits);

        boolean success = task.call();
        for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
            StringBuffer error = new StringBuffer(diagnostic.getKind().name())
                    .append(" @Line ")
                    .append(diagnostic.getLineNumber())
                    .append(" Position ")
                    .append(diagnostic.getPosition())
                    .append(" ")
                    .append(diagnostic.getMessage(Locale.ENGLISH));
            compilationErrors.add(error.toString());
        }
        LOGGER.info("Is Compilation Success ? " + success);
        LOGGER.info("Errors: " + compilationErrors.toString());
        if (success) {
            compilationErrors.add("Compiled successfully.");
        } else {
            throw new CompileException(compilationErrors.toString());
        }
        return compilationErrors;
    }
}

class SourceCode extends SimpleJavaFileObject {
    final String code;

    SourceCode(String name, String code) {
        super(URI.create("string:///" + CompileSourceCode.sourceDataDirectory + "//" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
        this.code = code;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return code;
    }
}


