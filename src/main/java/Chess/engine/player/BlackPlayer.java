package Chess.engine.player;

import Chess.engine.Colour;
import Chess.engine.Pair;
import Chess.engine.board.Board;
import Chess.engine.moves.Move;
import Chess.engine.pieces.Piece;
import Chess.engine.pieces.Rock;

import java.util.ArrayList;

public class BlackPlayer extends Player{
    public BlackPlayer(Board board, ArrayList<Move> myLegalMoves, ArrayList<Move> opponentLegalMoves) {
        super(board, myLegalMoves, opponentLegalMoves);
    }

    @Override
    public ArrayList<Piece> getActivePieces() {
        return board.getBlackPieces();
    }

    @Override
    public Colour getColour() {
        return Colour.BLACK;
    }

    @Override
    public Player getOpponent() {
        return board.whitePlayer;
    }

    @Override
    protected ArrayList<Move> calcKingCastles(ArrayList<Move> playerLegals, ArrayList<Move> opponentLegals) {

        final ArrayList<Move> kingCastles = new ArrayList<>();

        if(this.playerKing.isFirstMove() && !this.isCheck()){

            if(!this.board.getTile(new Pair(5, 7)).isBusy() && !this.board.getTile(new Pair(6, 7)).isBusy()){

                if(this.board.getTile(new Pair(7, 7)).isBusy() && this.board.getTile(new Pair(7, 7)).getPiece().isFirstMove()){
                    kingCastles.add(new Move.castlingOfTheKingSide(board, playerKing, new Pair(6, 7), (Rock) board.getTile(new Pair(7, 7)).getPiece(), board.getTile(new Pair(7, 7)).getTileCoordinate(), new Pair(5, 7)));
                }
            }
        }

        if(this.playerKing.isFirstMove() && !this.isCheck()){

            if(!this.board.getTile(new Pair(5, 7)).isBusy() && !this.board.getTile(new Pair(6, 7)).isBusy()){

                if(this.board.getTile(new Pair(7, 7)).isBusy() && this.board.getTile(new Pair(7, 7)).getPiece().isFirstMove()){
                    if (Player.attacksOnTile(new Pair(5, 7), opponentLegals).isEmpty() && Player.attacksOnTile(new Pair(6, 7), opponentLegals).isEmpty() && this.board.getTile(new Pair(7, 7)).getPiece().getPieceType().isRock()) {
                        kingCastles.add(null);
                    }
                }
            }
            if(!this.board.getTile(new Pair(1, 7)).isBusy() && !this.board.getTile(new Pair(2, 7)).isBusy() && !this.board.getTile(new Pair(3, 7)).isBusy()){

                if(this.board.getTile(new Pair(0, 7)).isBusy()
                        && this.board.getTile(new Pair(0, 7)).getPiece().isFirstMove()
                        && Player.attacksOnTile(new Pair(2, 7), opponentLegals).isEmpty()
                        && Player.attacksOnTile(new Pair(3, 7), opponentLegals).isEmpty()
                        && this.board.getTile(new Pair (0,7)).getPiece().getPieceType().isRock()){
                    kingCastles.add(new Move.castlingOfTheKingSide(board, playerKing, new Pair(2, 7), (Rock) board.getTile(new Pair(0, 7)).getPiece(), board.getTile(new Pair(0, 7)).getTileCoordinate(), new Pair(3, 7)));

                }
            }
        }

        return kingCastles;
    }
}
