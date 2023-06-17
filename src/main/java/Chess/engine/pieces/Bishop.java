package Chess.engine.pieces;

import Chess.engine.Colour;
import Chess.engine.PieceType;
import Chess.engine.board.Board;
import Chess.engine.board.Field;


public class Bishop extends Piece{
    public Bishop(Colour colour, Field sourceField, boolean firstMove) {
        super(colour, PieceType.BISHOP, sourceField, firstMove);
    }

    private boolean isObstacle(Board board, Field destinationField) {
    	boolean isObstacle = false;
    	int myRow = this.sourceField.row;
    	int myCol = this.sourceField.column;
    	int destRow = destinationField.row;
    	int destCol = destinationField.column;
    	int curRow = myRow;
    	int curCol = myCol;
    	
    	while (curCol != destCol && curRow != destRow) {
    		if (myRow < destRow) {
    			curRow++;
    		} else {
    			curRow--;
    		}
    		if (myCol < destCol) {
    			curCol++;
    		} else {
    			curCol--;
    		}
    		if (curCol != destCol && curRow != destRow && board.gameBoard[curCol][curRow].getPiece() != null)
    		{
    			isObstacle = true;
    			break;
    		}
    	}
    	
    	return isObstacle;
    }
    
	@Override
	public void addFieldAttack(Board board, boolean isWhiteMove) {
		int curRow = this.sourceField.row;
		int curCol = this.sourceField.column;
		boolean behindObstacle = false;
		while (curCol < 7 && curRow < 7) {
			curRow++;
			curCol++;
			if(behindObstacle == false) {
				board.gameBoard[curCol][curRow].addFieldAttacker(this);
			} else {
				board.gameBoard[curCol][curRow].removeFieldAttacker(this);
			}
			if (board.gameBoard[curCol][curRow].getPiece() != null  && 
					(board.gameBoard[curCol][curRow].getPiece() instanceof King) == false) {
				behindObstacle = true;
			}
		}
		
		curRow = this.sourceField.row;
		curCol = this.sourceField.column;
		behindObstacle = false;
		while (curCol < 7 && curRow > 0) {
			curRow--;
			curCol++;
			if(behindObstacle == false) {
				board.gameBoard[curCol][curRow].addFieldAttacker(this);
			} else {
				board.gameBoard[curCol][curRow].removeFieldAttacker(this);
			}
			if (board.gameBoard[curCol][curRow].getPiece() != null  && 
					(board.gameBoard[curCol][curRow].getPiece() instanceof King) == false) {
				behindObstacle = true;
			}
		}
		
		curRow = this.sourceField.row;
		curCol = this.sourceField.column;
		behindObstacle = false;
		while (curCol > 0 && curRow > 0) {
			curRow--;
			curCol--;
			if(behindObstacle == false) {
				board.gameBoard[curCol][curRow].addFieldAttacker(this);
			} else {
				board.gameBoard[curCol][curRow].removeFieldAttacker(this);
			}
			if (board.gameBoard[curCol][curRow].getPiece() != null  && 
					(board.gameBoard[curCol][curRow].getPiece() instanceof King) == false) {
				behindObstacle = true;
			}
		}

		curRow = this.sourceField.row;
		curCol = this.sourceField.column;
		behindObstacle = false;
		while (curCol > 0 && curRow < 7) {
			curRow++;
			curCol--;
			if(behindObstacle == false) {
				board.gameBoard[curCol][curRow].addFieldAttacker(this);
			} else {
				board.gameBoard[curCol][curRow].removeFieldAttacker(this);
			}
			if (board.gameBoard[curCol][curRow].getPiece() != null && 
					(board.gameBoard[curCol][curRow].getPiece() instanceof King) == false) {
				behindObstacle = true;
			}
		}
	}
	
	@Override
	public void removeFieldAttack(Board board) {
		int curRow = this.sourceField.row;
		int curCol = this.sourceField.column;
		
		while (curCol < 7 && curRow < 7) {
			curRow++;
			curCol++;
			board.gameBoard[curCol][curRow].removeFieldAttacker(this);
			if (board.gameBoard[curCol][curRow].getPiece() != null  && 
					(board.gameBoard[curCol][curRow].getPiece() instanceof King) == false) {
				break;
			}
		}
		
		curRow = this.sourceField.row;
		curCol = this.sourceField.column;
		while (curCol < 7 && curRow > 0) {
			curRow--;
			curCol++;
			board.gameBoard[curCol][curRow].removeFieldAttacker(this);
			if (board.gameBoard[curCol][curRow].getPiece() != null  && 
					(board.gameBoard[curCol][curRow].getPiece() instanceof King) == false) {
				break;
			}
		}
		
		curRow = this.sourceField.row;
		curCol = this.sourceField.column;
		while (curCol > 0 && curRow > 0) {
			curRow--;
			curCol--;
			board.gameBoard[curCol][curRow].removeFieldAttacker(this);
			if (board.gameBoard[curCol][curRow].getPiece() != null  && 
					(board.gameBoard[curCol][curRow].getPiece() instanceof King) == false) {
				break;
			}
		}
		
		curRow = this.sourceField.row;
		curCol = this.sourceField.column;
		while (curCol > 0 && curRow < 7) {
			curRow++;
			curCol--;
			board.gameBoard[curCol][curRow].removeFieldAttacker(this);
			if (board.gameBoard[curCol][curRow].getPiece() != null  && 
					(board.gameBoard[curCol][curRow].getPiece() instanceof King) == false) {
				break;
			}
		}
	}
    
	@Override
	public boolean isValidMove(Board board, Field destinationField, boolean isWhiteMove) {
		boolean isValid = false;
    	int tmpRow = Math.abs(destinationField.row - sourceField.row);
    	int tmpCol = Math.abs(destinationField.column - sourceField.column);
    	
    	if (tmpRow == tmpCol && 
    		destinationField.isOccupied() == false && isObstacle(board, destinationField) == false) {
    		isValid = true;
    	} else if (tmpRow == tmpCol &&
    			destinationField.isOccupied() == true &&  isObstacle(board, destinationField) == false &&
    			destinationField.getPiece().getColour() != this.getColour()) {
    		isValid = true;
    	}

		return isValid;
	}

	
	
	@Override
    public String toString() {
        return "B";
    }

}