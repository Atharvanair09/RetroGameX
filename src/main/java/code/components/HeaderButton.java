package code.components;

// Import FlatLaf's client properties, used for setting custom UI properties
import com.formdev.flatlaf.FlatClientProperties;

// Import Swing components (JButton in this case)
import javax.swing.*;

// Import AWT components for handling basic UI elements (Cursor, etc.)
import java.awt.*;


public class HeaderButton extends JButton
{
    // Constructor that takes a string (button label) as an argument
    public HeaderButton(String text)
    {
        // Call the superclass (JButton) constructor to set the button's label
        super(text);
        init();
    }

    // Method to initialize custom button properties
    public void init()
    {
        // Disable content area filling (transparent background)
        setContentAreaFilled(false);

        // Set the mouse cursor to a hand when hovering over the button
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Apply custom styling using FlatLaf properties (increase font size and make it bold)
        putClientProperty(FlatClientProperties.STYLE, "font:bold +5");
    }
}

