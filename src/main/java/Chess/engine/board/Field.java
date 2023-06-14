package Chess.engine.board;

import Chess.engine.Colour;
import Chess.engine.pieces.King;
import Chess.engine.pieces.Piece;


import java.util.*;

public class Field {
	Colour tileColor = null;
    Piece piece = null;
    public int row = -1;
    public int column = -1;
    private ArrayList<Piece> fieldAttacker = new ArrayList<Piece>();
    
    public Field(Colour tileColour, int row, int column) {
    	this.tileColor = tileColour;
    	this.row = row;
    	this.column = column;
    }
    
    public void addFieldAttacker(Piece piece) {
    	fieldAttacker.add(piece);
    }
    
    public void removeFieldAttacker(Piece piece) {
    	fieldAttacker.remove(piece);
    }
    
    public ArrayList<Piece> getFieldsAttackers() {
    	return fieldAttacker;
    }
    
    public void setPiece(Piece piece) {
    	this.piece = piece;
    }
    
    public Piece getPiece() {
    	return piece;
    }
    
    public boolean isOccupied() {
    	return piece != null;
    }
    
    public boolean isAvailableForKing(boolean isWhiteMove) {
    	boolean status = true;
    	for (Piece p : fieldAttacker) {
    		if (isWhiteMove == true && p.getColour() == Colour.BLACK) {
    			status = false;
    			break;
    		} else if(isWhiteMove == false && p.getColour() == Colour.WHITE) {
    			status = false;
    			break;
    		}
    	}
    	return status;
    }
    
    public boolean isKingChecked(Colour colour) {
    	boolean checked = false;
    	
    	for (Piece p : fieldAttacker) {
    		if (this.piece instanceof King && this.piece.getColour() == colour &&
    				p.getColour() != colour)
    		{
    			checked = true;
    		}
    	}
    	return checked;
    }
    
    public boolean isEnemy(Piece piece) {
    	return this.piece.getColour() != piece.getColour();
    }
    
    public void recalculateAttackers(Board board, boolean isWhiteMove) {
    	ArrayList<Piece> fieldAttacker = new ArrayList<Piece>(this.getFieldsAttackers());
    	Iterator<Piece> pItr = fieldAttacker.iterator();
		while (pItr.hasNext()) {
			Piece p = pItr.next();
			p.removeFieldAttack(board);
			p.addFieldAttack(board, isWhiteMove);
		}
    }
    
    public int movePiece(Board board, Field destinationField, boolean isWhiteMove) {
    	return piece.move(board, destinationField, this, isWhiteMove);
    }
}
