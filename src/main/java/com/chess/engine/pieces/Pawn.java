package com.chess.engine.pieces;

import com.chess.engine.Colour;
import com.chess.engine.Pair;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardFeature;
import com.chess.engine.moves.Move;

import java.util.ArrayList;
import java.util.List;

import static com.chess.engine.board.BoardFeature.numberOfTilesInColumn;

public class Pawn extends Piece{

    public Pawn( int positionX, int positionY, Colour colour) {
        super(typeOfPiece.PAWN, positionX, positionY, colour);
    }

    private static Pair[] candidateForMoves = {new Pair(0, 1), new Pair(0, 2), new Pair(1, 1), new Pair(-1, 1)};

    @Override
    public Piece moveActualPiece(Move move) {
        return new Pawn(move.getMoveCoordinates().getX(), move.getMoveCoordinates().getY(), move.getPieceToMove().getColour());
    }
    @Override
    public List<Move> AvailableMoves(Board board) {
        final List<Move> availableMoves = new ArrayList<>();
        for(final Pair currentVectorMove: candidateForMoves){
            Pair candidateForMove = new Pair(this.coordinate);
            candidateForMove.addCoordinates(currentVectorMove.getX() * getColour().getDirection(), currentVectorMove.getY() * getColour().getDirection());
            if(!BoardFeature.isValidMove(candidateForMove)){
                continue;
            }
            if(currentVectorMove.equals(new Pair(0, 1)) && currentVectorMove.getX() == 0 && !board.getTile(candidateForMove).isBusy()){
                availableMoves.add(new Move.standardMove(board, this, candidateForMove));
            }
            else if (currentVectorMove.equals(new Pair(0, 2)) && !board.getTile(candidateForMove).isBusy() && (this.isWhite() && this.coordinate.getY() == 1 ||
                    this.isBlack() && this.coordinate.getY() == numberOfTilesInColumn-2)){
                Pair placeBeforeJump = new Pair(this.coordinate.getX(), this.coordinate.getY()+ getColour().getDirection());
                if(!board.getTile(candidateForMove).isBusy()) {
                    availableMoves.add(new Move.standardMove(board, this, candidateForMove));
                }
            }

            else if(currentVectorMove.equals(new Pair(1, 1)) && board.getTile(candidateForMove).isBusy()){
                Piece candidateToBeBittenByPawn = board.getTile(candidateForMove).getPiece();
                if(this.colour != candidateToBeBittenByPawn.colour){
                    availableMoves.add(new Move.standardMove(board, this, candidateForMove));
                }
            }

            else if(currentVectorMove.equals(new Pair(-1, 1)) && board.getTile(candidateForMove).isBusy()){
                Piece candidateToBeBittenByPawn = board.getTile(candidateForMove).getPiece();
                if(this.colour != candidateToBeBittenByPawn.colour){
                    availableMoves.add(new Move.standardMove(board, this, candidateForMove));
                }
            }
        }
        return availableMoves;
    }
    @Override
    public String toString() {
        return typeOfPiece.PAWN.toString();
    }
}
