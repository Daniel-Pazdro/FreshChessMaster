package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
    public class Table {
        private final JFrame gameFrame;
        private final static Dimension sizeOfTable = new Dimension(822,822);

        public Table() {
            this.gameFrame = new JFrame("FreshChessMaster");
            final JMenuBar tableMenuBar = createTableMenuBar();
            this.gameFrame.setJMenuBar(tableMenuBar);
            this.gameFrame.setSize(sizeOfTable);
            this.gameFrame.setVisible(true);
        }

        private JMenuBar createTableMenuBar() {
            final JMenuBar tableMenuBar = new JMenuBar();
            tableMenuBar.add(createFileMenu());
            return tableMenuBar;
        }

        private JMenu createFileMenu() {
            final JMenu fileMenu = new JMenu();
            final JMenuItem openPGN = new JMenuItem();
            openPGN.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("open file");
                }
            });
            fileMenu.add(openPGN);
            return fileMenu;
        }
    }
