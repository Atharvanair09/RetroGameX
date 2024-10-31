package code.forms;

import code.components.*;
import code.main.main;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.util.CubicBezierEasing;
import net.miginfocom.swing.MigLayout;
import com.formdev.flatlaf.util.Animator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomeOverlay extends JWindow
{
    // Instance of the main application window
    main main = new main();

    // Panel that contains all overlay components
    private PanelOverlay overlay;

    // Constructor that takes the parent frame as parameter
    public HomeOverlay(JFrame frame)
    {
        super(frame);
        init();
    }

    // Method to close and dispose the overlay window
    public void closeWindow()
    {
        setVisible(false);
        dispose();
    }

    // Initialize the overlay window settings
    private void init()
    {
        // Set transparent background
        setBackground(new Color(0,0,0,0));

        // Use BorderLayout for component arrangement
        setLayout(new BorderLayout());

        // Create the main overlay panel
        overlay = new PanelOverlay();

        // Add overlay panel to the window
        add(overlay);
    }

    // Inner class that handles the overlay panel and its components
    public class PanelOverlay extends JPanel
    {
        private JPanel header;           // Panel for header components
        private Animator loginAnimator;  // Animator for login panel sliding
        private Animator signupAnimator; // Animator for signup panel sliding
        private boolean showLogin;       // Flag to track login panel visibility
        private boolean showSignup;      // Flag to track signup panel visibility
        private Login panelLogin;        // Login panel instance
        private SignUp panelSignup;      // Signup panel instance
        private MigLayout migLayout;     // Layout manager for the panel

        // Constructor for PanelOverlay
        public PanelOverlay()
        {
            init();
        }

        private void init()
        {
            // Make panel transparent
            setOpaque(false);

            // Setup MigLayout with specific constraints
            migLayout = new MigLayout("fill,insets 10 10 10 10", "fill", "[grow 0][]");
            setLayout(migLayout);

            createHeader();
            createLogin();
            createSignUp();

            // Add mouse listener to hide login panel when clicking outside
            addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseReleased(MouseEvent e)
                {
                    runLoginAnimation(false);
                }
            });

            // Add mouse listener to hide signup panel when clicking outside
            addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseReleased(MouseEvent e)
                {
                    runSignUpAnimation(false);
                }
            });

            // Setup animator for login panel sliding animation
            loginAnimator = new Animator(500, new Animator.TimingTarget()
            {
                @Override
                public void timingEvent(float v)
                {
                    float f = showLogin ? v : 1f - v;
                    int x = (int) ((350 + 180) * f);
                    migLayout.setComponentConstraints(panelLogin, "pos 100%-" + x + " 0.5al, w 350");
                    revalidate();
                }
            });
            loginAnimator.setInterpolator(CubicBezierEasing.EASE_IN);

            // Setup animator for signup panel sliding animation
            signupAnimator = new Animator(500, new Animator.TimingTarget()
            {
                @Override
                public void timingEvent(float v)
                {
                    float f = showSignup ? v : 1f - v;
                    int x = (int) ((350 + 180) * f);
                    migLayout.setComponentConstraints(panelSignup, "pos 100%-" + x + " 0.5al, w 350");
                    revalidate();
                }
            });
            signupAnimator.setInterpolator(CubicBezierEasing.EASE_IN);
        }

        // Create and setup the header panel with navigation buttons
        private void createHeader()
        {
            header = new JPanel(new MigLayout("fill","[]push[][]"));
            header.setOpaque(false);

            // Create and style the title
            JLabel title = new JLabel("RetroGameX");
            title.putClientProperty(FlatClientProperties.STYLE,"" + "font:bold +10");
            title.setForeground(Color.WHITE);

            // Create header buttons
            HeaderButton home = new HeaderButton("Home");
            HeaderButton login = new HeaderButton("Login");
            HeaderButton signup = new HeaderButton("SignUp");
            HeaderButton exit = new HeaderButton("Exit");

            // Add action listener for login button
            login.addActionListener(e ->
            {
                runSignUpAnimation(false);
                runLoginAnimation(true);
            });

            // Add action listener for signup button
            signup.addActionListener(e ->
            {
                runLoginAnimation(false);
                runSignUpAnimation(true);
            });

            // Add action listener for exit button
            exit.addActionListener(e ->
            {
                main.setVisible(false);
                //System.exit(0);
            });

            // Add components to header
            header.add(title);
            header.add(home);
            header.add(login);
            header.add(signup);
            header.add(exit);
            add(header);
        }

        // Helper method to get the instance of Home class
        private Home getHomeInstance()
        {
            return (Home) SwingUtilities.getAncestorOfClass(Home.class, this);
        }

        // Create and position the login panel
        private void createLogin()
        {
            panelLogin = new Login();
            add(panelLogin,"pos 100% 0.5al,w 350");
        }

        // Create and position the signup panel
        private void createSignUp()
        {
            panelSignup = new SignUp();
            add(panelSignup,"pos 100% 0.5al,w 350");
        }

        // Handle the animation for showing/hiding login panel
        private void runLoginAnimation(boolean show)
        {
            if (showLogin != show)
            {
                if (!loginAnimator.isRunning())
                {
                    showLogin = show;
                    loginAnimator.start();
                }
            }
        }

        // Handle the animation for showing/hiding signup panel
        private void runSignUpAnimation(boolean show)
        {
            if (showSignup != show)
            {
                if (!signupAnimator.isRunning())
                {
                    showSignup = show;
                    signupAnimator.start();
                }
            }
        }
    }
}