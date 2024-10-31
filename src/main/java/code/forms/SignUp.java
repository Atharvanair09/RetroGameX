package code.forms;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.util.UIScale;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static javax.swing.JOptionPane.showMessageDialog;

public class   SignUp extends JPanel
{
    // Constructor for SignUp class
    public SignUp()
    {
        init(); // Initialize the form components
    }

    // Method to initialize components and layout
    private void init()
    {
        setOpaque(false); // Set panel transparency (non-opaque)

        // Set layout using MigLayout with custom insets and fill behavior
        setLayout(new MigLayout("wrap,fillx,insets 45 45 50 45", "[fill]"));

        // Create a label for the title and center its text
        JLabel title = new JLabel("Register with us", SwingConstants.CENTER);

        // Create input fields for email, username, and password
        JTextField txtEmail1 = new JTextField(); // Input field for email
        JTextField txtUsername1 = new JTextField(); // Input field for username
        JPasswordField txtPassword1 = new JPasswordField(); // Input field for password

        // Create the sign-up button
        JButton cmdSignUp = new JButton("Sign Up");

        // Set custom styles for components
        title.putClientProperty(FlatClientProperties.STYLE, "font:bold +10"); // Bold and increase font size for the title
        txtUsername1.putClientProperty(FlatClientProperties.STYLE, "margin:5,10,5,10;focusWidth:1;"); // Add margin and focus width for username field
        txtPassword1.putClientProperty(FlatClientProperties.STYLE, "margin:5,10,5,10;focusWidth:1;showRevealButton:true"); // Add margin, focus width, and a reveal button to toggle password visibility
        cmdSignUp.putClientProperty(FlatClientProperties.STYLE, "background:$Component.accentColor;borderWidth:0;focusWidth:0;"); // Style the button with accent color and no border

        // Set placeholder text for email, username, and password fields
        txtEmail1.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your email");
        txtUsername1.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your username");
        txtPassword1.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Enter your password");

        // Add components to the panel
        add(title); // Add the title
        add(new JLabel("Email"), "gapy 20"); // Add a label for the email with a vertical gap
        add(txtEmail1); // Add the email input field
        add(new JLabel("Username"), "gapy 20"); // Add a label for the username with a vertical gap
        add(txtUsername1); // Add the username input field
        add(new JLabel("Password"), "gapy 10"); // Add a label for the password with a smaller vertical gap
        add(txtPassword1); // Add the password input field
        add(cmdSignUp, "gapy 30"); // Add the sign-up button with a larger vertical gap

        // Action listener for the sign-up button
        cmdSignUp.addActionListener(e ->
        {
            String Email, Username, Password, query;
            String SUrl, SUser, SPass;
            SUrl = "jdbc:MYSQL://localhost:3306/pacman";
            SUser = "root";
            SPass = "";
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(SUrl, SUser, SPass);
                Statement st = con.createStatement();
                Email = txtEmail1.getText();
                Username = txtUsername1.getText();
                Password = txtPassword1.getText();
                if (Email.isEmpty() || !Email.endsWith("@gmail.com"))
                {
                    showMessageDialog(new JFrame(), "Email must be a valid Gmail address", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if (Username.isEmpty() || Username.length() < 4)
                {
                    showMessageDialog(new JFrame(), "Username must be at least 4 characters long", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if (Password.isEmpty() || Password.length() < 6 || !Password.matches(".*[!@#$%^&*(),.?\":{}|<>].*"))
                {
                    showMessageDialog(new JFrame(), "Password must be at least 6 characters long and contain at least " +
                            "one special character", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    query = "INSERT INTO signup(Email, Username, Password) VALUES('" + Email + "','" + Username + "','"
                            + Password + "')";
                    st.execute(query);
                    txtEmail1.setText("");
                    txtUsername1.setText("");
                    txtPassword1.setText("");
                    showMessageDialog(null, "New Account has been created successfully");}
            }
            catch (Exception s)
            {
                System.out.println("Error! " + s.getMessage());
            }
        });
    }

    // Override paintComponent to customize the panel's appearance with rounded corners and transparency
    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g.create(); // Create a 2D graphics object for rendering
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Enable anti-aliasing for smoother rendering
        int arc = UIScale.scale(20); // Define the arc size for rounded corners, scaled for high DPI screens
        g2.setColor(getBackground()); // Set the background color
        g2.setComposite(AlphaComposite.SrcOver.derive(0.6f)); // Set transparency level to 60%
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arc, arc)); // Fill the rounded rectangle background
        g2.dispose(); // Dispose of the graphics object
        super.paintComponent(g); // Call the superclass method to ensure proper painting of child components
    }
}



