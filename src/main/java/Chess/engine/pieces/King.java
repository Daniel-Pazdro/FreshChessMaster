package Chess.engine.pieces;

import Chess.engine.Colour;
import Chess.engine.PieceType;
import Chess.engine.board.Board;
import Chess.engine.board.Field;

public class King extends Piece{
	boolean isChecked = false;
	
    public King(Colour colour, Field sourceField, boolean firstMove) {
        super(colour, PieceType.KING, sourceField, firstMove);
    }

    public void setChecked(boolean isChecked) {
    	this.isChecked = isChecked;
    }
    
    public boolean getChecked( ) {
    	return isChecked;
    }
    
    private boolean isObstacle(Board board, Field destinationField) {
    	boolean isObstacle = false;
    	int myRow = this.sourceField.row;
    	int myCol = this.sourceField.column;
    	int destRow = destinationField.row;
    	int destCol = destinationField.column;
    	int curRow = myRow;
    	int curCol = myCol;
    	
    	while (curCol != destCol || curRow != destRow) {
    		if (myRow == destRow && myCol < destCol) {
    			curCol++;
    		} else if (myRow == destRow) {
    			curCol--;
    		}
    		if ((curCol != destCol || curRow != destRow) && board.gameBoard[curCol][curRow].getPiece() != null)
    		{
    			isObstacle = true;
    			break;
    		}
    	}
    	
    	return isObstacle;
    }
    
    private boolean isRookMoved(Board board, Field destinationField) {
    	boolean isMoved = false;
    	if (this.sourceField.column < destinationField.column) {
    		if(board.gameBoard[7][this.sourceField.row].getPiece() != null && board.gameBoard[7][this.sourceField.row].getPiece().type == PieceType.ROOK && 
    		   board.gameBoard[7][this.sourceField.row].getPiece().firstMove == true ) {
    			isMoved = false;
    		}
    	} else if (this.sourceField.column > destinationField.column) {
    		if(board.gameBoard[0][this.sourceField.row].getPiece() != null && board.gameBoard[0][this.sourceField.row].getPiece().type == PieceType.ROOK && 
    		   board.gameBoard[0][this.sourceField.row].getPiece().firstMove == true ) {
    			isMoved = false;
    		}
    	}

    	return isMoved;
    }
    
	private void swapRook(Board board, Field destinationField, boolean isWhiteMove) {
		if (this.sourceField.column < destinationField.column) {
			sourceField.getPiece().removeFieldAttack(board);
			board.gameBoard[7][this.sourceField.row].getPiece().removeFieldAttack(board);
			board.gameBoard[7][this.sourceField.row].recalculateAttackers(board, isWhiteMove);
			board.gameBoard[5][this.sourceField.row].setPiece(board.gameBoard[7][this.sourceField.row].getPiece());
			board.gameBoard[5][this.sourceField.row].getPiece().sourceField.column = 5;
			board.gameBoard[5][this.sourceField.row].getPiece().addFieldAttack(board, isWhiteMove);
			board.gameBoard[5][this.sourceField.row].recalculateAttackers(board, isWhiteMove);
			board.gameBoard[7][this.sourceField.row].setPiece(null);
    	} else if (this.sourceField.column > destinationField.column) {
    		sourceField.getPiece().removeFieldAttack(board);
			board.gameBoard[0][this.sourceField.row].getPiece().removeFieldAttack(board);
			board.gameBoard[0][this.sourceField.row].recalculateAttackers(board, isWhiteMove);
    		board.gameBoard[3][this.sourceField.row].setPiece(board.gameBoard[0][this.sourceField.row].getPiece());
    		board.gameBoard[3][this.sourceField.row].getPiece().sourceField.column = 3;
    		board.gameBoard[3][this.sourceField.row].getPiece().addFieldAttack(board, isWhiteMove);
    		board.gameBoard[3][this.sourceField.row].getPiece().getSourceField().recalculateAttackers(board, isWhiteMove);
    		board.gameBoard[0][this.sourceField.row].setPiece(null);
    	}
	}
    
	@Override
	public void addFieldAttack(Board board, boolean isWhiteMove) {
		if (this.sourceField.row > 0 && this.sourceField.column > 0) {
			board.gameBoard[this.sourceField.column - 1][this.sourceField.row - 1].addFieldAttacker(this);
		}
		if (this.sourceField.row > 0 && this.sourceField.column < 7) {
			board.gameBoard[this.sourceField.column + 1][this.sourceField.row - 1].addFieldAttacker(this);
		}
		if (this.sourceField.row < 7 && this.sourceField.column > 0) {
			board.gameBoard[this.sourceField.column - 1][this.sourceField.row + 1].addFieldAttacker(this);
		}
		if (this.sourceField.row < 7 && this.sourceField.column < 7) {
			board.gameBoard[this.sourceField.column + 1][this.sourceField.row + 1].addFieldAttacker(this);
		}
		if (this.sourceField.column > 0) {
			board.gameBoard[this.sourceField.column - 1][this.sourceField.row].addFieldAttacker(this);
		}
		if (this.sourceField.column < 7) {
			board.gameBoard[this.sourceField.column + 1][this.sourceField.row].addFieldAttacker(this);
		}
		if (this.sourceField.row > 0) {
			board.gameBoard[this.sourceField.column][this.sourceField.row - 1].addFieldAttacker(this);
		}
		if (this.sourceField.row < 7) {
			board.gameBoard[this.sourceField.column][this.sourceField.row + 1].addFieldAttacker(this);
		}
	}
	
	@Override
	public void removeFieldAttack(Board board) {
		if (this.sourceField.row > 0 && this.sourceField.column > 0) {
			board.gameBoard[this.sourceField.column - 1][this.sourceField.row - 1].removeFieldAttacker(this);
		}
		if (this.sourceField.row > 0 && this.sourceField.column < 7) {
			board.gameBoard[this.sourceField.column + 1][this.sourceField.row - 1].removeFieldAttacker(this);
		}
		if (this.sourceField.row < 7 && this.sourceField.column > 0) {
			board.gameBoard[this.sourceField.column - 1][this.sourceField.row + 1].removeFieldAttacker(this);
		}
		if (this.sourceField.row < 7 && this.sourceField.column < 7) {
			board.gameBoard[this.sourceField.column + 1][this.sourceField.row + 1].removeFieldAttacker(this);
		}
		if (this.sourceField.column > 0) {
			board.gameBoard[this.sourceField.column - 1][this.sourceField.row].removeFieldAttacker(this);
		}
		if (this.sourceField.column < 7) {
			board.gameBoard[this.sourceField.column + 1][this.sourceField.row].removeFieldAttacker(this);
		}
		if (this.sourceField.row > 0) {
			board.gameBoard[this.sourceField.column][this.sourceField.row - 1].removeFieldAttacker(this);
		}
		if (this.sourceField.row < 7) {
			board.gameBoard[this.sourceField.column][this.sourceField.row + 1].removeFieldAttacker(this);
		}
	}
	
	@Override
	public boolean isValidMove(Board board, Field destinationField, boolean isWhiteMove) {
    	boolean isValid = false;
    	int tmpRow = Math.abs(destinationField.row - sourceField.row);
    	int tmpCol = Math.abs(destinationField.column - sourceField.column);
    	
    	if ((tmpRow < 2 && tmpCol < 2) && destinationField.isOccupied() == false && 
    			destinationField.isAvailableForKing(isWhiteMove) == true) {
    		this.isChecked = false;
    		isValid = true;
    	} else if (tmpRow == 0 && tmpCol == 2 && firstMove == true && 
    			destinationField.isOccupied() == false && destinationField.isAvailableForKing(isWhiteMove) == true && 
    			isObstacle(board, destinationField) == false && isRookMoved(board, destinationField) == false) {
    		this.isChecked = false;
    		isValid = true;
    	} else if ((tmpRow < 2 && tmpCol < 2) && 
    			destinationField.isOccupied() == true && destinationField.isEnemy(this) == true &&
    			destinationField.isAvailableForKing(isWhiteMove) == true) {
    		this.isChecked = false;
    		isValid = true;
 	    }
    	
    	if (isValid == true && firstMove == true && tmpCol == 2) {
    		int checkLongSwap = sourceField.column - destinationField.column - 2;
    		if(checkLongSwap == 0) {
    			if(board.gameBoard[1][destinationField.row].getPiece() != null){
    				return false;
    			}
    		}
    		swapRook(board, destinationField, isWhiteMove);
    	}
    	
		return isValid;
	}
	
	@Override
    public String toString() {
        return "K";
    }
}
