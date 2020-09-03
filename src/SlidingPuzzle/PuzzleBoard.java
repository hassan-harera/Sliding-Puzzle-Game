package SlidingPuzzle;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import javax.swing.*;
import java.util.Random;
import javax.swing.border.TitledBorder;

public class PuzzleBoard extends JFrame {

    final JPanel[][] panels = new JPanel[4][4];
    final JLabel[][] labels = new JLabel[4][4];
    Boolean vis[] = new Boolean[16];
    int currRow, currCol;
    Boolean isCompleated;

    public PuzzleBoard() {
        setTitle("S L I D I N G   P U Z Z L E   G A M E ");
        Arrays.fill(vis, false);
        isCompleated = false;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                panels[i][j] = new JPanel();
                panels[i][j].setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(TitledBorder.BOTTOM)));
                panels[i][j].setBackground(Color.red);
                panels[i][j].addMouseListener(new MouseEventDemo(i, j));

                labels[i][j] = new JLabel("");
                labels[i][j].setFont(new Font("BOLD", Font.BOLD, 36));

                if (i == 3 && j == 3) {
                    isCompleated = true;
                    currRow = i;
                    currCol = j;
                    panels[i][j].setBackground(Color.black);
                }

                while (!isCompleated) {
                    Random r = new Random();
                    int next = r.nextInt(16);

                    if (!vis[next] && next != 0) {
                        labels[i][j].setText(next + "");
                        vis[next] = true;
                        break;
                    }
                }
                panels[i][j].add(labels[i][j]);
               add(panels[i][j]);
            }
        }

        setLayout(new GridLayout(4, 4));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,600);
        setLocationRelativeTo(null);
        setBackground(Color.WHITE);
        setVisible(true);

    }

    class MouseEventDemo implements MouseListener {

        private int row, col;

        MouseEventDemo(int i, int j) {
            row = i;
            col = j;
        }

        public void mouseClicked(MouseEvent e) {
            if ((Math.abs(currRow - row) == 1 && currCol == col) || (Math.abs(currCol - col) == 1 && currRow == row)) {
                panels[currRow][currCol].setBackground(Color.red);
                panels[row][col].setBackground(Color.black);

                String s1 = labels[row][col].getText();
                String s2 = labels[currRow][currCol].getText();

                labels[currRow][currCol].setText(s1);
                labels[row][col].setText(s2);
                
                currRow = row;
                currCol = col;
                checkWin();
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
            dispose();
            new Winner().setVisible(true);
        }
    }
}
