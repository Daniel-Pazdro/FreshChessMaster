package Chess.engine.board;

import Chess.engine.Colour;
import Chess.engine.Pair;
import Chess.engine.moves.Move;
import Chess.engine.pieces.*;
import Chess.engine.player.WhitePlayer;
import Chess.engine.player.BlackPlayer;
import Chess.engine.player.Player;
import Chess.engine.pieces.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static Chess.engine.board.BoardFeature.numberOfTilesInColumn;

public class Board {
    private final ArrayList<ArrayList<Tile>> gameBoard;
    private final ArrayList<Piece> whitePieces;
    private final ArrayList<Piece> blackPieces;


    public final WhitePlayer whitePlayer;
    public final BlackPlayer blackPlayer;
    public final Player current;

    private Board (Builder builder){
        gameBoard = createGameBoard(builder);
        whitePieces = calculateActivePieces(gameBoard, Colour.WHITE);
        blackPieces = calculateActivePieces(gameBoard, Colour.BLACK);

        final ArrayList<Move> whiteLegalMoves = calculateValidMoves(this.whitePieces);
        final ArrayList<Move> blackLegalMoves = calculateValidMoves(this.blackPieces);
        this.whitePlayer = new WhitePlayer(this, whiteLegalMoves, blackLegalMoves);
        this.blackPlayer = new BlackPlayer(this, whiteLegalMoves, blackLegalMoves);
        this.current = builder.whoIsPlayingNow.choosePlayer(whitePlayer,blackPlayer);

    }

    private ArrayList<Move> calculateValidMoves(ArrayList<Piece> setOfPieces) {
        final ArrayList<Move> legalMoves = new ArrayList();
        for(final Piece piece: setOfPieces){
            legalMoves.addAll(piece.AvailableMoves(this));
        }

        return legalMoves;
    }

    private ArrayList<Piece> calculateActivePieces(ArrayList<ArrayList<Tile>> gameBoard, Colour colour) {
        final ArrayList<Piece> activePieces = new ArrayList<>();
        for(final ArrayList<Tile> list: gameBoard){
            for(final Tile tile : list){
                if(tile.isBusy()){
                    final Piece piece = tile.getPiece();
                    if(piece.colour == colour){
                        activePieces.add(piece);
                    }
                }
            }
        }
        return activePieces;
    }


    public static Board createStandardBoardImpl() {
        final Builder builder = new Builder();
        // Black
        builder.setPiece(new Rock(0, 7, Colour.BLACK));
        builder.setPiece(new Knight(1, 7, Colour.BLACK));
        builder.setPiece(new Bishop(2, 7, Colour.BLACK));
        builder.setPiece(new Queen(3, 7, Colour.BLACK));
        builder.setPiece(new King(4,7, Colour.BLACK));
        builder.setPiece(new Bishop(5, 7, Colour.BLACK));
        builder.setPiece(new Knight(6, 7, Colour.BLACK));
        builder.setPiece(new Rock(7, 7, Colour.BLACK));
        builder.setPiece(new Pawn(0, 6, Colour.BLACK));
        builder.setPiece(new Pawn(1, 6, Colour.BLACK));
        builder.setPiece(new Pawn(2, 6, Colour.BLACK));
        builder.setPiece(new Pawn(3, 6, Colour.BLACK));
        builder.setPiece(new Pawn(4, 6, Colour.BLACK));
        builder.setPiece(new Pawn(5, 6, Colour.BLACK));
        builder.setPiece(new Pawn(6, 6, Colour.BLACK));
        builder.setPiece(new Pawn(7, 6, Colour.BLACK));
        // White
        builder.setPiece(new Rock(0, 0, Colour.WHITE));
        builder.setPiece(new Knight(1, 0, Colour.WHITE));
        builder.setPiece(new Bishop(2, 0, Colour.WHITE));
        builder.setPiece(new Queen(3, 0, Colour.WHITE));
        builder.setPiece(new King(4,0, Colour.WHITE));
        builder.setPiece(new Bishop(5, 0, Colour.WHITE));
        builder.setPiece(new Knight(6, 0, Colour.WHITE));
        builder.setPiece(new Rock(7, 0, Colour.WHITE));
        builder.setPiece(new Pawn(0, 1, Colour.WHITE));
        builder.setPiece(new Pawn(1, 1, Colour.WHITE));
        builder.setPiece(new Pawn(2, 1, Colour.WHITE));
        builder.setPiece(new Pawn(3, 1, Colour.WHITE));
        builder.setPiece(new Pawn(4, 1, Colour.WHITE));
        builder.setPiece(new Pawn(5, 1, Colour.WHITE));
        builder.setPiece(new Pawn(6, 1, Colour.WHITE));
        builder.setPiece(new Pawn(7, 1, Colour.WHITE));
        //white to move
        builder.setPlayer(Colour.WHITE);
        //build the board
        return builder.build();
    }


    private static ArrayList<ArrayList<Tile>> createGameBoard(Builder builder) {
        final ArrayList<ArrayList<Tile>> tiles = new ArrayList(numberOfTilesInColumn){
        };

        for(int i = 0; i < numberOfTilesInColumn; i++){
            tiles.add(new ArrayList<>(numberOfTilesInColumn));
            for(int j = 0; j < numberOfTilesInColumn; j++){
                tiles.get(i).add(Tile.createTile(i, j, builder.boardConfiguration.get(new Pair(i, j))));
            }
        }
        return tiles;
    }


//    Colour whoIsPlayingNow;
    public Tile getTile(Pair candidateForMove) {
        for(int i = 0; i <numberOfTilesInColumn; i++){
            if(gameBoard.get(candidateForMove.getX()).get(i).coordinate.getY() == candidateForMove.getY()){
                return gameBoard.get(candidateForMove.getX()).get(i);
            }
        }
        return null;
    }

    public Collection<Move> getAllLegalMoves() {
        return Stream.concat(this.whitePlayer.getLegalMoves().stream(), this.blackPlayer.getLegalMoves().stream()).collect(Collectors.toList());
    }

    public static class Builder {
        Map<Pair, Piece> boardConfiguration;
        public Colour whoIsPlayingNow;
        public Builder(){
            this.boardConfiguration = new HashMap<>();

        }

        public Builder setPiece(final Piece piece){
            boardConfiguration.put(piece.coordinate, piece);
            return this;
        }

        public Builder setPlayer(Colour WhoIsPlayingNow){
            this.whoIsPlayingNow = WhoIsPlayingNow;
            return this;
        }


        public Board build() {
            return new Board(this);
        }

        public void setEnPassantPawn(Pawn movedPawn) {
        }
    }

    @Override
    public String toString() {
        final StringBuilder printedBoard = new StringBuilder();
        for(int i = numberOfTilesInColumn-1; i >=0 ; i--){
            for(int j = 0; j < numberOfTilesInColumn; j++){
                String Text = this.gameBoard.get(j).get(i).toString();
                printedBoard.append(String.format("%3s", Text));
            }
            printedBoard.append('\n');
        }
        return printedBoard.toString();
    }

    public ArrayList<Piece> getBlackPieces(){
        return blackPieces;
    }
    public ArrayList<Piece> getWhitePieces(){
        return whitePieces;
    }
}

