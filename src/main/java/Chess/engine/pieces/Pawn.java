package Chess.engine.pieces;

import Chess.engine.Colour;
import Chess.engine.PieceType;
import Chess.engine.board.Board;
import Chess.engine.board.Field;
import java.lang.Math;




public class Pawn extends Piece{
	public boolean enpassant = false;
	
    public Pawn(Colour colour, Field sourceField, boolean firstMove) {
        super(colour, PieceType.PAWN, sourceField, firstMove);
    }

    public boolean isPromoted(Field destinationField) {
    	boolean promotion = false;
    	Piece piece = destinationField.getPiece();
    	
    	if ((piece.colour == Colour.WHITE && destinationField.row == 7) ||
    	    (piece.colour == Colour.BLACK && destinationField.row == 0)) {
    		promotion = true;
    	}
        return promotion;
    }
    
	@Override
	public void addFieldAttack(Board board, boolean isWhiteMove) {
		if (this.sourceField.row > 0 && this.sourceField.row < 7) {
			if (this.sourceField.column > 0 && this.sourceField.column < 7) {
				if (this.getColour() == Colour.WHITE) {
					board.gameBoard[this.sourceField.column - 1][this.sourceField.row + 1].addFieldAttacker(this);
					board.gameBoard[this.sourceField.column + 1][this.sourceField.row + 1].addFieldAttacker(this);
				} else {
					board.gameBoard[this.sourceField.column - 1][this.sourceField.row - 1].addFieldAttacker(this);
					board.gameBoard[this.sourceField.column + 1][this.sourceField.row - 1].addFieldAttacker(this);
				}
			}
			if (this.sourceField.column == 0) {
				if (this.getColour() == Colour.WHITE) {
					board.gameBoard[this.sourceField.column + 1][this.sourceField.row + 1].addFieldAttacker(this);
				} else {
					board.gameBoard[this.sourceField.column + 1][this.sourceField.row - 1].addFieldAttacker(this);
				}
			}
			if (this.sourceField.column == 7) {
				if (this.getColour() == Colour.WHITE) {
					board.gameBoard[this.sourceField.column - 1][this.sourceField.row + 1].addFieldAttacker(this);
				} else {
					board.gameBoard[this.sourceField.column - 1][this.sourceField.row - 1].addFieldAttacker(this);
				}
			}
		}
	}
	
	@Override
	public void removeFieldAttack(Board board) {
		if (this.sourceField.row > 0 && this.sourceField.row < 7) {
			if (this.sourceField.column > 0 && this.sourceField.column < 7) {
				if (this.getColour() == Colour.WHITE) {
					board.gameBoard[this.sourceField.column - 1][this.sourceField.row + 1].removeFieldAttacker(this);
					board.gameBoard[this.sourceField.column + 1][this.sourceField.row + 1].removeFieldAttacker(this);
				} else {
					board.gameBoard[this.sourceField.column - 1][this.sourceField.row - 1].removeFieldAttacker(this);
					board.gameBoard[this.sourceField.column + 1][this.sourceField.row - 1].removeFieldAttacker(this);
				}
			}
			if (this.sourceField.column == 0) {
				if (this.getColour() == Colour.WHITE) {
					board.gameBoard[this.sourceField.column + 1][this.sourceField.row + 1].removeFieldAttacker(this);
				} else {
					board.gameBoard[this.sourceField.column + 1][this.sourceField.row - 1].removeFieldAttacker(this);
				}
			}
			if (this.sourceField.column == 7) {
				if (this.getColour() == Colour.WHITE) {
					board.gameBoard[this.sourceField.column - 1][this.sourceField.row + 1].removeFieldAttacker(this);
				} else {
					board.gameBoard[this.sourceField.column - 1][this.sourceField.row - 1].removeFieldAttacker(this);
				}
			}
		}
	}
	
    
	@Override
	public boolean isValidMove(Board board, Field destinationField, boolean isWhiteMove) {
    	boolean isValid = false;
    	int tmpRow = Math.abs(destinationField.row - sourceField.row);
    	int tmpCol = Math.abs(destinationField.column - sourceField.column);
    	int tmpColEnPas = destinationField.column - sourceField.column ;
    	
    	if (((sourceField.getPiece().getColour() == Colour.WHITE && (destinationField.row - sourceField.row) == 1) || 
    			(sourceField.getPiece().getColour() == Colour.BLACK && (destinationField.row - sourceField.row) == -1)) &&
    			tmpCol == 0 && destinationField.isOccupied() == false) {
    		isValid = true;
    	} else if (firstMove == true && tmpRow == 2 && tmpCol == 0 && destinationField.isOccupied() == false) {
    		isValid = true;
    	} else if (((sourceField.getPiece().getColour() == Colour.WHITE && (destinationField.row - sourceField.row) == 1) || 
    			(sourceField.getPiece().getColour() == Colour.BLACK && (destinationField.row - sourceField.row) == -1)) &&
    			tmpCol == 1 &&  destinationField.isOccupied() == true && destinationField.isEnemy(this) == true) {
    		isValid = true;
    	} else if (tmpRow == 1 && tmpCol == 1 && destinationField.isOccupied() == false &&
 			       ((sourceField.getPiece().getColour() == Colour.WHITE && sourceField.row == 4) ||
 			       (sourceField.getPiece().getColour() == Colour.BLACK && sourceField.row == 3)) &&
 			       board.gameBoard[sourceField.column + tmpColEnPas][sourceField.row].getPiece() instanceof Pawn &&
 			       ((Pawn)board.gameBoard[sourceField.column + tmpColEnPas][sourceField.row].getPiece()).enpassant == true) {
    		
    		board.pieceToEnpassnt = null;
    		board.gameBoard[sourceField.column + tmpColEnPas][sourceField.row].getPiece().removeFieldAttack(board);
    		board.gameBoard[sourceField.column + tmpColEnPas][sourceField.row].setPiece(null);
            isValid = true;
 	    }
    	
    	if (isValid == true && firstMove == true && tmpRow == 2) {
    		enpassant = true;
            if (board.pieceToEnpassnt != null) {
            	board.pieceToEnpassnt.enpassant = false;
            }
    		board.pieceToEnpassnt = this;
    	}
    	
		return isValid;
	}
    
	@Override
    public String toString() {
        return "P";
    }

}
