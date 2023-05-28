package Chess.engine.moves;

import Chess.engine.Pair;
import Chess.engine.board.Board;
import Chess.engine.board.BoardFeature;
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

        @Override
        public boolean equals(Object obj) {
            return this == obj || obj instanceof standardMove && super.equals(obj);
        }

        @Override
        public String toString() {
            return pieceToMove.getPieceType().toString() + BoardFeature.getPositionAtCoordinate(getMoveCoordinates().getX(), getMoveCoordinates().getY());
        }
    }

    public static  class MajorAttackMove extends AttackingMove{

        public MajorAttackMove(final Board board, Piece pieceMoved, final Pair coordinates, final Piece pieceAttacked){
            super(board, pieceMoved, coordinates, pieceAttacked);
        }

        @Override
        public boolean equals(Object obj) {
            return this == obj || obj instanceof pawnMove && super.equals(obj);
        }

        @Override
        public String toString() {
            return pieceToMove.getPieceType() + BoardFeature.getPositionAtCoordinate(moveCoordinates.getX(), moveCoordinates.getY());
        }
    }


    public static class PawnEnPassantAttack extends pawnAttackMove {

        public PawnEnPassantAttack(final Board board,
                                   final Piece pieceMoved,
                                   final Pair destinationCoordinate,
                                   final Piece pieceAttacked) {
            super(board, pieceMoved, destinationCoordinate, pieceAttacked);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof PawnEnPassantAttack && super.equals(other);
        }

        @Override
        public Board execute() {
            final Board.Builder builder = new Board.Builder();
            this.board.current.getActivePieces().stream().filter(piece -> !this.pieceToMove.equals(piece)).forEach(builder::setPiece);
            this.board.current.getOpponent().getActivePieces().stream().filter(piece -> !piece.equals(this.getAttackedPiece())).forEach(builder::setPiece);
            builder.setPiece(pieceToMove.moveActualPiece(this));
            builder.setPlayer(this.board.current.getOpponent().getColour());
            return builder.build();
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

        @Override
        public boolean equals(Object obj) {
            return this == obj || obj instanceof pawnMove && super.equals(obj);
        }

        @Override
        public String toString() {
            return BoardFeature.getPositionAtCoordinate(this.moveCoordinates.getX(), this.moveCoordinates.getY());
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
//            builder.setEnPassantPawn(movedPawn);
            builder.setPlayer(board.current.getOpponent().getColour());
            return builder.build();
        }
    }

    public static class pawnAttackMove extends AttackingMove{
        public pawnAttackMove(final Board board, Piece piece, Pair moveCoordinates, Piece attackedPiece){
            super(board, piece, moveCoordinates, attackedPiece);
        }

        @Override
        public boolean equals(final Object obj){
            return this == obj || obj instanceof pawnAttackMove && super.equals(obj);
        }

        @Override
        public String toString() {
            //TODO HERE !!!!!!
            return BoardFeature.getPositionAtCoordinate(this.pieceToMove.getCoordinate().getX(), pieceToMove.getCoordinate().getY()).substring(0, 1) + 'x' + BoardFeature.getPositionAtCoordinate(moveCoordinates.getX(), moveCoordinates.getY());
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

        @Override
        public int hashCode() {
            final int prime = 31;
            int res = super.hashCode();
            res = prime*res + castleRock.hashCode();
            res = prime * res + this.castleRockDestination.getX()*this.castleRockDestination.getY();
            return res;
        }

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof castling)) {
                return false;
            }
            final castling otherCastleMove = (castling) other;
            return super.equals(otherCastleMove) && this.castleRock.equals(otherCastleMove.getCastleRock());
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

        @Override
        public boolean equals(Object other) {
            return this == other || other instanceof castlingOfTheKingSide && super.equals(other);
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

        @Override
        public boolean equals(Object other) {
            return this == other || other instanceof castlingOfTheQueenSide && super.equals(other);
        }
    }

    public static class nullMove extends Move{
        public nullMove(){
            super(null, null, new Pair(-1, -1));
        }

        @Override
        public Board execute(){
            throw new RuntimeException("Cannot make this move");
        }

        @Override
        public Pair getMoveCoordinates() {
            return new Pair(-1, -1);
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

//    public static class PawnPromotion extends Move {
//        public PawnPromotion(Board board, Pawn pawn, Pair candidateForMove) {
//            super();
//        }
//    }
}
