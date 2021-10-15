package academy.devonline.tictactoe.component;

import academy.devonline.tictactoe.model.game.GameTable;
import academy.devonline.tictactoe.model.game.Sign;

/**
 * @author devonline
 * @link http://devonline.academy/java
 */
public interface ComputerMoveStrategy {

    boolean tryToMakeMove(GameTable gameTable, Sign sign);
}
