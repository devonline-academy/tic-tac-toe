package academy.devonline.tictactoe.component.strategy;

import academy.devonline.tictactoe.model.game.Sign;

/**
 * @author devonline
 * @link http://devonline.academy/java
 */
public class PreventUserWinComputerMoveStrategy extends AbstractComputerMoveStrategy {

    @Override
    protected Sign getFindSign(final Sign moveSign) {
        return moveSign.oppositeSign();
    }
}

