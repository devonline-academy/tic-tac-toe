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
 *
 */

package academy.devonline.tictactoe.component.config;

import academy.devonline.tictactoe.model.config.PlayerType;
import academy.devonline.tictactoe.model.config.UserInterface;

import static academy.devonline.tictactoe.model.config.PlayerType.COMPUTER;
import static academy.devonline.tictactoe.model.config.PlayerType.USER;
import static academy.devonline.tictactoe.model.config.UserInterface.CONSOLE;
import static academy.devonline.tictactoe.model.config.UserInterface.GUI;

/**
 * @author devonline
 * @link http://devonline.academy/java
 */
public class CommandLineArgumentParser {

    private final String[] args;

    public CommandLineArgumentParser(final String[] args) {
        this.args = args;
    }

    public CommandLineArguments parse() {
        PlayerType player1Type = null;
        PlayerType player2Type = null;
        UserInterface userInterface = null;
        for (final String arg : args) {
            if (USER.name().equalsIgnoreCase(arg) || COMPUTER.name().equalsIgnoreCase(arg)) {
                if (player1Type == null) {
                    player1Type = PlayerType.valueOf(arg.toUpperCase());
                } else if (player2Type == null) {
                    player2Type = PlayerType.valueOf(arg.toUpperCase());
                } else {
                    System.err.printf(
                            "Invalid command line argument: '%s', because player types already set: player1Type='%s', player2Type='%s'!%n",
                            arg, player1Type, player2Type
                    );
                }
            } else if (GUI.name().equalsIgnoreCase(arg) || CONSOLE.name().equalsIgnoreCase(arg)) {
                if (userInterface == null) {
                    userInterface = UserInterface.valueOf(arg.toUpperCase());
                } else {
                    System.err.printf(
                            "Invalid command line argument: '%s', because user interface already set: '%s'!%n",
                            arg, userInterface
                    );
                }
            } else {
                System.err.printf("Unsupported command line argument: '%s'%n", arg);
            }
        }
        if (userInterface == null) {
            userInterface = CONSOLE;
        }
        if (player1Type == null) {
            return new CommandLineArguments(USER, COMPUTER, userInterface);
        } else if (player2Type == null) {
            return new CommandLineArguments(USER, player1Type, userInterface);
        } else {
            return new CommandLineArguments(player1Type, player2Type, userInterface);
        }
    }

    /**
     * @author devonline
     * @link http://devonline.academy/java
     */
    public static class CommandLineArguments {

        private final PlayerType player1Type;

        private final PlayerType player2Type;

        private final UserInterface userInterface;

        private CommandLineArguments(final PlayerType player1Type,
                                     final PlayerType player2Type,
                                     final UserInterface userInterface) {
            this.player1Type = player1Type;
            this.player2Type = player2Type;
            this.userInterface = userInterface;
        }

        public PlayerType getPlayer1Type() {
            return player1Type;
        }

        public PlayerType getPlayer2Type() {
            return player2Type;
        }

        public UserInterface getUserInterface() {
            return userInterface;
        }
    }
}
