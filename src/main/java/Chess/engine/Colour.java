package Chess.engine;

import Chess.engine.player.BlackPlayer;
import Chess.engine.player.Player;
import Chess.engine.player.WhitePlayer;

public enum Colour {

    WHITE{
        @Override
       public int getDirection() {
            return 1;
        }

        @Override
        public int getOppositeDirection() {
            return -1;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public boolean isPawnPromotionSquare(Pair coordinate) {
            return coordinate.getY() == 7;
        }

        @Override
        public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
            return whitePlayer;
        }
    },
    BLACK{
        @Override
        public int getDirection() {
            return -1;
        }

        @Override
        public int getOppositeDirection() {
            return 1;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isPawnPromotionSquare(Pair coordinate) {
            return coordinate.getY() == 0;
        }

        @Override
        public Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) {
            return blackPlayer;
        }
    };


    public abstract int getDirection();
    public abstract int getOppositeDirection();
    public abstract boolean isBlack();
    public abstract boolean isWhite();
    public abstract boolean isPawnPromotionSquare(Pair coordinate);


    public abstract Player choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer);
}
