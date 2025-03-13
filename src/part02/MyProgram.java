
package part02;

import console.*;
import java.awt.Color;
import java.awt.Font;

public class MyProgram {
    public static void main(String[] args) {
        // Create a Console instance that can be used for both input and output
        Console con = new Console(true);
        
        // Set the size of the Console window
        con.setSize(500, 500);
        
        // Make the Console window visible
        con.setVisible(true);
        
        // Set the font (font name, style, size)
        con.setFont(new Font("Consolas", Font.BOLD, 20));
        
        // Set the text color (note the US spelling "Colour")
        con.setColour(Color.RED);
        
        // Print text to the Console
        con.println("Hello");
        
        // If you need to get input
        String userInput = con.readLine();
        int number = con.readInt();
        
        // Display images (assuming this functionality exists)
        // The path would be relative to your project, e.g.:
        // con.showImage("Images/myimage.png");
    }
}