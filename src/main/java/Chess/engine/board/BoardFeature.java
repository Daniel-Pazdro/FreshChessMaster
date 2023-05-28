package Chess.engine.board;

import Chess.engine.Pair;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BoardFeature {
    public static final int numberOfTilesInColumn = 8;
    public static final int totalNumberOfTiles = 64;
    public static final List<String> ALGEBRAIC_NOTATION = initializeAlgebraicNotation();

    public static boolean isValidMove(Pair candidateForMove) {
        return candidateForMove.getX() >= 0 && candidateForMove.getX() < numberOfTilesInColumn && candidateForMove.getY() >= 0 && candidateForMove.getY() < numberOfTilesInColumn;
    }

    private static List<String> initializeAlgebraicNotation() {
        return Collections.unmodifiableList(Arrays.asList(
                "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
                "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
                "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
                "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
                "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
                "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
                "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
                "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"));
    }

    public static String getPositionAtCoordinate(int x, int y) {
        return ALGEBRAIC_NOTATION.get((numberOfTilesInColumn - y-1)*8 + x+1);
    }
}
