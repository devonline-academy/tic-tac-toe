/*
 * Copyright (c) 2019. http://devonline.academy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

/**
 * @author devonline
 * @link http://devonline.academy/java
 */
public class BuildJar {

    public static void main(String[] args) throws IOException {
        // mkdir out\classes
        final Path classesDir = Paths.get("out/classes");
        if (!Files.exists(classesDir)) {
            Files.createDirectories(classesDir);
        }

        // javac -sourcepath src/ -d out/classes src/academy/devonline/tictactoe/Launcher.java
        compileJavaSourceFiles();

        // jar cfe out/tic-tac-toe.jar academy.devonline.tictactoe.Launcher -C out/classes .
        createJarFile();

        // del /q /f out\classes
        // rmdir /q /s out\classes
        deleteCompiledClassFilesAndPackageDirectories();
    }

    private static void compileJavaSourceFiles() throws IOException {
        // Create javac options
        final List<String> sourceFiles = new ArrayList<>();
        sourceFiles.add("-d");
        sourceFiles.add("out/classes");
        Files.walkFileTree(Paths.get("src"), new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(final Path file,
                                             final BasicFileAttributes attrs) throws IOException {
                sourceFiles.add(file.toAbsolutePath().toString());
                return super.visitFile(file, attrs);
            }
        });

        // Invoke javac
        final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(System.in, System.out, System.err, sourceFiles.toArray(new String[0]));
    }

    private static void createJarFile() throws IOException {
        // Create manifest
        final Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS, "academy.devonline.tictactoe.Launcher");

        // Create jar file out stream
        try (FileOutputStream stream = new FileOutputStream("out/tic-tac-toe.jar")) {
            try (JarOutputStream out = new JarOutputStream(stream, manifest)) {
                // Get absolute paths to class files
                final List<Path> files = new ArrayList<>();
                Files.walkFileTree(Paths.get("out/classes"), new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult visitFile(final Path file,
                                                     final BasicFileAttributes attrs) throws IOException {
                        files.add(file);
                        return super.visitFile(file, attrs);
                    }
                });

                final String absoluteClassesRootFolder = Paths.get("out/classes").toAbsolutePath() + "/";
                // Add class files to jar file
                for (final Path file : files) {
                    // Add archive entry
                    final String classFileAbsolutePath = file.toAbsolutePath().toString();
                    final String classFileRelativePath = classFileAbsolutePath
                            .substring(absoluteClassesRootFolder.length())
                            .replace('\\', '/');
                    final JarEntry jarAdd = new JarEntry(classFileRelativePath);
                    jarAdd.setTime(Files.getLastModifiedTime(file).toMillis());
                    out.putNextEntry(jarAdd);

                    // Write file to archive
                    try (InputStream in = Files.newInputStream(file)) {
                        final byte[] buffer = new byte[8192];
                        while (true) {
                            int nRead = in.read(buffer, 0, buffer.length);
                            if (nRead <= 0) {
                                break;
                            }
                            out.write(buffer, 0, nRead);
                        }
                    }
                    out.closeEntry();
                }
            }
        }
    }

    private static void deleteCompiledClassFilesAndPackageDirectories() throws IOException {
        Files.walkFileTree(Paths.get("out/classes"), new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(final Path file,
                                             final BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(final Path dir,
                                                      final IOException exc) throws IOException {
                Files.delete(dir);
                return super.postVisitDirectory(dir, exc);
            }
        });
    }
}