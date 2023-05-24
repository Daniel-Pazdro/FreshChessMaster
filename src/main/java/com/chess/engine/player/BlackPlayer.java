package com.chess.engine.player;

import com.chess.engine.Colour;
import com.chess.engine.board.Board;
import com.chess.engine.moves.Move;
import com.chess.engine.pieces.Piece;

import java.util.ArrayList;

public class BlackPlayer extends Player{
    public BlackPlayer(Board board, ArrayList<Move> myLegalMoves, ArrayList<Move> opponentLegalMoves) {
        super(board, myLegalMoves, opponentLegalMoves);
    }

    @Override
    public ArrayList<Piece> getActivePieces() {
        return board.getBlackPieces();
    }

    @Override
    public Colour getColour() {
        return Colour.BLACK;
    }

    @Override
    public Player getOpponent() {
        return board.whitePlayer;
    }
}
