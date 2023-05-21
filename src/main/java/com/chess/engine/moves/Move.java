package com.chess.engine.moves;

import com.chess.engine.Pair;
import com.chess.engine.board.Board;
import com.chess.engine.pieces.Piece;

public abstract class Move {

    final Board board;
    final Piece pieceToMove;
    final Pair moveCoordinates;

    private Move(Board board, Piece pieceToMove, Pair moveCoordinates){
        this.moveCoordinates = moveCoordinates;
        this.pieceToMove = pieceToMove;
        this.board = board;
    }

    public static class validMove extends Move{
        public validMove(final Board board, Piece piece, Pair moveCoordinates){
            super(board, piece, moveCoordinates);
        }
    }

    public static class AttackingMove extends Move{

        Piece attackedPiece;
        public AttackingMove(final Board board, Piece piece, Pair moveCoordinates, Piece attackedPiece){
            super(board, piece, moveCoordinates);
            this.attackedPiece = attackedPiece;
        }
    }

}
