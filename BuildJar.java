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

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

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
        runCmd("javac -sourcepath src/ -d out/classes src/academy/devonline/tictactoe/Launcher.java");

        // jar cfe out/tic-tac-toe.jar academy.devonline.tictactoe.Launcher -C out/classes .
        runCmd("jar cfe out/tic-tac-toe.jar academy.devonline.tictactoe.Launcher -C out/classes .");

        // del /q /f out\classes
        // rmdir /q /s out\classes
        deleteCompiledClassFilesAndPackageDirectories();
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

    private static void runCmd(final String cmd) {
        try {
            final Process process = Runtime.getRuntime().exec(cmd);
            process.getErrorStream().transferTo(System.err);
            process.getInputStream().transferTo(System.out);
            process.waitFor();
        } catch (final IOException | InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}