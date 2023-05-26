package Chess.GUI;
import Chess.engine.Pair;
import Chess.engine.board.BoardFeature;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Table {
        private final JFrame gameFrame;
        private final BoardPanel boardPanel;
        private final static Dimension sizeOfFrame = new Dimension(600,600);
        private final static Dimension sizeOfBoard = new Dimension(400, 400);
        private final static Dimension sizeOfTile = new Dimension(50,  50);

        private Color lightTileColour =  Color.decode("#D5DBDB");
        private Color darkTileColour =  Color.decode("#138D75");




    public Table() {
            this.gameFrame = new JFrame("FreshChessMaster");
            gameFrame.setSize(sizeOfFrame);
            gameFrame.setLayout(new BorderLayout());
            final JMenuBar tableMenuBar = createTableMenuBar();
            this.gameFrame.setJMenuBar(tableMenuBar);
            this.gameFrame.setSize(sizeOfFrame);
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
                validate();
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
