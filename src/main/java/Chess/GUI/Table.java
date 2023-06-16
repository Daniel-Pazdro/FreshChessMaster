package Chess.GUI;
import Chess.engine.Colour;
import Chess.engine.board.Board;
import Chess.engine.board.Field;
import Chess.engine.pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Table {
	private final JFrame gameFrame;
	private final BoardPanel boardPanel;
	private Board board;
	boolean isWhiteMove = true;
	private Field sourceTile = null;
	private Field destinationTile = null;
	private boolean pieceSelected = false;
	private final static Dimension sizeOfFrame = new Dimension(600,600);
	private final static Dimension sizeOfBoard = new Dimension(400, 400);
	private final static Dimension sizeOfTile = new Dimension(50,  50);
	
	private static String defaultPieceImagePath = "art/pieces/fancy/";
	private Color lightTileColour =  Color.decode("#D5DBDB");
	private Color darkTileColour =  Color.decode("#138D75");

	void closeWindow() {
		System.exit(0);
	}
	
	void startNewGame( ) {
		this.isWhiteMove = true;
		board = new Board();
		boardPanel.drawBoard(board);
	}
	

    public Table() {
		this.gameFrame = new JFrame("FreshChessMaster");
	    gameFrame.setSize(sizeOfFrame);
	    gameFrame.setLayout(new BorderLayout());
	    final JMenuBar tableMenuBar = createTableMenuBar();
	    this.gameFrame.setJMenuBar(tableMenuBar);
	    this.gameFrame.setSize(sizeOfFrame);
	    this.board = new Board();
	    boardPanel = new BoardPanel();
	    gameFrame.add(this.boardPanel, BorderLayout.CENTER);
	    this.gameFrame.setVisible(true);
    }

    private JMenuBar createTableMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        return tableMenuBar;
    }

    private JMenu createFileMenu() {
        final JMenu gameMenu = new JMenu("Game");
        final JMenuItem  startNewGameButtom = new JMenuItem("Start New Game");
        startNewGameButtom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	startNewGame();
            }
        });
        gameMenu.add(startNewGameButtom);
        final JMenuItem  exitMenuButtom = new JMenuItem("Exit");
        exitMenuButtom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	closeWindow();
            }
        });
        gameMenu.add(exitMenuButtom);
        return gameMenu;
    }

    private class BoardPanel extends JPanel {
        final TilePanel[][] boardOfTiles = new TilePanel[board.fieldsInColumn][board.fieldsInRow];

        BoardPanel() {
            super(new GridLayout(board.fieldsInRow, board.fieldsInColumn));
            for(int row = board.fieldsInRow - 1; row >= 0; row--){
            	JButton b1=new JButton("1");  
                for(int column = 0; column < board.fieldsInColumn; column++)
                {
                    final TilePanel panel = new TilePanel(this, column, row);
                    add(panel);
                }
            }
            setPreferredSize((sizeOfFrame));
            validate();
        }

        public void drawBoard(Board _board){
            removeAll();
            for(int row = board.fieldsInRow - 1; row >= 0; row--) {
                for(int column = 0; column < board.fieldsInColumn; column++)
                {
                    final TilePanel panel = new TilePanel(this, column, row);
                    add(panel);
                }
            }
            validate();
            repaint();
        }
    }
    private class TilePanel extends JPanel{
        private final int row;
        private final int column;
        
        private void processGameStatus(int status)
        {
        	if (status == 0) {
    			pieceSelected = false;
    			isWhiteMove = !isWhiteMove;
    			System.out.print("Good Move");
    		} else if (status == -1) {
    			pieceSelected = false;
    			System.out.print("Illegal move");
    		} else if (status > 0) {
    			String result = "";
    			if (status == 1) {
    				result = " Draw!!!";
    			} else if (status == 2) {
    				result = " White won!!!";
    			} else if (status == 3) {
    				result = " Black won!!!";
    			}
    			pieceSelected = false;
    			System.out.println("Start new game");
    			boardPanel.drawBoard(board);
    			JOptionPane.showMessageDialog(gameFrame, "Game over." + result);
    			int dialogButton = JOptionPane.YES_NO_OPTION;
    			int dialogResult = JOptionPane.showConfirmDialog (null, "Start new  game?","Warning",dialogButton);
    			if(dialogResult == JOptionPane.YES_OPTION) {
    				startNewGame();
    			} else {
    				closeWindow();
    			}
    			
    		}
        }
        
        
        TilePanel(final BoardPanel boardPanel, final int column, final int row) {
            super(new GridBagLayout());
            this.row = row;
            this.column = column;
            setPreferredSize(sizeOfBoard);
            assignTileColour();
            assignTIlePieceIcon(board);
            
            JPanel myPanel = this;

            
            
            
            
            addMouseListener(new MouseListener() {
                @Override public void mouseClicked(MouseEvent e) {
                    
                    if(isRightMouseButton(e)) {
                    	System.out.println("Right Clicked: Row:" + row + " Column:" + column );
                    	Field clickedTile = board.gameBoard[column][row];
                    	System.out.println("Field Attackers (" + clickedTile.getFieldsAttackers().size() + "):");
                    	for (Piece p : clickedTile.getFieldsAttackers()) {
                    		System.out.println(p.toString() + " on row:" + p.getSourceField().row + " col:" + p.getSourceField().column);
                    	}
                    	
                    }
                    else if(isLeftMouseButton(e)){
                    	System.out.println("Left Clicked: Row:" + row + " Column:" + column );
                    	
                    	Field clickedTile = board.gameBoard[column][row];
                    	
                    	if (pieceSelected == false && clickedTile.getPiece() != null)
                    	{
                			if((isWhiteMove == true && clickedTile.getPiece().getColour() == Colour.WHITE) ||
                			   (isWhiteMove == false && clickedTile.getPiece().getColour() == Colour.BLACK)) {
                				sourceTile = clickedTile;
                				pieceSelected = true;
                				System.out.print("You move " + clickedTile.getPiece().toString());
                			}
                			
                    	} else if (pieceSelected == true) {
                    		destinationTile = clickedTile;
                    		int status = board.movePiece(sourceTile, destinationTile, isWhiteMove);
                    		processGameStatus(status);
                    			
                    	}
                    }
                    
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            boardPanel.drawBoard(board);
                        }
                    });
                    
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                	if (isWhiteMove == true) {
                		myPanel.setBorder(BorderFactory.createLineBorder(Color.white, 4));
                	} else {
                		myPanel.setBorder(BorderFactory.createLineBorder(Color.black, 4));
                	}
                }

                @Override
                public void mouseExited(MouseEvent e) {
                	myPanel.setBorder(BorderFactory.createEmptyBorder());
                }
            });
            validate();

        }

            public void drawTile(final Board board){
                assignTileColour();
                assignTIlePieceIcon(board);
                validate();
                repaint();
            }

            private void assignTIlePieceIcon (final Board board) {
                this.removeAll();
                String pieceIconPath = "";
                if (board.gameBoard[column][row].getPiece() != null)
                {
                    String pieceImagePath = "";
                    try {
                        final BufferedImage image = ImageIO.read(
                        		new File(defaultPieceImagePath + board.gameBoard[column][row].getPiece().getColour().toString().substring(0 ,1) +
                        				board.gameBoard[column][row].getPiece().toString() + ".gif"));
                        add(new JLabel(new ImageIcon(image)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            private void assignTileColour() {
                if(this.row % 2 == 1){
                    setBackground(this.column % 2 == 0 ? lightTileColour : darkTileColour);
                }
                else {
                    setBackground(this.column % 2 != 0 ? lightTileColour : darkTileColour);
                }
            }
        }
    }
