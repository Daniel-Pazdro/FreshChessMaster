package Chess.engine.pieces;

import Chess.engine.Colour;
import Chess.engine.Pair;
import Chess.engine.board.Board;
import Chess.engine.board.BoardFeature;
import Chess.engine.moves.Move;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece{

    public Pawn( int positionX, int positionY, Colour colour) {
        super(typeOfPiece.PAWN, positionX, positionY, colour);
    }

    private static Pair[] candidateForMoves = {new Pair(0, 1), new Pair(0, 2), new Pair(1, 1), new Pair(-1, 1)};

    @Override
    public Piece moveActualPiece(Move move) {
        return new Pawn(move.getMoveCoordinates().getX(), move.getMoveCoordinates().getY(), move.getPieceToMove().getColour());
    }
    @Override
    public List<Move> AvailableMoves(Board board) {
        final List<Move> availableMoves = new ArrayList<>();
        for(final Pair currentVectorMove: candidateForMoves){
            Pair candidateForMove = new Pair(this.coordinate);
            candidateForMove.addCoordinates(currentVectorMove.getX() * getColour().getDirection(), currentVectorMove.getY() * getColour().getDirection());
            if(!BoardFeature.isValidMove(candidateForMove)){
                continue;
            }
            if(currentVectorMove.equals(new Pair(0, 1)) && currentVectorMove.getX() == 0 && !board.getTile(candidateForMove).isBusy()){
                availableMoves.add(new Move.pawnMove(board, this, candidateForMove));
//                if(this.colour.isPawnPromotionSquare(candidateForMove)){
//                    availableMoves.add(new Move.PawnPromotion(board, this, candidateForMove));
//                }
//                else{
//                    availableMoves.add(new Move.pawnMove(board, this, candidateForMove));
//                }
            }




            else if (currentVectorMove.equals(new Pair(0, 2)) && !board.getTile(candidateForMove).isBusy() && (this.isWhite() && this.coordinate.getY() == 1 ||
                    this.isBlack() && this.coordinate.getY() == BoardFeature.numberOfTilesInColumn-2)){
                Pair placeBeforeJump = new Pair(this.coordinate.getX(), this.coordinate.getY()+ getColour().getDirection());
                if(!board.getTile(candidateForMove).isBusy() && !board.getTile(placeBeforeJump).isBusy()) {
                    availableMoves.add(new Move.pawnJumpMove(board, this, candidateForMove));
                }
            }

            else if(currentVectorMove.equals(new Pair(1, 1)) && board.getTile(candidateForMove).isBusy()){
                Piece candidateToBeBittenByPawn = board.getTile(candidateForMove).getPiece();
                if(this.colour != candidateToBeBittenByPawn.colour){
                    availableMoves.add(new Move.pawnAttackMove(board, this, candidateForMove, candidateToBeBittenByPawn));
                }
            }

            else if(currentVectorMove.equals(new Pair(-1, 1)) && board.getTile(candidateForMove).isBusy()){
                Piece candidateToBeBittenByPawn = board.getTile(candidateForMove).getPiece();
                if(this.colour != candidateToBeBittenByPawn.colour){
                    availableMoves.add(new Move.pawnAttackMove(board, this, candidateForMove, candidateToBeBittenByPawn));
                }
            }
        }
        return availableMoves;
    }
    @Override
    public String toString() {
        return typeOfPiece.PAWN.toString();
    }
}
