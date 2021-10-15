package academy.devonline.tictactoe.component.strategy;

import academy.devonline.tictactoe.component.ComputerMoveStrategy;
import academy.devonline.tictactoe.model.game.Cell;
import academy.devonline.tictactoe.model.game.GameTable;
import academy.devonline.tictactoe.model.game.Sign;

import java.util.Random;

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
        final BestCells bestCells = new BestCells();
        findBestCellsForMoveByRows(gameTable, findSign, bestCells);
        findBestCellsForMoveByCols(gameTable, findSign, bestCells);
        findBestCellsForMoveByMainDiagonal(gameTable, findSign, bestCells);
        findBestCellsForMoveBySecondaryDiagonal(gameTable, findSign, bestCells);
        if (bestCells.count > 0) {
            final Cell randomCell = bestCells.emptyCells[new Random().nextInt(bestCells.count)];
            gameTable.setSign(randomCell, moveSign);
            return true;
        } else {
            return false;
        }
    }

    @SuppressWarnings("Convert2MethodRef")
    private void findBestCellsForMoveByRows(final GameTable gameTable, final Sign findSign, final BestCells bestCells) {
        for (int i = 0; i < 3; i++) {
            findBestCellsForMoveUsingLambdaConversion(gameTable, findSign, bestCells, i, (k, j) -> new Cell(k, j));
        }
    }

    private void findBestCellsForMoveByCols(final GameTable gameTable, final Sign findSign, final BestCells bestCells) {
        for (int i = 0; i < 3; i++) {
            findBestCellsForMoveUsingLambdaConversion(gameTable, findSign, bestCells, i, (k, j) -> new Cell(j, k));
        }
    }

    private void findBestCellsForMoveByMainDiagonal(final GameTable gameTable, final Sign findSign, final BestCells bestCells) {
        findBestCellsForMoveUsingLambdaConversion(gameTable, findSign, bestCells, -1, (k, j) -> new Cell(j, j));
    }

    private void findBestCellsForMoveBySecondaryDiagonal(final GameTable gameTable, final Sign findSign, final BestCells bestCells) {
        findBestCellsForMoveUsingLambdaConversion(gameTable, findSign, bestCells, -1, (k, j) -> new Cell(j, 2 - j));
    }

    protected abstract Sign getFindSign(Sign moveSign);

    private void findBestCellsForMoveUsingLambdaConversion(final GameTable gameTable,
                                                           final Sign findSign,
                                                           final BestCells bestCells,
                                                           final int i,
                                                           final Lambda lambda) {
        int countEmptyCells = 0;
        int countSignCells = 0;
        final Cell[] localEmptyCells = new Cell[3];
        int count = 0;
        for (int j = 0; j < 3; j++) {
            final Cell cell = lambda.convert(i, j);
            if (gameTable.isEmpty(cell)) {
                localEmptyCells[count++] = cell;
                countEmptyCells++;
            } else if (gameTable.getSign(cell) == findSign) {
                countSignCells++;
            } else {
                break;
            }
        }
        if (countEmptyCells == expectedCountEmptyCells &&
                countSignCells == 3 - expectedCountEmptyCells) {
            for (int j = 0; j < count; j++) {
                bestCells.add(localEmptyCells[j]);
            }
        }
    }

    /**
     * @author devonline
     * @link http://devonline.academy/java
     */
    @FunctionalInterface
    private interface Lambda {

        Cell convert(int k, int j);
    }

    /**
     * @author devonline
     * @link http://devonline.academy/java
     */
    private static class BestCells {

        private final Cell[] emptyCells = new Cell[9];

        private int count;

        private void add(final Cell cell) {
            emptyCells[count++] = cell;
        }
    }
}
