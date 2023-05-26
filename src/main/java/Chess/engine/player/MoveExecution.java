package Chess.engine.player;

import Chess.engine.board.Board;
import Chess.engine.moves.Move;

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
