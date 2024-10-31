package code.forms;

import code.main.*;
import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import com.formdev.flatlaf.util.UIScale;
import java.sql.DriverManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login extends JPanel
{
    JTextField txtUsername;
    public static String loginuser; // Declare a string to store the username of the user after successful login

    // Constructor for the Login class
    public Login()
    {
        init(); // Call the init method to initialize the login panel
    }

    // Private method to initialize the components of the login panel
    private void init()
    {
        setOpaque(false); // Set the panel to be transparent (non-opaque)
        addMouseListener(new MouseAdapter() { }); // Add a mouse listener to the panel (currently unused)

        // Set the layout of the panel using MigLayout, with specified insets and alignment properties
        setLayout(new MigLayout("wrap,fillx,insets 45 45 50 45", "[fill]"));
        // "wrap" moves components to the next line, "fillx" stretches them to fill horizontally, and insets set padding

        // Create a JLabel for the title with centered text
        JLabel title = new JLabel("Login to your account", SwingConstants.CENTER);

        // Create text fields for username and password input
        txtUsername = new JTextField(); // Text field for the username
        JPasswordField txtPassword = new JPasswordField(); // Password field for the password

        // Create a login button
        JButton cmdLogin = new JButton("Login");

        // Set custom styling for the title label (bold and increase font size by 10)
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +10");

        // Set styling for the username field, adding margins and focusWidth for border thickness
        txtUsername.putClientProperty(FlatClientProperties.STYLE, "margin:5,10,5,10;focusWidth:1;");

        // Set styling for the password field, adding margins, focusWidth, and a reveal button for password visibility
        txtPassword.putClientProperty(FlatClientProperties.STYLE, "margin:5,10,5,10;focusWidth:1;showRevealButton:true");

        // Style the login button with a background color, no border, and no focus outline
        cmdLogin.putClientProperty(FlatClientProperties.STYLE, "background:$Component.accentColor;borderWidth:0;focusWidth:0;");

        // Set placeholder text for the username and password fields
        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your username");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your password");

        // Add the title label to the panel
        add(title);

        // Add a label for "Username" with a vertical gap of 20 pixels before the component
        add(new JLabel("Username"), "gapy 20");

        // Add the username input field to the panel
        add(txtUsername);

        // Add a label for "Password" with a 10-pixel vertical gap
        add(new JLabel("Password"), "gapy 10");

        // Add the password input field to the panel
        add(txtPassword);

        // Add the login button with a 30-pixel vertical gap
        add(cmdLogin, "gapy 30");

        // Add an action listener for the login button
        cmdLogin.addActionListener(e ->
        {
            // Create a new instance of MainPage and Main (hypothetical classes)
            MainPage mainpage = new MainPage();
            main page = new main(); // This should likely be renamed to follow Java naming conventions

            String passDB = null;  // Declare a string to store the database password

            // MySQL database connection details
            String SUrl = "jdbc:MYSQL://localhost:3306/pacman"; // Database URL
            String SUser = "root"; // Database username
            String SPass = ""; // Database password (empty in this case)

            try
            {
                Class.forName("com.mysql.cj.jdbc.Driver"); // Load the MySQL JDBC driver
                Connection con = DriverManager.getConnection(SUrl, SUser, SPass); // Establish connection to the database

                // Get the input username and password from the text fields
                String user_name = txtUsername.getText().trim(); // Trim removes extra spaces
                String pass_word = txtPassword.getText().trim();

                // Check if the username or password fields are empty
                if (" ".equals(txtUsername.getText()))
                {
                    // Show error message if username is empty
                    JOptionPane.showMessageDialog(new JFrame(), "Username is required", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if (" ".equals(txtPassword.getText()))
                {
                    // Show error message if password is empty
                    JOptionPane.showMessageDialog(new JFrame(), "Password is required", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    // SQL query to check if the username and password match
                    String sql = "SELECT * FROM signup WHERE Username = ? and Password = ?";

                    // Prepare the SQL statement
                    PreparedStatement pstmt = con.prepareStatement(sql);
                    pstmt.setString(1, user_name); // Set the first parameter (username)
                    pstmt.setString(2, pass_word); // Set the second parameter (password)

                    // Execute the query and check if a record is found
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) // If a match is found
                    {
                        loginuser = txtUsername.getText().trim();
                        System.out.println("Username: " +loginuser);
                        System.out.println("Login successful");

                        mainpage.setVisible(true);
                        mainpage.setSize(997, 616);
                        page.setVisible(false);
                    }
                    else // If no match is found
                    {
                        // Show error message for incorrect username or password
                        JOptionPane.showMessageDialog(new JFrame(), "Incorrect username or password", "Error", JOptionPane.ERROR_MESSAGE);
                        txtUsername.setText(""); // Clear the username field
                        txtPassword.setText(""); // Clear the password field
                    }
                    rs.close(); // Close the ResultSet
                    pstmt.close(); // Close the PreparedStatement
                }
                con.close(); // Close the database connection
            }
            catch (Exception s)
            {
                // Print the exception message to the console
                System.out.println("Error: " + s.getMessage());
            }
        });
    }

    // Override the paintComponent method to customize the background painting
    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g.create(); // Create a 2D graphics object for rendering

        // Set rendering hints for smooth rendering (anti-aliasing)
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int arc = UIScale.scale(20); // Define the arc size for rounded corners, scaled for high DPI

        g2.setColor(getBackground()); // Set the background color
        g2.setComposite(AlphaComposite.SrcOver.derive(0.6f)); // Set the transparency of the background (60% opaque)

        // Draw a rounded rectangle as the background
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arc, arc));

        g2.dispose(); // Dispose of the graphics object
        super.paintComponent(g); // Call the superclass's paintComponent method
    }
}
