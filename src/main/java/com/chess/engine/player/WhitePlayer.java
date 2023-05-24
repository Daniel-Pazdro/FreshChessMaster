package com.chess.engine.player;

import com.chess.engine.Colour;
import com.chess.engine.board.Board;
import com.chess.engine.moves.Move;
import com.chess.engine.pieces.Piece;

import java.util.ArrayList;

public class WhitePlayer extends Player {
    public WhitePlayer(Board board, ArrayList<Move> myLegalMoves, ArrayList<Move> opponentLegalMoves) {
        super(board, myLegalMoves, opponentLegalMoves);
    }

    @Override
    public ArrayList<Piece> getActivePieces() {
        return board.getWhitePieces();
    }

    @Override
    public Colour getColour() {
        return Colour.WHITE;
    }

    @Override
    public Player getOpponent() {
        return board.blackPlayer;
    }
}
