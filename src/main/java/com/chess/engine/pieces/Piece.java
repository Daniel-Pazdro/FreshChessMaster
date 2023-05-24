package com.chess.engine.pieces;

import com.chess.engine.Colour;
import com.chess.engine.Pair;
import com.chess.engine.board.Board;
import com.chess.engine.moves.Move;

import java.util.List;

public abstract class Piece {

    public Pair coordinate;
    public Colour colour;

    protected boolean isFirstMove;
    protected final typeOfPiece pieceType;

    Piece(final typeOfPiece pieceType, int x, int y, Colour col){
        this.pieceType = pieceType;
        coordinate = new Pair(x, y);
        colour = col;
        this.isFirstMove = false;
    }

    public boolean isFirstMove(){
        //if(this.isBlack() && )
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){return true;}
        if(!(obj instanceof Piece)){return false;}
        final Piece otherPiece = (Piece) obj;
        return colour == otherPiece.getColour() && isFirstMove == otherPiece.isFirstMove && pieceType.equals(otherPiece) && coordinate.equals(otherPiece.coordinate);
    }

    @Override
    public int hashCode() {
        int hash = pieceType.hashCode();

        hash = 41 * hash + coordinate.getY()^coordinate.getX();
        hash = 23 * hash + colour.hashCode();
        hash = 19 * hash + (isFirstMove ?  2*coordinate.getY()+coordinate.getX() :  coordinate.getY()*coordinate.getX());
        return hash;
    }

    //below method takes the piece, make the move on him and returns new Piece with updated coordinates;
    public abstract Piece moveActualPiece(Move move);

    // we try to make pieces immutable due to th future attempt to parallelization
    public boolean isBlack(){
        return (colour == Colour.BLACK);
    }

    public boolean isWhite(){
        return (colour == Colour.WHITE);
    }

    public Colour getColour(){
        return colour;
    }

    public typeOfPiece getPieceType(){
        return this.pieceType;
    }

    public Pair getCoordinate(){
        return coordinate;
    }


    public abstract List<Move> AvailableMoves(final Board board);

    public enum typeOfPiece{

        PAWN("P"){
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KNIGHT("H") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        BISHOP("B") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        QUEEN("Q") {
            @Override
            public boolean isKing() {
                return false;
            }
        },
        KING("K") {
            @Override
            public boolean isKing() {
                return true;
            }
        },
        ROCK("R") {
            @Override
            public boolean isKing() {
                return false;
            }
        };



        @Override
        public String toString(){
    return this.pieceName;
}
        private String pieceName;
        typeOfPiece (String pieceName){
            this.pieceName = pieceName;
        }
        public abstract boolean isKing();

    }






}
