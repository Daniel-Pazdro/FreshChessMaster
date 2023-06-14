package Chess.engine;

public enum Colour {
    WHITE{
        @Override
        public String toString() {
            return "White";
        }

        public boolean isBlack() {
            return false;
        }

        public boolean isWhite() {
            return true;
        }
    },
    BLACK{
        @Override
        public String toString() {
            return "Black";
        }

        public boolean isBlack() {
            return false;
        }

        public boolean isWhite() {
            return false;
        }
    };
}
