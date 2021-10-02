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

package academy.devonline.tictactoe.component;

import academy.devonline.tictactoe.component.keypad.DesktopNumericKeypadCellNumberConverter;
import academy.devonline.tictactoe.model.Player;
import academy.devonline.tictactoe.model.PlayerType;

import static academy.devonline.tictactoe.model.PlayerType.COMPUTER;
import static academy.devonline.tictactoe.model.PlayerType.USER;
import static academy.devonline.tictactoe.model.Sign.O;
import static academy.devonline.tictactoe.model.Sign.X;

/**
 * @author devonline
 * @link http://devonline.academy/java
 */
public class GameFactory {

    private final PlayerType player1Type;

    private final PlayerType player2Type;

    public GameFactory(final String[] args) {
        PlayerType player1Type = null;
        PlayerType player2Type = null;
        for (final String arg : args) {
            if (USER.name().equalsIgnoreCase(arg) || COMPUTER.name().equalsIgnoreCase(arg)) {
                if (player1Type == null) {
                    player1Type = PlayerType.valueOf(arg.toUpperCase());
                } else if (player2Type == null) {
                    player2Type = PlayerType.valueOf(arg.toUpperCase());
                } else {
                    System.err.println("Unsupported command line argument: '" + arg + "'");
                }
            } else {
                System.err.println("Unsupported command line argument: '" + arg + "'");
            }
        }
        if (player1Type == null) {
            this.player1Type = USER;
            this.player2Type = COMPUTER;
        } else if (player2Type == null) {
            this.player1Type = USER;
            this.player2Type = player1Type;
        } else {
            this.player1Type = player1Type;
            this.player2Type = player2Type;
        }
    }

    public Game create() {
        final CellNumberConverter cellNumberConverter = new DesktopNumericKeypadCellNumberConverter();
        final Player player1;
        if (player1Type == USER) {
            player1 = new Player(X, new UserMove(cellNumberConverter));
        } else {
            player1 = new Player(X, new ComputerMove());
        }
        final Player player2;
        if (player2Type == USER) {
            player2 = new Player(O, new UserMove(cellNumberConverter));
        } else {
            player2 = new Player(O, new ComputerMove());
        }
        final boolean canSecondPlayerMakeFirstMove = player1Type != player2Type;
        return new Game(
                new DataPrinter(cellNumberConverter),
                player1,
                player2,
                new WinnerVerifier(),
                new CellVerifier(),
                canSecondPlayerMakeFirstMove
        );
    }
}
