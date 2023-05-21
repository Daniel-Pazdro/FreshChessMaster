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

    Piece(int x, int y, Colour col){
        coordinate = new Pair(x, y);
        colour = col;
        this.isFirstMove = false;
    }

    public boolean isFirstMove(){
        //if(this.isBlack() && )
        return true;
    }

    public boolean isBlack(){
        return (colour == Colour.BLACK);
    }

    public boolean isWhite(){
        return (colour == Colour.WHITE);
    }

    Colour getColour(){
        return colour;
    }

    public abstract List<Move> AvailableMoves(final Board board);

    public enum typeOfPiece{

        PAWN("P"),
        KNIGHT("H"),
        BISHOP("B"),
        QUEEN("Q"),
        KING("K"),
        ROCK("R");


@Override
public String toString(){
    return this.pieceName;
}


        private String pieceName;
        typeOfPiece(String pieceName){
            this.pieceName = pieceName;
        }
    }





}
