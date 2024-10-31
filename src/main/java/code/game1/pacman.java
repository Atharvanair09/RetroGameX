package code.game1;

import javax.swing.JFrame;

public class pacman extends JFrame
{
	// Constructor for the "pacman" class
	public pacman()
	{
		// Add a new instance of the Model class (presumably the game model) to the frame
		add(new Model());
	}

	// Main method to start the program
	public static void main(String[] args)
	{
		// Create a new instance of the "pacman" window
		pacman pac = new pacman();

		// Make the window visible to the user
		pac.setVisible(true);

		// Set the title of the window to "Pacman"
		pac.setTitle("Pacman");

		// Set the size of the window (width x height)
		pac.setSize(380, 420);

		// Center the window on the screen
		pac.setLocationRelativeTo(null);
	}
}
