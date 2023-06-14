package Chess.engine.pieces;

import Chess.engine.Colour;
import Chess.engine.PieceType;
import Chess.engine.board.Board;
import Chess.engine.board.Field;

public class Knight extends Piece{
    public Knight(Colour colour, Field sourceField, boolean firstMove) {
        super(colour, PieceType.KNIGHT, sourceField, firstMove);
    }
    

	@Override
	public void addFieldAttack(Board board, boolean isWhiteMove) {
		if (this.sourceField.row < 7 && this.sourceField.column + 1 < 7 ) {
			board.gameBoard[this.sourceField.column + 2][this.sourceField.row + 1].addFieldAttacker(this);
		}
		if (this.sourceField.row < 7 && this.sourceField.column - 1 > 0 ) {
			board.gameBoard[this.sourceField.column - 2][this.sourceField.row + 1].addFieldAttacker(this);
		}
		if (this.sourceField.row > 0 && this.sourceField.column + 1 < 7 ) {
			board.gameBoard[this.sourceField.column + 2][this.sourceField.row - 1].addFieldAttacker(this);
		}
		if (this.sourceField.row > 0 && this.sourceField.column - 1 > 0 ) {
			board.gameBoard[this.sourceField.column - 2][this.sourceField.row - 1].addFieldAttacker(this);
		}
		if (this.sourceField.row + 1 < 7 && this.sourceField.column < 7 ) {
			board.gameBoard[this.sourceField.column + 1][this.sourceField.row + 2].addFieldAttacker(this);
		}
		if (this.sourceField.row + 1 < 7 && this.sourceField.column > 0 ) {
			board.gameBoard[this.sourceField.column - 1][this.sourceField.row + 2].addFieldAttacker(this);
		}
		if (this.sourceField.row - 1> 0 && this.sourceField.column < 7 ) {
			board.gameBoard[this.sourceField.column + 1][this.sourceField.row - 2].addFieldAttacker(this);
		}
		if (this.sourceField.row - 1 > 0 && this.sourceField.column > 0 ) {
			board.gameBoard[this.sourceField.column - 1][this.sourceField.row - 2].addFieldAttacker(this);
		}
	}
	
	@Override
	public void removeFieldAttack(Board board) {
		if (this.sourceField.row < 7 && this.sourceField.column + 1 < 7 ) {
			board.gameBoard[this.sourceField.column + 2][this.sourceField.row + 1].removeFieldAttacker(this);
		}
		if (this.sourceField.row < 7 && this.sourceField.column - 1 > 0 ) {
			board.gameBoard[this.sourceField.column - 2][this.sourceField.row + 1].removeFieldAttacker(this);
		}
		if (this.sourceField.row > 0 && this.sourceField.column + 1 < 7 ) {
			board.gameBoard[this.sourceField.column + 2][this.sourceField.row - 1].removeFieldAttacker(this);
		}
		if (this.sourceField.row > 0 && this.sourceField.column - 1 > 0 ) {
			board.gameBoard[this.sourceField.column - 2][this.sourceField.row - 1].removeFieldAttacker(this);
		}
		if (this.sourceField.row + 1 < 7 && this.sourceField.column < 7 ) {
			board.gameBoard[this.sourceField.column + 1][this.sourceField.row + 2].removeFieldAttacker(this);
		}
		if (this.sourceField.row + 1 < 7 && this.sourceField.column > 0 ) {
			board.gameBoard[this.sourceField.column - 1][this.sourceField.row + 2].removeFieldAttacker(this);
		}
		if (this.sourceField.row - 1> 0 && this.sourceField.column < 7 ) {
			board.gameBoard[this.sourceField.column + 1][this.sourceField.row - 2].removeFieldAttacker(this);
		}
		if (this.sourceField.row - 1 > 0 && this.sourceField.column > 0 ) {
			board.gameBoard[this.sourceField.column - 1][this.sourceField.row - 2].removeFieldAttacker(this);
		}
	}
	
    
    @Override
	public boolean isValidMove(Board board, Field destinationField, boolean isWhiteMove) {
    	boolean isValid = false;
    	int tmpRow = Math.abs(destinationField.row - sourceField.row);
    	int tmpCol = Math.abs(destinationField.column - sourceField.column);
    	
    	if (((tmpRow == 2 && tmpCol == 1) || (tmpRow == 1 && tmpCol == 2)) && destinationField.isOccupied() == false) {
    		isValid = true;
    	} else if (((tmpRow == 2 && tmpCol == 1) || (tmpRow == 1 && tmpCol == 2)) && 
    			   destinationField.isOccupied() == true && destinationField.isEnemy(this) == true) {
    		isValid = true;
    	}

		return isValid;
	}

    
    
	@Override
    public String toString() {
        return "H";
    }
}