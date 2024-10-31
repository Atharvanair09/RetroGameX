package code.game2;

import code.forms.Login;
import code.main.MainPage;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener
{
    // Static variable to store player name
    public static String player;

    // Define board dimensions
    int boardWidth = 360;
    int boardHeight = 640;

    // Images for the background, bird, and pipes
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    // Bird properties
    int birdX = boardWidth / 8;
    int birdY = boardWidth / 2;
    int birdWidth = 34;
    int birdHeight = 24;

    // Class representing the bird in the game
    class Bird
    {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        // Constructor for Bird class that assigns an image
        Bird(Image img)
        {
            this.img = img;
        }
    }

    // Pipe properties
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64; // Scale of 1/6
    int pipeHeight = 512;

    // Class representing a pipe in the game
    class Pipe
    {
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false; // Tracks if pipe has been passed by the bird

        // Constructor for Pipe class that assigns an image
        Pipe(Image img)
        {
            this.img = img;
        }
    }

    // Game logic variables
    Bird bird;

    // Speed of pipes moving to the left (gives illusion of bird moving forward)
    int velocityX = -4;

    // Speed of bird moving up/down
    int velocityY = 0;

    // Gravity to pull bird downwards
    int gravity = 1;

    // List of pipes in the game
    ArrayList<Pipe> pipes;

    // Random generator for pipe positioning
    Random random = new Random();

    // Game timers

    // Main game loop timer
    Timer gameLoop;

    // Timer to place pipes at intervals
    Timer placePipeTimer;

    // Flag for game-over state
    boolean gameOver = false;

    // Flag for game-started state
    boolean gameStarted = false;

    // Game score
    double score = 0;

    // Start Menu elements

    // Game title label
    JLabel titleLabel;

    // Start button to begin game
    JButton startButton;

    // Constructor for the FlappyBird game class
    FlappyBird()
    {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        // Load images for the game
        backgroundImg = new ImageIcon("src/main/java/images/flappybirdbg.png").getImage();
        birdImg = new ImageIcon("src/main/java/images/flappybird.png").getImage();
        topPipeImg = new ImageIcon("src/main/java/images/toppipe.png").getImage();
        bottomPipeImg = new ImageIcon("src/main/java/images/bottompipe.png").getImage();

        // Initialize bird and pipes list
        bird = new Bird(birdImg);
        pipes = new ArrayList<Pipe>();

        // Timer to place pipes periodically
        placePipeTimer = new Timer(1500, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Method to place pipes on screen
                placePipes();
            }
        });

        // Main game timer for the game loop
        gameLoop = new Timer(1000 / 60, this);

        // Initialize Start Menu
        titleLabel = new JLabel("Flappy Bird");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.PLAIN, 20));
        startButton.addActionListener(e -> startGame());

        // Layout for Start Menu components
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titleLabel, gbc);
        gbc.gridy = 1;
        add(startButton, gbc);
    }

    // Method to start the game
    private void startGame()
    {
        gameStarted = true;
        setLayout(new BorderLayout());
        remove(titleLabel);
        remove(startButton);
        placePipeTimer.start();
        gameLoop.start();
    }

    // Method to place pipes on the screen
    void placePipes()
    {
        int randomPipeY = (int) (pipeY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = boardHeight / 4;

        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
    }

    // Method to paint game components on screen
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }

    // Draws game elements on the screen
    public void draw(Graphics g)
    {
        // Background
        g.drawImage(backgroundImg, 0, 0, this.boardWidth, this.boardHeight, null);

        // Bird
        g.drawImage(birdImg, bird.x, bird.y, bird.width, bird.height, null);

        // Pipes
        for (Pipe pipe : pipes)
        {
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        // Score display
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameOver)
        {
            g.drawString("Game Over: " + String.valueOf((int) score), 10, 35);
        }
        else
        {
            g.drawString(String.valueOf((int) score), 10, 35);
        }
    }

    // Handles movement logic for the bird and pipes
    public void move()
    {
        // Update bird's vertical position
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);

        // Move pipes and check for collisions
        for (Pipe pipe : pipes)
        {
            pipe.x += velocityX;

            // Check if pipe is passed for scoring
            if (!pipe.passed && bird.x > pipe.x + pipe.width)
            {
                score += 0.5;
                pipe.passed = true;
            }

            // Check for collision with bird
            if (collision(bird, pipe))
            {
                gameOver = true;
            }
        }

        // Check if bird has fallen off the screen
        if (bird.y > boardHeight)
        {
            gameOver = true;
        }
    }

    // Collision detection between bird and pipe
    boolean collision(Bird a, Pipe b)
    {
        return a.x < b.x + b.width &&  // Bird's top left corner does not reach pipe's top right corner
                a.x + a.width > b.x &&  // Bird's top right corner passes pipe's top left corner
                a.y < b.y + b.height && // Bird's top left corner does not reach pipe's bottom left corner
                a.y + a.height > b.y;   // Bird's bottom left corner passes pipe's top left corner
    }

    // Store player score in the database
    public void storeScore()
    {
        player = Login.loginuser;
        String query;
        String SUrl = "jdbc:MYSQL://localhost:3306/pacman";
        String SUser = "root";
        String SPass = "";
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(SUrl, SUser, SPass);
            Statement st = con.createStatement();
            query = "INSERT INTO game2(Player,Score) VALUES('" + player + "','" + score + "')";
            st.execute(query);
        }
        catch (Exception ex)
        {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    // Handles game updates on each tick
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (gameStarted)
        {
            move();
            repaint();
            if (gameOver)
            {
                int result = JOptionPane.showConfirmDialog(null, "Game Over! Go back to Home?", "FLAPPY BIRD", JOptionPane.YES_NO_OPTION);
                storeScore();

                if (result == JOptionPane.YES_OPTION)
                {
                    setVisible(false);
                    MainPage mainP = new MainPage();
                    mainP.setVisible(true);
                    mainP.setLocationRelativeTo(null);
                }

                // Stop game timers
                placePipeTimer.stop();
                gameLoop.stop();
            }
        }
    }

    // Key press events for user controls
    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            if (gameStarted)
            {
                velocityY = -9; // Bird jumps up when space is pressed
            }
            else
            {
                startGame();
            }

            if (gameOver)
            {
                // Reset game state if it is over
                bird.y = birdY;
                velocityY = 0;
                pipes.clear();
                gameOver = false;
                score = 0;
                gameLoop.start();
                placePipeTimer.start();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        // Required for KeyListener, but not used here
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        // Required for KeyListener, but not used here
    }
}
