package Chess;

import Chess.GUI.Table;
import Chess.engine.board.Board;

import java.awt.*;

public class Game {

    public static void main(String[] args){
        Board board = Board.createStandardBoardImpl();
        System.out.println(board);
        Table table = new Table();
    }
}
