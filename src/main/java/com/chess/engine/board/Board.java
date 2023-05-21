package com.chess.engine.board;

import com.chess.engine.Colour;
import com.chess.engine.Pair;
import com.chess.engine.moves.Move;
import com.chess.engine.pieces.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.chess.engine.pieces.Piece;

import static com.chess.engine.Colour.BLACK;
import static com.chess.engine.Colour.WHITE;
import static com.chess.engine.board.BoardFeature.numberOfTilesInColumn;

public class Board {
    private final ArrayList<ArrayList<Tile>> gameBoard;
    private final ArrayList<Piece> whitePieces;
    private final ArrayList<Piece> blackPieces;



    private Board (Builder builder){
        gameBoard = createGameBoard(builder);
        whitePieces = calculateActivePieces(gameBoard, WHITE);
        blackPieces = calculateActivePieces(gameBoard, BLACK);

        final ArrayList<Move> whiteLegalMoves = calculateValidMoves(this.whitePieces);
        final ArrayList<Move> blackLegalMoves = calculateValidMoves(this.blackPieces);

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
        builder.setPiece(new Rock(0, 7, BLACK));
        builder.setPiece(new Knight(1, 7, BLACK));
        builder.setPiece(new Bishop(2, 7, BLACK));
        builder.setPiece(new Queen(3, 7, BLACK));
        builder.setPiece(new King(4,7, BLACK));
        builder.setPiece(new Bishop(5, 7, BLACK));
        builder.setPiece(new Knight(6, 7, BLACK));
        builder.setPiece(new Rock(7, 7, BLACK));
        builder.setPiece(new Pawn(0, 6, BLACK));
        builder.setPiece(new Pawn(1, 6, BLACK));
        builder.setPiece(new Pawn(2, 6, BLACK));
        builder.setPiece(new Pawn(3, 6, BLACK));
        builder.setPiece(new Pawn(4, 6, BLACK));
        builder.setPiece(new Pawn(5, 6, BLACK));
        builder.setPiece(new Pawn(6, 6, BLACK));
        builder.setPiece(new Pawn(7, 6, BLACK));
        // White
        builder.setPiece(new Rock(0, 0, WHITE));
        builder.setPiece(new Knight(1, 0, WHITE));
        builder.setPiece(new Bishop(2, 0, WHITE));
        builder.setPiece(new Queen(3, 0, WHITE));
        builder.setPiece(new King(4,0, WHITE));
        builder.setPiece(new Bishop(5, 0, WHITE));
        builder.setPiece(new Knight(6, 0, WHITE));
        builder.setPiece(new Rock(7, 0, WHITE));
        builder.setPiece(new Pawn(0, 1, WHITE));
        builder.setPiece(new Pawn(1, 1, WHITE));
        builder.setPiece(new Pawn(2, 1, WHITE));
        builder.setPiece(new Pawn(3, 1, WHITE));
        builder.setPiece(new Pawn(4, 1, WHITE));
        builder.setPiece(new Pawn(5, 1, WHITE));
        builder.setPiece(new Pawn(6, 1, WHITE));
        builder.setPiece(new Pawn(7, 1, WHITE));
        //white to move
        builder.setPlayer(WHITE);
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


    int WhoIsPlayingNow;
    public Tile getTile(Pair candidateForMove) {
        for(int i = 0; i <numberOfTilesInColumn; i++){
            if(gameBoard.get(candidateForMove.getX()).get(i).coordinate.getY() == candidateForMove.getY()){
                return gameBoard.get(candidateForMove.getX()).get(i);
            }
        }
        return null;
    }
    public static class Builder {
        Map<Pair, Piece> boardConfiguration;
        Colour WhoIsPlayingNow;
        public Builder(){
            this.boardConfiguration = new HashMap<>();

        }

        public Builder setPiece(final Piece piece){
            boardConfiguration.put(piece.coordinate, piece);
            return this;
        }

        public Builder setPlayer(Colour WhoIsPlayingNow){
            this.WhoIsPlayingNow = WhoIsPlayingNow;
            return this;
        }


        public Board build() {
            return new Board(this);
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
}

