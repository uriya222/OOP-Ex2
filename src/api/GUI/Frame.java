package api.GUI;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame{
    @Override
    public void show(){
        //1. Create the frame.
        JFrame frame = new JFrame("FrameDemo");

//2. Optional: What happens when the frame closes?
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//3. Create components and put them in the frame.
//...create emptyLabel...
        BorderLayout b = new BorderLayout();
        String emptyLabel = "hello world!";
        //frame.getContentPane().add(new BorderLayout());

//4. Size the frame.
        frame.pack();

//5. Show it.
        frame.setVisible(true);
    } 
}
