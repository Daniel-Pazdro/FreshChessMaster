package Chess.engine.player;

import Chess.engine.Colour;
import Chess.engine.Pair;
import Chess.engine.board.Board;
import Chess.engine.moves.Move;
import Chess.engine.pieces.King;
import Chess.engine.pieces.Piece;

import java.util.ArrayList;

public abstract class Player {
    protected final Board board;
    protected final King playerKing;
    protected final ArrayList<Move> legalMoves;

    private final boolean isCheck;

    Player(final Board board, final ArrayList<Move> mylegalMoves, final ArrayList<Move> opponentMoves){
        this.board = board;
        playerKing = makeKing();
        mylegalMoves.addAll(calcKingCastles(mylegalMoves, opponentMoves));
        legalMoves = mylegalMoves;

        isCheck = !Player.attacksOnTile(this.playerKing.getCoordinate(), opponentMoves).isEmpty();
    }

    public ArrayList<Move> getLegalMoves(){
        return legalMoves;
    }
    protected static ArrayList<Move> attacksOnTile(Pair position, ArrayList<Move> listOfMoves) {
        ArrayList<Move> listOfAttackingMoves = new ArrayList<>();
        for(Move move: listOfMoves){
            if(position.equals(move.getMoveCoordinates())){
                listOfAttackingMoves.add(move);
            }
        }
        return  listOfAttackingMoves;
    }

    private King makeKing() {
        for(Piece piece : getActivePieces()){
            if (piece.getPieceType().isKing()) {

                return  (King) piece;
            }
        }

        throw new RuntimeException();
    }

    public abstract ArrayList<Piece> getActivePieces();
    public abstract Colour getColour();
    public abstract Player getOpponent();

    protected abstract ArrayList<Move> calcKingCastles(ArrayList<Move> playerLegals, ArrayList<Move> opponentLegals);

    public boolean isValidMove(final Move move){
        return this.legalMoves.contains(move);
    }

    public boolean isCheck(){
        return isCheck;
    }
    public boolean isCheckMate(){
        return isCheck && anyEscapeMoves();
    }

    protected boolean anyEscapeMoves() {
        for(Move move : legalMoves){
            MoveExecution execute = executeMove(move);
            if(execute.getStatus().isDone()){
                return true;
            }
        }
        return false;
    }

    public boolean isStealMate(){
        return !isCheck && !anyEscapeMoves();
    }

    public boolean isCastled(){return false;}

    public MoveExecution executeMove(final Move move){
        if(!isValidMove(move)){
            return new MoveExecution(board, move, MoveStatus.FORBIDDEN);
        }

        final Board transitionBoard = move.execute();
        final ArrayList<Move> kingAttackingMoves = Player.attacksOnTile(transitionBoard.current.getOpponent().playerKing.getCoordinate(), transitionBoard.current.legalMoves);
        if(kingAttackingMoves.isEmpty()){
            return new MoveExecution(board,move, MoveStatus.LEAVES_IN_CHECK);
        }
        return new MoveExecution(transitionBoard, move, MoveStatus.DONE);
    }
}
