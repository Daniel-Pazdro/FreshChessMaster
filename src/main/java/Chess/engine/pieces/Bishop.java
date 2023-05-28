package Chess.engine.pieces;

import Chess.engine.Colour;
import Chess.engine.Pair;
import Chess.engine.board.Board;
import Chess.engine.board.BoardFeature;
import Chess.engine.board.Tile;
import Chess.engine.moves.Move;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece{

    public Bishop(int positionX, int positionY, Colour colour) {
        super(typeOfPiece.BISHOP, positionX, positionY, colour);

    }
    private static Pair[] candidateForMoves = {new Pair(1, 1), new Pair(1, -1), new Pair(-1, -1), new Pair(-1, 1)};

    @Override
    public Piece moveActualPiece(Move move) {
        return new Bishop(move.getMoveCoordinates().getX(), move.getMoveCoordinates().getX(), move.getPieceToMove().getColour());
    }

    @Override
    public List<Move> AvailableMoves(Board board) {

        final List<Move> availableMoves = new ArrayList<>();
        Pair candidateForMove = new Pair(this.coordinate);


        for (Pair currentVectorMove : candidateForMoves) {
            candidateForMove.setX(coordinate.getX());
            candidateForMove.setY(coordinate.getY());
            while (BoardFeature.isValidMove(candidateForMove)) {
                candidateForMove.addCoordinates(currentVectorMove);
                if(BoardFeature.isValidMove(candidateForMove)) {

                    Tile examinedTile = board.getTile(candidateForMove);
                    if (!examinedTile.isBusy()) {
                        availableMoves.add(new Move.standardMove(board, this, candidateForMove));
                    }
                    else {
                        final Piece pieceAtDestination = examinedTile.getPiece();
                        final Colour pieceAtDestinationColour = pieceAtDestination.getColour();
                        if (this.colour != pieceAtDestinationColour) {
                            availableMoves.add(new Move.MajorAttackMove(board, this, candidateForMove, pieceAtDestination));
                        }
                        break;
                    }

                }
            }
        }
        return availableMoves;
    }

    @Override
    public String toString() {
        return typeOfPiece.BISHOP.toString();
    }
}
