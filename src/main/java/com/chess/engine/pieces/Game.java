package com.chess.engine.pieces;

import com.chess.engine.board.Board;

public class Game {
    public static void main(String[] args){
        Board board = Board.createStandardBoardImpl();
        System.out.println(board);
    }
}
