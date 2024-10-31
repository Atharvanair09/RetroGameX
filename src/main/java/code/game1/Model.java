package code.game1;

import code.forms.Login;
import code.main.MainPage;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.*;

/**
 * The Model class represents the main game logic and rendering for a Pacman-style game.
 * It extends JPanel for GUI rendering and implements ActionListener for game updates.
 */
public class Model extends JPanel implements ActionListener
{
    // Player identification
    public static String player;

    // GUI components
    private Dimension d;
    private final Font smallFont = new Font("Arial", Font.BOLD, 14);

    // Game state flags
    private boolean inGame = false;
    private boolean dying = false;

    // Game constants

    // Size of each maze block
    private final int BLOCK_SIZE = 24;

    // Number of blocks in each row/column
    private final int N_BLOCKS = 15;

    // Total screen size
    private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;

    // Maximum number of ghosts allowed
    private final int MAX_GHOSTS = 12;

    // Movement speed of Pacman
    private final int PACMAN_SPEED = 6;

    // Game variables

    // Current number of ghosts
    private int N_GHOSTS = 6;

    // Player lives and score
    private int lives, score;

    // Direction arrays for ghost movement
    private int[] dx, dy;

    // Ghost positions and movement
    private int[] ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed;

    // Game images

    // Sprites for lives and ghosts
    private Image heart, ghost;

    // Pacman directional sprites
    private Image up, down, left, right;

    // Pacman position and movement variables

    // Current position
    private int pacman_x, pacman_y;

    // Movement delta
    private int pacmand_x, pacmand_y;

    // Requested direction
    private int req_dx, req_dy;

    // Current game level
    private int currentLevel = 1;

    // Level 1 maze data - binary representation of walls and dots
    private final short levelData1[] =
    {
            // ... [existing level data] ...
    };

    // Level 2 maze data - more complex layout
    private final short levelData2[] =
    {
            // ... [existing level data] ...
    };

    // Ghost speed settings
    private final int validSpeeds[] = {1, 2, 3, 4, 6, 8};
    private final int maxSpeed = 6;
    private int currentSpeed = 3;

    // Current maze state
    private short[] screenData;
    private Timer timer;

    /**
     * Constructor initializes the game panel and sets up initial game state
     */
    public Model()
    {
        loadImages();
        initVariables();
        addKeyListener(new TAdapter());
        setFocusable(true);
        initGame();
    }

    /**
     * Loads all game sprites and images from files
     */
    private void loadImages()
    {
        down = new ImageIcon("src/main/java/images/down.gif").getImage();
        left = new ImageIcon("src/main/java/images/left.gif").getImage();
        right = new ImageIcon("src/main/java/images/right.gif").getImage();
        up = new ImageIcon("src/main/java/images/up.gif").getImage();
        ghost = new ImageIcon("src/main/java/images/ghost.gif").getImage();
        heart = new ImageIcon("src/main/java/images/heart.png").getImage();
    }

    /**
     * Initializes all game variables and arrays
     */
    private void initVariables()
    {
        screenData = new short[N_BLOCKS * N_BLOCKS];
        d = new Dimension(400, 400);
        ghost_x = new int[MAX_GHOSTS];
        ghost_dx = new int[MAX_GHOSTS];
        ghost_y = new int[MAX_GHOSTS];
        ghost_dy = new int[MAX_GHOSTS];
        ghostSpeed = new int[MAX_GHOSTS];
        dx = new int[4];
        dy = new int[4];
        timer = new Timer(40, this);  // 40ms refresh rate (~25 fps)
        timer.start();
    }

    /**
     * Initializes a new game with default values
     */
    private void initGame()
    {
        lives = 3;
        score = 0;
        initLevel();
        N_GHOSTS = 6;
        currentSpeed = 3;
    }

    /**
     * Initializes the current level's maze data
     */
    private void initLevel()
    {
        // Select appropriate level data based on current level
        short[] currentLevelData = (currentLevel == 1) ? levelData1 : levelData2;
        for(int i = 0; i < N_BLOCKS * N_BLOCKS; i++)
        {
            screenData[i] = currentLevelData[i];
        }
        continueLevel();
    }

    /**
     * Resets character positions and continues the current level
     */
    private void continueLevel()
    {
        // Initialize ghost positions and movement
        int dx = 1;
        int random;

        for (int i = 0; i < N_GHOSTS; i++)
        {
            ghost_y[i] = 4 * BLOCK_SIZE;  // Starting Y position
            ghost_x[i] = 4 * BLOCK_SIZE;  // Starting X position
            ghost_dy[i] = 0;
            ghost_dx[i] = dx;
            dx = -dx;  // Alternate direction for each ghost

            // Randomize ghost speeds within current speed limit
            random = (int) (Math.random() * (currentSpeed + 1));
            if (random > currentSpeed)
            {
                random = currentSpeed;
            }
            ghostSpeed[i] = validSpeeds[random];
        }

        // Initialize Pacman position and direction
        pacman_x = 7 * BLOCK_SIZE;
        pacman_y = 11 * BLOCK_SIZE;
        pacmand_x = 0;
        pacmand_y = 0;
        req_dx = 0;
        req_dy = 0;
        dying = false;
    }

    /**
     * Displays the game's intro screen
     */
    private void showIntroScreen(Graphics2D g2d)
    {
        String start = "Press SPACE to start";
        g2d.setColor(Color.yellow);
        g2d.drawString(start, (SCREEN_SIZE - g2d.getFontMetrics().stringWidth(start)) / 2, SCREEN_SIZE / 2);
    }

    /**
     * Draws the score and remaining lives
     */
    private void drawScore(Graphics2D g)
    {
        g.setFont(smallFont);
        g.setColor(new Color(5, 181, 79));
        String s = "Score: " + score;
        g.drawString(s, SCREEN_SIZE / 2 + 96, SCREEN_SIZE + 16);

        // Draw remaining lives as hearts
        for (int i = 0; i < lives; i++)
        {
            g.drawImage(heart, i * 28 + 8, SCREEN_SIZE + 1, this);
        }
    }

    /**
     * Checks if the current maze is completed
     */
    private void checkMaze()
    {
        int i = 0;
        boolean finished = true;

        // Check if all dots are eaten
        while (i < N_BLOCKS * N_BLOCKS && finished)
        {
            if ((screenData[i] & 48) != 0)
            {
                finished = false;
            }
            i++;
        }

        if (finished)
        {
            score += 50;  // Bonus for completing level

            // Increase difficulty
            if (N_GHOSTS < MAX_GHOSTS)
            {
                N_GHOSTS++;
            }
            if (currentSpeed < maxSpeed)
            {
                currentSpeed++;
            }

            // Progress to next level
            currentLevel++;
            if (currentLevel > 2)
            {
                currentLevel = 1;  // Loop back to first level
            }

            initLevel();
        }
    }

    /**
     * Handles Pacman death
     */
    private void death()
    {
        lives--;

        if (lives == 0)
        {
            storeScore();
            int result = JOptionPane.showConfirmDialog(null, "Game Over!! Go back to Home?", "PACMAN", JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION)
            {
                setVisible(false);
                MainPage mainP = new MainPage();
                mainP.setVisible(true);
                mainP.setLocationRelativeTo(null);
            }
            inGame = false;
        }

        continueLevel();
    }

    /**
     * Stores the player's score in the database
     */
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
            query = "INSERT INTO game1(Player,Score)" + "VALUES('"+player+"','"+score+"')";
            st.execute(query);
        }
        catch(Exception ex)
        {
            System.out.println(" Error!" +ex.getMessage());
        }
    }

    /**
     * Main paint method for the game panel
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        doDrawing(g);
    }

    /**
     * Handles all game drawing
     */
    private void doDrawing(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);

        drawMaze(g2d);
        drawScore(g2d);

        if (inGame)
        {
            playGame(g2d);
        }
        else
        {
            showIntroScreen(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }

    /**
     * Main game loop
     */
    private void playGame(Graphics2D g2d)
    {
        if (dying)
        {
            death();
        }
        else
        {
            movePacman();
            drawPacman(g2d);
            moveGhosts(g2d);
            checkMaze();
        }
    }

    /**
     * Draws the maze walls and dots
     */
    private void drawMaze(Graphics2D g2d)
    {
        int i = 0;
        int x, y;

        for (y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE)
        {
            for (x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE)
            {
                g2d.setColor(new Color(0, 72, 251));
                g2d.setStroke(new BasicStroke(5));

                // Draw walls based on maze data
                if ((screenData[i] & 1) != 0) { g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1); }           // Left wall
                if ((screenData[i] & 2) != 0) { g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y); }           // Top wall
                if ((screenData[i] & 4) != 0) { g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1, y + BLOCK_SIZE - 1); }  // Right wall
                if ((screenData[i] & 8) != 0) { g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1, y + BLOCK_SIZE - 1); }  // Bottom wall

                // Draw dots
                if ((screenData[i] & 16) != 0)
                {
                    g2d.setColor(new Color(255, 255, 255));
                    g2d.fillRect(x + 8, y + 8, 8, 8);
                }

                i++;
            }
        }
    }

    /**
     * Handles Pacman movement and collision detection
     */
    private void movePacman()
    {
        int pos;
        short ch;

        // Check if Pacman is aligned with the maze grid
        if (pacman_x % BLOCK_SIZE == 0 && pacman_y % BLOCK_SIZE == 0)
        {
            // Get current position in maze array
            pos = pacman_x / BLOCK_SIZE + N_BLOCKS * (pacman_y / BLOCK_SIZE);
            ch = screenData[pos];

            // Check for dot collision
            if ((ch & 16) != 0)
            {
                screenData[pos] = (short) (ch & 15);
                score++;
            }

            // Handle movement requests
            if (req_dx != 0 || req_dy != 0)
            {
                // Check if requested movement is possible (no wall in the way)
                if (!((req_dx == -1 && req_dy == 0 && (ch & 1) != 0)
                        || (req_dx == 1 && req_dy == 0 && (ch & 4) != 0)
                        || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0)
                        || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0)))
                {
                    pacmand_x = req_dx;
                    pacmand_y = req_dy;
                }
            }

            // Check for wall collision in current direction
            if ((pacmand_x == -1 && pacmand_y == 0 && (ch & 1) != 0)
                    || (pacmand_x == 1 && pacmand_y == 0 && (ch & 4) != 0)
                    || (pacmand_x == 0 && pacmand_y == -1 && (ch & 2) != 0)
                    || (pacmand_x == 0 && pacmand_y == 1 && (ch & 8) != 0))
            {
                pacmand_x = 0;
                pacmand_y = 0;
            }
        }

        // Update position
        pacman_x = pacman_x + PACMAN_SPEED * pacmand_x;
        pacman_y = pacman_y + PACMAN_SPEED * pacmand_y;
    }

    /**
     * Draws Pac-Man character on the game board based on its current direction
     * Uses different images for each direction of movement
     */
    private void drawPacman(Graphics2D g2d)
    {
        if (req_dx == -1)
        {
            // Moving left
            g2d.drawImage(left, pacman_x + 1, pacman_y + 1, this);
        }
        else if (req_dx == 1)
        {
            // Moving right
            g2d.drawImage(right, pacman_x + 1, pacman_y + 1, this);
        }
        else if (req_dy == -1)
        {
            // Moving up
            g2d.drawImage(up, pacman_x + 1, pacman_y + 1, this);
        }
        else
        {
            // Moving down
            g2d.drawImage(down, pacman_x + 1, pacman_y + 1, this);
        }
    }

    /**
     * Handles ghost movement logic and collision detection with Pac-Man
     * Ghosts can move in available directions based on the maze layout
     */
    private void moveGhosts(Graphics2D g2d)
    {
        int pos;
        int count;
        for (int i = 0; i < N_GHOSTS; i++)
        {
            // Check if ghost is at intersection (aligned with block grid)
            if (ghost_x[i] % BLOCK_SIZE == 0 && ghost_y[i] % BLOCK_SIZE == 0)
            {
                // Calculate ghost's current position in the maze array
                pos = ghost_x[i] / BLOCK_SIZE + N_BLOCKS * (ghost_y[i] / BLOCK_SIZE);
                count = 0;

                // Check available directions (not blocked by walls)
                // screenData bits: 1=left, 2=top, 4=right, 8=bottom

                // Check if can move left
                if ((screenData[pos] & 1) == 0 && ghost_dx[i] != 1)
                {
                    dx[count] = -1;
                    dy[count] = 0;
                    count++;
                }

                // Check if can move up
                if ((screenData[pos] & 2) == 0 && ghost_dy[i] != 1)
                {
                    dx[count] = 0;
                    dy[count] = -1;
                    count++;
                }

                // Check if can move right
                if ((screenData[pos] & 4) == 0 && ghost_dx[i] != -1)
                {
                    dx[count] = 1;
                    dy[count] = 0;
                    count++;
                }

                // Check if can move down
                if ((screenData[pos] & 8) == 0 && ghost_dy[i] != -1)
                {
                    dx[count] = 0;
                    dy[count] = 1;
                    count++;
                }

                // Handle ghost movement based on available directions
                if (count == 0)
                {
                    // If no available moves (trapped)
                    if ((screenData[pos] & 15) == 15)
                    {
                        ghost_dx[i] = 0;
                        ghost_dy[i] = 0;
                    }
                    else
                    {
                        // Reverse direction if hit wall
                        ghost_dx[i] = -ghost_dx[i];
                        ghost_dy[i] = -ghost_dy[i];
                    }
                }
                else
                {
                    // Randomly choose available direction
                    count = (int) (Math.random() * count);
                    if (count > 3)
                    {
                        count = 3;
                    }
                    ghost_dx[i] = dx[count];
                    ghost_dy[i] = dy[count];
                }
            }

            // Update ghost position based on speed and direction
            ghost_x[i] = ghost_x[i] + (ghost_dx[i] * ghostSpeed[i]);
            ghost_y[i] = ghost_y[i] + (ghost_dy[i] * ghostSpeed[i]);
            drawGhost(g2d, ghost_x[i] + 1, ghost_y[i] + 1);

            // Check for collision with Pac-Man (within 12 pixels)
            if (pacman_x > (ghost_x[i] - 12) && pacman_x < (ghost_x[i] + 12)
                    && pacman_y > (ghost_y[i] - 12) && pacman_y < (ghost_y[i] + 12) && inGame)
            {
                dying = true;
            }
        }
    }

    /**
     * Draws a ghost at the specified coordinates
     */
    private void drawGhost(Graphics2D g2d, int x, int y)
    {
        g2d.drawImage(ghost, x, y, this);
    }

    /**
     * Game timer event handler - triggers screen repaint
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        repaint();
    }

    /**
     * Keyboard input handler for game controls
     */
    class TAdapter extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            int key = e.getKeyCode();
            if (inGame)
            {
                // Handle directional controls during gameplay
                if (key == KeyEvent.VK_LEFT)
                {
                    req_dx = -1;
                    req_dy = 0;
                }
                else if (key == KeyEvent.VK_RIGHT)
                {
                    req_dx = 1;
                    req_dy = 0;
                }
                else if (key == KeyEvent.VK_UP)
                {
                    req_dx = 0;
                    req_dy = -1;
                }
                else if (key == KeyEvent.VK_DOWN)
                {
                    req_dx = 0;
                    req_dy = 1;
                }
                else if (key == KeyEvent.VK_ESCAPE && timer.isRunning())
                {
                    // Exit game when ESC is pressed
                    inGame = false;
                }
            }
            else
            {
                // Start new game when SPACE is pressed
                if (key == KeyEvent.VK_SPACE)
                {
                    inGame = true;
                    initGame();
                }
            }
        }

        /**
         * Handles key release events
         * Stops Pac-Man movement when direction keys are released
         */
        @Override
        public void keyReleased(KeyEvent e)
        {
            int key = e.getKeyCode();

            if (key == Event.LEFT || key == Event.RIGHT || key == Event.UP || key == Event.DOWN)
            {
                req_dx = 0;
                req_dy = 0;
            }
        }
    }

    /**
     * Called when component is added to container
     * Initializes the game
     */
    @Override
    public void addNotify()
    {
        super.addNotify();
        initGame();
    }
}


