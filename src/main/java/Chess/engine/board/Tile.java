package Chess.engine.board;


import Chess.engine.Pair;
import Chess.engine.pieces.Piece;

import java.util.HashMap;
import java.util.Map;

import static Chess.engine.board.BoardFeature.numberOfTilesInColumn;

public abstract class Tile {
   // protected final int number;

    Pair coordinate;

    private static final Map<Pair, EmptyTile> emptyBoard = createBoard();


    private static Map<Pair, EmptyTile> createBoard() {
        final Map<Pair, EmptyTile> emptyTileMap = new HashMap<>();
        for(int i = 0; i<numberOfTilesInColumn; i++){
            for(int j = 0; j<numberOfTilesInColumn; j++)
              emptyTileMap.put(new Pair(i, j), new EmptyTile(i, j));
        }

        return emptyTileMap;
    }

    public static Tile createTile(final int x, final int y, final Piece piece){
        if (piece != null) {return new BusyTile(x, y, piece);}
        for(Pair tmp: emptyBoard.keySet()) {
            if (tmp.getX() == x && tmp.getY() == y)
                return emptyBoard.get(tmp);
        }
        return new BusyTile(x, y, piece);
    }

    public abstract boolean isBusy();

    Tile(int a, int b){coordinate = new Pair(a, b);}
    public abstract Piece getPiece();

    public Pair getTileCoordinate() {
        return coordinate;
    }

    public static final class EmptyTile extends Tile{
        EmptyTile(final int x, final int y){
            super(x, y);
        }


        @Override
        public boolean isBusy() {
            return false;
        }
        @Override
        public String toString(){
            return " - ";
        }


        @Override
        public Piece getPiece() {
            return null;
        }
    }

    public static class BusyTile extends Tile{

        private final Piece piece;
        BusyTile(final int x, final int y, Piece pieceOn){
            super(x, y);
            piece = pieceOn;

        }

        @java.lang.Override
        public boolean isBusy() {
            return true;
        }

        @Override
        public String toString(){
            return getPiece().isBlack() ? getPiece().toString().toLowerCase() : getPiece().toString();
        }

        @java.lang.Override
        public Piece getPiece() {
            return piece;
        }
    }


}
