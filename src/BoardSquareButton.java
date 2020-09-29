import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/*
* TODO: move the mouselistener to main so we can call restart from there + we can investigate surrounding buttons from there
*
* */

public class BoardSquareButton extends JButton {

    public static final int DEFAULT_WIDTH = 100;
    public static final int DEFAULT_HEIGHT = 100;
    public static final String DEFAULT_TEXT = "?";
    public static final String MINE_TEXT = "O";
    public static final Color DEFAULT_COLOR = Color.GRAY;
    public static final Color INVESTIGATED_COLOR = Color.GREEN;
    public static final Color MINE_COLOR = Color.BLACK;
    public static final Color POTENTIAL_COLOR = Color.RED;

    private int width;
    private int height;
    private Color color;
    private int x;
    private int y;
    private int surroundingMines;
    private boolean mine;
    private boolean investigated;
    private boolean potential;

    /*
        CONSTRUCTORS
    */

    public BoardSquareButton(int x, int y, int width, int height, Color color)
    {
        this.x = x;
        this.y = y;

        initialise();

        this.setDefaultDimension(width, height);
        this.setColor(color);

        this.addNewMouseListener();
    }

    public BoardSquareButton(int x, int y)
    {
        this.x = x;
        this.y = y;

        initialise();

        this.addNewMouseListener();
    }

    private void addNewMouseListener()
    {
        super.addMouseListener(new Psymp7Main());
    }

    public void initialise()
    {
        this.setDefaultDimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        super.setFont(new Font("Ariel", Font.PLAIN, 40));
        super.setText("?");
        super.setBackground(DEFAULT_COLOR);
        this.setColor(DEFAULT_COLOR);
        this.setMine(false);
        this.setInvestigated(false);
        this.setPotential(false);
    }

    /*
        GETTERS AND SETTERS
    */
    public BoardSquareButton getSelf()
    {
        return this;
    }

    public void setDefaultDimension(int x, int y)
    {
        this.setWidth(x);
        this.setHeight(y);
        super.setPreferredSize(new Dimension(x, y));
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getSurroundingMines()
    {
        return surroundingMines;
    }

    public void setSurroundingMines(int mines)
    {
        this.surroundingMines = mines;
    }

    public Color getColor()
    {
        return color;
    }

    public void setColor(Color color)
    {
        this.color = color;
        super.setBackground(color);
    }

    public int getRow()
    {
        return x;
    }

    public void setRow(int x) { this.x = x; }

    public int getColumn()
    {
        return y;
    }

    public void setColumn(int y)
    {
        this.y = y;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine)
    {
        this.mine = mine;
    }

    public boolean isInvestigated()
    {
        return investigated;
    }

    public void setInvestigated(boolean investigated)
    {
        this.investigated = investigated;
        if (investigated)
        {
            this.setPotential(false);
            if (mine)
            {
                this.setColor(MINE_COLOR);
                this.setText(MINE_TEXT);
            } else
            {
                this.setColor(INVESTIGATED_COLOR);
                this.setText("" + this.getSurroundingMines());
            }
        }
        else
        {
            this.setColor(DEFAULT_COLOR);
            this.setText(DEFAULT_TEXT);
        }
    }
    public boolean isPotential()
    {
        return potential;
    }

    public void setPotential(boolean potential)
    {
        this.potential = potential;
        if(potential)
            this.setColor(POTENTIAL_COLOR);
        else
            this.setColor(DEFAULT_COLOR);
    }

}
