package Chess.GUI;
import Chess.engine.Pair;
import Chess.engine.board.Board;
import Chess.engine.board.BoardFeature;
import Chess.engine.board.Tile;
import Chess.engine.moves.Move;
import Chess.engine.pieces.Piece;
import Chess.engine.player.MoveExecution;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Table {
        private final JFrame gameFrame;
        private final BoardPanel boardPanel;
        private Board board;
        private Tile sourceTile;
        private Tile destinationTile;
        private Piece MovedPiece;
        private final static Dimension sizeOfFrame = new Dimension(600,600);
        private final static Dimension sizeOfBoard = new Dimension(400, 400);
        private final static Dimension sizeOfTile = new Dimension(50,  50);

        private static String defaultPieceImagePath = "art/pieces/fancy/";
        private Color lightTileColour =  Color.decode("#D5DBDB");
        private Color darkTileColour =  Color.decode("#138D75");




    public Table() {
            this.gameFrame = new JFrame("FreshChessMaster");
            gameFrame.setSize(sizeOfFrame);
            gameFrame.setLayout(new BorderLayout());
            final JMenuBar tableMenuBar = createTableMenuBar();
            this.gameFrame.setJMenuBar(tableMenuBar);
            this.gameFrame.setSize(sizeOfFrame);
            board = Board.createStandardBoardImpl();
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
            final JMenu fileMenu = new JMenu("File");
            final JMenuItem openPGN = new JMenuItem("Load PGN File");
            openPGN.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("open file");
                }
            });
            fileMenu.add(openPGN);
            final JMenuItem  exitMenuButtom = new JMenuItem("Exit");
            exitMenuButtom.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            fileMenu.add(exitMenuButtom);
            return fileMenu;
        }

        private class BoardPanel extends JPanel {
            final ArrayList<TilePanel> boardOfTiles;

            BoardPanel() {
                super(new GridLayout(8, 8));
                this.boardOfTiles = new ArrayList<>();
                for(int i = BoardFeature.numberOfTilesInColumn - 1; i >= 0; i--){
                    for(int j = 0; j < BoardFeature.numberOfTilesInColumn; j++)
                    {
                        final TilePanel panel = new TilePanel(this, j, i);
                        this.boardOfTiles.add(panel);
                        add(panel);
                    }
                }
                setPreferredSize((sizeOfFrame));
                validate();
            }

            public void drawBoard(Board _board){
                removeAll();
                for(TilePanel tilePanel: boardOfTiles){
                    tilePanel.drawTile(board);
                    add(tilePanel);
                }
                validate();
                repaint();
            }
        }
        private class TilePanel extends JPanel{
            private final int tileCoordinateX;
            private final int tileCoordinateY;
            TilePanel(final BoardPanel boardPanel, final int tileCoordinateX, final int tileCoordinateY){

                super(new GridBagLayout());
                this.tileCoordinateX = tileCoordinateX;
                this.tileCoordinateY = tileCoordinateY;
                setPreferredSize(sizeOfBoard);
                assignTileColour();
                assignTIlePieceIcon(board);
                addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(isRightMouseButton(e)) {

                            sourceTile = null;
                            destinationTile = null;
                            MovedPiece = null;
                            System.out.println("CLICK");
                            StringBuilder s= new StringBuilder();
                            s.append(board.getTile(new Pair(tileCoordinateX, tileCoordinateY)).getPiece().toString());
                            System.out.println(s);
                            //this part represents the first click
                        }
                        else if(isLeftMouseButton(e)){
                            if(sourceTile == null){
                                sourceTile = board.getTile(new Pair(tileCoordinateX, tileCoordinateY));
                                MovedPiece = sourceTile.getPiece();
                                if(MovedPiece == null) {
                                    sourceTile = null;
                                }
                            }
                            else{
                                destinationTile = board.getTile(new Pair(tileCoordinateX, tileCoordinateY));
                                final Move move = Move.moveExecutor.createMove(board, sourceTile.getTileCoordinate(), destinationTile.getTileCoordinate());
                                final MoveExecution trans = board.current.executeMove(move);
                                if(trans.getStatus().isDone()){
                                    board = trans.getTransitionBoard();
                                }
                                sourceTile = null;
                                destinationTile = null;
                                MovedPiece = null;
                            }
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    boardPanel.drawBoard(board);
                                }
                            });
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

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
                if (board.getTile(new Pair(tileCoordinateX, tileCoordinateY)).isBusy())
                {
                    String pieceImagePath = "";
                    try {
                        final BufferedImage image = ImageIO.read(new File(defaultPieceImagePath + board.getTile(new Pair(tileCoordinateX, tileCoordinateY)).getPiece().getColour().toString().substring(0 ,1) +
                                board.getTile(new Pair(tileCoordinateX, tileCoordinateY)).getPiece().toString() + ".gif"));
                        add(new JLabel(new ImageIcon(image)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            private void assignTileColour() {
                if(this.tileCoordinateX % 2 == 1){
                    setBackground(this.tileCoordinateY % 2 == 0 ? lightTileColour : darkTileColour);
                }
                else {
                    setBackground(this.tileCoordinateY % 2 != 0 ? lightTileColour : darkTileColour);
                }
            }
        }
    }
