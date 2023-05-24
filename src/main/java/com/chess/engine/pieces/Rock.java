package com.chess.engine.pieces;

import com.chess.engine.Colour;
import com.chess.engine.Pair;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardFeature;
import com.chess.engine.board.Tile;
import com.chess.engine.moves.Move;

import java.util.ArrayList;
import java.util.List;

import static com.chess.engine.board.BoardFeature.numberOfTilesInColumn;

public class Rock extends Piece{

    public Rock( int positionX, int positionY, Colour colour) {
        super(typeOfPiece.ROCK, positionX, positionY, colour);

    }
    private static Pair[] candidateForMoves = {new Pair(1, 0), new Pair(0, 1), new Pair(-1, 0), new Pair(0, -1)};

    @Override
    public Piece moveActualPiece(Move move) {
        return new Rock(move.getMoveCoordinates().getX(), move.getMoveCoordinates().getY(), move.getPieceToMove().getColour());
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
                            availableMoves.add(new Move.AttackingMove(board, this, candidateForMove, pieceAtDestination));
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
        return typeOfPiece.ROCK.toString();
    }

}
