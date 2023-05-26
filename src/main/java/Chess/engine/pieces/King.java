package Chess.engine.pieces;

import Chess.engine.Colour;
import Chess.engine.Pair;
import Chess.engine.board.Board;
import Chess.engine.board.BoardFeature;
import Chess.engine.board.Tile;
import Chess.engine.moves.Move;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece{

    public King(int positionX, int positionY, Colour colour) {
        super(typeOfPiece.KING, positionX, positionY, colour);
    }

    private static Pair[] candidateForMoves = {new Pair(1, 1), new Pair(1, -1), new Pair(-1, -1), new Pair(-1, 1),
            new Pair(0, 1), new Pair(1, 0), new Pair(-1, 0), new Pair(0, -1)};
    @Override
    public Piece moveActualPiece(Move move) {
        return new King(move.getMoveCoordinates().getX(), move.getMoveCoordinates().getY(), move.getPieceToMove().getColour());
    }

    @Override
    public List<Move> AvailableMoves(Board board) {
        final List<Move> availableMoves = new ArrayList<>();
        Pair candidateForMove = new Pair(this.coordinate);

        for (Pair current : candidateForMoves) {
            candidateForMove.setX(coordinate.getX() + current.getX());
            candidateForMove.setY(coordinate.getY() + current.getY());
            if (BoardFeature.isValidMove(candidateForMove)) {
                final Tile examinedTile = board.getTile(candidateForMove);
                if (!examinedTile.isBusy()) {
                    availableMoves.add(new Move.standardMove(board, this, candidateForMove));
                } else {
                    final Piece pieceAtDestination = examinedTile.getPiece();
                    final Colour pieceAtDestinationColour = pieceAtDestination.getColour();
                    if (this.colour != pieceAtDestinationColour) {
                        availableMoves.add(new Move.AttackingMove(board, this, candidateForMove, pieceAtDestination));
                    }
                }
            }
        }
        return availableMoves;
    }
    @Override
    public String toString() {
        return typeOfPiece.KING.toString();
    }
}
