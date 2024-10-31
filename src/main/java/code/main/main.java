package code.main;  // Defines the package that contains this class.

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.util.UIScale;
import code.forms.*;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class main extends JFrame // Extends JFrame to create a custom main window.
{
    public Home home;  // Declaring a variable 'home' to reference the Home panel.

    public main()  // Constructor for the main class.
    {
        init();  // Calling the init method to initialize the window settings and components.
    }

    private void init()  // Method to initialize the JFrame settings and event listeners.
    {
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Close the application when the window is closed.
        //setUndecorated(true);  // Remove title bar (no minimize, maximize, or close buttons).
        setSize(UIScale.scale(new Dimension(1365,768)));  // Set the size of the window (scaled for DPI settings).
        setLocationRelativeTo(null);  // Center the window on the screen.
        home = new Home();  // Instantiate the 'Home' panel.
        setContentPane(home);  // Set the 'Home' panel as the content of the window (replacing default content).

        // Add a window listener to handle actions when the window is opened or closed.
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowOpened(WindowEvent e)  // Trigger when the window is opened.
            {
                home.initOverlay(main.this);  // Initialize overlay functionality in the 'Home' panel.
                home.play();  // Start playing the video in the 'Home' panel.
                //playMusic();
            }

            @Override
            public void windowClosed(WindowEvent e)  // Trigger when the window is closed.
            {
                home.stop();
            }
        });
    }

    public static void main(String[] args)  // Main method to launch the application.
    {
        FlatRobotoFont.install();  // Install the Roboto font for UI components.
        FlatLaf.registerCustomDefaultsSource("code.themes");  // Register custom theme settings from the 'themes' directory.
        FlatMacDarkLaf.setup();  // Apply the FlatLaf dark theme (FlatMacDarkLaf).
        UIManager.put("defaultFont",new Font(FlatRobotoFont.FAMILY, Font.PLAIN,13));  // Set Roboto as the default font (plain, size 13) for the entire application.

        // Start the main window on the Event Dispatch Thread.
        EventQueue.invokeLater(() -> new main().setVisible(true));  // Create and display the main window in the Swing Event Dispatch Thread.
    }
}

