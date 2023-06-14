package Chess.engine;

public enum PieceType {
    ROOK{
        @Override
        public String toString() {
            return "Rook";
        }
    },
    KNIGHT {
        @Override
        public String toString() {
            return "Knight";
        }
    },
    BISHOP {
        @Override
        public String toString() {
            return "Bishop";
        }
    },
    QUEEN {
        @Override
        public String toString() {
            return "Queen";
        }
    },
    KING {
        @Override
        public String toString() {
            return "King";
        }
    },
    PAWN {
        @Override
        public String toString() {
            return "King";
        }
    },
    
    
};

