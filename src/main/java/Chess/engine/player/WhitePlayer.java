package Chess.engine.player;

import Chess.engine.Colour;
import Chess.engine.Pair;
import Chess.engine.board.Board;
import Chess.engine.moves.Move;
import Chess.engine.pieces.Piece;
import Chess.engine.pieces.Rock;

import java.util.ArrayList;

public class WhitePlayer extends Player {
    public WhitePlayer(Board board, ArrayList<Move> myLegalMoves, ArrayList<Move> opponentLegalMoves) {
        super(board, myLegalMoves, opponentLegalMoves);
    }

    @Override
    public ArrayList<Piece> getActivePieces() {
        return board.getWhitePieces();
    }

    @Override
    public Colour getColour() {
        return Colour.WHITE;
    }

    @Override
    public Player getOpponent() {
        return board.blackPlayer;
    }

    @Override
    protected ArrayList<Move> calcKingCastles(ArrayList<Move> playerLegals, ArrayList<Move> opponentLegals) {

        final ArrayList<Move> kingCastles = new ArrayList<>();

        if(this.playerKing.isFirstMove() && !this.isCheck()){

            if(!this.board.getTile(new Pair(5, 0)).isBusy() && !this.board.getTile(new Pair(6, 0)).isBusy()){

                if(this.board.getTile(new Pair(7, 0)).isBusy() && this.board.getTile(new Pair(7, 0)).getPiece().isFirstMove()){
                    kingCastles.add(null);
                }
            }
        }

        if(this.playerKing.isFirstMove() && !this.isCheck()){

            if(!this.board.getTile(new Pair(5, 0)).isBusy() && !this.board.getTile(new Pair(6, 0)).isBusy()){

                if(this.board.getTile(new Pair(7, 0)).isBusy() && this.board.getTile(new Pair(7, 0)).getPiece().isFirstMove()){
                    if (Player.attacksOnTile(new Pair(5, 0), opponentLegals).isEmpty() && Player.attacksOnTile(new Pair(6, 0), opponentLegals).isEmpty() && this.board.getTile(new Pair(7, 0)).getPiece().getPieceType().isRock()) {
                        kingCastles.add(new Move.castlingOfTheKingSide(board, playerKing, new Pair(6, 0), (Rock) board.getTile(new Pair(6, 0)).getPiece(), board.getTile(new Pair(6, 0)).getTileCoordinate(), new Pair(5, 0)));
                    }
                }
            }
            if(!this.board.getTile(new Pair(1, 0)).isBusy() && !this.board.getTile(new Pair(2, 0)).isBusy() && !this.board.getTile(new Pair(3, 0)).isBusy()){

                if(this.board.getTile(new Pair(0, 0)).isBusy() && this.board.getTile(new Pair(0, 0)).getPiece().isFirstMove()){
                    kingCastles.add(new Move.castlingOfTheKingSide(board, playerKing, new Pair(2, 0), (Rock) board.getTile(new Pair(0, 0)).getPiece(), board.getTile(new Pair(0, 0)).getTileCoordinate(), new Pair(5, 0)));
                }
            }
        }

        return kingCastles;
    }
}