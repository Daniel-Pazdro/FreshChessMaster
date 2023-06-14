package Chess.engine.pieces;


import Chess.engine.Colour;
import Chess.engine.PieceType;
import Chess.engine.board.Board;
import Chess.engine.board.Field;


public abstract class Piece {
    protected Colour colour = null;
    protected PieceType type = null;
    protected Field sourceField = null;
    protected boolean firstMove = true;
    
    public Piece(Colour pieceColour, PieceType pieceType, Field sourceField, boolean firstMove){
        colour = pieceColour;
        type = pieceType;
        this.sourceField = sourceField;
        this.firstMove = firstMove;
    }
    
    public void setSourceField(Field sourceField) {
    	this.sourceField = sourceField;
    	
    }
    
    public Field getSourceField() {
    	return this.sourceField;
    	
    }
    
    public Colour getColour() {
    	return colour;
    }
    
    public boolean wasMoved(){
        return !firstMove;
    }

    public abstract boolean isValidMove(Board board, Field destinationField, boolean isWhiteMove);
    
    public abstract void addFieldAttack(Board board, boolean isWhiteMove);
    public abstract void removeFieldAttack(Board board);
    
    
    public boolean canPieceShieldKing(Board board, Piece king, int row, int col) {
    	for (Piece p : board.gameBoard[col][row].getFieldsAttackers()) {
			if(p instanceof King == false) {
				if(p.getColour() == king.getColour()) {
					for (Piece pp : p.sourceField.getFieldsAttackers()) {

						
						if(pp.getColour() != p.getColour()) {
							if(pp instanceof King == false) {
								return false;
							}
						}
					}
					
					return true;
				}
			}
		}
    	return false;
    }
    public boolean isDiagonalProtector(Board board, Piece king, int row, int col) {
    	
    	return false;
    }
    
    public boolean isMate(Board board, Piece king, boolean isWhiteMove) {
    	isWhiteMove = !isWhiteMove; //we want check can oppsit king can run away so we calculate as black king move
    	boolean isMate = true;
    	if (king.getSourceField().column > 0 && king.getSourceField().row > 0) {
    		if (true == king.isValidMove(board, board.gameBoard[king.getSourceField().column -1][king.getSourceField().row], isWhiteMove)) {
    			isMate = false;
    		} else if (true == king.isValidMove(board, board.gameBoard[king.getSourceField().column -1 ][king.getSourceField().row -1], isWhiteMove)) {
    			isMate = false;
    		} else if (true == king.isValidMove(board, board.gameBoard[king.getSourceField().column][king.getSourceField().row -1], isWhiteMove)) {
    			isMate = false;
    		}
    	}
    	if (king.getSourceField().column > 0 && king.getSourceField().row < 7) {
    		if (true == king.isValidMove(board, board.gameBoard[king.getSourceField().column -1][king.getSourceField().row], isWhiteMove)) {
    			isMate = false;
    		} else if (true == king.isValidMove(board, board.gameBoard[king.getSourceField().column -1][king.getSourceField().row +1], isWhiteMove)) {
    			isMate = false;
    		} else if (true == king.isValidMove(board, board.gameBoard[king.getSourceField().column][king.getSourceField().row +1], isWhiteMove)) {
    			isMate = false;
    		} 
    	}
    	if (king.getSourceField().column < 7 && king.getSourceField().row < 7) {
    		if (true == king.isValidMove(board, board.gameBoard[king.getSourceField().column][king.getSourceField().row + 1], isWhiteMove)) {
    			isMate = false;
    		} else if (true == king.isValidMove(board, board.gameBoard[king.getSourceField().column + 1][king.getSourceField().row + 1], isWhiteMove)) {
    			isMate = false;
    		} else if (true == king.isValidMove(board, board.gameBoard[king.getSourceField().column + 1][king.getSourceField().row], isWhiteMove)) {
    			isMate = false;
    		} 
    	}
    	if (king.getSourceField().column < 7 && king.getSourceField().row > 0) {
    		if (true == king.isValidMove(board, board.gameBoard[king.getSourceField().column][king.getSourceField().row - 1], isWhiteMove)) {
    			isMate = false;
    		} else if (true == king.isValidMove(board, board.gameBoard[king.getSourceField().column + 1][king.getSourceField().row], isWhiteMove)) {
    			isMate = false;
    		} else if (true == king.isValidMove(board, board.gameBoard[king.getSourceField().column - 1][king.getSourceField().row - 1], isWhiteMove)) {
    			isMate = false;
    		}
    	}
    	
    	if(isMate == true) {
    		int countAttackers = 0;
    		for (Piece p : king.getSourceField().getFieldsAttackers()) {
    			if(king.getColour() != p.getColour()) {
    				countAttackers++;
    				if(countAttackers == 2) {
    					return isMate;
    				}
    			}
    		}
    		
    		for (Piece p : king.getSourceField().getFieldsAttackers()) {
    			if(king.getColour() != p.getColour()) {
    				for (Piece pp : p.getSourceField().getFieldsAttackers()) {
    					if (pp.getColour() == king.getColour())
    					{
    						if(pp instanceof King != true) {
    							isMate = !canPieceShieldKing(board, king, pp.getSourceField().row, king.getSourceField().column);
	    						if (isMate == false) {
	    							return isMate;
	    						}
    						}
    					}
    				}
    				if (p instanceof Knight) {
    					isMate = true;
    					return isMate;
    				} else if (p instanceof Rook) {
    					if (p.getSourceField().column == king.getSourceField().column) {
    						int tmpRow = king.getSourceField().row;
    						while (tmpRow != p.getSourceField().row) {
	    						if (king.sourceField.row < p.sourceField.row) {
	    							tmpRow++;
	    						} else {
	    							tmpRow--;
	    						}
	    						isMate = !canPieceShieldKing(board, king, tmpRow, king.getSourceField().column);
	    						if (isMate == false) {
	    							return isMate;
	    						}
    						}
    					}
    					if (p.getSourceField().row == king.getSourceField().row) {
    						int tmpCol = king.getSourceField().column;
    						while (tmpCol != p.getSourceField().row) {
	    						if (king.sourceField.row < p.sourceField.row) {
	    							tmpCol++;
	    						} else {
	    							tmpCol--;
	    						}
	    						isMate = !canPieceShieldKing(board, king, king.getSourceField().row, tmpCol);
	    						if (isMate == false) {
	    							return isMate;
	    						}
    						}
    					}
    				} else if (p instanceof Bishop) {
    					if (king.getSourceField().column < p.getSourceField().column) {
    						if(king.getSourceField().row < p.getSourceField().row) {
	    						int tmpRow = king.getSourceField().row;
	    						int tmpCol = king.getSourceField().column;
	    						while (tmpRow != p.getSourceField().row) {
		    						if (king.sourceField.row < p.sourceField.row) {
		    							tmpRow++;
		    							tmpCol++;
		    						}
		    						isMate = !canPieceShieldKing(board, king, tmpRow, tmpCol);
		    						if (isMate == false) {
		    							return isMate;
		    						}
	    						}
    						}
    						if(king.getSourceField().row > p.getSourceField().row) {
	    						int tmpRow = king.getSourceField().row;
	    						int tmpCol = king.getSourceField().column;
	    						while (tmpRow != p.getSourceField().row) {
		    						if (king.sourceField.row < p.sourceField.row) {
		    							tmpRow--;
		    							tmpCol++;
		    						}
		    						isMate = !canPieceShieldKing(board, king, tmpRow, tmpCol);
		    						if (isMate == false) {
		    							return isMate;
		    						}
	    						}
    						}
    					}
    					if (king.getSourceField().column > p.getSourceField().column) {
    						if(king.getSourceField().row > p.getSourceField().row) {
	    						int tmpRow = king.getSourceField().row;
	    						int tmpCol = king.getSourceField().column;
	    						while (tmpRow != p.getSourceField().row) {
		    						if (king.sourceField.row > p.sourceField.row) {
		    							tmpRow--;
		    							tmpCol--;
		    						}
		    						isMate = !canPieceShieldKing(board, king, tmpRow, tmpCol);
		    						if (isMate == false) {
		    							return isMate;
		    						}
	    						}
    						}
    						if(king.getSourceField().row < p.getSourceField().row) {
	    						int tmpRow = king.getSourceField().row;
	    						int tmpCol = king.getSourceField().column;
	    						while (tmpRow != p.getSourceField().row) {
		    						if (king.sourceField.row < p.sourceField.row) {
		    							tmpRow++;
		    							tmpCol--;
		    						}
		    						isMate = !canPieceShieldKing(board, king, tmpRow, tmpCol);
		    						if (isMate == false) {
		    							return isMate;
		    						}
	    						}
    						}
    					}
    				} else if (p instanceof Queen) {
    					if (p.getSourceField().column == king.getSourceField().column) {
    						int tmpRow = king.getSourceField().row;
    						while (tmpRow != p.getSourceField().row) {
	    						if (king.sourceField.row < p.sourceField.row) {
	    							tmpRow++;
	    						} else {
	    							tmpRow--;
	    						}
	    						isMate = !canPieceShieldKing(board, king, tmpRow, king.getSourceField().column);
	    						if (isMate == false) {
	    							return isMate;
	    						}
    						}
    					}
    					if (p.getSourceField().row == king.getSourceField().row) {
    						int tmpCol = king.getSourceField().column;
    						while (tmpCol != p.getSourceField().row) {
	    						if (king.sourceField.row < p.sourceField.row) {
	    							tmpCol++;
	    						} else {
	    							tmpCol--;
	    						}
	    						isMate = !canPieceShieldKing(board, king, king.getSourceField().row, tmpCol);
	    						if (isMate == false) {
	    							return isMate;
	    						}
    						}
    					}
    					if (king.getSourceField().column < p.getSourceField().column) {
    						if(king.getSourceField().row < p.getSourceField().row) {
	    						int tmpRow = king.getSourceField().row;
	    						int tmpCol = king.getSourceField().column;
	    						while (tmpRow != p.getSourceField().row) {
		    						if (king.sourceField.row > p.sourceField.row) {
		    							tmpRow++;
		    							tmpCol++;
		    						}
		    						canPieceShieldKing(board, king, tmpRow, tmpCol);
	    						}
    						}
    						if(king.getSourceField().row > p.getSourceField().row) {
	    						int tmpRow = king.getSourceField().row;
	    						int tmpCol = king.getSourceField().column;
	    						while (tmpRow != p.getSourceField().row) {
		    						if (king.sourceField.row > p.sourceField.row) {
		    							tmpRow--;
		    							tmpCol++;
		    						}
		    						isMate = !canPieceShieldKing(board, king, tmpRow, tmpCol);
		    						if (isMate == false) {
		    							return isMate;
		    						}
	    						}
    						}
    					}
    					if (king.getSourceField().column > p.getSourceField().column) {
    						if(king.getSourceField().row > p.getSourceField().row) {
	    						int tmpRow = king.getSourceField().row;
	    						int tmpCol = king.getSourceField().column;
	    						while (tmpRow != p.getSourceField().row) {
		    						if (king.sourceField.row > p.sourceField.row) {
		    							tmpRow--;
		    							tmpCol--;
		    						}
		    						isMate = !canPieceShieldKing(board, king, tmpRow, tmpCol);
		    						if (isMate == false) {
		    							return isMate;
		    						}
	    						}
    						}
    						if(king.getSourceField().row < p.getSourceField().row) {
	    						int tmpRow = king.getSourceField().row;
	    						int tmpCol = king.getSourceField().column;
	    						while (tmpRow != p.getSourceField().row) {
		    						if (king.sourceField.row < p.sourceField.row) {
		    							tmpRow++;
		    							tmpCol--;
		    						}
		    						isMate = !canPieceShieldKing(board, king, tmpRow, tmpCol);
		    						if (isMate == false) {
		    							return isMate;
		    						}
	    						}
    						}
    					}
    				}	
    			}
    		}
    	}
    	
    	return isMate;
    }
    
    public boolean isPat(Board board, Piece king, boolean isWhiteMove) {
    	boolean isPat = false; // when implement change this to true and prove it is false... remove comment
    	

    	
		return isPat;
	}

    
    public int move(Board board, Field destinationField, Field sourceField, boolean isWhiteMove) {
    	int status = -1;
    	
    	if (isValidMove(board, destinationField, isWhiteMove) == true) {
    		status = 0;
    		firstMove = false;
    		
    		this.removeFieldAttack(board);
    		this.getSourceField().recalculateAttackers(board, isWhiteMove);
    		

    		if (destinationField.getPiece() != null) {
    			destinationField.getPiece().removeFieldAttack(board);
    		}
    		
    		Piece tmpPiece = destinationField.getPiece();
    		
    		destinationField.setPiece(sourceField.getPiece());
    		
    		

    		if(destinationField.getPiece() instanceof Pawn && ((Pawn)destinationField.getPiece()).isPromoted(destinationField)) {

    			Piece p = new Queen(sourceField.getPiece().getColour(), destinationField, false);
    			destinationField.setPiece(p);
    		}

    		destinationField.getPiece().setSourceField(destinationField);
    		destinationField.getPiece().addFieldAttack(board, isWhiteMove);
    		destinationField.recalculateAttackers(board, isWhiteMove);
    		
    		
    		sourceField.setPiece(null);

    		sourceField.recalculateAttackers(board, isWhiteMove);
    		
    		((King)board.getWhiteKing()).setChecked(false);
    		((King)board.getBlackKing()).setChecked(false);
    		
    		if (board.getWhiteKing().getSourceField().isKingChecked(((King)board.getWhiteKing()).getColour())){
    			((King)board.getWhiteKing()).setChecked(true);
    		}
    		
    		if (board.getBlackKing().getSourceField().isKingChecked(((King)board.getBlackKing()).getColour())){
    			((King)board.getBlackKing()).setChecked(true);
    		}
    		
    		
    		if ((isWhiteMove == true && ((King)board.getWhiteKing()).getChecked() == true)) {
    			System.out.println("White King unshielded!");
    			status = -1;
    			
    			this.addFieldAttack(board, isWhiteMove);
    			
    			destinationField.getPiece().removeFieldAttack(board);
    			destinationField.recalculateAttackers(board, isWhiteMove);
    			if (this.getSourceField().getPiece() != null) {
    				this.getSourceField().getPiece().removeFieldAttack(board);
    				
        		}
    			sourceField.setPiece(destinationField.getPiece());
    			if(sourceField.getPiece() instanceof King ) {
    				if (((King)destinationField.getPiece()).getColour() == Colour.WHITE) {
        				board.setWhiteKing(destinationField.getPiece());
        			} else {
        				board.setBlackKing(destinationField.getPiece());
        			}
        		}
    			
    			sourceField.getPiece().setSourceField(sourceField);
    			sourceField.getPiece().addFieldAttack(board, isWhiteMove);
    			sourceField.recalculateAttackers(board, isWhiteMove);
    			destinationField.setPiece(tmpPiece);
    			if(destinationField.getPiece() != null) {
    				destinationField.getPiece().addFieldAttack(board, isWhiteMove);
    			}
    			
    			destinationField.recalculateAttackers(board, isWhiteMove);
 
    		} else if ((isWhiteMove == false && ((King)board.getBlackKing()).getChecked() == true)) {
    			System.out.println("Black King unshielded!");
    			status = -1;
    			this.addFieldAttack(board, isWhiteMove);
    			
    			destinationField.getPiece().removeFieldAttack(board);
    			destinationField.recalculateAttackers(board, isWhiteMove);
    			if (this.getSourceField().getPiece() != null) {
    				this.getSourceField().getPiece().removeFieldAttack(board);
    				
        		}
    			sourceField.setPiece(destinationField.getPiece());
    			if(sourceField.getPiece() instanceof King ) {
    				if (((King)destinationField.getPiece()).getColour() == Colour.WHITE) {
        				board.setWhiteKing(destinationField.getPiece());
        			} else {
        				board.setBlackKing(destinationField.getPiece());
        			}
        		}
    			
    			sourceField.getPiece().setSourceField(sourceField);
    			sourceField.getPiece().addFieldAttack(board, isWhiteMove);
    			sourceField.recalculateAttackers(board, isWhiteMove);
    			destinationField.setPiece(tmpPiece);
    			if(destinationField.getPiece() != null) {
    				destinationField.getPiece().addFieldAttack(board, isWhiteMove);
    			}
    			
    			destinationField.recalculateAttackers(board, isWhiteMove);

    		}
    		
    		if (isWhiteMove == true) {
				if (((King)board.getBlackKing()).getChecked() && true == isMate(board, board.getBlackKing(), isWhiteMove)) {
					System.out.println("White won!!! Congratulations!!!");
					status = 1;
				}
			} else {
				if (((King)board.getWhiteKing()).getChecked() && true == isMate(board, board.getWhiteKing(), isWhiteMove)) {
					System.out.println("Black won!!! Congratulations!!!");
					status = 1;
				}
			}
    		
    		if (isWhiteMove == true) {
				if (((King)board.getBlackKing()).getChecked() == false && true == isPat(board, board.getBlackKing(), isWhiteMove)) {
					System.out.println("We have got Pat!");
					status = 1;
				}
			} else {
				if (((King)board.getWhiteKing()).getChecked() ==false && true == isPat(board, board.getWhiteKing(), isWhiteMove)) {
					System.out.println("We have got Pat!");
					status = 1;
				}
			}
    	}
    	
    	return status;
    }
    
    @Override
    public String toString() {
        return this.colour + " " + this.type;
    }
}
