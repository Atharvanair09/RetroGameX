package code.game2;

// Import necessary Swing components for creating a window
import javax.swing.*;

public class Bird
{
    public static void main(String[] args) throws Exception
    {
        // Define the width and height of the game board
        int boardWidth = 360;
        int boardHeight = 640;

        JFrame frame = new JFrame("Flappy Bird");
        frame.setVisible(true);

        // Set the size of the window based on board dimensions
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        frame.pack();

        // Set focus to the FlappyBird component for user interaction
        flappyBird.requestFocus();

        frame.setVisible(true);
    }
}
