package academy.devonline.tictactoe.component.strategy;

import academy.devonline.tictactoe.component.ComputerMoveStrategy;
import academy.devonline.tictactoe.model.game.Cell;
import academy.devonline.tictactoe.model.game.GameTable;
import academy.devonline.tictactoe.model.game.Sign;

/**
 * @author devonline
 * @link http://devonline.academy/java
 */
public class FirstMoveToTheCenterComputerMoveStrategy implements ComputerMoveStrategy {

    @Override
    public boolean tryToMakeMove(final GameTable gameTable, final Sign sign) {
        final Cell cell = new Cell(1, 1);
        if (gameTable.isEmpty(cell)) {
            gameTable.setSign(cell, sign);
            return true;
        } else {
            return false;
        }
    }
}
