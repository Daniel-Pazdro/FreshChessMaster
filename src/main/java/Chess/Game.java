package Chess;

import Chess.GUI.Table;
import Chess.engine.board.Board;

public class Game {

    public static void main(String[] args){
    	boolean whieteMove = true;
        Board board = new Board();
        Table table = new Table(board, whieteMove);
    }
}
