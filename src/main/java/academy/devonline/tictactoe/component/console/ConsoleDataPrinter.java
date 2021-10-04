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

package academy.devonline.tictactoe.component.console;

import academy.devonline.tictactoe.component.CellNumberConverter;
import academy.devonline.tictactoe.component.DataPrinter;
import academy.devonline.tictactoe.model.Cell;
import academy.devonline.tictactoe.model.GameTable;

/**
 * @author devonline
 * @link http://devonline.academy/java
 */
public class ConsoleDataPrinter implements DataPrinter {

    private final CellNumberConverter cellNumberConverter;

    public ConsoleDataPrinter(final CellNumberConverter cellNumberConverter) {
        this.cellNumberConverter = cellNumberConverter;
    }

    @Override
    public void printInstructions() {
        printInfoMessage("Use the following mapping table to specify a cell using numbers from 1 to 9:");
        print((i, j) -> String.valueOf(cellNumberConverter.toNumber(new Cell(i, j))));
    }

    @Override
    public void printInfoMessage(final String message) {
        System.out.println(message);
    }

    @Override
    public void printErrorMessage(final String message) {
        System.err.println(message);
    }

    @Override
    public void printGameTable(final GameTable gameTable) {
        print((i, j) -> String.valueOf(gameTable.getSign(new Cell(i, j))));
    }

    private void print(final Lambda lambda) {
        for (int i = 0; i < 3; i++) {
            System.out.println("-------------");
            for (int j = 0; j < 3; j++) {
                System.out.print("| " + lambda.getValue(i, j) + " ");
            }
            System.out.println("|");
        }
        System.out.println("-------------");
    }

    /**
     * @author devonline
     * @link http://devonline.academy/java
     */
    @FunctionalInterface
    private interface Lambda {

        String getValue(int i, int j);
    }
}
