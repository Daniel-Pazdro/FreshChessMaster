package Chess.engine.board;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import Chess.engine.Colour;
import Chess.engine.pieces.*;

public class Board {
	public final int fieldsInRow = 8;
	public final int fieldsInColumn = 8;
	
	public final Field[][] gameBoard = new Field[fieldsInRow][fieldsInColumn];
	
	public Pawn pieceToEnpassnt = null;
	
	public Piece whiteKing= null;
	public Piece blackKing = null;

	
    public Board() {
    	newBoard();
    }

	public void newBoard() {
		for (int row = 0; row < fieldsInRow; ++row) {
			for (int column = 0; column < fieldsInRow; ++column) {
				Colour color = (row+column) % 2 == 0 ? Colour.BLACK : Colour.WHITE;
				gameBoard[column][row] = new Field(color, row, column);
			}
		}

		gameBoard[0][0].setPiece(new Rook(Colour.WHITE, gameBoard[0][0], true));
		gameBoard[7][0].setPiece(new Rook(Colour.WHITE, gameBoard[7][0], true));
		gameBoard[0][7].setPiece(new Rook(Colour.BLACK, gameBoard[0][7], true));
		gameBoard[7][7].setPiece(new Rook(Colour.BLACK, gameBoard[7][7], true));
		gameBoard[1][0].setPiece(new Knight(Colour.WHITE, gameBoard[1][0], true));
		gameBoard[6][0].setPiece(new Knight(Colour.WHITE, gameBoard[6][0], true));
		gameBoard[1][7].setPiece(new Knight(Colour.BLACK, gameBoard[1][7], true));
		gameBoard[6][7].setPiece(new Knight(Colour.BLACK, gameBoard[6][7], true));
		gameBoard[2][0].setPiece(new Bishop(Colour.WHITE, gameBoard[2][0], true));
		gameBoard[5][0].setPiece(new Bishop(Colour.WHITE, gameBoard[5][0], true));
		gameBoard[2][7].setPiece(new Bishop(Colour.BLACK, gameBoard[2][7], true));
		gameBoard[5][7].setPiece(new Bishop(Colour.BLACK, gameBoard[5][7], true));
		gameBoard[3][0].setPiece(new Queen(Colour.WHITE, gameBoard[3][0], true));
		gameBoard[3][7].setPiece(new Queen(Colour.BLACK, gameBoard[3][7], true));
		gameBoard[4][0].setPiece(new King(Colour.WHITE, gameBoard[4][0], true));
		whiteKing = gameBoard[4][0].getPiece();
		gameBoard[4][7].setPiece(new King(Colour.BLACK, gameBoard[4][7], true));
		blackKing = gameBoard[4][7].getPiece();

		for(int i = 0; i < fieldsInRow; ++i) {
			gameBoard[i][1].setPiece(new Pawn(Colour.WHITE, gameBoard[i][1], true));
			gameBoard[i][6].setPiece(new Pawn(Colour.BLACK, gameBoard[i][6], true));
		}

		calculateAttackedFields(true);
	}
    
    public void setWhiteKing(Piece k) {
    	whiteKing = k;
    }
    
    public Piece getWhiteKing() {
    	return whiteKing;
    }
    
    public void setBlackKing(Piece k) {
    	blackKing = k;
    }
    
    public Piece getBlackKing() {
    	return blackKing;
    }
    
    
    public void updateEnpassant(boolean isWhiteMove) {
		if(pieceToEnpassnt != null) {
			if ((isWhiteMove == true && pieceToEnpassnt.getColour() == Colour.BLACK) || 
				(isWhiteMove == false && pieceToEnpassnt.getColour() == Colour.WHITE)) {
				pieceToEnpassnt.enpassant = false;
				pieceToEnpassnt = null;
			}
		}
    }
    
    
    
    public void calculateAttackedFields(boolean isWhiteMove) {
    	for (int col = 0; col < fieldsInColumn; ++col) {
    		for (int row = 0; row < fieldsInRow; ++row) {
    			if (gameBoard[col][row].getPiece() != null) {
    				gameBoard[col][row].getPiece().addFieldAttack(this, isWhiteMove);
    			}
    		}
    	}
    }
    
    public int movePiece(Field from, Field to, boolean isWhiteMove) {
    	int status = -1;
    	status = from.movePiece(this, to, isWhiteMove);
    	if (status == 0) {
    		updateEnpassant(isWhiteMove);
    		
    	}
    	return status;
    }
}
