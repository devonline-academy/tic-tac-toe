# The Tic Tac Toe Project

## Build instructions

- Build distributions using maven tool:

```bash
mvn clean package
```

- Use the following archives:
    - `target/tic-tac-toe-${project.version}-windows.zip` for Windows
    - `target/tic-tac-toe-${project.version}-unix.tar.gz` for MacOS and Linux

## Run instructions

- Download [OpenJDK 11](https://jdk.java.net/11/);
- Unzip the downloaded OpenJDK archive;
- Configure the `PATH` environment variable:
    - Add `%JDK_HOME%\bin\` directory for Windows;
    - Add `$JDK_HOME/bin/` directory for MacOS and Linux;
- Re-login or restart computer;
- Unzip the Tic tac toe distribution:
    - Unzip `tic-tac-toe-${project.version}-windows.zip` for Windows;
    - Unzip `tic-tac-toe-${project.version}-unix.tar.gz` for MacOS and Linux;
- Go to unzipped directory;
- Run the game by double-click on the start script:
    - `start.cmd` for Windows;
    - `start.sh` for MacOS and Linux;

## Readme tutorial

- https://guides.github.com/features/mastering-markdown/
- https://help.github.com/categories/writing-on-github/
