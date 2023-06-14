package Chess.engine.pieces;

import Chess.engine.Colour;
import Chess.engine.PieceType;
import Chess.engine.board.Board;
import Chess.engine.board.Field;

public class Rook extends Piece{
    public Rook(Colour colour, Field sourceField, boolean firstMove) {
        super(colour, PieceType.ROOK, sourceField, firstMove);
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
    		if (destCol == myCol && myRow < destRow) {
    			curRow++;
    		} else if (destCol == myCol) {
    			curRow--;
    		}
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
    
	@Override
	public void addFieldAttack(Board board, boolean isWhiteMove) {
		int curRow = this.sourceField.row;
		int curCol = this.sourceField.column;
		boolean behindObstacle = false;
		while (curCol < 7) {
			curCol++;
			if(behindObstacle == false) {
				board.gameBoard[curCol][curRow].addFieldAttacker(this);
			} else {
				board.gameBoard[curCol][curRow].removeFieldAttacker(this);
			}
			if (board.gameBoard[curCol][curRow].getPiece() != null) {
				behindObstacle = true;
			}
		}
		
		curRow = this.sourceField.row;
		curCol = this.sourceField.column;
		behindObstacle = false;
		while (curCol > 0) {
			curCol--;
			if(behindObstacle == false) {
				board.gameBoard[curCol][curRow].addFieldAttacker(this);
			} else {
				board.gameBoard[curCol][curRow].removeFieldAttacker(this);
			}
			if (board.gameBoard[curCol][curRow].getPiece() != null) {
				behindObstacle = true;
			}
		}
		
		curRow = this.sourceField.row;
		curCol = this.sourceField.column;
		behindObstacle = false;
		while (curRow < 7) {
			curRow++;
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
		
		curRow = this.sourceField.row;
		curCol = this.sourceField.column;
		behindObstacle = false;
		while (curRow > 0) {
			curRow--;
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
		
		while (curCol < 7) {
			curCol++;
			board.gameBoard[curCol][curRow].removeFieldAttacker(this);
			if (board.gameBoard[curCol][curRow].getPiece() != null && 
					(board.gameBoard[curCol][curRow].getPiece() instanceof King) == false) {
				break;
			}
		}
		
		curRow = this.sourceField.row;
		curCol = this.sourceField.column;
		while (curCol > 0) {
			curCol--;
			board.gameBoard[curCol][curRow].removeFieldAttacker(this);
			if (board.gameBoard[curCol][curRow].getPiece() != null && 
					(board.gameBoard[curCol][curRow].getPiece() instanceof King) == false) {
				break;
			}
		}
		
		curRow = this.sourceField.row;
		curCol = this.sourceField.column;
		while (curRow < 7) {
			curRow++;
			board.gameBoard[curCol][curRow].removeFieldAttacker(this);
			if (board.gameBoard[curCol][curRow].getPiece() != null && 
					(board.gameBoard[curCol][curRow].getPiece() instanceof King) == false) {
				break;
			}
		}
		
		curRow = this.sourceField.row;
		curCol = this.sourceField.column;
		while (curRow > 0) {
			curRow--;
			board.gameBoard[curCol][curRow].removeFieldAttacker(this);
			if (board.gameBoard[curCol][curRow].getPiece() != null && 
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
    	
    	if (((tmpRow != 0 && tmpCol == 0) || (tmpRow == 0 && tmpCol != 0)) && 
    		destinationField.isOccupied() == false && isObstacle(board, destinationField) == false) {
    		isValid = true;
    	} else if (((tmpRow != 0 && tmpCol == 0) || (tmpRow == 0 && tmpCol != 0))  && 
    			destinationField.isOccupied() == true &&  isObstacle(board, destinationField) == false &&
    			destinationField.getPiece().getColour() != this.getColour()) {
    		isValid = true;
    	}

		return isValid;
	}

	@Override
    public String toString() {
        return "R";
    }


}
