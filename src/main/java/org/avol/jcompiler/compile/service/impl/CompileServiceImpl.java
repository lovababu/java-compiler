package org.avol.jcompiler.compile.service.impl;

import org.apache.commons.io.IOUtils;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.avol.jcompiler.compile.CompileSourceCode;
import org.avol.jcompiler.compile.MavenProjCompiler;
import org.avol.jcompiler.compile.exception.CompileException;
import org.avol.jcompiler.compile.service.CompileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by Durga on 7/12/2016.
 */
@Service
public class CompileServiceImpl implements CompileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompileServiceImpl.class);
    @Value("${source.data.directory}")
    private String sourceDataDirectory;

    @Override
    public String compileSource(InputStream inputStream, String fileName) throws CompileException,
            MavenInvocationException, IOException {
        createSourceDataDirectory(sourceDataDirectory);
        String output = null;
        String fileWithoutExt = fileName.substring(0, fileName.lastIndexOf('.'));
        if (fileName.endsWith(".zip")) {
            Path path = FileSystems.getDefault().getPath(sourceDataDirectory, fileWithoutExt);
            boolean isEmpty = unzip(inputStream, path.toFile());
            if (isEmpty) {
                throw new CompileException("File uploaded is empty.");
            }

            output = new MavenProjCompiler(path.toString()).build();
            //maven build.
        } else {
            //java source compile.
            try {
                Set<String> errors = new CompileSourceCode(sourceDataDirectory).compile(fileWithoutExt, IOUtils.toString(inputStream, "UTF-8"));
                IOUtils.closeQuietly(inputStream);
                output = errors.toString();
            } catch (IOException e) {
                LOGGER.error("Exception: ", e);
                throw new CompileException("Java file uploaded is invalid.");
            }
        }

        return output;
    }

    private void createSourceDataDirectory(String sourceDataDirectory) throws IOException {
        if (!Paths.get(sourceDataDirectory).toFile().exists()) {
            Paths.get(sourceDataDirectory).toFile().mkdir();
        }
    }

    private boolean unzip(InputStream inputStream, File outputFolder) throws CompileException {

        //create output directory is not exists
        if (!outputFolder.exists()) {
            outputFolder.mkdir();
        }
        ZipInputStream zis = new ZipInputStream(inputStream);
        boolean isEmpty = true;
        try {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                isEmpty = false;
                File newFile = new File(outputFolder, entry.getName());
                if (entry.isDirectory()) {
                    if (!newFile.exists()) {
                        newFile.mkdir();
                    }
                } else {
                    FileOutputStream fos = new FileOutputStream(newFile);
                    IOUtils.copy(zis, fos);
                    fos.flush();
                    IOUtils.closeQuietly(fos);
                }
            }

            IOUtils.closeQuietly(zis);
            IOUtils.closeQuietly(inputStream);
        } catch (IOException e) {
            LOGGER.error("Exception: ", e);
            throw new CompileException("File uploaded is not valid or corrupted.");
        }
        return isEmpty;
    }
}
