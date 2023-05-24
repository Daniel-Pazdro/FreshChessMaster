package com.chess.engine.moves;

import com.chess.engine.Pair;
import com.chess.engine.board.Board;
import com.chess.engine.pieces.Piece;

public abstract class Move {

    final Board board;
    final Piece pieceToMove;
    final Pair moveCoordinates;
    public static final Move NULL_MOVE = new nullMove();

    private Move(Board board, Piece pieceToMove, Pair moveCoordinates){
        this.moveCoordinates = moveCoordinates;
        this.pieceToMove = pieceToMove;
        this.board = board;
    }

    public Pair getCoordinatesBeforeMove(){
        return pieceToMove.getCoordinate();
    }
    public Pair getMoveCoordinates(){
        return moveCoordinates;
    }

    public Piece getPieceToMove(){return pieceToMove;}

    public Board execute() {

        Board.Builder builder = new Board.Builder();

        for(Piece piece : board.current.getActivePieces()){
            if(!pieceToMove.equals(piece)){
                builder.setPiece(piece);
            }
        }

        for(Piece piece : this.board.current.getOpponent().getActivePieces()){
            builder.setPiece(piece);
        }

        builder.setPiece(pieceToMove. moveActualPiece(this));
        builder.setPlayer(this.board.current.getOpponent().getColour());
        return null;
    }

    public static class standardMove extends Move{
        public standardMove(final Board board, Piece piece, Pair moveCoordinates){
            super(board, piece, moveCoordinates);
        }

    }

    public static class AttackingMove extends Move{

        Piece attackedPiece;
        public AttackingMove(final Board board, Piece piece, Pair moveCoordinates, Piece attackedPiece){
            super(board, piece, moveCoordinates);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public Board execute() {
            return null;
        }
    }
    public static class pawnMove extends Move{
        public pawnMove(final Board board, Piece piece, Pair moveCoordinates){
            super(board, piece, moveCoordinates);
        }
    }

    public static class pawnJumpMove extends Move{
        public pawnJumpMove(final Board board, Piece piece, Pair moveCoordinates){
            super(board, piece, moveCoordinates);
        }
    }

    public static class pawnAttackMove extends AttackingMove{
        public pawnAttackMove(final Board board, Piece piece, Pair moveCoordinates, Piece attackedPiece){
            super(board, piece, moveCoordinates, attackedPiece);
        }
    }

    public static class enPassantMove extends AttackingMove{
        public enPassantMove(final Board board, Piece piece, Pair moveCoordinates, Piece attackedPiece){
            super(board, piece, moveCoordinates, attackedPiece);
        }
    }

    static abstract class castling extends Move{
        public castling(final Board board, Piece piece, Pair moveCoordinates){
            super(board, piece, moveCoordinates);
        }
    }

    public static class castlingForKing extends castling{
        public castlingForKing(final Board board, Piece piece, Pair moveCoordinates){
            super(board, piece, moveCoordinates);
        }
    }

    public static class castlingForQueen extends castling{
        public castlingForQueen(final Board board, Piece piece, Pair moveCoordinates){
            super(board, piece, moveCoordinates);
        }
    }

    public static class nullMove extends Move{
        public nullMove(){
            super(null, null, null);
        }

        @Override
        public Board execute(){
            throw new RuntimeException("Cannot make this move");
        }

    }

    public static class moveExecutor{

        public static Move createMove(final Board board, final Pair currentCoordinates, Pair destinationCoordinate){

            for(Move move : board.getAllLegalMoves()){
                if(move.getMoveCoordinates().equals(destinationCoordinate) && move.getCoordinatesBeforeMove().equals(currentCoordinates))
                {return move;}
            }

            return NULL_MOVE;
        }
    }
}
