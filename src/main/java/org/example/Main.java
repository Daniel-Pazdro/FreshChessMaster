package org.example;

import com.chess.engine.board.Board;

import static com.chess.engine.board.Board.createStandardBoardImpl;

public class Main {
    public static void main(String[] args) {
        Board board = createStandardBoardImpl();

        System.out.println(board);
    }
}