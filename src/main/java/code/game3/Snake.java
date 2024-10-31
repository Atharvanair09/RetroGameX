package code.game3;

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


public class Snake extends JPanel implements ActionListener, KeyListener
{
    public static String player;
    public static String score;
    private JButton startButton;
    private JPanel startPanel;
    private boolean gameStarted;

    private class Tile
    {
        int x;
        int y;

        Tile(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }  

    int boardWidth;
    int boardHeight;
    int tileSize = 25;
    
    //snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    //food
    Tile food;
    Random random;

    //game logic
    int velocityX;
    int velocityY;
    Timer gameLoop;

    boolean gameOver = false;

    Snake(int boardWidth, int boardHeight)
    {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<Tile>();

        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        velocityX = 1;
        velocityY = 0;

		gameLoop = new Timer(100, this); //how long it takes to start timer, milliseconds gone between frames 
        gameLoop.start();

        startPanel = new JPanel();
        startPanel.setBackground(Color.black);
        startPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);

        startButton = new JButton("Start Game");
        startButton.setPreferredSize(new Dimension(150, 50));
        startButton.setBackground(new Color(0, 153, 0));
        startButton.setForeground(Color.white);
        startButton.addActionListener(this);
        startPanel.add(startButton, gbc);

        add(startPanel, BorderLayout.CENTER);
        startPanel.setVisible(true);
        gameLoop.stop();
        gameStarted = false;
	}	
    
    public void paintComponent(Graphics g)
    {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g)
    {
        //Grid Lines
        for(int i = 0; i < boardWidth/tileSize; i++)
        {
            //(x1, y1, x2, y2)
            g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
            g.drawLine(0, i*tileSize, boardWidth, i*tileSize); 
        }

        //Food
        g.setColor(Color.red);
        // g.fillRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize);
        g.fill3DRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize, true);

        //Snake Head
        g.setColor(Color.green);
        // g.fillRect(snakeHead.x, snakeHead.y, tileSize, tileSize);
        // g.fillRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x*tileSize, snakeHead.y*tileSize, tileSize, tileSize, true);
        
        //Snake Body
        for (int i = 0; i < snakeBody.size(); i++)
        {
            Tile snakePart = snakeBody.get(i);
            // g.fillRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize);
            g.fill3DRect(snakePart.x*tileSize, snakePart.y*tileSize, tileSize, tileSize, true);
		}

        //Score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if (gameOver)
        {
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
        }
        else 
        {
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize - 16, tileSize);
            score = String.valueOf(snakeBody.size());
        }
	}

    public void placeFood()
    {
        food.x = random.nextInt(boardWidth/tileSize);
		food.y = random.nextInt(boardHeight/tileSize);
	}

    public void move()
    {
        //eat food
        if (collision(snakeHead, food))
        {
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        //move snake body
        for (int i = snakeBody.size()-1; i >= 0; i--)
        {
            Tile snakePart = snakeBody.get(i);
            if (i == 0)
            {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else
            {
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }
        //move snake head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        //game over conditions
        for (int i = 0; i < snakeBody.size(); i++)
        {
            Tile snakePart = snakeBody.get(i);

            //collide with snake head
            if (collision(snakeHead, snakePart))
            {
                gameOver = true;
            }
        }

        if (snakeHead.x*tileSize < 0 || snakeHead.x*tileSize > boardWidth || //passed left border or right border
            snakeHead.y*tileSize < 0 || snakeHead.y*tileSize > boardHeight )
        {
            gameOver = true;
        }
    }

    public boolean collision(Tile tile1, Tile tile2)
    {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    public void storeScore()
    {
        player = Login.loginuser;
        System.out.println(player);
        System.out.println(score);
        String query;
        String SUrl,SUser,SPass;
        SUrl = "jdbc:MYSQL://localhost:3306/pacman";
        SUser = "root";
        SPass = "";
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(SUrl,SUser,SPass);
            Statement st = con.createStatement();
            String s = "Score: " + score;

            query = "INSERT INTO game3(Player,Score)" + "VALUES('"+player+"','"+score+"')";
            st.execute(query);
        }
        catch(Exception ex)
        {
            System.out.println(" Error!" +ex.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == startButton || gameStarted)
        {
            startPanel.setVisible(false);
            gameLoop.start();
            gameStarted = true;
        }
        else
        {
            move();
            repaint();
            if (gameOver)
            {
                int result = JOptionPane.showConfirmDialog(null,"GameOver!!. Go back to Home?","SNAKE",JOptionPane.YES_NO_OPTION);
                storeScore();

                if (result == JOptionPane.YES_OPTION)
                {
                    setVisible(false);
                    MainPage mainP = new MainPage();
                    mainP.setVisible(true);
                    mainP.setLocationRelativeTo(null);
                }
                else if (result == JOptionPane.NO_OPTION)
                {

                }
                gameLoop.stop();
            }
        }
    }  

    @Override
    public void keyPressed(KeyEvent e)
    {
        // System.out.println("KeyEvent: " + e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !gameStarted)
        {
            startPanel.setVisible(false);
            gameLoop.start();
            gameStarted = true;
        }
        else
        {
            if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1)
            {
                velocityX = 0;
                velocityY = -1;
            }
            else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1)
            {
                velocityX = 0;
                velocityY = 1;
            }
            else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1)
            {
                velocityX = -1;
                velocityY = 0;
            }
            else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1)
            {
                velocityX = 1;
                velocityY = 0;
            }
        }
    }

    //not needed
    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }
}