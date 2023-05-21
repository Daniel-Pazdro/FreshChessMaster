package com.chess.engine.pieces;

import com.chess.engine.Colour;
import com.chess.engine.Pair;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardFeature;
import com.chess.engine.board.Tile;
import com.chess.engine.moves.Move;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece{
    public Knight(int positionX, int positionY, Colour colour) {
        super(positionX, positionY, colour);
    }
    private final static Pair[] candidateForMoves = {new Pair(2, -1), new Pair(1, -2), new Pair(2, 1), new Pair(1, 2), new Pair(-2, 1), new Pair(-1, 2), new Pair(-2, -1), new Pair(-1, -2) };
    @Override
    public List<Move> AvailableMoves(Board board) {
        final List<Move> availableMoves = new ArrayList<>();
        Pair candidateForMove = new Pair(this.coordinate);

        for(Pair current : candidateForMoves){
            candidateForMove.setX(coordinate.getX() + current.getX());
            candidateForMove.setY(coordinate.getY() + current.getY());
            if(BoardFeature.isValidMove(candidateForMove)) {
                final Tile examinedTile = board.getTile(candidateForMove);
                if (!examinedTile.isBusy()) {
                    availableMoves.add(new Move.validMove(board, this, candidateForMove));
                } else {
                    final Piece pieceAtDestination = examinedTile.getPiece();
                    final Colour pieceAtDestinationColour = pieceAtDestination.getColour();
                    if(this.colour != pieceAtDestinationColour){
                        availableMoves.add(new Move.AttackingMove(board, this, candidateForMove, pieceAtDestination));
                    }
                }
            }

        }
//here there may be somekind problems to check

        return availableMoves;
    }
    @Override
    public String toString() {
        return typeOfPiece.KNIGHT.toString();
    }

}
