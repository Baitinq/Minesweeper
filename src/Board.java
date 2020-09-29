import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class Board {

    private int x;
    private int y;
    private int mines;
    private ArrayList<BoardSquareButton> buttons = new ArrayList<>();
    private ArrayList<BoardSquareButton> investigateQueue = new ArrayList<>();

    public Board(int x, int y, int mines)
    {
        this.x = x;
        this.y = y;
        this.mines = mines;

        System.out.println("Board x: " + getColumns() + " y: " + getRows());

        this.createButtons(x, y);
        this.initialiseButtons();
        this.createMines(mines);
        this.updateSurrounding();
    }

    public void importButtons(JFrame frame)
    {
        for(BoardSquareButton button : buttons)
        {
            frame.add(button);
        }
    }

    public void initialiseButtons()
    {
        for(BoardSquareButton button : buttons)
        {
            button.initialise();
        }
    }

    public void finished()
    {
        for(BoardSquareButton button : buttons)
        {
            button.setInvestigated(true);
        }
    }

    public void reset()
    {
        this.initialiseButtons();
        this.createMines(mines);
        this.updateSurrounding();
    }

    private void createButtons(int x, int y) {
        for (int i = 1; i <= x; i++) {
            for(int j = 1; j <= y; j++) {
                this.storeButton(new BoardSquareButton(i, j));
                System.out.println("Adding button with column: " + i + "and row: " + j);
            }
        }
    }

    private void createMines(int mines)
    {
        Random random = new Random();
        System.out.println("Creating "+ mines + " mines!");
        while(mines > 0)
        {
            int x = random.nextInt(this.getColumns()) + 1;
            int y = random.nextInt(this.getRows()) + 1;

            System.out.print("(Setting mines) Querying for button with x: " + y + "and y: " + x);

            BoardSquareButton button = this.getButton(y, x);
            if(!button.isMine())
            {
                button.setMine(true);
                mines--;
                System.out.println("Is now mine!");
            }
        }
    }

    public void investigateButton(BoardSquareButton button)
    {
        System.out.println("Investigating button with row: " + button.getRow() + " and column: " + button.getColumn());
        if(button.getSurroundingMines() == 0 && !button.isMine())
            investigateQueue.add(button);
        preformInvestigation();
        for(BoardSquareButton btn : investigateQueue)
        {
            btn.setInvestigated(true);
        }
        investigateQueue.clear();
    }

    private void preformInvestigation() {
        System.out.println("Investigating...");
        boolean done;
        do {
            done = true;
            for (int i = 0; i < investigateQueue.size(); i++)
            {
                BoardSquareButton button = investigateQueue.get(i);
                for (BoardSquareButton btn : getSurroundingButtons(button))
                {
                    if (!investigateQueue.contains(btn) && btn.getSurroundingMines() == 0 && !btn.isMine())
                    {
                        System.out.println("adding to the queue");
                        investigateQueue.add(btn);
                        done = false;
                    }
                }
            }
        }
        while(!done);
    }
    public boolean hasWon()
    {
        boolean won = true;

        for(BoardSquareButton button : buttons)
        {
            if(!button.isInvestigated() && !button.isMine())
                won = false;
        }

        return won;
    }

    public boolean hasLost()
    {
        boolean lost = false;

        for(BoardSquareButton button : buttons)
        {
            if(button.isInvestigated() && button.isMine())
                lost = true;
        }

        return lost;
    }

    public ArrayList<BoardSquareButton> getSurroundingButtons(BoardSquareButton button)
    {
        ArrayList<BoardSquareButton> returnarray = new ArrayList<>();
        try { returnarray.add(getButton(button.getColumn() - 1, button.getRow() - 1).getSelf()); } catch(NullPointerException e) {}
        try { returnarray.add(getButton(button.getColumn(), button.getRow() - 1).getSelf()); } catch(NullPointerException e) {}
        try { returnarray.add(getButton(button.getColumn() + 1, button.getRow() - 1).getSelf()); } catch(NullPointerException e) {}

        try { returnarray.add(getButton(button.getColumn() - 1, button.getRow()).getSelf()); } catch(NullPointerException e) {}
        try { returnarray.add(getButton(button.getColumn() + 1, button.getRow()).getSelf()); } catch(NullPointerException e) {}

        try { returnarray.add(getButton(button.getColumn() - 1, button.getRow() + 1).getSelf()); } catch(NullPointerException e) {}
        try { returnarray.add(getButton( button.getColumn(), button.getRow() + 1).getSelf()); } catch(NullPointerException e) {}
        try { returnarray.add(getButton( button.getColumn() + 1, button.getRow() + 1).getSelf()); } catch(NullPointerException e) {}

        return returnarray;
    }

    public int countSurrounding(BoardSquareButton button)
    {
        int count = 0;
        for(BoardSquareButton btn : getSurroundingButtons(button))
        {
            if(btn.isMine())
                count++;
        }

        System.out.println("Button with x: " + button.getRow() + " and y: " + button.getColumn() + " has " + count + " surrounding mines!");

        return count;
    }

    public void updateSurrounding()
    {
        for(BoardSquareButton button : buttons)
        {
            System.out.println("Counting surrounding for button with x: " + button.getRow() + " and y: " + button.getColumn());
            button.setSurroundingMines(countSurrounding(button));
        }
    }

    /*
        GETTERS AND SETTERS
    */
    public int getColumns()
    {
        return x;
    }

    public int getRows()
    {
        return y;
    }

    public ArrayList<BoardSquareButton> getButtons()
    {
        return buttons;
    }

    public BoardSquareButton getButton(int x, int y)
    {
        for(BoardSquareButton button : buttons)
        {
            if(button.getRow() == y && button.getColumn() == x)
                return button;
        }

        return null;
    }

    public void storeButton(BoardSquareButton button)
    {
        buttons.add(button);
    }
}
