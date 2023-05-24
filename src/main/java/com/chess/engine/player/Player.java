package com.chess.engine.player;

import com.chess.engine.Colour;
import com.chess.engine.Pair;
import com.chess.engine.board.Board;
import com.chess.engine.pieces.King;
import com.chess.engine.moves.Move;
import com.chess.engine.pieces.Piece;

import java.util.ArrayList;
import java.util.Collection;

public abstract class Player {
    protected final Board board;
    protected final King playerKing;
    protected final ArrayList<Move> legalMoves;

    private final boolean isCheck;

    Player(Board board, ArrayList<Move> mylegalMoves, final ArrayList<Move> opponentMoves){
        this.board = board;
        playerKing = makeKing();
        this.legalMoves = mylegalMoves;
        isCheck = !Player.attacksOnTile(this.playerKing.getCoordinate(), opponentMoves).isEmpty();
    }

    public ArrayList<Move> getLegalMoves(){
        return legalMoves;
    }
    private static ArrayList<Move> attacksOnTile(Pair position, ArrayList<Move> listOfMoves) {
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
