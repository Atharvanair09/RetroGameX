package code.game3;

import javax.swing.*;

public class App1 
{
    public static void main(String[] args)
    {
        int boardWidth = 600;
        int boardHeight = boardWidth;

        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);
	    frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        Snake snake = new Snake(boardWidth, boardHeight);
        frame.add(snake);
        frame.pack();
        snake.requestFocus();
    }
}