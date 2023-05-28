package Chess.engine.player;

public enum MoveStatus {

    DONE{
        @Override
        public boolean isDone(){
            return true;
        }
    },

    FORBIDDEN{
        @Override
        public boolean isDone() {
            return false;
        }
    },

    LEAVES_IN_CHECK{
        @Override
        public boolean isDone() {
            return false;
        }
    };

    public abstract boolean isDone();

}
