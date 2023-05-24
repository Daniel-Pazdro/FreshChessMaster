package com.chess.engine.player;

public enum MoveStatus {

    DONE{
        @Override
        boolean isDone(){
            return true;
        }
    },

    FORBIDDEN{
        @Override
        boolean isDone() {
            return false;
        }
    },

    LEAVES_IN_CHECK{
        @Override
        boolean isDone() {
            return false;
        }
    };

    abstract boolean isDone();

}
