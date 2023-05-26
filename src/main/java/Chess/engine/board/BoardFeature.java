package Chess.engine.board;

import Chess.engine.Pair;

public class BoardFeature {
    public static final int numberOfTilesInColumn = 8;
    public static final int totalNumberOfTiles = 64;

    public static boolean isValidMove(Pair candidateForMove) {
        return candidateForMove.getX() >= 0 && candidateForMove.getX() < numberOfTilesInColumn && candidateForMove.getY() >= 0 && candidateForMove.getY() < numberOfTilesInColumn;
    }
}
