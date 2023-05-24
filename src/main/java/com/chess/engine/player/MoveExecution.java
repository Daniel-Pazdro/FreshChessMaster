package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.moves.Move;

public class MoveExecution {

    private final Board board;
    private final Move move;
    private final MoveStatus moveStatus;

    MoveExecution(Board board, Move move, MoveStatus status){
        this.board = board;
        this.move = move;
        this.moveStatus = status;
    }

    MoveStatus getStatus(){return moveStatus;}

}
