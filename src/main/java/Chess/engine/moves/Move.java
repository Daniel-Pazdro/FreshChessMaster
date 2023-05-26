package Chess.engine.moves;

import Chess.engine.Pair;
import Chess.engine.board.Board;
import Chess.engine.pieces.Pawn;
import Chess.engine.pieces.Piece;
import Chess.engine.pieces.Rock;

public abstract class Move {

    final Board board;
    final Piece pieceToMove;
    final Pair moveCoordinates;
    public static final Move NULL_MOVE = new nullMove();

    private Move(Board board, Piece pieceToMove, Pair moveCoordinates){
        this.moveCoordinates = moveCoordinates;
        this.pieceToMove = pieceToMove;
        this.board = board;
    }

    public boolean isAttack(){
        return false;
    }

    public boolean isCastlingMove(){
        return false;
    }

    public Piece getAttackedPiece(){
        return null;
    }


    public Pair getCoordinatesBeforeMove(){
        return pieceToMove.getCoordinate();
    }
    public Pair getMoveCoordinates(){
        return moveCoordinates;
    }

    public Piece getPieceToMove(){return pieceToMove;}

    public Board execute() {

        Board.Builder builder = new Board.Builder();

        for(Piece piece : board.current.getActivePieces()){
            if(!pieceToMove.equals(piece)){
                builder.setPiece(piece);
            }
        }

        for(Piece piece : this.board.current.getOpponent().getActivePieces()){
            builder.setPiece(piece);
        }

        builder.setPiece(pieceToMove. moveActualPiece(this));
        builder.setPlayer(this.board.current.getOpponent().getColour());
        return null;
    }

    public static class standardMove extends Move{
        public standardMove(final Board board, Piece piece, Pair moveCoordinates){
            super(board, piece, moveCoordinates);
        }

    }

    public static class AttackingMove extends Move{

        Piece attackedPiece;
        public AttackingMove(final Board board, Piece piece, Pair moveCoordinates, Piece attackedPiece){
            super(board, piece, moveCoordinates);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public int hashCode() {
            return attackedPiece.hashCode() + super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if(this == obj){return true;}
            if(!(obj instanceof AttackingMove)){return false;}
            return super.equals((AttackingMove) obj) && getAttackedPiece().equals(((AttackingMove) obj).getAttackedPiece());
        }

        @Override
        public Board execute() {
            return null;
        }

        @Override
        public boolean isAttack() {
            return true;
        }

        @Override
        public Piece getAttackedPiece() {
            return this.attackedPiece;
        }
    }
    public static class pawnMove extends Move{
        public pawnMove(final Board board, Piece piece, Pair moveCoordinates){
            super(board, piece, moveCoordinates);
        }
    }

    public static class pawnJumpMove extends Move{
        public pawnJumpMove(final Board board, Piece piece, Pair moveCoordinates){
            super(board, piece, moveCoordinates);
        }

        @Override
        public Board execute() {
            final Board.Builder builder = new Board.Builder();
            for(Piece piece : this.board.current.getActivePieces()){
                if(!pieceToMove.equals(piece)){builder.setPiece(piece);}
            }

            for(Piece piece : board.current.getOpponent().getActivePieces())
            { builder.setPiece(piece);}

            Pawn movedPawn = (Pawn) pieceToMove.moveActualPiece(this);
            builder.setPiece(movedPawn);
            builder.setEnPassantPawn(movedPawn);
            builder.setPlayer(board.current.getOpponent().getColour());
            return builder.build();
        }
    }

    public static class pawnAttackMove extends AttackingMove{
        public pawnAttackMove(final Board board, Piece piece, Pair moveCoordinates, Piece attackedPiece){
            super(board, piece, moveCoordinates, attackedPiece);
        }
    }

    public static class enPassantMove extends AttackingMove{
        public enPassantMove(final Board board, Piece piece, Pair moveCoordinates, Piece attackedPiece){
            super(board, piece, moveCoordinates, attackedPiece);
        }
    }

    static abstract class castling extends Move{

        protected final Pair castleRockDestination;

        protected final Rock castleRock;
        protected final Pair castleRockStartCoordinate;

        public castling(final Board board, Piece piece, Pair moveCoordinates, Rock castleRock, Pair castleRockStartCoordinate, Pair castleRockDestination){
            super(board, piece, moveCoordinates);
            this.castleRockDestination = castleRockDestination;
            this.castleRock = castleRock;
            this.castleRockStartCoordinate = castleRockStartCoordinate;
        }

        public Rock getCastleRock(){return castleRock;}

        @Override
        public boolean isCastlingMove() {
            return true;
        }

        @Override
        public Board execute() {
            final Board.Builder builder = new Board.Builder();
            for(Piece piece : this.board.current.getActivePieces()){
                if(!pieceToMove.equals(piece) && !castleRock.equals(piece)){builder.setPiece(piece);}
            }

            for(Piece piece : board.current.getOpponent().getActivePieces())
            { builder.setPiece(piece);}

            builder.setPiece(pieceToMove.moveActualPiece(this));
            builder.setPiece(new Rock(castleRockDestination.getX(), castleRockDestination.getY(), castleRock.colour));
            builder.setPlayer(board.current.getOpponent().getColour());
            return builder.build();
        }
    }

    public static class castlingOfTheKingSide extends castling{


        public castlingOfTheKingSide(final Board board, Piece piece, Pair moveCoordinates, Rock castleRock, Pair castleRockStartCoordinate, Pair castleRockDestination) {
            super(board, piece, moveCoordinates, castleRock, castleRockDestination, castleRockStartCoordinate);
        }

        @Override
        public String toString() {
            return "O-O";
        }
    }

    public static class castlingOfTheQueenSide extends castling{

        public castlingOfTheQueenSide(final Board board, Piece piece, Pair moveCoordinates, Rock castleRock, Pair castleRockStartCoordinate, Pair castleRockDestination) {
            super(board, piece, moveCoordinates, castleRock, castleRockDestination, castleRockStartCoordinate);
        }

        @Override
        public String toString() {
            return "O-O-O";
        }
    }

    public static class nullMove extends Move{
        public nullMove(){
            super(null, null, null);
        }

        @Override
        public Board execute(){
            throw new RuntimeException("Cannot make this move");
        }

    }

    public static class moveExecutor{

        public static Move createMove(final Board board, final Pair currentCoordinates, Pair destinationCoordinate){

            for(Move move : board.getAllLegalMoves()){
                if(move.getMoveCoordinates().equals(destinationCoordinate) && move.getCoordinatesBeforeMove().equals(currentCoordinates))
                {return move;}
            }

            return NULL_MOVE;
        }
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 41 +pieceToMove.hashCode();
        hash = 53 * hash + (moveCoordinates.getX()*moveCoordinates.getY()^29)*hash;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {

        if(this == obj){return true;}
        if(!(obj instanceof Move)){return false;}
        Move move = (Move) obj;
        return getMoveCoordinates().equals(move.getMoveCoordinates())&&getPieceToMove().equals(move.getPieceToMove());
    }
}
