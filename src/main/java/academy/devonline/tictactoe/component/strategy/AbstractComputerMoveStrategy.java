package academy.devonline.tictactoe.component.strategy;

import academy.devonline.tictactoe.component.ComputerMoveStrategy;
import academy.devonline.tictactoe.model.game.Cell;
import academy.devonline.tictactoe.model.game.GameTable;
import academy.devonline.tictactoe.model.game.Sign;

/**
 * @author devonline
 * @link http://devonline.academy/java
 */
public abstract class AbstractComputerMoveStrategy implements ComputerMoveStrategy {

    private final int expectedCountEmptyCells;

    protected AbstractComputerMoveStrategy(final int expectedCountEmptyCells) {
        this.expectedCountEmptyCells = expectedCountEmptyCells;
    }

    @Override
    public final boolean tryToMakeMove(final GameTable gameTable, final Sign moveSign) {
        final Sign findSign = getFindSign(moveSign);
        return tryToMakeMoveByRows(gameTable, findSign, moveSign) ||
                tryToMakeMoveByCols(gameTable, findSign, moveSign) ||
                tryToMakeMoveByMainDiagonal(gameTable, findSign, moveSign) ||
                tryToMakeMoveBySecondaryDiagonal(gameTable, findSign, moveSign);
    }

    protected abstract Sign getFindSign(Sign moveSign);

    @SuppressWarnings("Convert2MethodRef")
    private boolean tryToMakeMoveByRows(final GameTable gameTable, final Sign findSign, final Sign moveSign) {
        for (int i = 0; i < 3; i++) {
            if (tryToMakeMoveUsingLambdaConversion(gameTable, findSign, moveSign, i, (k, j) -> new Cell(k, j))) {
                return true;
            }
        }
        return false;
    }

    private boolean tryToMakeMoveByCols(final GameTable gameTable, final Sign findSign, final Sign moveSign) {
        for (int i = 0; i < 3; i++) {
            if (tryToMakeMoveUsingLambdaConversion(gameTable, findSign, moveSign, i, (k, j) -> new Cell(j, k))) {
                return true;
            }
        }
        return false;
    }

    private boolean tryToMakeMoveByMainDiagonal(final GameTable gameTable, final Sign findSign, final Sign moveSign) {
        return tryToMakeMoveUsingLambdaConversion(gameTable, findSign, moveSign, -1, (k, j) -> new Cell(j, j));
    }

    private boolean tryToMakeMoveBySecondaryDiagonal(final GameTable gameTable, final Sign findSign, final Sign moveSign) {
        return tryToMakeMoveUsingLambdaConversion(gameTable, findSign, moveSign, -1, (k, j) -> new Cell(j, 2 - j));
    }

    private boolean tryToMakeMoveUsingLambdaConversion(final GameTable gameTable,
                                                       final Sign findSign,
                                                       final Sign moveSign,
                                                       final int i,
                                                       final Lambda lambda) {
        int countEmptyCells = 0;
        int countSignCells = 0;
        Cell lastEmptyCell = null;
        for (int j = 0; j < 3; j++) {
            final Cell cell = lambda.convert(i, j);
            if (gameTable.isEmpty(cell)) {
                lastEmptyCell = cell;
                countEmptyCells++;
            } else if (gameTable.getSign(cell) == findSign) {
                countSignCells++;
            } else {
                break;
            }
        }
        if (lastEmptyCell != null &&
                countEmptyCells == expectedCountEmptyCells &&
                countSignCells == 3 - expectedCountEmptyCells) {
            gameTable.setSign(lastEmptyCell, moveSign);
            return true;
        }
        return false;
    }

    /**
     * @author devonline
     * @link http://devonline.academy/java
     */
    @FunctionalInterface
    private interface Lambda {

        Cell convert(int k, int j);
    }
}
