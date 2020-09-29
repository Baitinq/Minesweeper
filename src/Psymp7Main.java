import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Psymp7Main implements MouseListener
{
    public final static int WIDTH = 600;
    public final static int HEIGHT = 600;
    public final static int NUM_BUTTONS_X = 6;
    public final static int NUM_BUTTONS_Y = 6;
    public final static int NUM_MINES = 5;

    private static Board board;

    public static void main(String[] args) {
        new Psymp7Main().start();
    }

    public void start() {
        if(NUM_MINES > (NUM_BUTTONS_X * NUM_BUTTONS_Y))
        {
            System.out.println("Too many mines!");
            return;
        }
        JFrame mainframe = new JFrame("MineSweeper");

        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setResizable(false);
        mainframe.setSize(WIDTH, HEIGHT);
        mainframe.setLayout(new GridLayout(NUM_BUTTONS_Y, NUM_BUTTONS_X));

        board = new Board(NUM_BUTTONS_Y, NUM_BUTTONS_X, NUM_MINES);
        board.importButtons(mainframe);
        mainframe.pack();
        mainframe.setVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent)
    {
        BoardSquareButton source = (BoardSquareButton) mouseEvent.getSource();

        if (mouseEvent.getButton() == MouseEvent.BUTTON1) // left click
        {
            source.setInvestigated(true);
            if(board.hasWon())
            {
                board.finished();
                JOptionPane.showMessageDialog(null, "You won!");
                board.reset();
            }
            else if(board.hasLost())
            {
                board.finished();
                JOptionPane.showMessageDialog(null, "You loose!");
                board.reset();
            }
            else if(source.getSurroundingMines() == 0 && !source.isMine())
            {
                board.investigateButton(source);
            }
            System.out.println("Clicked in x: " + source.getColumn() + " y: " + source.getRow());
        }
        if (mouseEvent.getButton() == MouseEvent.BUTTON3)//right click
        {
            if(!source.isInvestigated())
                source.setPotential(!source.isPotential());
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {}

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {}

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}

    @Override
    public void mouseExited(MouseEvent mouseEvent) {}
}