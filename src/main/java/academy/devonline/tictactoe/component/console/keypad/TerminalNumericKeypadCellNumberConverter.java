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

package academy.devonline.tictactoe.component.console.keypad;

import academy.devonline.tictactoe.component.console.CellNumberConverter;
import academy.devonline.tictactoe.model.game.Cell;

/**
 * @author devonline
 * @link http://devonline.academy/java
 */
public class TerminalNumericKeypadCellNumberConverter implements CellNumberConverter {

    @Override
    public Cell toCell(final char number) {
        final int val = number - '0' - 1;
        return new Cell(val / 3, val % 3);
    }

    @Override
    public char toNumber(final Cell cell) {
        return (char) ('0' + (cell.getRow() * 3 + cell.getCol() + 1));
    }
}
