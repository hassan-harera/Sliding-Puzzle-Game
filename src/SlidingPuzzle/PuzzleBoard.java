package SlidingPuzzle;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import java.util.Random;
import javax.swing.border.TitledBorder;

public class PuzzleBoard extends JFrame {

    final JFrame frame = new JFrame("S L I D I N G   P U Z Z L E   G A M E ");
    final JPanel[][] panels = new JPanel[4][4];
    final Label[][] labels = new Label[4][4];
    Boolean vis[] = new Boolean[16];
    ArrayList<Integer> list;
    int currRow, currCol;
    int prevRow, prevCol;

    public PuzzleBoard() {
        Arrays.fill(vis, false);
        list = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                panels[i][j] = new JPanel();
                panels[i][j].setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.CENTER, TitledBorder.BOTTOM, new Font("times new roman", Font.PLAIN, 12), Color.yellow));
                panels[i][j].setBackground(Color.red);

                Label l = new Label();
                labels[i][j] = new Label();
                l.setLocation(panels[i][j].getX() / 2, panels[i][j].getY() / 2);
                l.setFont(new Font(l.getName(), Font.BOLD, 36));

                while (list.size() < 15) {
                    Random r = new Random();
                    int next = r.nextInt(16);
                    if (!vis[next] && next != 0) {
                        l.setText(next + "");
                        labels[i][j].setText(next + "");
                        panels[i][j].add(l);
                        vis[next] = true;
                        list.add(next);
                        break;
                    }
                }

                if (i == 3 && j == 3) {
                    currRow = i;
                    currCol = j;
                    prevCol = 5;
                    prevRow = 5;
                    panels[i][j].setBackground(Color.black);
                    addMouseListener(i, j);
                }

                frame.getContentPane().add(panels[i][j]);
            }
        }

        frame.setLayout(new GridLayout(4, 4));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        frame.setBackground(Color.WHITE);
        frame.setVisible(true);

    }

    void addMouseListener(int i, int j) {
        if (i > 0) {
            panels[i - 1][j].addMouseListener(new MouseEventDemo(i - 1, j));
        }
        if (i < 3) {
            panels[i + 1][j].addMouseListener(new MouseEventDemo(i + 1, j));
        }
        if (j < 3) {
            panels[i][j + 1].addMouseListener(new MouseEventDemo(i, j + 1));
        }
        if (j > 0) {
            panels[i][j - 1].addMouseListener(new MouseEventDemo(i, j - 1));
        }
    }

    void removeMouseListener(int i, int j) {
        if (i > 0) {
            panels[i - 1][j].removeMouseListener(panels[i - 1][j].getMouseListeners()[0]);
        }
        if (i < 3) {
            panels[i + 1][j].removeMouseListener(panels[i + 1][j].getMouseListeners()[0]);
        }
        if (j < 3) {
            panels[i][j + 1].removeMouseListener(panels[i][j + 1].getMouseListeners()[0]);
        }
        if (j > 0) {
            panels[i][j - 1].removeMouseListener(panels[i][j - 1].getMouseListeners()[0]);
        }
    }

    class MouseEventDemo implements MouseListener {

        private int row, col;

        MouseEventDemo(int i, int j) {
            row = i;
            col = j;
        }

        public void mouseClicked(MouseEvent e) {
            panels[currRow][currCol].setBackground(Color.red);
            panels[currRow][currCol].add(panels[row][col].getComponent(0));
            labels[currRow][currCol].setText(labels[row][col].getText());
            panels[row][col].setBackground(Color.black);
            panels[row][col].removeAll();
            labels[row][col].setText("");
            prevCol = currCol;
            prevRow = currRow;
            currRow = row;
            currCol = col;
            removeMouseListener(prevRow, prevCol);
            addMouseListener(currRow, currCol);
            checkWin();
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

        private void checkWin() {
            int num = 1;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (num <= 15 && !labels[i][j].getText().equals(num + "")) {
                        return;
                    }
                    num++;
                }
            }
            frame.dispose();
            new Winner().setVisible(true);
        }
    }
}
